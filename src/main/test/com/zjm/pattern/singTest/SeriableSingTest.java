package com.zjm.pattern.singTest;

import com.zjm.pattern.singleton.SeriableSing;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Create by zjm on 2019/3/11
 */
public class SeriableSingTest {
    public static void main(String[] args) {
        SeriableSing s1 = null;
        SeriableSing s2 = SeriableSing.getInstance();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("SeriableSing.obj");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s2);
            oos.flush();
            oos.close();
            FileInputStream fis = new FileInputStream("SeriableSing.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            s1 = (SeriableSing)ois.readObject();
            ois.close();
            System.out.println(s1);
            System.out.println(s2);
            System.out.println(s1 == s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
