package pl.coderslab.dao;

import pl.coderslab.model.Group;
import pl.coderslab.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupDao {

    private static final String CREATE_QUERY = "INSERT INTO user_group(name) VALUES (?)";
    private static final String READ_BY_ID_QUERY = "SELECT * FROM user_group WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user_group SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_group WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM user_group";

    public Group create(Group group) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, group.getName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys(); //pobieramy wygenerowany klucz
            if (rs.next()) { //jeśli jest to tylko jedna kolumna z tym id
                group.setId(rs.getInt(1)); //ustawiamy id tej kolumny
            }
            return group;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Group read(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();//Query zwróci resulset
            if (rs.next()) {
                Group group = new Group();
                group.setId(rs.getInt("id"));
                group.setName(rs.getString("name"));//jako columnlabel podajemy nazwy kolumn z bazy danych
                return group;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Group group) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, group.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
                user.setGroupID(rs.getInt("group_id"));//podajemy nazwy kolumn z bazy danych
                users = addToArray(user, users);
            }
            return users;
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
