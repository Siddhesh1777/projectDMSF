import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class StudentTableModel extends AbstractTableModel{

    private FullStudentObject[] student;

    public StudentTableModel(FullStudentObject[] student) {
        this.student= student;
    }

    @Override
    public int getRowCount() {
        return student.length;
    }

    @Override
    public int getColumnCount() {
        return FullStudentObject.getColumnNames().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FullStudentObject fso = student[rowIndex];
        switch (columnIndex) {
            case 0:
                return fso.getName();
            case 1:
                return fso.getGradTerm();
            case 2:
                return String.join(", ", fso.getInterests());
            case 3:
                return String.join(", ", fso.getPhoneNumbers());
            case 4:
                return String.join(", ", fso.getEmails());
            case 5:
                return String.join(", ", fso.getMajors());
            case 6:
                return String.join(", ", fso.getProjects());
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return FullStudentObject.getColumnNames()[column];
    }
}
