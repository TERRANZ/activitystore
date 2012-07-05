package ru.terra.activitystore.mvc.model;

import java.util.List;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;

public class HibernateModelImpl extends ActivityStoreModel
{

	@Override
	public List<Block> getBlocks(Block root)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> getCards(Block block)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cell> getCells(Card card)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cell> getAllCells()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Template> getTemplates()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card generateCardFromTemplate(Template template, Block block)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Block createBlock(String name, Block parent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteBlock(Block block, Boolean recursive)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card createCard(String name, Block parent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteCard(Card card, Boolean recursive)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card addCellToCard(Cell cell, Card card)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card deleteCellFromCard(Cell cell, Card card)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
