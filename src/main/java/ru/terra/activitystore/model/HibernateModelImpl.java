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
		newBlock.setBlock(parent);
		newBlock.setName(name);
		bpm.insert(newBlock);
		return newBlock;
	}

	@Override
	public Boolean deleteBlock(Block block, Boolean recursive)
	{
		return null;
	}

	@Override
	public Card createCard(String name, Block parent)
	{
		return null;
	}

	@Override
	public Boolean deleteCard(Card card, Boolean recursive)
	{

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

}
