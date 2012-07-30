import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import ru.terra.activitystore.constants.Constants;
import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;

public class ControllerTest
{

	@Test
	public void CreateAndDeleteTest()
	{
		ActivityStoreController controller = ActivityStoreController.getInstance();
		List<Cell> cells = new ArrayList<Cell>();
		for (int c = 0; c <= 10; c++)
		{
			Random r = new Random();
			Cell cell = controller.createCell("cell " + String.valueOf(c));
			Integer type = r.nextInt(5);
			cell.setType(type);
			switch (type)
			{
			case 0:
			{
				cell.setVal(String.valueOf(r.nextInt()));
			}
				break;
			case 1:
			{
				cell.setVal(String.valueOf(r.nextFloat()));
			}
				break;
			case 2:
			{
				cell.setVal(String.valueOf(r.nextInt()));
			}
				break;
			case 3:
			{
				cell.setVal(String.valueOf(r.nextInt()));
			}
				break;
			case 4:
			{
			}
				break;
			case 5:
			{
				cell.setVal(new Date().toString());
			}
				break;
			}
			cells.add(cell);
			controller.updateCell(cell);
		}
		Block root = controller.getBlock(0);
		for (int i = 0; i <= 10; i++)
		{
			Block level1 = controller.createBlock("level" + i, root);
			for (int j = 0; j <= 10; j++)
			{
				Block level2 = controller.createBlock("level" + j, level1);
				for (int k = 0; k <= 10; k++)
				{
					Card card = controller.createCard("card" + k, level2);
					for (int c = 0; c <= 10; c++)
					{
						controller.addCellToCard(cells.get(c), card);
					}
				}
			}
		}

	}

}
