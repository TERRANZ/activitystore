package ru.terra.activitystore;

import ru.terra.activitystore.controller.ActivityStoreController;

/**
 * 
 * @author terranz
 */
public class Main
{
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		ActivityStoreController controller = new ActivityStoreController();
		controller.start();
	}
}
