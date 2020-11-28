package com.devcolibri.servlet.database;

import com.devcolibri.servlet.objects.BankAccount;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class BankAccountsDao implements Dao<BankAccount> {

    private static final Logger log = Logger.getLogger(BankAccountsDao.class);

    private Connection conn;

    public BankAccountsDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<BankAccount> selectAll() {
        ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM bank_accounts");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                float balance = resultSet.getFloat("balance");
                String number = resultSet.getString("number");
                BankAccount bankAccount = new BankAccount(id, userId, balance, number);
                bankAccounts.add(bankAccount);
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
        return bankAccounts;
    }

    public BankAccount selectOneById(int id) {
        BankAccount bankAccount = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM bank_accounts WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                float balance = resultSet.getFloat("balance");
                String number = resultSet.getString("number");
                bankAccount = new BankAccount(id, userId, balance, number);
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
        return bankAccount;
    }

    public BankAccount selectOneByUserId(int userId) {
        BankAccount bankAccount = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM bank_accounts WHERE user_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                float balance = resultSet.getFloat("balance");
                String number = resultSet.getString("number");
                bankAccount = new BankAccount(id, userId, balance, number);
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
        return bankAccount;
    }

    public BankAccount selectOneByNumber(String number) {
        BankAccount bankAccount = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM bank_accounts WHERE number = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                float balance = resultSet.getFloat("balance");
                bankAccount = new BankAccount(id, userId, balance, number);
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
        return bankAccount;
    }

    public int insert(BankAccount bankAccount) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO bank_accounts (user_id, balance, number) VALUES (?, ?, ?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bankAccount.getUserId());
            preparedStatement.setFloat(2, bankAccount.getBalance());
            preparedStatement.setString(3, bankAccount.getNumber());

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

    public int update(BankAccount bankAccount) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE bank_accounts SET user_id = ?, balance = ?, number = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, bankAccount.getUserId());
            preparedStatement.setFloat(2, bankAccount.getBalance());
            preparedStatement.setString(3, bankAccount.getNumber());
            preparedStatement.setInt(4, bankAccount.getId());
            preparedStatement.executeUpdate();

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

    public int deleteById(int id) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM bank_accounts WHERE id = ?";
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

    public int deleteByUserId(int userId) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM bank_accounts WHERE user_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

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

    public int deleteByNumber(String number) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM bank_accounts WHERE number = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, number);

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
