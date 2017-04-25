package by.hryharenka.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import by.hryharenka.game.screens.PlayScreen;

public class ZombieGame extends Game {
	public static final int V_WIDTH = 600;
	public static final int V_HEIGHT = 600;
	public SpriteBatch batch;

	@Override
	public void create () {
		Gdx.graphics.setResizable(false);
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
	}
}
