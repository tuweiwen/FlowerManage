package cn.ecut.tomastu.ui;

import cn.ecut.tomastu.bean.Orders;
import cn.ecut.tomastu.tableModel.OrdersTM;
import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class OrdersUpdateWindow {
    public static void main(Orders updateData, JTable oriTable, JFrame fatherFrame, String[] args) {
        JFrame ordersUpdateFrame = new JFrame("update");
        ordersUpdateFrame.setLocationRelativeTo(fatherFrame);
        ordersUpdateFrame.setSize(300, 300);
        ordersUpdateFrame.setResizable(false);

        GridLayout gridLayout = new GridLayout(4, 1);
        gridLayout.setVgap(10);
        JPanel updatePanel = new JPanel(gridLayout);
        updatePanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        Box flowerName = Box.createHorizontalBox();
        JTextField flowerNameTF = new JTextField(updateData.getFlowerName());
        flowerName.add(new JLabel("name:     "));
        flowerName.add(Box.createHorizontalStrut(10));
        flowerName.add(flowerNameTF);

        Box amount = Box.createHorizontalBox();
        JTextField amountTF = new JTextField(Long.toString(updateData.getAmount()));
        amount.add(new JLabel("amount:         "));
        amount.add(Box.createHorizontalStrut(10));
        amount.add(amountTF);

        Box discount = Box.createHorizontalBox();
        JTextField discountTF = new JTextField(Double.toString(updateData.getDiscount()));
        discount.add(new JLabel("discount:       "));
        discount.add(Box.createHorizontalStrut(10));
        discount.add(discountTF);

        Box ordersUpdate = Box.createHorizontalBox();
        JButton confirmBtn = new JButton("update");
        JButton cancelBtn = new JButton("cancel");

        confirmBtn.addActionListener(e -> {
            String ordersFlowerName = flowerNameTF.getText().trim();
            long ordersAmount = 0;
            double ordersDiscount = 0;
            try {
                ordersAmount = Long.parseLong(amountTF.getText().trim());
                ordersDiscount = Double.parseDouble(discountTF.getText().trim());

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
                } else {
                    SqlUtils.updateOrdersAll(updateData.getOrdersId(), ordersFlowerName, ordersAmount, ordersDiscount);
                    JOptionPane.showMessageDialog(ordersUpdateFrame, "update success!", "success!",
                            JOptionPane.INFORMATION_MESSAGE);
                    oriTable.setModel(new OrdersTM());
                    ordersUpdateFrame.dispose();
                }
            } catch (NumberFormatException exp) {
                JOptionPane.showMessageDialog(ordersUpdateFrame, "DO NOT ENTER INVALIDED VALUE\n" +
                        "(E.G. : NOT-NUM VALUE，EMPTY VALUE...", "ERROR!", JOptionPane.ERROR_MESSAGE);
            }

        });

        cancelBtn.addActionListener(e -> {
            ordersUpdateFrame.dispose();
        });

        ordersUpdate.add(Box.createHorizontalStrut(40));
        ordersUpdate.add(confirmBtn);
        ordersUpdate.add(Box.createHorizontalStrut(40));
        ordersUpdate.add(cancelBtn);
        ordersUpdate.add(Box.createHorizontalStrut(40));
        updatePanel.add(flowerName);
        updatePanel.add(amount);
        updatePanel.add(discount);
        updatePanel.add(ordersUpdate);

        ordersUpdateFrame.add(updatePanel);

        ordersUpdateFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        ordersUpdateFrame.setVisible(true);
    }
}
