import java.net.*;
import java.io.*;

public class UDPServer2{

	public static void main(String args[]){
		DatagramSocket aSocket = null;

		try{
			aSocket = new DatagramSocket(6787);
			byte[] buffer = new byte[1000];

			while(true){
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);

				ProcessBuilder processBuilder = new ProcessBuilder();
				String command = new String(request.getData()).trim();
				System.out.println("Your command: " + command);
				System.out.println();
				processBuilder.command("bash", "-c", command);
				Process process = processBuilder.start();
				StringBuilder output = new StringBuilder();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(process.getInputStream()));
				String line;
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}

				int exitVal = process.waitFor();
				if (exitVal == 0) {
					System.out.println(output);

					String outputString = output.toString();
					byte[] result = outputString.getBytes();

					DatagramPacket reply = new DatagramPacket(result, result.length, request.getAddress(), request.getPort());
					aSocket.send(reply);

					System.exit(0);
				}
			}

		} catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		} catch (IOException e) {System.out.println("IO: " + e.getMessage());
	} catch (InterruptedException e) {System.out.println("Interrupted: " + e.getMessage());
		} finally {if (aSocket != null) aSocket.close();}
	}
}
