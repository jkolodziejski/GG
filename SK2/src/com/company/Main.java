package com.company;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {


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
//
////        Scanner scanner = new Scanner(System.in);
////        for (int i=0;i<3;i++){
////            String clientMessage = scanner.nextLine();
////            OutputStream os = clientSocket.getOutputStream();
////            String msg = clientMessage;
////            os.write(msg.getBytes());
////        }
//        //client.register();
//        client.login();
//
//        client.send_mss("user2","test1 na xd");
//        //client.receive_mss();
//        clientSocket.close();
    }





}
