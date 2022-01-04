package com.company;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mss_listener implements Runnable {
    private Client client;
    private JList list_friends;
    private String message = "";

    public Mss_listener(Client client, JList list_friends) {
        this.client = client;
        this.list_friends = list_friends;
    }

    @Override
    public void run() {
        while (true) {

            try {
                message = client.receive_mss();

                if (!message.equals("")) {
                    System.out.println(message.substring(0,2));
                    if(message.startsWith("f\t")){
                        message=message.substring(2);

                        client.setFriends (new ArrayList<>(Arrays.asList(message.split("\t"))));
                        Main_pulpit.set_list(client.getFriends(),list_friends);

                    }
                    else {
                        message = message.substring(2);
                        message = message.substring(0, message.indexOf("\t"));
                        System.out.println(message);
                        List<String> list_friends_copy = new ArrayList<>(client.getFriends());
                        for (int i = 0; i < list_friends_copy.size(); i++) {
                            if (list_friends_copy.get(i).equals(message)) {
                                list_friends_copy.set(i, message + "*");
                                Main_pulpit.set_list(list_friends_copy, list_friends);
                            }
                        }
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

