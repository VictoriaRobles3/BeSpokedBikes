import java.sql.*;
import java.time.*;
//test get Sales Employees
//needs modification
public class test {
    private Connection connection;

    public test(){
        dbConnection();
    }

    private void dbConnection(){
        try {
            String url = "jdbc:mysql://localhost:3306/BeSpokedDB";
            String user = "root";
            String password = "";

            // check if the JDBC driver is loaded, needs to be added to the Libraries section VS code
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database. Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void getSalesEmployees(){
        
        String sqlCommand = "SELECT Fname, Lname, address, phone, StartDate, TerminationDate, Manager " +
                            "FROM SalesEmployees";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){

            System.out.println("\nSALES EMPLOYEE LIST\n");
            System.out.println("First Name\tLast Name\tAddress\t\t\t\tPhone\t\tStart Date\tTermination Date\tManager");
            System.out.println("----------\t---------\t-------\t\t\t\t------\t\t----------\t----------------\t--------");

            while (myRS.next()) {
                String firstName = myRS.getString("Fname");
                String lastName = myRS.getString("Lname");
                String address = myRS.getString("address");
                String phone = myRS.getString("phone");
                LocalDate StartDate = myRS.getDate("StartDate").toLocalDate();
                LocalDate TerminationDate = myRS.getDate("TerminationDate") != null ? myRS.getDate("TerminationDate").toLocalDate() : null;
                String manager = myRS.getString("Manager");


                System.out.printf("%-10s\t%-10s\t%-16s\t%-10s\t%-10s\t%-16s\t%-10s\n",
                firstName,
                lastName,
                address,
                phone,
                StartDate,
                (TerminationDate != null ? TerminationDate : "N/A"),
                manager);
            }
        } catch (SQLException e){
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }

    public static void main(String[] args) {
        test ex = new test();
        ex.getSalesEmployees();
    }
}
