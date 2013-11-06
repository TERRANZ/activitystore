package ru.terra.activitystore;

import org.apache.log4j.BasicConfigurator;
import ru.terra.activitystore.controller.ActivityStoreController;

/**
 * @author terranz
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BasicConfigurator.configure();
        ActivityStoreController.getInstance().start();
    }
}
