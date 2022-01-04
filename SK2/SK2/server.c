#include<stdio.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h> 
#include <arpa/inet.h> 
#include <unistd.h>
#include <unistd.h>
#include<errno.h>
#include <stdlib.h>

char buffor[100];

int main(int argc, char *argv[]){
      char liczba1 = argv[1];
      char liczba2 = argv[2];
      struct sockaddr_in server_addr;
      int my_socket = socket(AF_INET, SOCK_STREAM, 0);

      server_addr.sin_family = AF_INET ;
      server_addr.sin_port = htons(port);
      //server_addr.sin_addr.s_addr = argv[1];
      inet_pton(AF_INET, argv[1], &(server_addr.sin_addr));
      
      connect(my_socket, (struct sockaddr*)&server_addr, sizeof(server_addr));
      while(1){
          cahr buff[2];
          buff[0]=a;
          buff[1]=b;
      
      }
      close(my_socket);
      sleep(1);
      printf("\n");

}