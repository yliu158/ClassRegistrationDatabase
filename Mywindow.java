import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;

import java.util.ArrayList;

public class Mywindow {

  private static JFrame frame;
  private static JPanel main_panel;
  private static JPanel showStudents_panel;
  private static String[] show_button_names = {
    "showStudents",
    "showClasses",
    "showCourses",
    "showEnrollments",
    "showPrerequisites",
    "showLogs"
  };

  private static String[] action_button_names = {
    "insertStudent",
    "getClassOfStudent",
    "getPrerequisite",
    "getStudentsOfClass",
    "enrollToClass",
    "dropClass",
    "deleteStudent"
  };

  public Mywindow() {
    __init__();
  }

  /**
  For init the window
  Call by the constructor when the window is created
  This method build the main window and all buttons.
  */

  private void __init__() {
    frame = new JFrame();
    frame.setBounds(150, 100, 955, 627);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new CardLayout(0, 0));
    main_panel = new JPanel();
    frame.getContentPane().add(main_panel, "main");
    main_panel.setLayout(null);
    createShowButtons();
    createActButtons();
    frame.setVisible(true);
  }


  /**
  Provide data in Object[][] formate to the JTable constructor
  The tables are for showing all subjects in the database
  In this way the code is more concise
  @param index of the table
  @return entire data in the from of Object[][]
  */

  private Object[][] getData(int index) {
    Object[][] res = null;
    switch (index) {
      case 0: res = JdbcUtilities.showStudents(); break;
      case 1: res = JdbcUtilities.showClasses(); break;
      case 2: res = JdbcUtilities.showCourses(); break;
      case 3: res = JdbcUtilities.showEnrollments(); break;
      case 4: res = JdbcUtilities.showPrerequisites(); break;
      case 5: res = JdbcUtilities.showLogs(); break;
    }
    return res;
  }

  /**
  Provide columns information which are headers of tables in the database
  @param index of the table
  @return Headers of tables
  */

  private String[] getColumns(int index) {
    String[] rv = null;
    switch (index) {
      case 0: String[] res0 = {"SID", "FIRSTNAME", "LASTNAME", "STATUS", "GPA", "EMAIL"}; rv = res0; break;
      case 1: String[] res1 = {"CLASSID", "DEPT_CODE", "COURSE_NO", "SECT_NO", "YEAR", "SEMESTER", "LIMIT", "CLASS_SIZE"}; rv = res1; break;
      case 2: String[] res2 = {"DEPT_CODE", "COURSE_NO", "TITLE"}; rv = res2; break;
      case 3: String[] res3 = {"SID", "CLASSID", "LGRADE"}; rv = res3; break;
      case 4: String[] res5 = {"DEPT_CODE", "COURSE_NO", "PRE_DEPT_CODE", "PRE_COURSE_NO"}; rv = res5; break;
      case 5: String[] res7 = {"LOGID","WHO","TIME","TABLE_NAME","OPERATION","KEY_VALUE"}; rv = res7; break;
    }
    return rv;
  }


  /**
  Create all buttons to show tables in the databse
  */

  private void createShowButtons() {
    for (int i = 0; i < show_button_names.length; ++i) {
      Object[][] data = getData(i);
      String[] columns = getColumns(i);
      createShowButton(data, columns, i);
    }
  }


  /**
  Define each button
  Take all the information needed to create the table. And show the table when click
  the corresponding button.
  @param data, columns and index
  */

  private void createShowButton(Object[][] data, String[] columns, int index) {
    JButton b = new JButton(show_button_names[index]);
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[][] data = getData(index);
        String[] columns = getColumns(index);
        TableGenerator  g = new TableGenerator();
        JFrame f = g.initTable(data, columns);
        f.setVisible(true);
      }
    });
    b.setFont(new Font("Tahoma", Font.BOLD, 14));
    b.setBounds(36, 33+index*70, 162, 56);
    main_panel.add(b);
  }


  /**
  Create all the buttons of doing operations to the database
  */

  private void createActButtons() {
    for (int i = 0; i < action_button_names.length; ++i) {
      createActButton(i);
    }
  }


  /**
  Create a button doing one kind of query to the database
  When click the button corresponding window is created for further operation
  @param index of the tables
  */

  private void createActButton (int index) {
    JButton b = new JButton(action_button_names[index]);
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        switch (index) {
          case 0: insertStudentInputWindow(); break;
          case 1: getClassOfStudentInputWindow();break;
          case 2: getPrerequisiteInputWindow();break;
          case 3: getStudentsOfClassInputWindow();break;
          case 4: enrollToClassInputWindow();break;
          case 5: dropClassInputWindow(); break;
          case 6: deleteStudentInputWindow();
        }
      }
    });
    b.setFont(new Font("Tahoma", Font.PLAIN, 14));
    b.setBounds(600, 33+index*70, 240, 56);
    main_panel.add(b);
  }


  /**
  Implement query of insertion of a student to the table
  api on the corresponding window.
  When click on the insert button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void insertStudentInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 800, 230);
    JPanel p = new JPanel();
    JTextField sid = new JTextField();
    JTextField firstname = new JTextField();
    JTextField lastname = new JTextField();
    JTextField status = new JTextField();
    JTextField gpa = new JTextField();
    JTextField email = new JTextField();
    JLabel sid_l = new JLabel("SID");
    JLabel firstname_l = new JLabel("FIRSTNAME");
    JLabel lastname_l = new JLabel("LASTNAME");
    JLabel status_l = new JLabel("STATUS");
    JLabel gpa_l = new JLabel("GPA");
    JLabel email_l = new JLabel("EMAIL");
    sid_l.setBounds(10, 11, 90, 20);
    sid.setBounds(105, 11, 233, 20);
    firstname_l.setBounds(10, 42, 90, 20);
    firstname.setBounds(105, 42, 233, 20);
    lastname_l.setBounds(10, 73, 90, 20);
    lastname.setBounds(105, 73, 233, 20);
    status_l.setBounds(410, 11, 90, 20);
    status.setBounds(505, 11, 233, 20);
    gpa_l.setBounds(410, 42, 90, 20);
    gpa.setBounds(505, 42, 233, 20);
    email_l.setBounds(410, 73, 90, 20);
    email.setBounds(505, 73, 233, 20);
    p.setLayout(null);
    p.add(sid_l);
    p.add(sid);
    p.add(firstname_l);
    p.add(firstname);
    p.add(lastname_l);
    p.add(lastname);
    p.add(status_l);
    p.add(status);
    p.add(gpa_l);
    p.add(gpa);
    p.add(email_l);
    p.add(email);

    JTextArea sinfo = new JTextArea(2, 100);
    sinfo.setEditable(false);
    sinfo.setBounds(10, 150, 750, 30);
    sinfo.setLineWrap(true);
    p.add(sinfo);

    JButton act = new JButton("Insert");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.insertStudent(sid.getText(), firstname.getText(),
        lastname.getText(), status.getText(), Double.parseDouble(gpa.getText()), email.getText());
        sinfo.setText(message);
      }
    });
    act.setBounds(500, 100, 150, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  Create a window when clicked on the getClassOfStudent button on the main window.
  It provide an API for checking the classes of student taking.
  When click on the check out button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void getClassOfStudentInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 500);
    JPanel p = new JPanel();
    p.setLayout(null);
    JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("SID");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);
    String[] columns = {"CLASSID", "DEPT_CODE", "COURSE_NO", "SECT_NO", "YEAR", "SEMESTER", "LIMIT", "CLASS_SIZE"};
    JTable table = new JTable(new Object[0][8], columns);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setBounds(10, 130, 880, 350);
    JTextArea sinfo = new JTextArea(2, 100);
    sinfo.setEditable(false);
    sinfo.setBounds(10, 70, 880, 50);
    sinfo.setLineWrap(true);
    p.add(table);
    p.add(sinfo);
    JButton act = new JButton("Check Out");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ArrayList<String> message = new ArrayList<String>();
        Object[][] data = JdbcUtilities.getClassOfStudent(sid.getText(), message);
        DefaultTableModel dataModel = new DefaultTableModel(data, columns);
        table.setModel(dataModel);
        String mes = "";
        if (message.size() == 1) {
          mes = message.get(0);
        } else if (message.size() == 6) {
          for (int k = 0; k < 6; ++k) {
            mes += message.get(k);
          }
        } else if (message.size() == 7) {
          for (int k = 0; k < 6; ++k) {
            mes += message.get(k);
          }
          mes += "\n " + message.get(6);
        }
        sinfo.setText(mes);
      }
    });
    act.setBounds(399, 11, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  Create a window when clicked on the getPrerequisite button on the main window.
  It provide an API for checking the Prerequisites of course taking.
  When click on the check out button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void getPrerequisiteInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 500);
    JPanel p = new JPanel();
    p.setLayout(null);
    JTextField dept_code = new JTextField();
    JTextField course_no = new JTextField();
    JLabel dept_code_l = new JLabel("DEPT_CODE");
    JLabel course_no_l = new JLabel("COURSE_NO");
    dept_code_l.setBounds(10, 11, 90, 30);
    dept_code.setBounds(105, 11, 233, 30);
    course_no_l.setBounds(10, 43, 90, 30);
    course_no.setBounds(105, 43, 233, 20);
    p.add(dept_code);
    p.add(dept_code_l);
    p.add(course_no);
    p.add(course_no_l);

    String[] columns = {"PRE_DEPT_CODE", "PRE_COURSE_NO"};
    JTable table = new JTable(new Object[0][2], columns);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setBounds(10, 120, 880, 200);
    p.add(table);

    JButton act = new JButton("Check Out");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Object[][] data = JdbcUtilities.getPrerequisite(dept_code.getText(), course_no.getText());
        DefaultTableModel dataModel = new DefaultTableModel(data, columns);
        table.setModel(dataModel);
      }
    });
    act.setBounds(399, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  Create a window when clicked on the getStudentsOfClass button on the main window.
  It provide an API for checking the all the students who taking or taken the class.
  When click on the check out button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void getStudentsOfClassInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 620);
    JPanel p = new JPanel();
    p.setLayout(null);
    JTextField cid = new JTextField();
    JLabel cid_l = new JLabel("CID");
    cid_l.setBounds(10, 11, 90, 30);
    cid.setBounds(105, 11, 233, 30);
    p.add(cid_l);
    p.add(cid);
    String[] columns = {"SID", "FIRSTNAME", "LASTNAME", "STATUS", "GPA", "EMAIL"};
    JTable table = new JTable(new Object[0][6], columns);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    table.setBounds(10, 120, 880, 460);
    p.add(table);
    JTextArea cinfo = new JTextArea(2, 100);
    cinfo.setEditable(false);
    cinfo.setBounds(10, 60, 880, 50);
    cinfo.setLineWrap(true);
    p.add(cinfo);

    JButton act = new JButton("Check Out");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ArrayList<String> message = new ArrayList<String>();
        Object[][] data = JdbcUtilities.getStudentsOfClass(cid.getText(), message);
        DefaultTableModel dataModel = new DefaultTableModel(data, columns);
        table.setModel(dataModel);
        String mes = "";
        if (message.size() == 1) mes = message.get(0);
        if (message.size() == 9) {
        	for (int k = 0; k < 8; ++k) mes += message.get(k);
        	mes += "\n" + message.get(8);
        }
        if (message.size() == 8)for (int k = 0; k < 8; ++k) mes += message.get(k);
        cinfo.setText(mes);
      }
    });
    act.setBounds(500, 11, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  Create a window when clicked on the enrollToClass button on the main window.
  It provide an API to enroll a student to a class.
  When click on the enroll button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void enrollToClassInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 260);
    JPanel p = new JPanel();
    p.setLayout(null);
    JTextField cid = new JTextField();
    JLabel cid_l = new JLabel("CID");
    cid_l.setBounds(10, 43, 90, 30);
    cid.setBounds(105, 43, 233, 30);
    p.add(cid_l);
    p.add(cid);
    JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("SID");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);

    JTextArea info = new JTextArea(2, 100);
    info.setEditable(false);
    info.setBounds(10, 90, 880, 30);
    info.setLineWrap(true);
    p.add(info);

    JButton act = new JButton("Enroll");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.enrollToClass(sid.getText(), cid.getText());
        info.setText(message);
      }
    });
    act.setBounds(500, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  Create a window when clicked on the dropClass button on the main window.
  It provide an API for drop a student from a class.
  When click on the drop button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void dropClassInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 260);
    JPanel p = new JPanel();
    p.setLayout(null);
    JTextField cid = new JTextField();
    JLabel cid_l = new JLabel("CID");
    cid_l.setBounds(10, 43, 90, 30);
    cid.setBounds(105, 43, 233, 30);
    p.add(cid_l);
    p.add(cid);
    JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("SID");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);

    JTextArea info = new JTextArea(2, 100);
    info.setEditable(false);
    info.setBounds(10, 90, 880, 30);
    info.setLineWrap(true);
    p.add(info);

    JButton act = new JButton("Drop");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.dropClass(sid.getText(), cid.getText());
        info.setText(message);
      }
    });
    act.setBounds(500, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  Create a window when clicked on the deleteStudent button on the main window.
  It provide an API for delete the student from the database.
  When click on the delete button, it will extract info from the text area as
  Inputs and do the corresponding query and show the query results.
  */

  private void deleteStudentInputWindow() {
    JFrame f;
    f = new JFrame();
    f.setBounds(430, 200, 900, 260);
    JPanel p = new JPanel();
    p.setLayout(null);
    JTextField sid = new JTextField();
    JLabel sid_l = new JLabel("SID");
    sid_l.setBounds(10, 11, 90, 30);
    sid.setBounds(105, 11, 233, 30);
    p.add(sid_l);
    p.add(sid);

    JTextArea info = new JTextArea(2, 100);
    info.setEditable(false);
    info.setBounds(10, 90, 880, 30);
    info.setLineWrap(true);
    p.add(info);

    JButton act = new JButton("Delete");
    act.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String message = JdbcUtilities.deleteStudent(sid.getText());
        info.setText(message);
      }
    });
    act.setBounds(500, 43, 200, 30);
    p.add(act);
    f.getContentPane().add(p);
    f.setVisible(true);
  }


  /**
  The main fuction
  */

  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          Mywindow w = new Mywindow();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }


  /**
  A class to help construct the pop out windows of showing all tables
  */

  public class TableGenerator extends JFrame {
    JFrame f;
    JTable table;
    public JFrame initTable(Object[][] data, String[] columns) {
      f = new JFrame();
      f.setBounds(400, 200, 900, 600);
      table = new JTable(data, columns);
      table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
      f.add(new JScrollPane(table));
      f.setVisible(true);
      return f;
    }
  };

}
