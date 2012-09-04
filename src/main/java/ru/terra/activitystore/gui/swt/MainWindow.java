package ru.terra.activitystore.gui.swt;

import java.util.HashMap;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import ru.terra.activitystore.constants.Constants;
import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.gui.swt.print.BlockPrint;
import ru.terra.activitystore.gui.swt.print.CardPrint;
import ru.terra.activitystore.util.RandomUtils;
import ru.terra.activitystore.view.ActivityStoreView;

public class MainWindow extends ActivityStoreView
{
	private Shell shell;
	private Tree tree;
	private Table CardViewer;
	private Image blockImage, cardImage;
	private Menu blockMenu, cardMenu;
	private Display display;

	private class ViewHolder
	{
		public static final int BLOCK = 0;
		public static final int CARD = 1;

		public int type;
		public Block block;
		public Card card;

		public ViewHolder(Block block, Card card, int type)
		{
			this.block = block;
			this.card = card;
			this.type = type;
		}
	}

	public MainWindow(ActivityStoreController controller)
	{
		super(controller);
	}

	private void prepareImages()
	{
		blockImage = new Image(display, 16, 16);
		GC gc = new GC(blockImage);
		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		gc.drawLine(1, 1, 1, 14);
		gc.drawLine(1, 1, 14, 1);
		gc.drawLine(1, 14, 14, 14);
		gc.drawLine(14, 14, 14, 1);
		gc.dispose();
		cardImage = new Image(display, 16, 16);
		gc = new GC(cardImage);
		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		gc.drawLine(1, 1, 1, 14);
		gc.drawLine(1, 1, 7, 1);
		gc.drawLine(14, 5, 14, 14);
		gc.drawLine(7, 5, 14, 5);
		gc.drawLine(7, 1, 7, 5);
		gc.drawLine(1, 14, 14, 14);
		gc.dispose();
	}

