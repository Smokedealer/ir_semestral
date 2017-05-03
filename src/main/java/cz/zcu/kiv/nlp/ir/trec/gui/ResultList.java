package cz.zcu.kiv.nlp.ir.trec.gui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Created by msip on 5/3/17.
 */

class TableModel extends AbstractTableModel {

    public int getRowCount() {
        return 0;
    }

    public int getColumnCount() {
        return 0;
    }

    public Object getValueAt(int i, int i1) {
        return null;
    }
}

public class ResultList extends JPanel {
    public ResultList() {
        this.setBorder(BorderFactory.createTitledBorder("Results"));
    }
}
