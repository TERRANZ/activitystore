package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import ru.terra.activitystore.constants.Constants;
import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.util.RandomUtils;

public class SelectCellDialog extends Dialog
{

	private Cell ret;
	private Table cellsTable;

	public SelectCellDialog(Shell parent)
	{
		super(parent);
	}

	public Cell open()
	{

		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		return ret;
	}

	private void createContents(final Shell shell)
	{
		shell.setLayout(new GridLayout(2, true));
		Label label = new Label(shell, SWT.NONE);
		label.setText("Выберите ячейку");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(data);

		Button newCellBtn = new Button(shell, SWT.PUSH);
		newCellBtn.setText("Новая");
		newCellBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		newCellBtn.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				ret = new EditCellDialog(shell).open(null);
				shell.close();
			}
		});

		cellsTable = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		cellsTable.setLinesVisible(true);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		cellsTable.setLayoutData(data);
		loadCells();
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				if (cellsTable.getSelection() != null && cellsTable.getSelection().length == 1)
				{
					TableItem ti = cellsTable.getSelection()[0];
					if (ti != null)
					{
						ret = (Cell) ti.getData();
						shell.close();
					}
				}
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Отмена");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				ret = null;
				shell.close();
			}
		});

		shell.setDefaultButton(ok);
	}

	private void loadCells()
	{
		cellsTable.clearAll();
		for (Cell c : ActivityStoreController.getInstance().getAllCells())
		{
			TableItem ti = new TableItem(cellsTable, SWT.NONE);
			ti.setText(new String[] {
					c.getComment(),
					(String) RandomUtils.getMapKeyByValue(Constants.getConstants().getCellTypes(),
							c.getType()) });
			ti.setData(c);
		}
	}
}
