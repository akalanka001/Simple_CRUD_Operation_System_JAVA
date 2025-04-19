package components;

import db.MyDbConnector;

import javax.swing.*;
import java.awt.*;

public class Delete extends JPanel {
    private MyDbConnector dbConnector;
    private JLabel titleLabel;
    private Font font;

    public Delete(){
        initUI();
    }
    private void initUI(){
        font = new Font("Segoe UI", Font.BOLD, 24);
        setLayout(null);
        setBackground(Color.WHITE);

        JPanel labelBg = new JPanel();
        labelBg.setLayout(null);
        labelBg.setBounds(0, 0, 400, 50);
        labelBg.setBackground(Color.BLACK);

        titleLabel = new JLabel("Delete Student Details");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 0, 400, 50); // Text starts at 20px horizontally

        labelBg.add(titleLabel);
        add(labelBg);

    }
}
