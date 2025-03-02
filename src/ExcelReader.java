import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExcelReader {
    private static final String thePath = "src/students_details.csv";

    public static Student[] loadStudentsInfo() {
        Student[] students = new Student[350];
        int counter=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(thePath))) {
            String line;
            boolean firstLine = true; // Flag to skip the first line
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] cells = line.split(","); // Split the line by comma
                int id = Integer.parseInt(cells[0]);
                students[counter]  = new Student(id, cells[1], cells[2], cells[3], cells[4]);
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return students;
    }

}
