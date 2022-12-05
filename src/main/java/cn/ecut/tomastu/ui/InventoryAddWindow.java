package cn.ecut.tomastu.ui;

import cn.ecut.tomastu.tableModel.InventoryTM;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InventoryAddWindow {
    public static void main(JTable oriTable, JFrame fatherFrame, String[] args) {
        JFrame inventoryUpdateFrame = new JFrame("add");
        inventoryUpdateFrame.setLocationRelativeTo(fatherFrame);
        inventoryUpdateFrame.setSize(300, 300);
        inventoryUpdateFrame.setResizable(false);

        GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setVgap(10);
        JPanel addPanel = new JPanel(gridLayout);
        addPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        Box flowerName = Box.createHorizontalBox();
        JTextField flowerNameTF = new JTextField();
        flowerName.add(new JLabel("name:     "));
        flowerName.add(Box.createHorizontalStrut(10));
        flowerName.add(flowerNameTF);

        Box flowerPrice = Box.createHorizontalBox();
        JTextField flowerPriceTF = new JTextField();
        flowerPrice.add(new JLabel("price:      "));
        flowerPrice.add(Box.createHorizontalStrut(10));
        flowerPrice.add(flowerPriceTF);

        Box flowerStorage = Box.createHorizontalBox();
        JTextField flowerStorageTF = new JTextField();
        flowerStorage.add(new JLabel("storage: "));
        flowerStorage.add(Box.createHorizontalStrut(10));
        flowerStorage.add(flowerStorageTF);

        Box flowerAdd = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("add");
        JButton cancelBtn = new JButton("cancel");

        confirmBtn.addActionListener(e -> {
            String newName = flowerNameTF.getText().trim();
            double newPrice = 0;
            long newStorage = 0;
            try {
                newPrice = Double.parseDouble(flowerPriceTF.getText().trim());
                newStorage = Long.parseLong(flowerStorageTF.getText().trim());
            } catch (NumberFormatException exp) {
                JOptionPane.showMessageDialog(inventoryUpdateFrame, "DO NOT ENTER INVALIDED VALUE\n" +
                        "(E.G. : NOT-NUM VALUE，EMPTY VALUE...", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            if (newPrice < 0 || newStorage < 0) {
                JOptionPane.showMessageDialog(inventoryUpdateFrame, "DO NOT ENTER INVALIDED VALUE! \n(RANGE : " +
                        "EQUAL OR LARGER THAN 0) ", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            if (SqlUtils.queryFlowerUnique(newName)) {
                SqlUtils.addFlower(newName, newPrice, newStorage);
                JOptionPane.showMessageDialog(inventoryUpdateFrame, "update success!", "success!",
                            JOptionPane.INFORMATION_MESSAGE);
                oriTable.setModel(new InventoryTM());
                inventoryUpdateFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(inventoryUpdateFrame, "there is already a flower name \""
                        + flowerNameTF.getText() + "\"!\nupdate failed!", "failed!", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> {
            inventoryUpdateFrame.dispose();
        });

        flowerAdd.add(Box.createHorizontalStrut(40));
        flowerAdd.add(confirmBtn);
        flowerAdd.add(Box.createHorizontalStrut(40));
        flowerAdd.add(cancelBtn);
        flowerAdd.add(Box.createHorizontalStrut(40));
        addPanel.add(flowerName);
        addPanel.add(flowerPrice);
        addPanel.add(flowerStorage);
        addPanel.add(flowerAdd);

        inventoryUpdateFrame.add(addPanel);

        inventoryUpdateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        inventoryUpdateFrame.setVisible(true);
    }
}
