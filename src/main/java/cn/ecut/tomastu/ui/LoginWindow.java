package cn.ecut.tomastu.ui;

import cn.ecut.tomastu.utils.SqlUtils;

import javax.swing.*;
import java.awt.*;

public class LoginWindow {

    public static void main(String[] args) {
        // frame
        JFrame loginFrame = new JFrame("FlowerManager : Login");

        loginFrame.setSize(500, 300);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setResizable(false);
//        ImageIcon icon = new ImageIcon(getClass().getResource())

        // internal frame
        // menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuMenu = new JMenu("Menu");
        JMenuItem about = new JMenuItem("About");
        JMenuItem exit = new JMenuItem("Exit");

        // component
        JLabel usernameLabel = new JLabel("username");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("password    ");
        JPasswordField passwordField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        // assembled component to box
        Box usernameBox = Box.createHorizontalBox();
        usernameBox.add(Box.createHorizontalStrut(90));
        usernameBox.add(usernameLabel);
        usernameBox.add(Box.createHorizontalStrut(10));
        usernameBox.add(usernameField);
        usernameBox.add(Box.createHorizontalStrut(90));

        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(Box.createHorizontalStrut(90));
        passwordBox.add(passwordLabel);
        usernameBox.add(Box.createHorizontalStrut(10));
        passwordBox.add(passwordField);
        passwordBox.add(Box.createHorizontalStrut(100));

        Box loginBox = Box.createHorizontalBox();
        Component hGlueInLoginL = Box.createHorizontalGlue();
        Component hGlueInLoginR = Box.createHorizontalGlue();
        loginBox.add(hGlueInLoginL);
        loginBox.add(loginBtn);
        loginBox.add(hGlueInLoginR);

        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(75));
        vBox.add(usernameBox);
        vBox.add(Box.createVerticalStrut(5));
        vBox.add(passwordBox);
        vBox.add(Box.createVerticalStrut(10));
        vBox.add(loginBox);
        vBox.add(Box.createVerticalStrut(75));

        // assembled menu & menuItem
        menuMenu.add(about);
        menuMenu.add(exit);
        menuBar.add(menuMenu);

        // assembled internal frame to frame
        loginFrame.setJMenuBar(menuBar);
        loginFrame.setContentPane(vBox);


        // set listener
        // menuItem
        about.addActionListener(e -> JOptionPane.showMessageDialog(loginFrame, "Author：Tu Weiwen\nClass：2021820\nStudentId：2020216001", "About",
                JOptionPane.INFORMATION_MESSAGE));
        exit.addActionListener(e -> loginFrame.dispose());
        // button
        loginBtn.addActionListener(e -> {
            char[] passwordCA = passwordField.getPassword();
            StringBuilder sb = new StringBuilder();
            for (char c : passwordCA) {
                sb.append(c);
            }
            if (SqlUtils.login(usernameField.getText().trim(), sb.toString())) {
                JOptionPane.showMessageDialog(loginFrame, "login successful!", "result", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
                ManageWindow.main(new String[]{usernameField.getText()});
            } else {
                JOptionPane.showMessageDialog(loginFrame, "login failed!", "result", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // set exit & visible
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setVisible(true);
    }
}
