package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Main_pulpit {
    private JList list_friends;
    private JPanel main_pulpit_panel;
    private JTextField add_friend_filed;
    private JButton add_friend;
    private Client client;

    public void set_list(){
        DefaultListModel listModel=new DefaultListModel();
        List<String> friends = client.getFriends();
        for (int i=0;i<friends.size();i++){
            listModel.addElement(friends.get(i));
        }
        list_friends.setModel(listModel);
    }



    public Main_pulpit(Client client) throws IOException {
        this.client = client;
        client.add_friend("user1");
        client.add_friend("user2");
        set_list();
        add_friend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.add_friend(add_friend_filed.getText());
                    set_list();
                    add_friend_filed.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JPanel getMain_pulpit_panel() {
        return main_pulpit_panel;
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
