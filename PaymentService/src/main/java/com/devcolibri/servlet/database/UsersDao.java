package com.devcolibri.servlet.database;

import com.devcolibri.servlet.objects.User;

import java.sql.*;
import java.util.ArrayList;

public class UsersDao implements Dao<User> {

    private Connection conn;

    public UsersDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<User> selectAll() {
        ArrayList<User> users = new ArrayList<User>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String pass_hash = resultSet.getString("pass_hash");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                User user = new User(id, username, email, pass_hash, first_name, last_name);
                users.add(user);
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
        return users;
    }

    public User selectOneById(int id) {
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String pass_hash = resultSet.getString("pass_hash");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                user = new User(id, username, email, pass_hash, first_name, last_name);
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
        return user;
    }

    public User selectOneByUsername(String username) {
        User user = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM users WHERE username = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String pass_hash = resultSet.getString("pass_hash");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                user = new User(id, username, email, pass_hash, first_name, last_name);
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
        return user;
    }

    public int insert(User user) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO users (username, email, pass_hash, first_name, last_name) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassHash());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());

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

    public int update(User user) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE users SET username = ?, email = ?, pass_hash = ?, first_name = ?, last_name = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassHash());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getLastName());
            preparedStatement.setInt(6, user.getId());
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
            String sql = "DELETE FROM users WHERE id = ?";
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

    public int deleteByUsername(String username) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM users WHERE username = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, username);

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
