package sut.game01.core;


import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.keyboard;

public class SettingScreen extends Screen {

    private ScreenStack ss;
    private Image bgImage;
    private ImageLayer bg;

    public SettingScreen(final ScreenStack ss){
        this.ss=ss;

        bgImage = assets().getImage("images/setting.png");
        bg = graphics().createImageLayer(bgImage);

        keyboard().setListener(new Keyboard.Adapter(){
            public void onKeyDown(Keyboard.Event event){
                switch (event.key()){
                    case BACK:
                        ss.remove(ss.top());
                        ss.push(new HomeScreen(ss));
                        break;
                }
            }
        });
    }

    @Override
    public void wasShown(){
        super.wasShown();
        this.layer.add(bg);
    }
}
