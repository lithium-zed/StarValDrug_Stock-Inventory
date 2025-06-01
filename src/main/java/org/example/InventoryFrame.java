package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InventoryFrame extends JFrame implements ActionListener {
    JLabel vendorName, brandName, genericName, batchNumber, expirationDate, unitOfMeasure,
            quantity, purchaseCost, costPerUnitofMeasure, sellingPrice;
    JTextField vendorField, brandField, genericField, batchField, expField, uomField, qtyField,
            prchsField, costPerUnitField, sellingPriceField;

    // Table components - declared as instance variables
    private JTable inventoryTable;
    private InventoryTableModel inventoryTableModel;

    JRadioButton c, p;
    ButtonGroup productOriginGroup;

    private JButton addButton; // Declare the add button

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

        // --- Create a JPanel for Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the button
        addButton = new JButton("Add Item"); // Initialize the button
        buttonPanel.add(addButton);

        // Register this class (InventoryFrame) as the ActionListener for the button
        addButton.addActionListener(this);


        // --- Combine Product Details, Product Origin, and Buttons into one input section ---
        JPanel inputFormsAndButtonsPanel = new JPanel(new BorderLayout(10, 10)); // Use BorderLayout for overall layout
        inputFormsAndButtonsPanel.add(productInfoPanel, BorderLayout.CENTER); // Product details in center
        inputFormsAndButtonsPanel.add(productOriginPanel, BorderLayout.SOUTH); // Product origin below
        inputFormsAndButtonsPanel.add(buttonPanel, BorderLayout.EAST); // Button panel to the east (right side)


        // --- Setup the JTable ---
        inventoryTableModel = new InventoryTableModel(); // Initialize your table model

        // Add some dummy data for testing
        inventoryTableModel.addDrugData(new DrugData(1001, "PharmaCo", "Purchased", "BrandA", "GenericX", 50, "Box", "12/31/2025", 10.50, 0.21, 0.35));
        inventoryTableModel.addDrugData(new DrugData(1002, "MediSupp", "Consigned", "BrandB", "GenericY", 100, "Tablet", "06/15/2024", 0.05, 0.05, 0.10));
        inventoryTableModel.addDrugData(new DrugData(1003, "Health Inc", "Purchased", "BrandC", "GenericZ", 20, "Vial", "01/01/2026", 50.00, 2.50, 4.00));

        inventoryTable = new JTable(inventoryTableModel); // Create the table with your model
        inventoryTable.setFillsViewportHeight(true); // Makes the table fill the height of the scroll pane
        inventoryTable.setAutoCreateRowSorter(true); // Enables sorting by clicking column headers

        // Put the table inside a JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        // Set a preferred size for the scroll pane to help with initial layout
        scrollPane.setPreferredSize(new Dimension(850, 200)); // Adjust width and height as needed

        // --- Main layout panel for the entire frame content ---
        JPanel mainLayoutPanel = new JPanel(new BorderLayout(10, 10)); // Outer BorderLayout with 10px gap

        // --- CHANGE HERE: Table on top, input forms below ---
        mainLayoutPanel.add(scrollPane, BorderLayout.NORTH); // Table (in scroll pane) at the NORTH
        mainLayoutPanel.add(inputFormsAndButtonsPanel, BorderLayout.CENTER); // Input forms and buttons in the CENTER


        // Add the main layout panel to the JFrame
        this.add(mainLayoutPanel);

        this.pack(); // Size the frame to fit its components
        this.setLocationRelativeTo(null); // Center the frame on the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        this.setVisible(true); // Make the frame visible
    }

    // --- actionPerformed method to handle button clicks ---
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) { // Check if the event source is the addButton
            try {
                // 1. Get data from input fields
                String vendor = vendorField.getText();
                String brand = brandField.getText();
                String generic = genericField.getText();
                String expDate = expField.getText();
                String uom = uomField.getText();

                // Radio Button selection
                String cpType = "";
                if (c.isSelected()) {
                    cpType = "Consigned";
                } else if (p.isSelected()) {
                    cpType = "Purchased";
                } else {
                    JOptionPane.showMessageDialog(this, "Please select Product Origin (Consigned/Purchased).", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return; // Stop if no origin selected
                }

                // Basic validation for non-numeric fields
                if (vendor.isEmpty() || brand.isEmpty() || generic.isEmpty() || expDate.isEmpty() || uom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all text fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Parse numerical fields, handle potential errors
                int batchNum = Integer.parseInt(batchField.getText());
                int qty = Integer.parseInt(qtyField.getText());
                double purchaseC = Double.parseDouble(prchsField.getText());
                double costPU = Double.parseDouble(costPerUnitField.getText());
                double sellingP = Double.parseDouble(sellingPriceField.getText());

                // 2. Create new DrugData object
                DrugData newDrug = new DrugData(
                        batchNum, vendor, cpType, brand, generic, qty,
                        uom, expDate, purchaseC, costPU, sellingP
                );

                // 3. Add to table model
                inventoryTableModel.addDrugData(newDrug);

                // 4. Clear input fields for next entry
                clearInputFields();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format for Batch #, Quantity, Costs, or Selling Price. Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); // Print stack trace for debugging
            }
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
        productOriginGroup.clearSelection(); // Deselect radio buttons
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryFrame::new);
    }
}