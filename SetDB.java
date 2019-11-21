/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmu;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
/**
 *
 * @author apple
 */
public class SetDB {
     private String framework = "embedded";
    private String protocol = "jdbc:derby:";

    /**
	 * @param args The command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        new SetDB().go(args);
        System.out.println("Database setup finished");
    }
    
    /**
     * 
	 * A method to create and populate the database
	 * @param args  
     */
    void go(String[] args) {
      
        System.out.println("Database setup in " + framework + " mode");
        Connection conn = null;
        PreparedStatement psInsert;
        PreparedStatement psUpdate;

        ResultSet rs = null;
        try {
      
            String dbName = "faceRecTrial";
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/faceRecTrial;create=true","App","App");
			
			System.out.println("Connected to and created database " + dbName);

            //CLEANUP PREVIOUS VALUES
           deleteAllTables(conn);

            // CREATE ALL TABLES
           createTables(conn);

            //POPULATE TABLES
            populateTables(conn);
            conn.commit();

        } catch (SQLException sqle) {
            printSQLException(sqle);
        } finally {
            // release all open resources to avoid unnecessary memory usage  
            //Connection
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }

    }
	
	 /**
	 * Initialises the database from file
	 * @param conn The database connection object 
     */
    private void populateTables(Connection conn) {
        try {
			//INITIALIZING FILE PATHS
            String directoryPath = System.getProperty("user.dir") + "\\src\\main\\java\\cmu\\";
            String StudentInfoFile = directoryPath + "StudentInfo.csv";
            String VisitDetailsFile = directoryPath + "VisitDetails.csv";
            String AnnouncementFile = directoryPath + "Announcements.csv";
            String MarksFile = directoryPath + "Marks.csv";
            ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements

            //INSERTING STUDENT INFO
            PreparedStatement psInsert = conn.prepareStatement("insert into StudentInfo values (?, ?, ?, ?, ?, ?)");
            statements.add(psInsert);
            BufferedReader in = new BufferedReader(new FileReader(StudentInfoFile));
            Scanner sc = new Scanner(in);
            sc.useDelimiter(",|\\n");
            while (sc.hasNext()) {
                String StudentID = sc.next();
                psInsert.setString(1, StudentID);
                psInsert.setString(2, sc.next());
                psInsert.setString(3, sc.next());
                psInsert.setDate(4, java.sql.Date.valueOf(sc.next()));
                psInsert.setString(5, sc.next());
                psInsert.setString(6, sc.next());
                psInsert.executeUpdate();
                System.out.println("Inserted " + StudentID);
            }
            
            //INSERTING VISITING DETAILS
            PreparedStatement psInsert2 = conn.prepareStatement("insert into VisitDetails values (?, ?, ?, ?)");
            statements.add(psInsert2);
            in = new BufferedReader(new FileReader(VisitDetailsFile));
            sc = new Scanner(in);
            sc.useDelimiter(",|\\n");
            while (sc.hasNext()) {
                String VisitID = sc.next();
                psInsert2.setString(1, VisitID);
                psInsert2.setString(2, sc.next());
                psInsert2.setString(3, sc.next());
                psInsert2.setTimestamp(4, java.sql.Timestamp.valueOf(sc.next()));
                psInsert2.executeUpdate();
                System.out.println("Inserted " + VisitID);
            }
            
            //INSERTING ANNOUNCEMENTS
            PreparedStatement psInsert3 = conn.prepareStatement("insert into Announcements values (?, ?)");
            statements.add(psInsert3);
            in = new BufferedReader(new FileReader(AnnouncementFile));
            sc = new Scanner(in);
            sc.useDelimiter(",|\\n");
            while (sc.hasNext()) {

                psInsert3.setString(1, sc.next());
                psInsert3.setString(2, sc.next());
                psInsert3.executeUpdate();
                System.out.println("Inserted Annoumcement");
            }
			
			//INSERTING MARKS
            PreparedStatement psInsert4 = conn.prepareStatement("insert into Marks values (?,?,?,?,?,?)");
            in = new BufferedReader(new FileReader(MarksFile));
            sc = new Scanner(in);
            sc.useDelimiter(",|\\n");
            while (sc.hasNext()) {

                psInsert4.setString(1, sc.next());
                psInsert4.setInt(2, Integer.parseInt(sc.next().trim()));
                psInsert4.setInt(3, Integer.parseInt(sc.next().trim()));
                psInsert4.setInt(4, Integer.parseInt(sc.next().trim()));
                psInsert4.setInt(5, Integer.parseInt(sc.next().trim()));
                psInsert4.setInt(6, Integer.parseInt(sc.next().trim()));
                psInsert4.executeUpdate();
                System.out.println("Inserted Marks");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }
	
	/**
	 * Clears the database
	 * @param conn The database connection object 
     */
    void deleteAllTables(Connection conn) {
        try {

            ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements
            Statement s;
            s = conn.createStatement();
            statements.add(s);
            s.execute("drop table StudentInfo");
            System.out.println("Deleted table StudentInfo ");
            s.execute("drop table VisitDetails");
            System.out.println("Deleted table VisitDetails ");
            s.execute("drop table Announcements");
            System.out.println("Deleted table VisitDetails ");
            s.execute("drop table Marks");
            System.out.println("Deleted table Marks ");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
	
	/**
	 * Creates the required tables for the database
	 * @param conn The database connection object 
     */
    void createTables(Connection conn) {
        try {

            ArrayList<Statement> statements = new ArrayList<Statement>(); // list of Statements, PreparedStatements

            Statement s;
            s = conn.createStatement();
            statements.add(s);
            s.execute("create table StudentInfo(studentID varchar(20),name varchar(40), program varchar(40),DOB DATE, address varchar(40),gender varchar(20))");
            System.out.println("Created StudentInfo");
            System.out.println("Hello**********");
            s.execute("create table VisitDetails(visitID varchar(20), studentID varchar(40), reason varchar(70), time TIMESTAMP)");
            System.out.println("Created VisitDetails");
            s.execute("create table Announcements(studentID varchar(40), announcement varchar(70))");
            System.out.println("Created Announcements");
            s.execute("create table Marks(studentID varchar(40), Telecom int, ODI int, SPS int, DBS int ,Java int)");
            System.out.println("Created Marks");
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
	
	/**
	 * Displays SQL Exceptions
     */
    public static void printSQLException(SQLException e)
    {
        // Unwraps the entire exception chain to unveil the real cause of the
        // Exception.
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }
}
