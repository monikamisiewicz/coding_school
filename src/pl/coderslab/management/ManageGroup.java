package pl.coderslab.management;

import pl.coderslab.dao.UserGroupDao;
import pl.coderslab.model.UserGroup;

import java.util.Scanner;

public class ManageGroup {

    UserGroupDao userGroupDao = new UserGroupDao();

    public void manageUserGroup() {
        Scanner scanner = new Scanner(System.in);
        userGroupDao.findAll();

        System.out.println("Available options:\n" +
                "add - to add group\n" +
                "edit - to edit group\n" +
                "delete - to delete group\n" +
                "quit - to end program");

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose option: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "add":
                    addGroup(scanner);
                    break;
                case "edit":
                    editGroup(scanner);
                    break;
                case "delete":
                    deleteGroup(scanner);
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


    private UserGroup addGroup(Scanner scanner) {
        System.out.println("Enter group name: ");
        String name = scanner.nextLine();

        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroupDao.create(userGroup);
        manageUserGroup();

        return userGroup;
    }

    private UserGroup editGroup(Scanner scanner) {
        System.out.println("Enter new group name: ");
        String name = scanner.nextLine();
        System.out.println("Enter group id you want to update: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid id: ");
            scanner.nextLine();
        }

        int id = scanner.nextInt();
        scanner.nextLine();

        UserGroup userGroup = new UserGroup();
        userGroupDao.read(id);

        userGroup.setName(name);
        userGroup.setId(id);

        userGroupDao.update(userGroup);
        manageUserGroup();

        return userGroup;
    }

    private void deleteGroup(Scanner scanner) {

        System.out.println("Enter group id you want to delete: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Wrong value. Enter valid id: ");
            scanner.nextLine();
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        userGroupDao.delete(id);
        manageUserGroup();
    }
}
