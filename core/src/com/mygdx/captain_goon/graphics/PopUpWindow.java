package com.mygdx.captain_goon.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.captain_goon.CaptainGoon;

public class PopUpWindow {

    private Stage stage;
    private Dialog dialog;
    private Skin skin;
    private BitmapFont font;

    /**
     * @param stage Stage which the window will be added to
     * @param game Used to eventually return to the main menu screen
     */
    public PopUpWindow(Stage stage, final CaptainGoon game){
        this.stage = stage;
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("star-soldier/skin/star-soldier-ui.json"));
        dialog = new Dialog("Are you sure you want to quit ?", skin){
            public void result(Object object){
                if (object.equals(true)){
                    game.setScreen(new MainMenuScreen(game));
                }
            }
        };
        dialog.button("Yes", true);
        dialog.button("No", false);
    }

    /**
     * Displays the dialog window
     */
    public void run(){
        dialog.show(stage);
    }
}
