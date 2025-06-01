package org.example;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class InventoryTableModel extends AbstractTableModel {
    ArrayList<DrugData> drugDataList;
    String[] header = {"Batch #", "Vendor Name", "C/P", "Brand Name", "Generic Name", "Quantity", "Unit of Measure",
                            "Exp Date", "Purchase Cost", "Cost/Unit", "Selling Price"};

    public InventoryTableModel() {
        this.drugDataList = new ArrayList<>();
    }
    public void addDrugData(DrugData drug) {
        drugDataList.add(drug);
        fireTableRowsInserted(drugDataList.size() - 1, drugDataList.size() - 1);
    }

    @Override
    public int getRowCount() {
        return drugDataList.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }


    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        DrugData drug = drugDataList.get(rowIndex);

        switch (columnIndex) {
            case 0: return drug.getBatch_number();
            case 1: return drug.getVendor_name();
            case 2: return drug.getC_p();
            case 3: return drug.getBrand_name();
            case 4: return drug.getGeneric_name();
            case 5: return drug.getQuantity();
            case 6: return drug.getUnit_of_measure();
            case 7: return drug.getExp_date();
            case 8: return drug.getPurchase_cost();
            case 9: return drug.getCostPunit();
            case 10: return drug.getSelling_price();
            default: return null;
        }
    }

}
