import java.sql.PreparedStatement;
import java.util.Arrays;

/*
 * ISTE 330
 * Professor Jim Habermas
 * Names: Justin Stein, Siddhesh Periaswami
 * Group Data Layer Submission
 */
public class FullStudentObject {

    private int studentId;
    private String name;
    private String gradTerm;
    private String[] emails;
    private String[] phoneNumbers;
    private String[] projects;
    private String[] majors;
    private String[] interests;

    public static String[] getColumnNames() {
        return new String[]{"Name", "Grad Term", "Interests", "Phone Numbers", "Emails", "Majors", "Projects"};
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getGradTerm() {
        return gradTerm;
    }

    public void setGradTerm(String gradTerm) {
        this.gradTerm = gradTerm;
    }

    public String[] getEmails() {
        return emails;
    }

    public void setEmails(String[] emails) {
        this.emails = emails;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String[] getProjects() {
        return projects;
    }

    public void setProjects(String[] projects) {
        this.projects = projects;
    }

    public String[] getMajors() {
        return majors;
    }

    public void setMajors(String[] majors) {
        this.majors = majors;
    }

    public String[] getInterests() {
        return interests;
    }

    public void setInterests(String[] interests) {
        this.interests = interests;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Override
    public String toString() {
        return "FullStudentObject{" +
                "name='" + name + '\'' +
                ", gradTerm='" + gradTerm + '\'' +
                ", emails=" + Arrays.toString(emails) +
                ", phoneNumbers=" + Arrays.toString(phoneNumbers) +
                ", projects=" + Arrays.toString(projects) +
                ", majors=" + Arrays.toString(majors) +
                ", interests=" + Arrays.toString(interests) +
                '}';
    }

}
