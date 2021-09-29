/*
 * ISTE 330
 * Professor Jim Habermas
 * Names: Justin Stein, Siddhesh Periaswami
 * Group Data Layer Submission
 */
public class ProjectObject {

    private String projectName;
    private String projectAbstract;
    private String leadFaculty;
    private int projectId;

    public static String[] getColumnNames() {
        return new String[]{"Project Name", "Project Abstract", "Lead Faculty"};
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAbstract() {
        return projectAbstract;
    }

    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    public String getLeadFaculty() {
        return leadFaculty;
    }

    public void setLeadFaculty(String leadFaculty) {
        this.leadFaculty = leadFaculty;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return "Project Name: " + projectName + ", Project Abstract: " + projectAbstract + ", Lead Faculty: " + leadFaculty;
    }
}
