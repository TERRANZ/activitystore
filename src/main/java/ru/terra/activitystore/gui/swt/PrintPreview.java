package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;

public class PrintPreview extends Dialog
{
	private Block block;
	private Card card;
	private int type = 0;

	public PrintPreview(Block block, Shell shell)
	{
		super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.block = block;
	}

	public PrintPreview(Card card, Shell shell)
	{
		super(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		this.card = card;
		type = 1;
	}

	public void open()
	{
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		createContents(shell);
		if (type == 0)
		{
		}
		else
		{
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
	}

	private void createContents(Shell shell)
	{
		// TODO Auto-generated method stub

	}
}
