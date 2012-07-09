package ru.terra.activitystore.gui.swt;

import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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
		shell.setLayout(new FillLayout());
		fillMenu();
		createTree();
		shell.open();
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
	}

	@Override
	public void fillBlocksTree(List<Block> blocks)
	{
		for (Block b : blocks)
		{
			TreeItem newItem = new TreeItem(tree, 0);
			newItem.setText(b.getName());
			newItem.setData(b);
			// if (b.getBlocks() != null)
			// {
			// fillTree(newItem, b.getBlocks());
			// }
		}
	}

	private void fillTree(TreeItem parent, Set<Block> blocks)
	{
		for (Block b : blocks)
		{
			TreeItem newItem = new TreeItem(parent, 0);
			newItem.setText(b.getName());
			newItem.setData(b);
			// if (b.getBlocks() != null)
			// {
			// fillTree(newItem, b.getBlocks());
			// }
		}
	}
}
