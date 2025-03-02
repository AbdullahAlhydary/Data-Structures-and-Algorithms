
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Collector {
    Student [][] levels;
    int capacity = 151;
    Map<String, ArrayList<Integer>> firstNameMap;
    Map<String, ArrayList<Integer>> lastNameMap;
    Map<Integer, Integer> IdMap;
    public Collector (){
        levels = new Student[4][capacity];
        firstNameMap  = new HashMap<>();
        lastNameMap  = new HashMap<>();
        IdMap = new HashMap<>();
        for(int i=0 ; i<4 ; i++){
            for(int j=0 ; j<capacity ; j++){
                levels[i][j] = new Student("E");
            }
        }
        Student[] students = ExcelReader.loadStudentsInfo();
        downloadStudents(students);
    }
    private void downloadStudents (Student[] st){
        for (Student student : st) {
            if (student != null) {
                //--------------------------------------- Processing the 2D levels array
                int baseIndex = student.id % capacity;
                int index = findNextAvailableSlot(baseIndex, student.numLevel);
                levels[student.numLevel][index] = student;

                //--------------------------------------- Processing the firstNameMap map
                int identifierIndex = index + student.numLevel * 10000;
                if (firstNameMap.containsKey(student.firstName)) {
                    firstNameMap.get(student.firstName).add(identifierIndex);
                } else {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(identifierIndex);
                    firstNameMap.put(student.firstName, temp);
                }

                //--------------------------------------- Processing the lastNameMap map
                if (lastNameMap.containsKey(student.lastName)) {
                    lastNameMap.get(student.lastName).add(identifierIndex);
                } else {
                    ArrayList<Integer> temp = new ArrayList<>();
                    temp.add(identifierIndex);
                    lastNameMap.put(student.lastName, temp);
                }

                //--------------------------------------- Processing the IdMap map
                IdMap.put(student.id, identifierIndex);
            }
        }
    }
    private int findNextAvailableSlot(int currentSlot, int levelNumber){
        // ------------------------------------- Quadratic Probing
        for(int i=0 ; i<=(capacity-1)/2 ; i++){
            int temp = (currentSlot - i*i)%capacity;
            if(temp < 0){
                temp = (temp + capacity)%capacity;
            }
            if(!levels[levelNumber][(currentSlot + i*i)%capacity].status.equals("O")){
                return currentSlot + i*i;
            }else if(!levels[levelNumber][temp].status.equals("O")){
                return temp;
            }
        }
        return -1;
    }


    public void addNewStudent(Student st){
        Student[] students = {st};
        downloadStudents(students);
    }

    public void showStudentsInAcademicLevel(String level){
        int wantedLevel = -1;
        switch (level) {
            case "FR" -> wantedLevel = 0;
            case "SO" -> wantedLevel = 1;
            case "JR" -> wantedLevel = 2;
            case "SR" -> wantedLevel = 3;
            default -> System.out.println("Sorry! .. Make sure that you enter a valid entry");
        }
        if(wantedLevel != -1){
            System.out.println("Students in "+level+ "level:");
            System.out.println("  ID  " + "   Last Name  " + "   Firs Name  "+ "  Date of Brith ");
            for(int i=0 ; i<capacity ; i++){
                Student st = levels[wantedLevel][i];
                if(st != null && st.status.equals("O")){
                    System.out.println(" " + st.id + "   "+st.lastName+"       "+st.firstName+
                            "        "+st.dateOfBirth);
                }
            }
        }
    }


    public Student searchByID(int id){
        if (!IdMap.containsKey(id)) {
            return null; // Return null if the ID doesn't exist
        }
        int level = IdMap.get(id)/10000;
        int index = IdMap.get(id) % 10000;
        return levels[level][index];
    }
    public Student[] searchByFirstName(String firstName) {
        if (!firstNameMap.containsKey(firstName)) {
            return new Student[0]; // Return an empty array if the first name doesn't exist
        }

        // Retrieve the list of students
        Student[] names = new Student[firstNameMap.get(firstName).size()];
        int counter = 0;
        for (int index : firstNameMap.get(firstName)) {
            names[counter] = levels[index / 10000][index % 10000];
            counter++;
        }
        return names;
    }


    public Student[] searchByLastName(String lastName) {
        if (!lastNameMap.containsKey(lastName)) {
            return new Student[0]; // Return an empty array if the last name doesn't exist
        }

        // Retrieve the list of students
        Student[] names = new Student[lastNameMap.get(lastName).size()];
        int counter = 0;
        for (int index : lastNameMap.get(lastName)) {
            names[counter] = levels[index / 10000][index % 10000];
            counter++;
        }
        return names;
    }

    public void deleteStudent(Student stu) {
        int index = IdMap.get(stu.id) % 10000;
        levels[stu.numLevel][index].status = "D";
        // ------------------------------------Retrieve the identifierIndex from the IdMap
        int identifierIndex = IdMap.get(stu.id);

        // ------------------------------------Remove the identifierIndex from firstNameMap
        firstNameMap.get(stu.firstName).remove((Integer) identifierIndex);

        // ------------------------------------Remove the identifierIndex from lastNameMap
        lastNameMap.get(stu.lastName).remove((Integer) identifierIndex);
        IdMap.remove(stu.id);
    }



    public void editStudent (Student stu, int dataNum, String data){
        if(dataNum == 1){
            int index = IdMap.get(stu.id);
            lastNameMap.get(stu.lastName).remove(index);
            levels[index/10000][index%10000].lastName = data;
            if(lastNameMap.containsKey(data)){
                lastNameMap.get(data).add(index);
            }else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(index);
                lastNameMap.put(data, temp);
            }
        }else if(dataNum == 2){
            int index = IdMap.get(stu.id);
            firstNameMap.get(stu.firstName).remove(index);
            levels[index/10000][index%10000].firstName = data;
            if(firstNameMap.containsKey(data)){
                firstNameMap.get(data).add(index);
            }else {
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(index);
                firstNameMap.put(data, temp);
            }
        }else if(dataNum == 3){
            int index = IdMap.get(stu.id);
            levels[index/10000][index%10000].dateOfBirth = data;
        }else if(dataNum == 4){
            Student temp = new Student(stu.id, stu.lastName, stu.firstName , stu.dateOfBirth, data);
            deleteStudent(stu);
            addNewStudent(temp);
        }

    }
}
