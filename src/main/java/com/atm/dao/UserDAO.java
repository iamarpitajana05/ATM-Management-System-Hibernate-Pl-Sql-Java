package com.atm.dao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.atm.model.*;
import oracle.jdbc.OracleTypes;
import java.sql.SQLException;
import com.atm.util.HibernateUtil;
import com.atm.model.User;
import java.util.ArrayList;
import java.util.List;
public class UserDAO {

    private SessionFactory sessionFactory;

    public UserDAO() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Method to register a new user
    public void registerUser(String username, String password, int accountNumber) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.doWork(connection -> {
                try (CallableStatement stmt = connection.prepareCall("{call atm_pkg.register_user(?, ?, ?)}")) {
                    stmt.setString(1, username);
                    stmt.setString(2, password);
                    stmt.setInt(3, accountNumber);
                    stmt.execute();
                } catch (SQLException e) {
                    throw new RuntimeException("Error during user registration", e);
                }
            });
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to authenticate a user
    public Integer authenticateUser(String username, String password) {
        Session session = sessionFactory.openSession();
        Integer accountNumber = null;

        try {
            accountNumber = (Integer) session.doReturningWork(connection -> {
                try (CallableStatement stmt = connection.prepareCall("{? = call atm_pkg.authenticate_user(?, ?)}")) {
                    stmt.registerOutParameter(1, java.sql.Types.NUMERIC);
                    stmt.setString(2, username);
                    stmt.setString(3, password);
                    stmt.execute();
                    return stmt.getInt(1);
                } catch (SQLException e) {
                    throw new RuntimeException("Error during user authentication", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return accountNumber;
    }

    // Method to get the balance of a user
    public double getBalance(int accountNumber) {
        Session session = sessionFactory.openSession();
        double balance = 0.0;

        try {
            balance = (Double) session.doReturningWork(connection -> {
                try (CallableStatement stmt = connection.prepareCall("{? = call atm_pkg.get_balance(?)}")) {
                    stmt.registerOutParameter(1, java.sql.Types.NUMERIC);
                    stmt.setInt(2, accountNumber);
                    stmt.execute();
                    return stmt.getDouble(1);
                } catch (SQLException e) {
                    throw new RuntimeException("Error retrieving balance", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return balance;
    }

    // Method to deposit money into an account
    public void depositMoney(int accountNumber, double amount) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.doWork(connection -> {
                try (CallableStatement stmt = connection.prepareCall("{call atm_pkg.deposit_money(?, ?)}")) {
                    stmt.setInt(1, accountNumber);
                    stmt.setDouble(2, amount);
                    stmt.execute();
                } catch (SQLException e) {
                    throw new RuntimeException("Error during deposit", e);
                }
            });
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to withdraw money from an account
 /// Method to withdraw money from an account
    public boolean withdrawMoney(int accountNumber, double amount) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        final boolean[] success = {false}; // Use an array to allow modification

        try {
            tx = session.beginTransaction();
            session.doWork(connection -> {
                try (CallableStatement stmt = connection.prepareCall("{call atm_pkg.withdraw_money(?, ?)}")) {
                    stmt.setInt(1, accountNumber);
                    stmt.setDouble(2, amount);
                    stmt.execute();
                    success[0] = true; // Modify the first element of the array
                } catch (SQLException e) {
                    throw new RuntimeException("Error during withdrawal", e);
                }
            });
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return success[0]; // Return the value of the first element of the array
    }
    
    public List<String> getMiniStatementRaw(int accountNumber) {
        List<String> miniStatement = new ArrayList<>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        try {
            session.doWork(connection -> {
                String sql = "SELECT TRANSACTION_DATE, TRANSACTION_TYPE, AMOUNT FROM transactions WHERE ACCOUNT_NUMBER = ?";
                try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                    stmt.setInt(1, accountNumber);
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            String transactionDate = rs.getDate("TRANSACTION_DATE").toString();
                            String transactionType = rs.getString("TRANSACTION_TYPE");
                            double amount = rs.getDouble("AMOUNT");

                            // Formatting the result
                            miniStatement.add(transactionDate + " - " + transactionType + ": Rs. " + amount);
                        }
                    }
                } catch (SQLException e) {
                    throw new RuntimeException("Error retrieving mini-statement", e);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return miniStatement;
    }
    
    
    public User getUserByAccountNumber(int accountNumber) {
        Session session = sessionFactory.openSession();
        User user = null;
        try {
            user = session.get(User.class, accountNumber);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
}
