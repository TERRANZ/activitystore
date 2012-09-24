package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

import ru.terra.activitystore.controller.ActivityStoreController;

public abstract class AbstractEditDialog<T> extends Dialog
{
	protected ActivityStoreController controller = ActivityStoreController.getInstance();
	
	public AbstractEditDialog(Shell arg0)
	{
		super(arg0, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public abstract T open();
	public abstract T open(T parent);
}
