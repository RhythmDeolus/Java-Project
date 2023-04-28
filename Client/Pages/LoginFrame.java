package Client.Pages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.CardLayout;

public class LoginFrame extends JPanel implements ActionListener {
    private JLabel userLabel, passwordLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton submitButton, cancelButton;
    private ApplicationInterface app;
 
    public LoginFrame(ApplicationInterface app) {
        // setTitle("Login Form");
        userLabel = new JLabel("Username:");
        userText = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordText = new JPasswordField(20);
        submitButton = new JButton("Submit");
        cancelButton = new JButton("Cancel");
        this.app = app;
 
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
 
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(userLabel, constraints);
 
        constraints.gridx = 1;
        panel.add(userText, constraints);
 
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);
 
        constraints.gridx = 1;
        panel.add(passwordText, constraints);
 
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);
 
        constraints.gridy = 3;
        panel.add(cancelButton, constraints);
 
        // add the panel to the frame
        add(panel);
 
        submitButton.addActionListener(this);
        cancelButton.addActionListener(this);
        // pack();
        // setLocationRelativeTo(null); // center the frame on the screen
        setVisible(true);
    }
 
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == submitButton) {
            String user = userText.getText();
            String password = new String(passwordText.getPassword());
            app.login(Integer.parseInt(user), password);
            // if (user.equals("admin") && password.equals("password")) {
            //     JOptionPane.showMessageDialog(this, "Login Successful!");
            //     CardLayout cl = (CardLayout) app.getContentPane().getLayout();
            //     cl.next(app.getContentPane());
            // } else {
            //     JOptionPane.showMessageDialog(this, "Invalid Username or Password");
            // }
        } else if (ae.getSource() == cancelButton) {
            System.exit(0);
        }
    }
 
    // public static void main(String[] args) {
    //     new LoginFrame();
    // }
}
