package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractEditDialog<T> extends Dialog
{
	public AbstractEditDialog(Shell arg0)
	{
		super(arg0);
	}

	public abstract T open();
}
