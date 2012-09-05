package ru.terra.activitystore.util;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;

public class CardReportUtil
{
	public static String generateReport(Card card)
	{
		StringBuilder html = new StringBuilder();
		ActivityStoreController controller = ActivityStoreController.getInstance();
		if (card != null)
		{
			html.append("<html>");
			html.append("<head>");
			html.append("<title>");
			html.append("Отчёт по карточке ");
			html.append(card.getName());
			html.append("</title>");
			html.append("</head>");
			html.append("<body>");
			html.append("<h1>");
			html.append("Отчёт по карточке: ");
			html.append(card.getName());
			html.append("</h1>");
			html.append("<table BORDER>");
			html.append("<tr>");
			html.append("<th>");
			html.append("Ячейка");
			html.append("</th>");
			html.append("<th>");
			html.append("Значение");
			html.append("</th>");
			html.append("</tr>");
			for (Cell cell : card.getCellList())
			{
				html.append("<tr>");
				html.append("<td>");
				html.append(cell.getComment());
				html.append("</td>");
				html.append("<td>");
				html.append(controller.getCardCellVal(card.getId(), cell.getId()));
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
