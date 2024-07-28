import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.*;

public class Products extends JFrame {
    private Connection connection;

    public Products(Connection connection){
        this.connection = connection;
        start();
    }

    private void start(){
        setTitle("PRODUCTS");
        setSize(1600,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Products List"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        getProducts(resultArea);
    }

    private void getProducts(JTextArea resultArea){
        String sqlCommand = "SELECT name, manufacturer, prodStyle, purchasePrice, salePrice, QtyOnHand, commissionPercentage " +
        "FROM Products";

        try(Statement myStmt = connection.createStatement();
        ResultSet myRS = myStmt.executeQuery(sqlCommand)){
            StringBuilder text = new StringBuilder();

            text.append("Name\t\tManufacturer\tStyle\t\tPurchase Price\t\tSale Price\tQuantity\tCommission Percentage\n");
            text.append("-------------\t-----------\t------------\t-------------\t\t----------\t--------\t---------------------\n");

            while (myRS.next()) {
                String name = myRS.getString("name");
                String manufacturer = myRS.getString("manufacturer");
                String prodStyle = myRS.getString("prodStyle");
                Double purchasePrice = myRS.getDouble("purchasePrice");
                Double salePrice = myRS.getDouble("salePrice");
                int qtyOnHand = myRS.getInt("QtyOnHand");
                Double commissionPercentage = myRS.getDouble("commissionPercentage");

                text.append(String.format("%-16s\t%-15s\t%-10s\t$%-13.2f\t\t$%-10.2f\t%-8d\t%.2f%%\n",
                name,
                manufacturer,
                prodStyle,
                purchasePrice,
                salePrice,
                qtyOnHand,
                commissionPercentage
                ));
            }
            resultArea.setText(text.toString());
        } 
        catch (SQLException e){
        resultArea.setText("ERROR: " + e.getLocalizedMessage());
        }
    }
}
