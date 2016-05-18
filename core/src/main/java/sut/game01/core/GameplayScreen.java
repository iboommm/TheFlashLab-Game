package sut.game01.core;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.canvas.LayerCanvas;
import playn.core.util.Clock;
import sut.game01.core.character.Player;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.util.*;

import java.util.HashMap;

import static playn.core.PlayN.*;

public class GameplayScreen extends Screen{

    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;
    private Player player;

    private Image starImage;
    private ImageLayer star;

    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = true;
    private static HashMap<Body, String> bodies = new HashMap<Body,String>();
    private int score = 0;
    private String debugSring = "";


    public GameplayScreen(final ScreenStack ss){

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height-(float)4.5),new Vec2(width,height-(float)4.5));
        ground.createFixture(groundShape,0.0f);


        this.ss=ss;


        bgImage = assets().getImage("images/MAP1.png");
        bg = graphics().createImageLayer(bgImage);


        this.player = new Player(world, 100,300);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Body a = contact.getFixtureA().getBody();
                Body b = contact.getFixtureB().getBody();
                debugSring = " Score :  " + score;

                System.out.println(bodies.get(a));

                if(bodies.get(a) == null) {
                    score +=1;
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
        this.layer.add(bg);

        this.layer.add(player.layer());

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


    }

    @Override
    public void update(int delta) {
        bg.setTranslation(bg.tx()-1.5f,bg.ty());
        super.update(delta);
        player.update(delta);
        world.step(0.033f,10,10);

    }
    @Override
    public void paint(Clock clock){
        super.paint(clock);
        player.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().drawText(debugSring,50,50);
            world.drawDebugData();
        }
    }

}
