package com.mygdx.captain_goon.options;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.captain_goon.CaptainGoon;
import com.mygdx.captain_goon.graphics.GameScreen;
import com.mygdx.captain_goon.graphics.MainMenuScreen;
import com.mygdx.captain_goon.spaceships.SpaceShip;

public class Options {
    private Stage stage;
    private ImageButton muteButton;
    private boolean mute;
    private ImageButton quitButton;
    private GameScreen gameScreen;

    public Options(Stage stage, GameScreen gameScreen){
        this.stage = stage;
        this.gameScreen = gameScreen;
        mute = true;
        initMuteButton();
        initQuitButton();
    }

    /**
     * Create a button which allows to mute all sounds
     */
    public void initMuteButton(){
        muteButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("icons/mute.png"))));
        muteButton.getStyle().imageChecked = new TextureRegionDrawable(new Texture(Gdx.files.internal("icons/volume.png")));
        muteButton.setSize(30, 30);
        muteButton.setPosition(5, Gdx.graphics.getHeight() - muteButton.getHeight()-5);
        stage.addActor(muteButton);
        muteButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mute = !mute;
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Create a button which allows going back to menu
     */
    public void initQuitButton(){
        quitButton = new ImageButton(new TextureRegionDrawable(new Texture(Gdx.files.internal("icons/cancel.png"))));
        quitButton.setSize(30,30);
        quitButton.setPosition(Gdx.graphics.getWidth() - quitButton.getWidth() - 5, Gdx.graphics.getHeight() - quitButton.getHeight() - 5);
        stage.addActor(quitButton);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.getWindow().run();
            }
        });
    }

    /**
     * Listen if the M key is pressed
     */
    public void muteKey(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)){
            muteButton.setChecked(!muteButton.isChecked());
        }
    }

    /**
     * Mutes or unmutes all sounds
     */
    public void muteListener(GameScreen screen){
        if (isMuted()){
            screen.getAudioBackground().pause();
            SpaceShip.mute();
        } else {
            screen.getAudioBackground().play();
        }
    }


    public boolean isMuted() {
        return mute;
    }

}
