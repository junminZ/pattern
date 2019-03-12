package com.zjm.pattern.singTest;

import com.zjm.pattern.singleton.EnumSing;

import java.lang.reflect.Constructor;

/**
 * Create by zjm on 2019/3/11
 */
public class EnumSingTest {
//    public static void main(String[] args) {
//        try {
//            EnumSing instance1 = null;
//            EnumSing instance2 = EnumSing.getInstance();
//            instance2.setData(new Object());
//            FileOutputStream fos = new FileOutputStream("EnumSing.obj");
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(instance2);
//            oos.flush();
//            oos.close();
//            FileInputStream fis = new FileInputStream("EnumSing.obj");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            instance1 = (EnumSing) ois.readObject();
//            ois.close();
//            System.out.println(instance1.getData());
//            System.out.println(instance2.getData());
//            System.out.println(instance1.getData() == instance2.getData());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
        public static void main(String[] args) {
            try {
                Class clazz = EnumSing.class;
                Constructor c = clazz.getDeclaredConstructor(String.class,int.class);
                c.setAccessible(true);
                EnumSing enumSingleton = (EnumSing)c.newInstance("zjm",666);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
}
