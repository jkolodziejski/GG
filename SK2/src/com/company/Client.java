package com.company;


import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    private String login;
    private String password;
    private Socket clientSocket;
    private List<String> friends ;

    public Client(String login, String password, Socket clientSocket){
        this.login = login;
        this.password = password;
        this.clientSocket = clientSocket;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login() throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        writer.println("l"+"\t"+login+"\t"+password+"\t");


        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();

        System.out.printf(serverMessage+"\n");
        this.friends= new ArrayList<>(Arrays.asList(serverMessage.split("\t")));
        //serverMessage = reader.readLine();

        //System.out.printf(serverMessage);


        return true;
    }

    public boolean register() throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

        writer.println("r"+"\t"+login+"\t"+password+"\t");

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();
        System.out.println(serverMessage);


        return true;
    }

    public boolean add_friend(String login_add) throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);


        writer.println("f"+"\t"+login_add+"\t");

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();
        System.out.println(serverMessage);
        if(serverMessage.equals("Added friend")){
            friends.add(login_add);

        }

        return true;
    }

    public boolean deleted_friend(String login_add) throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);


        writer.println("d"+"\t"+login_add+"\t");

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();
        System.out.println(serverMessage);
        if(serverMessage.equals("Deleted friend")){
            friends.remove(login_add);

        }
        System.out.println(friends);

        return true;
    }

    public boolean send_mss(String to_who_mss, String mss) throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);


        writer.println("m"+"\t"+to_who_mss+"\t"+mss+"\t");

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();
        if(serverMessage.equals("Message sent")){
            System.out.println(serverMessage);

        }

        return true;
    }

    public boolean receive_mss() throws IOException {


            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String serverMessage = reader.readLine();
            System.out.println(serverMessage);
            return true;
    }






}