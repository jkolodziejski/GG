package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main_pulpit {
    private JList list_friends;
    private JPanel main_pulpit_panel;
    private JTextField add_friend_filed;
    private JButton add_friend;
    private JLabel nick_name;
    private JTextArea textArea1;
    private JTextField text_send_mss;
    private JButton sendButton;
    private JButton newMessageButton;
    private JButton deleteFriendButton;
    private Client client;
    private String to_who;
    private Thread thread;
    private List<String> updated_list_friends;

    public static void set_list(List<String> list_friends_copy, JList list_friends){
        DefaultListModel listModel=new DefaultListModel();
        List<String> friends = list_friends_copy;
        for (int i=0;i<friends.size();i++){
            listModel.addElement(friends.get(i));
        }
        list_friends.setModel(listModel);
    }







    public Main_pulpit(Client client) throws IOException{
        this.client = client;
        this.updated_list_friends=new ArrayList<>(client.getFriends());
        Mss_listener mss_listener = new Mss_listener(this.client, list_friends,this.updated_list_friends);
        this.thread = new Thread(mss_listener);
        thread.start();
        nick_name.setText(client.getLogin());
        set_list(updated_list_friends,list_friends);
        add_friend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.add_friend(add_friend_filed.getText());
                    updated_list_friends.add(add_friend_filed.getText());
                    set_list(updated_list_friends, list_friends);
                    add_friend_filed.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    client.send_mss(to_who,text_send_mss.getText());
                    text_send_mss.setText("");
                    textArea1.setText(client.load_old_mss(to_who));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });
        newMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    int index = list_friends.getSelectedIndex();
                    if(index==-1){
                        JOptionPane.showMessageDialog(new JFrame(), "Nie wybrano uzytkownika z listy znajomych", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {

                        to_who=client.getFriend(index);
                        System.out.println("Do kogo"+to_who);
                        updated_list_friends.set(index, to_who);
                        set_list(updated_list_friends,list_friends);
                        textArea1.setText(client.load_old_mss(to_who));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        deleteFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = list_friends.getSelectedIndex();
                to_who=client.getFriend(index);
                try {

                    client.deleted_friend(to_who);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                updated_list_friends.remove(index);
                set_list(updated_list_friends,list_friends);
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
