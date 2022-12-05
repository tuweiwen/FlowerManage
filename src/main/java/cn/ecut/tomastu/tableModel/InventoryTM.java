package cn.ecut.tomastu.tableModel;

import cn.ecut.tomastu.bean.Flower;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

public class InventoryTM extends AbstractTableModel {
    private ArrayList<String> columnName;
    private LinkedList<Flower> inventoryData;

    public InventoryTM() {
        try {
            Field[] declaredFields = Class.forName("cn.ecut.tomastu.bean.Flower").getDeclaredFields();
            columnName = new ArrayList<>();
            for (Field f : declaredFields) {
                columnName.add(f.getName());
            }
            inventoryData = SqlUtils.getFlowerAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InventoryTM(LinkedList<Flower> newData) {
        try {
            Field[] declaredFields = Class.forName("cn.ecut.tomastu.bean.Flower").getDeclaredFields();
            columnName = new ArrayList<>();
            for (Field f : declaredFields) {
                columnName.add(f.getName());
            }
            inventoryData = newData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return inventoryData.size();
    }

    @Override
    public int getColumnCount() {
        try {
            return Class.forName("cn.ecut.tomastu.bean.Flower").getDeclaredFields().length;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnName.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnName.get(columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Flower temp = inventoryData.get(rowIndex);
        switch (getColumnName(columnIndex)) {
            case "name":
                return temp.getName();
            case "price":
                return temp.getPrice();
            case "storage":
                return temp.getStorage();
        }
        return null;
    }

    public Flower getIndexData(int index) {
        return inventoryData.get(index);
    }
}
