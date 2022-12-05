package cn.ecut.tomastu.ui;

import cn.ecut.tomastu.tableModel.InventoryTM;
import cn.ecut.tomastu.tableModel.OrdersTM;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;

public class ManageWindow {
    public static void main(String[] args) {
        // init TableModel
        InventoryTM inventoryTM = new InventoryTM();
        OrdersTM ordersTM = new OrdersTM();

        // frame
        JFrame manageFrame = new JFrame("Flower Manager : (username)" + args[0]);
        manageFrame.setSize(800, 500);
        manageFrame.setLocationRelativeTo(null);
        manageFrame.setResizable(false);

        // internal frame
        // menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuMenu = new JMenu("Menu");
        JMenuItem about = new JMenuItem("About");
        JMenuItem exit = new JMenuItem("Exit");

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        GridBagConstraints c = null;
//        inventoryPanel.setBackground(Color.BLUE);
        JPanel ordersPanel = new JPanel(new BorderLayout());
//        ordersPanel.setBackground(Color.GREEN);

        tabbedPane.addTab("inventory", inventoryPanel);
        tabbedPane.addTab("order", ordersPanel);

        // assembled menu & menuItem
        menuMenu.add(about);
        menuMenu.add(exit);
        menuBar.add(menuMenu);

        // assembled internal frame to frame
        manageFrame.setJMenuBar(menuBar);
        manageFrame.add(tabbedPane);

        // tab1 panel
        JLabel inventorySearchLabel = new JLabel("search ");
        JTextField inventorySearchTF = new JTextField();
        JButton inventorySearchBtn = new JButton("search");
        Box inventoryPanelBorderNorth = Box.createHorizontalBox();
        inventoryPanelBorderNorth.add(inventorySearchLabel);
        inventoryPanelBorderNorth.add(inventorySearchTF);
        inventoryPanelBorderNorth.add(inventorySearchBtn);
        inventoryPanel.add(inventoryPanelBorderNorth, BorderLayout.NORTH);

        JButton inventoryBtnAdd = new JButton("add");
        JButton inventoryBtnUpdate = new JButton("update");
        JButton inventoryBtnRemove = new JButton("remove");
        JPanel inventoryPanelBorderSouth = new JPanel(new GridLayout(1, 3));
        inventoryPanelBorderSouth.add(inventoryBtnAdd);
        inventoryPanelBorderSouth.add(inventoryBtnUpdate);
        inventoryPanelBorderSouth.add(inventoryBtnRemove);
        inventoryPanel.add(inventoryPanelBorderSouth, BorderLayout.SOUTH);

        JTable inventoryTable = new JTable(inventoryTM);
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryTable);
        inventoryPanel.add(inventoryScrollPane, BorderLayout.CENTER);

        // tab2 panel
        JLabel ordersSearchLabel = new JLabel("search ");
        JTextField ordersSearchTF = new JTextField();
        JButton ordersSearchBtn = new JButton("search");
        Box ordersPanelBorderNorth = Box.createHorizontalBox();
        ordersPanelBorderNorth.add(ordersSearchLabel);
        ordersPanelBorderNorth.add(ordersSearchTF);
        ordersPanelBorderNorth.add(ordersSearchBtn);
        ordersPanel.add(ordersPanelBorderNorth, BorderLayout.NORTH);

        JButton ordersBtnAdd = new JButton("add");
        JButton ordersBtnUpdate = new JButton("update");
        JButton ordersBtnRemove = new JButton("remove");
        JPanel ordersBorderSouth = new JPanel(new GridLayout(1, 3));
        ordersBorderSouth.add(ordersBtnAdd);
        ordersBorderSouth.add(ordersBtnUpdate);
        ordersBorderSouth.add(ordersBtnRemove);
        ordersPanel.add(ordersBorderSouth, BorderLayout.SOUTH);

        JTable ordersTable = new JTable(ordersTM);
        JScrollPane ordersScrollPane = new JScrollPane(ordersTable);
        ordersPanel.add(ordersScrollPane, BorderLayout.CENTER);

        // set listener
        // menuItem
        about.addActionListener(e -> JOptionPane.showMessageDialog(manageFrame, "Author：Tu Weiwen\nClass：2021820\nStudentId：2020216001", "About",
                JOptionPane.INFORMATION_MESSAGE));

