package com.mygdx.captain_goon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.captain_goon.CaptainGoon;
import com.mygdx.captain_goon.options.GifDecoder;

public class MainMenuScreen implements Screen {

    private final CaptainGoon game;
    private OrthographicCamera camera;
    private Animation<TextureRegion> animation;
    private float elapsedTime;

    public MainMenuScreen(CaptainGoon game){
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("backgrounds/retrowave.gif").read());
    }


    @Override
    public void render(float delta) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(animation.getKeyFrame(elapsedTime), 0, 0, 1200, 800);
        game.getFont().getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        game.getFont().getData().setScale(2f);

        GlyphLayout layout1 = new GlyphLayout(game.getFont(),"Captain Goon");
        float font1_X =(Gdx.graphics.getWidth()-layout1.width)/2;
        float font1_Y =(Gdx.graphics.getHeight()-layout1.height)/2;
        game.getFont().draw(game.getBatch(),layout1, font1_X, font1_Y+50);

        BitmapFont blinkingFont = new BitmapFont(Gdx.files.internal("fonts/font-title-export.fnt"));
        float timer = (float) (1.3*elapsedTime % 2*(Math.PI));
        float blink = (float) Math.sin(timer);
        blinkingFont.setColor(1,1,1, blink);


        GlyphLayout layout2 = new GlyphLayout(blinkingFont, "Tap anywhere to begin");
        float font2_X =(Gdx.graphics.getWidth()-layout2.width)/2;
        float font2_Y =(Gdx.graphics.getHeight()-layout2.height)/2;

        blinkingFont.draw(game.getBatch(), layout2, font2_X, font2_Y-50);
        game.getBatch().end();

        if (Gdx.input.justTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }


    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
