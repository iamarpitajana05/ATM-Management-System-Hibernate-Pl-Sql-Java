package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import com.atm.dao.UserDAO;
import com.atm.model.User;

public class MiniStatementFrame extends JFrame {
    private final User user;
    private JTextArea statementArea;

    public MiniStatementFrame(User user) {
        this.user = user;
        setTitle("Mini Statement");
        setSize(550, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(mainPanel);

        JLabel titleLabel = new JLabel("Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(new Color(25, 25, 112));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        statementArea = new JTextArea();
        statementArea.setEditable(false);
        statementArea.setFont(new Font("Courier New", Font.PLAIN, 14));
        statementArea.setBackground(Color.WHITE);
        statementArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JScrollPane scrollPane = new JScrollPane(statementArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        styleButton(backButton, new Color(100, 149, 237));
        mainPanel.add(backButton, BorderLayout.SOUTH);

        displayMiniStatement();

        backButton.addActionListener(e -> {
            new TransactionMenuFrame(user).setVisible(true);
            dispose();
        });
    }

    private void displayMiniStatement() {
        UserDAO userDAO = new UserDAO();
        List<String> transactions = userDAO.getMiniStatementRaw(user.getAccountNumber());

        if (transactions == null || transactions.isEmpty()) {
            statementArea.setText("No recent transactions found.");
        } else {
            StringBuilder statement = new StringBuilder();
            for (String transaction : transactions) {
                statement.append(transaction).append("\n");
            }
            statementArea.setText(statement.toString());
        }
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
    }
}
