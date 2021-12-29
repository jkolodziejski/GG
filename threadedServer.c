#include <sys/types.h>
#include <sys/wait.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netdb.h>
#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <pthread.h>

#define SERVER_PORT 1238
#define QUEUE_SIZE 5
#define BUF_SIZE 1024

#define FIRST_READ 250
#define NUMER_OF_USERS 100
#define LOGIN_SIZE 100
#define PASS_SIZE 100


typedef struct 
{
    int connection_socket_descriptor;
    char login[NUMER_OF_USERS];
    char password[LOGIN_SIZE];
    int friends[NUMER_OF_USERS];
    
}user;


user list_user[NUMER_OF_USERS];

int users_registered=0;


//struktura zawierająca dane, które zostaną przekazane do wątku
struct thread_data_t
{
    int index_user;
};

// char * split_tab(char mss[],int size, int position){
//     char logging_login[sizeof(mss)]="";
//     int tmp_position=0;
//     int start=0;
//     for(int i=0;i<size+1;i++){
//         if(mss[i]=='\t'){
//             tmp_position++;
//         }
//         else if (position == tmp_position)
//         {
//             start=i;
//             break;
//         }
//     }
//     for(int i=start;i<size && mss[i]!='\t';i++){
//         logging_login[i-start]=mss[i];
        
//     }
   
//     return logging_login;
// }

void send_friends(int index, char * str){
    
    for (int i=0; i<NUMER_OF_USERS;i++){
        if(list_user[index].friends[i]==1){
            strcat(str,list_user[i].login);
            strcat(str,"\t");
        }

    }
    int size_list = strlen(str);
    str=realloc(str,size_list+1);
    strcat(str,"\n");
}

void add_friends(int index, char *login_friend){
    int user_found;
    for (int i=0;i<users_registered;i++){
        user_found = strcmp(list_user[i].login,login_friend);
        if(user_found== 0 && index != i && list_user[index].friends[i]==0){
             
            list_user[index].friends[i]=1;
            char buff[]="Added friend\n";
            int readOutput = write(list_user[index].connection_socket_descriptor ,buff, sizeof(buff));
            printf("%s added friend with login : %s\n",list_user[index].login,login_friend);
            return;
        }
            

    }
    //pomyslec/ zmienic opis
    char buff[]="User doesn't exist\n";  
    int readOutput = write(list_user[index].connection_socket_descriptor ,buff, sizeof(buff));

}

void deleted_friends(int index, char *login_friend){
    int user_found;
    for (int i=0;i<users_registered;i++){
        user_found = strcmp(list_user[i].login,login_friend);
        if(user_found == 0 && index != i && list_user[index].friends[i]==1){
             
            list_user[index].friends[i]=0;
            char buff[]="Deleted friend\n";
            int readOutput = write(list_user[index].connection_socket_descriptor ,buff, sizeof(buff));
            printf("%s Deleted friend with login : %s\n",list_user[index].login,login_friend);
            return;
        }
            

    }
    //pomyslec/ zmienic opis
    char buff[]="User doesn't exist\n";  
    int readOutput = write(list_user[index].connection_socket_descriptor ,buff, sizeof(buff));

}

void receive_mss(int index, char *login_friend,char mss[]){
    int user_found;
    char send_mss[4+LOGIN_SIZE+BUF_SIZE]="m\t";
    for (int i=0;i<users_registered;i++){
        user_found = strcmp(list_user[i].login,login_friend);
        if(user_found == 0 && index != i && list_user[index].friends[i]==1){
            strcat(send_mss,list_user[index].login);
            strcat(send_mss,"\t");
            strcat(send_mss,mss);
            strcat(send_mss,"\n");
            int status = write(list_user[i].connection_socket_descriptor,send_mss,sizeof(send_mss));
            char buff[]="Message sent\n";
            int readOutput = write(list_user[index].connection_socket_descriptor ,buff, sizeof(buff));

            printf("%s sent a message to : %s\n",list_user[index].login,login_friend);
            return;
        }
            

    }
    //pomyslec/ zmienic opis
    char buff[]="User doesn't exist\n";  
    int readOutput = write(list_user[index].connection_socket_descriptor ,buff, sizeof(buff));

    
}


