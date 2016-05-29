package sut.game01.core.character;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.Setting;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;
import tripleplay.game.ScreenStack;


public class Loading {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public static Body body;
    private ScreenStack ss;
    private float x;
    private float y;


    public enum State{
      IDLE,WALK,JUMP
    };
    private State state = State.IDLE;

    private int e =0;

    public Loading(final float x, final float y){
        this.x=x;
        this.y=y;


        sprite = SpriteLoader.getSprite("images/loading.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite sprite) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y);
                hasLoaded = true;
                System.out.println("loading...............");
                state = State.IDLE;
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });


    }

    public Layer layer(){
        return sprite.layer();
    }


    public void update(int delta) {
        if(!hasLoaded)return;
        e+=delta;

        if(e>5){
            switch (state){
                case IDLE:
                    if(!(spriteIndex>=0&&spriteIndex<=59)){
                        spriteIndex=0;
                    }
                    break;

            }

            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;

        }
    }

}
