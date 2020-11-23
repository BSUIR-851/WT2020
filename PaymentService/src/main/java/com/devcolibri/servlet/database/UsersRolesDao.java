package com.devcolibri.servlet.database;

import com.devcolibri.servlet.objects.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UsersRolesDao implements Dao<UserRole> {

    public static final int USER_ID = 1;
    public static final int USER_VALUE = 0;

    public static final int ADMIN_ID = 2;
    public static final int ADMIN_VALUE = 1;

    private Connection conn;

    public UsersRolesDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<UserRole> selectAll() {
        ArrayList<UserRole> userRoles = new ArrayList<UserRole>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users_roles");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                int roleId = resultSet.getInt("role_id");
                UserRole userRole = new UserRole(id, userId, roleId);
                userRoles.add(userRole);
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
        return userRoles;
    }

    public UserRole selectOneById(int id) {
        UserRole userRole = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM users_roles WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int roleId = resultSet.getInt("role_id");
                userRole = new UserRole(id, userId, roleId);
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
        return userRole;
    }

    public ArrayList<UserRole> selectByUserId(int userId) {
        ArrayList<UserRole> userRoles = new ArrayList<UserRole>();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM users_roles WHERE user_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int roleId = resultSet.getInt("role_id");
                UserRole userRole = new UserRole(id, userId, roleId);
                userRoles.add(userRole);
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
        return userRoles;
    }

    public int insert(UserRole userRole) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO users_roles (user_id, role_id) VALUES (?, ?);";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userRole.getUserId());
            preparedStatement.setInt(2, userRole.getRoleId());

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

    public int update(UserRole userRole) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE users_roles SET user_id = ?, role_id = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userRole.getUserId());
            preparedStatement.setInt(2, userRole.getRoleId());
            preparedStatement.setInt(3, userRole.getId());
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
            String sql = "DELETE FROM users_roles WHERE id = ?";
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

    public int deleteByUserId(int userId) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM users_roles WHERE user_id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);

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
