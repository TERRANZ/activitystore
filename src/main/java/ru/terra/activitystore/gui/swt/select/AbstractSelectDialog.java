package ru.terra.activitystore.gui.swt.select;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.terra.activitystore.gui.swt.edit.AbstractEditDialog;

public abstract class AbstractSelectDialog<T> extends Dialog {

    protected T ret;
    protected String newButtonText, selectText;
    private Table table;
    private AbstractEditDialog<T> editDialog;
    private Shell shell;

    public AbstractSelectDialog(Shell arg0, String newButtonText, String selectText, AbstractEditDialog<T> editDialog) {
        super(arg0, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        this.newButtonText = newButtonText;
        this.selectText = selectText;
        this.editDialog = editDialog;
        this.shell = arg0;
    }

    protected Shell getShell() {
        return shell;
    }

    protected Table getTable() {
        return table;
    }

    public T open() {
        Shell shell = new Shell(getParent(), getStyle());
        shell.setText(getText());
        createContents(shell);
        shell.pack();
        shell.open();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return ret;
    }

    protected void createContents(final Shell shell) {

        shell.setLayout(new GridLayout(2, true));
        Label label = new Label(shell, SWT.NONE);
        label.setText(selectText);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        label.setLayoutData(data);

        Button newCellBtn = new Button(shell, SWT.PUSH);
        newCellBtn.setText("Новый");
        newCellBtn.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        newCellBtn.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                ret = editDialog.open();
                shell.close();
            }
        });

        table = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        table.setLinesVisible(true);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        table.setLayoutData(data);
        loadCells();
        Button ok = new Button(shell, SWT.PUSH);
        ok.setText("OK");
        data = new GridData(GridData.FILL_HORIZONTAL);
        ok.setLayoutData(data);
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                if (table.getSelection() != null && table.getSelection().length == 1) {
                    TableItem ti = table.getSelection()[0];
                    if (ti != null) {
                        ret = (T) ti.getData();
                        shell.close();
                    }
                }
            }
        });

        Button cancel = new Button(shell, SWT.PUSH);
        cancel.setText("Отмена");
        data = new GridData(GridData.FILL_HORIZONTAL);
        cancel.setLayoutData(data);
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                ret = null;
                shell.close();
            }
        });

        shell.setDefaultButton(ok);
    }

    protected abstract void loadCells();
}
