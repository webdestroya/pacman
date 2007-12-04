<<<<<<< .mine
package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;


import java.io.*;
import java.net.*;
/**
 * Client handles the input and output of the server.
 * @author Networking Team
 */
=======
package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;


import java.io.*;
import java.net.*;
/**
 * Client handles the input and output of the server. 
 * @author Networking Team
 */
public class Client// extends Thread
{
	private static final long serialVersionUID = 1L;
	public static InetAddress address;
	
	/**
	 * 
	 * @param host name/ip of the host(String)
	/**
	 * 
	 * @param host
	 */
	public Client(String host)
	{
		Client.setHost(host);
	}

	public static void setHost(String host)
	{
		try
		{
			Client.address = InetAddress.getByName(host);
		}
		catch(Exception e)
		{

		}
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
