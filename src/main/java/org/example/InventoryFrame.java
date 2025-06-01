package org.example;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InventoryFrame extends JFrame {
    JLabel vendorName, brandName, genericName, batchNumber, expirationDate, unitOfMeasure,
            quantity, purchaseCost, costPerUnitofMeasure, sellingPrice;
    JTextField vendorField, brandField, genericField, batchField, expField, uomField, qtyField,
            prchsField, costPerUnitField, sellingPriceField;

    JRadioButton c, p; // Now for Consigned and Purchased
    ButtonGroup productOriginGroup; // Renamed for clarity

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

        // --- Create a JPanel for Product Information ---
        JPanel productInfoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding around components
        // Set fill and anchor defaults once if they are common for most components
        gbc.fill = GridBagConstraints.HORIZONTAL; // Make fields fill horizontally
        gbc.anchor = GridBagConstraints.WEST;    // Anchor labels to the west

        productInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Product Details", TitledBorder.LEFT, TitledBorder.TOP));

        // Add components to productInfoPanel using GridBagLayout
        // We'll increment gbc.gridy for each new row of label/field pairs

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

        // Group the radio buttons
        productOriginGroup = new ButtonGroup();
        productOriginGroup.add(c);
        productOriginGroup.add(p);

        // Add radio buttons to their panel
        productOriginPanel.add(c);
        productOriginPanel.add(p);

        // --- Main content panel to hold other panels ---
        JPanel mainContentPanel = new JPanel(new BorderLayout(10, 10));

        // Add the product info panel to the center
        mainContentPanel.add(productInfoPanel, BorderLayout.CENTER);
        // Add the product origin panel to the south
        mainContentPanel.add(productOriginPanel, BorderLayout.SOUTH);

        // Add the main content panel to the JFrame
        this.add(mainContentPanel);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryFrame::new);
    }
}