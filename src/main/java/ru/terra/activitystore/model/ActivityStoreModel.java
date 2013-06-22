package ru.terra.activitystore.model;

import java.util.List;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.db.entity.Vlist;

public abstract class ActivityStoreModel
{
	public static ActivityStoreModel getDefaultImpl()
	{
		return new JpaModelImpl();
	}

	public abstract void start();

	public abstract List<Block> getAllBlocks();

	public abstract List<Block> getBlocks(Block root);

	public abstract List<Card> getCards(Block block);

	public abstract List<Card> getAllCards();

	public abstract List<Cell> getCells(Card card);

	public abstract List<Cell> getAllCells();

	public abstract List<Template> getTemplates();

	public abstract Card generateCardFromTemplate(Template template, Block block);

	public abstract Block createBlock(String name, Block parent);

	public abstract Boolean deleteBlock(Block block, Boolean recursive);

	public abstract Card createCard(String name, Block parent);

	public abstract Card createCard(String name, Template parent);

	public abstract Boolean deleteCard(Card card, Boolean recursive);

	public abstract Card addCellToCard(Cell cell, Card card);

	public abstract Card deleteCellFromCard(Cell cell, Card card);

	public abstract Block addCardToBlock(Card card, Block block);

	public abstract Block getBlock(Integer id);

	public abstract Card getCard(Integer id);

	public abstract Boolean addBlockToBlock(Block newBlock, Block parent);

	public abstract void updateBlock(Block block);

	public abstract void updateCard(Card card);

	public abstract Card saveCard(Card card);

	public abstract Cell saveCell(Cell cell);

	public abstract Block saveBlock(Block block);

	public abstract void updateCell(Cell cell);

	public abstract Card createCard(String name);

	public abstract String getCellValue(Integer cardId, Integer cellId);

	public abstract void setCellValue(Integer cardId, Integer cellId, String val);

	public abstract Template createTemlate(String name, Card card);

	public abstract Template createTemlate(String name);

	public abstract void updateTemplate(Template tpl);

	public abstract void deleteTemplate(Template tpl);

	public abstract List<Vlist> getAllLists();

	public abstract Vlist getList(Integer id);

	public abstract String getListValue(Integer val);
}
