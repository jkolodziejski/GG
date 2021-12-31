package com.company;


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
    public static String host="localhost";
    public static Integer port=1253;


    public Client(String login, String password, Socket clientSocket) throws Exception {
        if(login.equals("") || password.equals("")) {
            throw new Exception("Didn't creat user");
        }
        this.friends = new ArrayList<String>();
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

    public void getnewClientSocket() throws IOException {
        this.clientSocket.close();
        Socket clientSocket_new = new Socket(Client.host,Client.port);
        this.clientSocket=clientSocket_new;
    }

    public List<String> getFriends() {
        return friends;
    }

    public String getFriend(int index){
        return friends.get(index);
    }


    public String login() throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        writer.println("l"+"\t"+login+"\t"+password+"\t");


        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();

        System.out.printf(serverMessage+"\n");
        if (!serverMessage.equals("Wrong login") && !serverMessage.equals("Wrong password")) {
            this.friends = new ArrayList<>(Arrays.asList(serverMessage.split("\t")));
        }

        //System.out.printf(serverMessage);


        return serverMessage;
    }

    public String register() throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

        writer.println("r"+"\t"+login+"\t"+password+"\t");

        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = reader.readLine();
        System.out.println(serverMessage);


        return serverMessage;
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

    public void login_out() throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        writer.println("q"+"\t");

    }


    public String  load_old_mss(String from_who) throws IOException {
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        writer.println("c"+"\t"+from_who+"\t");
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String serverMessage = "";
        String full_mss="";
        serverMessage= reader.readLine();
        while(!serverMessage.equals("finish")) {



            full_mss+=serverMessage.substring(2,serverMessage.length());
            full_mss+="\n";
            serverMessage= reader.readLine();

        }
        return full_mss;

    }





}