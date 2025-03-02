
import java.util.Scanner;

public class Manager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Collector collector = new Collector(); // Initialize the data structure
        boolean exit = false;

        System.out.println("    ||بسم الله الرحمن الرحيم||     ");
        while (!exit) {
            // Display menu
            System.out.println("\n==== Student Records Management ====");
            System.out.println("1. Search Student");
            System.out.println("2. Add New Student");
            System.out.println("3. Show Students in an Academic Level");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> searchStudent(scanner, collector);
                case 2 -> addNewStudent(scanner, collector);
                case 3 -> showStudentsInLevel(scanner, collector);
                case 4 -> exit = true;
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }

        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void searchStudent(Scanner scanner, Collector collector) {
        System.out.println("\n=== Search Student ===");
        System.out.println("1. By ID");
        System.out.println("2. By Last Name");
        System.out.println("3. By First Name");
        System.out.print("Enter your choice: ");

        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return;
        }

        switch (choice) {
            case 1 -> {
                System.out.print("Enter Student ID: ");
                int id = Integer.parseInt(scanner.nextLine());
                Student student = collector.searchByID(id);
                if (student != null) {
                    System.out.println(student);
                    manageStudent(scanner, collector, student);
                } else {
                    System.out.println("Student not found!");
                }
            }
            case 2 -> {
                System.out.print("Enter Last Name: ");
                String lastName = scanner.nextLine();
                Student[] students = collector.searchByLastName(lastName);
                if (students.length > 0) {
                    System.out.println("Select a student from the list:");
                    for (int i = 0; i < students.length; i++) {
                        System.out.println((i + 1) + ". " + students[i]);
                    }
                    System.out.print("Enter the number of the student: ");
                    int selection = Integer.parseInt(scanner.nextLine()) - 1;
                    if (selection >= 0 && selection < students.length) {
                        manageStudent(scanner, collector, students[selection]);
                    } else {
                        System.out.println("Invalid selection!");
                    }
                } else {
                    System.out.println("No students found with last name: " + lastName);
                }
            }
            case 3 -> {
                System.out.print("Enter First Name: ");
                String firstName = scanner.nextLine();
                Student[] students = collector.searchByFirstName(firstName);
                if (students.length > 0) {
                    System.out.println("Select a student from the list:");
                    for (int i = 0; i < students.length; i++) {
                        System.out.println((i + 1) + ". " + students[i]);
                    }
                    System.out.print("Enter the number of the student: ");
                    int selection = Integer.parseInt(scanner.nextLine()) - 1;
                    if (selection >= 0 && selection < students.length) {
                        manageStudent(scanner, collector, students[selection]);
                    } else {
                        System.out.println("Invalid selection!");
                    }
                } else {
                    System.out.println("No students found with first name: " + firstName);
                }
            }

            default -> System.out.println("Invalid choice!");
        }
    }

    private static void addNewStudent(Scanner scanner, Collector collector) {
        System.out.println("\n=== Add New Student ===");
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Date of Birth (dd/mm/yyyy): ");
        String dob = scanner.nextLine();
        System.out.print("Enter Academic Level (FR, SO, JR, SR): ");
        String level = scanner.nextLine().toUpperCase();

        Student newStudent = new Student(id, lastName, firstName, dob, level);
        collector.addNewStudent(newStudent);
        System.out.println("Student added successfully!");
    }

    private static void showStudentsInLevel(Scanner scanner, Collector collector) {
        System.out.println("\n=== Show Students by Academic Level ===");
        System.out.print("Enter Academic Level (FR, SO, JR, SR): ");
        String level = scanner.nextLine().toUpperCase();
        collector.showStudentsInAcademicLevel(level);
    }

    private static void manageStudent(Scanner scanner, Collector collector, Student student) {
        System.out.println("\n=== Manage Student ===");
        System.out.println("1. Edit Student");
        System.out.println("2. Delete Student");
        System.out.println("3. Return to Main Menu");
        System.out.print("Enter your choice: ");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> editStudent(scanner, collector, student);
            case 2 -> {
                collector.deleteStudent(student);
                System.out.println("Student deleted successfully!");
            }
            case 3 -> System.out.println("Returning to main menu...");
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void editStudent(Scanner scanner, Collector collector, Student student) {
        System.out.println("\n=== Edit Student ===");
        System.out.println("1. Edit Last Name");
        System.out.println("2. Edit First Name");
        System.out.println("3. Edit Date of Birth");
        System.out.println("4. Edit Academic Level");
        System.out.print("Enter your choice: ");

        int choice = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();

        collector.editStudent(student, choice, newValue);
        System.out.println("Student updated successfully!");
    }
}
