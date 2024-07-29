import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.*;

public class Sales extends JFrame {
    private Connection connection;

    public Sales(Connection connection){
        this.connection = connection;
        start();
    }

    private void start(){
        setTitle("SALES");
        setSize(1600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Sales List"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        getSales(resultArea);
    }

    private void getSales(JTextArea resultArea){
        String sqlCommand = "SELECT p.name, s.salePrice, s.saleDate, e.Fname AS empFname, e.Lname AS empLname, " +
                            "c.Fname AS custFname, c.Lname AS custLname, p.commissionPercentage " +
                            "FROM Sales s " + "JOIN Products p ON s.prodid = p.prodid "+
                            "JOIN SalesEmployees e ON s.empid = e.empid " +
                            "JOIN Customers c ON s.custid = c.custid " +
                            "ORDER BY s.saleDate DESC";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){
                StringBuilder text = new StringBuilder();

                text.append("\nProduct Name\t\tCustomer Name\tSale Date\tSale Price\tSales Employee\tEmployee Commission\n");
                text.append("------------\t-------------\t----------\t--------\t--------------\t------------------\n");

                while (myRS.next()) {
                        String ProductName = myRS.getString("name");
                        Double salePrice = myRS.getDouble("salePrice");
                        LocalDate saleDate = myRS.getDate("saleDate").toLocalDate();
                        String employeeFirstName = myRS.getString("empFname");
                        String employeeLastName = myRS.getString("empLname");
                        String customerFirstName = myRS.getString("custFname");
                        String customerLastName = myRS.getString("custLname");
                        Double commission = myRS.getDouble("commissionPercentage");

                        text.append(String.format("%-20s\t%-20s\t%-10s\t$%-9.2f\t%-20s\t$%.2f\n",
                        ProductName,
                        customerFirstName + " " + customerLastName,
                        saleDate,
                        salePrice,
                        employeeFirstName + " " + employeeLastName,
                        commission
                    ));
                }
                resultArea.setText(text.toString());
            } 
        catch (SQLException e){
            resultArea.setText("ERROR: " + e.getLocalizedMessage());
        }
    }
}
