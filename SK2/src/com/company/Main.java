package com.company;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {

//
//        JFrame frame = new JFrame("Gadu-Gadu");
//        Login login = new Login();
//        frame.setContentPane(login.getPanel1());
//        login.setFrame(frame);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setSize(500,400);
//        frame.setVisible(true);


        Socket socket = new Socket(Client.host,Client.port);


//        Scanner scanner = new Scanner(System.in);
//        for (int i=0;i<3;i++){
//            String clientMessage = scanner.nextLine();
//            OutputStream os = clientSocket.getOutputStream();
//            String msg = clientMessage;
//            os.write(msg.getBytes());
//      //  }
//        Client client = new Client("user1","linux123",socket);
//        client.login();
//        client.add_friend("user2");
//        client.send_mss("user2","Wiadomoscaaaaaa 1.");
//        client.send_mss("user2","Wiadomosc 2.");
//        client.send_mss("user2","Wiadomosc 3.");
//        client.send_mss("user2","Wiadomosc 4.");
//
        Client client = new Client("user2","linux123",socket);
        client.login();
        client.load_old_mss("user1");
        socket.close();
    }





}
