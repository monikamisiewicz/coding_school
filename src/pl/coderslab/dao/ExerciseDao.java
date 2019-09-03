package pl.coderslab.dao;

import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ExerciseDao {

    private static final String CREATE_QUERY = "INSERT INTO exercise(title, description) VALUES (?, ?)";
    private static final String READ_BY_ID_QUERY = "SELECT * FROM exercise WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE exercise SET title = ?,description = ?  WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM exercise WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM exercise";
    private static final String FIND_NOT_ADDED_BY__USER_QUERY = "SELECT e.id, e.title, e.description FROM exercise e " +
            "LEFT JOIN solution s ON e.id = s.exercise_id AND s.user_id = ? WHERE s.id IS NULL ;";

    public Exercise create(Exercise exercise) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                exercise.setId(rs.getInt(1));
            }
            return exercise;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise read(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(rs.getInt("id"));
                exercise.setTitle(rs.getString("title"));//jako columnlabel podajemy nazwy kolumn z bazy danych
                exercise.setDescription(rs.getString("description"));
                return exercise;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Exercise exercise) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setString(1, exercise.getTitle());
            statement.setString(2, exercise.getDescription());
            statement.setInt(3,exercise.getId());
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

    public Exercise[] findAll() {
        try (Connection conn = DBUtil.createConnection()) {
            Exercise[] exercises = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(rs.getInt("id"));
                exercise.setTitle(rs.getString("title"));
                exercise.setDescription(rs.getString("description"));
                exercises = addToArray(exercise, exercises);
            }

            System.out.println("All exercises: ");
            for(int i=0; i<exercises.length; i++) {
                System.out.println(String.format("id: %d, %s, %s",
                        exercises[i].getId(),
                        exercises[i].getTitle(),
                        exercises[i].getDescription()
                ));
            }
            return exercises;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Exercise[] findNotAddedByUser(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            Exercise[] exercisesNotAdded = new Exercise[0];
            PreparedStatement statement = conn.prepareStatement(FIND_NOT_ADDED_BY__USER_QUERY);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Exercise exercise = new Exercise();
                exercise.setId(rs.getInt("id"));
                exercise.setTitle(rs.getString("title"));
                exercise.setDescription(rs.getString("description"));

                exercisesNotAdded = addToArray(exercise, exercisesNotAdded);
            }
            System.out.println("Exercises not added yet by user with id "+ id);
            for(int i=0; i<exercisesNotAdded.length; i++) {
                System.out.println(String.format("exercise id: %d, %s, %s",
                        exercisesNotAdded[i].getId(),
                        exercisesNotAdded[i].getTitle(),
                        exercisesNotAdded[i].getDescription()
                ));
            }
            return exercisesNotAdded;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private Exercise[] addToArray(Exercise exercise, Exercise[] exercises) {
        Exercise[] tmp = Arrays.copyOf(exercises, exercises.length + 1);
        tmp[exercises.length] = exercise;
        return tmp;
    }
}

