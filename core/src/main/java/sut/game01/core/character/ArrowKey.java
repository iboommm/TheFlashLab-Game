package sut.game01.core.character;

import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.Mouse;
import playn.core.util.Callback;
import sut.game01.core.CutScence;
import sut.game01.core.HomeScreen;
import sut.game01.core.Tools.RandomArrow;
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
    public static String key = "";
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

            public void onKeyDown(Keyboard.Event event) {
                char[] a, b;

                int x, y = 0;
                switch (event.key()) {
                    case UP:
                        key = key + "1";
                        System.out.println("key : " + key);
                        break;
                    case LEFT:
                        key = key + "2";
                        System.out.println("key : " + key);
                        break;
                    case RIGHT:
                        key = key + "3";
                        System.out.println("key : " + key);
                        break;
                    case DOWN:
                        key = key + "4";
                        System.out.println("key : " + key);
                        break;
                    case SPACE:

                        break;


                }
                System.out.println(RandomArrow.keyOut + " " + key);

                a = RandomArrow.keyOut.toCharArray();
                b = key.toCharArray();
                if (RandomArrow.keyOut.equals(key) == true) {
                    CutScence.stateKey = true;
                } else {

                    for (int i = 0; i < b.length; i++) {
                        x = b[i] - '0';

                        if (a[i] == b[i]) {
                            System.out.println("nice");
                            System.out.println("X : " + x);
                            CutScence.key.get("key_" + i).setKeyComplete(x);
                        } else {
                            for (int j = 0; j < a.length; j++) {
                                y = a[j] - '0';
                                CutScence.key.get("key_" + j).setKeyDefault(y);
                            }
                            key = "";

                        }
                    }
                }
            }
        });
    }


    public Layer layer(){
        return this.sprite.layer();
    }

    public void setKeyDefault(int key) {
        if(key == 1) {
            sprite.setSprite(0);
        }if(key == 2) {
            sprite.setSprite(2);
        }
        if(key == 3) {
            sprite.setSprite(4);
        }
        if(key == 4) {
            sprite.setSprite(6);
        }
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
