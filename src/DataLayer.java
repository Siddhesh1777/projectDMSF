import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * ISTE 330
 * Professor Jim Habermas
 * Names: Justin Stein, Siddhesh Periaswami
 * Group Data Layer Submission
 */
public class DataLayer {

    private Connection conn;

    public void loadDriver() throws ClassNotFoundException {
        // Step 2) Load a driver
        //Class.forName method returns the Class object
        //associated with the class or interface
        //with the given string name,
        //using the given class loader.
        String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
        Class.forName(DEFAULT_DRIVER);
        System.out.println("\nDriver Loaded " + DEFAULT_DRIVER + "\n");
    }// end of method loadDriver()

    public void connect(String url, String userName, String password) {
        boolean connected = false;

        try {
            loadDriver();
            conn = DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);
            connected = true;
        }// end of try block
        catch (Exception e) {
            System.out.println("\nERROR CAN NOT MAKE CONNECTION");
            System.out.println("ERROR WAS " + e + "\n");
            e.printStackTrace();
        }// end of catch

        if (connected) {
            String temporary = "Your DB Connection is live!";
            System.out.println(temporary);
        } else {
            System.out.println("Unable to connect to data source");
        }// end of if
    }//end of testConnection method()

    public ArrayList<AbstractObject> getAbstractsForUser(int userId) {
        String sql = "SELECT AbstractID, FacultyID, AbstractText FROM Abstract WHERE FacultyID = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet abstractResults = preparedStatement.executeQuery();
            ArrayList<AbstractObject> abstractObjects = new ArrayList<>();
            while (abstractResults.next()) {
                AbstractObject abstractObject = new AbstractObject();
                abstractObject.setAbstractId(abstractResults.getInt(1));
                abstractObject.setAbstractText(abstractResults.getString(3));
                abstractObjects.add(abstractObject);
            }
            conn.commit();
            return abstractObjects;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public ArrayList<ProjectObject> getProjects() {
        String sql = "SELECT ProjectName AS 'Project Name', Abstract AS 'Project Abstract', CONCAT(LastName, ', ', FirstName) AS 'Lead Faculty', ProjectID FROM Project INNER JOIN Person ON Project.LeadFacultyID = Person.PersonID";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ArrayList<ProjectObject> projects = new ArrayList<>();
            while (resultSet.next()) {
                ProjectObject project = new ProjectObject();
                project.setProjectName(resultSet.getString(1));
                project.setProjectAbstract(resultSet.getString(2));
                project.setLeadFaculty(resultSet.getString(3));
                project.setProjectId(resultSet.getInt(4));
                projects.add(project);
            }

            conn.commit();

            return projects;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;

    }

    public ArrayList<ProjectObject> getProjectsForUser(int userId) {
        String sql = "SELECT ProjectName AS 'Project Name', Abstract AS 'Project Abstract', CONCAT(LastName, ', ', FirstName) AS 'Lead Faculty', ProjectID FROM Project INNER JOIN Person ON Project.LeadFacultyID = Person.PersonID WHERE LeadFacultyID = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<ProjectObject> projects = new ArrayList<>();
            while (resultSet.next()) {
                ProjectObject project = new ProjectObject();
                project.setProjectName(resultSet.getString(1));
                project.setProjectAbstract(resultSet.getString(2));
                project.setLeadFaculty(resultSet.getString(3));
                project.setProjectId(resultSet.getInt(4));
                projects.add(project);
            }

            conn.commit();

            return projects;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;

    }

    public ArrayList<FullStudentObject> getStudentsInProject(int projectId) {
        String sql = "SELECT PersonID, CONCAT(FirstName, ' ', LastName) AS Name FROM studentproject INNER JOIN person USING (PersonID) WHERE ProjectID = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<FullStudentObject> students = new ArrayList<>();
            while (resultSet.next()) {
                FullStudentObject fso = new FullStudentObject();
                fso.setStudentId(resultSet.getInt(1));
                fso.setName(resultSet.getString(2));
                students.add(fso);
            }
            conn.commit();
            return students;

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public boolean addStudentToProject(int projectId, int studentId) {
        String sql = "INSERT INTO studentproject VALUES (?, ?)";
        int updated = -1;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, projectId);
            updated = preparedStatement.executeUpdate();
            conn.commit();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return updated > 0;
    }

    public ArrayList<DepartmentObject> getDepartments() {
        String sql = "SELECT * FROM department";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ArrayList<DepartmentObject> departments = new ArrayList<>();
            while (resultSet.next()) {
                DepartmentObject departmentObject = new DepartmentObject();
                departmentObject.setDepartmentId(resultSet.getInt(1));
                departmentObject.setDepartmentName(resultSet.getString(2));

                departments.add(departmentObject);
            }

            conn.commit();

            return departments;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public boolean createStudent(String firstName, String lastName, String gradTerm) {
        String personSql = "INSERT INTO Person (FirstName, LastName) VALUES (?, ?)";
        String studentSql = "INSERT INTO Student (PersonID, GradTerm) VALUES (?, ?)";

        try {
            PreparedStatement personPreparedStatement = conn.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
            personPreparedStatement.setString(1, firstName);
            personPreparedStatement.setString(2, lastName);

            personPreparedStatement.execute();

            ResultSet createdIds = personPreparedStatement.getGeneratedKeys();
            int generatedKey = -1;
            if (createdIds.next()) {
                generatedKey = createdIds.getInt(1);
            }

            System.out.println("ID of inserted record is: " + generatedKey);

            PreparedStatement studentPreparedStatement = conn.prepareStatement(studentSql);
            studentPreparedStatement.setInt(1, generatedKey);
            studentPreparedStatement.setString(2, gradTerm);
            int updated = studentPreparedStatement.executeUpdate();

            conn.commit();
            return updated > 0;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }

    public boolean insertEmail(int personId, String email) {
        int inserted = -1;
        try {
            String sql = "INSERT INTO Emails VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, email);
            inserted = preparedStatement.executeUpdate();
            conn.commit();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return inserted > 0;
    }

    public boolean deleteEmail(int personId, String email) {
        int deleted = -1;
        try {
            String sql = "DELETE FROM Emails WHERE PersonID = ? AND Email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, email);
            deleted = preparedStatement.executeUpdate();
            conn.commit();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return deleted > 0;
    }

    public ArrayList<MajorObject> getMajors() {
        String sql = "Select * from Major";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ArrayList<MajorObject> majors = new ArrayList<>();
            while (resultSet.next()) {
                MajorObject major = new MajorObject();
                //major.setName(resultSet.getString(1));
                major.setMajorId(resultSet.getInt(1));
                major.setMajorName(resultSet.getString(2));
                majors.add(major);
            }
            conn.commit();
            return majors;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;

    }
    public ArrayList<InterestObject> getInterests() {
        String sql = "Select * from Interest";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            ArrayList<InterestObject> interests = new ArrayList<>();
            while (resultSet.next()) {
                InterestObject interest = new InterestObject();
                interest.setInterestId(resultSet.getInt(1));
                interest.setInterestName(resultSet.getString(2));
                interests.add(interest);
            }

            conn.commit();
            return interests;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;

    }
    //Method to insert interests of students by students
    public int insertInterestStudent(int interestId, int studentId){
        int rows = 0;
        System.out.println("-----Inserting interests-----");
        try {
            String sql = "INSERT INTO StudentInterest VALUES (?,?)";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, studentId);
            preparedStatement.setInt(2, interestId);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Insert finished-----");

        }//try
        catch(SQLException sqle12) {
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of insert method

    //Method to delete interests of students by students
    public int deleteInterestStudent(int interestId, int studentId){
        int rows = 0;
        System.out.println("-----Deleting interests-----");
        try {
            String sql = "Delete from StudentInterest where InterestID = ? AND PersonID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            System.out.println("Command to be executed: " + sql);
            preparedStatement.setInt(1 , interestId);
            preparedStatement.setInt(2, studentId);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Deleted interests-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Delete FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of delete method

    //Method to insert majors of students by students
    public int insertMajorStudent(int majorId, int studentId){
        int rows = 0;
        System.out.println("-----Inserting Majors-----");
        try{
            String sql = "INSERT INTO StudentMajor VALUES(?,?)";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1 , studentId);
            preparedStatement.setInt(2 , majorId);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Insert finished-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of insert method

    //Method to delete majors of students by students
    public int deleteMajorStudent(int majorId, int studentId){
        int rows = 0;
        System.out.println("-----Deleting Majors-----");
        try{
            String sql = "DELETE FROM StudentMajor WHERE MajorID = ? AND PersonID = ?";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1 , majorId);
            preparedStatement.setInt(2, studentId);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Deleted majors-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Delete FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of delete method

    //Method to add abstracts by the faculty
    public int insertAbstract(int facultyID, String abstractText) {
        int rows = 0;
        System.out.println("-----Inserting abstracts-----");
        try{
            String sql = "INSERT INTO Abstract (FacultyID, AbstractText) VALUES(?,?);";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, facultyID);
            preparedStatement.setString(2, abstractText);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Insert finished-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of insert method

    //Method to delete abstracts by the faculty
    public int deleteAbstract(int abstractID, int facultyID){
        int rows = 0;
        System.out.println("-----Deleting abstracts-----");
        try{
            String sql = "Delete from Abstract where AbstractID = ? AND FacultyID = ?;";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1 , abstractID);
            preparedStatement.setInt(2 , facultyID);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Delete finished-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Delete FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of delete method

    //Method to add projects by the faculty
    public int insertProjectFaculty(int facultyID, int projectID){
        int rows = 0;
        System.out.println("-----Inserting projects-----");
        try{
            String sql = "INSERT INTO ProjectFaculty VALUES(?,?);";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1 , facultyID);
            preparedStatement.setInt(2 , projectID);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Insert finished-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of insert method

    //Method to add projects by the faculty
    public boolean insertProject(String projectName, String abstractName, int leadFacultyID){
        int created = -1;
        System.out.println("-----Inserting projects-----");
        try{
            String sql = "INSERT INTO Project (ProjectName, Abstract, LeadFacultyID) VALUES(?,?,?);";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, projectName);
            preparedStatement.setString(2, abstractName);
            preparedStatement.setInt(3, leadFacultyID);
            preparedStatement.execute();

            ResultSet createdIds = preparedStatement.getGeneratedKeys();
            int projectId = -1;
            if (createdIds.next()) {
                projectId = createdIds.getInt(1);
            }

            System.out.println("ID of inserted record is: " + projectId);

            String sql2 = "INSERT INTO ProjectFaculty VALUES (?, ?)";
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql2);
            preparedStatement1.setInt(1, leadFacultyID);
            preparedStatement1.setInt(2, projectId);
            preparedStatement1.executeUpdate();
            conn.commit();
            System.out.println("-----Insert finished-----");
            return created > 0;
        }//try
        catch(SQLException sqle12){
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
        }
        return false;
    }//end of insert method

    //Method to insert Faculty
    public int insertFaculty(String employmentStartDate, String password, String firstName, String lastName){
        int rows = 0;
        System.out.println("-----Inserting faculty-----");
        try{
            String personSql = "INSERT INTO Person (FirstName, LastName) VALUES(?,?);";
            String facultySql = "INSERT INTO Faculty VALUES(?,?,?)";
            System.out.println("Command to be executed: " + personSql);
            PreparedStatement preparedStatement = conn.prepareStatement(personSql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            preparedStatement.execute();

            ResultSet createdIds = preparedStatement.getGeneratedKeys();
            int generatedKey = -1;
            if (createdIds.next()) {
                generatedKey = createdIds.getInt(1);
            }

            System.out.println("ID of inserted record is: " + generatedKey);

            PreparedStatement preparedStatement1 = conn.prepareStatement(facultySql);
            preparedStatement1.setInt(1 , generatedKey);
            preparedStatement1.setString(2 , employmentStartDate);
            preparedStatement1.setString(3 , getPassword(password));
            preparedStatement1.execute();
            conn.commit();
            System.out.println("-----Insert finished-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of insert method

    public String getPassword(String secret)
    {

        String sha1 = "";
        String value = new String(secret);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(value.getBytes("utf8"));
            sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (Exception e){
            e.printStackTrace();
        }// end of catch
        System.out.println("Layer password: " + sha1);
        return sha1;
    }//end of function

    //Method to insert Phone number
    public int insertPhoneNumber(int personId, String phoneNumber){
        int rows = 0;
        System.out.println("-----Inserting phone number-----");
        try{
            String sql = "INSERT INTO PhoneNumbers VALUES(?,?);";
            System.out.println("Command to be executed: " + sql);
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1 , personId);
            preparedStatement.setString(2 , phoneNumber);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Insert finished-----");
        }//try
        catch(SQLException sqle12){
            System.out.println("Insert FAILED!!!!");
            System.out.println("ERROR MESSAGE IS -> "+sqle12);
            sqle12.printStackTrace();
            return(0);
        }
        return (rows);
    }//end of insert method

    public int deletePhoneNumber(int personId, String phoneNumber) {
        int rows = 0;
        System.out.println("-----Deleting phone number-----");
        try {
            String sql = "DELETE FROM PhoneNumbers WHERE PersonID = ? AND PhoneNumber = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, personId);
            preparedStatement.setString(2, phoneNumber);
            rows = preparedStatement.executeUpdate();
            conn.commit();
            System.out.println("-----Delete finished-----");
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return rows;
    }

    public boolean updateGradTerm(int personId, String gradTerm) {
        String sql = "UPDATE Student SET GradTerm = ? WHERE PersonID = ?";
        int updated = -1;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, gradTerm);
            preparedStatement.setInt(2, personId);

            updated = preparedStatement.executeUpdate();
            conn.commit();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return updated > 0;
    }

    public boolean updateFacultyPassword(int personId, String password) {
        String sql = "UPDATE Faculty SET Password = ? WHERE PersonID = ?";
        int updated = -1;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, getPassword(password));
            preparedStatement.setInt(2, personId);

            updated = preparedStatement.executeUpdate();
            conn.commit();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return updated > 0;
    }

    public Map<Integer, FacultyObject> getFacultyForStudentsMap() {
        String sql = "SELECT CONCAT(LastName, ', ', FirstName) AS 'Name', EmploymentStartDate AS 'Employment Start Date', COALESCE(GROUP_CONCAT(DISTINCT DepartmentName SEPARATOR ';; '), 'None') AS Departments, COALESCE(GROUP_CONCAT(DISTINCT PhoneNumber SEPARATOR ';; '), 'None') AS 'Phone Numbers', COALESCE(GROUP_CONCAT(DISTINCT Email SEPARATOR ';; '), 'None') AS Emails, COALESCE(GROUP_CONCAT(DISTINCT AbstractText SEPARATOR ';; '), 'None') AS 'Abstracts', COALESCE(GROUP_CONCAT(DISTINCT ProjectName SEPARATOR ';; '), 'None') AS 'Projects', Person.PersonID AS 'ID', Password FROM Person INNER JOIN faculty USING (PersonID) LEFT JOIN facultydepartment ON faculty.PersonID = facultydepartment.FacultyID LEFT JOIN department USING (DepartmentID) LEFT JOIN emails USING (PersonID) LEFT JOIN phonenumbers USING (PersonID) LEFT JOIN abstract ON abstract.FacultyID = person.PersonID LEFT JOIN projectfaculty ON projectfaculty.FacultyID = person.PersonID LEFT JOIN project USING (ProjectID) GROUP BY Person.PersonID";
//        String sql = "SELECT PersonID, Password FROM Faculty";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            Map<Integer, FacultyObject> faculty = new HashMap<>();
            while (resultSet.next()) {
                FacultyObject facultyObject = new FacultyObject();
                facultyObject.setFacultyId(resultSet.getInt(8));
                facultyObject.setName(resultSet.getString(1));
                facultyObject.setEmploymentStartDate(resultSet.getDate(2));
                facultyObject.setDepartments(resultSet.getString(3).split(";; "));
                facultyObject.setPhoneNumbers(resultSet.getString(4).split(";; "));
                facultyObject.setEmails(resultSet.getString(5).split(";; "));
                facultyObject.setAbstractTexts(resultSet.getString(6).split(";; "));
                facultyObject.setProjects(resultSet.getString(7).split(";; "));
                facultyObject.setPassword(resultSet.getString(9));

                faculty.put(facultyObject.getFacultyId(), facultyObject);
            }

            conn.commit();
            return faculty;

        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public Map<Integer, FullStudentObject> getStudentsMap() {
        String sql = "SELECT CONCAT(FirstName, ' ', LastName) AS 'Name', GradTerm AS 'Grad Term', COALESCE(GROUP_CONCAT(DISTINCT Email SEPARATOR ';; '), 'None') AS 'Emails', COALESCE(GROUP_CONCAT(DISTINCT PhoneNumber SEPARATOR ';; '), 'None') AS 'Phone Numbers', COALESCE(GROUP_CONCAT(DISTINCT ProjectName SEPARATOR ';; '), 'None') AS 'Projects', COALESCE(GROUP_CONCAT(DISTINCT MajorName SEPARATOR ';; '), 'None') AS 'Majors', COALESCE(GROUP_CONCAT(DISTINCT InterestName SEPARATOR ';; '), 'None') AS 'Interests', Person.PersonID FROM Person INNER JOIN Student USING (PersonID) LEFT JOIN Emails USING (PersonID) LEFT JOIN phonenumbers USING (PersonID) LEFT JOIN studentmajor USING (PersonID) LEFT JOIN major USING (MajorID) LEFT JOIN studentproject USING (PersonID) LEFT JOIN project USING (ProjectID) LEFT JOIN studentinterest USING (PersonID) LEFT JOIN interest USING (InterestID) GROUP BY Person.PersonID";
        String where = " WHERE Name = ?";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            Map<Integer, FullStudentObject> students = new HashMap<>();
            while (resultSet.next()) {
                FullStudentObject fullStudentObject = new FullStudentObject();
                fullStudentObject.setStudentId(resultSet.getInt(8));
                fullStudentObject.setName(resultSet.getString(1));
                fullStudentObject.setGradTerm(resultSet.getString(2));
                fullStudentObject.setEmails(resultSet.getString(3).split(";; "));
                fullStudentObject.setPhoneNumbers(resultSet.getString(4).split(";; "));
                fullStudentObject.setProjects(resultSet.getString(5).split(";; "));
                fullStudentObject.setMajors(resultSet.getString(6).split(";; "));
                fullStudentObject.setInterests(resultSet.getString(7).split(";; "));
                students.put(fullStudentObject.getStudentId(), fullStudentObject);
            }
            System.out.println("Data Layer: " + students.toString());
            conn.commit();
            return students;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public Map<String, ProjectObject> getProjectsMap() {
        String sql = "SELECT ProjectName AS 'Project Name', Abstract AS 'Project Abstract', CONCAT(LastName, ', ', FirstName) AS 'Lead Faculty', ProjectID FROM Project INNER JOIN Person ON Project.LeadFacultyID = Person.PersonID";
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sql);
            //ArrayList<ProjectObject> projects = new ArrayList<>();
            Map<String, ProjectObject> projects = new HashMap<>();
            while (resultSet.next()) {
                ProjectObject project = new ProjectObject();
                project.setProjectName(resultSet.getString(1));
                project.setProjectAbstract(resultSet.getString(2));
                project.setLeadFaculty(resultSet.getString(3));
                project.setProjectId(resultSet.getInt(4));
                //projects.add(project);
                projects.put(project.getProjectName(),project);
            }

            conn.commit();

            return projects;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;

    }

}
