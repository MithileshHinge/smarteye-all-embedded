import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SendingFrame extends Thread{
	static int port = 6666;
	static ServerSocket serverSocket;
	static Socket socket;
	public static boolean flag = false; 
	  public void run() {
		 
          while (true) {
        	  
        	  //OutputStream out = null;
          	// completing the connection 
				try {
					//System.out.println(String.format("here"));
					socket = serverSocket.accept();
					Main.out= socket.getOutputStream();
					flag = true;
					//System.out.println(String.format("connected"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob"));
					e.printStackTrace();
				}
			
			// sending 1 processed frame 
				
			// sending 2 unprocessed frames

        
          }

	  }
}
