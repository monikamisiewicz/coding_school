package pl.coderslab.dao;

import pl.coderslab.model.User;
import pl.coderslab.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;


public class UserDao {

    private static final String CREATE_QUERY = "INSERT INTO users (username, email, password, user_group_id) VALUES (?, ?, ?, ?)";
    private static final String READ_BY_ID_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET username = ?, email = ?, password = ?, user_group_id = ? where id = ?";
    private static final String DELETE_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM users";
    private static final String FIND_ALL_BY_GROUP_ID = "SELECT * FROM users WHERE user_group_id = ?";

    public User create(User user) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getGroupId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public User read(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGroupId(rs.getInt("user_group_id"));
                return user;
            }
            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void update(User user) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getGroupId());
            statement.setInt(5, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User[] findAll() {
        try (Connection conn = DBUtil.createConnection()) {
            User[] users = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setGroupId(rs.getInt("user_group_id"));
                users = addToArray(user, users);
            }

            System.out.println("All users: ");
            for (int i = 0; i < users.length; i++) {
                System.out.println(String.format("id: %d, %s, %s, group: %d",
                        users[i].getId(),
                        users[i].getUserName(),
                        users[i].getEmail(),
                        users[i].getGroupId()
                ));
            }

            return users;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public User[] findAllByGroupId(int groupId) {
        try (Connection conn = DBUtil.createConnection()) {
            User[] groupUsers = new User[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_GROUP_ID);
            statement.setInt(1, groupId); //ustawienie parametru w zapytaniu
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User groupUser = new User();
                groupUser.setId(rs.getInt("id"));
                groupUser.setUserName(rs.getString("username"));
                groupUser.setEmail(rs.getString("email"));
                groupUser.setPassword(rs.getString("password"));
                groupUser.setGroupId(rs.getInt("user_group_id"));

                groupUsers = addToArray(groupUser, groupUsers);

                System.out.println("Users in group " + groupId + ": ");
                for (int i = 0; i < groupUsers.length; i++) {
                    System.out.println(String.format("%s, %s",
                            groupUsers[i].getUserName(),
                            groupUsers[i].getEmail()
                    ));
                }
            }
            return groupUsers;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User[] addToArray(User user, User[] users) {
        User[] tmp = Arrays.copyOf(users, users.length + 1);
        tmp[users.length] = user;
        return tmp;
    }
}
