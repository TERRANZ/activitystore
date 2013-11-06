package ru.terra.activitystore.gui.swt;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;
import ru.terra.activitystore.constants.Constants;
import ru.terra.activitystore.controller.ActivityStoreController;
import ru.terra.activitystore.db.entity.Block;
import ru.terra.activitystore.db.entity.Card;
import ru.terra.activitystore.db.entity.Cell;
import ru.terra.activitystore.gui.swt.edit.BlockInputDialog;
import ru.terra.activitystore.gui.swt.edit.EditCardDialog;
import ru.terra.activitystore.gui.swt.edit.ListSelectDialog;
import ru.terra.activitystore.gui.swt.print.BlockPrint;
import ru.terra.activitystore.gui.swt.print.CardPrint;
import ru.terra.activitystore.gui.swt.select.TemplatesDialog;
import ru.terra.activitystore.util.Pair;
import ru.terra.activitystore.util.RandomUtils;
import ru.terra.activitystore.view.ActivityStoreView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainWindow extends ActivityStoreView {
    private Shell shell;
    private Tree tree;
    private Table cardViewer;
    private Image blockImage, cardImage;
    private Menu blockMenu, cardMenu;
    private Display display;

    private class ViewHolder {
        public static final int BLOCK = 0;
        public static final int CARD = 1;
        public static final int CELL = 2;

        public int type;
        public Block block;
        public Card card;
        public Cell cell;
        public boolean edit;

        public ViewHolder(Block block, Card card, Cell cell, int type) {
            this.block = block;
            this.card = card;
            this.type = type;
            this.edit = false;
            this.cell = cell;
        }
    }

    public MainWindow(ActivityStoreController controller) {
        super(controller);
    }

    private void prepareImages() {
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
    public void start() {
        display = new Display();
        prepareImages();
        shell = new Shell(display);
        shell.setLayout(new FillLayout(SWT.HORIZONTAL));
        shell.setText("Управление активностями");
        fillMenu();
        createTree();
        cardViewer = new Table(shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        String[] titles = {"Ячейка", "Значение", "Тип"};
        for (int i = 0; i < titles.length; i++) {
            TableColumn column = new TableColumn(cardViewer, SWT.NONE);
            column.setText(titles[i]);
        }
        for (int i = 0; i < titles.length; i++) {
            cardViewer.getColumn(i).pack();
        }
        cardViewer.setLinesVisible(true);
        cardViewer.setHeaderVisible(true);
        shell.open();
        controller.onViewStarted();

        while (!shell.isDisposed()) {
            try {
                if (!display.readAndDispatch())
                    display.sleep();
            } catch (Exception e) {
                MessageDialog.openError(shell, "Ошибка", "Произошла ошибка при работе приложения: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        display.dispose();
    }

    private void fillMenu() {
        Menu bar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(bar);
        MenuItem fileItem = new MenuItem(bar, SWT.CASCADE);
        fileItem.setText("&Управление");
        Menu submenu = new Menu(shell, SWT.DROP_DOWN);
        fileItem.setMenu(submenu);

        MenuItem editTemplates = new MenuItem(submenu, SWT.PUSH);
        editTemplates.setText("Настройка шаблонов");
        editTemplates.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                TemplatesDialog td = new TemplatesDialog(shell);
                td.open();

            }
        });

        MenuItem lists = new MenuItem(submenu, SWT.PUSH);
        lists.setText("списки");
        lists.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event arg0) {
                ListSelectDialog dlg = new ListSelectDialog(shell);
                dlg.open();
            }
        });

        MenuItem exitItem = new MenuItem(submenu, SWT.PUSH);
        exitItem.setText("Выход");
        exitItem.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event e) {
                System.exit(0);
            }
        });
        createBlockMenu();
        createCardMenu();
    }

    private void createBlockMenu() {
        blockMenu = new Menu(shell, SWT.POP_UP);
        MenuItem createBlockMenuItem = new MenuItem(blockMenu, SWT.PUSH);
        createBlockMenuItem.setText("Новый блок");
        MenuItem createCardMenuItem = new MenuItem(blockMenu, SWT.PUSH);
        createCardMenuItem.setText("Новая карточка");
        MenuItem deleteBlockMenuItem = new MenuItem(blockMenu, SWT.PUSH);
        deleteBlockMenuItem.setText("Удалить блок");
        MenuItem editBlockName = new MenuItem(blockMenu, SWT.PUSH);
        editBlockName.setText("Редактировать");
        MenuItem printBlock = new MenuItem(blockMenu, SWT.PUSH);
        printBlock.setText("Отчёт по блоку");
        createBlockMenuItem.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                try {
                    BlockInputDialog dlg = new BlockInputDialog(shell);
                    String name = dlg.open(null);
                    if (name != null) {
                        TreeItem parent = tree.getSelection()[0];
                        if (parent != null) {
                            ViewHolder vh = (ViewHolder) parent.getData();
                            if (vh.type == ViewHolder.BLOCK) {
                                Block newBlock = new Block();
                                newBlock.setName(name);
                                ViewHolder newVH = new ViewHolder(newBlock, null, null, ViewHolder.BLOCK);
                                TreeItem newItem = new TreeItem(parent, 0);
                                newItem.setText(name);
                                newItem.setData(newVH);
                                newItem.setImage(blockImage);
                                controller.addBlockToBlock(newBlock, vh.block);
                            }
                        }
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при создании блока", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        createCardMenuItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                try {
                    EditCardDialog dlg = new EditCardDialog(shell);
                    Card card = dlg.open(null);
                    if (card != null) {
                        TreeItem parent = tree.getSelection()[0];
                        if (parent != null) {
                            ViewHolder vh = (ViewHolder) parent.getData();
                            if (vh.type == ViewHolder.BLOCK) {
                                ViewHolder newVH = new ViewHolder(null, card, null, ViewHolder.CARD);
                                TreeItem newItem = new TreeItem(parent, 0);
                                newItem.setText(card.getName());
                                newItem.setData(newVH);
                                newItem.setImage(cardImage);
                                controller.addCardToBlock(card, vh.block);
                            }
                        }
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при создании карточки", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        deleteBlockMenuItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                TreeItem parent = tree.getSelection()[0];
                if (parent != null) {
                    ViewHolder vh = (ViewHolder) parent.getData();
                    if (vh.type == ViewHolder.BLOCK) {
                        if (vh.block.getId().equals(0)) {
                            MessageDialog.openError(shell, "Ошибка", "Нельзя удалять корневой блок");
                        } else {
                            if (MessageDialog.openConfirm(shell, "Удаление блока", "Вы действительно хотите удалить блок '" + vh.block.getName()
                                    + "'")) {
                                controller.deleteBlock(vh.block, true);
                                parent.dispose();
                            }
                        }
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        editBlockName.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                try {
                    if (tree.getSelection() != null && tree.getSelection().length == 1) {
                        TreeItem selectedItem = tree.getSelection()[0];
                        if (selectedItem != null) {
                            ViewHolder vh = (ViewHolder) selectedItem.getData();
                            if (vh.type == ViewHolder.BLOCK) {
                                BlockInputDialog dlg = new BlockInputDialog(shell);
                                Block selectedBlock = vh.block;
                                String name = dlg.open(selectedBlock.getName());
                                if (name != null) {
                                    selectedBlock.setName(name);
                                    controller.updateBlock(selectedBlock);
                                    vh.block = selectedBlock;
                                    selectedItem.setData(vh);
                                    selectedItem.setText(name);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при редактировании имени блока", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {

            }
        });

        printBlock.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                try {
                    if (tree.getSelection() != null && tree.getSelection().length == 1) {
                        TreeItem selectedBlock = tree.getSelection()[0];
                        if (selectedBlock != null) {
                            ViewHolder vh = (ViewHolder) selectedBlock.getData();
                            if (vh.type == ViewHolder.BLOCK) {
                                Block block = vh.block;
                                try {
                                    new BlockPrint(block, shell, 0).open();
                                } catch (Exception e) {
                                    MessageDialog.openError(shell, "Ошибка запуска окна для отчёта ", e.getMessage());
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при печати блока", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }

        });
    }

    private void createCardMenu() {
        cardMenu = new Menu(shell, SWT.POP_UP);
        MenuItem editCardMenuItem = new MenuItem(cardMenu, SWT.PUSH);
        editCardMenuItem.setText("Редактировать");
        editCardMenuItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                try {
                    TreeItem ti = tree.getSelection()[0];
                    if (ti != null && ((ViewHolder) ti.getData()).type == ViewHolder.CARD) {
                        Card ret = new EditCardDialog(shell).open(((ViewHolder) ti.getData()).card);
                        if (ret != null)
                            controller.updateCard(ret);
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при редактировании карточки", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        MenuItem deleteCardMenuItem = new MenuItem(cardMenu, SWT.PUSH);
        deleteCardMenuItem.setText("Удалить");
        deleteCardMenuItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                TreeItem parent = tree.getSelection()[0];
                if (parent != null) {
                    ViewHolder vh = (ViewHolder) parent.getData();
                    if (vh.type == ViewHolder.CARD) {
                        if (MessageDialog.openConfirm(shell, "Удаление карточки", "Вы действительно хотите удалить карточку '" + vh.card.getName()
                                + "'")) {
                            controller.deleteCard(vh.card, true);
                            parent.dispose();
                        }
                    }
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });

        MenuItem printCardMenuItem = new MenuItem(cardMenu, SWT.PUSH);
        printCardMenuItem.setText("Печать");
        printCardMenuItem.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent arg0) {
                try {
                    TreeItem ti = tree.getSelection()[0];
                    if (ti != null && ((ViewHolder) ti.getData()).type == ViewHolder.CARD) {
                        new CardPrint(((ViewHolder) ti.getData()).card, shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL).open();
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при печати карточки", e.getMessage());
                    e.printStackTrace();
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });
    }

    private void createTree() {
        tree = new Tree(shell, SWT.BORDER);
        tree.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event arg0) {
                try {
                    if (tree.getSelection() != null && tree.getSelection().length > 0) {
                        TreeItem item = tree.getSelection()[0];
                        if (item != null) {
                            if (((ViewHolder) item.getData()).type == ViewHolder.BLOCK) {
                                tree.setMenu(blockMenu);
                                cardViewer.clearAll();
                                cardViewer.removeAll();
                            } else {
                                tree.setMenu(cardMenu);
                                loadCard(((ViewHolder) item.getData()).block, ((ViewHolder) item.getData()).card);
                            }
                        } else {
                            tree.setMenu(null);
                        }
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при создании карточки", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadCard(Block block, Card card) {
        cardViewer.clearAll();
        cardViewer.removeAll();
        for (Cell c : controller.getCells(card)) {
            TableItem ti = new TableItem(cardViewer, SWT.NONE);
            String cellType = (String) RandomUtils.getMapKeyByValue(Constants.getConstants().getCellTypes(), c.getType());
            ti.setText(new String[]{c.getComment(), controller.getCardCellVal(card.getId(), c.getId()), cellType});
            ti.setData(new ViewHolder(block, card, c, ViewHolder.CELL));
        }
        cardViewer.addListener(SWT.MouseDown, new Listener() {
            public void handleEvent(Event event) {
                try {
                    Rectangle clientArea = cardViewer.getClientArea();
                    Point pt = new Point(event.x, event.y);
                    boolean visible = false;
                    if (cardViewer.getSelectionCount() > 0) {
                        // получаем выделенный элемент
                        final TableItem item = cardViewer.getSelection()[0];
                        // ищем колонку, в которую кликнули
                        for (int i = 0; i < cardViewer.getColumnCount(); i++) {
                            Rectangle rect = item.getBounds(i);
                            if (rect.contains(pt)) {
                                // нашли колонку в которую кликнули
                                final int column = i;
                                final Integer cardId = ((ViewHolder) item.getData()).card.getId();
                                final Integer cellId = ((ViewHolder) item.getData()).cell.getId();
                                switch (column) {
                                    case 1: {
                                        Cell cell = ((ViewHolder) item.getData()).cell;
                                        if (cell.getType() == 4) {
                                            ListSelectDialog dlg = new ListSelectDialog(shell);
                                            Pair<Integer, List<Integer>> ret = dlg.open();
                                            cell.setListId(controller.getList(ret.x));
                                            String s = "";
                                            if (ret.y != null) {
                                                for (Integer selectedListValId : ret.y) {
                                                    s += selectedListValId.toString() + ",";
                                                }
                                                s = s.substring(0, s.length() - 1);
                                                // cell.setVal(s);
                                                // controller.updateCell(cell);
                                                controller.setCardCellVal(cardId, cellId, s);
                                                item.setText(1, s);
                                            }
                                        } else {
                                            final Text text = new Text(cardViewer, SWT.NONE);
                                            // final Integer cardId = ((ViewHolder)
                                            // item.getData()).card.getId();
                                            // final Integer cellId = ((ViewHolder)
                                            // item.getData()).cell.getId();
                                            Listener textListener = new Listener() {
                                                public void handleEvent(final Event e) {
                                                    switch (e.type) {
                                                        case SWT.FocusOut:
                                                            item.setText(column, text.getText());
                                                            controller.setCardCellVal(cardId, cellId, text.getText());
                                                            text.dispose();
                                                            break;
                                                        case SWT.Traverse:
                                                            switch (e.detail) {
                                                                case SWT.TRAVERSE_RETURN:
                                                                    item.setText(column, text.getText());
                                                                    ((ViewHolder) item.getData()).edit = true;
                                                                    // FALL THROUGH
                                                                case SWT.TRAVERSE_ESCAPE:
                                                                    e.doit = false;
                                                                    controller.setCardCellVal(cardId, cellId, text.getText());
                                                                    text.dispose();
                                                            }
                                                            break;
                                                    }
                                                }
                                            };

                                            text.addListener(SWT.FocusOut, textListener);
                                            text.addListener(SWT.Traverse, textListener);
                                            TableEditor editor = new TableEditor(cardViewer);
                                            editor.horizontalAlignment = SWT.LEFT;
                                            editor.grabHorizontal = true;

                                            editor.setEditor(text, item, i);
                                            text.setText(item.getText(i));
                                            text.selectAll();
                                            text.setFocus();
                                        }
                                        return;
                                    }
                                    case 2: {
                                        final Combo combo = new Combo(shell, SWT.VERTICAL | SWT.DROP_DOWN | SWT.BORDER | SWT.READ_ONLY);
                                        TableEditor te = new TableEditor(cardViewer);
                                        final Map<String, Integer> cellTypes = Constants.getConstants().getCellTypes();
                                        for (Integer type : cellTypes.values()) {
                                            combo.add((String) RandomUtils.getMapKeyByValue(cellTypes, type));
                                        }
                                        Listener comboListener = new Listener() {
                                            @Override
                                            public void handleEvent(Event e) {
                                                switch (e.type) {
                                                    case SWT.FocusOut:
                                                        item.setText(column, combo.getText());
                                                        ((ViewHolder) item.getData()).edit = true;
                                                        ((ViewHolder) item.getData()).cell.setType(cellTypes.get(combo.getText()));
                                                        controller.updateCell(((ViewHolder) item.getData()).cell);
                                                        combo.dispose();
                                                        break;
                                                    case SWT.Traverse:
                                                        switch (e.detail) {
                                                            case SWT.TRAVERSE_RETURN:
                                                                item.setText(column, combo.getText());
                                                                ((ViewHolder) item.getData()).edit = true;
                                                                ((ViewHolder) item.getData()).cell.setType(cellTypes.get(combo.getText()));
                                                                controller.updateCell(((ViewHolder) item.getData()).cell);
                                                            case SWT.TRAVERSE_ESCAPE:
                                                                combo.dispose();
                                                                e.doit = false;
                                                        }
                                                        break;
                                                }
                                            }
                                        };
                                        combo.addListener(SWT.FocusOut, comboListener);
                                        combo.addListener(SWT.Traverse, comboListener);
                                        TableEditor editor = new TableEditor(cardViewer);
                                        editor.horizontalAlignment = SWT.LEFT;
                                        editor.grabHorizontal = true;
                                        editor.setEditor(combo, item, i);
                                        combo.setText(item.getText(i));
                                        combo.setFocus();
                                        return;
                                    }
                                }
                            }
                            if (!visible && rect.intersects(clientArea)) {
                                visible = true;
                            }
                        }
                        if (!visible)
                            return;
                    }
                } catch (Exception e) {
                    MessageDialog.openError(shell, "Ошибка при создании карточки", e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void fillBlocksTree(List<Block> blocks) {
        HashMap<Integer, TreeItem> blockMap = new HashMap<Integer, TreeItem>();
        for (Block b : blocks) {
            TreeItem newItem;

            TreeItem parentItem = blockMap.get(b.getParent());
            if (parentItem != null) {
                newItem = new TreeItem(parentItem, 0);
            } else {
                newItem = new TreeItem(tree, 0);
            }

            newItem.setText(b.getName());
            newItem.setData(new ViewHolder(b, null, null, ViewHolder.BLOCK));
            newItem.setImage(blockImage);
            List<Card> cards = controller.getCards(b);
            if (cards != null) {
                for (Card c : cards) {
                    TreeItem cardItem = new TreeItem(newItem, 0);
                    cardItem.setData(new ViewHolder(null, c, null, ViewHolder.CARD));
                    cardItem.setText(c.getName());
                    cardItem.setImage(cardImage);
                }
            }
            blockMap.put(b.getId(), newItem);
        }
    }
}
