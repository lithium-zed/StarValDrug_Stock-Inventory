package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

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
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        checkoutButton.addActionListener(this);

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


        stockPanel.add(tablePane,BorderLayout.NORTH);
        stockPanel.add(searchPanel,BorderLayout.CENTER);

        sorter = new TableRowSorter<>(inventoryTableModel);
        inventoryTable.setRowSorter(sorter);
        sorter.setComparator(7, new Comparator<String>() {
            private final SimpleDateFormat parser = new SimpleDateFormat("MM/dd/yyyy"); // Local instance for thread-safety
            @Override
            public int compare(String dateStr1, String dateStr2) {
                try {
                    Date date1 = parser.parse(dateStr1);
                    Date date2 = parser.parse(dateStr2);
                    return date1.compareTo(date2);
                } catch (ParseException e) {
                    // Handle parsing errors (e.g., malformed date strings)
                    // You might want to log this or treat malformed dates as first/last
                    System.err.println("Error parsing date for sorting: " + e.getMessage());
                    // Fallback to string comparison if parsing fails, or place them at the end/beginning
                    return dateStr1.compareTo(dateStr2); // Fallback
                }
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }

            public void filterTable(){
                String text = searchField.getText();
                if(text.trim().isEmpty()){
                    sorter.setRowFilter(null);
                }else{
                    try{
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }catch (PatternSyntaxException e){
                        sorter.setRowFilter(null);
                    }
                }
            }
        });



        JPanel checkoutPanel = new JPanel(new BorderLayout());
        checkOutTableModel = new CheckOutTableModel();
        checkoutTable = new JTable(checkOutTableModel);
        checkoutTable.setFillsViewportHeight(true);
        checkoutTable.setAutoCreateRowSorter(true);
        checkoutTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane checkOutPane = new JScrollPane(checkoutTable);
        checkOutPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Checkout", TitledBorder.LEFT, TitledBorder.TOP));
        checkoutPanel.add(checkOutPane, BorderLayout.NORTH);
        checkoutPanel.add(checkoutButton, BorderLayout.CENTER);
        checkoutPanel.add(removeButton, BorderLayout.SOUTH);

        inventoryTable.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "addToCheckout");
        inventoryTable.getActionMap().put("addToCheckout", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowInView = inventoryTable.getSelectedRow();
                if (selectedRowInView != -1) {

                    int quantity = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));
                    int selectedRowInModel = inventoryTable.convertRowIndexToModel(selectedRowInView);
                    DrugData selectedDrug = inventoryTableModel.drugDataList.get(selectedRowInModel);
                    CheckoutData checkoutData = new CheckoutData(selectedDrug.getBatch_number(),selectedDrug.getBrand_name(),selectedDrug.getGeneric_name(),quantity,(quantity*selectedDrug.getSelling_price()));
                    if (selectedDrug != null) {
                        checkOutTableModel.addCheckoutData(checkoutData);
                    }
                } else {
                    JOptionPane.showMessageDialog(SalesFrame.this,
                            "Please select an item from the inventory to add to checkout.",
                            "No Item Selected", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        checkoutTable.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "removeFromCheckout");
        checkoutTable.getActionMap().put("removeFromCheckout", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRowInView = checkoutTable.getSelectedRow();
                if (selectedRowInView != -1) { // Check if a row is actually selected
                    // Convert row index from view to model, accounting for sorting/filtering
                    int selectedRowInModel = checkoutTable.convertRowIndexToModel(selectedRowInView);

                    // Call the removeItem method in your CheckOutTableModel
                    checkOutTableModel.removeItem(selectedRowInModel);
                } else {
                    JOptionPane.showMessageDialog(SalesFrame.this,
                            "Please select an item from the checkout table to remove.",
                            "No Item Selected", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        container.add(stockPanel);
        container.add(checkoutPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton){
            int selectedRowInView = inventoryTable.getSelectedRow();
            if (selectedRowInView != -1) {

                int quantity = Integer.parseInt(JOptionPane.showInputDialog("Quantity:"));
                int selectedRowInModel = inventoryTable.convertRowIndexToModel(selectedRowInView);
                DrugData selectedDrug = inventoryTableModel.drugDataList.get(selectedRowInModel);
                CheckoutData checkoutData = new CheckoutData(selectedDrug.getBatch_number(),selectedDrug.getBrand_name(),selectedDrug.getGeneric_name(),quantity,(quantity*selectedDrug.getSelling_price()));
                if (selectedDrug != null) {
                    checkOutTableModel.addCheckoutData(checkoutData);
                }
            } else {
                JOptionPane.showMessageDialog(SalesFrame.this,
                        "Please select an item from the inventory to add to checkout.",
                        "No Item Selected", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == removeButton) {
            int selectedRowInView = checkoutTable.getSelectedRow();
            if (selectedRowInView != -1) {
                int selectedRowInModel = checkoutTable.convertRowIndexToModel(selectedRowInView);
                
                checkOutTableModel.removeItem(selectedRowInModel);
            } else {
                JOptionPane.showMessageDialog(SalesFrame.this,
                        "Please select an item from the checkout table to remove.",
                        "No Item Selected", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == checkoutButton) {
            
        }
    }

}
