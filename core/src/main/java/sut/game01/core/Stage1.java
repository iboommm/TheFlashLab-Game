package sut.game01.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.Tools.RandomArrow;
import sut.game01.core.Tools.ToolsG;
import sut.game01.core.character.*;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.util.Colors;

import java.util.HashMap;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Stage1 extends UIScreen {

    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private Player player;
    Image btSkip;
    ImageLayer skipLayer;

    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;

    private Image x1Image,x2Image,x3Image,x4Image,failedImage,darkImage;
    private ImageLayer x1Layer,x2Layer,x3Layer,x4Layer,failedLayer,darkLayer;


    private Image bonusImage;
    private ImageLayer bonusLayer;

    ToolsG toolsg = new ToolsG();

    public static boolean stateKey = false;
    public static HashMap<String,ArrowKey> key = new HashMap<String,ArrowKey>();
    public Layer a,b,lifeText,bonusCombo;
    public int score = 0;
    public static int over = 0;
    float fadeBonus=0.00f;

    int life = 3,attack = 0,remaining=10,track=0,fadeStatus=0;
    int level = 1;
    int bonus = 0;

    int hour = 8,minute=0,e=0,time_pause=0;


    ArrowKey ar;
    Enemy enemy;

    private Boolean showDebugDraw = false;
    private DebugDrawBox2D debugDraw;


    private float alphaTest = 0;

    public Stage1(final ScreenStack ss){

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height-(float)4.2),new Vec2(width,height-(float)4.2));
        ground.createFixture(groundShape,0.0f);

        player = new Player(world,80f,290f);
        enemy  = new Enemy(world,700f,290f);


        btSkip = assets().getImage("images/skip.png");
        skipLayer = graphics().createImageLayer(btSkip);
        skipLayer.setTranslation(390f,0f);

        bgImage = assets().getImage("images/map.png");
        bg = graphics().createImageLayer(bgImage);

        x1Image = assets().getImage("images/X1.png");
        x1Layer = graphics().createImageLayer(x1Image);
        x2Image = assets().getImage("images/X2.png");
        x2Layer = graphics().createImageLayer(x2Image);
        x3Image = assets().getImage("images/X3.png");
        x3Layer = graphics().createImageLayer(x3Image);
        x4Image = assets().getImage("images/X4.png");
        x4Layer = graphics().createImageLayer(x4Image);
        failedImage = assets().getImage("images/f.png");
        failedLayer = graphics().createImageLayer(failedImage);
        darkImage = assets().getImage("images/darkFade.jpg");
        darkLayer = graphics().createImageLayer(darkImage);
        x1Layer.setTranslation(120f,140f);
        x2Layer.setTranslation(120f,140f);
        x3Layer.setTranslation(120f,140f);
        x4Layer.setTranslation(120f,140f);
        failedLayer.setTranslation(120f,140f);


        failedLayer.setAlpha(0);
        x1Layer.setAlpha(0);
        x2Layer.setAlpha(0);
        x3Layer.setAlpha(0);
        x4Layer.setAlpha(0);


        bonusImage = assets().getImage("images/bonus_gate.png");
        bonusLayer = graphics().createImageLayer(bonusImage);

        skipLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(new Setting(ss));
            }
        });


        this.ss = ss;
        if(hour >= 10 && minute >= 10) {
            a = toolsg.genText("Time : " + hour + ":" + minute,30, Colors.BLACK, 100, 10);
        }else if(hour < 10 && minute > 10) {
            a = toolsg.genText("Time : 0" + hour + ":" + minute,30, Colors.BLACK, 100, 10);
        }else if(hour >= 10 && minute < 10) {
            a = toolsg.genText("Time : " + hour + ":" + minute + "0",30, Colors.BLACK, 100, 10);
        }else {
            a = toolsg.genText("Time : 0" + hour + ":" + minute + "0", 30, Colors.BLACK, 100, 10);
        }
        b = toolsg.genText("คะแนน : " + score,30, Colors.BLACK,400,20);
        lifeText = toolsg.genText("ชีวิต : " + life,20, Colors.BLACK,120,40);
        bonusCombo = toolsg.genText("Remaining : " + remaining,20, Colors.BLACK,400,50);
        ar = new ArrowKey(100f,100f,1);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();


                if((a == player.body && b == enemy.body) || (b == player.body && a == enemy.body)) {
                    life -= 1;
                    layer.remove(lifeText);
                    lifeText = toolsg.genText("ชีวิต : " + life,20, Colors.BLACK,120,40);
                    layer.add(lifeText);
                    attack = 1;
                }


                //              b.applyForce(new Vec2(5000f, -1000f), b.getPosition());  // ผัลกธรรมดา
                //             b.applyLinearImpulse(new Vec2(100f,0),b.getPosition());  // ยิง

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

    }



    @Override
    public void wasShown(){




        super.wasShown();
        layer.add(bg);
        layer.add(bonusLayer);
        layer.add(a);
        layer.add(b);
        layer.add(lifeText);
        layer.add(bonusCombo);
        layer.add(x1Layer);
        layer.add(x2Layer);
        layer.add(x3Layer);
        layer.add(x4Layer);
        layer.add(failedLayer);
        layer.add(darkLayer);


        //layer.add(skipLayer);
        layer.add(player.layer());

        layer.add(enemy.layer());

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / Setting.M_PER_PIXEL),
                    (int) (height / Setting.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f / Setting.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }



    }

    @Override
    public void update(int delta) {
        if(time_pause < 50) {
            time_pause += 1;
            alphaTest = 1.0f;
            return;
        }else {
            if(alphaTest > 0f) {
                alphaTest-= 0.05f;
                darkLayer.setAlpha(alphaTest);

                time_pause += 1;
                return;
            }
            if(time_pause >= 70 && time_pause <= 80) {
                newLoad(level);
                time_pause = 100;
            }

        }
        super.update(delta);
        player.update(delta);
        enemy.update(delta);
        world.step(0.033f,10,10);


        e+=2;
        if(e > 100) {
            if(minute == 5) {
                hour++;
                minute = 0;
                updateTime();
                e=0;
            }else {
                minute += 1;
                updateTime();
                e = 0;
            }
        }

        bg.setTranslation(bg.tx()-2,bg.ty());

        /*if(life == 0) {
            removeBody();
            key.clear();
            ArrowKey.key = "";

            ss.push(new GameOver(ss));

        }*/
        if(stateKey == true) {
            remaining--;
            updateBonus();
            if(enemy.layer().ty()+20 < 100) {
                bonus = level*1;
                track=1;
            }else if(enemy.layer().ty()+20 < 190) {
                bonus = level*2;
                track=2;
            }else if(enemy.layer().ty()+20 < 230) {
                bonus = level*3;
                track=3;
            }else if(enemy.layer().ty()+20 < 280) {
                bonus = level*4;
                track=4;
            }

            score += bonus ;
            bonus = 0;
            newLoad(level);

            layer.remove(enemy.layer());
            world.destroyBody(enemy.body);
            enemy  = new Enemy(world,700f,290f);
            layer.add(enemy.layer());


            updateScore();
            ArrowKey.key ="";
            stateKey = false;

        }
        //System.out.println("fadeBonus : " + fadeBonus + "\ntrack = " + track);
        if(track == 1) {
            if(fadeBonus < 1.5f && track == 1 && fadeStatus == 0) {
                fadeBonus += 0.05f;
                x1Layer.setAlpha(fadeBonus);
            } else {
                fadeStatus = 1;
                fadeBonus -= 0.5f;
                x1Layer.setAlpha(fadeBonus);
                if(fadeBonus < 0f) {
                    track = 0;
                    fadeStatus = 0;
                    fadeBonus = 0f;
                }
            }
        }else if(track == 2) {
                if(fadeBonus < 1.5f && track == 2 && fadeStatus == 0) {
                    fadeBonus += 0.05f;
                    x2Layer.setAlpha(fadeBonus);
                } else {
                    fadeStatus = 1;
                    fadeBonus -= 0.5f;
                    x2Layer.setAlpha(fadeBonus);
                    if(fadeBonus < 0f) {
                        track = 0;
                        fadeStatus = 0;
                        fadeBonus = 0f;
                    }
                }
        }else if(track == 3) {
            if(fadeBonus < 1.5f && track == 3 && fadeStatus == 0) {
                fadeBonus += 0.05f;
                x3Layer.setAlpha(fadeBonus);
            } else {
                fadeStatus = 1;
                fadeBonus -= 0.5f;
                x3Layer.setAlpha(fadeBonus);
                if(fadeBonus < 0f) {
                    track = 0;
                    fadeStatus = 0;
                    fadeBonus = 0f;
                }
            }
        }else if(track == 4) {
            if(fadeBonus < 1.5f && track == 4 && fadeStatus == 0) {
                fadeBonus += 0.05f;
                x4Layer.setAlpha(fadeBonus);
            } else {
                fadeStatus = 1;
                fadeBonus -= 0.5f;
                x4Layer.setAlpha(fadeBonus);
                if(fadeBonus < 0f) {
                    track = 0;
                    fadeStatus = 0;
                    fadeBonus = 0f;
                }
            }
        }else if(track == 5) {
            if(fadeBonus < 1.5f && track == 4 && fadeStatus == 0) {
                fadeBonus += 0.05f;
                failedLayer.setAlpha(fadeBonus);
            } else {
                fadeStatus = 1;
                fadeBonus -= 0.5f;
                failedLayer.setAlpha(fadeBonus);
                if(fadeBonus < 0f) {
                    track = 0;
                    fadeStatus = 0;
                    fadeBonus = 0f;
                }
            }
         }
        if(attack == 1) {
            removeBody();
            newLoad(level);
            attack = 0;
        }
    }
    @Override

    public void paint(Clock clock){
        super.paint(clock);
        player.paint(clock);
        enemy.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }

    public void newLoad(int level) {
        if(key.size() > 0) {
            for(int i = 0;i<key.size();i++){
                layer.remove(key.get("key_" + i).layer());
            }
        }
        System.out.println("Open Key");

        RandomArrow arEvent = new RandomArrow(layer,level);
        key = arEvent.getHashMap();
        for(int i = 0;i<key.size();i++){
            layer.add(key.get("key_" + i).layer());
        }
        System.out.println("new key : " + RandomArrow.keyOut + " ");
        over = 0;
        ArrowKey.key = "";
    }

    public void updateScore() {
        layer.remove(b);
        b = toolsg.genText("คะแนน : " + score,30, Colors.BLACK,400,20);
        layer.add(b);
    }
    public void updateTime() {
        layer.remove(a);
        if(hour >= 10 && minute >= 10) {
            a = toolsg.genText("Time : " + hour + ":" + minute,30, Colors.BLACK, 100, 10);
        }else if(hour < 10 && minute > 10) {
            a = toolsg.genText("Time : 0" + hour + ":" + minute,30, Colors.BLACK, 100, 10);
        }else if(hour >= 10 && minute < 10) {
            a = toolsg.genText("Time : " + hour + ":" + minute + "0",30, Colors.BLACK, 100, 10);
        }else {
            a = toolsg.genText("Time : 0" + hour + ":" + minute + "0", 30, Colors.BLACK, 100, 10);
        }
        layer.add(a);
    }
    public void updateBonus() {
        layer.remove(bonusCombo);
        bonusCombo = toolsg.genText("Remaining : " + remaining,20, Colors.BLACK,400,50);
        layer.add(bonusCombo);
    }
    public void removeBody() {
        world.destroyBody(enemy.body);
        layer.remove(enemy.layer());
        enemy = new Enemy(world,700f,290f);
        layer.add(enemy.layer());
    }







}
