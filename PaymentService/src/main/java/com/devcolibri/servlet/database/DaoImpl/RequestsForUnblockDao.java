package com.devcolibri.servlet.database.DaoImpl;

import com.devcolibri.servlet.database.Dao;
import com.devcolibri.servlet.database.Database;

import com.devcolibri.servlet.objects.RequestForUnblock;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class RequestsForUnblockDao implements Dao<RequestForUnblock> {

    private static final Logger log = Logger.getLogger(RequestsForUnblockDao.class);

    private Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public RequestsForUnblockDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<RequestForUnblock> selectAll() {
        ArrayList<RequestForUnblock> requestsForUnblock = new ArrayList<RequestForUnblock>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM requests_for_unblock");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int blockedBankAccountId = resultSet.getInt("blocked_bank_account_id");
                RequestForUnblock requestForUnblock = new RequestForUnblock(id, blockedBankAccountId);
                requestsForUnblock.add(requestForUnblock);
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
        return requestsForUnblock;
    }

    public RequestForUnblock selectOneById(int id) {
        RequestForUnblock requestForUnblock = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM requests_for_unblock WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int blockedBankAccountId = resultSet.getInt("blocked_bank_account_id");
                requestForUnblock = new RequestForUnblock(id, blockedBankAccountId);
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
        return requestForUnblock;
    }

    public RequestForUnblock selectOneByBlockedBankAccountId(int blockedBankAccountId) {
        RequestForUnblock requestForUnblock = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM requests_for_unblock WHERE blocked_bank_account_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, blockedBankAccountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                requestForUnblock = new RequestForUnblock(id, blockedBankAccountId);
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
        return requestForUnblock;
    }

    public int insert(RequestForUnblock requestForUnblock) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO requests_for_unblock (blocked_bank_account_id) VALUES (?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, requestForUnblock.getBlockedBankAccountId());

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

    public int update(RequestForUnblock requestForUnblock) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE requests_for_unblock SET blocked_bank_account_id = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, requestForUnblock.getBlockedBankAccountId());
            preparedStatement.setInt(2, requestForUnblock.getId());

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
            String sql = "DELETE FROM requests_for_unblock WHERE id = ?";
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

    public int deleteByBlockedBankAccountId(int blockedBankAccountId) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM requests_for_unblock WHERE blocked_bank_account_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, blockedBankAccountId);

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
