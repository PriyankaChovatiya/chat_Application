import java.net.*;

import javax.swing.*;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.io.*;

public class Client extends JFrame {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //Declare component 
    private JLabel heading = new JLabel("Client Area");
    private JTextArea MessageArea = new JTextArea();
    private JTextField MessageInput = new JTextField();
    private Font font = new Font("Roboto",Font.PLAIN,20);

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


        createGUI();
        handleEvent();
        StartReading();
        //  StartWriting();

    } catch (Exception e) {
        e.printStackTrace();
       
    }
   }

//GUI 
private void createGUI(){

    //gui
    this.setTitle("Client Messager(END)");
    this.setSize(600, 600);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //coding for component 
    heading.setFont(font);
    MessageArea.setFont(font);
    MessageInput.setFont(font);

        // Resize the icon
        ImageIcon originalIcon = new ImageIcon("clogo.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Set the desired width and height
        ImageIcon resizedIcon = new ImageIcon(resizedImage);  
        heading.setIcon(resizedIcon);
        heading.setHorizontalAlignment(SwingConstants.LEFT);

         //Text 
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        // heading.setBackground(Color.decode("#2F4F7F"));

     //Frame Layout 
    this.setLayout(new BorderLayout());

    //Adding component to thte frame 
    this.add(heading,BorderLayout.NORTH);
    this.add(MessageArea,BorderLayout.CENTER);
    this.add(MessageInput,BorderLayout.SOUTH);

    this.setVisible(true);

}

private void handleEvent(){
     MessageInput.addKeyListener(new KeyListener() {

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
             
        String ContentToSend = MessageInput.getText();
        MessageArea.append("Me :" + ContentToSend + "\n");
        out.println(ContentToSend);
        out.flush();
        MessageInput.setText("");
        MessageInput.requestFocus();
        }
        
     });
}

   public void StartReading(){
    //read

    // thread : Read and give
    Runnable r1= () -> {
        System.out.println("Reader Started....");

        try{
        while (true) {
         
                String msg = br.readLine();
          
            if(msg.equals("Exit"))
            {
                System.out.println("Server is Terminated from chat");
                JOptionPane.showMessageDialog(this, "Server Terminated the chat ");
                MessageInput.setEnabled(false);
                socket.close();
                break;
            }
             MessageArea.append("Server: " + msg + "\n");
        }
    } catch(Exception e){
        // e.printStackTrace();
        System.out.println("Client Read Connection Closed");
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

                if(content.equals("Exit")){
                    socket.close();
                    break;
                }


           
        } 
    } catch(Exception e){
        // e.printStackTrace();
        System.out.println("Client Write Connection closed");
            
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
