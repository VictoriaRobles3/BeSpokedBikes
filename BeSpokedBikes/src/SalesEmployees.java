import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.*;

public class SalesEmployees extends JFrame {
    private Connection connection;

    public SalesEmployees(Connection connection){
        this.connection = connection;
        start();
    }

    private void start(){
        setTitle("SALES EMPLOYEES");
        setSize(1600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Sales Employees List"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        getSalesEmployees(resultArea);
    }

    private void getSalesEmployees(JTextArea resultArea){
        String sqlCommand = "SELECT Fname, Lname, address, phone, StartDate, TerminationDate, Manager " +
                            "FROM SalesEmployees";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){
                StringBuilder text = new StringBuilder();

                text.append("\nFirst Name\tLast Name\tAddress\t\t\tPhone\t\tStart Date\tTermination Date\t\tManager\n");
                text.append("----------\t---------\t--------------------------\t------------\t----------\t----------------\t\t----------------\n");

                while (myRS.next()) {
                    String firstName = myRS.getString("Fname");
                    String lastName = myRS.getString("Lname");
                    String address = myRS.getString("address");
                    String phone = myRS.getString("phone");
                    LocalDate StartDate = myRS.getDate("StartDate").toLocalDate();
                    LocalDate TerminationDate = myRS.getDate("TerminationDate") != null ? myRS.getDate("TerminationDate").toLocalDate() : null;
                    String manager = myRS.getString("Manager");


                    text.append(String.format("%-15s\t%-15s\t%-35s\t%-20s\t%-12s\t%-60s\t%-20s\n",
                    firstName,
                    lastName,
                    address,
                    phone,
                    StartDate,
                    (TerminationDate != null ? TerminationDate : "N/A"),
                    manager));
                }
                resultArea.setText(text.toString());
            } 
        catch (SQLException e){
            resultArea.setText("ERROR: " + e.getLocalizedMessage());
        }
    }
}
