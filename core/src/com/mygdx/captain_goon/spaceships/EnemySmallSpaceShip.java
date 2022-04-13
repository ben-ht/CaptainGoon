package com.mygdx.captain_goon.spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.captain_goon.ammos.Ammo;
import com.mygdx.captain_goon.ammos.SmallBomb;
import com.mygdx.captain_goon.explosions.EnemyShipDestroyedExplosion;

public class EnemySmallSpaceShip extends SpaceShip{
    private final int SIZE = 100;
    private int num;
    private final static Texture IMAGE = new Texture(Gdx.files.internal("sprites/ships/roundysh_small.png"));

    /**
     *
     * @param X position on the game batch
     * @param num Is used to set the initial speed
     */
    public EnemySmallSpaceShip(float x, int num) {
        this.setHitBox(new Rectangle(x-SIZE/2, Gdx.graphics.getHeight()-SIZE-80, SIZE, SIZE));
        this.setImage(IMAGE);
        this.setExplosion(new EnemyShipDestroyedExplosion());

        this.num = num;
        if (this.num == 0){
            setSpeed(getInitialSpeed());
        } else {
            setSpeed(-getInitialSpeed());
        }

    }

    /**
     * @see SpaceShip#spawnAmmo()
     */
    public void spawnAmmo() {
        if (!isDestroyed()) {
            if (TimeUtils.nanoTime() - this.getLastDropTime() > 1100000000) {
                this.createAmmo();
            }
        }
    }

    /**
     * Creates a new ammunition and adds it to the ammunition array
     */
    public void createAmmo(){
        Ammo newAmmo = new SmallBomb(this.getHitBox().getX() + this.getHitBox().getWidth()/2, this.getHitBox().getY());
        this.getAmmunition().add(newAmmo);
        setLastDropTime(TimeUtils.nanoTime());
    }

    /**
     * @see SpaceShip#move()
     */
    public void move(){
        if (!isDestroyed()) {
            if (num == 0) {
                if (this.getHitBox().getX() < 0) {
                    this.setSpeed(SpaceShip.getInitialSpeed());
                }
                if (this.getHitBox().getX() + this.getHitBox().getWidth() / 2 > Gdx.graphics.getWidth() / 2) {
                    this.setSpeed(-SpaceShip.getInitialSpeed());
                }
            } else {
                if (this.getHitBox().getX() > Gdx.graphics.getWidth() - this.getHitBox().getWidth()) {
                    this.setSpeed(-SpaceShip.getInitialSpeed());
                }
                if (this.getHitBox().getX() + this.getHitBox().getWidth() / 2 < Gdx.graphics.getWidth() / 2) {
                    this.setSpeed(SpaceShip.getInitialSpeed());
                }
            }
            this.getHitBox().setX(this.getHitBox().getX() + this.getSpeed() * Gdx.graphics.getDeltaTime());
        }
    }
}
