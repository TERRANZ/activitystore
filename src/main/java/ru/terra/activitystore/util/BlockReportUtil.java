package ru.terra.activitystore.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;

public class BlockReportUtil
{
	Map<Integer, String> values = new HashMap<Integer, String>();
	Map<Integer, Cell> cells = new HashMap<Integer, Cell>();
	ActivityStoreController contoller = ActivityStoreController.getInstance();

	public String generateReport(Block block)
	{
		StringBuilder html = new StringBuilder();
		if (block != null)
		{
			html.append("<html>");
			html.append("<head>");
			html.append("<title>");
			html.append("Отчёт по блоку ");
			html.append(block.getName());
			html.append("</title>");
			html.append("</head>");
			html.append("<body>");
			html.append("<h1>");
			html.append("Отчёт по блоку: ");
			html.append(block.getName());
			html.append("</h1>");

			genBlockRepRecursive(block);

			html.append("<table BORDER>");
			html.append("<tr>");
			html.append("<th>");
			html.append("Ячейка");
			html.append("</th>");
			html.append("<th>");
			html.append("Значение");
			html.append("</th>");
			html.append("</tr>");
			for (Integer c : values.keySet())
			{
				Cell cell = cells.get(c);
				html.append("<tr>");
				html.append("<td>");
				html.append(cell.getComment());
				html.append("</td>");
				html.append("<td>");
				html.append(values.get(c));
				html.append("</td>");
				html.append("</tr>");

			}
			html.append("</table>");

			html.append("</body>");
			html.append("</html>");
		}
		return html.toString();
	}

	private void genBlockRepRecursive(Block block)
	{
		List<Block> childrens = contoller.getBlocks(block);
		genBlockRep(block);
		if (childrens != null && childrens.size() > 0)
		{
			for (Block b : childrens)
			{
				if (b.getId() != 0)
					genBlockRepRecursive(b);
			}
		}
	}

	private void genBlockRep(Block block)
	{
		List<Card> cards = ActivityStoreController.getInstance().getCards(block);
		for (Card card : cards)
		{
			for (Cell cell : card.getCellList())
			{
				cells.put(cell.getId(), cell);
				if (values.get(cell.getId()) != null)
				{
					switch (cell.getType())
					{
					case 0:// int
					{
						Long value = Long.parseLong(values.get(cell.getId()));
						values.remove(cell.getId());
						value += Long.parseLong(contoller.getCardCellVal(card.getId(), cell.getId()));
						values.put(cell.getId(), value.toString());
					}
						break;
					case 1:// float
					{

						Double value = Double.parseDouble(values.get(cell.getId()));
						values.remove(cell.getId());
						value += Float.parseFloat(contoller.getCardCellVal(card.getId(), cell.getId()));
						values.put(cell.getId(), value.toString());
					}
						break;
					}

				}
				else
				{
					switch (cell.getType())
					{
					case 0:// int
					{
						values.put(cell.getId(), contoller.getCardCellVal(card.getId(), cell.getId()));
					}
						break;
					case 1:// float
					{
						values.put(cell.getId(), contoller.getCardCellVal(card.getId(), cell.getId()));
					}
						break;
					}
				}
			}
		}
	}
}
