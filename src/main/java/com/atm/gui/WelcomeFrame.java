package com.atm.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WelcomeFrame extends JFrame {

    public WelcomeFrame() {
        setTitle("Welcome to ATM");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(230, 240, 255));

        JLabel welcomeLabel = new JLabel("Welcome to Smart ATM System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setForeground(new Color(25, 25, 112));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(new Color(230, 240, 255));

        JButton loginButton = new JButton("Login (Already have an account)");
        styleButton(loginButton, new Color(60, 179, 113));
        loginButton.addActionListener((ActionEvent e) -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JButton signupButton = new JButton("Sign Up (New User)");
        styleButton(signupButton, new Color(100, 149, 237));
        signupButton.addActionListener((ActionEvent e) -> {
            new SignupFrame().setVisible(true);
            dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomeFrame().setVisible(true));
    }
}
