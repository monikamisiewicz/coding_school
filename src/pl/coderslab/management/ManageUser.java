package pl.coderslab.management;

import pl.coderslab.dao.UserDao;
import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.User;
import pl.coderslab.utils.PasswordUtil;

import java.util.Scanner;

public class ManageUser {

    UserDao userDao = new UserDao();

    public void manageUser() {
        Scanner scanner = new Scanner(System.in);
        userDao.findAll();

        System.out.println("Available options:\n" +
                "add - to add user\n" +
                "edit - to edit user\n" +
                "delete - to delete user\n" +
                "quit - to end program");

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "add":
                    addUser(scanner);
                    break;
                case "edit":
                    editUser(scanner);
                    break;
                case "delete":
                    deleteUser(scanner);
                    break;
                case "quit":
                    System.out.println("Closing program...");
                    exit = true;
                    break;
                default:
                    System.out.println("No such option available");
                    exit = true;
                    break;
            }
            exit = true;
        }
        scanner.close();
    }


    private User addUser(Scanner scanner) {
        System.out.println("Enter user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter user email: ");
        String email = scanner.nextLine();
        System.out.println("Enter user password: ");
        String password = scanner.nextLine();
        System.out.println("Enter group id: ");
            UserGroupDao groupDao = new UserGroupDao();
            groupDao.findAll();
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid group id: ");
            scanner.nextLine();
        }
        int groupId = scanner.nextInt();
        scanner.nextLine();


        User user = new User();
        user.setUserName(name);
        user.setEmail(email);
        String hashedPassword = PasswordUtil.createHash(password);
        user.setPassword(hashedPassword);
        user.setGroupId(groupId);
        userDao.create(user);
        manageUser();

        return user;
    }

    private User editUser(Scanner scanner) {

        System.out.println("Enter new user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter new user email: ");
        String email = scanner.nextLine();
        System.out.println("Enter new user password: ");
        String password = scanner.nextLine();
        System.out.println("Enter group id: ");
        UserGroupDao groupDao = new UserGroupDao();
        groupDao.findAll();
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid group id: ");
            scanner.nextLine();
        }
        int groupId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter user id you want to update: ");
        userDao.findAll();
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid id: ");
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        User user = new User();
        userDao.read(id);

        user.setUserName(name);
        user.setEmail(email);
        String hashedPassword = PasswordUtil.createHash(password);
        user.setPassword(hashedPassword);
        user.setGroupId(groupId);
        user.setId(id);

        userDao.update(user);
        manageUser();

        return user;
    }

    private void deleteUser(Scanner scanner) {

        System.out.println("Enter user id you want to delete: ");
        userDao.findAll();
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid id: ");
            scanner.nextLine();
        }
        int userId = scanner.nextInt();
        scanner.nextLine();

        userDao.delete(userId);
        System.out.println("User with id " + userId + " deleted");
        manageUser();
    }
}

