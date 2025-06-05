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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class SalesFrame extends JFrame implements ActionListener {
    JLabel searchLabel;
    JTextField searchField;
    JButton addButton, removeButton, checkoutButton, generateSaleSummaryButton;
    Container container;
    FlowLayout layout;
    private JTable inventoryTable, checkoutTable;
    private TableRowSorter<InventoryTableModel> sorter;
    private InventoryTableModel inventoryTableModel;
    private CheckOutTableModel checkOutTableModel;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    private ArrayList<SaleTransaction> completedSales;

    public SalesFrame(InventoryTableModel tableModel) throws HeadlessException {
        super("Sales Management");

        container = this.getContentPane();
        layout = new FlowLayout(FlowLayout.LEFT);
        container.setLayout(layout);
        completedSales = new ArrayList<>();

        searchLabel = new JLabel("Search:");
        searchField = new JTextField(25);

        addButton = new JButton("Add Item");
        removeButton = new JButton("Remove Item");
        checkoutButton = new JButton("Checkout");
        generateSaleSummaryButton = new JButton("Generate Sale Summary");
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        checkoutButton.addActionListener(this);
        generateSaleSummaryButton.addActionListener(this);

        JPanel stockPanel = new JPanel(new BorderLayout());

        inventoryTableModel = tableModel;
        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setAutoCreateRowSorter(true);
        inventoryTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane tablePane = new JScrollPane(inventoryTable);
        tablePane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Inventory", TitledBorder.LEFT, TitledBorder.TOP));
        tablePane.setPreferredSize(new Dimension(850, 200));


        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(addButton);
        searchPanel.add(generateSaleSummaryButton);


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
                    int quantity = 0;
                    try {
                        String qtyInput = JOptionPane.showInputDialog(SalesFrame.this, "Enter Quantity:", "Add Item Quantity", JOptionPane.PLAIN_MESSAGE);
                        if (qtyInput == null || qtyInput.trim().isEmpty()) { // User cancelled or entered empty string
                            return;
                        }
                        quantity = Integer.parseInt(qtyInput.trim());
                        if (quantity <= 0) {
                            JOptionPane.showMessageDialog(SalesFrame.this, "Quantity must be a positive number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(SalesFrame.this, "Invalid quantity. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int selectedRowInModel = inventoryTable.convertRowIndexToModel(selectedRowInView);
                    // Ensure InventoryTableModel has a public method to get DrugData by model index
                    // Example: public DrugData getDrugData(int modelRowIndex) { return drugDataList.get(modelRowIndex); }
                    DrugData selectedDrug = inventoryTableModel.drugDataList.get(selectedRowInModel); // Assuming this method exists

                    if (selectedDrug != null) {
                        // Check if inventory has enough stock (assuming DrugData has getQuantity())
                        if (selectedDrug.getQuantity() < quantity) {
                            JOptionPane.showMessageDialog(SalesFrame.this, "Not enough stock in inventory for " + selectedDrug.getBrand_name() + ". Available: " + selectedDrug.getQuantity(), "Insufficient Stock", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        // Create CheckoutData object
                        CheckoutData checkoutData = new CheckoutData(
                                selectedDrug.getBatch_number(),
                                selectedDrug.getBrand_name(),
                                selectedDrug.getGeneric_name(),
                                quantity,
                                (double)quantity * selectedDrug.getSelling_price() // Calculate total price for this line
                        );
                        checkOutTableModel.addCheckoutData(checkoutData);

                        // OPTIONAL: Decrement inventory stock immediately
                        // You'd need a method in InventoryTableModel to update quantity based on drug (e.g., by batch #)
                        // inventoryTableModel.decrementQuantityByBatch(selectedDrug.getBatch_number(), quantity);

                        System.out.println("Added " + selectedDrug.getBrand_name() + " (Qty: " + quantity + ") to checkout.");
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
            inventoryTable.getActionMap().get("addToCheckout").actionPerformed(new ActionEvent(inventoryTable, ActionEvent.ACTION_PERFORMED, null));
        } else if (e.getSource() == removeButton) {
            inventoryTable.getActionMap().get("removeFromCheckout").actionPerformed(new ActionEvent(checkoutTable, ActionEvent.ACTION_PERFORMED,null));
        } else if (e.getSource() == checkoutButton) {
            double total = checkOutTableModel.getTotalCheckoutAmount();

            if (checkOutTableModel.getRowCount() == 0) { // Check if checkout is empty
                JOptionPane.showMessageDialog(this, "Checkout table is empty. Please add items.", "Checkout Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, String.format("Total: Php %.2f", total), "Confirm Checkout", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // --- NEW: Save the completed transaction ---
                // Get a defensive copy of the current checkout items
                ArrayList<CheckoutData> itemsForSale = checkOutTableModel.getCurrentCheckoutItems();
                SaleTransaction newSale = new SaleTransaction(new Date(), itemsForSale, total);
                completedSales.add(newSale); // Add to our list of completed sales

                // Clear the current checkout table for the next transaction
                checkOutTableModel.clearAllItems();
                JOptionPane.showMessageDialog(this, "Sale completed and recorded!\nTotal: Php " + String.format("%.2f", total), "Transaction Complete", JOptionPane.INFORMATION_MESSAGE);

                 for (CheckoutData item : itemsForSale) {
                     inventoryTableModel.decrementQuantityByBatch(item.getBatch_number(), item.getQuantity());
                 }
            } else {
                JOptionPane.showMessageDialog(this, "Checkout cancelled.", "Transaction Cancelled", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (e.getSource() == generateSaleSummaryButton) {
            generateSalesSummaryReport();
        }
    }
    private void generateSalesSummaryReport() {
        if (completedSales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No completed sales to generate a summary report.", "No Sales Data", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create a new modal dialog for the report
        JDialog reportDialog = new JDialog(this, "Sales Summary Report", true); // `true` makes it modal
        reportDialog.setSize(550, 600); // Adjust size as needed
        reportDialog.setLocationRelativeTo(this); // Center relative to SalesFrame

        JTextArea reportTextArea = new JTextArea();
        reportTextArea.setEditable(false); // Make it read-only
        reportTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Monospaced font for receipt-style alignment
        JScrollPane scrollPane = new JScrollPane(reportTextArea);

        StringBuilder reportBuilder = new StringBuilder();

        // --- Report Header ---
        reportBuilder.append("===================================================\n");
        reportBuilder.append(String.format("%-51s\n", "\t SALES SUMMARY REPORT"));
        reportBuilder.append(String.format("%-51s\n", "Victorias City, Negros Occidental, Philippines"));
        reportBuilder.append("---------------------------------------------------\n");
        reportBuilder.append("Generated On: ").append(DATETIME_FORMAT.format(new Date())).append("\n"); // Current generation time
        reportBuilder.append("===================================================\n\n");

        double grandTotalSales = 0.0;
        int transactionCount = 0;

        // --- Iterate through each completed sale transaction ---
        for (SaleTransaction sale : completedSales) {
            transactionCount++;
            reportBuilder.append("---------------------------------------------------\n");
            reportBuilder.append("TRANSACTION # ").append(transactionCount).append(" (ID: ").append(sale.getTransactionId()).append(")\n");
            reportBuilder.append("Date:     ").append(DATETIME_FORMAT.format(sale.getTransactionDate())).append("\n");
            reportBuilder.append("---------------------------------------------------\n");
            reportBuilder.append(String.format("%-25s %5s %10s %10s\n", "ITEM", "QTY", "UNIT PR", "TOTAL"));
            reportBuilder.append("---------------------------------------------------\n");

            // --- Iterate through items within each transaction ---
            for (CheckoutData item : sale.getItems()) {
                String itemName = item.getBrand_name();
                if (itemName.length() > 24) { // Truncate if too long for ITEM column
                    itemName = itemName.substring(0, 22) + "..";
                }
                reportBuilder.append(String.format("%-25s %5d %10.2f %10.2f\n",
                        itemName,
                        item.getQuantity(),
                        item.getPrice(),
                        item.getPrice()));
            }
            reportBuilder.append("---------------------------------------------------\n");
            reportBuilder.append(String.format("%-42s %10.2f\n", "TRANSACTION TOTAL:", sale.getTotalAmount()));
            reportBuilder.append("---------------------------------------------------\n\n");
            grandTotalSales += sale.getTotalAmount();
        }

        // --- Report Footer ---
        reportBuilder.append("===================================================\n");
        reportBuilder.append(String.format("TOTAL COMPLETED TRANSACTIONS: %d\n", transactionCount));
        reportBuilder.append(String.format("GRAND TOTAL SALES: Php %.2f\n", grandTotalSales));
        reportBuilder.append("===================================================\n");
        reportBuilder.append(String.format("%-51s\n", "                 END OF REPORT"));
        reportBuilder.append("===================================================\n");

        reportTextArea.setText(reportBuilder.toString());
        reportTextArea.setCaretPosition(0);

        reportDialog.add(scrollPane, BorderLayout.CENTER);
        reportDialog.setVisible(true);
    }

}
