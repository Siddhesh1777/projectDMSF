import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ProjectTableModel extends AbstractTableModel {

    private ProjectObject[] projects;

    public ProjectTableModel(ProjectObject[] projects) {
        this.projects = projects;
    }

    @Override
    public int getRowCount() {
        return projects.length;
    }

    @Override
    public int getColumnCount() {
        return ProjectObject.getColumnNames().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProjectObject po = projects[rowIndex];
        switch (columnIndex) {
            case 0:
                return po.getProjectName();
            case 1:
                return po.getProjectAbstract();
            case 2:
                return po.getLeadFaculty();
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return ProjectObject.getColumnNames()[column];
    }
}
