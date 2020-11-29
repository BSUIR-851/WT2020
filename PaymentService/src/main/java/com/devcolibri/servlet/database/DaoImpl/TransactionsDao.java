package com.devcolibri.servlet.database.DaoImpl;

import com.devcolibri.servlet.database.Dao;
import com.devcolibri.servlet.database.Database;
import com.devcolibri.servlet.objects.Transaction;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class TransactionsDao implements Dao<Transaction> {

    private static final Logger log = Logger.getLogger(TransactionsDao.class);

    private Connection conn;

    public TransactionsDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<Transaction> selectAll() {
        ArrayList<Transaction> transactions = new ArrayList<Transaction>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int scardId = resultSet.getInt("scard_id");
                int dcardId = resultSet.getInt("dcard_id");
                float amount = resultSet.getFloat("amount");
                Timestamp datetime = resultSet.getTimestamp("datetime");
                Transaction transaction = new Transaction(id, scardId, dcardId, amount, datetime);
                transactions.add(transaction);
            }

        } catch (Exception ex) {
            this.log.error(ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                }
            }
        }
        return transactions;
    }

    public Transaction selectOneById(int id) {
        Transaction transaction = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM transactions WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int scardId = resultSet.getInt("scard_id");
                int dcardId = resultSet.getInt("dcard_id");
                float amount = resultSet.getFloat("amount");
                Timestamp datetime = resultSet.getTimestamp("datetime");
                transaction = new Transaction(id, scardId, dcardId, amount, datetime);
            }

        } catch (Exception ex) {
            this.log.error(ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                }
            }
        }
        return transaction;
    }

    public int insert(Transaction transaction) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO transactions (scard_id, dcard_id, amount, datetime) VALUES (?, ?, ?, ?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, transaction.getScardId());
            preparedStatement.setInt(2, transaction.getDcardId());
            preparedStatement.setFloat(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, transaction.getDatetime());

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                res = rs.getInt(1);
            }

        } catch (Exception ex) {
            this.log.error(ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                }
            }
        }
        return res;
    }

    public int update(Transaction transaction) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE transactions SET scard_id = ?, dcard_id = ?, amount = ?, datetime = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, transaction.getScardId());
            preparedStatement.setInt(2, transaction.getDcardId());
            preparedStatement.setFloat(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, transaction.getDatetime());
            preparedStatement.setInt(5, transaction.getId());

            res = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            this.log.error(ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                }
            }
        }
        return res;
    }

    public int deleteById(int id) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM transactions WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            res = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            this.log.error(ex);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                }
            }
        }
        return res;
    }
}
