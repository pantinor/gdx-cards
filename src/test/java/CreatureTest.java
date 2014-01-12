import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardSetup;
import org.antinori.cards.Cards;
import org.antinori.cards.Creature;
import org.antinori.cards.CreatureFactory;
import org.antinori.cards.LogScrollPane;
import org.antinori.cards.Player;
import org.antinori.cards.PlayerImage;
import org.antinori.cards.Sound;
import org.antinori.cards.Sounds;
import org.antinori.cards.Spell;
import org.antinori.cards.SpellFactory;
import org.antinori.cards.spells.FlameWave;
import org.apache.commons.io.FileUtils;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class CreatureTest implements ApplicationListener {

	public static void main(String[] args) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "test";
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 768;
		new LwjglApplication(new CreatureTest(), cfg);

	}

	public void create() {

		try {
			
			String playerCard = "Titan";
			String opponentCard = "SeaSprite";

			String dir = System.getProperty("user.dir");

			Cards game = new Cards();

			Skin skin = new Skin(new FileHandle(dir + "/src/main/resources/skin/uiskin.json"));

			Cards.logScrollPane = new LogScrollPane(skin);
			CardSetup cs = new CardSetup();
			cs.parseCards();

			TextureAtlas smallCardAtlas = new TextureAtlas(new FileHandle(dir + "/src/main/resources/images/smallCardsPack.txt"), true);
			TextureAtlas smallTGACardAtlas = new TextureAtlas(new FileHandle(dir + "/src/main/resources/images/smallTGACardsPack.txt"), true);

			PlayerImage player = new PlayerImage(null, null, new Player());
			PlayerImage opponent = new PlayerImage(null, null, new Player());
			
			player.setName("player");
			opponent.setName("opponent");

			CardImage ci1 = cs.getCardImageByName(smallCardAtlas, smallTGACardAtlas, playerCard);
			CardImage ci2 = cs.getCardImageByName(smallCardAtlas, smallTGACardAtlas, opponentCard);

			player.getSlotCards()[1] = ci1;
			opponent.getSlotCards()[1] = ci2;

			Creature creature = CreatureFactory.getCreatureClass(playerCard, game, ci1.getCard(), ci1, 1, player, opponent);
			ci1.setCreature(creature);
			
			Creature creature2 = CreatureFactory.getCreatureClass(opponentCard, game, ci2.getCard(), ci2, 1, opponent, player);
			ci2.setCreature(creature2);

			//creature.onSummoned();
			//creature.onAttack();
			
			//Sounds.play(Sound.MAGIC);
			Sounds.play(new FlameWave(null, null, ci2, opponent, opponent));

					
//			for (Card card : cs.getSpellCards()) {
//				String name = card.getName();
//				Spell spell = SpellFactory.getSpellClass(name, null, null, null, new PlayerImage(null, null, new Player()), new PlayerImage(null, null, new Player()));	
//				Music m = Sounds.play(spell);
//				
//				//while (m.isPlaying()) {
//					Thread.sleep(3000);
//				//}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main2(String[] args) {

		Creature c = CreatureFactory.getCreatureClass("Lemure", null, null, null, 1, new PlayerImage(null, null, new Player()), new PlayerImage(null, null, new Player()));

		System.out.println(c);
	}

	public static void main3(String[] args) {

		CardSetup cs = new CardSetup();
		cs.parseCards();

		Set<Card> creatures = cs.getCreatureCards();

		Set<Card> spells = cs.getSpellCards();

		String template1 = "package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;\n\n" + "import org.antinori.cards.Card;\n" + "import org.antinori.cards.CardImage;\n" + "import org.antinori.cards.Cards;\n\n"
				+ "public class %s extends BaseCreature {\n" + "public %s(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {\n" + "super(game, card, cardImage, slotIndex, owner, opponent);\n}\n"
				+ "public void onSummoned() {\nsuper.onSummoned();\n}\n" + "public void onAttack() {\nsuper.onAttack();\n}\n}\n";

		String template2 = "package org.antinori.cards.spells;import org.antinori.cards.PlayerImage;\n\n" + "import org.antinori.cards.Card;\n" + "import org.antinori.cards.CardImage;\n" + "import org.antinori.cards.Cards;\n\n"
				+ "public class %s extends BaseSpell {\n" + "public %s(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {\n" + "super(game, card, cardImage, owner, opponent);\n}\n"
				+ "public void onCast() {\nsuper.onCast();\n}\n}\n";

		for (Card c : creatures) {
			String clazz = String.format(template1, c.getName(), c.getName());
			try {
				FileUtils.writeStringToFile(new File("d:/temp/cr/" + c.getName() + ".java"), clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		for (Card c : spells) {
			String clazz = String.format(template2, c.getName(), c.getName());
			try {
				FileUtils.writeStringToFile(new File("d:/temp/sp/" + c.getName() + ".java"), clazz);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean needsGL20() {
		return false;
	}

	public void resume() {
	}

	public void render() {
	}

	public void resize(int width, int height) {
	}

	public void pause() {
	}

	public void dispose() {
	}

}
