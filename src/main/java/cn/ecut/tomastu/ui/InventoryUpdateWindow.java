package cn.ecut.tomastu.ui;

import cn.ecut.tomastu.bean.Flower;
import cn.ecut.tomastu.tableModel.InventoryTM;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class InventoryUpdateWindow {
    public static void main(Flower updateData, JTable oriTable, JFrame fatherFrame, String[] args) {
        JFrame inventoryUpdateFrame = new JFrame("update");
        inventoryUpdateFrame.setLocationRelativeTo(fatherFrame);
        inventoryUpdateFrame.setSize(300, 300);
        inventoryUpdateFrame.setResizable(false);

        GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setVgap(10);
        JPanel updatePanel = new JPanel(gridLayout);
        updatePanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        Box flowerName = Box.createHorizontalBox();
        JTextField flowerNameTF = new JTextField(updateData.getName());
        flowerName.add(new JLabel("name:     "));
        flowerName.add(Box.createHorizontalStrut(10));
        flowerName.add(flowerNameTF);

        Box flowerPrice = Box.createHorizontalBox();
        JTextField flowerPriceTF = new JTextField(Double.toString(updateData.getPrice()));
        flowerPrice.add(new JLabel("price:      "));
        flowerPrice.add(Box.createHorizontalStrut(10));
        flowerPrice.add(flowerPriceTF);

        Box flowerStorage = Box.createHorizontalBox();
        JTextField flowerStorageTF = new JTextField(Long.toString(updateData.getStorage()));
        flowerStorage.add(new JLabel("storage: "));
        flowerStorage.add(Box.createHorizontalStrut(10));
        flowerStorage.add(flowerStorageTF);

        Box flowerUpdate = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("update");
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
            if (SqlUtils.queryFlowerUnique(newName) || newName.equals(updateData.getName())) {
                boolean b = SqlUtils.updateFlowerAll(updateData.getName(), newName, newPrice, newStorage);
                if (b) {
                    JOptionPane.showMessageDialog(inventoryUpdateFrame, "update success!", "success!",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(inventoryUpdateFrame, "update failed!", "failed!",
                            JOptionPane.ERROR_MESSAGE);
                }
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

        flowerUpdate.add(Box.createHorizontalStrut(40));
        flowerUpdate.add(confirmBtn);
        flowerUpdate.add(Box.createHorizontalStrut(40));
        flowerUpdate.add(cancelBtn);
        flowerUpdate.add(Box.createHorizontalStrut(40));
        updatePanel.add(flowerName);
        updatePanel.add(flowerPrice);
        updatePanel.add(flowerStorage);
        updatePanel.add(flowerUpdate);

        inventoryUpdateFrame.add(updatePanel);

        inventoryUpdateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        inventoryUpdateFrame.setVisible(true);
    }
}
