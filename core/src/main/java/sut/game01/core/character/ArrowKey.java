package sut.game01.core.character;

import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.util.Callback;
import sut.game01.core.Stage1;
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

    public ArrowKey(final float x, final float y, final int mode){
        this.x=x;
        this.y=y;
        this.mode = mode;

        this.sprite = SpriteLoader.getSprite("images/json/Keyboard.json");
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
                        System.out.println("press key : " + key);
                        break;
                    case LEFT:
                        key = key + "2";
                        System.out.println("press key : " + key);
                        break;
                    case RIGHT:
                        key = key + "3";
                        System.out.println("press key : " + key);
                        break;
                    case DOWN:
                        key = key + "4";
                        System.out.println("press key : " + key);
                        break;
                    case SPACE:

                        if(Stage1.over == 0) {
                            key = key + "0";
                            System.out.println("press key : " + key);
                        }else {
                            System.out.println("Can't press space bar");
                        }
                        break;
                    default:
                        return;


                }

                a = RandomArrow.keyOut.toCharArray();
                b = key.toCharArray();
                System.out.println( RandomArrow.keyOut + " ==== " + key);
                if (RandomArrow.keyOut.equals(key) == true) {
                    Stage1.stateKey = true;
                } else {

                    for (int i = 0; i < key.length(); i++) {
                        x = b[i] - '0';

                        if (a[i] == b[i]) {
                            if(Stage1.key.size() > 0) {
                                System.out.println("nice");
                                Stage1.key.get("key_" + i).setKeyComplete(x);
                            }
                        } else {
                            System.out.println("keySize : " + Stage1.key.size());
                            if(Stage1.key.size() > 0) {
                                for (int j = 0; j < Stage1.key.size(); j++) {
                                    y = a[j] - '0';
                                    Stage1.key.get("key_" + j).setKeyDefault(y);
                                }
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
            }
            else if(key == 2) {
                sprite.setSprite(2);
            }
            else if(key == 3) {
                sprite.setSprite(4);
            }
            else if(key == 4) {
                sprite.setSprite(6);
            }
            else  {
                return;
            }
    }

    public void setKeyComplete(int key) {
            if (key == 1) {
                sprite.setSprite(1);
            } else if (key == 2) {
                sprite.setSprite(3);
            } else if (key == 3) {
                sprite.setSprite(5);
            } else if (key == 4) {
                sprite.setSprite(7);
            } else {
                return;
            }
    }
}
