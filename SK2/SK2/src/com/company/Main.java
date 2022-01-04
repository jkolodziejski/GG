package com.company;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
////        Socket socket = new Socket(Client.host,Client.port);
////        Client client = new Client("user1","linux123",socket);
////
////        client.login();
////        client.add_friend("user2");
////        client.send_mss("user2","test1");
////        client.login_out();
////        socket.close();
//
//        Socket socket1 = new Socket(Client.host,Client.port);
//        Client client1 = new Client("user2","linux123",socket1);
//        client1.login();
//        client1.send_mss("user1","test2");
//        client1.send_mss("user1","test3");
//
//        client1.login_out();
//        socket1.close();
////        Socket socket2 = new Socket(Client.host,Client.port);
////        Client client2 = new Client("user1","linux123",socket2);
////        client2.login();
////        client2.send_mss("user2","test1");
////        client2.login_out();
////        socket2.close();


        JFrame frame = new JFrame("Gadu-Gadu");
        Login login = new Login();
        frame.setContentPane(login.getPanel1());
        login.setFrame(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(500,400);
        frame.setVisible(true);


//
//
//
////        Scanner scanner = new Scanner(System.in);
////        for (int i=0;i<3;i++){
////            String clientMessage = scanner.nextLine();
////            OutputStream os = clientSocket.getOutputStream();
////            String msg = clientMessage;
////            os.write(msg.getBytes());
////        }
//
////
//        Client client = new Client("user2","linux123",socket);
//        client.login();
//        client.load_old_mss("user1");
//        socket.close();
    }





}
