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

        productInfoPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Product Details", TitledBorder.LEFT, TitledBorder.TOP)); // Changed title

        // Add components to productInfoPanel using GridBagLayout
        // Vendor Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST; productInfoPanel.add(vendorName, gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(vendorField, gbc);

        // Brand Name
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(brandName, gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(brandField, gbc);

        // Generic Name
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(genericName, gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(genericField, gbc);

        // Batch #
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(batchNumber, gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(batchField, gbc);

        // Expiration Date
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(expirationDate, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(expField, gbc);

        // Unit Of Measure
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(unitOfMeasure, gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(uomField, gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(quantity, gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(qtyField, gbc);

        // Purchase Cost
        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(purchaseCost, gbc);
        gbc.gridx = 1; gbc.gridy = 7; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(prchsField, gbc);

        // Cost / Unit of Measure
        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(costPerUnitofMeasure, gbc);
        gbc.gridx = 1; gbc.gridy = 8; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(costPerUnitField, gbc);

        // Selling Price
        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; productInfoPanel.add(sellingPrice, gbc);
        gbc.gridx = 1; gbc.gridy = 9; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0; productInfoPanel.add(sellingPriceField, gbc);

        // --- Create a JPanel for Product Origin Radio Buttons ---
        JPanel productOriginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productOriginPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Product Origin", TitledBorder.LEFT, TitledBorder.TOP)); // Changed title

        c = new JRadioButton("Consigned"); // Label changed
        p = new JRadioButton("Purchased"); // Label changed

        // Group the radio buttons
        productOriginGroup = new ButtonGroup(); // Renamed ButtonGroup variable
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