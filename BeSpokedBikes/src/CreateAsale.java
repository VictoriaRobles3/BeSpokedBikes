import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.*;

public class CreateAsale extends JFrame {
    private Connection connection;

    public CreateAsale(Connection connection){
        this.connection = connection;
        start();
    }

    private void start(){
        setTitle("CREATE A SALE");
        setSize(1600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(8,2, 10, 10));

        //labels
        JLabel productIdLabel = new JLabel("Product ID:");
        JLabel employeeIdLabel = new JLabel("Employee ID:");
        JLabel customerIdLabel = new JLabel("Customer ID:");
        JLabel saleDateLabel = new JLabel("Sale Date (YYYY-MM-DD):");
        JLabel salePriceLabel = new JLabel("Sale Price:");
        JLabel discountLabel = new JLabel("Discount:");
        JLabel finalPriceLabel = new JLabel("Final Price:");

        //text fields for input
        JTextField productIdField = new JTextField();
        JTextField employeeIdField = new JTextField();
        JTextField customerIdField = new JTextField();
        JTextField saleDateField = new JTextField();
        JTextField salePriceField = new JTextField();
        JTextField discountField = new JTextField();
        JTextField finalPriceField = new JTextField();

        //button to make a purchase. 
        JButton purchaseButton = new JButton("Purchase");

        // Add components to the frame
        add(productIdLabel);
        add(productIdField);
        add(employeeIdLabel);
        add(employeeIdField);
        add(customerIdLabel);
        add(customerIdField);
        add(saleDateLabel);
        add(saleDateField);
        add(salePriceLabel);
        add(salePriceField);
        add(discountLabel);
        add(discountField);
        add(finalPriceLabel);
        add(finalPriceField);
        // add empty label to left side, making purchase button to the right side
        add(new JLabel());
        //Display button at end of input fields
        add(purchaseButton);

        // action listener for purchase button
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int productId = Integer.parseInt(productIdField.getText());
                    int employeeId = Integer.parseInt(employeeIdField.getText());
                    int customerId = Integer.parseInt(customerIdField.getText());
                    LocalDate saleDate = LocalDate.parse(saleDateField.getText());
                    double salePrice = Double.parseDouble(salePriceField.getText());
                    double discount = Double.parseDouble(discountField.getText());
                    double finalPrice = Double.parseDouble(finalPriceField.getText());

                    // Insert the variables into the parameters of makeSale method
                    makeSale(productId, employeeId, customerId, saleDate, salePrice, discount, finalPrice);
                    //clear field inputs when makeSale method executes
                    productIdField.setText("");
                    employeeIdField.setText("");
                    customerIdField.setText("");
                    saleDateField.setText("");
                    salePriceField.setText("");
                    discountField.setText("");
                    finalPriceField.setText("");

                    JOptionPane.showMessageDialog(CreateAsale.this, "Purchase made successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CreateAsale.this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //frame visibility
        setVisible(true);
    }

    private void makeSale(int productId, int employeeId, int customerId, LocalDate saleDate, double salePrice, double discount, double finalPrice) throws SQLException {
        String sql = "INSERT INTO Sales (prodid, empid, custid, saleDate, salePrice, discountApplied, finalPrice) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, employeeId);
            pstmt.setInt(3, customerId);
            pstmt.setDate(4, Date.valueOf(saleDate));
            pstmt.setDouble(5, salePrice);
            pstmt.setDouble(6, discount);
            pstmt.setDouble(7, finalPrice);
            pstmt.executeUpdate();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BeSpokedDB", "root", ""); //need pass here dont forget!
                new CreateAsale(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}