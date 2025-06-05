package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.regex.PatternSyntaxException;

public class InventoryFrame extends JFrame implements ActionListener {
    JLabel vendorName, brandName, genericName, batchNumber, expirationDate, unitOfMeasure,
            quantity, purchaseCost, costPerUnitofMeasure, sellingPrice, target_margin, searchLabel;
    JTextField vendorField, brandField, genericField, batchField, expField, uomField, qtyField,
            prchsField, costPerUnitField, sellingPriceField, targetMarginField, searchField;

    // Table components - declared as instance variables
    private JTable inventoryTable;
    private TargetMargin targetMargin;
    private TableRowSorter<InventoryTableModel> sorter;
    private InventoryTableModel inventoryTableModel;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    JRadioButton c, p;
    ButtonGroup productOriginGroup;

    private JButton addButton, setButton, toSalesButton; // Declare the add button

    public InventoryFrame() throws HeadlessException {
        super("Inventory Management");

        // Initialize Labels
        vendorName = new JLabel("Vendor Name:");
        brandName = new JLabel("Brand Name:");
        genericName = new JLabel("Generic Name:");
        batchNumber = new JLabel("Batch #:");
        expirationDate = new JLabel("Expiration Date(MM/DD/YYYY):");
        unitOfMeasure = new JLabel("Unit Of Measure:");
        quantity = new JLabel("Quantity:");
        purchaseCost = new JLabel("Purchase Cost:");
        costPerUnitofMeasure = new JLabel("Cost / Unit of Measure:");
        sellingPrice = new JLabel("Selling Price:");
        target_margin = new JLabel("Target Margin:");
        searchLabel = new JLabel("Search:");

        // Initialize TextFields
        vendorField = new JTextField(15);
        brandField = new JTextField(15);
        genericField = new JTextField(15);
        batchField = new JTextField(15);
        expField = new JTextField(15);
        uomField = new JTextField(15);
        qtyField = new JTextField(15);
        prchsField = new JTextField(15);
        costPerUnitField = new JTextField(15);
        sellingPriceField = new JTextField(15);
        targetMarginField = new JTextField(10);
        searchField = new JTextField(20);

        // --- Create a JPanel for Product Information (Input Fields) ---
        JPanel productInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make fields fill horizontally
        gbc.anchor = GridBagConstraints.WEST;    // Anchor labels to the west

        productInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Product Details", TitledBorder.LEFT, TitledBorder.TOP));

        int row = 0; // Keep track of the current row

        // Vendor Name
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(vendorName, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(vendorField, gbc);
        row++;

        // Brand Name
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(brandName, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(brandField, gbc);
        row++;

        // Generic Name
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(genericName, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(genericField, gbc);
        row++;

        // Batch #
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(batchNumber, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(batchField, gbc);
        row++;

        // Expiration Date
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(expirationDate, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(expField, gbc);
        row++;

        // Unit Of Measure
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(unitOfMeasure, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(uomField, gbc);
        row++;

        // Quantity
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(quantity, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(qtyField, gbc);
        row++;

        // Purchase Cost
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(purchaseCost, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(prchsField, gbc);
        row++;

        // Cost / Unit of Measure
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(costPerUnitofMeasure, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(costPerUnitField, gbc);
        row++;

        // Selling Price
        gbc.gridx = 0; gbc.gridy = row; productInfoPanel.add(sellingPrice, gbc);
        gbc.gridx = 1; gbc.gridy = row; productInfoPanel.add(sellingPriceField, gbc);
        row++;


        // --- Create a JPanel for Product Origin Radio Buttons ---
        JPanel productOriginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productOriginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Product Origin", TitledBorder.LEFT, TitledBorder.TOP));

        c = new JRadioButton("Consigned");
        p = new JRadioButton("Purchased");

        productOriginGroup = new ButtonGroup();
        productOriginGroup.add(c);
        productOriginGroup.add(p);

        productOriginPanel.add(c);
        productOriginPanel.add(p);

        // --- Create a JPanel for Target Margin ---
        // Changed to GridBagLayout for better alignment of label and field
        setButton = new JButton("Set");
        setButton.addActionListener(this);
        targetMarginField.setText("0.25");
        targetMargin = new TargetMargin(0.25); // Initialize the targetMargin object
        JPanel targetMarginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints tmg_gbc = new GridBagConstraints();
        tmg_gbc.insets = new Insets(5, 5, 5, 5);
        tmg_gbc.anchor = GridBagConstraints.WEST; // Anchor to the left

        targetMarginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Margin Details", TitledBorder.LEFT, TitledBorder.TOP));

        tmg_gbc.gridx = 0; tmg_gbc.gridy = 0; targetMarginPanel.add(target_margin, tmg_gbc);
        tmg_gbc.gridx = 1; tmg_gbc.gridy = 0; tmg_gbc.fill = GridBagConstraints.HORIZONTAL; tmg_gbc.weightx = 1.0; targetMarginPanel.add(targetMarginField, tmg_gbc);
        tmg_gbc.gridx = 2; tmg_gbc.gridy = 0; tmg_gbc.fill = GridBagConstraints.HORIZONTAL; tmg_gbc.weightx = 1.0; targetMarginPanel.add(setButton, tmg_gbc);

        // --- Create a JPanel for Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add Item");
        toSalesButton = new JButton("To Sales");
        buttonPanel.add(addButton);
        buttonPanel.add(toSalesButton);
        addButton.addActionListener(this);
        toSalesButton.addActionListener(this);

        // --- Combine Product Origin and Target Margin Panels side-by-side ---
        JPanel originAndMarginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); // 10px horizontal gap
        originAndMarginPanel.add(productOriginPanel);
        originAndMarginPanel.add(targetMarginPanel);


        // --- Combine Product Details, Combined Origin/Margin, and Buttons into one input section ---
        JPanel inputFormsAndButtonsPanel = new JPanel(new BorderLayout(10, 10));
        inputFormsAndButtonsPanel.add(productInfoPanel, BorderLayout.CENTER); // Product details in center
        inputFormsAndButtonsPanel.add(originAndMarginPanel, BorderLayout.SOUTH); // Combined origin/margin below
        inputFormsAndButtonsPanel.add(buttonPanel, BorderLayout.EAST); // Button panel to the east (right side)


        // --- Setup the JTable ---
        inventoryTableModel = new InventoryTableModel();

        // Dummy Data (assuming targetMargin is already initialized, e.g., to 0.25)
        inventoryTableModel.addDrugData(new DrugData(
                1001, "Unilab Pharma", "Purchased", "Biogesic", "Paracetamol",
                100, "Tablet", "11/30/2026", 150.00, 1.50, 2.50, targetMargin
        ));
        inventoryTableModel.addDrugData(new DrugData(
                1002, "Pascual Lab", "Consigned", "Ascof Forte", "Lagundi",
                50, "Capsule", "08/15/2025", 250.00, 5.00, 8.00, targetMargin
        ));
        inventoryTableModel.addDrugData(new DrugData(
                1003, "RiteMed Phils.", "Purchased", "Neozep Forte", "Phenylephrine HCl",
                75, "Tablet", "02/01/2027", 200.00, 2.67, 4.50, targetMargin
        ));
        inventoryTableModel.addDrugData(new DrugData(
                1004, "Mundipharma", "Purchased", "Betadine", "Povidone-Iodine",
                20, "Bottle", "05/10/2026", 300.00, 15.00, 25.00, targetMargin
        ));
        inventoryTableModel.addDrugData(new DrugData(
                1005, "Servier Phils.", "Consigned", "Daflon", "Diosmin/Hesperidin",
                40, "Tablet", "07/25/2025", 800.00, 20.00, 35.00, targetMargin
        ));
        inventoryTableModel.addDrugData(new DrugData(
                1006, "GSK Philippines", "Purchased", "Amoclav", "Amoxicillin-Clavulanic Acid",
                60, "Tablet", "09/05/2025", 600.00, 10.00, 18.00, targetMargin
        ));
        inventoryTableModel.addDrugData(new DrugData(
                1007, "Bayer Phils.", "Consigned", "Canesten", "Clotrimazole",
                10, "Tube", "04/22/2026", 180.00, 18.00, 30.00, targetMargin
        ));

        inventoryTable = new JTable(inventoryTableModel);
        inventoryTable.setFillsViewportHeight(true);
        inventoryTable.setAutoCreateRowSorter(true);
        inventoryTable.getTableHeader().setReorderingAllowed(false);

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

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setPreferredSize(new Dimension(850, 200));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

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


        // --- Main layout panel for the entire frame content ---
        JPanel mainLayoutPanel = new JPanel(new BorderLayout(10, 10));
        mainLayoutPanel.add(searchPanel, BorderLayout.NORTH);
        mainLayoutPanel.add(scrollPane, BorderLayout.CENTER);
        mainLayoutPanel.add(inputFormsAndButtonsPanel, BorderLayout.SOUTH);

        this.add(mainLayoutPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    // --- actionPerformed method to handle button clicks ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String expDateStr = expField.getText();
            Date parsedExpirationDate = null; // Will hold the parsed Date object

            try {
                // Use the class-level DATE_FORMAT for parsing
                DATE_FORMAT.setLenient(false); // Make parsing strict
                parsedExpirationDate = DATE_FORMAT.parse(expDateStr);
            } catch (ParseException ex) {
                // If parsing fails, show an error and STOP the process
                JOptionPane.showMessageDialog(this, "Invalid date format for Expiration Date. Please use MM/DD/YYYY.", "Date Input Error", JOptionPane.ERROR_MESSAGE);
                return; // *** IMPORTANT: Exit the method here ***
            }

            try {
                // 1. Get data from input fields
                String vendor = vendorField.getText();
                String brand = brandField.getText();
                String generic = genericField.getText();
                String expDate = expField.getText();
                String uom = uomField.getText();
                String cpType = "";

                if (c.isSelected()) {
                    cpType = "Consigned";
                } else if (p.isSelected()) {
                    cpType = "Purchased";
                } else {
                    JOptionPane.showMessageDialog(this, "Please select Product Origin (Consigned/Purchased).", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Basic validation for non-numeric fields
                if (vendor.isEmpty() || brand.isEmpty() || generic.isEmpty() || expDate.isEmpty() || uom.isEmpty() || targetMarginField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all text fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (targetMargin == null || targetMarginField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please set the Target Margin before adding a drug.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                // Parse numerical fields, handle potential errors
                int batchNum = Integer.parseInt(batchField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                double purchaseC = Double.parseDouble(prchsField.getText());
                double costPU = Double.parseDouble(costPerUnitField.getText());
                double sellingP = Double.parseDouble(sellingPriceField.getText());


                // 2. Create new DrugData object (Temporarily without targetMargin if DrugData doesn't have it yet)
                // For now, I'm passing a dummy 0.0 for targetMargin if DrugData doesn't support it yet
                DrugData newDrug = new DrugData(
                        batchNum, vendor, cpType, brand, generic, qty,
                        uom, expDate, purchaseC, costPU, sellingP, targetMargin
                );

                // 3. Add to table model
                inventoryTableModel.addDrugData(newDrug);

                // 4. Clear input fields for next entry
                clearInputFields();

                JOptionPane.showMessageDialog(this, "Drug data added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format for Batch #, Quantity, Costs, Selling Price, or Target Margin. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else if (e.getSource() == setButton) {
            try {
                double marginValue = Double.parseDouble(targetMarginField.getText());
                targetMargin = new TargetMargin(marginValue);
                // Update suggested price for existing drugs when target margin changes
                for (int i = 0; i < inventoryTableModel.getRowCount(); i++) {
                    DrugData drug = inventoryTableModel.drugDataList.get(i);
                    drug.setTargetMargin(targetMargin);
                    inventoryTableModel.fireTableCellUpdated(i, 11); // 11 is the Suggested Selling Price column
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format for Target Margin. Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == toSalesButton) {
            SalesFrame salesFrame = new SalesFrame(this.inventoryTableModel);
            salesFrame.setVisible(true);
        }
    }

    // Helper method to clear input fields
    private void clearInputFields() {
        vendorField.setText("");
        brandField.setText("");
        genericField.setText("");
        batchField.setText("");
        expField.setText("");
        uomField.setText("");
        qtyField.setText("");
        prchsField.setText("");
        costPerUnitField.setText("");
        sellingPriceField.setText("");
        productOriginGroup.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryFrame::new);
    }
}