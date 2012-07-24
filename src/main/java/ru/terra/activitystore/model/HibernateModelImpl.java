package ru.terra.activitystore.model;

import java.util.List;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.db.manager.BlockPersistanceManager;
import ru.terra.activitystore.db.manager.CardPersistanceManager;
import ru.terra.activitystore.db.manager.CellPersistanceManager;
import ru.terra.activitystore.db.manager.TemplatePersistanceManager;

public class HibernateModelImpl extends ActivityStoreModel
{
	private BlockPersistanceManager bpm;
	private CardPersistanceManager cardpm;
	private TemplatePersistanceManager tpm;
	private CellPersistanceManager cellpm;

	public HibernateModelImpl()
	{
		bpm = new BlockPersistanceManager();
		cardpm = new CardPersistanceManager();
		tpm = new TemplatePersistanceManager();
		cellpm = new CellPersistanceManager();
	}

	@Override
	public List<Block> getBlocks(Block root)
	{
		return bpm.getChildrenBlocks(root);
	}

	@Override
	public List<Card> getCards(Block block)
	{
		return cardpm.getCardsFromBlock(block);
	}

	@Override
	public List<Cell> getCells(Card card)
	{
		return cellpm.getCellsFromCard(card);
	}

	@Override
	public List<Cell> getAllCells()
	{
		return cellpm.findAll(Cell.class);
	}

	@Override
	public List<Template> getTemplates()
	{

		return tpm.findAll(Template.class);
	}

	@Override
	public Card generateCardFromTemplate(Template template, Block block)
	{
		return null;
	}

	@Override
	public Block createBlock(String name, Block parent)
	{
		Block newBlock = new Block();
		newBlock.setParent(parent != null ? parent.getId() : null);
		newBlock.setName(name);
		bpm.insert(newBlock);
		return newBlock;
	}

	@Override
	public Boolean deleteBlock(Block block, Boolean recursive)
	{
		if (recursive)
		{
			for (Block c : getBlocks(block))
			{
				deleteBlock(c, true);
				bpm.delete(c);
			}
		}
		bpm.delete(block);
		return true;
	}

	@Override
	public Card createCard(String name, Block parent)
	{
		Card newCard = new Card();
		newCard.setName(name);
		newCard.setBlockId(parent.getId());
		cardpm.insert(newCard);
		return newCard;
	}

	@Override
	public Boolean deleteCard(Card card, Boolean recursive)
	{
		if (recursive)
		{
			for (Cell c : getCells(card))
			{
				cellpm.delete(c);
			}
		}
		cardpm.delete(card);
		return null;
	}

	@Override
	public Card addCellToCard(Cell cell, Card card)
	{
		card.getCells().add(cell);
		cardpm.update(card);
		return card;
	}

	@Override
	public Card deleteCellFromCard(Cell cell, Card card)
	{
		card.getCells().remove(cell);
		cardpm.update(card);
		return card;
	}

	@Override
	public void start()
	{
	}

	@Override
	public Block addCardToBlock(Card card, Block block)
	{
		card.setBlockId(block.getId());
		cardpm.update(card);
		return block;
	}

	@Override
	public Card createCard(String name, Template parent)
	{
		Card newCard = new Card();
		newCard.setName(name);
		newCard.setTemplateId(parent.getId());
		cardpm.insert(newCard);
		return newCard;
	}

	@Override
	public Block getBlock(Integer id)
	{
		return bpm.findById(id);
	}

	@Override
	public Card getCard(Integer id)
	{
		return cardpm.findById(id);
	}

	@Override
	public List<Block> getAllBlocks()
	{
		return bpm.findAll(Block.class);
	}

	@Override
	public Boolean addBlockToBlock(Block newBlock, Block parent)
	{
		newBlock.setParent(parent.getId());
		bpm.insert(newBlock);
		return true;
	}

	@Override
	public void updateBlock(Block block)
	{
		bpm.update(block);
	}

	@Override
	public void updateCard(Card card)
	{
		cardpm.update(card);
	}

	@Override
	public List<Card> getAllCards()
	{
		return cardpm.findAll(Card.class);
	}

}
