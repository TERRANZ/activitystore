package ru.terra.activitystore.controller;

import java.util.Date;
import java.util.List;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.model.ActivityStoreModel;
import ru.terra.activitystore.view.ActivityStoreView;

public class ActivityStoreController
{
	private ActivityStoreModel model;
	private ActivityStoreView view;
	private static ActivityStoreController instance = new ActivityStoreController();

	public ActivityStoreController(ActivityStoreModel model, ActivityStoreView view)
	{
		super();
		this.model = model;
		this.view = view;
	}

	public void start()
	{
		model.start();
		view.start();
	}

	public static ActivityStoreController getInstance()
	{
		return instance;
	}

	private ActivityStoreController()
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

	public List<Card> getAllCards()
	{
		return model.getAllCards();
	}

	public List<Cell> getCells(Card card)
	{
		return model.getCells(card);
	}

	public List<Cell> getAllCells()
	{
		return model.getAllCells();
	}

	public List<Template> getAllTemplates()
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

	public Card createCard(String name)
	{
		return model.createCard(name);
	}

	public Card createCard(String name, Block parent)
	{
		return model.createCard(name, parent);
	}

	public Card createCard(String name, Template parent)
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

	public Block addCardToBlock(Card card, Block block)
	{
		return model.addCardToBlock(card, block);
	}

	public Block getBlock(Integer id)
	{
		return model.getBlock(id);
	}

	public Card getCard(Integer id)
	{
		return model.getCard(id);
	}

	public void onViewStarted()
	{
		view.fillBlocksTree(model.getAllBlocks());
	}

	public void addBlockToBlock(Block newBlock, Block parent)
	{
		model.addBlockToBlock(newBlock, parent);
	}

	public void updateBlock(Block block)
	{
		block.setUpdateDate(new Date());
		model.updateBlock(block);
	}

	public void updateCard(Card card)
	{
		card.setUpdateDate(new Date());
		model.updateCard(card);
	}

	public Card saveNewCard(Card card)
	{
		return model.saveCard(card);
	}

	public Cell saveNewCell(Cell cell)
	{
		return model.saveCell(cell);
	}

	public Block saveNewBlock(Block block)
	{
		return model.saveBlock(block);
	}

	public Cell createCell(String name)
	{
		Cell ret = new Cell();
		ret.setComment(name);
		return model.saveCell(ret);
	}

	public void updateCell(Cell cell)
	{
		model.updateCell(cell);
	}
}
