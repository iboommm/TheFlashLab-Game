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

public class CutScence extends UIScreen {

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
    ToolsG toolsg = new ToolsG();

    public static boolean stateKey = false;
    public static HashMap<String,ArrowKey> key = new HashMap<String,ArrowKey>();
    public Layer a,b,lifeText;
    public int score = 0;

    int life = 3,attack = 0;


    ArrowKey ar;
    Girl girl;
    Enemy enemy;


    private Boolean showDebugDraw = true;
    private DebugDrawBox2D debugDraw;


    private float alphaTest = 0;

    public CutScence(final ScreenStack ss){

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height-(float)2.0),new Vec2(width,height-(float)2.0));
        ground.createFixture(groundShape,0.0f);

        girl = new Girl(world,80f,290f);
        player = new Player(world,120f,290f);
        enemy  = new Enemy(world,500f,290f);
        girl.layer().setScaleX(-1f);


        btSkip = assets().getImage("images/skip.png");
        skipLayer = graphics().createImageLayer(btSkip);
        skipLayer.setTranslation(390f,0f);

        bgImage = assets().getImage("images/cutscence.png");
        bg = graphics().createImageLayer(bgImage);

        skipLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(new GameplayScreen(ss));
            }
        });


        this.ss = ss;
        a = toolsg.genText("Time : 08:10",30, Colors.BLACK,100,20);
        b = toolsg.genText("คะแนน : " + score,30, Colors.BLACK,400,20);
        lifeText = toolsg.genText("ชีวิต : " + life,30, Colors.BLACK,100,50);
        ar = new ArrowKey(100f,100f,1);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();



                if((a == player.body && b == enemy.body) || (b == player.body && a == enemy.body)) {
                    life -= 1;
                    layer.remove(lifeText);
                    lifeText = toolsg.genText("ชีวิต : " + life,30, Colors.BLACK,100,50);
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

    public void removeBody() {
        world.destroyBody(enemy.body);
        layer.remove(enemy.layer());
        enemy = new Enemy(world,500f,290f);
        layer.add(enemy.layer());
    }


    @Override
    public void wasShown(){




        super.wasShown();
        layer.add(bg);
        layer.add(a);
        layer.add(b);
        layer.add(lifeText);

        //layer.add(skipLayer);
        layer.add(girl.layer());
        layer.add(player.layer());

        layer.add(enemy.layer());

        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / GameplayScreen.M_PER_PIXEL),
                    (int) (height / GameplayScreen.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f / GameplayScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }

        newLoad(1);

    }

    @Override
    public void update(int delta) {
        super.update(delta);
        girl.update(delta);
        player.update(delta);
        enemy.update(delta);
        world.step(0.033f,10,10);
        if(life == -1) {
            ss.push(new GameOver(ss));
        }
        if(attack == 1) {
            removeBody();
            attack = 0;
        }
        if(stateKey == true) {
            for(int i = 0; i< key.size();i++) {
                Layer a = key.get("key_" + i).layer();
                System.out.println("remove : " + i);
                layer.remove(a);
            }
            layer.remove(enemy.layer());
            world.destroyBody(enemy.body);
            enemy  = new Enemy(world,500f,290f);
            layer.add(enemy.layer());
            newLoad(2);

            score += score + 10;
            updateScore();
            ArrowKey.key ="";
            stateKey = false;

        }
    }
    @Override

    public void paint(Clock clock){
        super.paint(clock);
        girl.paint(clock);
        player.paint(clock);
        enemy.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }

    public void newLoad(int level) {
        RandomArrow arEvent = new RandomArrow(layer,level);
        key = arEvent.getHashMap();
        for(int i = 0;i<key.size();i++){
            layer.add(key.get("key_" + i).layer());
        }
        System.out.println("new key : " + RandomArrow.keyOut + " ");
    }

    public void updateScore() {
        layer.remove(b);
        b = toolsg.genText("คะแนน : " + score,30, Colors.BLACK,400,20);
        layer.add(b);
    }





}
