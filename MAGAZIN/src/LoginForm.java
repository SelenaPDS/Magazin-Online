import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginForm extends JDialog{
    private JPasswordField tfPAROLA;
    private JButton btnOK;
    private JButton btnCancel;
    private JPanel loginPanel;
    private JTextField tfNUME_UTILIZATOR;

    public LoginForm(JFrame parent){
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nume_utilizator = tfNUME_UTILIZATOR.getText();
                String parola = String.valueOf(tfPAROLA.getPassword());

                user = getAuthenticatedUser(nume_utilizator, parola);

                if (user != null) {
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(LoginForm.this,
                            "Email or Password Invalid",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }


        });
    }
    public User user;
    private User getAuthenticatedUser(String numeUtilizator, String parola) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            // Connected to database successfully...

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE nume_utilizator=? AND parola=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, numeUtilizator);
            preparedStatement.setString(2, parola);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm(null);
    }

}
