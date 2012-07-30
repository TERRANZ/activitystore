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
	public static String generateReport(Block block)
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
			List<Card> cards = ActivityStoreController.getInstance().getCards(block);
			Map<Integer, String> values = new HashMap<Integer, String>();
			Map<Integer, Cell> cells = new HashMap<Integer, Cell>();
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
							value += Long.parseLong(cell.getVal());
							values.put(cell.getId(), value.toString());
						}
							break;
						case 1:// float
						{

							Double value = Double.parseDouble(values.get(cell.getId()));
							values.remove(cell.getId());
							value += Long.parseLong(cell.getVal());
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
							values.put(cell.getId(), cell.getVal());
						}
							break;
						case 1:// float
						{
							values.put(cell.getId(), cell.getVal());
						}
							break;
						}
					}
				}
			}
			html.append("<table BORDER>");
			html.append("<tr>");
			html.append("<th>");
			html.append("Ячейка");
			html.append("</th>");
			html.append("<th>");
			html.append("Значение");
			html.append("</th>");
			html.append("</tr>");
			for (Integer cellId : values.keySet())
			{
				Cell cell = cells.get(cellId);
				html.append("<tr>");
				html.append("<td>");
				html.append(cell.getComment());
				html.append("</td>");
				html.append("<td>");
				html.append(cell.getVal());
				html.append("</td>");
				html.append("</tr>");

			}
			html.append("</table>");

			html.append("</body>");
			html.append("</html>");
		}
		return html.toString();
	}
}
