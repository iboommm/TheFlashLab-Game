package sut.game01.core;


import playn.core.*;
import sut.game01.core.character.Button;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.util.Colors;
import  sut.game01.core.Tools.*;

import java.util.HashMap;

import static playn.core.PlayN.*;

public class HomeScreen extends UIScreen{

    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;
    ToolsG toolsg = new ToolsG();
    Button bt1 = new Button(100f,300f,"b1");
    Button bt2 = new Button(100f,370f,"b2");

    private float alphaTest = 0;

    public HomeScreen(final ScreenStack ss){
        this.ss = ss;

         bgImage = assets().getImage("images/title.png");
         bg = graphics().createImageLayer(bgImage);

        bt1.layer().addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(new CutScence(ss));
            }
        });
        bt2.layer().addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                ss.push(new SettingScreen(ss));
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
