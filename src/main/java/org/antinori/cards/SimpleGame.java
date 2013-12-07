package org.antinori.cards;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;

public abstract class SimpleGame extends InputAdapter implements ApplicationListener {

	public OrthographicCamera camera;

	public Stage stage;
	public Skin skin;
	
	static org.lwjgl.input.Cursor emptyCursor;
	Texture cursor;
	int xHotspot, yHotspot;

	public SimpleGame() {
	}

	public abstract void init();

	public abstract void draw(float delta);

	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		
		stage = new Stage();
		stage.setCamera(camera);
		
		cursor = new Texture(Gdx.files.classpath("images/cursor.png"));
		xHotspot = 0;
		yHotspot = cursor.getHeight();
	
		skin = new Skin(Gdx.files.classpath("skin/uiskin.json"));

		init();

	}

	public void render() {
				
		try {
			setHWCursorVisible(false);
		} catch (LWJGLException e) {
			throw new GdxRuntimeException(e);
		}
		
		draw(Gdx.graphics.getDeltaTime());
		
	}

	public boolean keyDown(int keycode) {
		return false;
	}

	public boolean keyUp(int keycode) {
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
	}

	public void resize(int width, int height) {

	}
	
	public void setHWCursorVisible(boolean visible) throws LWJGLException {
		if (Gdx.app.getType() != ApplicationType.Desktop && Gdx.app instanceof LwjglApplication)
			return;
		if (emptyCursor == null) {
			if (Mouse.isCreated()) {
				int min = org.lwjgl.input.Cursor.getMinCursorSize();
				IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
				emptyCursor = new org.lwjgl.input.Cursor(min, min, min / 2, min / 2, 1, tmp, null);
			} else {
				throw new LWJGLException("Could not create empty cursor before Mouse object is created");
			}
		}
		if (Mouse.isInsideWindow())
			Mouse.setNativeCursor(visible ? null : emptyCursor);
	}
}
