package com.zjm.pattern.factory.factorymethod;

import com.zjm.pattern.factory.ICourse;
import com.zjm.pattern.factory.JavaCourse;

/**
 * Created by Tom.
 */
public class JavaCourseFactory implements ICourseFactory {
    public ICourse create() {
        return new JavaCourse();
    }
}
