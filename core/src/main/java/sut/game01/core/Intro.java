package sut.game01.core;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.core.character.Girl;
import sut.game01.core.character.Loading;
import sut.game01.core.character.Player;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import java.util.HashMap;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Intro extends Screen{

    public static  float M_PER_PIXEL = 1 / 26.666667f;
    private static int width = 24;
    private static int height = 18;
    private World world;
    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;
    private Player player;
    private Girl girl;


    private Loading loading;

    private DebugDrawBox2D debugDraw;
    private Boolean showDebugDraw = false;
    private String debugSring = "";

    HashMap<String, Layer> txt = new HashMap<String, Layer>();
    Image txt1,txt2,txt3,txt4;
    ImageLayer txt1Layer,txt2Layer,txt3Layer,txt4Layer;

    int time=300;
    int txtS=1;
    int show=0;
    int time_pause=0;

    private float alphaTest = 0;

    float fade=0;

    private Image darkImage,skipImage;
    private ImageLayer darkLayer,skipLayer;

    Sound bgS;

    public Intro(final ScreenStack ss){

        bgS = assets().getSound("sounds/open");

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        Body ground = world.createBody(new BodyDef());
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(new Vec2(0,height-(float)1.9),new Vec2(width,height-(float)1.9));
        ground.createFixture(groundShape,0.0f);


        this.ss=ss;
        loading = new Loading(600f,435f);

        bgImage = assets().getImage("images/darkFade.png");
        bg = graphics().createImageLayer(bgImage);

        txt1 = assets().getImage("images/intro.png");
        txt1Layer = graphics().createImageLayer(txt1);

        darkImage = assets().getImage("images/darkFade.png");
        darkLayer = graphics().createImageLayer(darkImage);
        skipImage = assets().getImage("images/skip.png");
        skipLayer = graphics().createImageLayer(skipImage);

        txt.put("txt1",txt1Layer);

        this.player = new Player(world,100,350 );
        this.girl = new Girl(world, 150,350);

    }

    @Override
    public void wasShown(){
        super.wasShown();

        this.layer.add(bg);

        skipLayer.setTranslation(350f,330f);
        skipLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                bgS.stop();
                super.onMouseDown(event);
                ss.remove(ss.top());
                ss.push(new CutScene2(ss));
            }
        });

        this.layer.add(darkLayer);
        this.layer.add(loading.layer());


        if(showDebugDraw){
            CanvasImage image = graphics().createImage(
                    (int) (width / Intro.M_PER_PIXEL),
                    (int) (height / Intro.M_PER_PIXEL));
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit |
                    DebugDraw.e_jointBit |
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f / Intro.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }


    }

    @Override
    public void update(int delta) {
        super.update(delta);

        if(time_pause < 100) {
            loading.update(delta);
            time_pause += 1;
            alphaTest = 1.0f;
            return;
        }else {
            if(alphaTest > 0f) {
                alphaTest-= 0.05f;
                darkLayer.setAlpha(alphaTest);
                loading.layer().setAlpha(alphaTest);
                time_pause += 1;
                player.layer().setVisible(true);
                girl.layer().setVisible(true);
                bg.setVisible(true);

                return;

            }
            if(time_pause >= 120 && time_pause <= 130) {
                layer.remove(darkLayer);
                layer.remove(loading.layer());
                time_pause = 150;

            }

        }


        time+=2;

        if(time >= 200) {
            if(show == 0) {
                if(fade == 0f) {
                    txt.get("txt" + txtS).setAlpha(0);
                    layer.add(txt.get("txt" + txtS));
                    fade += 0.05;
                    bgS.play();
                }else if(fade > 1f) {
                    show = 1;
                    time = 0;
                } else {
                    txt.get("txt" + txtS).setAlpha(fade);
                    fade += 0.05;
                }
            }else {
                if(fade > 1f) {
                    fade = 1f;
                    txt.get("txt" + txtS).setAlpha(fade);
                    fade -= 0.05;
                }else if(fade < 0f) {
                    fade = 0f;
                    layer.remove(layer.get(layer.size()-1));
                    show = 0;
                    time = 300;
                    txtS++;
                } else {
                    txt.get("txt" + txtS).setAlpha(fade);
                    fade -= 0.05;
                }
            }

        }

        if(txtS == txt.size()+1) {
            bgS.stop();
            ss.remove(ss.top());
            ss.push(new HomeScreen(ss));
        }

    }
    @Override
    public void paint(Clock clock){
        super.paint(clock);
        player.paint(clock);
        girl.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            debugDraw.getCanvas().drawText(debugSring,50,50);
            world.drawDebugData();
        }
    }

}
