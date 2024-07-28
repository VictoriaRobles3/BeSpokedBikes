import java.sql.*;
import java.time.*;
//test get Sales Employees
//needs modification
public class test {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/BeSpokedDB";
        String user = "root";
        String password = "";

        try (Connection myConn = DriverManager.getConnection(url, user, password)) {
            String sqlCommand = "SELECT Fname, Lname, address, phone, StartDate, TerminationDate, Manager " +
                                "FROM SalesEmployees";

            Statement myStmt = myConn.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand);

            System.out.println("\nEMPLOYEE LIST\n");
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
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }
    }
}
