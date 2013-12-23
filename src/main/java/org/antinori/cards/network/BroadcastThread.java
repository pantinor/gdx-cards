package org.antinori.cards.network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Date;
import java.util.Random;

public class BroadcastThread extends Thread {

	private boolean alive = true;

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public void run() {

		try {

			MulticastSocket socket = new MulticastSocket(4446);
			InetAddress group = InetAddress.getByName("239.255.255.255");
			socket.joinGroup(group);
			
			System.out.println("Broadcasting to subnet");

			while (alive) {
				try {
					byte[] buf = new byte[256];

					String dString = new Date().toString();
					buf = dString.getBytes();

					DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
					socket.send(packet);
					
					System.out.println("Sent multicast broadcast signal on the local subnet.");

					int factor = new Random().nextInt(10);
					
					sleep(factor * 1000);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			socket.leaveGroup(group);
			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
