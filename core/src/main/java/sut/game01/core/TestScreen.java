package sut.game01.core;

import playn.core.Font;
import playn.core.Mouse;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.ui.layout.AxisLayout;
import static playn.core.PlayN.*;

public class TestScreen extends Screen{
	private final ScreenStack ss;
  private final ImageLayer bg;
  private final ImageLayer bt;

  
	public TestScreen(final ScreenStack ss) {
		this.ss = ss;

    Image bgImage = assets().getImage("images/bg.png");
    this.bg = graphics().createImageLayer(bgImage);
    
    Image button = assets().getImage("images/back.png");
    this.bt = graphics().createImageLayer(button);
    bt.setTranslation(100,100);

    bt.addListener(new Mouse.LayerAdapter() {
        @Override
        public void onMouseUp(Mouse.ButtonEvent event) {
          ss.remove(ss.top());
        }
      });
    
	}
  
  	@Override
  	public void wasShown() {
  		super.wasShown();		
      layer.add(bg);
      layer.add(bt);
  	}
}
