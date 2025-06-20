package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.atm.model.User;

public class TransactionMenuFrame extends JFrame {
    private final User user;

    public TransactionMenuFrame(User user) {
        this.user = user;
        setTitle("ATM Dashboard");
        setSize(550, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Greeting label
        JLabel greetingLabel = new JLabel("Hello, " + user.getUsername() + " (A/C: " + user.getAccountNumber() + ")");
        greetingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        greetingLabel.setForeground(new Color(0, 102, 153));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(greetingLabel, gbc);

        // Title
        JLabel title = new JLabel("Choose Your Action", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(25, 25, 112));
        gbc.gridy = 1;
        panel.add(title, gbc);

        // Reset gridwidth for buttons
        gbc.gridwidth = 1;

        // Buttons
        JButton depositButton = new JButton("Deposit", getIcon("FileView.floppyDriveIcon"));
        JButton withdrawButton = new JButton("Withdraw", getIcon("OptionPane.warningIcon"));
        JButton balanceButton = new JButton("Check Balance", getIcon("FileView.computerIcon"));
        JButton miniStatementButton = new JButton("Mini Statement", getIcon("FileView.directoryIcon"));
        JButton logoutButton = new JButton("Logout", getIcon("OptionPane.errorIcon"));

        styleButton(depositButton, new Color(60, 179, 113));
        styleButton(withdrawButton, new Color(255, 165, 0));
        styleButton(balanceButton, new Color(100, 149, 237));
        styleButton(miniStatementButton, new Color(123, 104, 238));
        styleButton(logoutButton, new Color(220, 20, 60));

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(depositButton, gbc);
        gbc.gridx = 1;
        panel.add(withdrawButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(balanceButton, gbc);
        gbc.gridx = 1;
        panel.add(miniStatementButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(logoutButton, gbc);

        // Button Actions
        depositButton.addActionListener(e -> {
            new DepositFrame(user).setVisible(true);
            dispose();
        });

        withdrawButton.addActionListener(e -> {
            new WithdrawFrame(user).setVisible(true);
            dispose();
        });

        balanceButton.addActionListener(e -> {
            new BalanceFrame(user).setVisible(true);
            dispose();
        });

        miniStatementButton.addActionListener(e -> {
            new MiniStatementFrame(user).setVisible(true);
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(12);
    }

    private Icon getIcon(String key) {
        return UIManager.getIcon(key); // Fallback to basic icons (or replace with custom ImageIcon)
    }
}
