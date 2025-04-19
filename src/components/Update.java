package components;

import db.MyDbConnector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update extends JPanel implements ActionListener {
    private MyDbConnector dbConnector;
    private JLabel titleLabel;
    private Font font,font2;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    private JTableHeader tableHeader;
    private JLabel idLabel,nameLabel,phoneLabel;
    private JTextField idTextField,nameTextField,phoneTextField;
    private JButton loadButton,updateButton,clearButton;
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private String id,name,phone,newName,newPhone,phoneText,nameText;

    public Update(){
        initUI();
    }
    private void initUI(){
        font = new Font("Segoe UI", Font.BOLD, 24);
        font2 = new Font("MV Boli",Font.PLAIN,15);
        setLayout(null);
        setBackground(Color.WHITE);

        JPanel labelBg = new JPanel();
        labelBg.setLayout(null);
        labelBg.setBounds(0, 0, 400, 50);
        labelBg.setBackground(Color.BLACK);

        titleLabel = new JLabel("Update Student Details");
        titleLabel.setFont(font);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 0, 400, 50);

        String[] columnNames = {"ID","Name","Phone"};
        tableModel = new DefaultTableModel(columnNames,0);

        table = new JTable(tableModel);
        tableHeader = table.getTableHeader();
        tableHeader.setForeground(Color.WHITE);
        tableHeader.setBackground(new Color(102, 153, 255));


        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20,100,350,250);

        idLabel = createLabel("ID",400);
        nameLabel = createLabel("Name",450);
        phoneLabel = createLabel("Phone",500);

        idTextField = createTextField(400);
        nameTextField = createTextField(450);
        phoneTextField = createTextField(500);

        loadButton = createButtons("Load",400);
        updateButton = createButtons("Update",450);
        clearButton = createButtons("Clear",500);


        labelBg.add(titleLabel);
        add(labelBg);
        add(scrollPane);
        add(idLabel);
        add(nameLabel);
        add(phoneLabel);
        add(idTextField);
        add(nameTextField);
        add(phoneTextField);
        add(loadButton);
        add(updateButton);
        add(clearButton);

        loadStudentDetails();

    }
    private JLabel createLabel(String text,int y){
        JLabel label = new JLabel(text);
        label.setBounds(20,y,100,50);
        label.setFont(font2);
        return label;
    }
    private JTextField createTextField(int y){
        JTextField textField = new JTextField();
        textField.setBounds(100,y,150,50);
        textField.setFont(font2);
        return textField;
    }
    private JButton createButtons(String text,int y){
        JButton btn = new JButton(text);
        btn.setBounds(260,y,100,30);
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.GRAY);
        btn.setFocusable(false);
        btn.setBorderPainted(false);
        btn.addActionListener(this);
        return btn;
    }

    private void loadStudentDetails(){
        tableModel.setRowCount(0);
        String sql = "Select * from student";
        dbConnector = new MyDbConnector();
        con = dbConnector.getMyConnection();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while(rs.next()){
                id = rs.getString(1);
                name = rs.getString(2);
                phone = rs.getString(3);

                tableModel.addRow(new Object[]{id,name,phone});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Can't load Student details at the moment","Load error",0);
        }

    }
    private void loadDetails(String newId){
        String sql = "Select * from student where stu_id = ?";
        dbConnector = new MyDbConnector();
        con = dbConnector.getMyConnection();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,newId);
            rs = ps.executeQuery();

            while(rs.next()){
                String newId2 = rs.getString(1);
                newName = rs.getString(2);
                newPhone = rs.getString(3);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Can't load Student details at the moment","Load error",0);
        }
    }
    private boolean validateId(String newId){
        String sql = "Select stu_id from student";
        dbConnector = new MyDbConnector();
        con = dbConnector.getMyConnection();
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                id = rs.getString(1);
                if(newId.equals(id)){
                    return true;
                }
            }
        }catch (SQLException e) {
                JOptionPane.showMessageDialog(this,"Can't load Student details at the moment","Load error",0);
            }
        JOptionPane.showMessageDialog(this,"Can't find details wth this ID, check the id please","ID error",0);
        return false;
    }
    private boolean validateData(){
        phoneText = phoneTextField.getText().trim();
        nameText = nameTextField.getText().trim();
        if(phoneText.isEmpty() || nameText.isEmpty()){
            JOptionPane.showMessageDialog(this,"Please fill all the fields","Empty Error",0);
            return false;
        }else if(phoneText.matches("//d{9}")){
            JOptionPane.showMessageDialog(this,"Phone number should be 9 numbers","Phone number error",0);
            return false;
        }
        return true;
    }
    private void updateData(){
        String sql = "update student set stu_name = ? , stu_phone = ? where stu_id = ? ";
        dbConnector = new MyDbConnector();
        con = dbConnector.getMyConnection();
        String userId = idTextField.getText();
        String stuName = nameTextField.getText();
        String stuPhone = phoneTextField.getText();
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,stuName);
            ps.setString(2,stuPhone);
            ps.setString(3,userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,"Can't update at the moment","Update error",0);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if(src == loadButton){
            id = idTextField.getText().trim();
            if(validateId(id)){
                loadDetails(id);
                nameTextField.setText(newName);
                phoneTextField.setText(newPhone);
            }
        }
        if(src == updateButton){
            if(validateData()){
                updateData();
                loadStudentDetails();
                JOptionPane.showMessageDialog(this,"Record Successfully","Alert",1);
            }
        }
        if(src == clearButton){
            idTextField.setText("");
            nameTextField.setText("");
            phoneTextField.setText("");
        }
    }
}
