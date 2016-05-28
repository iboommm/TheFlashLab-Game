package sut.game01.core.Tools;

import playn.core.*;
import sut.game01.core.Setting;
import tripleplay.game.UIScreen;

import static playn.core.PlayN.graphics;

/**
 * Created by GTX on 13/5/2559.
 */
public class ToolsG extends UIScreen {
    private int width = 24;
    private int height = 18;
    public Integer time=0;

    public Layer genText(String text, int fontSize, Integer fontColor, Integer x, Integer y) {
        Font font = graphics().createFont("Tahoma", Font.Style.PLAIN, fontSize);
        TextLayout layout = graphics().layoutText(
                text, new TextFormat().withFont(font).withWrapWidth(200));
        Layer textLayer = createTextLayer(layout, fontColor,x,y);
        return textLayer;
    }

    private Layer createTextLayer(TextLayout layout, Integer fontColor,Integer x,Integer y) {
        CanvasImage image = graphics().createImage(
                (int) (width / Setting.M_PER_PIXEL),
                (int) (height / Setting.M_PER_PIXEL));
        if (fontColor != null) image.canvas().setFillColor(fontColor);
        image.canvas().fillText(layout,x,y);
        //image.canvas().setStrokeColor(fontColor);
        //image.canvas().setAlpha(100);
        //image.canvas().strokeText(layout,10,10);

        return graphics().createImageLayer(image);
    }

    public float fade(float alphaTest) {
        if(alphaTest < 1f)
            return alphaTest + (float)0.05;
        else
            return 1f;
    }



}
