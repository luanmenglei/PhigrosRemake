package me.clesip.phirunner;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import me.clesip.phirunner.chart.PhigrosChart;

public class PhiRunner extends ApplicationAdapter {
	private PhigrosChart chart;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, WIDTH, HEIGHT);
		batch = new SpriteBatch();
		debugRenderer = new ShapeRenderer();
		chart = new PhigrosChart("demo", "a");
		chart.play();
	}

	private static ShapeRenderer debugRenderer;

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		camera.update();
		chart.update(batch);

		// draw debug line
//		Gdx.gl.glLineWidth(2f);
//		debugRenderer.begin(ShapeRenderer.ShapeType.Line);
//		debugRenderer.setColor(255, 0, 0, 1);
//		debugRenderer.line(new Vector2(WIDTH / 2f - 1, HEIGHT), new Vector2(WIDTH / 2f - 1, 0));
//		debugRenderer.line(new Vector2(0, HEIGHT / 2f - 1), new Vector2(WIDTH, HEIGHT / 2f - 1));
//		debugRenderer.end();
	}

	@Override
	public void dispose () {
		chart.dispose();
	}

	public static int WIDTH = 1280;
	public static int HEIGHT = 720;
}
