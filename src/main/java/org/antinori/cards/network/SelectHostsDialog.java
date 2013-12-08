package org.antinori.cards.network;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import org.antinori.cards.Cards;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SelectHostsDialog extends Window {
	private Cards game;
	private java.util.List<String> foundHosts = new ArrayList<String>();
	private List entries;
	private boolean alive = true;

	public SelectHostsDialog(String title, Cards game, final Stage stage, Skin skin) {
		
		super(title, skin);
		this.game = game;
		final Skin skinTemp = skin;

		Thread listener = new Thread(new HostsListenerThread());
		listener.start();

		defaults().padTop(2);
		defaults().padBottom(2);
		defaults().padLeft(2);
		defaults().padRight(2);

		String[] items = {"","",""};
		
		Label label1 = new Label("Detected Hosts:",skin);

		entries = new List(items, skin);
		ScrollPane scrollPane = new ScrollPane(entries, skin);
		scrollPane.setFlickScroll(false);
		
		Label label2 = new Label("Enter Host:",skin);
		final TextField textfield = new TextField("", skin);
		
		Button connect = new TextButton("Connect", skin, "default");
		
		connect.addListener(new ChangeListener() {
			String selectedHost;
			public void changed (ChangeEvent event, Actor actor) {
				
				selectedHost = textfield.getText();
				if (selectedHost == null || selectedHost.length() < 1) {
					selectedHost = entries.getSelection();
				}
				
				if (selectedHost == null || selectedHost.length() < 1) {
					return;
				}
				
				Dialog dialog = new Dialog("Connect", skinTemp, "dialog") {
					protected void result (Object object) {
						doConnection(selectedHost);
					}
				}.text("Connect to "+selectedHost+"?").button("Yes", true).button("No", false).key(Keys.ENTER, true);
				
				dialog.show(stage);
			}
		});

		try {
			
			add().space(3);
			add(label1);
			add(scrollPane).fill().expand().colspan(4).maxHeight(200);
			add().space(3);
			row();
			
			add().space(3);
			add(label2);
			add(textfield).expandX().fillX().colspan(4);
			add().space(3);
			row();
			
			add().space(3).expandX().fillX().colspan(5);
			add(connect).expandX().fillX();
			add().space(3);
			row();
			pack();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void doConnection(String host) {
		Cards.NET_GAME = new NetworkGame(this.game, false);
		boolean connected = Cards.NET_GAME.connectToServer(host);
		if (connected) {
			setAlive(false);
			this.remove();
		}
	}

	class HostsListenerThread implements Runnable {

		public void run() {

			try {
				
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

					if (!foundHosts.contains(host)) 
						foundHosts.add(host);
					
					Object[] items = foundHosts.toArray();
					entries.setItems(items);
					SelectHostsDialog.this.pack();
				}

				socket.leaveGroup(group);
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("HostsListenerThread done");

		}
	}
	
	
	
//	class TestThread implements Runnable {
//
//		public void run() {
//
//
//			for (int i=1;i<6;i++) {
//				try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				foundHosts.add("192.168.1."+i);
//				Object[] items = foundHosts.toArray();
//				entries.setItems(items);
//				
//				SelectHostsDialog.this.pack();
//			}
//
//		}
//	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

}
