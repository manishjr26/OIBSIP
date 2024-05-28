import java.sql.*;
import java.util.Scanner;

public class TrainReservationSystem
{
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/train_reservation";

    static final String USER = "root";
    static final String PASS = "tiger@123";

    public static void main(String[] args)
    {
        Connection conn = null;
        Statement stmt = null;
        Scanner scanner = new Scanner(System.in);

        try 
        {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            // Login Form
            System.out.println("Enter username:");
            String username = scanner.nextLine();
            System.out.println("Enter password:");
            String password = scanner.nextLine();
            if (login(stmt, username, password)) 
            {
                System.out.println("Login successful!");
                // Show available trains and booked tickets
                showAvailableTrains(stmt);
                // Reservation or cancellation logic goes here
                System.out.println("Do you want to (R)eserve or (C)ancel ticket?");
                String choice = scanner.nextLine();
                if (choice.equalsIgnoreCase("R"))
                {
                    makeReservation(stmt, username, scanner);
                } else if (choice.equalsIgnoreCase("C"))
                {
                    cancelReservation(stmt, scanner);
                } else 
                {
                    System.out.println("Invalid choice!");
                }
            } else 
            {
                System.out.println("Invalid username or password!");
            }

            stmt.close();
            conn.close();
        } catch (Exception e) 
        {
            e.printStackTrace();
        } finally 
        {
            try 
            {
                if (stmt != null) stmt.close();
            } catch (SQLException se2)
            {
            }
            try 
            {
                if (conn != null) conn.close();
            } catch (SQLException se) 
            {
                se.printStackTrace();
            }
        }
    }

    public static boolean login(Statement stmt, String username, String password) throws SQLException 
    {
        String sql = "SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'";
        ResultSet rs = stmt.executeQuery(sql);
        return rs.next();
    }

    public static void showAvailableTrains(Statement stmt) throws SQLException 
    {
        String sql = "SELECT * FROM trains";
        ResultSet rs = stmt.executeQuery(sql);
        System.out.println("Available Trains:");
        while (rs.next())
        {
            System.out.println(rs.getString("train_number") + " - " + rs.getString("train_name"));
        }
        rs.close();
    }

    public static void makeReservation(Statement stmt, String username, Scanner scanner) throws SQLException 
    {
        System.out.println("Enter train number:");
        String trainNumber = scanner.nextLine();
        System.out.println("Enter class type (EC, 2A, FC, 3A):");
        String classType = scanner.nextLine();
        System.out.println("Enter journey date (YYYY-MM-DD):");
        String journeyDate = scanner.nextLine();
        String sql = "INSERT INTO reservations (username, train_number, class_type, journey_date) " +
                     "VALUES ('" + username + "', '" + trainNumber + "', '" + classType + "', '" + journeyDate + "')";
        int rowsAffected = stmt.executeUpdate(sql);
        if (rowsAffected > 0)
        {
            System.out.println("Reservation successful!");
        } else 
        {
            System.out.println("Reservation failed!");
        }
    }

    public static void cancelReservation(Statement stmt, Scanner scanner) throws SQLException 
    {
        System.out.println("Enter PNR number:");
        int pnrNumber = scanner.nextInt();
        String sql = "SELECT * FROM reservations WHERE pnr_number=" + pnrNumber;
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) 
        {
            System.out.println("Do you want to cancel this reservation? (Y/N)");
            String choice = scanner.next();
            if (choice.equalsIgnoreCase("Y")) 
            {
                String deleteSql = "DELETE FROM reservations WHERE pnr_number=" + pnrNumber;
                int rowsAffected = stmt.executeUpdate(deleteSql);
                if (rowsAffected > 0) 
                {
                    System.out.println("Reservation cancelled successfully!");
                } else
                {
                    System.out.println("Cancellation failed!");
                }
            } else 
            {
                System.out.println("Cancellation aborted!");
            }
        } else 
        {
            System.out.println("Reservation not found!");
        }
        rs.close();
    }
}
