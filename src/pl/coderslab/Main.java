package pl.coderslab;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.management.*;
import pl.coderslab.model.User;
import pl.coderslab.model.UserGroup;



public class Main {


    public static void main(String[] args) {

        UserDao userDao = new UserDao();
        UserGroupDao userGroupDao = new UserGroupDao();
        ExerciseDao exerciseDao = new ExerciseDao();
        SolutionDao solutionDao = new SolutionDao();

//        ManageUser users = new ManageUser();
//        users.manageUser();

//        ManageGroup groups = new ManageGroup();
//        groups.manageUserGroup();

//        ManageExercise exercises = new ManageExercise();
//        exercises.manageExercise();

//        ManageSolution solutions = new ManageSolution();
//        solutions.manageSolution();


//        User user = new User();
//        UserProgram userProgram = new UserProgram(user);
//        userProgram.options();

        Evaluation evaluation = new Evaluation();
        evaluation.evaluateSolution();

    }
}
