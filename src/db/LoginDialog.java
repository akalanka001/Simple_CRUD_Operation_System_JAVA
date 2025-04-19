package db;

import javax.swing.*;

public class LoginDialog {
    private String username;
    private String password;
    private boolean confirmed = false;

    public LoginDialog() {
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        Object[] fields = {
                "MySQL Username:", userField,
                "MySQL Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "MySQL Login", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            username = userField.getText();
            password = new String(passField.getPassword());
            confirmed = true;
        }
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isConfirmed() { return confirmed; }
}
