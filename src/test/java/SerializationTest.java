import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.GameOverException;
import org.antinori.cards.Player;
import org.antinori.cards.Specializations;


public class SerializationTest {
	
	public static void main(String[] args) {
		
		try {
			whatever();
		} catch (GameOverException ge) {
			//ge.printStackTrace();
		} finally {
			System.out.println("in finally2");
		}

		
	}
	
	public static void whatever() throws GameOverException {
		try {
			throw new GameOverException("123123");
		} catch (GameOverException ge) {
			throw ge;
		} finally {
			System.out.println("in finally1");
		}
	}
	
	public static void main2(String[] args) {
		
//		for (String s : Specializations.titles()) {
//			Specializations sp = Specializations.fromTitleString(s);
//			System.out.println(sp);
//		}
		
		try {
		
			Player player1 = new Player();
			player1.setPlayerClass(Specializations.Beastmaster);
			player1.setImgName("test");
			
			CardImage card1 = new CardImage();
			card1.setEnabled(true);
			card1.setHighlighted(true);
			Card c = new Card(CardType.AIR);
			c.setName("test");
			card1.setCard(c);
			
			List<CardImage> cards = new ArrayList<CardImage>();
			cards.add(card1);
			player1.setCards(CardType.FIRE, cards);
			player1.setCards(CardType.AIR, cards);
			player1.setCards(CardType.WATER, cards);
			player1.setCards(CardType.EARTH, cards);
			player1.setCards(CardType.OTHER, cards);

			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(player1);
			out.close();
			
			byte[] buf = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream in = new ObjectInputStream(bais);
			Player player2 =  (Player)in.readObject();
			in.close();
			
			System.out.println("Got "+player2);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			Card card1 = new Card(CardType.AIR);
			card1.setName("test");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(card1);
			out.close();
			
			byte[] buf = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream in = new ObjectInputStream(bais);
			Card card2 =  (Card)in.readObject();
			in.close();
			
			System.out.println("Got "+card2);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
			CardImage card1 = new CardImage();
			card1.setEnabled(true);
			card1.setHighlighted(true);
			Card c = new Card(CardType.AIR);
			c.setName("test");
			card1.setCard(c);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(baos);
			out.writeObject(card1);
			out.close();
			
			byte[] buf = baos.toByteArray();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(buf);
			ObjectInputStream in = new ObjectInputStream(bais);
			CardImage card2 =  (CardImage)in.readObject();
			in.close();
			
			System.out.println("Got "+card2);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
