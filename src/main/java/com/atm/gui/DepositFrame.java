package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.atm.dao.UserDAO;
import com.atm.model.User;

public class DepositFrame extends JFrame {
    private final User user;
    private JTextField amountField;

    public DepositFrame(User user) {
        this.user = user;
        setTitle("Deposit Money");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(245, 250, 255));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Deposit Funds", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Enter Amount:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(15);
        panel.add(amountField, gbc);

        JButton depositButton = new JButton("Deposit");
        styleButton(depositButton, new Color(60, 179, 113));

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(100, 149, 237));

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(depositButton, gbc);

        gbc.gridx = 1;
        panel.add(backButton, gbc);

        depositButton.addActionListener(e -> handleDeposit());
        backButton.addActionListener(e -> {
            new TransactionMenuFrame(user).setVisible(true);
            dispose();
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                throw new NumberFormatException();
            }

            UserDAO userDAO = new UserDAO();
            userDAO.depositMoney(user.getAccountNumber(), amount);

            JOptionPane.showMessageDialog(this, "Deposit successful!");
            new TransactionMenuFrame(user).setVisible(true);
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }
}
