package Client.Pages;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Client.Pages.Components.Util;

import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JPanel implements ActionListener {
    private JLabel userLabel, passwordLabel;
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton submitButton, cancelButton;
    private ApplicationInterface app;
    private Dimension screenBounds;
 
    public LoginFrame(ApplicationInterface app) {
        // setTitle("Login Form");
        userLabel = new JLabel("UserID:");
        userLabel.setFont(Util.uiNormalFont);
        userText = new JTextField(20);
        userText.setFont(Util.uiNormalFont);
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Util.uiNormalFont);
        passwordText = new JPasswordField(20);
        passwordText.setFont(Util.uiNormalFont);
        submitButton = new JButton("Submit");
        submitButton.setFont(Util.uiNormalFont);
        submitButton.setBackground(Util.bottomToolBarBtnColor);
        submitButton.setForeground(Util.themeColor1);
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(Util.uiNormalFont);
        cancelButton.setBackground(Util.bottomToolBarBtnColor);
        cancelButton.setForeground(Util.themeColor1);
        // setSize(, HEIGHT);

        screenBounds = Toolkit.getDefaultToolkit().getScreenSize();

        setSize((int)screenBounds.getWidth(), (int)screenBounds.getHeight());

        setBackground(Util.themeColor1);

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(400, 400, 400, 400));
        this.app = app;
 
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.weighty = 10;
        constraints.weighty = 10;
 
        // add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        setPreferredSize(screenBounds);
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

        panel.setBackground(Util.themeColor1);
 
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
