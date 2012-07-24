package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.widgets.Shell;

import ru.terra.activitystore.db.entity.Template;

public class EditTemplateDialog extends AbstractEditDialog<Template>
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

	@Override
	public Template open(Template parent)
	{
		return null;
	}
}
