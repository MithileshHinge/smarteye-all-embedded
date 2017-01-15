import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NotificationThread extends Thread {
	
	static int port_note = 6667;
	static ServerSocket serverSocket_note;
	static Socket socket_note;
	public static OutputStream out_note;
	
	public static boolean notify = false;
	public static boolean warned1 = false;
	public static boolean warned2 = false;
	
	public static boolean warn_level1 = false;
	public static boolean warn_level2 = false;
	
	public NotificationThread(){
		try {
			serverSocket_note = new ServerSocket(port_note);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(String.format("problem2"));
		}
		
	}
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(0, 10000);

			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (notify) {
				System.out.println("Face is detected......................");
				try {
					socket_note = serverSocket_note.accept();
					out_note = socket_note.getOutputStream();
					out_note.write(1);
					out_note.flush();
					socket_note.close();
					// System.out.println(String.format("connected"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob2"));
					e.printStackTrace();
				}
				notify = false;
			}
			if (!warned1 && warn_level1) {
				System.out.println("alert level 1...................");
				try {
					socket_note = serverSocket_note.accept();
					out_note = socket_note.getOutputStream();
					out_note.write(2);
					out_note.flush();
					socket_note.close();
					// System.out.println(String.format("connected"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob3"));
					e.printStackTrace();
				}
				warn_level1 = false;
				warned1 = true;
			}
			if (!warned2 && warn_level2) {
				System.out.println("alert level 2...................");
				try {
					socket_note = serverSocket_note.accept();
					out_note = socket_note.getOutputStream();
					out_note.write(3);
					out_note.flush();
					socket_note.close();
					// System.out.println(String.format("connected"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(String.format("connection_prob4"));
					e.printStackTrace();
				}
				warn_level2 = false;
				warned2 = true;
			}
		}
	}
}
