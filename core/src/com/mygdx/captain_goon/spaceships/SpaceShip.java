package com.mygdx.captain_goon.spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.captain_goon.ammos.Ammo;
import com.mygdx.captain_goon.ammos.Bullet;
import com.mygdx.captain_goon.explosions.Explosion;

import java.util.Iterator;

public abstract class SpaceShip {

    private final static int INITIAL_SPEED = 200;
    private final static int DEFAULT_LIFE = 100;

    private Rectangle hitBox;
    private Texture image;
    private int life;
    private float speed;
    private Array<Ammo> ammunition;
    private long lastDropTime;

    private Explosion explosion;
    private Color color;

    private final static Sound audioExplosion = Gdx.audio.newSound(Gdx.files.internal("audio/glitch-arcade-explosion.wav"));
    private boolean soundPlayed;

    public SpaceShip(){
        this.setLife(DEFAULT_LIFE);
        this.setSpeed(INITIAL_SPEED);
        this.ammunition = new Array<>();
        this.color = new Color(Color.WHITE);
        soundPlayed = false;
    }

    /**
     * Creates a new ammunition and adds it to the ammunition array
     * Sets the time interval between bombs drops for enemy spaceships
     */
    public abstract void spawnAmmo();

    /**
     * Manages all the movements of the ship
     */
    public abstract void move();

    /**
     * Performs all basic actions
     */
    public void handle(Batch batch){
        this.draw(batch);
        this.move();
        this.spawnAmmo();
        this.explode(batch);
        this.moveBombs();
        this.drawAmmunition(batch);
    }

    /**
     * Makes the ship color more red
     * @param dmg The amount of red & blue subtracted is linked to the damage the ship has taken
     */
    public void tint(int dmg){
        this.color.sub(0,dmg*0.01f,dmg*0.01f,0);
    }

    /**
     * Plays explosion when the ship is destroyed and removes the ship
     */
    public void explode(Batch batch){
        if (this.isDestroyed()){
            if (!soundPlayed){
                audioExplosion.play();
                soundPlayed = true;
            }
            this.getExplosion().playExplosion(batch, this.getHitBox().getX() + this.getHitBox().getWidth()/2, this.getHitBox().getY() + this.getHitBox().getHeight()/2);
            if (this.getExplosion().isComplete())
                setHitBox(new Rectangle());
        }
    }

    /**
     * Draws the ship with its color
     * @see #tint
     */
    public void draw(Batch batch){
        if (!isDestroyed()){
            batch.setColor(this.color);
            batch.draw(this.getImage(), this.getHitBox().getX(), this.getHitBox().getY());
            batch.setColor(Color.WHITE);
        }
    }

    /**
     * Draws all ammunition
     */
    public void drawAmmunition(Batch batch){
        for (int i = 0; i < this.getAmmunition().size; i++) {
            if (!this.getAmmunition().get(i).isUsed()) {
                batch.setColor(Color.WHITE);
                batch.draw(this.getAmmunition().get(i).getImage(), this.getAmmunition().get(i).getHitBox().getX(), this.getAmmunition().get(i).getHitBox().getY());
            }
        }
    }

    /**
     * Makes the bombs fall
     * Removes the bombs if they are out of the window
     */
    public void moveBombs(){
        Iterator<Ammo> iter = this.getAmmunition().iterator();
        while (iter.hasNext()) {
            Ammo ammo = iter.next();
            if (!ammo.isUsed() && !(ammo instanceof Bullet))
            ammo.getHitBox().setY(ammo.getHitBox().getY() - 500 * Gdx.graphics.getDeltaTime());
            if (ammo.getHitBox().getY() < 0) {
                iter.remove();
            }
        }
    }

    /**
     * Detects collisions and sets the new life, tints the ship, remove the ammunition which collided and plays the ammunition explosion
     * @param ship The ship which is targeted
     */
    public void hit(SpaceShip ship, Batch batch){
        Iterator<Ammo> iter = this.getAmmunition().iterator();
        while (iter.hasNext()) {
            Ammo ammo = iter.next();
            if (ammo.getHitBox().overlaps(ship.getHitBox())) {
                if (!ammo.isUsed()) {
                    if (ship.getLife() - this.getAmmunition().first().getDamage() < 0) {
                        ship.setLife(0);
                    } else {
                        ship.setLife(ship.getLife() - this.getAmmunition().first().getDamage());
                    }
                    ship.tint(ammo.getDamage());
                }
                ammo.setUsed();
            }
            if (ammo.isUsed() && !ship.isDestroyed()) {
                ammo.getExplosion().playExplosion(batch, ammo.getHitBox().getX() + ammo.getHitBox().getWidth()/2, ammo.getHitBox().getY() + ammo.getHitBox().getHeight()/2);
                if (ammo.getExplosion().isComplete()) {
                    iter.remove();
                }
            }
        }
    }

    /**
     * Mute the explosion sound
     */
    public static void mute(){
        audioExplosion.stop();
    }

    public boolean isDestroyed(){
        return this.getLife() <= 0;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public Texture getImage() {
        return image;
    }

    public int getLife() {
        return life;
    }

    public float getSpeed() {
        return speed;
    }

    public static int getInitialSpeed() {
        return INITIAL_SPEED;
    }

    public long getLastDropTime() {
        return lastDropTime;
    }

    public Array<Ammo> getAmmunition() {
        return ammunition;
    }

    public Explosion getExplosion(){
        return explosion;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }

    public void setLastDropTime(long lastDropTime) {
        this.lastDropTime = lastDropTime;
    }
}
