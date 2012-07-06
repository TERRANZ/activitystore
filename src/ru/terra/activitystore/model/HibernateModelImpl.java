package ru.terra.activitystore.model;

import java.util.List;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.db.manager.BlockPersistanceManager;

public class HibernateModelImpl extends ActivityStoreModel
{
	private BlockPersistanceManager bpm;

	public HibernateModelImpl()
	{
		bpm = new BlockPersistanceManager();
	}

	@Override
	public List<Block> getBlocks(Block root)
	{
		return bpm.getChildrenBlocks(root);
	}

	@Override
	public List<Card> getCards(Block block)
	{
		return null;
	}

	@Override
	public List<Cell> getCells(Card card)
	{
		return null;
	}

	@Override
	public List<Cell> getAllCells()
	{
		return null;
	}

	@Override
	public List<Template> getTemplates()
	{

		return null;
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
		return null;
	}

	@Override
	public Card deleteCellFromCard(Cell cell, Card card)
	{
		return null;
	}

	@Override
	public void start()
	{
	}

}
