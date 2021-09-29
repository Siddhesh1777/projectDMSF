import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class FacultyTableModel extends AbstractTableModel {

    private FacultyObject[] faculty;

    public FacultyTableModel(FacultyObject[] faculty) {
        this.faculty = faculty;
    }

    @Override
    public int getRowCount() {
        return faculty.length;
    }

    @Override
    public int getColumnCount() {
        return FacultyObject.getColumnNames().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FacultyObject fo = faculty[rowIndex];
        switch (columnIndex) {
            case 0:
                return fo.getName();
            case 1:
                return fo.getEmploymentStartDate();
            case 2:
                return String.join(", ", fo.getDepartments());
            case 3:
                return String.join(", ", fo.getPhoneNumbers());
            case 4:
                return String.join(", ", fo.getEmails());
            case 5:
                return String.join(", ", fo.getProjects());
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return FacultyObject.getColumnNames()[column];
    }
}
