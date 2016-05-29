package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.Mouse;
import sut.game01.core.Tools.ToolsG;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;
import tripleplay.util.Colors;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Result extends UIScreen{
	private final ScreenStack ss;
    private final ImageLayer bg;
    ToolsG tool = new ToolsG();
    Layer txtScore,txtScore2,txtScore3,txtScore4;

    Image nextImage;
    ImageLayer nextLayer;

	public Result(final ScreenStack ss) {
        this.ss = ss;

        Image bgImage = assets().getImage("images/clear.png");
        bg = graphics().createImageLayer(bgImage);

        nextImage = assets().getImage("images/next.png");
        nextLayer = graphics().createImageLayer(nextImage);
        nextLayer.setTranslation(390f,150f);
        nextLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                super.onMouseDown(event);
                ss.remove(ss.top());
                ss.push(new Stage(ss));
            }
        });



        txtScore = tool.genText("Score : " + Score.score ,40, Colors.BLACK,80,180);
        txtScore2 = tool.genText("Combo X2 : " + Score.x2 ,30, Colors.BLACK,80,230);
        txtScore3 = tool.genText("Combo X3 : " + Score.x3 ,30, Colors.BLACK,80,270);
        txtScore4 = tool.genText("Combo X4 : " + Score.x4 ,30, Colors.BLACK,80,310);
    }
  
  	@Override
  	public void wasShown() {
  		super.wasShown();		
        layer.add(bg);
        layer.add(nextLayer);
        layer.add(txtScore);
        layer.add(txtScore2);
        layer.add(txtScore3);
        layer.add(txtScore4);
        Score.stage++;
  	}
}
