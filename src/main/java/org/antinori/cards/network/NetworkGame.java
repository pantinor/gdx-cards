package org.antinori.cards.network;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.CreatureFactory;
import org.antinori.cards.Player;
import org.antinori.cards.SlotImage;

import com.badlogic.gdx.scenes.scene2d.Action;

public class NetworkGame {

	private String remoteHost;
	private Socket socket;
	private ServerSocket serverSocket;
	private static final int SERVER_PORT = 5000;
	private boolean server = false;
	private Cards game;

	public NetworkGame(Cards game, boolean server) {

		this.server = server;
		this.game = game;
		
		BroadcastThread bcth = new BroadcastThread();
		bcth.start();

		if (server) {
			try {
				serverSocket = new ServerSocket(SERVER_PORT);
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}

			try {
				socket = serverSocket.accept();
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}

		}

	}

	public String getRemoteHost() {
		return remoteHost;
	}

	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	public void startListenFromServer() {
		try {
			socket = new Socket(remoteHost, SERVER_PORT);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void read() {
		try {
			boolean ready = false;
			while(!ready) {
			
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Object obj = in.readObject();
				
				if (obj instanceof Player) {
					Player player = (Player)obj;
					setPlayer(player);
					Player opptPlayer = game.opponent.getPlayerInfo();
					
					opptPlayer.setLife(player.getLife());
					
					opptPlayer.setStrengthFire(player.getStrengthFire());
					opptPlayer.setStrengthAir(player.getStrengthAir());
					opptPlayer.setStrengthWater(player.getStrengthWater());
					opptPlayer.setStrengthEarth(player.getStrengthEarth());
					opptPlayer.setStrengthSpecial(player.getStrengthSpecial());

					opptPlayer.setCards(CardType.FIRE, player.getFireCards());
					opptPlayer.setCards(CardType.AIR, player.getAirCards());
					opptPlayer.setCards(CardType.WATER, player.getWaterCards());
					opptPlayer.setCards(CardType.EARTH, player.getEarthCards());
					opptPlayer.setCards(CardType.OTHER, player.getSpecialCards());

				} else if (obj instanceof CardSerializable) {
					
					CardSerializable cs = (CardSerializable)obj;
					
					CardImage ci = getCardImageAfterSerialization(cs.getCard());
					if (!cs.getCard().isSpell()) {
						Creature sp1 = CreatureFactory.getCreatureClass(cs.getCard().getName(), game, cs.getCard(), ci, true, cs.getSlotIndex());
						ci.setCreature(sp1);
						SlotImage slot = game.getTopSlots()[cs.getSlotIndex()];
						slot.setOccupied(true);
						game.getTopSlotCards()[cs.getSlotIndex()] = ci;
						ci.setBounds(slot.getX() + 5, slot.getY() + 26, ci.getFrame().getWidth(), ci.getFrame().getHeight());
						game.stage.addActor(ci);
						ci.addAction(sequence(moveTo(slot.getX() + 5, slot.getY() + 26, 1.0f)));
					} else {
						
					}

					
				} else if (obj instanceof String) {
					ready = true;
				}
				
				// Send a reply
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject("OK");
				out.flush();
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}




	public void sendPlayer(Player player) {

		try {
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(player);
			out.flush();
			
			//wait for reply
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String reply = (String) in.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendCard(Card card, int slot) {

		try {
			
			CardSerializable cs = new CardSerializable(card, slot);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(cs);
			out.flush();
			
			//wait for reply
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String reply = (String) in.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendReadySignal() {

		try {
			
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject("Ready");
			out.flush();
			
			//wait for reply
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			String reply = (String) in.readObject();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	private void setPlayer(Player player) {
		try {
			//set cards after serialized
			player.setCards(CardType.FIRE, setCardsAfterSerialization(player.getFireCards()));
			player.setCards(CardType.AIR, setCardsAfterSerialization(player.getAirCards()));
			player.setCards(CardType.WATER, setCardsAfterSerialization(player.getWaterCards()));
			player.setCards(CardType.EARTH, setCardsAfterSerialization(player.getEarthCards()));
			player.setCards(CardType.OTHER, setCardsAfterSerialization(player.getSpecialCards()));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	private List<CardImage> setCardsAfterSerialization(List<CardImage> cards) throws Exception {
		
		List<CardImage> newCards = new ArrayList<CardImage>();

		for (CardImage temp : cards) {
			CardImage ci = getCardImageAfterSerialization(temp.getCard());
			ci.setEnabled(temp.isEnabled());
			ci.setHighlighted(temp.isHighlighted());
			
			newCards.add(ci);
		}
		
		CardImage.sort(newCards);
		
		return newCards;
	}
	
	private CardImage getCardImageAfterSerialization(Card card) throws Exception {
		String name = card.getName();
		CardImage ci = game.cs.getCardImageByName(Cards.smallCardAtlas, Cards.smallTGACardAtlas, name);
		ci.setFont(Cards.greenfont);
		ci.setFrame(ci.getCard().isSpell()?Cards.spellramka:Cards.ramka);
		ci.addListener(game.sdl);
		ci.setBounds(0, 0, ci.getFrame().getWidth(), ci.getFrame().getHeight());
		ci.setCard(card);
		return ci;
	}
	

}
