package cn.ecut.tomastu.ui;

import cn.ecut.tomastu.tableModel.OrdersTM;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrdersAddWindow {
    public static void main(JTable oriTable, JFrame fatherFrame, String operator, String[] args) {
        JFrame ordersUpdateFrame = new JFrame("add");
        ordersUpdateFrame.setLocationRelativeTo(fatherFrame);
        ordersUpdateFrame.setSize(300, 300);
        ordersUpdateFrame.setResizable(false);

        GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setVgap(10);
        JPanel addPanel = new JPanel(gridLayout);
        addPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        Box flowerName = Box.createHorizontalBox();
        JTextField flowerNameTF = new JTextField();
        flowerName.add(new JLabel("flower name:"));
        flowerName.add(Box.createHorizontalStrut(10));
        flowerName.add(flowerNameTF);

        Box amount = Box.createHorizontalBox();
        JTextField amountTF = new JTextField("0");
        amount.add(new JLabel("amount:         "));
        amount.add(Box.createHorizontalStrut(10));
        amount.add(amountTF);

        Box discount = Box.createHorizontalBox();
        JTextField discountTF = new JTextField("0.00");
        discount.add(new JLabel("discount:       "));
        discount.add(Box.createHorizontalStrut(10));
        discount.add(discountTF);

        Box ordersAdd = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("add");
        JButton cancelBtn = new JButton("cancel");

        confirmBtn.addActionListener(e -> {
            String ordersFlowerName = flowerNameTF.getText().trim();
            long ordersAmount = 0;
            double ordersDiscount = 0;
            try {
                ordersAmount = Long.parseLong(amountTF.getText().trim());
                ordersDiscount = Double.parseDouble(discountTF.getText().trim());
            } catch (NumberFormatException exp) {
                JOptionPane.showMessageDialog(ordersUpdateFrame, "DO NOT ENTER INVALIDED VALUE\n" +
                        "(E.G. : NOT-NUM VALUE，EMPTY VALUE...", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }
            if (ordersAmount <= 0 || ordersDiscount < 0) {
                JOptionPane.showMessageDialog(ordersUpdateFrame, "DO NOT ENTER INVALIDED VALUE! \n(RANGE : " +
                        "EQUAL OR LARGER THAN 0 IN DISCOUNT, LARGER THAN 0 IN AMOUNT) ", "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
            } else if (ordersFlowerName.equals("")) {
                JOptionPane.showMessageDialog(ordersUpdateFrame, "PLEASE ENTER FLOWER NAME ", "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
            } else if (SqlUtils.queryFlowerUnique(ordersFlowerName)) {
                JOptionPane.showMessageDialog(ordersUpdateFrame, "FLOWER DOESN'T EXIST! ", "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
            } else if (SqlUtils.queryFlowerStorage(ordersFlowerName) < ordersAmount) {
                JOptionPane.showMessageDialog(ordersUpdateFrame, "ORDERS AMOUNT LARGER THAN STORAGE! ", "ERROR!",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                SqlUtils.addOrders(ordersFlowerName, ordersAmount, ordersDiscount, operator);
                SqlUtils.updateFlowerStorage(ordersFlowerName, ordersAmount);
                JOptionPane.showMessageDialog(ordersUpdateFrame, "add success!", "success!",
                        JOptionPane.INFORMATION_MESSAGE);
                oriTable.setModel(new OrdersTM());
                ordersUpdateFrame.dispose();
            }
        });

        cancelBtn.addActionListener(e -> {
            ordersUpdateFrame.dispose();
        });

        ordersAdd.add(Box.createHorizontalStrut(40));
        ordersAdd.add(confirmBtn);
        ordersAdd.add(Box.createHorizontalStrut(40));
        ordersAdd.add(cancelBtn);
        ordersAdd.add(Box.createHorizontalStrut(40));
        addPanel.add(flowerName);
        addPanel.add(amount);
        addPanel.add(discount);
        addPanel.add(ordersAdd);

        ordersUpdateFrame.add(addPanel);

        ordersUpdateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ordersUpdateFrame.setVisible(true);
    }
}
