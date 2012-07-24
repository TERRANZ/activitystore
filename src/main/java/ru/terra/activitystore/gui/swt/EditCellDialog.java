package ru.terra.activitystore.gui.swt;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.terra.activitystore.constants.Constants;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.util.RandomUtils;

public class EditCellDialog extends AbstractEditDialog<Cell>
{
	private String cellName;
	private String name;
	private Cell ret;

	public EditCellDialog(Shell arg0)
	{
		super(arg0);
	}

	@Override
	public Cell open(Cell cell)
	{
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		if (cell != null)
		{
			name = cell.getComment();
		}
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
		label.setText("Новая ячейка");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		final Text textCellName = new Text(shell, SWT.BORDER);
		if (name != null)
			textCellName.setText(name);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		textCellName.setLayoutData(data);

		final Combo combo = new Combo(shell, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		combo.setLayoutData(data);
		Map<String, Integer> cellTypes = Constants.getConstants().getCellTypes();
		for (Integer type : cellTypes.values())
		{
			combo.add((String) RandomUtils.getMapKeyByValue(cellTypes, type));
		}

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				cellName = textCellName.getText();
				String cellType = combo.getText();
				if (cellType != null && cellType != "" && cellName != "")
				{
					ret = new Cell();
					ret.setComment(cellName);
					shell.close();
				}
				else
				{
					MessageBox mb = new MessageBox(shell);
					mb.setMessage("Не заполнены поля");
					mb.open();
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
				cellName = null;
				ret = null;
				shell.close();
			}
		});

		shell.setDefaultButton(ok);
	}

	@Override
	public Cell open()
	{
		return open(null);
	}
}
