/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
