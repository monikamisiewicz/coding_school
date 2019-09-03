package pl.coderslab.management;

import pl.coderslab.dao.ExerciseDao;
import pl.coderslab.model.Exercise;

import java.util.Scanner;

public class ManageExercise {

    ExerciseDao exerciseDao = new ExerciseDao();

    public void manageExercise() {

        Scanner scanner = new Scanner(System.in);
        exerciseDao.findAll();


        System.out.println("Available options:\n" +
                "add - to add exercise\n" +
                "edit - to edit exercise\n" +
                "delete - to delete exercise\n" +
                "quit - to end program");

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "add":
                    addExercise(scanner);
                    break;
                case "edit":
                    editExercise(scanner);
                    break;
                case "delete":
                    deleteExercise(scanner);
                    break;
                case "quit":
                    System.out.println("Closing program...");
                    exit = true;
                default:
                    break;
            }
            exit = true;
        }

        scanner.close();
    }

    private Exercise addExercise(Scanner scanner) {
        System.out.println("Enter exercise title: ");
        String title = scanner.nextLine();
        System.out.println("Enter exercise description: ");
        String description = scanner.nextLine();

        Exercise exercise = new Exercise();
        exercise.setTitle(title);
        exercise.setDescription(description);
        exerciseDao.create(exercise);
        manageExercise();

        return exercise;
    }

    private Exercise editExercise(Scanner scanner) {

        System.out.println("Enter exercise title: ");
        String title = scanner.nextLine();
        System.out.println("Enter exercise description: ");
        String description = scanner.nextLine();
        System.out.println("Enter exercise id you want to update: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid exercise id: ");
            scanner.next();
        }
        int exerciseId = scanner.nextInt();
        scanner.nextLine();

        Exercise exercise = new Exercise();
        exerciseDao.read(exerciseId);

        exercise.setTitle(title);
        exercise.setDescription(description);
        exercise.setId(exerciseId);

        exerciseDao.update(exercise);
        manageExercise();

        return exercise;
    }

    private void deleteExercise(Scanner scanner) {

        System.out.println("Enter exercise id you want to delete : ");
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid id: ");
            scanner.next();
        }
        int exerciseId = scanner.nextInt();
        scanner.nextLine();

        exerciseDao.delete(exerciseId);
        System.out.println("Exercise with id " + exerciseId + " deleted");
        manageExercise();
    }
}

