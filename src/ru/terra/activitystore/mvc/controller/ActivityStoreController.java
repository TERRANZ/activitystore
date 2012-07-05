package ru.terra.activitystore.mvc.controller;

import java.util.List;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.mvc.model.ActivityStoreModel;
import ru.terra.activitystore.mvc.view.ActivityStoreView;

public abstract class ActivityStoreController
{
	private ActivityStoreModel model;
	private ActivityStoreView view;

	public ActivityStoreController(ActivityStoreModel model, ActivityStoreView view)
	{
		super();
		this.model = model;
		this.view = view;
	}

	public ActivityStoreController()
	{
		this.model = ActivityStoreModel.getDefaultImpl();
		this.view = ActivityStoreView.getDefaultView(this);
	}

	public List<Block> getBlocks(Block root)
	{
		return model.getBlocks(root);
	}

	public List<Card> getCards(Block block)
	{
		return model.getCards(block);
	}

	public List<Cell> getCells(Card card)
	{
		return model.getCells(card);
	}

	public List<Cell> getAllCells()
	{
		return model.getAllCells();
	}

	public List<Template> getTemplates()
	{
		return model.getTemplates();
	}

	public Card generateCardFromTemplate(Template template, Block block)
	{
		return model.generateCardFromTemplate(template, block);
	}

	public Block createBlock(String name, Block parent)
	{
		return model.createBlock(name, parent);
	}

	public Boolean deleteBlock(Block block, Boolean recursive)
	{
		return model.deleteBlock(block, recursive);
	}

	public Card createCard(String name, Block parent)
	{
		return model.createCard(name, parent);
	}

	public Boolean deleteCard(Card card, Boolean recursive)
	{
		return model.deleteCard(card, recursive);
	}

	public Card addCellToCard(Cell cell, Card card)
	{
		return model.addCellToCard(cell, card);
	}

	public Card deleteCellFromCard(Cell cell, Card card)
	{
		return model.deleteCellFromCard(cell, card);
	}

}
