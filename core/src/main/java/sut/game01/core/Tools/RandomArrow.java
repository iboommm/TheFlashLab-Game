package sut.game01.core.Tools;

import playn.core.GroupLayer;
import playn.core.Layer;
import sut.game01.core.character.ArrowKey;

import javax.swing.*;
import java.util.HashMap;
import java.util.Random;

public class RandomArrow
{
    public HashMap<String,Layer> key = new HashMap<String,Layer>();
    public String keyRandom;
    float x = 200f;
    float y = 100f;

    public RandomArrow(GroupLayout layer,int level) {
        randomKey(level);
        setHashMap();

    }



    public void setHashMap() {
        char[] a = keyRandom.toCharArray();
        int i,s;
        for(i = 0; i< keyRandom.length();i++) {
            s = Integer.valueOf(a[i]);
            key.put("key_" + i + "_" + s,new ArrowKey(x + (i*10),y,s).layer());
        }
    }

    public void randomKey(int level) {
        int random;
        Random ran = new Random();
        int x = ran.nextInt(6) + 5;
        int s, i;

        if (level == 1) {
            random = ran.nextInt(9999);
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
            keyRandom = String.valueOf(a);
            System.out.println(keyRandom);
        }else if (level == 2) {
            random = ran.nextInt(999999);
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
            keyRandom = String.valueOf(a);
            System.out.println(keyRandom);
        }else if (level == 3) {
            random = ran.nextInt(999999999);
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
            keyRandom = String.valueOf(a);
            System.out.println(keyRandom);
        }
        this.keyRandom = keyRandom;
    }
}
