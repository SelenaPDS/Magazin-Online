import org.junit.jupiter.api.*;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class RegistrationFormTest {

    private RegistrationForm registrationForm;

    @BeforeEach
    public void setup() {
        CountDownLatch latch = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            JFrame parent = new JFrame();
            registrationForm = new RegistrationForm(parent);
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void cleanup() {
        SwingUtilities.invokeLater(() -> registrationForm.dispose());
    }

    @Test
    public void testRegistrationSuccess() {
        SwingUtilities.invokeLater(() -> {
            // Simulate user input
            registrationForm.getTfNUME_UTILIZATOR().setText("username");
            registrationForm.getTfPAROLA().setText("password");
            registrationForm.getTfCONFIRMA_PAROLA().setText("password");
            registrationForm.getTfNUME().setText("John");
            registrationForm.getTfPRENUME().setText("Doe");
            registrationForm.getTfADRESA().setText("123 Street");
            registrationForm.getTfNR_TELEFON().setText("123456789");

            // Perform registration
            registrationForm.registerUser();

            // Verify the user object is not null
            Assertions.assertNotNull(registrationForm.user);

            // Verify the user information
            Assertions.assertEquals("username", registrationForm.user.nume_utilizator);
            Assertions.assertEquals("password", registrationForm.user.parola);
            Assertions.assertEquals("password", registrationForm.user.confirma_parola);
            Assertions.assertEquals("John", registrationForm.user.nume);
            Assertions.assertEquals("Doe", registrationForm.user.prenume);
            Assertions.assertEquals("123 Street", registrationForm.user.adresa);
            Assertions.assertEquals("123456789", registrationForm.user.nr_telefon);
        });
    }

    @Test
    public void testEmptyFields() {
        SwingUtilities.invokeLater(() -> {
            // Simulate empty fields
            registrationForm.getTfNUME_UTILIZATOR().setText("");
            registrationForm.getTfPAROLA().setText("");
            registrationForm.getTfCONFIRMA_PAROLA().setText("");
            registrationForm.getTfNUME().setText("");
            registrationForm.getTfPRENUME().setText("");
            registrationForm.getTfADRESA().setText("");
            registrationForm.getTfNR_TELEFON().setText("");

            // Perform registration
            registrationForm.registerUser();

            // Verify that an error message is shown
            Dialog dialog = getVisibleDialog();
            Assertions.assertNotNull(dialog);
            Assertions.assertEquals("Please enter all fields", dialog.getTitle());
        });
    }

    @Test
    public void testMismatchedPasswords() {
        SwingUtilities.invokeLater(() -> {
            // Simulate mismatched passwords
            registrationForm.getTfNUME_UTILIZATOR().setText("username");
            registrationForm.getTfPAROLA().setText("password1");
            registrationForm.getTfCONFIRMA_PAROLA().setText("password2");
            registrationForm.getTfNUME().setText("John");
            registrationForm.getTfPRENUME().setText("Doe");
            registrationForm.getTfADRESA().setText("123 Street");
            registrationForm.getTfNR_TELEFON().setText("123456789");

            // Perform registration
            registrationForm.registerUser();

            // Verify that an error message is shown
            Dialog dialog = getVisibleDialog();
            Assertions.assertNotNull(dialog);
            Assertions.assertEquals("Confirm password does not match", dialog.getTitle());
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
