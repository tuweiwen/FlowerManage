package cn.ecut.tomastu.tableModel;

import cn.ecut.tomastu.bean.Orders;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;

public class OrdersTM extends AbstractTableModel {
    private ArrayList<String> columnName;
    private LinkedList<Orders> ordersData;

    public OrdersTM() {
        try {
            Field[] declaredFields = Class.forName("cn.ecut.tomastu.bean.Orders").getDeclaredFields();
            columnName = new ArrayList<>();
            for (Field f : declaredFields) {
                columnName.add(f.getName());
            }
            ordersData = SqlUtils.getOrdersAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OrdersTM(LinkedList<Orders> newData) {
        try {
            Field[] declaredFields = Class.forName("cn.ecut.tomastu.bean.Orders").getDeclaredFields();
            columnName = new ArrayList<>();
            for (Field f : declaredFields) {
                columnName.add(f.getName());
            }
            ordersData = newData;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRowCount() {
        return ordersData.size();
    }

    @Override
    public int getColumnCount() {
        try {
            return Class.forName("cn.ecut.tomastu.bean.Orders").getDeclaredFields().length;
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
        Orders temp = ordersData.get(rowIndex);
        switch (getColumnName(columnIndex)) {
            case "ordersId":
                return temp.getOrdersId();
            case "ordersDate":
                return temp.getOrdersDate();
            case "flowerName":
                return temp.getFlowerName();
            case "amount":
                return temp.getAmount();
            case "discount":
                return temp.getDiscount();
            case "operator":
                return temp.getOperator();
        }
        return null;
    }

    public Orders getIndexData(int index) {
        return ordersData.get(index);
    }
}
