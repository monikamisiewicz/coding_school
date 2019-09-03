package pl.coderslab.dao;

import pl.coderslab.model.UserGroup;
import pl.coderslab.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class UserGroupDao {

    private static final String CREATE_QUERY = "INSERT INTO user_group(name) VALUES (?)";
    private static final String READ_BY_ID_QUERY = "SELECT * FROM user_group WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE user_group SET name = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_group WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM user_group";

    public UserGroup create(UserGroup userGroup) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, userGroup.getName());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys(); //pobieramy wygenerowany klucz
            if (rs.next()) {
                userGroup.setId(rs.getInt(1)); //ustawiamy id tej kolumny korzystając z settera
                //rs.getInt - to metoda resultSet --> zwraca wartość typu int, znajdującą się w kolumnie o podanej nazwie.
                // Istnieją analogiczne metody dla innych typów.
            }
            return userGroup;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public UserGroup read(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();//Query zwróci resulset
            if (rs.next()) {
                UserGroup userGroup = new UserGroup(); //nowy obiekt typu UserGroup i ustawiamy dla niego parametry:
                userGroup.setId(rs.getInt("id"));
                userGroup.setName(rs.getString("name"));//jako columnlabel podajemy nazwy kolumn z bazy danych


                StringBuilder sb = new StringBuilder();
                sb.append(rs.getString("id")).append(", ");
                sb.append(rs.getString("name")).append(", ");
                System.out.println("id: " + sb);
                return userGroup;
            }
//            System.out.println("Group with id " + id +  " does not exist");
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(UserGroup userGroup) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, userGroup.getName());
            statement.setInt(2, userGroup.getId());
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
            System.out.println("Group with id " + id + " deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserGroup[] findAll() {
        try (Connection conn = DBUtil.createConnection()) {
            UserGroup[] userGroups = new UserGroup[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                UserGroup userGroup = new UserGroup();
                userGroup.setId(rs.getInt("id"));
                userGroup.setName(rs.getString("name"));

                userGroups = addToArray(userGroup, userGroups);
            }

            System.out.println("All groups: ");
            for(int i=0; i<userGroups.length; i++) {
                System.out.println(String.format("id %d: %s",
                        userGroups[i].getId(),
                        userGroups[i].getName()
                ));
            }
            return userGroups;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private UserGroup[] addToArray(UserGroup userGroup, UserGroup[] userGroups) {
        UserGroup[] tmp = Arrays.copyOf(userGroups, userGroups.length + 1);
        tmp[userGroups.length] = userGroup;
        return tmp;
    }


}
