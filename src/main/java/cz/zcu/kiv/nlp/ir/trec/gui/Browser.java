package cz.zcu.kiv.nlp.ir.trec.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by msip on 5/3/17.
 */
public class Browser extends JFrame {

    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 500;

    private ResultList resultList;

    public Browser() {
        super("MyBrowser");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.add(createSearchPanel(), BorderLayout.PAGE_START);
        this.add(new ResultList(), BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Enter query"));
        FlowLayout layout = new FlowLayout();
        panel.setLayout(layout);

        JButton searchButton = new JButton("Search");
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("SansSerif", Font.BOLD, 16));

        panel.add(textField);
        panel.add(searchButton);

        return panel;
    }

    @Override
    public Insets getInsets() {
        return new Insets(10, 10,10, 10);
    }
}
