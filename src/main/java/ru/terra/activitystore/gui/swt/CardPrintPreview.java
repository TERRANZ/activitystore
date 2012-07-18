package ru.terra.activitystore.gui.swt;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

import ru.terra.activitystore.db.entity.Card;

public class CardPrintPreview extends Dialog
{
	Card card;

	public CardPrintPreview(Card card, Shell shell)
	{
		super(shell);
		this.card = card;
	}

	public void open()
	{
	}
}
