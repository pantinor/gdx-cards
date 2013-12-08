import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.antinori.cards.network.BroadcastThread;


public class NetworkTest {
	
	
	public static void main(String[] args) {
		
		NetworkTest nt = new NetworkTest();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public NetworkTest() {
		
		HostsListenerThread listen = new HostsListenerThread();
		BroadcastThread broadcast = new BroadcastThread();
		
		broadcast.start();
		listen.start();
		
	}
	
	
	
	class HostsListenerThread extends Thread {

		public void run() {
			
			try {
				
				boolean alive = true;
				
				System.out.println("HostsListenerThread started on subnet");

				MulticastSocket socket = new MulticastSocket(4446);
				InetAddress group = InetAddress.getByName("239.255.255.255");
				socket.joinGroup(group);

				DatagramPacket packet;
				while (alive) {
					byte[] buf = new byte[256];
					packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);

					String host = packet.getAddress().getHostAddress();

					System.out.println("Found host: " + host);


				}

				socket.leaveGroup(group);
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("HostsListenerThread done");

		}
	}

}
