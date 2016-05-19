package sut.game01.core.character;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Keyboard;
import playn.core.Layer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.core.GameplayScreen;
import sut.game01.core.sprite.Sprite;
import sut.game01.core.sprite.SpriteLoader;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.keyboard;


public class Player {
    private Sprite sprite;
    private int spriteIndex = 0;
    private boolean hasLoaded = false;
    public static Body body;
    private ScreenStack ss;
    private float x;
    private float y;
    FixtureDef fixtureDef;


    public enum State{
      IDLE,WALK,JUMP
    };
    private State state = State.IDLE;

    private int e =0;

    public Player(final World world, final float x, final float y){
        this.x=x;
        this.y=y;


        sprite = SpriteLoader.getSprite("images/player.json");
        sprite.addCallback(new Callback<Sprite>() {
            @Override
            public void onSuccess(Sprite sprite) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width()/2f,sprite.height()/2f);
                sprite.layer().setTranslation(x,y);
                body = initPhysicsBody(world,
                        GameplayScreen.M_PER_PIXEL * x,
                        GameplayScreen.M_PER_PIXEL * y);
                hasLoaded = true;
                System.out.println("Loaded");
                state = State.WALK;
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });


    }

    public Layer layer(){
        return sprite.layer();
    }

    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((sprite.layer().width()+20)* GameplayScreen.M_PER_PIXEL/2,
                sprite.layer().height()*GameplayScreen.M_PER_PIXEL/2);


        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.3f;
        fixtureDef.friction = 0.1f;
        //fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }

    public void update(int delta) {
        if(!hasLoaded)return;
        e+=delta;

        if(e>150){
            switch (state){
                case IDLE:
                    if(!(spriteIndex>=0&&spriteIndex<=4)){
                        spriteIndex=0;
                    }
                    break;
                case WALK:
                    if(!(spriteIndex>=6&&spriteIndex<=9)){
                        spriteIndex=6;
                    }
                    break;
                case JUMP:
                    if(!(spriteIndex>=18&&spriteIndex<=23)){
                        spriteIndex=18;
                    }
                    break;
            }
            if(spriteIndex==23){
                state=State.IDLE;
            }
            sprite.setSprite(spriteIndex);
            spriteIndex++;
            e=0;

        }
    }

    public void paint(Clock clock){
        if(!hasLoaded)return;
        switch (state){
            case JUMP:
                if(spriteIndex>=18 && spriteIndex<=23) {
                    body.applyLinearImpulse(new Vec2(0, -3f), body.getPosition());
                }

                break;
            case WALK:
                break;
        }


        sprite.layer().setTranslation(
                (body.getPosition().x / GameplayScreen.M_PER_PIXEL),
                (body.getPosition().y / GameplayScreen.M_PER_PIXEL));

        sprite.layer().setRotation(body.getAngle());
    }

}
