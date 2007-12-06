package code.uci.pacman.multiplayer;

import code.uci.pacman.game.*;
import code.uci.pacman.ai.*;
import code.uci.pacman.controllers.*;
import code.uci.pacman.objects.controllable.*;
import code.uci.pacman.objects.stationary.*;
import java.io.*;
import java.net.*;
import java.util.*;


/**
 * Server is responsible for handling incoming client requests.
 * @author Networking Team
 *	
 */
public class Server extends Thread
{
	private static final long serialVersionUID = 1L;
	protected DatagramSocket socket = null;

	protected final static String MCAST_ADDRESS = "230.0.0.1";
	

	// TODO: TODO TODO TODO TODO: These must be reset when the server changes levels, or weird
	// things will happen
	private static ArrayList<DatagramPacket> packetHistory;

	protected static InetAddress group;
	class ClientMap
	{
		private HashMap<GhostType, InetAddress> gt2ia;
		private HashMap<InetAddress, GhostType> ia2gt;

		public ClientMap()
		{
			gt2ia = new HashMap<GhostType, InetAddress>();
			ia2gt = new HashMap<InetAddress, GhostType>();
		}
		
		public void add(InetAddress a, GhostType g)
		{
			gt2ia.put(g,a);
			ia2gt.put(a,g);
		}
		public void add(GhostType g, InetAddress a)
		{
			gt2ia.put(g,a);
			ia2gt.put(a,g);
		}

		public void drop(InetAddress a)
		{
			GameState.getInstance().getGhosts().getObjectAt( capitalize(ia2gt.get(a).name()) ).returnAI();
			gt2ia.remove( ia2gt.get(a) );
			ia2gt.remove(a);
		}
		public void drop(GhostType g)
		{
			GameState.getInstance().getGhosts().getObjectAt( capitalize(g.name()) ).returnAI();
			ia2gt.remove( gt2ia.get(g) );
			gt2ia.remove(g);
		}

		public boolean contains( InetAddress a)
		{
			return ia2gt.containsKey(a);
		}
		public boolean contains( GhostType g)
		{
			return gt2ia.containsKey(g);
		}
		
		public GhostType get(InetAddress a)
		{
			return ia2gt.get(a);
		}
		public InetAddress get(GhostType g)
		{
			return gt2ia.get(g);
		}

		public int size()
		{
			return gt2ia.size();
		}
	}//CLIENTMAP////////////////////////
	
	private static ClientMap clients;

    protected boolean moreQuotes = true;

