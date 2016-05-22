package sut.game01.core;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.Tools.RandomArrow;
import sut.game01.core.Tools.ToolsG;
import sut.game01.core.character.ArrowKey;
import sut.game01.core.character.Button;
import sut.game01.core.character.Girl;
import sut.game01.core.character.Player;
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

    ArrowKey ar;
    Girl girl;

    Layer a;
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
        ar = new ArrowKey(100f,100f,1);


    }


    @Override
    public void wasShown(){
        super.wasShown();
        layer.add(bg);
        layer.add(a);



        //layer.add(skipLayer);
        layer.add(girl.layer());
        layer.add(player.layer());

        newLoad(1);

    }

    @Override
    public void update(int delta) {
        girl.update(delta);
        player.update(delta);
        super.update(delta);
        world.step(0.033f,10,10);
        if(stateKey == true) {
            for(int i = 0; i< key.size();i++) {
                Layer a = key.get("key_" + i).layer();
                System.out.println("remove : " + i);
                layer.remove(a);
            }
            newLoad(2);
            ArrowKey.key ="";
            stateKey = false;

        }
    }
    @Override

    public void paint(Clock clock){
        super.paint(clock);
        girl.paint(clock);
        player.paint(clock);
    }

    public void newLoad(int level) {
        RandomArrow arEvent = new RandomArrow(layer,level);
        key = arEvent.getHashMap();
        for(int i = 0;i<key.size();i++){
            layer.add(key.get("key_" + i).layer());
        }
        System.out.println("new key : " + RandomArrow.keyOut + " ");
    }





}
