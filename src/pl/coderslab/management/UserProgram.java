package pl.coderslab.management;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.dao.SolutionDao;
import pl.coderslab.dao.UserDao;
import pl.coderslab.model.Exercise;
import pl.coderslab.model.Solution;
import pl.coderslab.model.User;

import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;


public class UserProgram {

    private User user;

    public UserProgram(User user) {
        this.user = user;
    }

    UserDao userDao = new UserDao();
    SolutionDao solutionDao = new SolutionDao();
    ExerciseDao exerciseDao = new ExerciseDao();
    Solution solution = new Solution();


    public void options() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter user id: ");
        userDao.findAll();
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid user id: ");
            scanner.next();
        }
        int userId = scanner.nextInt();
        scanner.nextLine();

        boolean exit = false;
        System.out.println("Available options :\n" +
                "add - to add solution\n" +
                "view - to browse your solutions");

        while (!exit) {
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "add":
                    System.out.println("Available exercises: ");
                    exerciseDao.findNotAddedByUser(userId);
                    addUserSolution(scanner, userId);
                    break;
                case "view":
                    solutionDao.findAllByUserId(userId);
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
        scanner.close();
    }

    public Solution addUserSolution(Scanner scanner, int userId) {

        System.out.println("Enter exercise id to which you want to add solution: ");
        String description = "";
        int exerciseId = 0;

        while(true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong value. Enter valid exercise id: ");
                scanner.next();
            }
            exerciseId = scanner.nextInt();
            scanner.nextLine();

            if (exerciseDao.read(exerciseId) == null) {
                System.out.println("Exercise with id " + exerciseId + " doesn't exist");
                continue;
            }

            System.out.println("Add solution to the exercise: ");
            description = scanner.nextLine();
            break;
        }

        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        Date date = new Date(currentDate.getTime());

        solution.setCreated(date);
        solution.setUpdated(null);
        solution.setDescription(description);
        solution.setExerciseId(exerciseId);
        solution.setUserId(userId);
        solutionDao.create(solution);


        return solution;
    }
}
