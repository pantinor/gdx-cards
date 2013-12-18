package org.antinori.cards;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.antinori.cards.network.NetworkGame;
import org.antinori.cards.network.SelectHostsDialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SingleDuelChooser implements EventListener {
	
	Stage stage;
	
	TextureRegion background;
	Image bgimg;
	
	TextureRegion chooserBg;
	Image cbgimg;
	
	PlayerImage pi;
	PlayerImage oi;
	
	Cards game;
	AtomicBoolean done = new AtomicBoolean(false);
	
	final AtomicInteger playerIndex = new AtomicInteger(1);
	final AtomicInteger opponentIndex = new AtomicInteger(1);
	
	static int buttonWidth = 13;
	static int buttonHeight = 120;
	static int imgWidth = 120;

	SelectBox classesPlayer;
	SelectBox classesOpponent;
	
	private boolean selectHostsShown = false;

	public void init (Cards game) {
		this.game = game;
		
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(stage);

		background = new TextureRegion(new Texture(Gdx.files.classpath("images/splash.png")));
		bgimg = new Image(background);

		chooserBg = new TextureRegion(new Texture(Gdx.files.classpath("images/singleduel1.png")));
		cbgimg = new Image(chooserBg);

		Sprite spP = Cards.faceCardAtlas.createSprite(game.player.getPlayerInfo().getImgName());
		spP.flip(false, true);
		pi = new PlayerImage(spP, Cards.portraitramka, game.player.getPlayerInfo());
		
		Sprite spO = Cards.faceCardAtlas.createSprite(game.opponent.getPlayerInfo().getImgName());
		spO.flip(false, true);
		oi = new PlayerImage(spO, Cards.portraitramka, game.opponent.getPlayerInfo());
		
		TextButton play = new TextButton("Start", game.skin);
		play.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				
				pi.getPlayerInfo().setPlayerClass(Specializations.fromTitleString(classesPlayer.getSelection()));
				oi.getPlayerInfo().setPlayerClass(Specializations.fromTitleString(classesOpponent.getSelection()));
				
				stage.clear();
				stage.dispose();
				
				done.set(true);
				
				return true;
			}
		});
		
		
		final Cards temp = game;
		TextButton selectHostsButton = new TextButton("Connect", game.skin);
		selectHostsButton.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (selectHostsShown || Cards.NET_GAME != null) return true;
				selectHostsShown = true;

				final SelectHostsDialog window = new SelectHostsDialog("Select Remote Player on Network", temp, SingleDuelChooser.this.stage, temp.skin);
				
				TextButton close = new TextButton("X", temp.skin);
				close.addListener(new ChangeListener() {
					public void changed (ChangeEvent event, Actor actor) {
						window.setAlive(false);
						window.remove();
						selectHostsShown = false;
					}
				});
				
				window.addCloseListener(SingleDuelChooser.this);
				
				window.getButtonTable().add(close).height(window.getPadTop());
				window.setPosition(200, 100);
				stage.addActor(window);
				return true;
			}
		});
		
		TextButton startNetworkServer = new TextButton("Listen", game.skin);
		startNetworkServer.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				if (Cards.NET_GAME != null) return;
				Dialog dialog = new Dialog("Start Server", temp.skin, "dialog") {
					protected void result (Object object) {
						if (object.toString().equalsIgnoreCase("true")) {
							Cards.NET_GAME = new NetworkGame(SingleDuelChooser.this.game, true);
							Sprite sp = Cards.faceCardAtlas.createSprite("lanface");
							sp.flip(false, true);
							oi.setImg(sp);
						}
					}
				}.text("Start a network server?").button("Yes", true).button("No", false).key(Keys.ENTER, true);
				
				dialog.show(SingleDuelChooser.this.stage);
			}
		});
		
		
		classesPlayer = new SelectBox(Specializations.titles(), game.skin);
		classesOpponent = new SelectBox(Specializations.titles(), game.skin);

		int x = 300;
		int y = 253;

		cbgimg.setPosition(x, 0);
		
		Button lpb = createButton(x+=34, y, pi, playerIndex, true);
		pi.setPosition(x+=(buttonWidth+7), y);
		Button rpb = createButton(x+=imgWidth+10, y, pi, playerIndex, false);
		
		Button lob = createButton(x+=43, y, oi, opponentIndex, true);
		oi.setPosition(x+=(buttonWidth+7), y);
		Button rob = createButton(x+=imgWidth+10, y, oi, opponentIndex, false);
		
		Label lbl = new Label("Single Duel", game.skin);
		lbl.setPosition(465, 430);
		
		play.setBounds(410, 133, 60, 25);
		selectHostsButton.setBounds(475, 133, 60, 25);
		startNetworkServer.setBounds(540, 133, 60, 25);

				
			
		stage.addActor(bgimg);

		stage.addActor(cbgimg);

		
		stage.addActor(lbl);
		
		stage.addActor(lpb);
		stage.addActor(pi);
		stage.addActor(rpb);

		stage.addActor(lob);
		stage.addActor(oi);
		stage.addActor(rob);
		
		stage.addActor(classesPlayer);
		classesPlayer.setPosition(355, 194);
		classesPlayer.setHeight(25);
		classesPlayer.setWidth(123);

		stage.addActor(classesOpponent);
		classesOpponent.setPosition(548, 194);
		classesOpponent.setHeight(25);
		classesOpponent.setWidth(123);

		stage.addActor(play);
		stage.addActor(selectHostsButton);
		stage.addActor(startNetworkServer);


	}
	
	private Button createButton(int x, int y, PlayerImage img, AtomicInteger index, boolean left) {
		ButtonListener bl = new ButtonListener(img, index, left);

		Button btn = new Button(game.skin);
		btn.addListener(bl);
		
		btn.setBounds(x, y, buttonWidth, buttonHeight);
		
		return btn;	
	}
	
	class ButtonListener extends InputListener {
		PlayerImage pi;
		AtomicInteger index;
		boolean decreasing = false;
		ButtonListener(PlayerImage pi, AtomicInteger index, boolean decreasing) {
			this.pi = pi;
			this.index = index;
			this.decreasing = decreasing;
		}
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
			if (decreasing) {
				index.decrementAndGet();
				if (index.get() < 1) index.set(1);
			} else {
				index.incrementAndGet();
				if (index.get() > 77) index.set(77);
			}
			Sprite sp = Cards.faceCardAtlas.createSprite("face"+index);
			sp.flip(false, true);
			pi.setImg(sp);
			pi.getPlayerInfo().setImgName("face"+index);
			return true;
		}
	}
	
	public void draw(float delta) {
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	public boolean handle(Event event) {
		Sprite sp = Cards.faceCardAtlas.createSprite("lanface");
		sp.flip(false, true);
		oi.setImg(sp);
		return false;
	}
	
//	private Image getRectangleImage(int width, int height) {
//
//		Pixmap p = new Pixmap(width, height, Pixmap.Format.RGBA8888);
//		p.setColor(Color.BLACK);
//		p.fill();
//		
//		Image img = new Image(new TextureRegion(new Texture(p), width, height));
//		
//		return img; 
//	}




}
