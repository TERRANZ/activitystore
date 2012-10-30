package ru.terra.activitystore.gui.swt.edit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import ru.terra.activitystore.db.entity.Card;
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

	private void createContents(final Shell shell)
	{
		shell.setLayout(new GridLayout(2, true));
		Label label = new Label(shell, SWT.NONE);
		label.setText("Шаблон");
		GridData data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		final Label label2 = new Label(shell, SWT.NONE);
		label2.setText("Карточка:  нет карточки");
		data = new GridData();
		data.horizontalSpan = 2;
		label.setLayoutData(data);

		templateNameInput = new Text(shell, SWT.BORDER);
		if (templateName != null)
			templateNameInput.setText(templateName);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		templateNameInput.setLayoutData(data);

		Button setCard = new Button(shell, SWT.PUSH);
		setCard.setText("Карточка");
		data = new GridData(GridData.FILL);
		setCard.setLayoutData(data);
		setCard.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				EditCardDialog dlg = new EditCardDialog(shell);
				Card card = dlg.open(null);
				if (card != null)
				{
					templateName = templateNameInput.getText();
					if (ret == null)
						ret = controller.createTemplate(templateName, card);
					else
						ret.setCard(card);
					label2.setText("Карточка: " + card.getName());
				}
			}
		});

		Button ok = new Button(shell, SWT.PUSH);
		ok.setText("OK");
		data = new GridData(GridData.FILL_HORIZONTAL);
		ok.setLayoutData(data);
		ok.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				templateName = templateNameInput.getText();
				ret.setName(templateName);
				shell.close();
			}
		});

		Button cancel = new Button(shell, SWT.PUSH);
		cancel.setText("Отмена");
		data = new GridData(GridData.FILL_HORIZONTAL);
		cancel.setLayoutData(data);
		cancel.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent event)
			{
				shell.close();
			}
		});

		shell.setDefaultButton(ok);
	}
}
