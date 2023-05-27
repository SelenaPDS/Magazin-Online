import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class RegistrationForm extends JDialog{
    private JTextField tfNUME_UTILIZATOR;
    private JTextField tfNUME;
    private JTextField tfPRENUME;
    private JTextField tfADRESA;
    private JTextField tfNR_TELEFON;
    private JPasswordField tfPAROLA;
    private JPasswordField tfCONFIRMA_PAROLA;
    private JButton btnRegister;
    private JButton btnCancel;
    private JLabel PRENUME;
    private JPanel registerPanel;

    public JTextField getTfNUME_UTILIZATOR(){
        return tfNUME_UTILIZATOR;
    }
    public JTextField getTfNUME(){
        return tfNUME;
    }
    public JTextField getTfPRENUME(){
        return tfPRENUME;
    }
    public JTextField getTfADRESA(){
        return tfADRESA;
    }
    public JTextField getTfNR_TELEFON(){
        return tfNR_TELEFON;
    }
    public JPasswordField getTfPAROLA(){
        return tfPAROLA;
    }
    public JPasswordField getTfCONFIRMA_PAROLA(){
        return tfCONFIRMA_PAROLA;
    }

    public RegistrationForm(JFrame parent){
        super(parent);
        setTitle("Create a new account");
        setContentPane(registerPanel);
        setMinimumSize(new Dimension(450,474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btnRegister.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    public void registerUser() {
        String nume_utilizator = tfNUME_UTILIZATOR.getText();
        String parola = String.valueOf(tfPAROLA.getPassword());
        String confirma_parola = String.valueOf(tfCONFIRMA_PAROLA.getPassword());
        String nume = tfNUME.getText();
        String prenume = tfPRENUME.getText();
        String adresa = tfADRESA.getText();
        String nr_telefon = tfNR_TELEFON.getText();

        if(nume_utilizator.isEmpty() || parola.isEmpty() || nume.isEmpty() || prenume.isEmpty() || adresa.isEmpty() || nr_telefon.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please enter all fields", "Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!parola.equals(confirma_parola)){
            JOptionPane.showMessageDialog(this,"Confirm password does not match","Try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDatabase(nume_utilizator,parola,confirma_parola,nume,prenume,adresa,nr_telefon);

        if(user != null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"Failed to register new user","Try again",JOptionPane.ERROR_MESSAGE);
        }
    }

    public User user;
    private User addUserToDatabase(String numeUtilizator, String parola, String confirmaParola, String nume, String prenume, String adresa, String nrTelefon) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost/mybd?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO users (nume_utilizator, parola, confirma_parola, nume, prenume, adresa, nr_telefon) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, numeUtilizator);
            preparedStatement.setString(2, parola);
            preparedStatement.setString(3, confirmaParola);
            preparedStatement.setString(4, nume);
            preparedStatement.setString(5, prenume);
            preparedStatement.setString(6, adresa);
            preparedStatement.setString(7, nrTelefon);

            //Insert row into the table
            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                user = new User();
                user.nume_utilizator = numeUtilizator;
                user.parola = parola;
                user.confirma_parola = confirmaParola;
                user.nume = nume;
                user.prenume = prenume;
                user.adresa = adresa;
                user.nr_telefon = nrTelefon;
            }

            stmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public static void main(String[] args) {

        RegistrationForm myForm = new RegistrationForm(null);
        User user = myForm.user;
        if (user != null) {
            System.out.println("Successful registration of: " + user.nume_utilizator);
        }
        else {
            System.out.println("Registration canceled");
        }
    }
}
