package sut.game01.core;
import static playn.core.PlayN.*;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Pointer;
import playn.core.util.Clock;
import sut.game01.core.character.Player;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Root;

import java.awt.event.MouseListener;

public class HomeScreen extends Screen {
    private Player player;

    public final ScreenStack ss;
    private Root root;
    private final Image bgImage;
    private final ImageLayer bgLayer;

    public HomeScreen(final ScreenStack ss) {
        this.ss = ss;

        bgImage = assets().getImage("images/bg.png");
        bgLayer = graphics().createImageLayer(bgImage);

    }

    @Override
    public void wasShown() {
        super.wasShown();
        this.layer.add(bgLayer);

        player = new Player(300f,200f);
        this.layer.add(player.layer());

    }
    public void update(int delta) {
        this.player.update(delta);
    }
}

