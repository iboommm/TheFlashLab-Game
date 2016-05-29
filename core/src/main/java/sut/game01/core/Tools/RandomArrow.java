package sut.game01.core.Tools;

import playn.core.GroupLayer;
import playn.core.Layer;
import sut.game01.core.character.ArrowKey;

import java.util.HashMap;
import java.util.Random;

public class RandomArrow
{
    public HashMap<String,ArrowKey> key = new HashMap<String,ArrowKey>();
    public int level;
    public String keyRandom;
    public static String keyOut;
    GroupLayer layer;
    float x = 200f;
    float y = 100f;

    public RandomArrow(GroupLayer layer,int level) {
        this.layer = layer;
        this.level = level;
        randomKey(level);
        setHashMap();
        keyOut = keyRandom;

    }


    public HashMap getHashMap() {
        return key;
    }

    public GroupLayer getLayer() {
        return layer;
    }


    public void setHashMap() {
        char[] a = keyRandom.toCharArray();
        int i,s;
        for(i = 0; i< keyRandom.length();i++) {
            s = Integer.valueOf(a[i] - '0');
            String b = "key_" + i;
            key.put(b,new ArrowKey(50f + (i*60),400f,s));
            System.out.println(b);
        }
    }


    public void randomKey(int level) {
        int random;
        Random ran = new Random();
        int x = ran.nextInt(6) + 5;
        int s, i;

        if (level == 1) {
            random = ran.nextInt(8999) + 1000;
            char[] a;
            keyRandom = String.valueOf(random);
            a = keyRandom.toCharArray();
            for (i = 0; i < keyRandom.length(); i++) {

                s = Integer.valueOf(a[i]);
                if (s > 3) {
                    s = s % 4;
                    a[i] = (char) ('1' + s);
                }
            }
            keyRandom = String.valueOf(a) + "0";
            System.out.println(keyRandom);
        }else if (level == 2) {
            random = ran.nextInt(899999) + 100000;
            char[] a;
            keyRandom = String.valueOf(random);
            a = keyRandom.toCharArray();
            for (i = 0; i < keyRandom.length(); i++) {

                s = Integer.valueOf(a[i]);
                if (s > 3) {
                    s = s % 4;
                    a[i] = (char) ('1' + s);
                }
            }
            keyRandom = String.valueOf(a) + "0";
            System.out.println(keyRandom);
        }else if (level == 3) {
            random = ran.nextInt(89999999) + 10000000;
            char[] a;
            keyRandom = String.valueOf(random);
            a = keyRandom.toCharArray();
            for (i = 0; i < keyRandom.length(); i++) {

                s = Integer.valueOf(a[i]);
                if (s > 3) {
                    s = s % 4;
                    a[i] = (char) ('1' + s);
                }
            }
            keyRandom = String.valueOf(a) + "0";
            System.out.println("KeyRandom : " + keyRandom);
        }
    }
}
