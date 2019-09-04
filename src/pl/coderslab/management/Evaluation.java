package pl.coderslab.management;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;

public class Evaluation {

    public void evaluateSolution() {

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        System.out.println("Available options :\n" +
                "grade - to assess the solution\n" +
                "quit - to exit program");

        while (!exit) {
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "grade":
                    addGrade(scanner);
                    scanner.next();
                    break;
                case "quit":
                    System.out.println("Closing program...");
                    exit = true;
                    break;
                default:
                    System.out.println("No such option available");
                    break;
            }
        }
        scanner.close(); //gdzie zamknąć scanner??????
    }


    public Solution addGrade(Scanner scanner) {

        UserDao userDao = new UserDao();
        userDao.findAll();
        System.out.println("Enter user id: ");
        int userId;
        int solutionId;
        int exerciseId;

        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid user id: ");
                scanner.next();
            }
            userId = scanner.nextInt();
            scanner.nextLine();

            if (userDao.read(userId) == null) {
                System.out.println("User with id " + userId + " doesn't exist. Enter again: ");
                continue;
            }


            SolutionDao solutionDao = new SolutionDao();
            solutionDao.findAllByUserId(userId);
            System.out.println("Enter solution id you want to evaluate: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid solution id: ");
                scanner.next();
            }
            solutionId = scanner.nextInt();
            scanner.nextLine();
            if (solutionDao.read(solutionId) == null) {
                System.out.println("Solution with id " + solutionId + " doesn't exist. Enter again");
                continue;
            }


            solutionDao.findAllByUserId(userId);
            System.out.println("Enter exercise id you want to evaluate: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid exercise id: ");
                scanner.next();
            }
            exerciseId = scanner.nextInt();
            scanner.nextLine();
            ExerciseDao exerciseDao = new ExerciseDao();
            if (exerciseDao.read(exerciseId) == null) {
                System.out.println("Exercise with id " + exerciseId + " doesn't exist. Enter again");
                continue;
            }
            break;
        }


        System.out.println("Enter comment of the solution: ");
        String comment = scanner.nextLine();


        int points;
        System.out.println("Enter number of points for this solution in range 0-20: ");
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter number of points from 0 to 20: ");
                scanner.next();
            }

            points = scanner.nextInt();
            if (points < 0) {
                System.out.println("Number of points cannot be less than 0");
                continue;
            } else if (points > 20) {
                System.out.println("Number of points cannot be bigger than 20");
                continue;
            }
            break;
        }

        int grade = 0;
        if (points >= 18 && points <= 20) {
            grade = 5;
            System.out.println(points + " points – EXELLENT! Grade " + grade);
        } else if (points >= 15 && points < 18) {
            grade = 4;
            System.out.println(points + " points – GOOD! Grade " + grade);
        } else if (points >= 12 && points < 15) {
            grade = 3;
            System.out.println(points + " POINTS – AVERAGE! Grade " + grade);
        } else if (points >= 10 && points < 12) {
            grade = 2;
            System.out.println(points + " points – POOR! Grade " + grade);
        } else if (points >= 0 && points <= 9) {
            grade = 1;
            System.out.println(points + " points – NOT ENOUGH! Grade " + grade);//niedostateczna
        }


        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        Date date = new Date(currentDate.getTime());

        Solution solution = new Solution();
        SolutionDao solutionDao = new SolutionDao();
        solutionDao.read(solutionId);

        solution.setUpdated(date);
        solution.setDescription(null);
        solution.setExerciseId(exerciseId);
        solution.setUserId(userId);
        solution.setComment(comment);
        solution.setGrade(grade);
        solution.setId(solutionId);

        solutionDao.update(solution);
        evaluateSolution();

        return solution;
        //gdzie zamknąć scanner w tej metodzie???
    }
}

