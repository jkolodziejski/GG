package com.company;

import java.io.IOException;
import java.net.Socket;

public class user2 {
    public class Main {

        public static void main(String[] args) throws IOException {
            Socket clientSocket = new Socket("localhost", 1235);

            Client client = new Client("user2","linux123",clientSocket);

//        Scanner scanner = new Scanner(System.in);
//        for (int i=0;i<3;i++){
//            String clientMessage = scanner.nextLine();
//            OutputStream os = clientSocket.getOutputStream();
//            String msg = clientMessage;
//            os.write(msg.getBytes());
//        }
            //client.register();
            client.login();
            client.add_friend("user1");
            client.add_friend("user3");
            //client.send_mss("user2","test1 na xd");
            client.receive_mss();
            clientSocket.close();
        }




    }
}
