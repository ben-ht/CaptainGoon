package com.mygdx.captain_goon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.mygdx.captain_goon.CaptainGoon;
import com.mygdx.captain_goon.options.Options;
import com.mygdx.captain_goon.hud.HUD;
import com.mygdx.captain_goon.spaceships.EnemySmallSpaceShip;
import com.mygdx.captain_goon.spaceships.EnemySpaceShip;
import com.mygdx.captain_goon.spaceships.FriendlySpaceShip;
import com.mygdx.captain_goon.spaceships.SpaceShip;

public class GameScreen implements Screen {

    private final CaptainGoon game;

    public static Music audioBackground;
    private static Sound winSound;
    private static Sound loseSound;
    private boolean soundPlayed;
    private Options options;

    private ArrayMap<String, SpaceShip> allShips;

    private Texture backgroundImage;

    private OrthographicCamera camera;
    private HUD hud;
    private PopUpWindow window;

    private Stage stage;

    public GameScreen(final CaptainGoon game) {
        this.game = game;
        init();
    }

    @Override
    public void render(float delta) {
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);
        game.getBatch().begin();
        game.getBatch().draw(backgroundImage, 0, 0, 1200, 800);
        hud.draw(game.getBatch(), (FriendlySpaceShip) allShips.get("friendlyShip"), (EnemySpaceShip) allShips.get("enemyShip"));
        play();
        game.getBatch().end();
    }

    /**
     * Starts a game
     */
    public void play(){
        for (ObjectMap.Entry<String, SpaceShip> ship : allShips.entries()) {
            ship.value.handle(game.getBatch());
        }
        fight();
        options();
        endGame();
    }

    /**
     * Adds all options to a game
     */
    public void options(){
        stage.draw();
        stage.act();
        options.muteKey();
        options.muteListener(this);
    }

    /**
     * Enables collisions between all ships
     */
    public void fight(){
        for (ObjectMap.Entry<String , SpaceShip> ship : allShips.entries()) {
            if (!(ship.value instanceof FriendlySpaceShip)) {
                ship.value.hit(allShips.get("friendlyShip"), game.getBatch());
            } else {
                ship.value.hit(allShips.get("enemyShip"), game.getBatch());
                ship.value.hit(allShips.get("smallEnemyShip1"), game.getBatch());
                ship.value.hit(allShips.get("smallEnemyShip2"), game.getBatch());
            }
        }
    }

    /**
     * Creates all necessary objects to start a game
     */
    public void init(){
        camera = new OrthographicCamera();
        stage = new Stage();
        camera.setToOrtho(false, 1200, 800);
        backgroundImage = new Texture(Gdx.files.internal("backgrounds/Planets.jpg"));
        audioBackground = Gdx.audio.newMusic(Gdx.files.internal("audio/imperial-march.mp3"));
        audioBackground.setLooping(true);
        winSound = Gdx.audio.newSound(Gdx.files.internal("audio/win.wav"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("audio/game-over.wav"));
        hud = new HUD();
        options = new Options(stage, this);
        window = new PopUpWindow(stage, game);

        allShips = new ArrayMap<>();
        allShips.put("friendlyShip", new FriendlySpaceShip());
        allShips.put("enemyShip", new EnemySpaceShip());
        allShips.put("smallEnemyShip1", new EnemySmallSpaceShip((float) (Gdx.graphics.getWidth() * 0.25), 0));
        allShips.put("smallEnemyShip2", new EnemySmallSpaceShip((float) (Gdx.graphics.getWidth() * 0.75), 1));
    }

    /**
     * Ends the game and display end message accordingly
     */
    public void endGame(){
        boolean end = false;
        if (allShips.get("friendlyShip").isDestroyed() && allShips.get("friendlyShip").getExplosion().isComplete()){
            hud.displayLoseMessage(game.getBatch());
            if (!soundPlayed && !options.isMuted()){
                loseSound.play();
                soundPlayed = true;
            }
            audioBackground.stop();
            end = true;
        }
        int shipsDestroyed = 0;
        for (ObjectMap.Entry<String , SpaceShip> ship : allShips.entries()) {
            if (ship.key != "friendlyShip") {
                if (ship.value.isDestroyed() && ship.value.getExplosion().isComplete()) {
                    shipsDestroyed++;
                }
            }
        }
        if (shipsDestroyed == 3){
            hud.displayWinMessage(game.getBatch());
            if (!soundPlayed && !options.isMuted()){
                winSound.play();
                soundPlayed = true;
            }
            audioBackground.stop();
            end = true;
        }
        if (Gdx.input.justTouched() && end){
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }


    @Override
    public void show() {
        audioBackground.setVolume(0.3f);
        audioBackground.play();
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
        audioBackground.dispose();
        winSound.dispose();
        loseSound.dispose();
        backgroundImage.dispose();
    }

    public Music getAudioBackground() {
        return audioBackground;
    }

    public PopUpWindow getWindow() {
        return window;
    }
}
