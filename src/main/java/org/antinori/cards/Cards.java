package org.antinori.cards;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.color;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.removeActor;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.antinori.cards.network.NetworkGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class Cards extends SimpleGame {
	
	
	public static NetworkGame NET_GAME;

	public static TextureAtlas smallCardAtlas;
	public static TextureAtlas smallTGACardAtlas;
	public static TextureAtlas largeCardAtlas;
	public static TextureAtlas largeTGACardAtlas;
	public static TextureAtlas faceCardAtlas;
	
	public static Texture ramka;
	public static Texture spellramka;

	static Texture portraitramka;
	static Texture ramkabig;
	static Texture ramkabigspell;
	static Texture slotTexture;

	public static BitmapFont defaultFont;
	public static BitmapFont greenfont;
	public static BitmapFont customFont;
	public static Label.LabelStyle whiteStyle;
	public static Label.LabelStyle redStyle;
	public static Label.LabelStyle greenStyle;

	
	public static int SCREEN_WIDTH = 1024;
	public static int SCREEN_HEIGHT = 768;
		
	static Texture background;
	static Sprite sprBg;
	
	public PlayerImage player;
	public PlayerImage opponent;
	
	Label playerInfoLabel;
	Label opptInfoLabel;
	
	Button shuffleCardsButton;
	ImageButton skipTurnButton;
	Button showOpptCardsButton;
	public static LogScrollPane logScrollPane;
	
	Label[] topStrengthLabels = new Label[5];
	Label[] bottomStrengthLabels = new Label[5];


	public CardSetup cs;
	CardDescriptionImage cdi;
	
	public Music bgm;
	public Music attackSound;
	public Music magicSound;

	SpriteBatch batch;
	
	public MouseOverCardListener li;
	public ShowDescriptionListener sdl;
	public SlotListener sl;
	
	SingleDuelChooser chooser;
	
	private CardImage selectedCard;
	private boolean activeTurn = false;
	
	private boolean gameOver = false;
	private boolean opptCardsShown = false;
	

	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Cards";
		cfg.useGL20 = true;
		cfg.width = SCREEN_WIDTH;
		cfg.height = SCREEN_HEIGHT;
		new LwjglApplication(new Cards(), cfg);

	}

	@Override
	public void init() {
				
		cs = new CardSetup();
		cs.parseCards();
		
		batch = new SpriteBatch();
		
//		bgm = Gdx.audio.newMusic(Gdx.files.classpath("audio/combat3.ogg"));
//		bgm.setLooping(true);
//		bgm.setVolume(0.2f);
//		bgm.play();
		
		attackSound = Gdx.audio.newMusic(Gdx.files.classpath("audio/attack.ogg"));
		attackSound.setVolume(0.3f);
		magicSound = Gdx.audio.newMusic(Gdx.files.classpath("audio/magic.ogg"));
		magicSound.setVolume(0.3f);
		
		ramka = new Texture(Gdx.files.classpath("images/ramka.png"));
		spellramka = new Texture(Gdx.files.classpath("images/ramkaspell.png"));
		portraitramka = new Texture(Gdx.files.classpath("images/portraitramka.png"));
		ramkabig = new Texture(Gdx.files.classpath("images/ramkabig.png"));
		ramkabigspell = new Texture(Gdx.files.classpath("images/ramkabigspell.png"));
		slotTexture = new Texture(Gdx.files.classpath("images/slot.png"));

		smallCardAtlas = new TextureAtlas(Gdx.files.classpath("images/smallCardsPack.txt"), true);
		largeCardAtlas = new TextureAtlas(Gdx.files.classpath("images/largeCardsPack.txt"), true);
		
		smallTGACardAtlas = new TextureAtlas(Gdx.files.classpath("images/smallTGACardsPack.txt"), true);
		largeTGACardAtlas = new TextureAtlas(Gdx.files.classpath("images/largeTGACardsPack.txt"), true);

		faceCardAtlas = new TextureAtlas(Gdx.files.classpath("images/faceCardsPack.txt"), true);
			
		background = new Texture(Gdx.files.classpath("images/background.jpg"));
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		sprBg = new Sprite(background, 0, 0, background.getWidth(), background.getHeight());
		
		player = new PlayerImage(null, portraitramka, greenfont, new Player(), 80, ydown(300));
		opponent = new PlayerImage(null, portraitramka, greenfont, new Player(), 80, ydown(125));
		
		defaultFont = new BitmapFont(Gdx.files.classpath("default.fnt"), false);
		greenfont = new BitmapFont(Gdx.files.classpath("fonts/BellMT_16.fnt"), false);
		greenfont.setColor(Color.valueOf("105410"));
		customFont = new BitmapFont(Gdx.files.classpath("fonts/BellMT_16.fnt"),false);
		customFont.setColor(Color.BLACK);

		whiteStyle = new Label.LabelStyle(defaultFont, Color.WHITE);
		redStyle = new Label.LabelStyle(customFont, Color.RED);
		greenStyle = new Label.LabelStyle(customFont, Color.GREEN);
		
		playerInfoLabel = new Label(Specializations.Cleric.getTitle(), whiteStyle);
		opptInfoLabel = new Label(Specializations.Cleric.getTitle(), whiteStyle);
		playerInfoLabel.setPosition(80 + 10 + 120, ydown(300));
		opptInfoLabel.setPosition(80 + 10 + 120, ydown(30));
		
		
		ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.classpath("images/endturnbutton.png")));
		style.imageUp = new TextureRegionDrawable(tr);
		style.imageDown = new TextureRegionDrawable(tr);
		
		
		
		showOpptCardsButton = new Button(skin);
		showOpptCardsButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (opptCardsShown) return true;
				opptCardsShown = true;

				String title = getPlayerDescription(opponent.getPlayerInfo());
				final OpponentCardWindow window = new OpponentCardWindow(title, opponent.getPlayerInfo(), Cards.this, skin);
				
				TextButton close = new TextButton("X", skin);
				close.addListener(new ChangeListener() {
					public void changed (ChangeEvent event, Actor actor) {
						window.remove();
						opptCardsShown = false;
					}
				});
				window.getButtonTable().add(close).height(window.getPadTop());
				window.setPosition(200, 100);
				stage.addActor(window);
				return true;
			}
		});
		showOpptCardsButton.setBounds(10, ydown(50), 50, 50);
		stage.addActor(showOpptCardsButton);
		
		
		skipTurnButton = new ImageButton(style);
		skipTurnButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (gameOver) return true;
				BattleRoundThread t = new BattleRoundThread(Cards.this, player, opponent);
				t.start();
				return true;
			}
		});
		skipTurnButton.setBounds(10, ydown(110), 50, 50);
		stage.addActor(skipTurnButton);
		
		
		shuffleCardsButton = new Button(skin);
		shuffleCardsButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				try {
					initializePlayerCards(player.getPlayerInfo(), true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		});
		shuffleCardsButton.setBounds(10, ydown(170), 50, 50);
		stage.addActor(shuffleCardsButton);
		
		
		int x = 420;
		int y = ydown(337);
		int incr = 103;
		for (int i=0;i<5;i++) {
			bottomStrengthLabels[i] = new Label(getPlayerStrength(player.playerInfo, CardType.OTHER), whiteStyle);
			bottomStrengthLabels[i].setPosition(x+=incr, y);
			stage.addActor(bottomStrengthLabels[i]);
		}
		x = 420;
		y = ydown(25);
		for (int i=0;i<5;i++) {
			topStrengthLabels[i] = new Label(getPlayerStrength(opponent.playerInfo, CardType.OTHER), whiteStyle);
			topStrengthLabels[i].setPosition(x+=incr, y);
			stage.addActor(topStrengthLabels[i]);
		}


		cdi = new CardDescriptionImage(20, ydown(512));
		cdi.setFont(greenfont);
		
		logScrollPane = new LogScrollPane(skin);
		logScrollPane.setBounds(24, 36, 451, 173);
		
		stage.addActor(player);
		stage.addActor(opponent);
		stage.addActor(playerInfoLabel);
		stage.addActor(opptInfoLabel);
		stage.addActor(cdi);
		stage.addActor(logScrollPane);

		
		sl = new SlotListener();
		li = new MouseOverCardListener();
		sdl = new ShowDescriptionListener();
		
		addSlotImages(opponent, 330, ydown(170), false);
		addSlotImages(player, 330, ydown(290), true);
		
		chooser = new SingleDuelChooser();
		chooser.init(this);  
						
	}
	
	public static int ydown(int y) {
		return SCREEN_HEIGHT - y;
	}


	@Override
	public void draw(float delta) {
		
		if (chooser != null) {
			
			if (!chooser.done.get()) {
				chooser.draw(delta);
			} else {
				
				Thread t = new Thread(new InitializeGameThread());
				t.start();
				
				Gdx.input.setInputProcessor(new InputMultiplexer(this, stage));
			}
			
		} else {
		
			batch.begin();
			sprBg.draw(batch);
			if (NET_GAME != null) {
				defaultFont.draw(batch, (NET_GAME.isMyTurn()?"Your Turn":"Their Turn"), 65, ydown(312));
				customFont.draw(batch, NET_GAME.getConnectedHost(), 274, ydown(315));
			}
			batch.end();
			
			Player pInfo = player.getPlayerInfo();
			Player oInfo = opponent.getPlayerInfo();
			
			playerInfoLabel.setText(getPlayerDescription(pInfo));
			opptInfoLabel.setText(getPlayerDescription(oInfo));
			
			CardType[] types = {CardType.FIRE, CardType.AIR, CardType.WATER, CardType.EARTH, opponent.getPlayerInfo().getPlayerClass().getType()};
			for (int i=0;i<5;i++) {
				setStrengthLabel(topStrengthLabels[i], oInfo, types[i]);
			}
			types[4] = player.getPlayerInfo().getPlayerClass().getType();
			for (int i=0;i<5;i++) {
				setStrengthLabel(bottomStrengthLabels[i], pInfo, types[i]);
			}

			stage.act(Gdx.graphics.getDeltaTime());
			stage.draw();
	
		}

		
		batch.begin();
		int x = Gdx.input.getX();
		int y = Gdx.input.getY();
		batch.draw(cursor, x - xHotspot, Gdx.graphics.getHeight() - y - 1 - yHotspot);
		batch.end();

	}
	
	class InitializeGameThread implements Runnable {
		public void run() {
			try {
				initialize();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initialize() throws Exception {
	
		synchronized(this) {
		
			if (chooser == null) return; 

			player.setImg(chooser.pi.getImg());
			player.setPlayerInfo(chooser.pi.getPlayerInfo());

			opponent.setImg(chooser.oi.getImg());
			opponent.setPlayerInfo(chooser.oi.getPlayerInfo());
			
			chooser = null;
		}
		
		initializePlayerCards(player.getPlayerInfo(), true);
		initializePlayerCards(opponent.getPlayerInfo(), false);
		
		if (NET_GAME != null) {
			//block until handshake
			NET_GAME.waitForPlayerInitHandshake();
			
			//if we are client then the server side takes the first turn
			if (!NET_GAME.isServer()) {
				//block here until far side has taken turn
				NET_GAME.read();
			}
		}
		
		//do this again just to be sure
		for (CardType type : Player.TYPES) {
			player.getPlayerInfo().enableDisableCards(type);
			opponent.getPlayerInfo().enableDisableCards(type);
		}
		
	}
	
	public void initializePlayerCards(Player player, boolean visible) throws Exception {
		
		selectedCard = null;
		
		int x = 405;
		int y = ydown(328);
		
		CardType[] types = {CardType.FIRE, CardType.AIR, CardType.WATER, CardType.EARTH, player.getPlayerClass().getType()};
		
		for (CardType type : types) {
			
			if (player.getCards(type) != null && player.getCards(type).size() > 0) {
				for (CardImage ci : player.getCards(type)) {
					ci.remove();
				}
			}
			
			List<CardImage> v1 = cs.getCardImagesByType(smallCardAtlas, smallTGACardAtlas,  type, 4);
			x += 104;
			addVerticalGroupCards(x,y,v1, player, type, visible);
			player.setCards(type, v1);
			
			player.enableDisableCards(type);
		}

	}
	
	public void setStrengthLabel(Label label, Player pl, CardType type) {
		label.setText(getPlayerStrength(pl, type));
	}
	
	public String getPlayerDescription(Player pl) {
		return pl.getPlayerClass().getTitle() + " Life: " + pl.getLife();
	}
	
	public String getPlayerStrength(Player pl, CardType type) {
		int str = pl==null?0:pl.getStrength(type);
		return type.getTitle() + ":  " + str;
	}
	
	
	public void addVerticalGroupCards(int x, int y, List<CardImage> cards, Player player, CardType type, boolean addToStage) {

		CardImage.sort(cards);
		
		float x1 = x;
		float y1 = y;
		int spacing = 6;
		
		for (CardImage ci : cards) {
			
			if (!addToStage) {
				x1 = 0;
				y1 = ydown(0);
			}
			
			ci.setFont(greenfont);
			ci.setFrame(ci.getCard().isSpell()?spellramka:ramka);
			ci.addListener(sdl);

			y1 -= (spacing + ci.getFrame().getHeight());
			ci.setBounds(x1, y1, ci.getFrame().getWidth(), ci.getFrame().getHeight());
			
			if (addToStage) {
				ci.addListener(li);
				stage.addActor(ci);
			}
		}
		
	}
	
	public void addSlotImages(PlayerImage pi, int x, int y, boolean bottom) {
		float x1 = x;
		int spacing = 5;
		for (int i=0;i<6;i++) {
			
			SlotImage s = new SlotImage(slotTexture, i, bottom);
			s.setBounds(x1, y, s.getWidth(), s.getHeight());
			x1 += (spacing + s.getWidth());
			s.addListener(sl);

			stage.addActor(s);
			
			pi.getSlots()[i] = s;
			
		}
	}
	
	class MouseOverCardListener extends InputListener {
		
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
			if (gameOver) return true;

			Actor actor = event.getListenerActor();
			if (actor instanceof CardImage) {
				
				selectedCard = (CardImage)actor;
				
				clearHighlights();
				
				if (canStartMyTurn() && selectedCard.getCard().isSpell() && selectedCard.isEnabled()) {
					
					if (selectedCard.getCard().isTargetable()) {
						
						CardImage[] cards = selectedCard.getCard().isDamagingSpell()?opponent.getSlotCards():player.getSlotCards();
						
						//highlight targets
						for (CardImage ci : cards) {
							if (ci != null) {
								ci.setHighlighted(true);
								ci.addAction(forever(sequence(color(Color.GREEN, .75f), color(Color.WHITE, .75f))));
							}
						}
					} else {
						//cast the spell
						BattleRoundThread t = new BattleRoundThread(Cards.this, player, opponent, selectedCard);
						t.start();
					}
				}
			}
			return true;
		}

	}
	
	public class TargetedCardListener extends InputListener {	
		private int index;
		private String targetedCardOwnerId;
		public TargetedCardListener(String id, int index) {
			this.targetedCardOwnerId = id;
			this.index = index;
		}
		public int getIndex() {
			return index;
		}
		public String getOwnerId() {
			return targetedCardOwnerId;
		}
		
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			Actor actor = event.getListenerActor();
			if (actor instanceof CardImage) {
				CardImage targetedCard = (CardImage)actor;
				
				if (!targetedCard.isHighlighted) return true;
				
				clearHighlights();
				
				//cast the spell to target
				BattleRoundThread t = new BattleRoundThread(Cards.this, player, opponent, selectedCard, targetedCard, targetedCardOwnerId, index);
				t.start();
			}
			return true;
		}
	}
	
	class ShowDescriptionListener extends InputListener {
        		
		public void enter (InputEvent event, float x, float y, int pointer, Actor a) {
			Actor actor = event.getListenerActor();
			
			if (actor == null) return;
			
			if (actor instanceof CardImage) {
				CardImage ci = (CardImage) actor;
				Card card = ci.getCard();
				
				Sprite sp = largeCardAtlas.createSprite(card.getName().toLowerCase());
				if (sp == null) {
					sp = largeTGACardAtlas.createSprite(card.getName().toLowerCase());
	        		if (sp != null) sp.flip(false, true); //tga files need to be flipped twice
				}
				if (sp == null) {
					cdi.setImg(null);
					return;
				}
				
	    		sp.flip(false, true);

				
				cdi.setImg(sp);
				cdi.setFrame(ci.getCard().isSpell()?ramkabigspell:ramkabig);
				cdi.setCard(card);
			}
		}
		
		public void exit (InputEvent event, float x, float y, int pointer, Actor a) {
			cdi.setImg(null);
		}
	}
	
	public void clearHighlights() {
		for (CardImage ci : player.getSlotCards()) {
			if (ci != null) {
				ci.setHighlighted(false);
				ci.clearActions();
				ci.setColor(Color.WHITE);
			}
		}
		for (CardImage ci : opponent.getSlotCards()) {
			if (ci != null) {
				ci.setHighlighted(false);
				ci.clearActions();
				ci.setColor(Color.WHITE);
			}
		}
	}
	
	class SlotListener extends InputListener {
		
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
			if (gameOver) return true;
			
			Actor actor = event.getListenerActor();

			if (actor instanceof SlotImage) {
				final SlotImage si = (SlotImage) actor;

				if (canStartMyTurn() && selectedCard != null && selectedCard.isEnabled() && si.isBottomSlots()) {
					
					if (!selectedCard.getCard().isSpell()) {
						
						final CardImage clone = selectedCard.clone();
						
						stage.addActor(clone);
						clone.addListener(new TargetedCardListener(player.getPlayerInfo().getId(), si.getIndex()));
						clone.addListener(sdl);

						CardImage[] imgs = player.getSlotCards();
						imgs[si.getIndex()] = clone;
						
						SlotImage[] slots = player.getSlots();
						slots[si.getIndex()].setOccupied(true);
						
						Creature summonedCreature = CreatureFactory.getCreatureClass(clone.getCard().getName(), Cards.this, clone.getCard(), clone, si.getIndex(), player, opponent);
						clone.setCreature(summonedCreature);
						
						clone.addAction(sequence(moveTo(si.getX() + 5, si.getY() + 26, 1.0f), new Action() {
							public boolean act(float delta) {
								BattleRoundThread t = new BattleRoundThread(Cards.this, player, opponent, clone, si.getIndex());
								t.start();
								return true;
							}
						}));						

					} else {
						//nothing
					}
				}
			}
			return true;
		}
	}
	
	
	public void animateDamageText(int value, float sx, float sy, float dx, float dy) {
		if (redStyle == null) return;
		Label label = new Label("- "+value, redStyle);
		label.setPosition(sx, sy); 
		stage.addActor(label);
		label.addAction(sequence(moveTo(dx, dy, 3),fadeOut(1),removeActor(label)));
	}
	
	public void animateHealingText(int value, float sx, float sy, float dx, float dy) {
		if (greenStyle == null) return;
		Label label = new Label("+ "+value, greenStyle);
		label.setPosition(sx, sy); 
		stage.addActor(label);
		label.addAction(sequence(moveTo(dx, dy, 3),fadeOut(1),removeActor(label)));
	}
	
	public void moveCardActorOnBattle(CardImage ci, PlayerImage pi) {
		
		if (ci == null || pi == null) {
			System.err.println("moveCardActorOnBattle: null ci or pi");
			return;
		}

		if (attackSound != null) attackSound.play();
		
		if (pi.getSlots()[0] == null) return;
		
		final AtomicBoolean doneBattle = new AtomicBoolean(false);
		
		boolean isBottom = pi.getSlots()[0].isBottomSlots();
		
		ci.addAction(sequence(moveBy(0, isBottom?20:-20, 0.5f), moveBy(0, isBottom?-20:20, 0.5f), new Action() {
			public boolean act(float delta) {
				doneBattle.set(true);
				return true;
			}
		}));
		
		//wait for battle action to end
		while(!doneBattle.get()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
				
	}
	
	public void moveCardActorOnMagic(CardImage ci, PlayerImage pi) {
		
		if (ci == null || pi == null) {
			System.err.println("moveCardActorOnMagic: null ci or pi");
			return;
		}

		magicSound.play();
		
		final AtomicBoolean done = new AtomicBoolean(false);
		
		boolean isBottom = pi.getSlots()[0].isBottomSlots();
    
		pi.addAction(sequence(moveBy(0, isBottom?-20:20, 0.5f), moveBy(0, isBottom?20:-20, 0.5f), new Action() {
			public boolean act(float delta) {
				done.set(true);
				return true;
			}
		}));
		
		//wait for spell action to end
		while(!done.get()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
				
	}
	
	


	public CardImage getSelectedCard() {
		return selectedCard;
	}

	
	public void handleGameOver() {
		gameOver = true;
		
		Cards.logScrollPane.add("Game Over");
	}
	
	public PlayerImage getPlayerImage(String id) throws Exception {
		
		PlayerImage ret = null;
		if (player.getPlayerInfo().getId().equalsIgnoreCase(id)) 
			ret = player;
		
		if (opponent.getPlayerInfo().getId().equalsIgnoreCase(id)) 
			ret = opponent;
		
		if (ret == null)
			throw new Exception("Could not find player with id: " + id);
		
		return ret;
	}
	
	public void setOpposingPlayerId(String id) {
		opponent.getPlayerInfo().setId(id);
	}
	
	public PlayerImage getOpposingPlayerImage(String id) throws Exception {
		
		PlayerImage ret = null;
		if (player.getPlayerInfo().getId().equalsIgnoreCase(id)) 
			ret = opponent;
		
		if (opponent.getPlayerInfo().getId().equalsIgnoreCase(id)) 
			ret = player;
		
		if (ret == null)
			throw new Exception("Could not find player with id: " + id);
		
		return ret;
	}
	
	
	public void startTurn() {
		activeTurn = true;
	}

	public void finishTurn() {
		this.activeTurn = false;
		this.selectedCard = null;
	}
	
	public boolean canStartMyTurn() {
		
		if (NET_GAME != null && NET_GAME.isConnected()) {
			return NET_GAME.isMyTurn();
		}
			
		return !activeTurn;
		
	}


}
