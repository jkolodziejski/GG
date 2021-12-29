package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Login {
    private JPanel panel1;
    private JButton Login;
    JFrame frame;

    private JPasswordField passwordField1;
    private JTextField Login_area;
    private JButton donTHaveAccountButton;
    private JLabel login_error;
    private JLabel password_error;

    private Client creat_user(String user, String password) throws IOException {
        Socket clientSocket = new Socket(Client.host, Client.port);
        Client client = new Client(user,password,clientSocket);
        return client;
    }


    public Login() {
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = null;
                try {
                    login_error.setVisible(false);
                    password_error.setVisible(false);
                    client=creat_user(Login_area.getText(), String.valueOf(passwordField1.getPassword()));

                    String answer = client.login();
                    if (answer.equals("Wrong login")){
                        login_error.setVisible(true);
                    }
                    else if(answer.equals("Wrong password")){
                        password_error.setVisible(true);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Problem z logowaniem");
                }


            }
        });
        donTHaveAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register();
                //frame.removeAll();
                frame.setContentPane(register.getRegister_panel());

                frame.pack();
                frame.setSize(500,400);

                frame.setVisible(true);

            }
        });
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public JPanel getPanel1() {
        return panel1;
    }

}
