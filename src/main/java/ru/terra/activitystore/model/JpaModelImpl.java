package ru.terra.activitystore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.internal.sessions.remote.RemoveServerSideRemoteValueHolderCommand;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.CardCellVal;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.db.entity.Vlist;
import ru.terra.activitystore.db.manager.BlockJpaController;
import ru.terra.activitystore.db.manager.CardCellValJpaController;
import ru.terra.activitystore.db.manager.CardJpaController;
import ru.terra.activitystore.db.manager.CellJpaController;
import ru.terra.activitystore.db.manager.ListValJpaController;
import ru.terra.activitystore.db.manager.TemplateJpaController;
import ru.terra.activitystore.db.manager.VlistJpaController;
import ru.terra.activitystore.db.manager.exceptions.IllegalOrphanException;
import ru.terra.activitystore.db.manager.exceptions.NonexistentEntityException;

public class JpaModelImpl extends ActivityStoreModel {

	private BlockJpaController bc;
	private CardJpaController cardc;
	private CellJpaController cellc;
	private TemplateJpaController tc;
	private CardCellValJpaController ccv;
	private VlistJpaController ljc;
	private ListValJpaController lvjc;

	public JpaModelImpl() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("activitystorePU");
		bc = new BlockJpaController(emf);
		cardc = new CardJpaController(emf);
		cellc = new CellJpaController(emf);
		tc = new TemplateJpaController(emf);
		ccv = new CardCellValJpaController(emf);
		ljc = new VlistJpaController(emf);
		lvjc = new ListValJpaController(emf);
	}

	@Override
	public void start() {
	}

	@Override
	public List<Block> getAllBlocks() {
		return bc.findBlockEntities();
	}

	@Override
	public List<Block> getBlocks(Block root) {
		return bc.findBlockEntities(root);
	}

	@Override
	public List<Card> getCards(Block block) {
		return cardc.findCardEntities(block);
	}

	@Override
	public List<Card> getAllCards() {
		return cardc.findCardEntities();
	}

	@Override
	public List<Cell> getCells(Card card) {
		return cellc.findCellEntities(card);
	}

	@Override
	public List<Cell> getAllCells() {
		return cellc.findCellEntities();
	}

	@Override
	public List<Template> getTemplates() {
		return tc.findTemplateEntities();
	}

	@Override
	public Card generateCardFromTemplate(Template template, Block block) {
		Card newCard = new Card();
		newCard.setBlockId(block.getId());
		newCard.setCellList(new ArrayList<Cell>(template.getCard().getCellList()));
		newCard.setCreationDate(new Date());
		newCard.setName(template.getCard().getName());
		newCard.setTemplateId(template.getId());
		saveCard(newCard);
		return newCard;
	}

	@Override
	public Block createBlock(String name, Block parent) {
		Block newBlock = new Block();
		newBlock.setName(name);
		newBlock.setParent(parent.getId());
		bc.create(newBlock);
		return newBlock;
	}

	@Override
	public Boolean deleteBlock(Block block, Boolean recursive) {
		if (recursive) {
			for (Card c : getCards(block))
				deleteCard(c, true);
			for (Block b : getBlocks(block)) {
				deleteBlock(b, true);
			}
		}
		try {
			bc.destroy(block.getId());
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Card createCard(String name, Block parent) {
		Card newCard = new Card();
		newCard.setName(name);
		newCard.setBlockId(parent != null ? parent.getId() : null);
		cardc.create(newCard);
		return newCard;
	}

	@Override
	public Card createCard(String name, Template parent) {
		Card newCard = new Card();
		newCard.setName(name);
		newCard.setTemplateId(parent != null ? parent.getId() : null);
		cardc.create(newCard);
		return newCard;
	}

	@Override
	public Boolean deleteCard(Card card, Boolean recursive) {
		try {
			cardc.destroy(card.getId());
		} catch (IllegalOrphanException e) {
			e.printStackTrace();
			return false;
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Card addCellToCard(Cell cell, Card card) {
		if (cell.getId() == null) {
			cell = saveCell(cell);
			cell.getCardList().add(card);
			try {
				cellc.edit(cell);
			} catch (NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		card.getCellList().add(cell);
		try {
			cardc.edit(card);
		} catch (IllegalOrphanException e) {
			e.printStackTrace();
			return null;
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return card;
	}

	@Override
	public Card deleteCellFromCard(Cell cell, Card card) {
		card.getCellList().remove(cell);
		try {
			cardc.edit(card);
		} catch (IllegalOrphanException e) {
			e.printStackTrace();
			return null;
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return card;
	}

	@Override
	public Block addCardToBlock(Card card, Block block) {
		card.setBlockId(block.getId());
		if (card.getId() == null)
			card = saveCard(card);
		try {
			cardc.edit(card);
		} catch (IllegalOrphanException e) {
			e.printStackTrace();
			return null;
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return block;
	}

	@Override
	public Block getBlock(Integer id) {
		return bc.findBlock(id);
	}

	@Override
	public Card getCard(Integer id) {
		return cardc.findCard(id);
	}

	@Override
	public Boolean addBlockToBlock(Block newBlock, Block parent) {
		newBlock.setParent(parent.getId());
		if (newBlock.getId() == null)
			newBlock = saveBlock(newBlock);
		try {
			bc.edit(newBlock);
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void updateBlock(Block block) {
		try {
			bc.edit(block);
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateCard(Card card) {
		try {
			cardc.edit(card);
		} catch (IllegalOrphanException e) {
			e.printStackTrace();
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Card saveCard(Card card) {
		cardc.create(card);
		return card;
	}

	@Override
	public Cell saveCell(Cell cell) {
		cellc.create(cell);
		return cell;
	}

	@Override
	public Block saveBlock(Block block) {
		bc.create(block);
		return block;
	}

	@Override
	public void updateCell(Cell cell) {
		try {
			cellc.edit(cell);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Card createCard(String name) {
		Card newCard = new Card();
		newCard.setName(name);
		cardc.create(newCard);
		return newCard;
	}

	@Override
	public String getCellValue(Integer cardId, Integer cellId) {
		return ccv.getVal(cardId, cellId);
	}

	@Override
	public void setCellValue(Integer cardId, Integer cellId, String val) {
		CardCellVal cc = ccv.getCardCellVal(cardId, cellId);
		if (cc != null) {
			cc.setVal(val);
			try {
				ccv.edit(cc);
			} catch (NonexistentEntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			cc = new CardCellVal();
			cc.setCardId(cardId);
			cc.setCellId(cellId);
			cc.setVal(val);
			ccv.create(cc);
		}

	}

	@Override
	public Template createTemlate(String name, Card card) {
		Template newTpl = new Template();
		newTpl.setName(name);
		newTpl.setCard(card);
		tc.create(newTpl);
		return newTpl;
	}

	@Override
	public void updateTemplate(Template tpl) {
		try {
			tc.edit(tpl);
		} catch (NonexistentEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTemplate(Template tpl) {
		try {
			tc.destroy(tpl.getId());
		} catch (NonexistentEntityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Template createTemlate(String name) {
		Template newTpl = new Template();
		newTpl.setName(name);
		tc.create(newTpl);
		return newTpl;
	}

	@Override
	public Vlist getList(Integer id) {
		if (id != null)
			return ljc.findVlist(id);
		else
			return null;
	}

	@Override
	public List<Vlist> getAllLists() {
		return ljc.findVlistEntities();
	}

	@Override
	public String getListValue(Integer val) {
		return lvjc.findListVal(val).getValue();
	}
}