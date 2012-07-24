package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.db.entity.Template;

public class EditTemplateDialog extends AbstractEditDialog<Template>
{
	private Template ret;
	private String templateName;
	private Text templateNameInput;

	public EditTemplateDialog(Shell arg0)
	{
		super(arg0);
	}

	public Template open()
	{
		return open(null);
	}

	@Override
	public Template open(Template parent)
	{
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		this.ret = parent;
		if (parent != null)
		{
			templateName = parent.getName();
		}
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

	private void createContents(Shell shell)
	{
		// shell.setLayout(new GridLayout(2, true));
		// Label label = new Label(shell, SWT.NONE);
		// label.setText("Новая карточка");
		// GridData data = new GridData();
		// data.horizontalSpan = 2;
		// label.setLayoutData(data);
		//
		// templateNameInput = new Text(shell, SWT.BORDER);
		// if (templateName != null)
		// templateNameInput.setText(templateName);
		// data = new GridData(GridData.FILL_HORIZONTAL);
		// data.horizontalSpan = 2;
		// templateNameInput.setLayoutData(data);
		//
		// cellsTable = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		// cellsTable.setLinesVisible(true);
		// data = new GridData(GridData.FILL_HORIZONTAL);
		// data.horizontalSpan = 2;
		// cellsTable.setLayoutData(data);
		//
		// if (card != null)
		// {
		// loadCard();
		// }
		// else
		// {
		// card = new Card();
		// }
		//
		// Menu cellsMenu = new Menu(cellsTable);
		// MenuItem miDelete = new MenuItem(cellsMenu, SWT.POP_UP);
		// miDelete.setText("Удалить");
		// miDelete.addSelectionListener(new SelectionListener()
		// {
		//
		// @Override
		// public void widgetSelected(SelectionEvent arg0)
		// {
		// if (cellsTable.getSelection() != null && cellsTable.getSelection().length == 1)
		// {
		// TableItem selected = cellsTable.getSelection()[0];
		// if (selected != null)
		// {
		// Cell c = (Cell) selected.getData();
		// card.getCells().remove(c);
		// cellsTable.remove(cellsTable.getSelectionIndex());
		// cellsTable.setRedraw(true);
		// }
		// }
		// }
		//
		// @Override
		// public void widgetDefaultSelected(SelectionEvent arg0)
		// {
		// }
		// });
		//
		// MenuItem miAdd = new MenuItem(cellsMenu, SWT.POP_UP);
		// miAdd.setText("Добавить");
		// miAdd.addSelectionListener(new SelectionListener()
		// {
		//
		// @Override
		// public void widgetSelected(SelectionEvent arg0)
		// {
		// Cell newCell = new SelectCellDialog(shell).open();
		// if (newCell != null)
		// {
		// card.getCells().add(newCell);
		// TableItem newItem = new TableItem(cellsTable, SWT.NONE);
		// newCell.setCard(card);
		// newItem.setText(0, newCell.getComment());
		// newItem.setData(newCell);
		// }
		// }
		//
		// @Override
		// public void widgetDefaultSelected(SelectionEvent arg0)
		// {
		// }
		// });
		//
		// cellsTable.setMenu(cellsMenu);
		//
		// Button ok = new Button(shell, SWT.PUSH);
		// ok.setText("OK");
		// data = new GridData(GridData.FILL_HORIZONTAL);
		// ok.setLayoutData(data);
		// ok.addSelectionListener(new SelectionAdapter()
		// {
		// public void widgetSelected(SelectionEvent event)
		// {
		// cardName = cardNameInput.getText();
		// card.setName(cardName);
		// ret = card;
		// shell.close();
		// }
		// });
		//
		// Button cancel = new Button(shell, SWT.PUSH);
		// cancel.setText("Отмена");
		// data = new GridData(GridData.FILL_HORIZONTAL);
		// cancel.setLayoutData(data);
		// cancel.addSelectionListener(new SelectionAdapter()
		// {
		// public void widgetSelected(SelectionEvent event)
		// {
		// cardName = null;
		// card = null;
		// ret = card;
		// shell.close();
		// }
		// });
		//
		// shell.setDefaultButton(ok);
	}
}
