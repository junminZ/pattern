package com.zjm.pattern.prototype;

import java.io.*;
import java.util.Date;

/**
 * Create by zjm on 2019/3/15
 */
public class Police extends Human implements Cloneable, Serializable {
    public Gun gun;

    public Police() {
        this.birthday = new Date();
        this.gun = new Gun();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return deepClone();
    }

    public Object deepClone() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Police copy = (Police)objectInputStream.readObject();
            copy.birthday = new Date();
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Police shallowClone(Police p) {
        Police police = new Police();
        police.weight = p.weight;
        police.height = p.height;

        police.gun = p.gun;
        police.birthday = new Date();
        return police;
    }
}
