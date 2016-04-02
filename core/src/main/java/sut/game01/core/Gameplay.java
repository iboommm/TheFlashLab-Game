package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Root;

import java.awt.event.MouseListener;

public class Gameplay extends Screen {
    public final ScreenStack ss;
    private Root root;
    private final Image bgImage;
    private final Image startImage;
    private final ImageLayer bgLayer;
    private final ImageLayer startButton;

    public Gameplay(final ScreenStack ss) {
        this.ss = ss;

        bgImage = assets().getImage("images/bg.png");
        startImage = assets().getImage("images/exit.png");
        bgLayer = graphics().createImageLayer(bgImage);
        startButton = graphics().createImageLayer(startImage);
        startButton.addListener(new Pointer.Adapter() {
            public void onPointerStart(Pointer.Event event) {
                ss.remove(ss.top());
            }
        });
        startButton.setTranslation(200, 200);
    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);
        this.layer.add(startButton);


    }
}

