import java.net.*;
import java.io.*;


class Server{
       
    //Variables
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;


    //Constructor
    public Server(){

        try{
            server = new ServerSocket(777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting......");
            socket = server.accept();

            //read
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Write
            out = new PrintWriter(socket.getOutputStream());

            StartReading();
            StartWriting();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    public void StartReading(){
        //read

        // thread : Read and give
        Runnable r1= () ->{
            System.out.println("Reader Started....");

            while (true) {
                try{
                    String msg = br.readLine();
              
                if(msg.equals("Exit"))
                {
                    System.out.println("Client is Terminated from chat");

                    socket.close();
                    
                    break;
                }
                System.out.println("Client: " +msg);
                  } catch(Exception e){
                    e.printStackTrace();
            }
                
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

            while (true) {
                try {

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                
            }

        };

         //start the thread
         new Thread(r2).start();

    }

    public static void main(String[] args) {
        System.out.println("Server is running...");
        new Server();

    }
}