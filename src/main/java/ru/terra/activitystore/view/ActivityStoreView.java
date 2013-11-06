package ru.terra.activitystore.view;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.gui.swt.MainWindow;

import java.util.List;

public abstract class ActivityStoreView {

    protected ActivityStoreController controller;

    public static ActivityStoreView getDefaultView(ActivityStoreController controller) {
        return new MainWindow(controller);
    }

    public ActivityStoreView(ActivityStoreController controller) {
        this.controller = controller;
    }

    public abstract void start();

    public abstract void fillBlocksTree(List<Block> blocks);
}
