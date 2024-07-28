import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.*;

public class Customers extends JFrame {
    private Connection connection;

    public Customers(Connection connection){
        this.connection = connection;
        start();
    }

    private void start(){
        setTitle("CUSTOMERS");
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Customers List"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        getCustomers(resultArea);
    }

    private void getCustomers(JTextArea resultArea){
        String sqlCommand = "SELECT Fname, Lname, address, phone, StartDate " +
                            "FROM Customers " +
                            "ORDER BY StartDate ASC";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){
                StringBuilder text = new StringBuilder();

                text.append("\nFirst Name\tLast Name\tAddress\t\t\tPhone\t\tFirst Purchase Date\n");
                text.append("----------\t---------\t-----------------------------\t-------------\t---------------\n");

                while (myRS.next()) {
                    String firstName = myRS.getString("Fname");
                    String lastName = myRS.getString("Lname");
                    String address = myRS.getString("address");
                    String phone = myRS.getString("phone");
                    LocalDate StartDate = myRS.getDate("StartDate").toLocalDate();

                    text.append(String.format("%-10s\t%-10s\t%-42s\t%-10s\t%-15s\n",
                    firstName,
                    lastName,
                    address,
                    phone,
                    StartDate));
                }
                resultArea.setText(text.toString());
            } 
        catch (SQLException e){
            resultArea.setText("ERROR: " + e.getLocalizedMessage());
        }
    }
}