	// this class just sends notifications that slots are open on the server
	private class OpenSlots extends Thread 
	{
		public void run()
		{
			// be lazy at the start
			try
			{
				// for some reason, if the client connects too fast, the game explodes
				Thread.currentThread().sleep(5000);
			}
			catch(Exception e){}

			// start the notifications
			while(true)
			{
				try
				{
					boolean spotsOpen = false;
					byte[] buf = new byte[2];
					GhostType gtype = GhostType.CLYDE;
					if( !Server.clients.contains(GhostType.PINKY) )
					{
						spotsOpen = true;
						gtype = GhostType.PINKY;
					}
					else if( !Server.clients.contains(GhostType.CLYDE) )
					{
						spotsOpen = true;
						gtype = GhostType.CLYDE;
					}
					else if( !Server.clients.contains(GhostType.INKY) )
					{
						spotsOpen = true;
						gtype = GhostType.INKY;
					}
					else if( !Server.clients.contains(GhostType.BLINKY) )
					{
						spotsOpen = true;
						gtype = GhostType.BLINKY;
					}
					
					// be quiet if we dont have any slots open
					if(spotsOpen)
					{
						// build the packet
						buf[0] = new Integer(PType.SPOTFREE.ordinal()).byteValue();
						buf[1] = new Integer(gtype.ordinal()).byteValue();

						// send the packet out
						//InetAddress group = InetAddress.getByName("230.0.0.1");
						DatagramSocket socketSend = new MulticastSocket();
						DatagramPacket packet = new DatagramPacket(buf, buf.length, Server.group, 4446 );
						socketSend.send(packet);
						socketSend.close();

						// sleep some
						Thread.currentThread().sleep(5000);
					}
					else
					{
						// take longer naps when we are full
						Thread.currentThread().sleep(10000);
					}
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}/// OPEN SLOTS/////////////

	// this class just sends notifications that slots are open on the server
	private class GameStats extends Thread 
	{
		private InetAddress gsgroup;
		private MulticastSocket gssocket;

		public GameStats()
		{
			try
			{
				gsgroup = InetAddress.getByName(Server.MCAST_ADDRESS);
				gssocket = new MulticastSocket();
			}
			catch(Exception e){}
		}
		private void send(byte[] buf)
		{
			try
			{
				DatagramPacket packet = new DatagramPacket(buf, buf.length, gsgroup, 4446 );
				gssocket.send(packet);
			}
			catch(Exception e){
				
					e.printStackFrame();	
				}
		}
		private void send(DatagramPacket pack)
		{
			try
			{
				gssocket.send(pack);
			}
			catch(Exception e){
					e.printStackFrame();	
				}
		}
		public void run()
		{
			// be lazy at the start
			try
			{
				// for some reason, if the client connects too fast, the game explodes
				Thread.currentThread().sleep(5000);
			}
			catch(Exception e){}

			// start the notifications
			while(true)
			{
				try
				{
					if(Server.clients.size()>0)
					{
						// SEND SCORE
						byte[] score = new byte[3];
						score[0] = new Integer( PType.SCORE.ordinal() ).byteValue();
						byte[] scoreVal = new byte[2];
						scoreVal = Server.getInt( GameState.getInstance().getScore() );
						score[1] = scoreVal[0];
						score[2] = scoreVal[1];
						send(score);

						// SEND LIVES
						byte[] lives = new byte[2];
						lives[0] = new Integer( PType.LIVES.ordinal() ).byteValue();
						lives[1] = new Integer( GameState.getInstance().getLives() ).byteValue();
						send(lives);

						// SEND LEVEL
						byte[] level = new byte[2];
						level[0] = new Integer( PType.LIVES.ordinal() ).byteValue();
						level[1] = new Integer( GameState.getInstance().getLevel() ).byteValue();
						send(level);
					
						// PACKET HISTORY
						for(int i=0;i<packetHistory.size();i++)
						{
							send(packetHistory.get(i));			
						}
						// take longer naps when we are full
						Thread.currentThread().sleep(1000);
					}
					else
					{
						// nobody is playing, wait longer
						Thread.currentThread().sleep(6000);
					}
				}
				catch(Exception e)
				{
					e.printStackFrame();	
				}
			}
		}
	}/// OPEN SLOTS/////////////


	
	private class Consistency extends Thread
	{
		private InetAddress cgroup;
		private MulticastSocket csocket;

		public Consistency()
		{
			try
			{
				cgroup = InetAddress.getByName(MCAST_ADDRESS);
				csocket = new MulticastSocket();
			}
			catch(Exception e){}
		}

		private void send(GhostType gtype, int x, int y)
		{
			try
			{
				byte[] buf = new byte[6];
				buf[0] = new Integer(PType.GPOS.ordinal()).byteValue();
				buf[1] = new Integer(gtype.ordinal()).byteValue();

				byte[] xp = Server.getInt(x);
				buf[2] = xp[0];
				buf[3] = xp[1];
			
				byte[] yp = Server.getInt(y);
				buf[4] = yp[0];
				buf[5] = yp[1];

				// send the packet out
				DatagramPacket packet = new DatagramPacket(buf, buf.length, cgroup, 4446 );
				csocket.send(packet);
			}
			catch(Exception e)
			{

			}
		}
		private void send( int x, int y)
		{
			try
			{
				byte[] buf = new byte[6];
				buf[0] = new Integer(PType.PPOS.ordinal()).byteValue();
				
				byte[] xp = Server.getInt(x);
				buf[1] = xp[0];
				buf[2] = xp[1];
			
				byte[] yp = Server.getInt(y);
				buf[3] = yp[0];
				buf[4] = yp[1];

				// send the packet out
				DatagramPacket packet = new DatagramPacket(buf, buf.length, cgroup, 4446 );
				csocket.send(packet);
			}
			catch(Exception e)
			{

			}
		}

		public void run()
		{
			try
			{
				while(true)
				{
					if(Server.clients.size()>0)
					{
						// pacman position
						PacMan pm = GameState.getInstance().getPacMan();
						send( pm.x(), pm.y() );

						GhostController gc = GameState.getInstance().getGhosts();
						
						Ghost gbl;
						
						gbl = gc.getObjectAt("Blinky");
						send( GhostType.BLINKY, gbl.x(), gbl.y() );
						
						gbl = gc.getObjectAt("Clyde");
						send( GhostType.CLYDE, gbl.x(), gbl.y() );

						gbl = gc.getObjectAt("Inky");
						send( GhostType.INKY, gbl.x(), gbl.y() );

						gbl = gc.getObjectAt("Pinky");
						send( GhostType.PINKY, gbl.x(), gbl.y() );
					}
					/// sleep
					Thread.currentThread().sleep(100);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}//run
	}//CONSISTENCY /////////////////////









	protected static byte[] getInt(int d)
	{
		byte[] buf = new byte[2];
		
		int hund = (int) Math.floor(d/100.0);
		buf[0] = new Integer( hund ).byteValue();
		buf[1] = new Integer( (d) - (100*hund) ).byteValue();
		return buf;
	}





   /**
    * Starts the Server.
    * @throws IOException
    */
    public Server() throws IOException 
    {
		this("Server");
    }
	
	/**
	 * Starts a server at socket 4445
	 * @param name unknown
	 * @throws IOException
	 */
    public Server(String name) throws IOException
    {
        super(name);
        socket = new DatagramSocket(4445);
		Server.clients = new ClientMap();//new ArrayList<GhostType>();
		Server.group = InetAddress.getByName(MCAST_ADDRESS);

		packetHistory = new ArrayList<DatagramPacket>();

        System.out.println("START SERVER");
    }
	
	// a single command
    
    /**
     * Sends a packet
     * @param type of command to send out
     */
	public static void send(PType type)
	{
		byte[] buf = new byte[1];
		buf[0] = new Integer(type.ordinal()).byteValue();
		sendData(buf);
	}
	
	/**
     * Sends a packet
     * @param type of command to send out add/del for pill/pellets/fruit
	 * @param x xcoord of the object
	 * @param y ycoord of the object
     */
	public static void send(PType type, int x, int y)
	{
		byte[] buf = new byte[5];
		buf[0] = new Integer(type.ordinal()).byteValue();
		
		byte[] xp = getInt(x);
		buf[1] = xp[0];
		buf[2] = xp[1];
	
		byte[] yp = getInt(y);
		buf[3] = yp[0];
		buf[4] = yp[1];
		
		if( type.equals(PType.PILLD) || type.equals(PType.PPILLD) || type.equals(PType.DFRUIT) )
		{
			sendData(buf,true);
		}
		else
		{
			sendData(buf);
		}
	}

	/**
     * send packet with ghost info and direction info
     * @param ghost the type of ghost
	 * @param dir the direction its moving
     */
	public static void send(GhostType ghost, Direction dir)
	{
		byte[] buf = new byte[3];
		buf[0] = new Integer(PType.GMOVE.ordinal()).byteValue(); // TYPE
		buf[1] = new Integer(dir.ordinal()).byteValue(); // DIRECTION
		buf[2] = new Integer(ghost.ordinal()).byteValue(); // GHOST_TYPE
		sendData(buf);
	}

	/**
	 * Send a direction to clients
	 * @param dir the direction pacman is moving
	 */
	public static void send(Direction dir)
	{
		byte[] buf = new byte[2];
		buf[0] = new Integer(PType.PMOVE.ordinal()).byteValue();
		buf[1] = new Integer(dir.ordinal()).byteValue();
		sendData(buf);
	}
	
	/**
	 * Sends a packet with corresponding ghost type
	 * @param type the type of command to send
	 * @param ghost the ghost to associate with
	 */
	public static void send(PType type, GhostType ghost)
	{
		byte[] buf = new byte[2];
		buf[0] = new Integer(type.ordinal()).byteValue();
		buf[1] = new Integer(ghost.ordinal()).byteValue();
		sendData(buf);
	}


	private static void sendData(byte[] buf)
	{
		sendClientData(buf,false);
	}
	private static void sendData(byte[] buf, boolean save)
	{
		sendClientData(buf,save);
	}

	// sends raw data to a client
	private static void sendClientData(byte[] buf, boolean save)
	{
		try
		{
			MulticastSocket socketSend = new MulticastSocket();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446 );
			if(save)
			{
				packetHistory.add(packet);
			}
			socketSend.send(packet);
			socketSend.close();
		}
		catch(Exception e)
		{

		}
	}


	private String capitalize(String s)
	{
		if (s.length() == 0)
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}


	/**
	 * runs the server
	 */
    public void run()
	{
		// should be while game is not over
		new OpenSlots().start();
		
		new Consistency().start();
		new GameStats().start();

		while (moreQuotes) 
		{
			try 
			{
				byte[] buf = new byte[3];
				//byte[] bufOut = new byte[6];

				// receive request
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				buf = packet.getData();
				
				// get client address
				InetAddress address = packet.getAddress();
				
				// get the packet type
				int packetType = buf[0] & 0x000000FF;
				//System.out.print("PACKET("+packetType+","+(buf[1]&0x000000FF)+","+(buf[2]&0x000000FF)+","+(buf[3]&0x000000FF)+"): ");

				// this makes sure that they are actually playing
				if( Server.clients.contains(address) )
				{									
					if( PType.GMOVE.ordinal() == packetType )
					{
						// a ghost move
						
						Direction dir = Direction.UP;

						// get direction
						switch((buf[1]&0x000000FF))
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
						/*
						switch((buf[2]&0x000000FF))
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
						}*/
						
						GhostType gtype = clients.get(address);

						GameState.getInstance().getGhosts().getObjectAt( capitalize(gtype.name()) ).setDirection(dir);

						// notify all the clients
						send(gtype, dir);
					}
					else if( PType.HEARTBEAT.ordinal() == packetType )
					{
						// A client heartbeat, that way, we keep everyone updated.


					}
					else if( PType.LEAVE.ordinal() == packetType )
					{
						// a ghost is leaving

						GhostType gtype = clients.get(address);
						// find which ghost it is, and notify all the clients that they dropped
						/*
						switch( (buf[1]&0x000000FF) )
						{
							case 0://b
								gtype = GhostType.BLINKY;
								break;
							case 1://c
								gtype = GhostType.CLYDE;
								break;
							case 2://i
								gtype = GhostType.INKY;
								break;
							case 3://p
								gtype = GhostType.PINKY;
								break;
						}*/

						send(PType.LEAVE, gtype);
						Server.clients.drop( gtype );


					}
					else
					{
						// some other junk packet
						//sendError(address,ServErrors.UNKNOWN_COM);
					}
				}//is client
				else
				{
					// they are not playing, so all they can do is join
					if( PType.JOIN.ordinal() == packetType )
					{
						if( Server.clients.size() < 4)
						{
							// get the ghost type
							GhostType gtype = GhostType.BLINKY;
							
							// Update the server
							switch((buf[1]&0x000000FF))
							{
								case 0://b
									gtype = GhostType.BLINKY;
									break;
								case 1://c
									gtype = GhostType.CLYDE;
									break;
								case 2://i
									gtype = GhostType.INKY;
									break;
								case 3://p
									gtype = GhostType.PINKY;
									break;
							}
							// add to client database
							Server.clients.add( gtype, address );
							
							// place them in game
							GameState.getInstance().getGhosts().getObjectAt(capitalize(gtype.name()) ).setDirection(Direction.UP);

						}
					}//join
					else
					{
						// wtf? just ignore them
					}

				}// not a client
			}
			catch (Exception e)
			{
				//e.printStackTrace(System.out);
				//moreQuotes = false;
			}
		}

		// TODO: when the socket fails out, we should make everyone use the AI again

		//System.out.println("Server Shutdown");
		socket.close();
    }//run	
}





