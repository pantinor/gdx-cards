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

import org.antinori.cards.BaseFunctions;
import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.CreatureFactory;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.SlotImage;

public class NetworkGame {

	private Socket socket;
	private ServerSocket serverSocket;
	private static final int SERVER_PORT = 5000;
	private Cards game;
	private boolean server = false;
	private boolean isMyTurn = false;

	public NetworkGame(Cards game, boolean server) {

		this.game = game;
		this.server = server;
		
		if (server) {
			
			final BroadcastThread bcth = new BroadcastThread();
			bcth.start();
			
			try {
				serverSocket = new ServerSocket(SERVER_PORT);
				final Cards temp = game;
				new Thread() {
					public void run() {
						try {
							socket = serverSocket.accept();
							
							System.out.println("Server Socket listening on port 5000");
							
							bcth.setAlive(false);
							isMyTurn = true;
							
							//send the player id and set the remote player id
							sendEvent(new NetworkEvent(Event.REMOTE_PLAYER_ID_INIT,temp.player.getPlayerInfo().getId()));
							
							ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
							Object obj = in.readObject();
							
							System.out.println("Received: " + obj);
							if (obj instanceof NetworkEvent) {

								NetworkEvent ne = (NetworkEvent) obj;
								Event evt = ne.getEvent();
								String id = ne.getId();

								if (evt == Event.REMOTE_PLAYER_ID_INIT) {
									temp.setOpposingPlayerId(id);
								}
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
		             
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			
		}

	}
	
	public boolean isServer() {
		return this.server;
	}
	
	public boolean isMyTurn() {
		return this.isMyTurn;
	}

	public boolean connectToServer(String remoteHost) {
		try {
			socket = new Socket(remoteHost, SERVER_PORT);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean isConnected() {
		return (socket==null?false:socket.isConnected());
	}
	
	public String getConnectedHost() {
		return (isConnected()? "not connected": "connected");
	}
	
	public void read() {
		try {
			
			if (socket == null) throw new Exception("socket is connected (null).");
			
			boolean stillMoreToCome = true;
			while(stillMoreToCome) {
			
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				Object obj = in.readObject();
				
				System.out.println("Received: "+ obj);

				
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

					
				} else if (obj instanceof NetworkEvent) {
					
					NetworkEvent ne = (NetworkEvent)obj;
					
					Event evt = ne.getEvent();
					int index = ne.getSlot();
					int val = ne.getAttackAffectedAmount();
					int health = ne.getHealthAffectedAmount();
					String id = ne.getId();
					
					if (evt == Event.REMOTE_PLAYER_ID_INIT) {
						game.setOpposingPlayerId(id);
						sendEvent(new NetworkEvent(Event.REMOTE_PLAYER_ID_INIT,game.player.getPlayerInfo().getId()));
						continue;
					}

					PlayerImage pi = game.getPlayerImage(id);
					SlotImage slot = pi.getSlots()[index];
					CardImage[] cards = pi.getSlotCards();
					CardImage existingCardImage = cards[index];
						
					switch(evt) {
					
					case REMOTE_PLAYER_ID_INIT:
						break;
					case ATTACK_AFFECTED:
						if (val > 0) {
							existingCardImage.getCard().incrementAttack(val);
						} else {
							existingCardImage.getCard().decrementAttack(val);
						}
						break;
					case CARD_ADDED:
						
						CardImage orig = game.cs.getCardImageByName(Cards.smallCardAtlas, Cards.smallTGACardAtlas, ne.getCardName());
						CardImage ci = orig.clone();
						
						Creature sp1 = CreatureFactory.getCreatureClass(ne.getCardName(), game, ci.getCard(), ci, index, pi, game.getOpposingPlayerImage(id));
						ci.setCreature(sp1);
						
						cards[index] = ci;
						slot.setOccupied(true);
						ci.setFont(Cards.greenfont);
						ci.setFrame(Cards.ramka);
						ci.addListener(game.tl);
						ci.addListener(game.sdl);
						ci.setBounds(0, Cards.SCREEN_HEIGHT, ci.getFrame().getWidth(), ci.getFrame().getHeight());
						game.stage.addActor(ci);
						ci.addAction(sequence(moveTo(slot.getX() + 5, slot.getY() + 26, 1.0f)));
						
						break;
					case CARD_HEALTH_AFFECTED:
						if (health > 0) {
							existingCardImage.incrementLife(Math.abs(health), game);
						} else {
							existingCardImage.decrementLife(Math.abs(health), game);
							game.moveCardActorOnBattle(existingCardImage, pi);
						}
						break;
					case CARD_REMOVED:
						
						BaseFunctions bf = (BaseFunctions)existingCardImage.getCreature();
						bf.disposeCardImage(pi, index);
						
						break;
					case PLAYER_HEALTH_AFFECTED:
						if (health > 0) {
							pi.incrementLife(Math.abs(health), game);
						} else {
							pi.decrementLife(Math.abs(health), game, ne.isDamageViaSpell());
						}
						break;
					case SPELL_CAST:
						break;
					case PLAYER_STRENGTH_AFFECTED:
						int str = ne.getStrengthAffected();
						CardType type = ne.getTypeStrengthAffected();
						if (str > 0) {
							pi.getPlayerInfo().incrementStrength(type, Math.abs(str));
						} else {
							pi.getPlayerInfo().decrementStrength(type, Math.abs(str));
						}
						break;
					default:
						break;
					
					}
					
					
				} else if (obj instanceof String) {
					stillMoreToCome = false;
				}
				
			}
			
			isMyTurn = true;
			System.out.println("It is now my turn!");

		
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}




	public void sendPlayer(Player player) {

		try {
			if (socket == null) throw new Exception("socket is connected (null).");

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(player);
			out.flush();
			
			System.out.println("Sent player: "+ player);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	public void sendEvent(NetworkEvent event) {

		try {
			if (socket == null) throw new Exception("socket is connected (null).");

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(event);
			out.flush();
			
			System.out.println("Sent event: "+ event);
			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void sendYourTurnSignal() {

		try {
			if (socket == null) throw new Exception("socket is connected (null).");

			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject("Your turn.");
			out.flush();
			
			System.out.println("Sent \"your turn\" signal.");
			isMyTurn = false;

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
