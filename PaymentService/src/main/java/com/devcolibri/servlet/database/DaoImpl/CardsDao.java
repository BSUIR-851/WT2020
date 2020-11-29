package com.devcolibri.servlet.database.DaoImpl;

import com.devcolibri.servlet.database.Dao;
import com.devcolibri.servlet.database.Database;
import com.devcolibri.servlet.objects.Card;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;

public class CardsDao implements Dao<Card> {

    private static final Logger log = Logger.getLogger(CardsDao.class);

    private Connection conn;

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public CardsDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<Card> selectAll() {
        ArrayList<Card> cards = new ArrayList<Card>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cards");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bankAccountId = resultSet.getInt("bank_account_id");
                String number = resultSet.getString("number");
                float balance = resultSet.getFloat("balance");
                String pinHash = resultSet.getString("pin_hash");
                String cvvHash = resultSet.getString("cvv_hash");
                Date expireDate = resultSet.getDate("expire_date");
                Card card = new Card(id, userId, bankAccountId, number, balance, pinHash, cvvHash, expireDate);
                cards.add(card);
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
        return cards;
    }

    public ArrayList<Card> selectAllByUserId(int userId) {
        ArrayList<Card> cards = new ArrayList<Card>();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM cards WHERE user_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int bankAccountId = resultSet.getInt("bank_account_id");
                String number = resultSet.getString("number");
                float balance = resultSet.getFloat("balance");
                String pinHash = resultSet.getString("pin_hash");
                String cvvHash = resultSet.getString("cvv_hash");
                Date expireDate = resultSet.getDate("expire_date");
                Card card = new Card(id, userId, bankAccountId, number, balance, pinHash, cvvHash, expireDate);
                cards.add(card);
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
        return cards;
    }

    public Card selectOneById(int id) {
        Card card = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM cards WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int bankAccountId = resultSet.getInt("bank_account_id");
                String number = resultSet.getString("number");
                float balance = resultSet.getFloat("balance");
                String pinHash = resultSet.getString("pin_hash");
                String cvvHash = resultSet.getString("cvv_hash");
                Date expireDate = resultSet.getDate("expire_date");
                card = new Card(id, userId, bankAccountId, number, balance, pinHash, cvvHash, expireDate);
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
        return card;
    }

    public Card selectOneByNumber(String number) {
        Card card = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM cards WHERE number = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int bankAccountId = resultSet.getInt("bank_account_id");
                float balance = resultSet.getFloat("balance");
                String pinHash = resultSet.getString("pin_hash");
                String cvvHash = resultSet.getString("cvv_hash");
                Date expireDate = resultSet.getDate("expire_date");
                card = new Card(id, userId, bankAccountId, number, balance, pinHash, cvvHash, expireDate);
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
        return card;
    }

    public int insert(Card card) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO cards (user_id, bank_account_id, number, balance, pin_hash, cvv_hash, expire_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, card.getUserId());
            preparedStatement.setInt(2, card.getBankAccountId());
            preparedStatement.setString(3, card.getNumber());
            preparedStatement.setFloat(4, card.getBalance());
            preparedStatement.setString(5, card.getPinHash());
            preparedStatement.setString(6, card.getCvvHash());
            preparedStatement.setDate(7, card.getExpireDate());

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

    public int update(Card card) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE cards SET user_id = ?, bank_account_id = ?, number = ?, balance = ?, pin_hash = ?, cvv_hash = ?, expire_date = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, card.getUserId());
            preparedStatement.setInt(2, card.getBankAccountId());
            preparedStatement.setString(3, card.getNumber());
            preparedStatement.setFloat(4, card.getBalance());
            preparedStatement.setString(5, card.getPinHash());
            preparedStatement.setString(6, card.getCvvHash());
            preparedStatement.setDate(7, card.getExpireDate());
            preparedStatement.setInt(8, card.getId());

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
            String sql = "DELETE FROM cards WHERE id = ?";
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

    public int deleteByNumber(String number) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM cards WHERE number = ?";
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
