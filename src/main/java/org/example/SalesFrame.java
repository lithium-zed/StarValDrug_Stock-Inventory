package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class SalesFrame extends JFrame implements ActionListener {
    JLabel searchLabel;
    JTextField searchField;
    JButton addButton, removeButton, checkoutButton;
    Container container;
    FlowLayout layout;
    private JTable inventoryTable, checkoutTable;
    private TableRowSorter<InventoryTableModel> sorter;
    private InventoryTableModel inventoryTableModel;
    private CheckOutTableModel checkOutTableModel;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public SalesFrame(InventoryTableModel tableModel) throws HeadlessException {
        super("Sales Management");

        container = this.getContentPane();
        layout = new FlowLayout(FlowLayout.LEFT);
        container.setLayout(layout);


        searchLabel = new JLabel("Search:");
        searchField = new JTextField(25);

        addButton = new JButton("Add Item");
        removeButton = new JButton("Remove Item");
        checkoutButton = new JButton("Checkout");


        JPanel stockPanel = new JPanel(new BorderLayout());

        inventoryTableModel = tableModel;
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setAutoCreateRowSorter(true);
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane tablePane = new JScrollPane(inventoryTable);
        tablePane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Inventory", TitledBorder.LEFT, TitledBorder.TOP));
        tablePane.setPreferredSize(new Dimension(850, 200));


        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; searchPanel.add(searchLabel,gbc);
        gbc.gridx = 1; gbc.gridy = row; searchPanel.add(searchField,gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; searchPanel.add(addButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; searchPanel.add(removeButton, gbc);


        stockPanel.add(tablePane,BorderLayout.NORTH);
        stockPanel.add(searchPanel,BorderLayout.CENTER);


        JPanel checkoutPanel = new JPanel(new BorderLayout());
        checkOutTableModel = new CheckOutTableModel();
        checkoutTable = new JTable(checkOutTableModel);
        checkoutTable.setFillsViewportHeight(true);
        checkoutTable.setAutoCreateRowSorter(true);
        checkoutTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane checkOutPane = new JScrollPane(checkoutTable);
        checkoutPanel.add(checkOutPane, BorderLayout.NORTH);
        checkoutPanel.add(checkoutButton, BorderLayout.SOUTH);



        container.add(stockPanel);
        container.add(checkoutPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //to be changed to DISPOSE ON CLOSE
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
