package ru.terra.activitystore.gui.swt.edit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.gui.swt.select.SelectCellDialog;

public class EditCardDialog extends AbstractEditDialog<Card> {
    private String cardName;
    private String name;
    private Card ret, card;
    private Table cellsTable;
    private Text cardNameInput;

    public EditCardDialog(Shell arg0) {
        super(arg0);
    }

    @Override
    public Card open(Card card) {
        Shell shell = new Shell(getParent(), getStyle());
        shell.setText(getText());
        this.card = card;
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

    private void createContents(final Shell shell) {
        GridLayout gl = new GridLayout(2, true);
        shell.setLayout(gl);
        Label label = new Label(shell, SWT.NONE);
        label.setText("Новая карточка");
        GridData data = new GridData();
        data.horizontalSpan = 2;
        label.setLayoutData(data);

        cardNameInput = new Text(shell, SWT.BORDER);
        if (name != null)
            cardNameInput.setText(name);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        cardNameInput.setLayoutData(data);

        cellsTable = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        cellsTable.setLinesVisible(true);
        data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        data.verticalAlignment = GridData.FILL;
        data.grabExcessVerticalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.grabExcessHorizontalSpace = true;
        cellsTable.setLayoutData(data);
        if (card != null) {
            loadCard();
        } else {
            card = ActivityStoreController.getInstance().createCard("");
        }

        Menu cellsMenu = new Menu(cellsTable);
        MenuItem miDelete = new MenuItem(cellsMenu, SWT.POP_UP);
        miDelete.setText("Удалить");
        miDelete.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                if (cellsTable.getSelection() != null && cellsTable.getSelection().length == 1) {
                    TableItem selected = cellsTable.getSelection()[0];
                    if (selected != null) {
                        Cell c = (Cell) selected.getData();
                        ActivityStoreController.getInstance().deleteCellFromCard(c, card);
                        cellsTable.remove(cellsTable.getSelectionIndex());
                        cellsTable.setRedraw(true);
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        MenuItem miAdd = new MenuItem(cellsMenu, SWT.POP_UP);
        miAdd.setText("Добавить");
        miAdd.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                Cell newCell = new SelectCellDialog(shell).open();
                if (newCell != null) {
                    ActivityStoreController.getInstance().addCellToCard(newCell, card);
                    newCell.getCardList().add(card);
                    TableItem newItem = new TableItem(cellsTable, SWT.NONE);
                    newItem.setText(0, newCell.getComment());
                    newItem.setData(newCell);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        cellsTable.setMenu(cellsMenu);

        Button ok = new Button(shell, SWT.PUSH);
        ok.setText("OK");
        data = new GridData(GridData.FILL_HORIZONTAL);
        ok.setLayoutData(data);
        ok.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                cardName = cardNameInput.getText();
                card.setName(cardName);
                ret = card;
                shell.close();
            }
        });

        Button cancel = new Button(shell, SWT.PUSH);
        cancel.setText("Отмена");
        data = new GridData(GridData.FILL_HORIZONTAL);
        cancel.setLayoutData(data);
        cancel.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent event) {
                cardName = null;
                card = null;
                ret = card;
                shell.close();
            }
        });

        shell.setDefaultButton(ok);
    }

    private void loadCard() {
        cardNameInput.setText(card.getName());
        cellsTable.setRedraw(false);
        for (Cell c : ActivityStoreController.getInstance().getCells(card)) {
            TableItem ti = new TableItem(cellsTable, SWT.NONE);
            ti.setText(0, c.getComment());
            ti.setData(c);
            System.out.println("Loading cell to table " + c.getComment());
        }
        cellsTable.setRedraw(true);
    }

    @Override
    public Card open() {
        return open(null);
    }
}
