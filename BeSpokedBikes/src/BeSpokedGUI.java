import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class BeSpokedGUI extends JFrame{

    private Connection connection;
    private JTextArea resultArea;

    public BeSpokedGUI(){
        components();
        dbConnection();
    }
    
    private void components() {
        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Results area to display connection to DB
        resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Results"));
        mainPanel.add(scrollPane);

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

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                BeSpokedGUI frame = new BeSpokedGUI();
                frame.setTitle("BeSpoked Bikes");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
