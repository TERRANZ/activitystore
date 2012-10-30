package ru.terra.activitystore.gui.swt.edit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import ru.terra.activitystore.db.entity.ListVal;
import ru.terra.activitystore.db.entity.Vlist;
import ru.terra.activitystore.util.Pair;

public class ListSelectDialog extends AbstractEditDialog<Pair<Integer, List<Integer>>>
{
	private Pair<Integer, List<Integer>> ret = new Pair<Integer, List<Integer>>(null, null);
	private List<Vlist> vlist;
	private Table listTable;
	protected int vlistId;

	public ListSelectDialog(Shell arg0)
	{
		super(arg0);
	}

	@Override
	public Pair<Integer, List<Integer>> open()
	{
		start();
		return ret;
	}

	@Override
	public Pair<Integer, List<Integer>> open(Pair<Integer, List<Integer>> parent)
	{
		start();
		load(parent);
		return ret;
	}

	private void start()
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
	}

	private void load(Pair<Integer, List<Integer>> id)
	{
		loadListToTable(id.x);
		setChecked(id.y);
	}

	private void setChecked(List<Integer> ids)
	{
		for (TableItem ti : listTable.getItems())
		{
			if (ids.get((Integer) ti.getData()) != null)
			{
				ti.setChecked(true);
			}
		}
	}

	private void createContents(final Shell shell)
	{
		GridLayout gl = new GridLayout(1, false);

		shell.setLayout(gl);

		Label label = new Label(shell, SWT.NONE);
		label.setText("Выберите список");

		final Combo combo = new Combo(shell, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
		vlist = controller.getAllLists();
		for (int i = 0; i < vlist.size(); i++)
		{
			combo.add(vlist.get(i).getName(), i);
		}
		combo.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent e)
			{
				vlistId = combo.getSelectionIndex();
				loadListToTable(vlistId);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e)
			{
			}
		});
		combo.select(0);
		Label label2 = new Label(shell, SWT.NONE);
		label2.setText("Выберите элементы списка");

		listTable = new Table(shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);

		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		listTable.setLinesVisible(true);
		listTable.setLayoutData(gridData);
		loadListToTable(0);
		Button btnOk = new Button(shell, SWT.PUSH);
		btnOk.setText("OK");
		btnOk.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				List<Integer> retList = new ArrayList<Integer>();
				if (listTable.getSelection() != null)
					for (TableItem ti : listTable.getItems())
					{
						if (ti.getChecked())
							retList.add((Integer) ti.getData());
					}
				ret.x = vlistId;
				ret.y = retList;
				shell.close();
			}
		});
		shell.setDefaultButton(btnOk);
	}

	private void loadListToTable(Integer id)
	{
		Vlist list = vlist.get(id);
		if (list != null)
		{
			listTable.clearAll();
			listTable.removeAll();
			for (ListVal lv : list.getListValList())
			{
				TableItem ti = new TableItem(listTable, SWT.NONE);
				ti.setText(new String[] { lv.getValue() });
				ti.setData(lv.getValId());
			}
		}
	}
}
