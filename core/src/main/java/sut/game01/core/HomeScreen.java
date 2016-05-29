package sut.game01.core;


import playn.core.*;
import sut.game01.core.character.Button;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import  sut.game01.core.Tools.*;

import static playn.core.PlayN.*;

public class HomeScreen extends UIScreen{

    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;
    ToolsG toolsg = new ToolsG();
    Button bt1 = new Button(100f,300f,"b1");
    Button bt2 = new Button(100f,370f,"b2");
    Sound intro,check;
    private float alphaTest = 0;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;

        intro = assets().getSound("sounds/Intro");
        check  = assets().getSound("sounds/check");
        intro.play();
        intro.setLooping(true);

         bgImage = assets().getImage("images/title.png");
         bg = graphics().createImageLayer(bgImage);

        bt1.layer().addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                super.onMouseOver(event);
                check.play();
            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(new Stage(ss));
                intro.stop();
            }
        });
        bt2.layer().addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                super.onMouseOver(event);
                check.play();
            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(new CutScene1(ss));
                intro.stop();
            }
        });
    }


    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
        this.layer.add(bt1.layer());
        this.layer.add(bt2.layer());
        //layer.add(toolsg.genText("test",20,Colors.WHITE,100,200));


    }

    @Override
    public void update(int delta) {
        alphaTest = toolsg.fade(alphaTest);
        bg.setAlpha(alphaTest);
        bt1.layer().setAlpha(alphaTest);
        bt2.layer().setAlpha(alphaTest);

    }





}
