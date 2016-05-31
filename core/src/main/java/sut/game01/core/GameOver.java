package sut.game01.core;

import playn.core.Mouse;
import sut.game01.core.Tools.ToolsG;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import playn.core.*;
import tripleplay.util.Colors;

import static playn.core.PlayN.*;

public class GameOver extends UIScreen{
	private final ScreenStack ss;
    private final ImageLayer bg;
    ToolsG tool = new ToolsG();
    Layer txtScore,txtScore2,txtScore3,txtScore4;

    Image retryImage,backImage;
    ImageLayer retryLayer,backLayer;
    Sound bgS;
  
	public GameOver(final ScreenStack ss) {
        this.ss = ss;
        bgS = assets().getSound("Sounds/GameOver");

        Image bgImage = assets().getImage("images/gameover.jpg");
        bg = graphics().createImageLayer(bgImage);

        retryImage = assets().getImage("images/retry.png");
        retryLayer = graphics().createImageLayer(retryImage);
        backImage = assets().getImage("images/back.png");
        backLayer = graphics().createImageLayer(backImage);
        retryLayer.setTranslation(380f,280f);
        backLayer.setTranslation(380f,360f);
        retryLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                super.onMouseDown(event);
                bgS.stop();
                ss.remove(ss.top());
                ss.push(new Stage(ss));
            }
        });

        backLayer.addListener(new Mouse.LayerAdapter() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent event) {
                super.onMouseDown(event);
                bgS.stop();
                ss.remove(ss.top());
                ss.push(new HomeScreen(ss));
            }
        });


        txtScore = tool.genText("Score : " + Score.score ,30, Colors.BLACK,50,300);
        txtScore2 = tool.genText("X2 : " + Score.x2 ,20, Colors.BLACK,50,340);
        txtScore3 = tool.genText("X3 : " + Score.x3 ,20, Colors.BLACK,50,370);
        txtScore4 = tool.genText("X4 : " + Score.x4 ,20, Colors.BLACK,50,400);
    }
  
  	@Override
  	public void wasShown() {
        bgS.play();
  		super.wasShown();		
        layer.add(bg);
        layer.add(retryLayer);
        layer.add(backLayer);
        layer.add(txtScore);
        layer.add(txtScore2);
        layer.add(txtScore3);
        layer.add(txtScore4);

        Score.score = 0;
        Score.x2 = 0;
        Score.x3 = 0;
        Score.x4 = 0;
        Score.stage = 1;
  	}
}
