import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import components.*;
import db.MyDbConnector;

public class Student extends JFrame implements ActionListener {

    private JPanel leftPanel,rightPanel;
    private CardLayout cardLayout;
    private JButton displayBtn,insertBtn,updateBtn,deleteBtn;

    Student(){
        initUI();
    }


    private void initUI(){

        Font buttonFont = new Font("Roboto", Font.PLAIN, 13);

        setTitle("Student");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(600,600);
        setResizable(false);


        leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0,0,200,600);
        leftPanel.setBackground(Color.BLACK);

        displayBtn = createButton("Display",100,buttonFont);
        insertBtn = createButton("Insert",150,buttonFont);
        updateBtn = createButton("Update",200,buttonFont);
        deleteBtn = createButton("Delete",250,buttonFont);

        leftPanel.add(displayBtn);
        leftPanel.add(insertBtn);
        leftPanel.add(updateBtn);
        leftPanel.add(deleteBtn);

        cardLayout = new CardLayout();
        rightPanel = new JPanel();
        rightPanel.setLayout(cardLayout);
        rightPanel.setBounds(200,0,400,600);

        rightPanel.add(new Display(),"Display");
        rightPanel.add(new Insert(),"Insert");
        rightPanel.add(new Update(),"Update");
        rightPanel.add(new Delete(),"Delete");



        add(leftPanel);
        add(rightPanel);

        cardLayout.show(rightPanel,"Display");
        displayBtn.setForeground(Color.WHITE);
        displayBtn.setEnabled(false);

        setVisible(true);
    }

    private void resetButtons(){
        JButton[] buttons = {
                displayBtn, insertBtn, updateBtn, deleteBtn
        };

        for(JButton btn : buttons){
            btn.setEnabled(true);
            btn.setForeground(Color.DARK_GRAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        resetButtons();
        Object src = e.getSource();

        if(src == displayBtn){
            cardLayout.show(rightPanel,"Display");
            displayBtn.setForeground(Color.WHITE);
            displayBtn.setEnabled(false);
        }else if(src == insertBtn){
            cardLayout.show(rightPanel,"Insert");
            insertBtn.setForeground(Color.WHITE);
            insertBtn.setEnabled(false);
        }else if(src == updateBtn){
            cardLayout.show(rightPanel,"Update");
            updateBtn.setForeground(Color.WHITE);
            updateBtn.setEnabled(false);
        }else if(src == deleteBtn){
            cardLayout.show(rightPanel,"Delete");
            deleteBtn.setForeground(Color.WHITE);
            deleteBtn.setEnabled(false);
        }

    }

    private JButton createButton(String text, int y, Font font){
        JButton btn = new JButton(text);
        btn.setBounds(0,y,200,50);
        btn.setForeground(Color.DARK_GRAY);
        btn.setBackground(Color.BLACK);
        btn.setFont(font);
        btn.setFocusable(false);
        btn.setBorderPainted(false);
        btn.addActionListener(this);
        return btn;
    }

}
