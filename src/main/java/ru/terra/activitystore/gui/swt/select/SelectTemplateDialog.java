package ru.terra.activitystore.gui.swt.select;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.gui.swt.edit.EditTemplateDialog;

public class SelectTemplateDialog extends AbstractSelectDialog<Template> {

	public SelectTemplateDialog(Shell arg0) {
		super(arg0, "Новый", "Выберите Шаблон", new EditTemplateDialog(arg0));
	}

	@Override
	protected void loadCells() {
		getTable().clearAll();
		for (Template t : ActivityStoreController.getInstance().getAllTemplates()) {
			TableItem ti = new TableItem(getTable(), SWT.NONE);
			ti.setText(new String[] { t.getName(), t.getCreationDate().toString() });
			ti.setData(t);
		}

	}

}
