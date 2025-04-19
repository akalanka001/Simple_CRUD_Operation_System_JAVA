import javax.swing.*;
import java.awt.*;

public class SqlInstructionsDialog {

    public static void showSqlInstructions(JFrame parent) {
        String sqlText = """
            -- Create database
            CREATE DATABASE sample;

            -- Use the database
            USE sample;

            -- Create student table
            CREATE TABLE student (
                stu_id INT AUTO_INCREMENT PRIMARY KEY,
                stu_name VARCHAR(100),
                stu_phone VARCHAR(15)
            );
            
            ---sample data if you want 
                INSERT INTO student (stu_id, stu_name, stu_phone) VALUES
                (1, 'akalanka', '713389291'),
                (2, 'perera', '713325284'),
                (3, 'ravindu', '771345678'),
                (4, 'sahan', '723456712');
               
            """;

        JTextArea textArea = new JTextArea(sqlText);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 250));

        JOptionPane.showMessageDialog(parent, scrollPane, "MySQL Setup Instructions", JOptionPane.INFORMATION_MESSAGE);
    }
}
