package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SalesFrame extends JFrame implements ActionListener {
    JLabel searchLabel;
    JTextField searchField;
    JButton addButton, removeButton, checkoutButton;
    Container container;
    FlowLayout layout;

    public SalesFrame() throws HeadlessException {
        super("Sales Management");

        container = this.getContentPane();
        layout = new FlowLayout(FlowLayout.LEFT);
        container.setLayout(layout);


        searchLabel = new JLabel("Search:");
        searchField = new JTextField(25);

        addButton = new JButton("Add Item");
        removeButton = new JButton("Remove Item");
        checkoutButton = new JButton("Checkout");








        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //to be changed to DISPOSE ON CLOSE
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        SalesFrame salesFrame = new SalesFrame();
    }
}
