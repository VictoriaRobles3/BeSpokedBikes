import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class QuarterlyReport extends JFrame {
    private Connection connection;

    public QuarterlyReport(Connection connection){
        this.connection = connection;
        start();
    }

    private void start(){
        setTitle("QUARTERLY REPORT");
        setSize(1600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("QUARTERLY REPORT"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        getQuarterlyReport(resultArea);
    }

    private void getQuarterlyReport(JTextArea resultArea){
        String sqlCommand = "SELECT q.year, q.quarter, q.totalSales, q.bonusAmount, q.totalQtrCommission, " +
                            "e.Fname AS empFname, e.Lname AS empLname " + 
                            "FROM QuarterlyBonuses q " +
                            "JOIN SalesEmployees e ON q.empid = e.empid " + 
                            "ORDER BY q.year DESC, q.quarter DESC";

        try(Statement myStmt = connection.createStatement();
            ResultSet myRS = myStmt.executeQuery(sqlCommand)){
                StringBuilder text = new StringBuilder();

                text.append("\nSales Employee Name\tYear\tQuarter\tTotal Quarter Sales\t Bonus Amount\tTotal Quarter Commission\n");
                text.append("--------------------\t-----\t------\t---------------\t------------\t---------------------\n");

                while (myRS.next()) {
                    String empFirstName = myRS.getString("empFname");
                    String empLastName = myRS.getString("empLname");
                    int year = myRS.getInt("year");
                    int quarter = myRS.getInt("quarter");
                    Double totalQtrSales = myRS.getDouble("totalSales");
                    Double bonusAmount = myRS.getDouble("bonusAmount");
                    Double qtrCommission = myRS.getDouble("totalQtrCommission");

                    text.append(String.format("%-20s\t%4d\t%2d\t$%-25.2f\t$%-28.2f\t$%-18.2f\n",
                    empFirstName + " " + empLastName,
                    year,
                    quarter,
                    totalQtrSales,
                    bonusAmount,
                    qtrCommission
                    ));
                }
                resultArea.setText(text.toString());
            } 
        catch (SQLException e){
            resultArea.setText("ERROR: " + e.getLocalizedMessage());
        }
    }
}
