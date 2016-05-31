package sut.game01.core;


import playn.core.*;
import sut.game01.core.character.Button;
import sut.game01.core.character.Girl;
import sut.game01.core.character.Player;
import tripleplay.entity.System;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import  sut.game01.core.Tools.*;

import static playn.core.PlayN.*;

public class HomeScreen extends UIScreen{

    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;
    ToolsG toolsg = new ToolsG();
    Button bt1 = new Button(100f,250f,"b1");
    Button bt2 = new Button(100f,320f,"b2");
    Button bt3 = new Button(100f,390f,"b3");
    Sound intro,check;

    private float alphaTest = 0;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;


        intro = assets().getSound("Sounds/Intro");
        check = assets().getSound("Sounds/check");
        intro.play();
        bgImage = assets().getImage("images/title.jpg");
        bg = graphics().createImageLayer(bgImage);

        bt1.layer().addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                super.onMouseOver(event);
                check.play();
            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                intro.stop();
                ss.remove(ss.top());
                ss.push(new CutScene1(ss));
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
                intro.stop();
                ss.remove(ss.top());
                ss.push(new Howto(ss));

            }
        });
        bt3.layer().addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseOver(Mouse.MotionEvent event) {
                super.onMouseOver(event);
                check.play();
            }

            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                intro.stop();
                Runtime.getRuntime().exit(1);

            }
        });

    }


    @Override
    public void wasShown(){
        super.wasShown();


        this.layer.add(bg);
        this.layer.add(bt1.layer());
        this.layer.add(bt2.layer());
        this.layer.add(bt3.layer());


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
