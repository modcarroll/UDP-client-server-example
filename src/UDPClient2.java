import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient2 {

	public static void main(String args1[]){

			Scanner scan = new Scanner(System.in);
			System.out.println("Enter your command:");
			String message = scan.nextLine();
			System.out.println();

			DatagramSocket aSocket = null;

			String args[] = new String[2];
			args[0] = message;
			args[1] = "123.12.123.123"; // IP address for UDPServer

			try {
				aSocket = new DatagramSocket();
				byte [] m = args[0].getBytes();
				InetAddress aHost = InetAddress.getByName(args[1]);
				int serverPort = 6787;

				DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);

				aSocket.send(request);
				byte[] buffer = new byte[1024];

				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(reply);

				String response = new String(reply.getData());

				System.out.println("Reply from" + reply.getSocketAddress().toString() + ": ");
				System.out.println(response);

			} catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			} catch (IOException e){System.out.println("IO: " + e.getMessage());
			} finally { if(aSocket != null) aSocket.close();}
	}
}