int login(int connection_socket_descriptor, char buff[FIRST_READ]){

    int index_user;
    int user_found=-1;
    int size_of_login=0;

    char logging_login[LOGIN_SIZE]="";
    char logging_password[PASS_SIZE]="";
    for(int i=2;i<LOGIN_SIZE && buff[i]!='\t';i++){
        logging_login[i-2]=buff[i];
        size_of_login=i;
    }
    for (int i=0;i<NUMER_OF_USERS;i++){
        user_found = strcmp(list_user[i].login,logging_login);
        if(user_found==0){
            index_user=i;
            break;
        }

    }
    if(user_found!=0){
        char buff[]="Wrong login\n";
        int readOutput = write(connection_socket_descriptor, buff, sizeof(buff));
        close(connection_socket_descriptor);
        return -1;
    }
    else{
        for(int i=size_of_login+2;i<LOGIN_SIZE && buff[i]!='\t';i++){
        logging_password[i-size_of_login-2]=buff[i];
        
        }
        int pass_correct = strcmp(logging_password,list_user[index_user].password);
        if(pass_correct!=0){
            char buff[]="Wrong password\n";
            int readOutput = write(connection_socket_descriptor, buff, sizeof(buff));
            close(connection_socket_descriptor);
            return -1;

        }
        else{
            char *str = malloc(NUMER_OF_USERS*(LOGIN_SIZE+2)*sizeof(char));
            send_friends(index_user,str);
            int status_send_list = write(connection_socket_descriptor,str,strlen(str));

            //char buff[]="Login successful\n";
            //int readOutput = write(connection_socket_descriptor, buff, sizeof(buff));
            
            list_user[index_user].connection_socket_descriptor=connection_socket_descriptor;
            printf("Login successful -- > %s\n", list_user[index_user].login);
            return index_user;
          
            

        }

    }

}



void register_new(int connection_socket_descriptor, char buff[FIRST_READ]){

    int index_user;
    int user_found=-1;
    int size_of_login=0;

    char logging_login[LOGIN_SIZE]="";
    char logging_password[PASS_SIZE]="";
    for(int i=2;i<LOGIN_SIZE && buff[i]!='\t';i++){
        logging_login[i-2]=buff[i];
        size_of_login=i;
    }
    for (int i=0;i<NUMER_OF_USERS;i++){
        user_found = strcmp(list_user[i].login,logging_login);
        if(user_found==0){
            index_user=i;
            break;
        }

    }
    if(user_found==0){
        char buff[]="User already exist\n";
        int readOutput = write(connection_socket_descriptor, buff, sizeof(buff));
        close(connection_socket_descriptor);
    }
    else{
        for(int i=size_of_login+2;i<LOGIN_SIZE && buff[i]!='\t';i++){
        logging_password[i-size_of_login-2]=buff[i];
        }
        strcpy(list_user[users_registered].login,logging_login);
        strcpy(list_user[users_registered].password,logging_password);
        for (int i=0;i<NUMER_OF_USERS;i++){
            list_user[users_registered].friends[i]=0;
        }
        users_registered+=1;
        char buff[]="User registered\n";
        int readOutput = write(connection_socket_descriptor, buff, sizeof(buff));
        printf("User registered -- > %s\n",logging_login);


    }

        

}





void wait_for_instruction(struct thread_data_t *th_data){
    while(1){
        char buff[4+LOGIN_SIZE+BUF_SIZE]="";
        int readOutput = read(list_user[th_data->index_user].connection_socket_descriptor, buff, sizeof(buff));
        if (readOutput<0){
            puts("Blad z odczytaniem danych klient!");

        }
        int size_of_login=0;
        int size_of_mss =0;
        char logging_login[LOGIN_SIZE]="";
        char mss[BUF_SIZE]="";
        for(int i=2;i<LOGIN_SIZE+2 && buff[i]!='\t';i++){
            logging_login[i-2]=buff[i];
            size_of_login=i;
        }
        
        
        if(buff[0]=='f'){

            add_friends(th_data->index_user,logging_login);
            
        }
        else if(buff[0]=='d'){
            deleted_friends(th_data->index_user,logging_login);
        }
        else if(buff[0]=='m'){
            for(int i=2+size_of_login;i<BUF_SIZE+size_of_login+2 && buff[i]!='\t';i++){
            mss[i-2-size_of_login]=buff[i];
            size_of_mss=i;
            }
            receive_mss(th_data->index_user,logging_login,mss);

        }

        
    }
}

