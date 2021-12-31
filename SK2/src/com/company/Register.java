package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Socket;

public class Register {
    private JButton registerButton;
    private JTextField login_register;
    private JPasswordField registerpassword;
    private JPanel Register_panel;
    private JLabel user_exist;
    private JLabel empty_error;
    private JFrame frame;

    private Client client = null;

    private Client creat_user(String user, String password) throws Exception {
        Socket clientSocket = new Socket(Client.host, Client.port);
        Client client = new Client(user,password,clientSocket);
        return client;
    }

    public Register(JFrame jFrame) {
        this.frame=jFrame;
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                user_exist.setVisible(false);
                empty_error.setVisible(false);
                try {
                    client=creat_user(login_register.getText(), String.valueOf(registerpassword.getPassword()));


                    String answer=client.register();
                    if(answer.equals("User already exist")){
                        user_exist.setVisible(true);
                    }
                    else {
                        client.getnewClientSocket();
                        client.login();
                        Main_pulpit main_pulpit = new Main_pulpit(client);
                        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                        frame.addWindowListener(new WindowAdapter() {

                            @Override
                            public void windowClosing(WindowEvent e) {
                                int confirm = JOptionPane.showOptionDialog(
                                        null, "Are You Sure to Close Application?",
                                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                                if (confirm == 0) {
                                    try {
                                        client.login_out();
                                    } catch (IOException ex) {
                                        ex.printStackTrace();
                                    }
                                    System.exit(0);

                                }

                            }
                        });
                        frame.setContentPane(main_pulpit.getMain_pulpit_panel());

                        frame.pack();
                        frame.setSize(500,400);

                        frame.setVisible(true);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Problem z rejestracja");
                    empty_error.setVisible(true);
                }
            }
        });
    }

    public JPanel getRegister_panel() {
        return Register_panel;
    }
}
