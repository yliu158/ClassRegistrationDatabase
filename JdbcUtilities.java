// package jdbc_proj2.utility;
import java.sql.*;
import oracle.jdbc.*;
import java.math.*;
import java.io.*;
import java.awt.*;
import oracle.jdbc.pool.OracleDataSource;
import java.util.ArrayList;

public class JdbcUtilities {

    /**
    Set the connection info for queries
    */

    private static final String URL = "jdbc:oracle:thin:@castor.cc.binghamton.edu:1521:ACAD111";
    private static final String USER = "yliu158";
    private static final String PASSWORD = "123456";
    private static final int TABLE_LIMIT = 100;

    /**
    @return Connection to execute queries
    */

    private static Connection getConnection(){
      OracleDataSource ds = null;
      Connection conn = null;
      CallableStatement cs = null;
        try {
            ds = new oracle.jdbc.pool.OracleDataSource();
            ds.setURL(URL);
            conn = ds.getConnection(USER, PASSWORD);
        }
        catch (SQLException ex) { System.out.println ("\n*** SQLException caught ***\n" + ex.getMessage());}
        catch (Exception e) {System.out.println ("\n*** other Exception caught ***\n");}
        return conn;
    }

    /** question 2 */
    /**
    Execute the query of show students table.
    @return table in the from of Object[][]
    */

    public static Object[][] showStudents() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.show_STUDENTS(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[6];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          obj[2] = res.getString(3);
          obj[3] = res.getString(4);
          obj[4] = res.getDouble(5);
          obj[5] = res.getString(6);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][6];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /**
    Execute the query of show Prerequisites table.
    @return table in the from of Object[][]
    */

