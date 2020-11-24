package com.devcolibri.servlet.database;

import com.devcolibri.servlet.objects.BlockedBankAccount;

import java.sql.*;
import java.util.ArrayList;

public class BlockedBankAccountsDao implements Dao<BlockedBankAccount> {

    private Connection conn;

    public BlockedBankAccountsDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<BlockedBankAccount> selectAll() {
        ArrayList<BlockedBankAccount> blockedBankAccounts = new ArrayList<BlockedBankAccount>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM blocked_bank_accounts");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bankAccountId = resultSet.getInt("bank_account_id");
                BlockedBankAccount blockedBankAccount = new BlockedBankAccount(id, bankAccountId);
                blockedBankAccounts.add(blockedBankAccount);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception ex) {
                }
            }
        }
        return blockedBankAccounts;
    }

    public BlockedBankAccount selectOneById(int id) {
        BlockedBankAccount blockedBankAccount = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM blocked_bank_accounts WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int bankAccountId = resultSet.getInt("bank_account_id");
                blockedBankAccount = new BlockedBankAccount(id, bankAccountId);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                }
            }
        }
        return blockedBankAccount;
    }

    public BlockedBankAccount selectOneByBankAccountId(int bankAccountId) {
        BlockedBankAccount blockedBankAccount = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM blocked_bank_accounts WHERE bank_account_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, bankAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                blockedBankAccount = new BlockedBankAccount(id, bankAccountId);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (Exception ex) {
                }
            }
        }
        return blockedBankAccount;
    }

    public int insert(BlockedBankAccount blockedBankAccount) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO blocked_bank_accounts (bank_account_id) VALUES (?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, blockedBankAccount.getBankAccountId());

            preparedStatement.execute();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                res = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public int update(BlockedBankAccount blockedBankAccount) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE blocked_bank_accounts SET bank_account_id = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, blockedBankAccount.getBankAccountId());
            preparedStatement.setInt(2, blockedBankAccount.getId());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                res = rs.getInt(1);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
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
            String sql = "DELETE FROM blocked_bank_accounts WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            res = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
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

    public int deleteByBankAccountId(int bankAccountId) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM blocked_bank_accounts WHERE bank_account_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, bankAccountId);

            res = preparedStatement.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
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
