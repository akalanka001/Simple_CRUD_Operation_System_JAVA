import db.LoginDialog;
import db.MyDbConnector;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LoginDialog login = new LoginDialog();
        if (login.isConfirmed()) {
            MyDbConnector.setCredentials(login.getUsername(), login.getPassword());
            SqlInstructionsDialog.showSqlInstructions(null);
            new Student();
        } else {
            JOptionPane.showMessageDialog(null, "Login cancelled. Exiting.");
            System.exit(0);
        }
    }
}