//funkcja opisującą zachowanie wątku - musi przyjmować argument typu (void *) i zwracać (void *)
void *ThreadBehavior(void *t_data)
{
    pthread_detach(pthread_self());
    struct thread_data_t *th_data = (struct thread_data_t*)t_data;

    //dostęp do pól struktury: (*th_data).pole
    //TODO (przy zadaniu 1) klawiatura -> wysyłanie albo odbieranie -> wyświetlanie
    wait_for_instruction(th_data);

    pthread_exit(NULL);
}

void created_thread(int index){
    //wynik funkcji tworzącej wątek
    int create_result = 0;

    //uchwyt na wątek
    pthread_t thread1;

    //dane, które zostaną przekazane do wątku
    struct thread_data_t *t_data = malloc(sizeof(struct thread_data_t));
    t_data->index_user = index;
    //TODO dynamiczne utworzenie instancji struktury thread_data_t o nazwie t_data (+ w odpowiednim miejscu zwolnienie pamięci)
    //TODO wypełnienie pól struktury

    create_result = pthread_create(&thread1, NULL, ThreadBehavior, (void *)t_data);
    if (create_result){
        printf("Błąd przy próbie utworzenia wątku, kod błędu: %d\n", create_result);
        exit(-1);
    }
}


//funkcja obsługująca połączenie z nowym klientem
void handleConnection(int connection_socket_descriptor) {
    // status_login = -1 (error) , status_login = index (success) 
    int status_login;
    char buff[FIRST_READ];
    int readOutput = read(connection_socket_descriptor, buff, sizeof(buff));
    //printf("%c--->",buff[0]);
    if (buff[0]=='l'){
        status_login = login(connection_socket_descriptor,buff);
        if(status_login!=-1){
            created_thread(status_login);
        }
    }
    else if (buff[0]=='r')
    {
        register_new(connection_socket_descriptor,buff);
    }

}

int main(int argc, char* argv[])
{
   int server_socket_descriptor;
   int connection_socket_descriptor;
   int bind_result;
   int listen_result;
   char reuse_addr_val = 1;
   struct sockaddr_in server_address;


   //do testowania      
   int tablica[NUMER_OF_USERS];
    for (int i=0;i<NUMER_OF_USERS;i++){
        list_user[0].friends[i]=0;
        list_user[1].friends[i]=0;
        list_user[2].friends[i]=0;
    }
    // printf("Login : ");
    // scanf("%s",&list_user[0].login);
    // printf("Password : ");
    // scanf("%s",&list_user[0].password);
    strcpy(list_user[0].login,"user1");
    strcpy(list_user[0].password,"linux123");  

    strcpy(list_user[1].login,"user2");
    strcpy(list_user[1].password,"linux123"); 
    strcpy(list_user[2].login,"user3");
    strcpy(list_user[2].password,"linux123");     
    users_registered+=3;
    
   


   //inicjalizacja gniazda serwera
   
   memset(&server_address, 0, sizeof(struct sockaddr));
   server_address.sin_family = AF_INET;
   server_address.sin_addr.s_addr = htonl(INADDR_ANY);
   server_address.sin_port = htons(SERVER_PORT);

   server_socket_descriptor = socket(AF_INET, SOCK_STREAM, 0);
   if (server_socket_descriptor < 0)
   {
       fprintf(stderr, "%s: Błąd przy próbie utworzenia gniazda..\n", argv[0]);
       exit(1);
   }
   setsockopt(server_socket_descriptor, SOL_SOCKET, SO_REUSEADDR, (char*)&reuse_addr_val, sizeof(reuse_addr_val));

   bind_result = bind(server_socket_descriptor, (struct sockaddr*)&server_address, sizeof(struct sockaddr));
   if (bind_result < 0)
   {
       fprintf(stderr, "%s: Błąd przy próbie dowiązania adresu IP i numeru portu do gniazda.\n", argv[0]);
       exit(1);
   }

   listen_result = listen(server_socket_descriptor, QUEUE_SIZE);
   if (listen_result < 0) {
       fprintf(stderr, "%s: Błąd przy próbie ustawienia wielkości kolejki.\n", argv[0]);
       exit(1);
   }

   while(1)
   {
       connection_socket_descriptor = accept(server_socket_descriptor, NULL, NULL);
       if (connection_socket_descriptor < 0)
       {
           fprintf(stderr, "%s: Błąd przy próbie utworzenia gniazda dla połączenia.\n", argv[0]);
           exit(1);
       }

       handleConnection(connection_socket_descriptor);
   }

   close(server_socket_descriptor);
   return(0);
}
