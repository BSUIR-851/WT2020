package com.devcolibri.servlet.database;

import com.devcolibri.servlet.objects.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RolesDao implements Dao<Role> {

    private Connection conn;

    public RolesDao() {
        this.conn = Database.getConnection();
    }

    public ArrayList<Role> selectAll() {
        ArrayList<Role> roles = new ArrayList<Role>();
        Statement statement = null;
        try {
            statement = this.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM roles");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int privilegeLevel = resultSet.getInt("privilege_level");
                Role role = new Role(id, name, privilegeLevel);
                roles.add(role);
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
        return roles;
    }

    public Role selectOneById(int id) {
        Role role = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM roles WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int privilegeLevel = resultSet.getInt("privilege_level");
                role = new Role(id, name, privilegeLevel);
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
        return role;
    }

    public Role selectOneByName(String name) {
        Role role = null;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "SELECT * FROM roles WHERE name = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int privilegeLevel = resultSet.getInt("privilege_level");
                role = new Role(id, name, privilegeLevel);
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
        return role;
    }

    public int insert(Role role) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "INSERT INTO roles (name, privilege_level) VALUES (?, ?)";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getPrivilegeLevel());

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

    public int update(Role role) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "UPDATE roles SET name = ?, privilege_level = ? WHERE id = ?";
            preparedStatement = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, role.getName());
            preparedStatement.setInt(2, role.getPrivilegeLevel());
            preparedStatement.setInt(3, role.getId());
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
            String sql = "DELETE FROM roles WHERE id = ?";
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

    public int deleteByName(String name) {
        PreparedStatement preparedStatement = null;
        int res = 0;
        try {
            String sql = "DELETE FROM roles WHERE name = ?";
            preparedStatement = this.conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

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
