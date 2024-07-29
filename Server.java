import java.net.*;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.io.*;

class Server extends JFrame{
       
    //Variables
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

  //Declare component 
  private JLabel s_heading = new JLabel("Server Area");
  private JTextArea s_MessageArea = new JTextArea();
  private JTextField s_MessageInput = new JTextField();
  private Font font = new Font("Roboto",Font.PLAIN,20);


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

            createGUI();
            handleEvent();
            StartReading();
            // StartWriting();

        }catch(Exception e){
            e.printStackTrace();
        }
        
    }


    //GUI 
private void createGUI(){

    //gui
    this.setTitle("Server Messager(END)");
    this.setSize(600, 800);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   
    //coding for component 
    s_heading.setFont(font);
    s_MessageArea.setFont(font);
    s_MessageInput.setFont(font);

        // Resize the icon
        ImageIcon originalIcon = new ImageIcon("clogo.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Set the desired width and height
        ImageIcon resizedIcon = new ImageIcon(resizedImage);  
        s_heading.setIcon(resizedIcon);
        s_heading.setHorizontalAlignment(SwingConstants.LEFT);

         //Text 
        s_heading.setHorizontalAlignment(SwingConstants.CENTER);
        s_heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        // heading.setBackground(Color.decode("#2F4F7F"));

        //For message Aera
        s_MessageArea.setEditable(false);

     //Frame Layout 
    this.setLayout(new BorderLayout());

    //Adding component to thte frame 
    this.add(s_heading,BorderLayout.NORTH);
    JScrollPane jScrollPane = new JScrollPane(s_MessageArea);
    this.add(jScrollPane,BorderLayout.CENTER);
    this.add(s_MessageInput,BorderLayout.SOUTH);

    this.setVisible(true);

}










private void handleEvent(){
     s_MessageInput.addKeyListener(new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub
          
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub
        //    System.out.println("Key released "+e.getKeyCode());
        if(e.getKeyCode() ==10)     {
        String ContentToSend = s_MessageInput.getText();
        s_MessageArea.append("Me :" + ContentToSend + "\n");
        out.println(ContentToSend);
        out.flush();
        s_MessageInput.setText("");
        s_MessageInput.requestFocus();
        }
       }
     });
}

    public void StartReading(){
        //read

        // thread : Read and give
        Runnable r1= () ->{
            System.out.println("Reader Started....");

            try{
            while (true) {

                    String msg = br.readLine();
              
                if(msg.equals("exit"))
                {
                    System.out.println("Client is Terminated from chat");
                    JOptionPane.showMessageDialog(this, "Client Terminated the chat ");
                    s_MessageInput.setEnabled(false);
                    socket.close();
                    break;
                }
                s_MessageArea.append("Server: " + msg + "\n");
                  
            }

        } catch(Exception e){
            // e.printStackTrace();
            System.out.println("Server Read Connection closed");
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
            while (true && !socket.isClosed()) {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();

                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                
            }
        }catch(Exception e){
            // e.printStackTrace();
            System.out.println("Server Write Connection closed");
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