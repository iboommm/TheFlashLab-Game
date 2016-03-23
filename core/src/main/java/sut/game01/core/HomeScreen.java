package sut.game01.core;

import playn.core.Font;
import react.UnitSlot;
import tripleplay.game.UIScreen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.*;
import playn.core.Image;
import playn.core.ImageLayer;
import tripleplay.game.Screen;
import tripleplay.ui.layout.AxisLayout;
import static playn.core.PlayN.*;


public class HomeScreen extends UIScreen{

	public static final Font TITLE_FONT = graphics().createFont("Helvatica",Font.Style.PLAIN,24);

	private ScreenStack ss;
	private Root root;
	
	private final TestScreen testScreen;

	public HomeScreen(ScreenStack ss) {
		this.ss = ss;
		this.testScreen = new TestScreen(ss);
	}
  
  	@Override
  	public void wasShown() {
  		super.wasShown();
  		root = iface.createRoot(
  			AxisLayout.vertical().gap(15),
  			SimpleStyles.newSheet(), this.layer);

		root.addStyles(Style.BACKGROUND.is(Background.bordered(0xFFCCCCCC, 0xFF99CCFF, 5).inset(5, 10)));
		root.setSize(width(), height());

		root.add(new Label("Even Driven Programming").addStyles(Style.FONT.is(HomeScreen.TITLE_FONT)));
		Button button = new Button("Start").onClick(new UnitSlot() {
			@Override
			public void onEmit() {
				ss.push(testScreen);
			}
		}) ;
		root.add(button);

  	}
}
