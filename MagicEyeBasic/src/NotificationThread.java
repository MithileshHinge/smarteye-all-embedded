import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class NotificationThread extends Thread {
	
	static int port_note = 6667;
	static ServerSocket serverSocket_note;
	static Socket socket_note;
	public static OutputStream out_note;
	
	public static boolean notify = false;
	public static boolean warned1 = false;
	public static boolean warned2 = false;
	public static boolean sendmail = false;
	
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
			if(sendmail)
			{
				  sendmail = false;
				  // Recipient's email ID needs to be mentioned.
			      String to = "isha.sagote@gmail.com";
			      // Sender's email ID needs to be mentioned
			      String from = "missblahboo@gmail.com";
			      
			      final String username = "missblahboo@gmail.com";//change accordingly
			      final String password = "blahblahbooboo";//change accordingly
			      String host = "74.125.206.108";

			      Properties props = new Properties();
			      props.put("mail.smtp.auth", "true");
			      props.put("mail.smtp.starttls.enable", "true");
			      props.put("mail.smtp.host", host);
			      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			      props.setProperty("mail.smtp.socketFactory.fallback", "false");
			      props.setProperty("mail.smtp.port", "465");
			      props.setProperty("mail.smtp.socketFactory.port", "465");	      
			      // Get the Session object.
			      Session session = Session.getInstance(props,
			         new javax.mail.Authenticator() {
			            protected PasswordAuthentication getPasswordAuthentication() {
			               return new PasswordAuthentication(username, password);
			            }
			         });

				try {
			         // Create a default MimeMessage object.
			         Message message = new MimeMessage(session);

			         // Set From: header field of the header.
			         message.setFrom(new InternetAddress(from));

			         // Set To: header field of the header.
			         message.setRecipients(Message.RecipientType.TO,
			            InternetAddress.parse(to));

			         // Set Subject: header field
			         message.setSubject("Magic Eye Video");

			         // Create the message part
			         BodyPart messageBodyPart = new MimeBodyPart();

			         // Now set the actual message
			         messageBodyPart.setText("Hello!" + '\n' + "This is a video recorded by your Magic Eye System on " + Main.store_file_name + "." + '\n' + "Please take a look.");

			         // Create a multipart message
			         Multipart multipart = new MimeMultipart();

			         // Set text message part
			         multipart.addBodyPart(messageBodyPart);

			         // Part two is attachment
			         messageBodyPart = new MimeBodyPart();
			         String filename = Main.store_name;
			         DataSource source = new FileDataSource(filename);
			         messageBodyPart.setDataHandler(new DataHandler(source));
			         messageBodyPart.setFileName(Main.store_file_name +".mp4");
			         multipart.addBodyPart(messageBodyPart);

			         // Send the complete message parts
			         message.setContent(multipart);
			         System.out.println("reached jst b4 sending");

			         // Send message
			         Transport.send(message);
			         
			         System.out.println("Sent message successfully....");
			         
			      } catch (MessagingException e) {
			    	 System.out.println("Sending failed!!!");
			         throw new RuntimeException(e);
			      }
			}

		}
	}
}
