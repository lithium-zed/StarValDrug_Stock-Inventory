package org.example;

public class TargetMargin {
    double targetMargin;
    InventoryFrame inventoryFrame;

    public TargetMargin(double targetMargin, InventoryFrame inventoryFrame) {
        this.targetMargin = targetMargin;
        this.inventoryFrame = inventoryFrame;
    }

    public double getTargetMargin() {
        return targetMargin;
    }
}
