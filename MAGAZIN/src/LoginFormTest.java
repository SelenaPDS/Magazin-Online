import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class LoginFormTest {

    private LoginForm loginForm;

    @BeforeEach
    public void setup() {
        SwingUtilities.invokeLater(() -> {
            JFrame parent = new JFrame();
            loginForm = new LoginForm(parent);
        });
    }

    @AfterEach
    public void cleanup() {
        SwingUtilities.invokeLater(() -> loginForm.dispose());
    }

    @Test
    public void testAuthenticationSuccess() {
        SwingUtilities.invokeLater(() -> {
            // Simulate user input
            loginForm.getTfNUME_UTILIZATOR().setText("username");
            loginForm.getTfPAROLA().setText("password");

            // Perform authentication
            loginForm.getBtnOK().doClick();

            // Verify the user object is not null
            Assertions.assertNotNull(loginForm.user);

            // Verify the user information
            Assertions.assertEquals("username", loginForm.user.nume_utilizator);
            Assertions.assertEquals("password", loginForm.user.parola);
        });
    }

    @Test
    public void testAuthenticationFailure() {
        SwingUtilities.invokeLater(() -> {
            // Simulate invalid user input
            loginForm.getTfNUME_UTILIZATOR().setText("nonexistent");
            loginForm.getTfPAROLA().setText("password");

            // Perform authentication
            loginForm.getBtnOK().doClick();

            // Verify that an error message is shown
            Dialog dialog = getVisibleDialog();
            Assertions.assertNotNull(dialog);
            Assertions.assertEquals("Email or Password Invalid", dialog.getTitle());
        });
    }

    private Dialog getVisibleDialog() {
        for (Window window : Window.getWindows()) {
            if (window instanceof Dialog && window.isVisible()) {
                return (Dialog) window;
            }
        }
        return null;
    }
}
