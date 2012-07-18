import org.junit.Test;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;

public class ControllerTest
{

	@Test
	public void CreateAndDeleteTest()
	{
		ActivityStoreController controller = new ActivityStoreController();
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
						Cell cell = new Cell();
						cell.setComment("cell " + String.valueOf(c));
						cell.setCard(card);
						controller.addCellToCard(cell, card);
					}
				}
			}
		}

	}

}
