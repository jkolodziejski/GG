package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class Register {
    private JButton registerButton;
    private JTextField login_register;
    private JPasswordField registerpassword;
    private JPanel Register_panel;

    private Client creat_user(String user, String password) throws IOException {
        Socket clientSocket = new Socket(Client.host, Client.port);
        Client client = new Client(user,password,clientSocket);
        return client;
    }

    public Register() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client client = null;
                try {
                    client=creat_user(login_register.getText(), String.valueOf(registerpassword.getPassword()));

                    client.register();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    System.out.println("Problem z rejestracja");
                }
            }
        });
    }

    public JPanel getRegister_panel() {
        return Register_panel;
    }
}
