package ru.terra.activitystore.gui.swt.select;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Template;
import ru.terra.activitystore.gui.swt.edit.EditTemplateDialog;

public class TemplatesDialog extends Dialog
{

	private ActivityStoreController controller = ActivityStoreController.getInstance();
	private Table templatesTable;
	private Shell shell;

	public TemplatesDialog(Shell arg0)
	{
		super(arg0, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public void open()
	{
		shell = new Shell(getParent(), getStyle());
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
	}

	private void createContents(final Shell shell)
	{
		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		shell.setLayout(gl);
		templatesTable = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		String[] titles = { "Номер", "Шаблон", "Карточка" };
		for (int i = 0; i < titles.length; i++)
		{
			TableColumn column = new TableColumn(templatesTable, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < titles.length; i++)
		{
			templatesTable.getColumn(i).pack();
		}
		templatesTable.setLinesVisible(true);
		templatesTable.setHeaderVisible(true);

		loadTemplates();

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.verticalSpan = 3;
		templatesTable.setLayoutData(gridData);

		Button btnAdd = new Button(shell, SWT.PUSH);
		btnAdd.setText("Новый");
		btnAdd.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				EditTemplateDialog dlg = new EditTemplateDialog(shell);
				Template ret = dlg.open();
				if (ret != null)
				{
					controller.updateTemplate(ret);
					loadTemplates();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}
		});
		Button btnEdit = new Button(shell, SWT.PUSH);
		btnEdit.setText("Изменить");
		btnEdit.addListener(SWT.PUSH, new EditTemplateListener());
		Button btnDel = new Button(shell, SWT.PUSH);
		btnDel.setText("Удалить");
		btnDel.addListener(SWT.PUSH, new DeleteTemplateListener());
	}

	private void loadTemplates()
	{
		templatesTable.removeAll();
		templatesTable.clearAll();
		for (Template tpl : controller.getAllTemplates())
		{
			TableItem ti = new TableItem(templatesTable, SWT.NONE);
			ti.setText(new String[] { tpl.getId().toString(), tpl.getName(), tpl.getCard().getName() });
			ti.setData(tpl);
		}
	}

	private class EditTemplateListener implements Listener
	{

		@Override
		public void handleEvent(Event arg0)
		{
		}
	}

	private class DeleteTemplateListener implements Listener
	{

		@Override
		public void handleEvent(Event arg0)
		{
		}
	}
}
