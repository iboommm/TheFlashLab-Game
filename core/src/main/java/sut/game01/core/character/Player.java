package sut.game01.core.character;
import playn.core.*;
import playn.core.util.Callback;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

import java.awt.*;

public class Player {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;

    public enum State {
        IDLE, RUN, ATK
    };

    private State state = State.IDLE;

    private int e = 0;
    private int offset = 8;

    public Player(final float x,final float y) {
        PlayN.keyboard().setListener(new Keyboard.Adapter() {
            @Override
            public void onKeyUp(Keyboard.Event event) {
                if(event.key() == Key.SPACE) {
                    switch (state) {
                        case IDLE:
                            state = State.RUN;
                            break;
                        case RUN:
                            state = State.ATK;
                            break;
                        case ATK:
                            state = State.IDLE;
                            break;
                    }
                }
            }
        });
        sprite = SpriteLoader.getSprite("images/Player.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite result) {
                System.out.println("load");
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y + 13f);
                hasLoaded = true;
            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
        sprite.layer().addListener(new Pointer.Adapter() {
            @Override
            public void onPointerStart(Pointer.Event event) {
                state = State.RUN;
                spriteIndex = -1;
                e =0;

            }
        });
    }

    public Layer layer() {
        return sprite.layer();
    }

    public void update(int delta) {
        if(hasLoaded == false) return;

        e = e + delta;
        if(e > 150){
            switch (state) {
                case IDLE: offset = 0;
                    break;
                case RUN:
                    offset = 4;
                    break;
                case ATK:
                    offset=8;
                    break;
            }
            spriteIndex = offset + ((spriteIndex + 1) % 4);
            sprite.setSprite(spriteIndex);
            e=0;
        }
    }
}
