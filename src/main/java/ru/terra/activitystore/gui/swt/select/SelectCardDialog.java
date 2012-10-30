package ru.terra.activitystore.gui.swt.select;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.gui.swt.edit.EditCardDialog;

public class SelectCardDialog extends AbstractSelectDialog<Card>
{

	public SelectCardDialog(Shell arg0)
	{
		super(arg0, "Новая", "Выберите карточку", new EditCardDialog(arg0));
	}

	@Override
	protected void loadCells()
	{
		getTable().clearAll();
		for (Card c : ActivityStoreController.getInstance().getAllCards())
		{
			TableItem ti = new TableItem(getTable(), SWT.NONE);
			ti.setText(new String[] { c.getName(), c.getCreationDate().toString() });
			ti.setData(c);
		}
	}

}
