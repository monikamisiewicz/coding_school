package pl.coderslab.dao;

import pl.coderslab.model.Solution;
import pl.coderslab.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class SolutionDao {

    private static final String CREATE_QUERY = "INSERT INTO solution(created, updated, description, exercise_id, user_id, comment, grade) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String READ_BY_ID_QUERY = "SELECT * FROM solution WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE solution SET updated = ?, description = ?, exercise_id = ?, user_id = ?, comment = ?, grade = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM solution WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM solution";
    private static final String FIND_ALL_BY_USER_ID = "SELECT * FROM solution WHERE user_id = ?";
    private static final String FIND_ALL_BY_EXERCISE_ID = "SELECT * FROM solution WHERE exercise_id = ? ORDER BY created";

    public Solution create(Solution solution) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setDate(1, solution.getCreated());
            statement.setDate(2, solution.getUpdated());
            statement.setString(3, solution.getDescription());
            statement.setInt(4, solution.getExerciseId());
            statement.setInt(5, solution.getUserId());
            statement.setString(6, solution.getComment());
            statement.setInt(7, solution.getGrade());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                solution.setId(rs.getInt(1));
            }
            return solution;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Solution read(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(READ_BY_ID_QUERY);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();//Query zwróci resulset
            if (rs.next()) {
                Solution solution = new Solution();
                solution.setId(rs.getInt("id"));
                solution.setCreated(rs.getDate("created"));
                solution.setUpdated(rs.getDate("updated"));
                solution.setDescription(rs.getString("description"));
                solution.setExerciseId(rs.getInt("exercise_id"));//podajemy nazwy kolumn z bazy danych
                solution.setUserId(rs.getInt("user_id"));
                solution.setComment(rs.getString("comment"));
                solution.setGrade(rs.getInt("grade"));
                return solution;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Solution solution) {
        try (Connection conn = DBUtil.createConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);
            statement.setDate(1, solution.getUpdated());
            statement.setString(2, solution.getDescription());
            statement.setInt(3, solution.getExerciseId());
            statement.setInt(4,solution.getUserId());
            statement.setString(5, solution.getComment());
            statement.setInt(6, solution.getGrade());
            statement.setInt(7, solution.getId());
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

    public Solution[] findAll() {
        try (Connection conn = DBUtil.createConnection()) {
            Solution[] solutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Solution solution = new Solution();
                solution.setId(rs.getInt("id"));
                solution.setCreated(rs.getDate("created"));
                solution.setUpdated(rs.getDate("updated"));
                solution.setDescription(rs.getString("description"));
                solution.setExerciseId(rs.getInt("exercise_id"));
                solution.setUserId(rs.getInt("user_id"));
                solution.setComment(rs.getString("comment"));
                solution.setGrade(rs.getInt("grade"));
                solutions = addToArray(solution, solutions);
            }

            System.out.println("All solutions: ");
            for(int i=0; i<solutions.length; i++) {
                System.out.println(String.format("id: %d, created: %tF, updated: %tF, %s, exercise id: %d, user id: %d, %s, grade: %d",
                        solutions[i].getId(),
                        solutions[i].getCreated(),
                        solutions[i].getUpdated(),
                        solutions[i].getDescription(),
                        solutions[i].getExerciseId(),
                        solutions[i].getUserId(),
                        solutions[i].getComment(),
                        solutions[i].getGrade()
                ));
            }
            return solutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByUserId(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            Solution[] userSolutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Solution userSolution = new Solution();
                userSolution.setId(rs.getInt("id"));
                userSolution.setCreated(rs.getDate("created"));
                userSolution.setUpdated(rs.getDate("updated"));
                userSolution.setDescription(rs.getString("description"));
                userSolution.setExerciseId(rs.getInt("exercise_id"));
                userSolution.setUserId(rs.getInt("user_id"));
                userSolution.setComment(rs.getString("comment"));
                userSolution.setGrade(rs.getInt("grade"));

                userSolutions = addToArray(userSolution, userSolutions);
            }
            System.out.println("Solutions of user with id: " + id);
            for(int i=0; i<userSolutions.length; i++) {
                System.out.println(String.format("id: %d, %tD, %tD, %s, exercise: %d, user id: %d",
                        userSolutions[i].getId(),
                        userSolutions[i].getCreated(),
                        userSolutions[i].getUpdated(),
                        userSolutions[i].getDescription(),
                        userSolutions[i].getExerciseId(),
                        userSolutions[i].getUserId(),
                        userSolutions[i].getComment(),
                        userSolutions[i].getGrade()
                ));
            }
            return userSolutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Solution[] findAllByExerciseId(int id) {
        try (Connection conn = DBUtil.createConnection()) {
            Solution[] exerciseSolutions = new Solution[0];
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_BY_EXERCISE_ID);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                Solution exerciseSolution = new Solution();
                exerciseSolution.setId(rs.getInt("id"));
                exerciseSolution.setCreated(rs.getDate("created"));
                exerciseSolution.setUpdated(rs.getDate("updated"));
                exerciseSolution.setDescription(rs.getString("description"));
                exerciseSolution.setExerciseId(rs.getInt("exercise_id"));
                exerciseSolution.setUserId(rs.getInt("users_id"));
                exerciseSolution.setComment(rs.getString("comment"));
                exerciseSolution.setGrade(rs.getInt("grade"));

                exerciseSolutions = addToArray(exerciseSolution, exerciseSolutions);
            }

            System.out.println("Solutions of exercise with id: " + id);
            for(int i=0; i<exerciseSolutions.length; i++) {
                System.out.println(String.format("id: %d, %tD, %tD, %s, exercise: %d, user id: %d",
                        exerciseSolutions[i].getId(),
                        exerciseSolutions[i].getCreated(),
                        exerciseSolutions[i].getUpdated(),
                        exerciseSolutions[i].getDescription(),
                        exerciseSolutions[i].getExerciseId(),
                        exerciseSolutions[i].getUserId(),
                        exerciseSolutions[i].getComment()
                ));
            }
            return exerciseSolutions;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Solution[] addToArray(Solution solution, Solution[] solutions) {
        Solution[] tmp = Arrays.copyOf(solutions, solutions.length + 1);
        tmp[solutions.length] = solution;
        return tmp;
    }
}


