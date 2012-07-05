package ru.terra.activitystore.mvc.view;

import ru.terra.activitystore.mvc.controller.ActivityStoreController;

public abstract class ActivityStoreView
{
	public static ActivityStoreView getDefaultView(ActivityStoreController controller)
	{
		return null;
	}
}
