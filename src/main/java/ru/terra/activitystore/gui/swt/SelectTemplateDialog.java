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

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Template;

public class SelectTemplateDialog extends Dialog
{

	private Template ret;
	private Table templatesTable;

	public SelectTemplateDialog(Shell arg0)
	{
		super(arg0, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public Template open()
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
		label.setText("Выберите Шаблон");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(data);

		Button newCellBtn = new Button(shell, SWT.PUSH);
		newCellBtn.setText("Новый");
		newCellBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		newCellBtn.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				ret = new EditTemplateDialog(shell).open();
				shell.close();
			}
		});

		templatesTable = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		templatesTable.setLinesVisible(true);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		templatesTable.setLayoutData(data);
		loadCells();
		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				if (templatesTable.getSelection() != null && templatesTable.getSelection().length == 1)
				{
					TableItem ti = templatesTable.getSelection()[0];
					if (ti != null)
					{
						ret = (Template) ti.getData();
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
		templatesTable.clearAll();
		for (Template t : ActivityStoreController.getInstance().getAllTemplates())
		{
			TableItem ti = new TableItem(templatesTable, SWT.NONE);
			ti.setText(new String[] { t.getName(), t.getCreationDate().toString() });
			ti.setData(t);
		}
	}

}
