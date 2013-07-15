package ru.terra.activitystore.gui.swt.edit;

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
import org.eclipse.swt.widgets.Text;

public class BlockInputDialog extends Dialog {
	private String blockName;
	private String input;

	public BlockInputDialog(Shell arg0) {
		super(arg0, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}

	public String open(String blockName) {
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		this.blockName = blockName;
		createContents(shell);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return input;
	}

	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, true));

		Label label = new Label(shell, SWT.NONE);
		label.setText("Введите название для новой карточки");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		final Text text = new Text(shell, SWT.BORDER);
		if (blockName != null)
			text.setText(blockName);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		text.setLayoutData(data);

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				input = text.getText();
				shell.close();
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Отмена");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				input = null;
				shell.close();
			}
		});

		shell.setDefaultButton(ok);
	}
}
