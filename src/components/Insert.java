package components;

import db.MyDbConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Insert extends JPanel implements ActionListener {
    private MyDbConnector dbConnector;
    private JLabel titleLabel,stuNameLabel,stuPhoneLabel;
    private JTextField stuNameTextField,stuPhoneTextField;
    private JButton submitButton,clearButton,refreshButton;
    private Font font,font2;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTableHeader tableHeader;
    private Connection con;
    private Statement stmt;
    private PreparedStatement ps;
    private ResultSet rs;
    private String stuId,stuName,stuPhone,nameText,phoneText;
    private JScrollPane scrollPane;
    private JPanel labelBg;

    public Insert(){
        initUI();

    }
    private void initUI(){
        font = new Font("Segoe UI", Font.BOLD, 24);
        font2 = new Font("MV Boli", Font.PLAIN, 15);
        setLayout(null);
        setBackground(Color.WHITE);

        labelBg = new JPanel();
        labelBg.setLayout(null);
        labelBg.setBounds(0, 0, 400, 50);
        labelBg.setBackground(Color.BLACK);

        titleLabel = new JLabel("Insert Student Details");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 0, 400, 50);

        String[] columnNames = {"Student ID","Student Name","Student Phone"};
        tableModel = new DefaultTableModel(columnNames,0);

        table = new JTable(tableModel);
        tableHeader = table.getTableHeader();
        tableHeader.setBackground(new Color(102, 153, 255));
        tableHeader.setForeground(Color.WHITE);


        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,100,350,200);

        stuNameLabel = new JLabel("Student Name");
        stuNameLabel.setBounds(20,350,150,50);
        stuNameLabel.setFont(font2);

        stuPhoneLabel = new JLabel("Student Phone");
        stuPhoneLabel.setBounds(20,400,150,50);
        stuPhoneLabel.setFont(font2);

        stuNameTextField = new JTextField();
        stuNameTextField.setBounds(150,350,150,50);
        stuNameTextField.setFont(font2);

        stuPhoneTextField = new JTextField();
        stuPhoneTextField.setBounds(150,400,150,50);
        stuPhoneTextField.setFont(font2);

        submitButton = createButtons("Insert",20);
        clearButton = createButtons("Clear",150);
        refreshButton = createButtons("Refresh",280);


        loadStudentDetails();
        add(scrollPane);
        labelBg.add(titleLabel);
        add(labelBg);
        add(stuNameLabel);
        add(stuPhoneLabel);
        add(stuNameTextField);
        add(stuPhoneTextField);
        add(submitButton);
        add(clearButton);
        add(refreshButton);

    }
    private JButton createButtons(String text, int x){
        JButton btn = new JButton(text);
        btn.setBounds(x,500,100,30);
        btn.setFont(font2);
        btn.setBackground(Color.GRAY);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusable(false);
        btn.addActionListener(this);

        return btn;
    }
    private void insertStudentDetails(){
        nameText = stuNameTextField.getText();
        phoneText = stuPhoneTextField.getText();

        String sql = "insert into student (stu_name,stu_phone) values (? ,?)";
        dbConnector = new MyDbConnector();
        con = dbConnector.getMyConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,nameText);
            ps.setString(2,phoneText);
            ps.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Unable to  insert data","Insertion error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateData(){
        phoneText = stuPhoneTextField.getText().trim();
        nameText = stuNameTextField.getText().trim();

        if(phoneText.isEmpty() || nameText.isEmpty()){
            JOptionPane.showMessageDialog(this,"All field should filled before submit","Required fields",0);
            return false;
        }else if(!phoneText.matches("\\d{9}")){
            JOptionPane.showMessageDialog(this,"Phone number should be 9 integers","Phone number error",0);
            return false;
        }
        return true;

    }

    private void loadStudentDetails(){
        tableModel.setRowCount(0);
        String sql = "select * from student";
        dbConnector = new MyDbConnector();
        con = dbConnector.getMyConnection();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                stuId = rs.getString(1);
                stuName = rs.getString(2);
                stuPhone = rs.getString(3);

                tableModel.addRow(new Object[]{stuId,stuName,stuPhone});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Cant load student details","Loading Error",JOptionPane.ERROR_MESSAGE);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if(src == submitButton){
            if(validateData()){
                insertStudentDetails();
                loadStudentDetails();
            }

        }else if(src == clearButton){
            stuNameTextField.setText("");
            stuPhoneTextField.setText("");
        }else if(src == refreshButton){
            loadStudentDetails();
        }

    }
}
