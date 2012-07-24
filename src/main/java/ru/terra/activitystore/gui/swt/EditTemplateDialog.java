package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

import ru.terra.activitystore.db.entity.Template;

public class EditTemplateDialog extends Dialog
{
	private Template ret;

	public EditTemplateDialog(Shell arg0)
	{
		super(arg0);
	}

	public Template open()
	{
		return ret;
	}
}
