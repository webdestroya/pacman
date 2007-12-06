
package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;
import code.uci.pacman.controllers.*;
import java.io.*;
import java.net.*;


/**
 * Client handles the input and output of the server. 
 * @author Networking Team
 */
public class Client extends Thread
{
	private static final long serialVersionUID = 1L;
	private static InetAddress address;
	protected MulticastSocket socket = null;
	private static boolean moreQuotes = true;
	private static boolean isPlaying = false;

	// This tells the client what type he is
	private static GhostType ghostType = GhostType.BLINKY;

	/**
	 * Starts a Client
	 * @param host IP address or host name
	 */
	public Client()
	{
		try
		{
			socket = new MulticastSocket(4446);
		}
		catch(Exception e)
		{
			// cant make a socket?
		}
	}
	/**
	 * Changes the host
	 * @param host IP Address or Host Name
	 */
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

	public static GhostType getGhostType()
	{
		return ghostType;
	}

	/**
	 * Sends a packet to the Server;
	 * @param type the packet type
	 */
	public static void send(PType type)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( type.ordinal() ).byteValue();	
		buf[1] = new Integer( ghostType.ordinal() ).byteValue();
		sendData(buf);
	}

	

	/**
	 * Sends a packet to the Server;
	 * @param dir the direction the ghost is moving
	 */
	public static void send(Direction dir)
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( PType.GMOVE.ordinal() ).byteValue();	
		buf[1] = new Integer( dir.ordinal() ).byteValue();
		buf[2] = new Integer( ghostType.ordinal() ).byteValue();

		sendData(buf);
	}
	
	private void joinGame()
	{
		byte[] buf = new byte[4];
		buf[0] = new Integer( PType.JOIN.ordinal() ).byteValue();	
		buf[1] = new Integer( Client.ghostType.ordinal() ).byteValue();
		Client.sendData(buf);
	}
	
	
	// send the raw packet to the server
	private static void sendData(byte[] buf)
	{
		try
		{
			DatagramSocket socksend = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket( buf, buf.length, address, 4445 );
			socksend.send(packet);
			socksend.close();
		}
		catch(Exception e)
		{
			// TODO: error? the server is not responding
		}
	}


	private String capitalize(String s)
	{
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}



	/**
	 * This is the client listener, that listens for updates
	 */
	public void run()
	{
		// should be while game is not over
		try
		{
			InetAddress group = InetAddress.getByName("230.0.0.1");
			socket.joinGroup(group);

			while (moreQuotes) 
			{

				byte[] buf = new byte[4];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				buf = packet.getData();
				
				// update the server address
				Client.address = packet.getAddress();

				// get the packet type and data
				int packetType = buf[0] & 0x000000FF;
				int data1 = buf[1] & 0x000000FF;
				int data2 = buf[2] & 0x000000FF;
				int data3 = buf[3] & 0x000000FF;
				
				//System.out.print("PACKET("+packetType+","+data1+","+data2+","+data3+"): ");
				
				
				// listen for open slots, but only if we arent playing
				if( PType.SPOTFREE.ordinal() == packetType && !Client.isPlaying )
				{
					// tells the player that a spot is open on the server
					switch(data1)
					{
						case 0://blinky
							Client.ghostType = GhostType.BLINKY;
							break;
						case 1://clyde
							Client.ghostType = GhostType.CLYDE;
							break;
						case 2://inky
							Client.ghostType = GhostType.INKY;
							break;
						case 3://p
							Client.ghostType = GhostType.PINKY;
							break;
					}
					Client.isPlaying = true;
					//System.out.println("SPOTFREE GhostType Set: " + Client.ghostType.name() );
				
					// notify the server we are joinging
					joinGame();

					// setup the player or something, go hooray?
					GameController.getInstance().startGame();
					//GameController.getInstance().getPacInstance().showScene(ScreenMode.GAME);
					GameController.getInstance().getPacInstance().showGameScreen();
					
					// set the ghosts direction
					GameState.getInstance().getGhosts().getObjectAt(capitalize(Client.ghostType.name())).setDirection(Direction.UP);
				
				
				}
				else if( PType.GMOVE.ordinal() == packetType )
				{
					// another ghost moved. (We may or may not use this to also update the current player as consistency)
					GhostType gtype = GhostType.BLINKY;
					Direction dir = Direction.UP;

					// get direction
					switch(data1)
					{
						case 0://up
							dir = Direction.UP;
							break;
						case 1://down
							dir = Direction.DOWN;
							break;
						case 2://left
							dir = Direction.LEFT;
							break;
						case 3://right
							dir = Direction.RIGHT;
							break;
					}

					// get the ghost
					switch(data2)
					{
						case 0://blinky
							gtype = GhostType.BLINKY;
							break;
						
						case 1://clyde
							gtype = GhostType.CLYDE;
							break;

						case 2://inky
							gtype = GhostType.INKY;
							break;

						case 3://pinky
							gtype = GhostType.PINKY;
							break;
					}

					// move the ghost
					GameState.getInstance().getGhosts().getObjectAt( capitalize(gtype.name()) ).setDirection(dir);

				}
				else if( PType.PMOVE.ordinal() == packetType )
				{
					// pacman made a move
					
					Direction dir = Direction.UP;
					// what direction did he move?
					switch(data1)
					{
						case 0://up
							dir = Direction.UP;
							break;
						case 1://down
							dir = Direction.DOWN;
							break;
						case 2://left
							dir = Direction.LEFT;
							break;
						case 3://right
							dir = Direction.RIGHT;
							break;
					}

					// set the direction
					GameController.getInstance().setPacManDirection(dir);
					


				}
				else if( PType.JOIN.ordinal() == packetType )
				{
					// notify us that another ghost has joined
					// readd them to the board or something
					
					// what ghost just joined?
					switch(data1)
					{
						case 0://blinky joined
							break;
						case 1:// clyde joined
							break;
						case 2:// inky joined
							break;
						case 3:// pinky joined
							break;
					}

				}
				else if( PType.LEAVE.ordinal() == packetType )
				{
					// notify that a ghost has left the game
					// if the ghost type is the same as the client, then client drops out.

					if( data1==Client.ghostType.ordinal() )
					{
						// the ghost leaving is the current player
						moreQuotes = false;
						
						System.out.println("You were dropped from the server, or you just left.");
						
						break;
					}
					else
					{
						// someone else is leaving
						// TODO: Process the drop
					}
				}
				else if( PType.HEARTBEAT.ordinal() == packetType )
				{
					// heartbeat
				}
				else if( PType.SCORE.ordinal() == packetType )
				{
					// receive a score update
				}
				else if( PType.GAMEOVER.ordinal() == packetType )
				{
					// game ended
					moreQuotes = false;
					GameController.getInstance().getPacInstance().drawGameover();
				}
				else
				{
					// some other junk packet (these are discarded as nobody cares about a client
				}
			}
			socket.leaveGroup(group);
			socket.close();
			System.exit(0);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }




}//Client
