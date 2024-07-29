import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BeSpokedGUI extends JFrame{

    private Connection connection;
    private JTextArea resultArea;
    //Create buttons to be displayed on main page
    private JButton salesEmployeesButton;
    private JButton customersButton;
    private JButton productsButton;
    private JButton salesButton;
    private JButton quarterlyReportButton;
    private JButton createAsaleButton;

    public BeSpokedGUI(){
        components();
        dbConnection();
        actionButtons();
    }
    
    private void components() {
        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Title
        JLabel titleLabel = new JLabel("BeSpoked Bikes");
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        //space
        mainPanel.add(Box.createVerticalStrut(30));

        //initialize buttons
        salesEmployeesButton = new JButton("Sales Employees");
        customersButton = new JButton("Customers");
        productsButton = new JButton("Products");
        salesButton = new JButton("Sales");
        quarterlyReportButton = new JButton("Quarterly Report");
        createAsaleButton = new JButton("Create a Sale");

        //Panel for buttons
        JPanel buttoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttoPanel.add(salesEmployeesButton);
        buttoPanel.add(customersButton);
        buttoPanel.add(productsButton);
        buttoPanel.add(salesButton);
        buttoPanel.add(quarterlyReportButton);
        buttoPanel.add(createAsaleButton);

        //Add new button panel to main panel
        mainPanel.add(buttoPanel);

        //space between buttons and results connection area
        mainPanel.add(Box.createVerticalStrut(100));

        //Results area to display connection to DB
        resultArea = new JTextArea(2, 30);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Database Connection Status"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        resultPanel.add(resultArea);
        mainPanel.add(resultPanel);

        // Set the main panel to the JFrame
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    private void dbConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/BeSpokedDB";
            String user = "root";
            String password = "";

            // check if the JDBC driver was added to the Libraries section
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, user, password);
            resultArea.setText("Connected to the database successfully.");
        } catch (ClassNotFoundException e) {
            resultArea.setText("MySQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            resultArea.setText("Failed to connect to the database. Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actionButtons(){
        salesEmployeesButton.addActionListener(e -> displaySalesEmployeesWindow());
        customersButton.addActionListener(e -> displayCustomersWindow());
        productsButton.addActionListener(e -> displayProductsWindow());
        salesButton.addActionListener(e -> displaySalesWindow());
        quarterlyReportButton.addActionListener(e -> displayQtrReportWindow());
        createAsaleButton.addActionListener(e -> displayMakeSaleWindow());

    }

    private void displaySalesEmployeesWindow(){
        if(connection != null){
            SwingUtilities.invokeLater(() -> new SalesEmployees(connection).setVisible(true));
        }
        else{
            JOptionPane.showMessageDialog(this, "Connection is not established.");
        }
    }

    private void displayCustomersWindow(){
        if(connection != null){
            SwingUtilities.invokeLater(() -> new Customers(connection).setVisible(true));
        }
        else{
            JOptionPane.showMessageDialog(this, "Connection is not established.");
        }
    }

    private void displayProductsWindow(){
        if(connection != null){
            SwingUtilities.invokeLater(() -> new Products(connection).setVisible(true));
        }
        else{
            JOptionPane.showMessageDialog(this, "Connection is not established.");
        }
    }

    private void displaySalesWindow(){
        if(connection != null){
            SwingUtilities.invokeLater(() -> new Sales(connection).setVisible(true));
        }
        else{
            JOptionPane.showMessageDialog(this, "Connection is not established.");
        }
    }

    private void displayQtrReportWindow(){
        if(connection != null){
            SwingUtilities.invokeLater(() -> new QuarterlyReport(connection).setVisible(true));
        }
        else{
            JOptionPane.showMessageDialog(this, "Connection is not established.");
        }
    }

    private void displayMakeSaleWindow(){
        if(connection != null){
            SwingUtilities.invokeLater(() -> new CreateAsale(connection).setVisible(true));
        }
        else{
            JOptionPane.showMessageDialog(this, "Connection is not established.");
        } 
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                BeSpokedGUI frame = new BeSpokedGUI();
                frame.setTitle("BeSpoked Bikes");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1600, 600);
                frame.setVisible(true);
            }
        });
    }
}