    public static Object[][] showPrerequisites() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.show_PREREQUISITES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[4];
          obj[0] = res.getString(1);
          obj[1] = res.getInt(2);
          obj[2] = res.getString(3);
          obj[3] = res.getInt(4);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][4];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /**
    Execute the query of show logs table.
    @return table in the from of Object[][]
    */

    public static Object[][] showLogs() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.show_LOGS(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        int count = 0;
        while (res.next()) {
          Object[] obj = new Object[6];
          obj[0] = res.getInt(1);
          obj[1] = res.getString(2);
          obj[2] = res.getTimestamp(3);
          obj[3] = res.getString(4);
          obj[4] = res.getString(5);
          obj[5] = res.getString(6);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][6];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /**
    Execute the query of show Enrollments table.
    @return table in the from of Object[][]
    */

    public static Object[][] showEnrollments() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.show_ENROLLMENTS(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[3];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          obj[2] = res.getString(3);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][3];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }


    /**
    Execute the query of show courses table.
    @return table in the from of Object[][]
    */

    public static Object[][] showCourses() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.show_COURSES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[3];
          obj[0] = res.getString(1);
          obj[1] = res.getInt(2);
          obj[2] = res.getString(3);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      // return l.toArray();
      Object[][] rv = new Object[l.size()][3];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < 3; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /**
    Execute the query of show classes table.
    @return table in the from of Object[][]
    */

    public static Object[][] showClasses() {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.show_CLASSES(?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.registerOutParameter(1, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(1);
        while (res.next()) {
          Object[] obj = new Object[8];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          obj[2] = res.getInt(3);
          obj[3] = res.getInt(4);
          obj[4] = res.getInt(5);
          obj[5] = res.getString(6);
          obj[6] = res.getInt(7);
          obj[7] = res.getInt(8);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][8];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question 3 */
    /**
    Execute the query of insert a student to the students table.
    @return message string
    */
    public static String insertStudent(String sid, String firstname, String lastname, String status, double gpa, String email) {
      String rv = "";
      String query = "begin university.insert_a_student(?,?,?,?,?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.setString(2, firstname);
        cs.setString(3, lastname);
        cs.setString(4, status);
        cs.setDouble(5, gpa);
        cs.setString(6, email);
        cs.registerOutParameter(7, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(7);
        while (res.next()) {
          rv =
          res.getString(1) + "\t" +
          res.getString(2) + "\t" +
          res.getString(3) + "\t" +
          res.getString(4) + "\t" +
          res.getDouble(5) + "\t" +
          res.getString(6) + "\n";
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return rv;
    }

    /** question 4 */
    /**
    Execute the query of get classes of student.
    @return table in the formate of Object[][]
    */

    public static Object[][] getClassOfStudent(String sid, ArrayList<String> message) {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.get_student_classes(?,?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.registerOutParameter(3, OracleTypes.CURSOR);
        cs.registerOutParameter(4, java.sql.Types.VARCHAR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(2);
        while (res.next()) {
          message.add(res.getString(1)+"\t");
          message.add(res.getString(2)+"\t");
          message.add(res.getString(3)+"\t");
          message.add(res.getString(4)+"\t");
          message.add(Double.toString(res.getDouble(5))+"\t");
          message.add(res.getString(6));
        }
        ResultSet classes = (ResultSet)cs.getObject(3);
        while (classes.next()) {
          Object[] obj = new Object[8];
          obj[0] = classes.getString(1);
          obj[1] = classes.getString(2);
          obj[2] = classes.getInt(3);
          obj[3] = classes.getInt(4);
          obj[4] = classes.getInt(5);
          obj[5] = classes.getString(6);
          obj[6] = classes.getInt(7);
          obj[7] = classes.getInt(8);
          l.add(obj);
        }
        String err_message = cs.getString(4);
        if (err_message != null) {
          message.add(err_message);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][8];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question 5 */
    /**
    Execute the query of get Prerequisites of given course_no and dept_code.
    @return table in the formate of Object[][]
    */

    public static Object[][] getPrerequisite(String dept_code, String course_no) {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.get_prerequisite(?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, dept_code);
        cs.setString(2, course_no);
        cs.registerOutParameter(3, OracleTypes.CURSOR);
        cs.execute();
        ResultSet res = (ResultSet)cs.getObject(3);
        while (res.next()) {
          Object[] obj = new Object[2];
          obj[0] = res.getString(1);
          obj[1] = res.getString(2);
          l.add(obj);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][2];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question 6 */
    /**
    Execute the query of get students a class.
    @return table in the formate of Object[][]
    */

    public static Object[][] getStudentsOfClass(String cid, ArrayList<String> cla) {
      ArrayList<Object[]> l = new ArrayList<Object[]>();
      String query = "begin university.get_enrolledOf(?,?,?,?); end;";
      CallableStatement cs = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, cid);
        cs.registerOutParameter(2, OracleTypes.CURSOR);
        cs.registerOutParameter(3, OracleTypes.CURSOR);
        cs.registerOutParameter(4, java.sql.Types.VARCHAR);
        cs.execute();
        ResultSet classes = (ResultSet)cs.getObject(2);
        while (classes.next()) {
          cla.add(classes.getString(1)+ "\t");
          cla.add(classes.getString(2)+ "\t");
          cla.add(Integer.toString(classes.getInt(3))+ "\t");
          cla.add(Integer.toString(classes.getInt(4))+ "\t");
          cla.add(Integer.toString(classes.getInt(5))+ "\t");
          cla.add(classes.getString(6)+ "\t");
          cla.add(Integer.toString(classes.getInt(7))+ "\t");
          cla.add(Integer.toString(classes.getInt(8)));
        }
        ResultSet students = (ResultSet)cs.getObject(3);
        while (students.next()) {
          Object[] obj = new Object[6];
          obj[0] = students.getString(1);
          obj[1] = students.getString(2);
          obj[2] = students.getString(3);
          obj[3] = students.getString(4);
          obj[4] = students.getDouble(5);
          obj[5] = students.getString(6);
          l.add(obj);
        }
        String err_message = cs.getString(4);
        if (err_message != null) {
          cla.add(err_message);
        }
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      Object[][] rv = new Object[l.size()][6];
      for (int i = 0; i < l.size(); ++i) {
        for (int j = 0; j < l.get(i).length; ++j) {
          rv[i][j] = l.get(i)[j];
        }
      }
      return rv;
    }

    /** question 7 */
    /**
    Execute the query of enroll a students to a class.
    @return table in the formate of Object[][]
    */

    public static String enrollToClass(String sid, String cid) {
      String query = "begin university.enroll_std2cla(?,?,?); end;";
      CallableStatement cs = null;
      String message = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.setString(2, cid);
        cs.registerOutParameter(3, java.sql.Types.VARCHAR);
        cs.execute();
        message = cs.getString(3);
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return message;
    }

    /** question 8 */
    /**
    Execute the query of drop students from a class.
    @return table in the formate of Object[][]
    */

    public static String dropClass(String sid, String cid) {
      String query = "begin university.drop_class(?,?,?); end;";
      CallableStatement cs = null;
      String message = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.setString(2, cid);
        cs.registerOutParameter(3, java.sql.Types.VARCHAR);
        cs.execute();
        message = cs.getString(3);
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return message;
    }

    /** question 9 */
    /**
    Execute the query of delete a students.
    @return table in the formate of Object[][]
    */

    public static String deleteStudent(String sid) {
      String query = "begin university.delete_student(?,?); end;";
      CallableStatement cs = null;
      String message = null;
      try {
        Connection conn = getConnection();
        cs = conn.prepareCall(query);
        cs.setString(1, sid);
        cs.registerOutParameter(2, java.sql.Types.VARCHAR);
        cs.execute();
        message = cs.getString(2);
        cs.close();
        conn.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
      return message;
    }
}
