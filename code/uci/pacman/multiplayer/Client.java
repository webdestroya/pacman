package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;


import java.io.*;
import java.net.*;
/**
 * Client handles the input and output of the server. It is responsible for starting the ClientInputListener thread.
 * @author Networking Team
 */
public class Client// extends Thread
{
	private static final long serialVersionUID = 1L;
	public static String hostname;
	
	/**
	 * 
	 * @param host name/ip of the host(String)
	 */
	public Client(String host)
	{
		hostname = host;
	}
	
	/**
	 * 
	 * @param ghost which ghost you are sending
	 * @param dir stuff
	 */
	public static void send(String ghost, String dir)
	{
		try
		{
			DatagramSocket socket = new DatagramSocket();

			byte[] buf = new byte[256];
		
			String sending = ghost + "|" + dir;
			buf = sending.getBytes();
		
			InetAddress address = InetAddress.getByName(hostname);
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
		
			socket.send(packet);

			// get response

			packet = new DatagramPacket(buf, buf.length);
			socket.receive(packet);

			// display response
			String received = new String(packet.getData(), 0, packet.getLength());
			//System.out.println("Quote of the Moment: " + received);
			socket.close();
		}
		catch(Exception e)
		{
		
		}
	}
}
