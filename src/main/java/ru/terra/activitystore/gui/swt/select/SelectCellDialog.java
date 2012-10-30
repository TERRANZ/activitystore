package ru.terra.activitystore.gui.swt.select;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;

import ru.terra.activitystore.constants.Constants;
import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.gui.swt.edit.EditCellDialog;
import ru.terra.activitystore.util.RandomUtils;

public class SelectCellDialog extends AbstractSelectDialog<Cell>
{
	public SelectCellDialog(Shell arg0)
	{
		super(arg0, "Новая", "Выберите ячейку", new EditCellDialog(arg0));
	}

	@Override
	protected void loadCells()
	{
		getTable().clearAll();
		for (Cell c : ActivityStoreController.getInstance().getAllCells())
		{
			TableItem ti = new TableItem(getTable(), SWT.NONE);
			ti.setText(new String[] { c.getComment(), (String) RandomUtils.getMapKeyByValue(Constants.getConstants().getCellTypes(), c.getType()) });
			ti.setData(c);
		}
	}
}
