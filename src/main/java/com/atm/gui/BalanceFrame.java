package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.atm.dao.UserDAO;
import com.atm.model.User;

public class BalanceFrame extends JFrame {
    private final User user;
    private JLabel balanceLabel;

    public BalanceFrame(User user) {
        this.user = user;
        setTitle("Check Balance");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        panel.setBackground(new Color(240, 248, 255));
        add(panel);

        JLabel title = new JLabel("Your Current Balance", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(new Color(25, 25, 112));
        panel.add(title, BorderLayout.NORTH);

        balanceLabel = new JLabel("Balance: $0.00", SwingConstants.CENTER);
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        panel.add(balanceLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(100, 149, 237));
        panel.add(backButton, BorderLayout.SOUTH);

        displayBalance();

        backButton.addActionListener(e -> {
            new TransactionMenuFrame(user).setVisible(true);
            dispose();
        });
    }

    private void displayBalance() {
        UserDAO userDAO = new UserDAO();
        double balance = userDAO.getBalance(user.getAccountNumber());
        balanceLabel.setText("Balance: $" + String.format("%.2f", balance));
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}
