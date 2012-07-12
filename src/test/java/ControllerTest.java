import org.junit.Test;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;

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
					controller.createCard("card" + k, level2);
				}
			}
		}

	}

}
