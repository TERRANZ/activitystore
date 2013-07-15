package ru.terra.activitystore;

import ru.terra.activitystore.controller.ActivityStoreController;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * 
 * @author terranz
 */
public class Main {
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Logger.getLogger(Main.class).info("Starting application...");
		ActivityStoreController controller = ActivityStoreController.getInstance();
		controller.start();
	}
}
