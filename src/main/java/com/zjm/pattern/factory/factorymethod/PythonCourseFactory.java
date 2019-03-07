package com.zjm.pattern.factory.factorymethod;


import com.zjm.pattern.factory.ICourse;
import com.zjm.pattern.factory.PythonCourse;

/**
 * Created by Tom.
 */
public class PythonCourseFactory implements ICourseFactory {

    public ICourse create() {
        return new PythonCourse();
    }
}
