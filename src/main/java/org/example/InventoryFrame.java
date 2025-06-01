package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class InventoryFrame extends JFrame {
    JLabel vendorName, brandName, genericName, batchNumber, expirationDate, unitOfMeasure,
            quantity, purchaseCost, costPerUnitofMeasure, sellingPrice;
    JTextField vendorField, brandField, genericField, batchField, expField, uomField, qtyField,
            prchsField, costPerUnitField, sellingPriceField;

    // Table components - declared as instance variables
    private JTable inventoryTable;
    private InventoryTableModel inventoryTableModel;

    JRadioButton c, p;
    ButtonGroup productOriginGroup;

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

        // --- Create a JPanel to hold both input and radio button panels ---
        // This panel will organize the input forms (Product Details + Product Origin)
        JPanel inputSectionPanel = new JPanel(new BorderLayout(10, 10)); // 10px gap
        inputSectionPanel.add(productInfoPanel, BorderLayout.CENTER);
        inputSectionPanel.add(productOriginPanel, BorderLayout.SOUTH);

        // --- Setup the JTable ---
        inventoryTableModel = new InventoryTableModel(); // Initialize your table model

        // Add some dummy data for testing (remove in production)
        inventoryTableModel.addDrugData(new DrugData(1001, "PharmaCo", "Purchased", "BrandA", "GenericX", 50, "Box", "12/31/2025", 10.50, 0.21, 0.35));
        inventoryTableModel.addDrugData(new DrugData(1002, "MediSupp", "Consigned", "BrandB", "GenericY", 100, "Tablet", "06/15/2024", 0.05, 0.05, 0.10));
        inventoryTableModel.addDrugData(new DrugData(1003, "Health Inc", "Purchased", "BrandC", "GenericZ", 20, "Vial", "01/01/2026", 50.00, 2.50, 4.00));
        // You can add more dummy data here

        inventoryTable = new JTable(inventoryTableModel); // Create the table with your model
        inventoryTable.setFillsViewportHeight(true); // Makes the table fill the height of the scroll pane
        inventoryTable.setAutoCreateRowSorter(true); // Enables sorting by clicking column headers

        // Put the table inside a JScrollPane for scrollability
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        // Set a preferred size for the scroll pane to help with initial layout
        scrollPane.setPreferredSize(new Dimension(850, 200)); // Adjust width and height as needed

        // --- Main layout panel for the entire frame content ---
        // This panel uses BorderLayout to place the JScrollPane at the NORTH
        // and the input form section (inputSectionPanel) in the CENTER.
        JPanel mainLayoutPanel = new JPanel(new BorderLayout(10, 10)); // Outer BorderLayout with 10px gap

        mainLayoutPanel.add(scrollPane, BorderLayout.NORTH);
        mainLayoutPanel.add(inputSectionPanel, BorderLayout.CENTER);

        // Add the main layout panel to the JFrame
        this.add(mainLayoutPanel);

        this.pack(); // Size the frame to fit its components
        this.setLocationRelativeTo(null); // Center the frame on the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        this.setVisible(true); // Make the frame visible
    }

    public static void main(String[] args) {
        // Ensure GUI updates are done on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(InventoryFrame::new);
    }
}