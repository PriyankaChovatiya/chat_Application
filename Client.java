import java.net.*;
import java.io.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //constructor
   public Client(){
    try {
        System.out.println("Sending request to server");
        socket = new Socket("127.0.0.1",777);
        System.out.println("Connected to server");

         //read
         br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

         //Write
         out = new PrintWriter(socket.getOutputStream());

         StartReading();
         StartWriting();

    } catch (Exception e) {
        e.printStackTrace();
       
    }
   }


   public void StartReading(){
    //read

    // thread : Read and give
    Runnable r1= () ->{
        System.out.println("Reader Started....");

        try{
        while (true) {
         
                String msg = br.readLine();
          
            if(msg.equals("Exit"))
            {
                System.out.println("Server is Terminated from chat");
                break;
            }
            System.out.println("Server: " + msg);
              
            
        }
    } catch(Exception e){
        e.printStackTrace();
    }

    };

    //start the thread
    new Thread(r1).start();

}


public void StartWriting(){
    //write

    //Thread : Take the data  from the user and send to the client 
    Runnable r2 = () ->{

        System.out.println("Writer Started.....");

        try{
        while (true) {
           
                BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                String content = br1.readLine();
                out.println(content);
                out.flush();
            
        } 
    } catch(Exception e){
        e.printStackTrace();
    }

    };

     //start the thread
     new Thread(r2).start();
}
public static void main(String[] args) {
    System.out.println("Client is running...");
    new Client();
}

}