        exit.addActionListener(e -> manageFrame.dispose());

        // tab1 panel component's listener
        inventorySearchBtn.addActionListener(e -> {
            String queryString = inventorySearchTF.getText().trim();
            InventoryTM newTm;
            if (!queryString.equals("")) {
                newTm = new InventoryTM(SqlUtils.getFlowerByName(queryString));
            } else {
                newTm = new InventoryTM();
            }
            inventoryTable.setModel(newTm);
        });

        inventoryBtnAdd.addActionListener(e -> {
            InventoryAddWindow.main(inventoryTable, manageFrame, null);
        });

        inventoryBtnUpdate.addActionListener(e -> {
            int index = inventoryTable.getSelectedRow();
            if (index >= 0) {
                InventoryUpdateWindow.main(inventoryTM.getIndexData(index), inventoryTable, manageFrame, null);
            } else {
                JOptionPane.showMessageDialog(manageFrame, "Please select one data!", "CAUTION!", JOptionPane.ERROR_MESSAGE);
            }
        });

        inventoryBtnRemove.addActionListener(e -> {
            int deleteConfirm = JOptionPane.showConfirmDialog(manageFrame, "confirm to delete this data?", "CAUTION!", JOptionPane.YES_NO_OPTION);
            if (deleteConfirm == JOptionPane.YES_OPTION) {
                // BUG(单次会话中添加中文对象后再对中文进行删除会出现无法删除的问题)
//                System.out.println("getSelectedRow(): " + inventoryTable.getSelectedRow());
//                System.out.println("getIndexData(): " + inventoryTM.getIndexData(inventoryTable.getSelectedRow()));
//                System.out.println("getName(): " + inventoryTM.getIndexData(inventoryTable.getSelectedRow()).getName());
                SqlUtils.deleteFlowerByName(inventoryTM.getIndexData(inventoryTable.getSelectedRow()).getName());
            }
            inventoryTable.setModel(new InventoryTM());
        });

        //tab2 panel component's listener
        ordersSearchBtn.addActionListener(e -> {
            String queryString = ordersSearchTF.getText().trim();
            OrdersTM newTM;
            if (!queryString.equals("")) {
                newTM = new OrdersTM(SqlUtils.getOrdersById(queryString));
            } else {
                newTM = new OrdersTM();
            }
            ordersTable.setModel(newTM);
        });

        ordersBtnAdd.addActionListener(e -> {
            OrdersAddWindow.main(ordersTable, manageFrame, args[0], null);
        });

        ordersBtnUpdate.addActionListener(e -> {
            int index = ordersTable.getSelectedRow();
            if (index >= 0) {
                OrdersUpdateWindow.main(ordersTM.getIndexData(index), ordersTable, manageFrame, null);
            } else {
                JOptionPane.showMessageDialog(manageFrame, "Please select one data!", "CAUTION!", JOptionPane.ERROR_MESSAGE);
            }
        });

        // bug(偶发性出现数据无法删除)
        ordersBtnRemove.addActionListener(e -> {
            int deleteConfirm = JOptionPane.showConfirmDialog(manageFrame, "confirm to delete this data?", "CAUTION!", JOptionPane.YES_NO_OPTION);
            if (deleteConfirm == JOptionPane.YES_OPTION) {
                // BUG(单次会话中添加中文对象后再对中文进行删除会出现无法删除的问题)
//                System.out.println("getSelectedRow(): " + inventoryTable.getSelectedRow());
//                System.out.println("getIndexData(): " + inventoryTM.getIndexData(inventoryTable.getSelectedRow()));
//                System.out.println("getName(): " + inventoryTM.getIndexData(inventoryTable.getSelectedRow()).getName());
//                SqlUtils.deleteFlowerByName(inventoryTM.getIndexData(inventoryTable.getSelectedRow()).getName());
                SqlUtils.deleteOrdersById(ordersTM.getIndexData(ordersTable.getSelectedRow()).getOrdersId());
            }
            ordersTable.setModel(new OrdersTM());
        });

        // set exit & visible
        manageFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        manageFrame.setVisible(true);
    }
}
