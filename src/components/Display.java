package components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import db.MyDbConnector;

import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Display extends JPanel {

    private MyDbConnector dbConnector;
    private JLabel titleLabel;
    private Font font;
    private JTable table;
    private JScrollPane scrollPane;
    private String stuId,stuName,stuPhone;
    private DefaultTableModel tableModel;


    public Display(){
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

        titleLabel = new JLabel("Display Student Details");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 0, 400, 50);

        String[] columnNames = {"ID", "Name", "Phone no"};
        tableModel = new DefaultTableModel(columnNames,0);

        table = new JTable(tableModel);
        JTableHeader header = table.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(new Color(102, 153, 255));

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,100,350,400);
        add(scrollPane);

        labelBg.add(titleLabel);
        add(labelBg);
        loadStudentDetails();

    }
    private void loadStudentDetails(){
        dbConnector = new MyDbConnector();
        String query = "select * from student";
        Connection con = dbConnector.getMyConnection();
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                stuId = rs.getString(1);
                stuName = rs.getString(2);
                stuPhone = rs.getString(3);

                tableModel.addRow(new Object[]{stuId,stuName,stuPhone});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Error Loading Student Details","Error",JOptionPane.ERROR_MESSAGE);
        }

    }
}
