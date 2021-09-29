import java.sql.Date;
import java.util.Arrays;

/*
 * ISTE 330
 * Professor Jim Habermas
 * Names: Justin Stein, Siddhesh Periaswami
 * Group Data Layer Submission
 */
public class FacultyObject {

    private int facultyId;
    private String name;
    private String password;
    private Date employmentStartDate;
    private String[] departments;
    private String[] phoneNumbers;
    private String[] emails;
    private String[] abstractTexts;
    private String[] projects;

    public static String[] getColumnNames() {
        return new String[]{"Name", "Employment Start Date", "Departments", "Phone Numbers", "Emails", "Projects"};
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public String[] getDepartments() {
        return departments;
    }

    public void setDepartments(String[] departments) {
        this.departments = departments;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public String[] getAbstractTexts() {
        return abstractTexts;
    }

    public void setAbstractTexts(String[] abstractTexts) {
        this.abstractTexts = abstractTexts;
    }

    public String[] getProjects() {
        return projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "FacultyObject{" +
                "name='" + name + '\'' +
                ", employmentStartDate=" + employmentStartDate +
                ", departments=" + Arrays.toString(departments) +
                ", phoneNumbers=" + Arrays.toString(phoneNumbers) +
                ", emails=" + Arrays.toString(emails) +
                ", abstractTexts=" + Arrays.toString(abstractTexts) +
                ", projects=" + Arrays.toString(projects) +
                ", id=" + this.facultyId +
                '}';
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getPassword() {
        System.out.println("Object PW: " + password);
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAbstractsForPrint() {
        String test = "";
        System.out.println(Arrays.toString(abstractTexts));
        for (int i = 0; i < abstractTexts.length; i++) {
            String abstractText = abstractTexts[i];
            test += "Abstract " + (i+1) + "\n";
            test += abstractText + "\n";
            if (i != abstractTexts.length - 1) {
                test += "\n";
            }
        }
        return test;
    }

}
