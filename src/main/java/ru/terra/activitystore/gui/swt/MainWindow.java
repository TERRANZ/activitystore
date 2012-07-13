package ru.terra.activitystore.gui.swt;

import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.view.ActivityStoreView;

public class MainWindow extends ActivityStoreView
{
	private Shell shell;
	private Tree tree;

	public MainWindow(ActivityStoreController controller)
	{
		super(controller);
	}

	@Override
	public void start()
	{
		Display display = new Display();
		shell = new Shell(display);
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		fillMenu();
		createTree();
		shell.open();
		controller.onViewStarted();
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private void fillMenu()
	{
		Menu bar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(bar);
		MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
		fileItem.setText("&File");
		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);
		MenuItem item = new MenuItem(submenu, SWT.PUSH);
		item.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event e)
			{
				System.out.println("Select All");
			}
		});
		item.setText("Select &All\tCtrl+A");
		item.setAccelerator(SWT.MOD1 + 'A');
	}

	private void createTree()
	{
		tree = new Tree(shell, SWT.BORDER);
		final Menu rootMenu = new Menu(shell, SWT.POP_UP);
		MenuItem addChild = new MenuItem(rootMenu, SWT.PUSH);
		addChild.setText("Add first Item");
		final Menu childMenu = new Menu(shell, SWT.POP_UP);
		MenuItem childMenuItem = new MenuItem(childMenu, SWT.PUSH);
		childMenuItem.setText("delete Item");
		tree.addListener(SWT.Selection, new Listener()
		{

			@Override
			public void handleEvent(Event arg0)
			{
				// if (tree.getTopItem().getItemCount() == 0)
				// {
				tree.setMenu(rootMenu);
				// }
				// else if (tree.getTopItem().getItemCount() == 1)
				// {
				// TreeItem[] itemSelected = tree.getSelection();
				// if (itemSelected[0].getText() == "Root")
				// {
				// tree.setMenu(null);
				// }
				// else
				// tree.setMenu(childMenu);
				// }
				// else
				// tree.setMenu(null);

			}
		});
		addChild.addSelectionListener(new SelectionListener()
		{

			public void widgetSelected(SelectionEvent arg0)
			{
				TreeItem item = new TreeItem(tree, SWT.PUSH);
				item.setText("Child");

			}

			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}

		});

		childMenuItem.addSelectionListener(new SelectionListener()
		{

			public void widgetSelected(SelectionEvent arg0)
			{
				TreeItem[] item = tree.getSelection();
				item[0].dispose();
			}

			public void widgetDefaultSelected(SelectionEvent arg0)
			{
				// TODO Auto-generated method stub

			}

		});

	}

	@Override
	public void fillBlocksTree(List<Block> blocks)
	{
		HashMap<Integer, TreeItem> blockMap = new HashMap<Integer, TreeItem>();
		for (Block b : blocks)
		{
			TreeItem newItem;

			TreeItem parentItem = blockMap.get(b.getParent());
			if (parentItem != null)
			{
				newItem = new TreeItem(parentItem, 0);
			}
			else
			{
				newItem = new TreeItem(tree, 0);
			}

			newItem.setText(b.getName());
			newItem.setData(b);
			blockMap.put(b.getId(), newItem);
		}
	}
}
