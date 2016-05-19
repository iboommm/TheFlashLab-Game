package sut.game01.core.character;

import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.Mouse;
import playn.core.util.Callback;
import sut.game01.core.HomeScreen;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;

import static playn.core.PlayN.keyboard;


public class ArrowKey {
    private Sprite sprite;
    public int spriteIndex = 0;
    private boolean hasLoaded = false;
    private float x;
    private float y;
    private int mode;
    String name;

    public ArrowKey(final float x, final float y, final int mode){
        this.x=x;
        this.y=y;
        this.mode = mode;

        this.sprite = SpriteLoader.getSprite("images/Keyboard.json");
        this.sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite sprite) {

                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y);
                hasLoaded = true;

                if(mode == 1){
                    sprite.setSprite(0);
                }else if(mode == 2) {
                    sprite.setSprite(2);
                }else if(mode == 3) {
                    sprite.setSprite(4);
                }else if(mode == 4) {
                    sprite.setSprite(6);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        keyboard().setListener(new Keyboard.Adapter(){
            public void onKeyDown(Keyboard.Event event){
                switch (event.key()){
                    case UP:
                        if(mode == 1) {
                            sprite.setSprite(1);
                        }
                        break;
                    case LEFT:
                        if(mode == 2) {
                            sprite.setSprite(3);
                        }
                        break;
                    case RIGHT:
                        if(mode == 3) {
                            sprite.setSprite(5);
                        }
                        break;
                    case DOWN:
                        if(mode == 4) {
                            sprite.setSprite(7);
                        }
                        break;
                }
            }
        });
    }


    public Layer layer(){
        return this.sprite.layer();
    }

    public void setKeyComplete(int key) {
                if(key == 1) {
                    sprite.setSprite(1);
                }else if(key == 2) {
                    sprite.setSprite(3);
                }
                if(key == 3) {
                    sprite.setSprite(5);
                }
                if(key == 4) {
                    sprite.setSprite(7);
                }
    }
}
