package pl.coderslab.management;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.utils.DBUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Scanner;

public class ManageSolution {

    SolutionDao solutionDao = new SolutionDao();
    UserDao userDao = new UserDao();

    public void manageSolution() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        System.out.println("Available options:\n" +
                "add - to assign solution to the exercise\n" +
                "view - to browse user's solutions\n" +
                "quit - to end program");


        while (!exit) {
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "add":
                    addSolution(scanner);
                    break;
                case "view":
                    viewSolution(scanner);
                    break;
                case "quit":
                    System.out.println("Closing program...");
                    exit = true;
                    break;
                default:
                    exit = true;
                    break;
            }
            exit = true;
        }
        scanner.close();
    }

    private Solution addSolution(Scanner scanner) {


            ExerciseDao exerciseDao = new ExerciseDao();
            exerciseDao.findAll();

            System.out.println("Enter exercise id: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid exercise id: ");
                scanner.next();
            }
            int exerciseId = scanner.nextInt();
            scanner.nextLine();

            userDao.findAll();
            System.out.println("Enter user id: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid user id: ");
                scanner.next();
            }
            int userId = scanner.nextInt();
            scanner.nextLine();

            Solution solution = new Solution();

            Calendar calendar = Calendar.getInstance();

            java.util.Date currentDate = calendar.getTime();
            Date date = new Date(currentDate.getTime());


            solution.setCreated(date);
            solution.setUpdated(null);
            solution.setDescription(null);
            solution.setExerciseId(exerciseId);
            solution.setUserId(userId);
            solutionDao.create(solution);

            manageSolution();
            return solution;
    }

    private Solution viewSolution(Scanner scanner) {

            userDao.findAll();
            System.out.println("Enter user id to see solutions: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid user id: ");
                scanner.nextLine();
            }
            int userId = scanner.nextInt();
            scanner.nextLine();

            Solution solution = new Solution();
            solutionDao.findAllByUserId(userId);
            manageSolution();

            return solution;
    }
}

