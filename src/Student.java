
public class Student {
    int id;
    String status;
    String firstName;
    String lastName;
    String dateOfBirth;
    String universityLevel;
    int numLevel;
    public Student(String status){
        this.status = status;
    }
    public Student (int id, String lastName, String firstName, String dateOfBirth, String universityLevel){
        this.id = id;
        status = "O";
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.universityLevel = universityLevel;
        switch (universityLevel) {
            case "FR" -> numLevel = 0;
            case "SO" -> numLevel = 1;
            case "JR" -> numLevel = 2;
            case "SR" -> numLevel = 3;
            default -> numLevel = -1;
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", universityLevel='" + universityLevel + '\'' +
                '}';
    }
}