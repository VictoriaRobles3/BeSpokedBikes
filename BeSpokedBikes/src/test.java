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
            //System.out.println("Connected to the database successfully.");
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
            System.out.println("----------\t---------\t--------------------------\t------------\t----------\t----------------\t----------------");

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

    private void getCustomers(){

        String sqlCommand = "SELECT Fname, Lname, address, phone, StartDate " +
                            "FROM Customers";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){

            System.out.println("\nCUSTOMER LIST\n");
            System.out.println("First Name\tLast Name\tAddress\t\t\t\t\tPhone\t\tStart Date");
            System.out.println("----------\t---------\t-----------------------------\t\t-------------\t----------");

            while (myRS.next()) {
                String firstName = myRS.getString("Fname");
                String lastName = myRS.getString("Lname");
                String address = myRS.getString("address");
                String phone = myRS.getString("phone");
                LocalDate StartDate = myRS.getDate("StartDate").toLocalDate();

                System.out.printf("%-10s\t%-10s\t%-32s\t%-10s\t%-15s\n",
                firstName,
                lastName,
                address,
                phone,
                StartDate);
            }
        } catch (SQLException e){
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }

    }

    private void getProducts(){
        String sqlCommand = "SELECT name, manufacturer, prodStyle, purchasePrice, salePrice, QtyOnHand, commissionPercentage " +
                            "FROM Products";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){

            System.out.println("\nPRODUCTS LIST\n");
            System.out.println("Name\t\t\tManufacturer\tStyle\t\tPurchase Price\t\tSale Price\tQuantity\tCommission Percentage");
            System.out.println("-------------\t\t-----------\t------------\t-------------\t\t----------\t--------\t---------------------");

            while (myRS.next()) {
                String name = myRS.getString("name");
                String manufacturer = myRS.getString("manufacturer");
                String prodStyle = myRS.getString("prodStyle");
                Double purchasePrice = myRS.getDouble("purchasePrice");
                Double salePrice = myRS.getDouble("salePrice");
                int qtyOnHand = myRS.getInt("QtyOnHand");
                Double commissionPercentage = myRS.getDouble("commissionPercentage");

                System.out.printf("%-16s\t%-15s\t%-10s\t$%-13.2f\t\t$%-10.2f\t%-8d\t%.2f%%\n",
                name,
                manufacturer,
                prodStyle,
                purchasePrice,
                salePrice,
                qtyOnHand,
                commissionPercentage
                );
            }
        } catch (SQLException e){
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }

    }

    private void getSales(){
        String sqlCommand = "SELECT p.name, s.salePrice, s.saleDate, e.Fname AS empFname, e.Lname AS empLname, " +
                            "c.Fname AS custFname, c.Lname AS custLname, p.commissionPercentage " +
                            "FROM Sales s " + "JOIN Products p ON s.prodid = p.prodid "+
                            "JOIN SalesEmployees e ON s.empid = e.empid " +
                            "JOIN Customers c ON s.custid = c.custid";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){

            System.out.println("\nSALES LIST\n");
            System.out.println("Product Name\tCustomer Name\tSale Date\tSale Price\tSales Employee\t\tEmployee Commission");
            System.out.println("------------\t-------------\t---------\t----------\t--------------\t\t-------------------");

            while (myRS.next()) {
                String ProductName = myRS.getString("name");
                Double salePrice = myRS.getDouble("salePrice");
                LocalDate saleDate = myRS.getDate("saleDate").toLocalDate();
                String employeeFirstName = myRS.getString("empFname");
                String employeeLastName = myRS.getString("empLname");
                String customerFirstName = myRS.getString("custFname");
                String customerLastName = myRS.getString("custLname");
                Double commission = myRS.getDouble("commissionPercentage");

                System.out.printf("%-12s\t%-13s\t%-10s\t$%-9.2f\t%-20s\t$%.2f\n",
                ProductName,
                customerFirstName + " " + customerLastName,
                saleDate,
                salePrice,
                employeeFirstName + " " + employeeLastName,
                commission
                );
            }
        } catch (SQLException e){
            System.out.println("ERROR: " + e.getLocalizedMessage());
        }

    }

    public static void main(String[] args) {
        test ex = new test();
        ex.getSalesEmployees();
        ex.getCustomers();
        ex.getProducts();
        ex.getSales();
    }
}
