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
    private List<String> updated_list_friends;

    public Mss_listener(Client client, JList list_friends,List<String> updated_list_friends) {
        this.updated_list_friends=updated_list_friends;
        this.client = client;
        this.list_friends = list_friends;
    }

    @Override
    public void run() {
        while (true) {

            try {
                message = client.receive_mss();

                if (!message.equals("")) {
                    if(message.startsWith("f\t")){
                        message=message.substring(2);

                        client.setFriends (new ArrayList<>(Arrays.asList(message.split("\t"))));
                        Main_pulpit.set_list(client.getFriends(),list_friends);

                    }
                    else {
                        message = message.substring(2);
                        message = message.substring(0, message.indexOf("\t"));
                        System.out.println(message);
                        for (int i = 0; i < updated_list_friends.size(); i++) {
                            if (updated_list_friends.get(i).equals(message)) {
                                updated_list_friends.set(i, message + "*");
                                Main_pulpit.set_list(updated_list_friends, list_friends);
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

