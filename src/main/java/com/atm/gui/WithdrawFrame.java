package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.atm.dao.UserDAO;
import com.atm.model.User;

public class WithdrawFrame extends JFrame {
    private final User user;
    private JTextField amountField;

    public WithdrawFrame(User user) {
        this.user = user;
        setTitle("Withdraw Money");
        setSize(420, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(255, 250, 240));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Withdraw Funds", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(139, 0, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(new JLabel("Enter Amount:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(15);
        panel.add(amountField, gbc);

        JButton withdrawButton = new JButton("Withdraw");
        styleButton(withdrawButton, new Color(220, 20, 60));

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(100, 149, 237));

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(withdrawButton, gbc);

        gbc.gridx = 1;
        panel.add(backButton, gbc);

        withdrawButton.addActionListener(e -> handleWithdraw());
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

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                throw new NumberFormatException();
            }

            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.withdrawMoney(user.getAccountNumber(), amount);

            if (success) {
                JOptionPane.showMessageDialog(this, "Withdrawal successful!");
                new TransactionMenuFrame(user).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance or transaction failed!", "Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive amount.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
        }
    }
}
