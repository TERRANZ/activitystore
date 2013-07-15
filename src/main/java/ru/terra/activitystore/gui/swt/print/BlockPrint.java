package ru.terra.activitystore.gui.swt.print;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.util.BlockReportUtil;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class BlockPrint extends org.eclipse.swt.widgets.Dialog {

	private Shell dialogShell;
	private Menu mainMenu;
	private Browser browser;
	private Block block;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 */

	public BlockPrint(Block block, Shell parent, int style) {
		super(parent, style);
		this.block = block;
	}

	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			dialogShell.layout();
			dialogShell.pack();
			dialogShell.setSize(500, 500);
			{
				browser = new Browser(dialogShell, SWT.NONE);
				FillLayout browserLayout = new FillLayout(org.eclipse.swt.SWT.HORIZONTAL);
				browser.setLayout(browserLayout);
				FormData browserLData = new FormData();
				browserLData.left = new FormAttachment(0, 1000, 0);
				browserLData.top = new FormAttachment(0, 1000, 0);
				browserLData.width = 361;
				browserLData.height = 249;
				browserLData.right = new FormAttachment(1000, 1000, 0);
				browserLData.bottom = new FormAttachment(1000, 1000, 0);
				browser.setLayoutData(browserLData);
				browser.setText(new BlockReportUtil(parent).generateReport(block));
			}
			{
				mainMenu = new Menu(dialogShell, SWT.BAR);
				MenuItem mainItem = new MenuItem(mainMenu, SWT.CASCADE);
				mainItem.setText("&Отчёт");
				Menu submenu = new Menu(dialogShell, SWT.DROP_DOWN);
				mainItem.setMenu(submenu);

				MenuItem print = new MenuItem(submenu, SWT.PUSH);
				print.setText("&Печать");
				print.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						browser.execute("javascript:window.print();");
					}
				});

				MenuItem exit = new MenuItem(submenu, SWT.PUSH);
				exit.setText("&Выход");
				exit.addListener(SWT.Selection, new Listener() {
					@Override
					public void handleEvent(Event arg0) {
						dialogShell.close();
					}
				});

				dialogShell.setMenuBar(mainMenu);
			}
			dialogShell.setLocation(getParent().toDisplay(100, 100));

			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