	@Override
	public void start()
	{
		display = new Display();
		prepareImages();
		shell = new Shell(display);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		shell.setText("Управление активностями");
		fillMenu();
		createTree();
		CardViewer = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
		String[] titles = { "Ячейка", "Значение", "Тип" };
		for (int i = 0; i < titles.length; i++)
		{
			TableColumn column = new TableColumn(CardViewer, SWT.NONE);
			column.setText(titles[i]);
		}
		for (int i = 0; i < titles.length; i++)
		{
			CardViewer.getColumn(i).pack();
		}
		CardViewer.setLinesVisible(true);
		CardViewer.setHeaderVisible(true);
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
		fileItem.setText("&Управление");
		Menu submenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(submenu);
		MenuItem item = new MenuItem(submenu, SWT.PUSH);
		item.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event e)
			{
				System.exit(0);
			}
		});
		item.setText("Выход");
		createBlockMenu();
		createCardMenu();
	}

	private void createBlockMenu()
	{
		blockMenu = new Menu(shell, SWT.POP_UP);
		MenuItem createBlockMenuItem = new MenuItem(blockMenu, SWT.PUSH);
		createBlockMenuItem.setText("Новый блок");
		MenuItem createCardMenuItem = new MenuItem(blockMenu, SWT.PUSH);
		createCardMenuItem.setText("Новая карточка");
		MenuItem deleteMenuItem = new MenuItem(blockMenu, SWT.PUSH);
		deleteMenuItem.setText("Удалить");
		MenuItem editBlockName = new MenuItem(blockMenu, SWT.PUSH);
		editBlockName.setText("Редактировать");
		MenuItem printBlock = new MenuItem(blockMenu, SWT.PUSH);
		printBlock.setText("Отчёт по блоку");
		createBlockMenuItem.addSelectionListener(new SelectionListener()
		{
			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				BlockInputDialog dlg = new BlockInputDialog(shell);
				String name = dlg.open(null);
				if (name != null)
				{
					TreeItem parent = tree.getSelection()[0];
					if (parent != null)
					{
						ViewHolder vh = (ViewHolder) parent.getData();
						if (vh.type == ViewHolder.BLOCK)
						{
							Block newBlock = new Block();
							newBlock.setName(name);
							ViewHolder newVH = new ViewHolder(newBlock, null, ViewHolder.BLOCK);
							TreeItem newItem = new TreeItem(parent, 0);
							newItem.setText(name);
							newItem.setData(newVH);
							newItem.setImage(blockImage);
							controller.addBlockToBlock(newBlock, vh.block);
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});

		createCardMenuItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				EditCardDialog dlg = new EditCardDialog(shell);
				Card card = dlg.open(null);
				if (card != null)
				{
					TreeItem parent = tree.getSelection()[0];
					if (parent != null)
					{
						ViewHolder vh = (ViewHolder) parent.getData();
						if (vh.type == ViewHolder.BLOCK)
						{
							ViewHolder newVH = new ViewHolder(null, card, ViewHolder.CARD);
							TreeItem newItem = new TreeItem(parent, 0);
							newItem.setText(card.getName());
							newItem.setData(newVH);
							newItem.setImage(cardImage);
							controller.addCardToBlock(card, vh.block);
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});

		deleteMenuItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});

		editBlockName.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (tree.getSelection() != null && tree.getSelection().length == 1)
				{
					TreeItem selectedItem = tree.getSelection()[0];
					if (selectedItem != null)
					{
						ViewHolder vh = (ViewHolder) selectedItem.getData();
						if (vh.type == ViewHolder.BLOCK)
						{
							BlockInputDialog dlg = new BlockInputDialog(shell);
							Block selectedBlock = vh.block;
							String name = dlg.open(selectedBlock.getName());
							if (name != null)
							{
								selectedBlock.setName(name);
								controller.updateBlock(selectedBlock);
								vh.block = selectedBlock;
								selectedItem.setData(vh);
								selectedItem.setText(name);
							}
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{

			}
		});

		printBlock.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				if (tree.getSelection() != null && tree.getSelection().length == 1)
				{
					TreeItem selectedBlock = tree.getSelection()[0];
					if (selectedBlock != null)
					{
						ViewHolder vh = (ViewHolder) selectedBlock.getData();
						if (vh.type == ViewHolder.BLOCK)
						{
							Block block = vh.block;
							new BlockPrint(block, shell, 0).open();
						}
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}

		});
	}

	private void createCardMenu()
	{
		cardMenu = new Menu(shell, SWT.POP_UP);
		MenuItem editCardMenuItem = new MenuItem(cardMenu, SWT.PUSH);
		editCardMenuItem.setText("Редактировать");
		editCardMenuItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				TreeItem ti = tree.getSelection()[0];
				if (ti != null && ((ViewHolder) ti.getData()).type == ViewHolder.CARD)
				{
					Card ret = new EditCardDialog(shell).open(((ViewHolder) ti.getData()).card);
					if (ret != null)
						controller.updateCard(ret);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});

		MenuItem deleteCardMenuItem = new MenuItem(cardMenu, SWT.PUSH);
		deleteCardMenuItem.setText("Удалить");
		deleteCardMenuItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});

		MenuItem printCardMenuItem = new MenuItem(cardMenu, SWT.PUSH);
		printCardMenuItem.setText("Печать");
		printCardMenuItem.addSelectionListener(new SelectionListener()
		{

			@Override
			public void widgetSelected(SelectionEvent arg0)
			{
				TreeItem ti = tree.getSelection()[0];
				if (ti != null && ((ViewHolder) ti.getData()).type == ViewHolder.CARD)
				{
					new CardPrint(((ViewHolder) ti.getData()).card, shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL).open();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{
			}
		});
	}

	private void createTree()
	{
		tree = new Tree(shell, SWT.BORDER);
		tree.addListener(SWT.Selection, new Listener()
		{

			@Override
			public void handleEvent(Event arg0)
			{
				if (tree.getSelection() != null && tree.getSelection().length > 0)
				{
					TreeItem item = tree.getSelection()[0];
					if (item != null)
					{
						if (((ViewHolder) item.getData()).type == ViewHolder.BLOCK)
						{
							tree.setMenu(blockMenu);
							CardViewer.clearAll();
							CardViewer.removeAll();
						}
						else
						{
							tree.setMenu(cardMenu);
							loadCard(((ViewHolder) item.getData()).card);
						}
					}
					else
					{
						tree.setMenu(null);
					}
				}
			}
		});
	}

	private void loadCard(Card card)
	{
		CardViewer.clearAll();
		CardViewer.removeAll();
		for (Cell c : controller.getCells(card))
		{
			TableItem ti = new TableItem(CardViewer, SWT.NONE);
			ti.setText(new String[] { c.getComment(), controller.getCardCellVal(card.getId(), c.getId()), (String) RandomUtils.getMapKeyByValue(Constants.getConstants().getCellTypes(), c.getType()) });
			ti.setData(c);
		}

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
			newItem.setData(new ViewHolder(b, null, ViewHolder.BLOCK));
			newItem.setImage(blockImage);
			List<Card> cards = controller.getCards(b);
			if (cards != null)
			{
				for (Card c : cards)
				{
					TreeItem cardItem = new TreeItem(newItem, 0);
					cardItem.setData(new ViewHolder(null, c, ViewHolder.CARD));
					cardItem.setText(c.getName());
					cardItem.setImage(cardImage);
				}
			}
			blockMap.put(b.getId(), newItem);
		}
	}
}
