import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/*
 * ISTE 330
 * Professor Jim Habermas
 * Names: Justin Stein, Siddhesh Periaswami
 * Group Presentation Layer Submission
 */
public class CustomPresentationLayer extends JFrame{
    private JLabel jl2;
    private JLabel choiceLabel;
    private JLabel jl3;
    private String data;
    private JTable table;
    AbstractTableModel profileModel;
    public CustomPresentationLayer() {

        DataLayer dataLayer = new DataLayer();
        dataLayer.connect("jdbc:mysql://localhost:3306/iste330groupproject4", "root", "student");

        boolean loggedIn = false;
        Map<Integer, FacultyObject> facultyMap = dataLayer.getFacultyForStudentsMap();
        Map<Integer, FullStudentObject> studentsMap = dataLayer.getStudentsMap();
        int userId = -1;
        while (!loggedIn) {
            userId = Integer.parseInt(JOptionPane.showInputDialog("Enter your ID."));
            if (facultyMap.containsKey(userId)) {
                String password = JOptionPane.showInputDialog("Please enter your faculty password.");
                if (dataLayer.getPassword(password).equals(facultyMap.get(userId).getPassword())) {
                    JOptionPane.showMessageDialog(null, "Welcome to the database, " + facultyMap.get(userId).getName() + "!");
                    loggedIn = true;
                }
                else {
                    JOptionPane.showMessageDialog(null, "Incorrect password! Please try again.");
                }
            }
            else if (studentsMap.containsKey(userId)) {
                JOptionPane.showMessageDialog(null, "Welcome to the database, " + studentsMap.get(userId).getName() + "!");
                loggedIn = true;
            }
            else {
                JOptionPane.showMessageDialog(null, "ERROR! ID not found in the system! Please try again. If you think your user ID should exist, exit the program and try again.");
            }
        }

        StudentTableModel studentTableModel = new StudentTableModel(dataLayer.getStudentsMap().values().toArray(new FullStudentObject[0]));

//        table = new JTable(studentTableModel);
//        table.setSize(1000,450);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
//        JPopupMenu popupMenu = getFacultyPopupMenu(table, faculty);
//
//        table.setComponentPopupMenu(popupMenu);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
//        JFrame window = new JFrame();
//        window.setTitle("ISTE-330 Group Project Research Data System");
//        window.setSize(1361, 500);
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(3,1));
        JPanel choicePanel = new JPanel();
        choiceLabel = new JLabel("Choose: ");
        choicePanel.add(choiceLabel);
        String[] choices ={"My Profile","Display Students","Display Faculty","Display Projects"};
        JComboBox choicesBox = new JComboBox(choices);
        choicesBox.setBounds(50, 50,90,20);
        choicePanel.add(choicesBox);
        mainPanel.add(choicePanel);
        add(mainPanel,BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");

        refreshButton.setSize(50,50);
        buttonPanel.add(refreshButton);
        mainPanel.add(buttonPanel);
        add(mainPanel);

        JPanel contentPanel = new JPanel();
        jl2 = new JLabel("");
        contentPanel.add(jl2);
        //contentPanel.add(refreshButton, BorderLayout.NORTH);
        mainPanel.add(contentPanel);
        add(mainPanel,BorderLayout.CENTER);
        int finalUserId = userId;
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                /*if (choicesBox.getItemAt(choicesBox.getSelectedIndex()).equals("My Profile")) {
                    if (facultyMap.containsKey(UserId)) {
                        Map<Integer, FacultyObject> facultyMap = dataLayer.getFacultyForStudentsMap();
                        FacultyTableModel facultyTableModel = new FacultyTableModel(dataLayer.getFacultyForStudentsMap().values().toArray(new FacultyObject[0]));
                        profileModel = new FacultyTableModel(new FacultyObject[]{dataLayer.getFacultyForStudentsMap().get(UserId)});
                        mainPanel.remove(contentPanel);
                        mainPanel.add(contentPanel);
                        contentPanel.removeAll();
                        contentPanel.setLayout(new FlowLayout());
                        table = new JTable(profileModel);
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                        profileModel.fireTableDataChanged();
                        JPopupMenu popupMenu = getFacultyProfilePopupMenu(table, dataLayer.getFacultyForStudentsMap().values().toArray(new FacultyObject[0]), dataLayer);
                        table.setComponentPopupMenu(popupMenu);
                        JScrollPane jsp = new JScrollPane(table);
                        jsp.setPreferredSize(new Dimension(1400, 700));
                        contentPanel.add(jsp);
                        mainPanel.add(contentPanel);
                        validate();
                    } else {
                        Map<Integer, FullStudentObject> studentsMap = dataLayer.getStudentsMap();
                        StudentTableModel studentTableModel = new StudentTableModel(dataLayer.getStudentsMap().values().toArray(new FullStudentObject[0]));
                        profileModel = new StudentTableModel(new FullStudentObject[]{studentsMap.get(UserId)});
                        mainPanel.remove(contentPanel);
                        mainPanel.add(contentPanel);
                        contentPanel.removeAll();
                        contentPanel.setLayout(new FlowLayout());
                        table = new JTable(profileModel);
                        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                        profileModel.fireTableDataChanged();
                        JPopupMenu popupMenu = getStudentProfilePopupMenu(table, dataLayer.getStudentsMap().values().toArray(new FullStudentObject[0]), dataLayer);
                        table.setComponentPopupMenu(popupMenu);
                        JScrollPane jsp = new JScrollPane(table);
                        jsp.setPreferredSize(new Dimension(1400, 700));
                        contentPanel.add(jsp);
                        mainPanel.add(contentPanel);
                        validate();
                    }
                }*/
                stuff(dataLayer, mainPanel, contentPanel, choicesBox, finalUserId);

            }
        });

        setVisible(true);
        pack();
        setTitle("ISTE-330 Group Project Research Data System");
        setSize(1450, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPopupMenu popupMenu;
        if(facultyMap.containsKey(userId)) {
            profileModel = new FacultyTableModel(new FacultyObject[]{facultyMap.get(userId)});
            table = new JTable(profileModel);
            popupMenu = getFacultyProfilePopupMenu(table, new FacultyObject[]{facultyMap.get(userId)}, dataLayer);
        }
        else {
            profileModel = new StudentTableModel(new FullStudentObject[]{studentsMap.get(userId)});
            table = new JTable(profileModel);
            popupMenu = getStudentProfilePopupMenu(table, new FullStudentObject[]{studentsMap.get(userId)}, dataLayer);
        }

        mainPanel.remove(contentPanel);
        mainPanel.add(contentPanel);
        contentPanel.removeAll();
        contentPanel.setLayout(new FlowLayout());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.setComponentPopupMenu(popupMenu);
        JScrollPane jsp = new JScrollPane(table);
        jsp.setPreferredSize(new Dimension(1400,700));
        contentPanel.add(jsp);
        mainPanel.add(contentPanel);
        validate();

        int finalUserId1 = userId;
        choicesBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stuff(dataLayer, mainPanel, contentPanel, choicesBox, finalUserId1);
            }

        });
    }

    private void stuff(DataLayer dataLayer, JPanel mainPanel, JPanel contentPanel, JComboBox choicesBox, int finalUserId) {
        data = ""
                + choicesBox.getItemAt(choicesBox.getSelectedIndex());
        jl2.setText("");
        if (data.equals("Display Students")){
            Map<Integer, FullStudentObject> studentsMap = dataLayer.getStudentsMap();
            StudentTableModel studentTableModel = new StudentTableModel(dataLayer.getStudentsMap().values().toArray(new FullStudentObject[0]));
            mainPanel.remove(contentPanel);
            mainPanel.add(contentPanel);
            contentPanel.removeAll();
            contentPanel.setLayout(new FlowLayout());
            table = new JTable(studentTableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setPreferredSize(new Dimension(1400,700));
            contentPanel.add(jsp);
            mainPanel.add(contentPanel);
            validate();


        }
        else if (data.equals("Display Projects")){
            Map<String, ProjectObject> projectMap = dataLayer.getProjectsMap();
            mainPanel.remove(contentPanel);
            mainPanel.add(contentPanel);
            contentPanel.removeAll();
            contentPanel.setLayout(new FlowLayout());
            ProjectTableModel projectTableModel = new ProjectTableModel(dataLayer.getProjectsMap().values().toArray(new ProjectObject[0]));
            table = new JTable(projectTableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            JPopupMenu popupMenu = projectPopupMenu(table, projectMap.values().toArray(new ProjectObject[0]), dataLayer);
            table.setComponentPopupMenu(popupMenu);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setPreferredSize(new Dimension(1400,700));
            contentPanel.add(jsp);
            mainPanel.add(contentPanel);
            validate();
        }
        else if (data.equals("Display Faculty")){
            Map<Integer, FacultyObject> facultyMap = dataLayer.getFacultyForStudentsMap();
            mainPanel.remove(contentPanel);
            mainPanel.add(contentPanel);
            contentPanel.removeAll();
            contentPanel.setLayout(new FlowLayout());
            FacultyTableModel facultyTableModel = new FacultyTableModel(dataLayer.getFacultyForStudentsMap().values().toArray(new FacultyObject[0]));
            table = new JTable(facultyTableModel);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
            JPopupMenu popupMenu = getFacultyPopupMenu(table, dataLayer.getFacultyForStudentsMap().values().toArray(new FacultyObject[0]));
            table.setComponentPopupMenu(popupMenu);
            JScrollPane jsp = new JScrollPane(table);
            jsp.setPreferredSize(new Dimension(1400,700));
            contentPanel.add(jsp);
            mainPanel.add(contentPanel);
            validate();
        }
        else if (data.equals("My Profile")||data.equals("")){
            AbstractTableModel profileModel;
            if(dataLayer.getFacultyForStudentsMap().containsKey(finalUserId)) {
                profileModel = new FacultyTableModel(new FacultyObject[]{dataLayer.getFacultyForStudentsMap().get(finalUserId)});
                mainPanel.remove(contentPanel);
                mainPanel.add(contentPanel);
                contentPanel.removeAll();
                contentPanel.setLayout(new FlowLayout());
                table = new JTable(profileModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                JPopupMenu popupMenu = getFacultyProfilePopupMenu(table, dataLayer.getFacultyForStudentsMap().values().toArray(new FacultyObject[0]), dataLayer);
                table.setComponentPopupMenu(popupMenu);
                JScrollPane jsp = new JScrollPane(table);
                jsp.setPreferredSize(new Dimension(1400,700));
                contentPanel.add(jsp);
                mainPanel.add(contentPanel);
                validate();
            }
            else {
                profileModel = new StudentTableModel(new FullStudentObject[]{dataLayer.getStudentsMap().get(finalUserId)});
                mainPanel.remove(contentPanel);
                mainPanel.add(contentPanel);
                contentPanel.removeAll();
                contentPanel.setLayout(new FlowLayout());
                table = new JTable(profileModel);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                JPopupMenu popupMenu = getStudentProfilePopupMenu(table, dataLayer.getStudentsMap().values().toArray(new FullStudentObject[0]), dataLayer);
                table.setComponentPopupMenu(popupMenu);
                JScrollPane jsp = new JScrollPane(table);
                jsp.setPreferredSize(new Dimension(1400,700));
                contentPanel.add(jsp);
                mainPanel.add(contentPanel);
                validate();
            }

        }
    }

    private JPopupMenu getFacultyPopupMenu(JTable table, FacultyObject[] faculty) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem showAbstractsButton = new JMenuItem("Show Abstracts");
        popupMenu.add(showAbstractsButton);
        showAbstractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected row: " + table.getSelectedRow());
                FacultyObject fo = faculty[table.getSelectedRow()];
                JOptionPane.showMessageDialog(null, fo.getAbstractsForPrint());
            }
        });
        return popupMenu;
    }
    private JPopupMenu projectPopupMenu(JTable table, ProjectObject[] projects, DataLayer dataLayer) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem showAbstractsButton = new JMenuItem("Show Students");
        popupMenu.add(showAbstractsButton);
        showAbstractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectObject po = projects[table.getSelectedRow()];
                String printString = "";
                System.out.println(po.getProjectId());
                ArrayList<FullStudentObject> students = dataLayer.getStudentsInProject(po.getProjectId());
                System.out.println(students.size());
                for (FullStudentObject fso : students) {
                    System.out.println("Student: " + fso.getName());
                    printString += fso.getName() + "\n";
                }
                JOptionPane.showMessageDialog(getParent(), printString);
            }
        });
        return popupMenu;
    }

    private JPopupMenu getStudentProfilePopupMenu(JTable table, FullStudentObject[] student, DataLayer dataLayer) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem insertMajorsButton = new JMenuItem("Insert Majors");
        JMenuItem insertInterestsButton = new JMenuItem("Insert Interests");
        JMenuItem insertPhoneNumbersButton = new JMenuItem("Insert Phone Numbers");
        JMenuItem insertEmailButton = new JMenuItem("Insert Email");
        JMenuItem deleteMajorsButton = new JMenuItem("Delete Majors");
        JMenuItem deleteInterestsButton = new JMenuItem("Delete Interests");
        JMenuItem deletePhoneNumbersButton = new JMenuItem("Delete Phone Numbers");
        JMenuItem deleteEmailButton = new JMenuItem("Delete Email");
        JMenuItem updateGradTermButton = new JMenuItem("Update Grad Term");
        popupMenu.add(updateGradTermButton);
        popupMenu.add(insertMajorsButton);
        popupMenu.add(insertInterestsButton);
        popupMenu.add(insertPhoneNumbersButton);
        popupMenu.add(insertEmailButton);
        popupMenu.add(deleteMajorsButton);
        popupMenu.add(deleteInterestsButton);
        popupMenu.add(deletePhoneNumbersButton);
        popupMenu.add(deleteEmailButton);

        updateGradTermButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gradTerm = "";
                while (gradTerm.equals("")) {
                    gradTerm = JOptionPane.showInputDialog("Please enter your new grad term.");
                }

                boolean updated = dataLayer.updateGradTerm(student[table.getSelectedRow()].getStudentId(), gradTerm);
                if (updated) JOptionPane.showMessageDialog(null, "Grad term updated. Please click refresh to see changes.");
            }
        });

        insertMajorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object major = null;
                FullStudentObject studentObject = student[table.getSelectedRow()];
                ArrayList<MajorObject> majorObjectArrayList = dataLayer.getMajors();
                ArrayList<MajorObject> options = new ArrayList<>();
                for (MajorObject obj : majorObjectArrayList) {
                    if (Arrays.stream(studentObject.getMajors()).noneMatch(obj.getMajorName()::equals)) {
                        options.add(obj);
                    }
                }
                MajorObject[] optionsArr = new MajorObject[options.size()];
                for (int i = 0; i < optionsArr.length; i++) {
                    optionsArr[i] = options.get(i);
                }
                while (null == major) {
                    major = JOptionPane.showInputDialog(null,
                            "Please select a major",
                            "Major", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                }
                int majorAdd = dataLayer.insertMajorStudent(((MajorObject) major).getMajorId(), student[table.getSelectedRow()].getStudentId());
                if (majorAdd >= 1) {
                    JOptionPane.showMessageDialog(getParent(), "Major inserted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not add major. Please try again.");
                }

            }
        });
        insertInterestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object interest = null;
                FullStudentObject studentObject = student[table.getSelectedRow()];
                ArrayList<InterestObject> interestObjectArrayList = dataLayer.getInterests();
                ArrayList<InterestObject> options = new ArrayList<>();
                for (InterestObject obj : interestObjectArrayList) {
                    if (Arrays.stream(studentObject.getInterests()).noneMatch(obj.getInterestName()::equals)) {
                        options.add(obj);
                    }
                }
                InterestObject[] optionsArr = new InterestObject[options.size()];
                for (int i = 0; i < optionsArr.length; i++) {
                    optionsArr[i] = options.get(i);
                }
                while (null == interest) {
                    interest = JOptionPane.showInputDialog(null,
                            "Please select a major",
                            "Major", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                }
                int interestAdd = dataLayer.insertInterestStudent(((InterestObject) interest).getInterestId(), student[table.getSelectedRow()].getStudentId());
                if (interestAdd >= 1) {
                    JOptionPane.showMessageDialog(getParent(), "Interest inserted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not add interest. Please try again.");
                }

            }
        });
        insertPhoneNumbersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = "";
                while (phoneNumber.equals("")) {
                    phoneNumber = JOptionPane.showInputDialog("Please enter the phoneNumber.");
                }
                int phoneNumberAdd = dataLayer.insertPhoneNumber(student[table.getSelectedRow()].getStudentId(), phoneNumber);
                if (phoneNumberAdd >= 1) {
                    JOptionPane.showMessageDialog(getParent(), "Phone Number inserted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not add Phone Number. Please try again.");
                }

            }
        });
        insertEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = "";
                while (email.equals("")) {
                    email = JOptionPane.showInputDialog("Please enter the Email Address.");
                }
                boolean emailAdd = dataLayer.insertEmail(student[table.getSelectedRow()].getStudentId(), email);
                if (emailAdd) {
                    JOptionPane.showMessageDialog(getParent(), "Email inserted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not add Email Address. Please try again.");
                }

            }
        });
        deleteInterestsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object interest = null;
                FullStudentObject studentObject = student[table.getSelectedRow()];
                ArrayList<InterestObject> interestObjectArrayList = dataLayer.getInterests();
                Map<String, Integer> interestMap = new HashMap<>();
                for (InterestObject io : interestObjectArrayList) {
                    interestMap.put(io.getInterestName(), io.getInterestId());
                }

                String[] optionsArr = studentObject.getInterests();
                interest = JOptionPane.showInputDialog(getParent(),
                        "Select an Interest to Delete",
                        "Delete Interest", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                if (interest != null) {
                    int interestDelete = dataLayer.deleteInterestStudent(interestMap.get(interest), student[table.getSelectedRow()].getStudentId());
                    if (interestDelete >= 1) {
                        JOptionPane.showMessageDialog(getParent(), "Interest deleted. Please click refresh to see changes.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete interest. Please try again.");
                    }
                }
            }
        });
        deletePhoneNumbersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = null;
                FullStudentObject studentObject = student[table.getSelectedRow()];
                String[] optionsArr = studentObject.getPhoneNumbers();
                phoneNumber = (String) JOptionPane.showInputDialog(getParent(),
                        "Select a Phone Number to Delete",
                        "Delete Phone Number", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                if (phoneNumber != null) {
                    int phoneDelete = dataLayer.deletePhoneNumber(studentObject.getStudentId(), phoneNumber);
                    if (phoneDelete >= 1) {
                        JOptionPane.showMessageDialog(getParent(), "Phone number deleted. Please click refresh to see changes.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete phone number. Please try again.");
                    }
                }
            }
        });

        deleteEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = null;
                FullStudentObject studentObject = student[table.getSelectedRow()];
                String[] optionsArr = studentObject.getEmails();
                email = (String) JOptionPane.showInputDialog(getParent(),
                        "Select an Email Address to Delete",
                        "Delete Email Address", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                if (email != null) {
                    boolean emailDeleted = dataLayer.deleteEmail(studentObject.getStudentId(), email);
                    if (emailDeleted) {
                        JOptionPane.showMessageDialog(getParent(), "Email deleted. Please click refresh to see changes.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete email. Please try again.");
                    }
                }
            }
        });
        deleteMajorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object major = null;
                FullStudentObject studentObject = student[table.getSelectedRow()];
                ArrayList<MajorObject> majorObjectArrayList = dataLayer.getMajors();
                Map<String, Integer> majorMap = new HashMap<>();
                for (MajorObject mo : majorObjectArrayList) {
                    majorMap.put(mo.getMajorName(), mo.getMajorId());
                }

                String[] optionsArr = studentObject.getMajors();
                major = JOptionPane.showInputDialog(getParent(),
                        "Select a Major to Delete",
                        "Delete Major", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                if (major != null) {
                    int majorDelete = dataLayer.deleteMajorStudent(majorMap.get(major), student[table.getSelectedRow()].getStudentId());
                    if (majorDelete >= 1) {
                        JOptionPane.showMessageDialog(getParent(), "Major deleted. Please click refresh to see changes.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete major. Please try again.");
                    }
                }
            }
        });
        return popupMenu;
    }

    private JPopupMenu getFacultyProfilePopupMenu(JTable table, FacultyObject[] faculty, DataLayer dataLayer) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem showAbstractsButton = new JMenuItem("Show Abstracts");
        JMenuItem insertAbstractsButton = new JMenuItem("Insert Abstracts");
        JMenuItem insertProjectsButton = new JMenuItem("Insert Projects");
        JMenuItem insertPhoneNumbersButton = new JMenuItem("Insert Phone Numbers");
        JMenuItem insertEmailButton = new JMenuItem("Insert Email");
        JMenuItem deleteAbstractsButton = new JMenuItem("Delete Abstracts");
        JMenuItem deletePhoneNumbersButton = new JMenuItem("Delete Phone Numbers");
        JMenuItem deleteEmailButton = new JMenuItem("Delete Email");
        JMenuItem addStudentToProjectButton = new JMenuItem("Add Student to Project");
        popupMenu.add(showAbstractsButton);
        popupMenu.add(insertAbstractsButton);
        popupMenu.add(insertProjectsButton);
        popupMenu.add(insertPhoneNumbersButton);
        popupMenu.add(insertEmailButton);
        popupMenu.add(deleteAbstractsButton);
        popupMenu.add(deletePhoneNumbersButton);
        popupMenu.add(deleteEmailButton);
        popupMenu.add(addStudentToProjectButton);

        showAbstractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Selected row: " + table.getSelectedRow());
                FacultyObject fo = faculty[table.getSelectedRow()];
                JOptionPane.showMessageDialog(null, fo.getAbstractsForPrint());
            }
        });

        JDialog jDialog = new JDialog((JFrame) getParent(), "Insert Abstract");
        jDialog.setModal(true);
        jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        jDialog.setLayout(new BorderLayout());
        JLabel jLabel = new JLabel("Please add abstract", SwingConstants.CENTER);
        jDialog.add(jLabel, BorderLayout.NORTH);
        JPanel jPanelTextArea = new JPanel();
        JTextArea jTextArea = new JTextArea(25,50);
        jPanelTextArea.add(jTextArea);
        jDialog.add(jPanelTextArea, BorderLayout.CENTER);
        JPanel jPanelButton = new JPanel();
        JButton jButton = new JButton("OK");
        jPanelButton.add(jButton);
        jDialog.add(jPanelButton, BorderLayout.SOUTH);
        jDialog.pack();
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int inserted = dataLayer.insertAbstract(faculty[0].getFacultyId(), jTextArea.getText());
                if (inserted >= 1) {
                    JOptionPane.showMessageDialog(null, "Record inserted. Please click refresh to see changes.");
                }

                jDialog.dispose();
            }
        });

        jDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jTextArea.setText("");
            }
        });

        insertAbstractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.setVisible(true);

            }
        });
        JDialog jDialogProject = new JDialog((JFrame) getParent(), "Insert Project");
        jDialogProject.setModal(true);
        jDialogProject.setLayout(new BorderLayout());
        JLabel jLabelProject = new JLabel("Please add Project", SwingConstants.CENTER);
        jDialogProject.add(jLabelProject, BorderLayout.NORTH);
        JPanel jPanelTextAreaProject = new JPanel();
        jPanelTextAreaProject.setLayout(new BorderLayout());
        JTextField jTextFieldProject = new JTextField("Project Name");
        JTextArea jTextAreaProject = new JTextArea("Project Abstract", 25,50);
        jPanelTextAreaProject.add(jTextFieldProject, BorderLayout.NORTH);
        jPanelTextAreaProject.add(jTextAreaProject, BorderLayout.SOUTH);
        jDialogProject.add(jPanelTextAreaProject, BorderLayout.CENTER);
        JPanel jPanelButtonProject = new JPanel();
        JButton jButtonProject = new JButton("OK");
        jPanelButtonProject.add(jButtonProject);
        jDialogProject.add(jPanelButtonProject, BorderLayout.SOUTH);
        jDialogProject.pack();
        jButtonProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean inserted = dataLayer.insertProject(jTextFieldProject.getText(), jTextAreaProject.getText(), faculty[table.getSelectedRow()].getFacultyId());
                if (inserted) {
                    JOptionPane.showMessageDialog(null, "Record inserted. Please click refresh to see changes.");
                }

                jDialogProject.dispose();
            }
        });
        jDialogProject.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                jTextFieldProject.setText("Project Name");
                jTextAreaProject.setText("Project Abstract");
            }
        });

        insertProjectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialogProject.setVisible(true);

            }
        });
        insertPhoneNumbersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phoneNumber = "";
                while (phoneNumber.equals("")) {
                    phoneNumber = JOptionPane.showInputDialog("Please enter the phone number.");
                }
                int phoneNumberAdd = dataLayer.insertPhoneNumber(faculty[table.getSelectedRow()].getFacultyId(), phoneNumber);
                if (phoneNumberAdd >= 1) {
                    JOptionPane.showMessageDialog(getParent(), "Phone number inserted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not add phone number. Please try again.");
                }

            }
        });
        insertEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = "";
                while (email.equals("")) {
                    email = JOptionPane.showInputDialog("Please enter the email address.");
                }
                boolean emailAdd = dataLayer.insertEmail(faculty[table.getSelectedRow()].getFacultyId(), email);
                if (emailAdd) {
                    JOptionPane.showMessageDialog(getParent(), "Email address inserted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not add email address. Please try again.");
                }

            }
        });
        deleteAbstractsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] facultyAbstracts = faculty[table.getSelectedRow()].getAbstractTexts();
                String[] facultyAbstractOptions = new String[facultyAbstracts.length];
                Map<String, Integer> abstractRedactionToFullMap = new HashMap<>();
                for (int i = 0; i < facultyAbstracts.length; i++) {
                    String[] abstractWords = facultyAbstracts[i].split(" ");
                    String abstractRedacted = "";
                    for (int j = 0; j < 5; j++) {
                        abstractRedacted += abstractWords[j] + " ";
                    }
                    abstractRedacted += "(...)";
                    facultyAbstractOptions[i] = abstractRedacted;
                    abstractRedactionToFullMap.put(abstractRedacted, i);
                }
                Map<String, Integer> abstractToId = new HashMap<>();
                for (AbstractObject abstractObject : dataLayer.getAbstractsForUser(faculty[table.getSelectedRow()].getFacultyId())) {
                    abstractToId.put(abstractObject.getAbstractText(), abstractObject.getAbstractId());
                }
                String abstractText = null;
                abstractText = (String) JOptionPane.showInputDialog(getParent(),
                        "Select an abstract to delete.",
                        "Delete Abstract",
                        JOptionPane.PLAIN_MESSAGE, null, facultyAbstractOptions, facultyAbstractOptions[0]);
                if (abstractText != null) {
                    System.out.println("Map: " + abstractRedactionToFullMap.get(abstractText));
                    System.out.println("Abstract 1: " + facultyAbstracts[abstractRedactionToFullMap.get(abstractText)]);
                    System.out.println(abstractToId.toString());
                    int abstractDelete = dataLayer.deleteAbstract(abstractToId.get(facultyAbstracts[abstractRedactionToFullMap.get(abstractText)]), faculty[table.getSelectedRow()].getFacultyId());
                    if (abstractDelete >= 1) {
                        JOptionPane.showMessageDialog(getParent(), "Abstract deleted. Please click refresh and use the \"Show Abstracts\" menu item to see changes.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete abstract. Please try again.");
                    }
                }
            }
        });
        deletePhoneNumbersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FacultyObject facultyObject = faculty[table.getSelectedRow()];
                String[] optionsArr = facultyObject.getPhoneNumbers();
                String phoneNumber = (String) JOptionPane.showInputDialog(getParent(),
                        "Select a Phone Number to Delete",
                        "Delete Phone Number", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);

                if (phoneNumber != null) {
                    int phoneDelete = dataLayer.deletePhoneNumber(facultyObject.getFacultyId(), phoneNumber);
                    if (phoneDelete >= 1) {
                        JOptionPane.showMessageDialog(getParent(), "Phone number deleted. Please click refresh to see changes.");
                    }
                    else {
                        JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete phone number. Please try again.");
                    }
                }
            }
        });

        addStudentToProjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectObject[] projects = dataLayer.getProjectsForUser(faculty[table.getSelectedRow()].getFacultyId()).toArray(new ProjectObject[0]);
                String[] options = new String[projects.length];
                Map<String, Integer> projectNameToId = new HashMap<>();
                for (int i = 0; i < projects.length; i++) {
                    options[i] = projects[i].getProjectName();
                    projectNameToId.put(options[i], projects[i].getProjectId());
                }
                String projectName = null;
                while (null == projectName) {
                    projectName = (String) JOptionPane.showInputDialog(getParent(),
                            "Select a Project to Add a Student To.",
                            "Select a Project", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                }

                FullStudentObject[] studentsInProject = dataLayer.getStudentsInProject(projectNameToId.get(projectName)).toArray(new FullStudentObject[0]);
                Map<Integer, FullStudentObject> map = dataLayer.getStudentsMap();
                ArrayList<FullStudentObject> optionsList = new ArrayList<>();
                for (FullStudentObject fso : studentsInProject) {
                    map.remove(fso.getStudentId());
                }

                FullStudentObject[] fullStudentObjects = map.values().toArray(new FullStudentObject[0]);
                String[] studentOptions = new String[fullStudentObjects.length];
                Map<String, Integer> studentNameToId = new HashMap<>();
                for (int i = 0; i < fullStudentObjects.length; i++) {
                    studentOptions[i] = fullStudentObjects[i].getName();
                    studentNameToId.put(studentOptions[i], fullStudentObjects[i].getStudentId());
                }
                String studentName = null;
                while (null == studentName) {
                    studentName = (String) JOptionPane.showInputDialog(getParent(),
                            "Select a Student to Add",
                            "Select a Student", JOptionPane.PLAIN_MESSAGE, null, studentOptions, studentOptions[0]);
                }
                if (dataLayer.addStudentToProject(projectNameToId.get(projectName), studentNameToId.get(studentName))) {
                    JOptionPane.showMessageDialog(getParent(), "Added student successfully.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "Error occurred. Please try again.");
                }


            }
        });
        deleteEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = null;
                FacultyObject facultyObject = faculty[table.getSelectedRow()];
                String[] optionsArr = facultyObject.getEmails();
                while (null == email) {
                    email = (String) JOptionPane.showInputDialog(getParent(),
                            "Select an Email Address to Delete",
                            "Delete Email Address", JOptionPane.PLAIN_MESSAGE, null, optionsArr, optionsArr[0]);
                }
                boolean emailDeleted = dataLayer.deleteEmail(facultyObject.getFacultyId(), email);
                if (emailDeleted) {
                    JOptionPane.showMessageDialog(getParent(), "Email deleted. Please click refresh to see changes.");
                }
                else {
                    JOptionPane.showMessageDialog(getParent(), "ERROR: Could not delete email. Please try again.");
                }
            }
        });
        return popupMenu;
    }

    public static void main(String[] args) {
        new CustomPresentationLayer();
    }

}
