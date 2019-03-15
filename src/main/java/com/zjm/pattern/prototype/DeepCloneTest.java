package com.zjm.pattern.prototype;

/**
 * Create by zjm on 2019/3/15
 */
public class DeepCloneTest {
    public static void main(String[] args) {
        try {
            Police police = new Police();
            Police clone = (Police) police.clone();
            System.out.println("深克隆：" + (police.gun == clone.gun));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Police p = new Police();
        Police pc = p.shallowClone(p);
        System.out.println("浅克隆：" + (p.gun == pc.gun));

    }
}
