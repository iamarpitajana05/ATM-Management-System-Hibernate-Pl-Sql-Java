package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.atm.dao.UserDAO;

public class SignupFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField accountNumberField;

    public SignupFrame() {
        setTitle("ATM Sign Up");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 250, 255));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Create New Account", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 70, 140));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Account Number:"), gbc);
        gbc.gridx = 1;
        accountNumberField = new JTextField(15);
        panel.add(accountNumberField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        JButton signupButton = new JButton("Sign Up");
        styleButton(signupButton, new Color(60, 179, 113));
        gbc.gridy = 4;
        gbc.gridx = 0;
        panel.add(signupButton, gbc);

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(100, 149, 237));
        gbc.gridx = 1;
        panel.add(backButton, gbc);

        signupButton.addActionListener((ActionEvent e) -> handleSignup());
        backButton.addActionListener((ActionEvent e) -> {
            new WelcomeFrame().setVisible(true);
            dispose();
        });

        add(panel);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }

    private void handleSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        int accountNumber;

        try {
            accountNumber = Integer.parseInt(accountNumberField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid account number. Please enter a valid number.");
            return;
        }

        UserDAO userDAO = new UserDAO();
        userDAO.registerUser(username, password, accountNumber);

        JOptionPane.showMessageDialog(this, "Signup successful!");
        new LoginFrame().setVisible(true);
        dispose();
    }
}
