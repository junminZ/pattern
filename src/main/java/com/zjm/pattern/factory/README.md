# 对常见的设计模式的学习记录
## 简单工厂
`简单工厂模式`（Simple Factory Pattern）是指由一个工厂对象决定创建出哪一种产品类的实例，但它不属于 GOF，23 种设计模式式（参考资料：
http://en.wikipedia.org/wiki/Design_Patterns#Patterns_by_Type）。
简单工厂适用于工厂类负责创建的对象较少的场景，且客户端只需要传入工厂类的参数，对于如何创建对象的逻辑不需要关心。
简单工厂的缺点：工厂类的职责相对过重，不易于扩展过于复杂的产品结构。
### 代码示例
以课程为例。咕泡学院目前开设有 Java 架构、大数据、人
工智能等课程，已经形成了一个生态。我们可以定义一个课程标准 ICourse 接口：
``` java
public interface ICourse {
/**
录制视频 
*/
public void record();
}
```
创建一个 Java 课程的实现 JavaCourse 类：
``` java
public class JavaCourse implements ICourse {
public void record() {
System.out.println("录制 Java 课程");
}
}
```
看客户端调用代码，我们会这样写：
``` java
public static void main(String[] args) {
ICourse course = new JavaCourse();
course.record();
}
```
看上面的代码，父类 ICourse 指向子类 JavaCourse 的引用，应用层代码需要依赖JavaCourse，如果业务扩展，我继续增加 PythonCourse 甚至更多，那么我们客户端的依赖会变得越来越臃肿。因此，我们要想办法把这种依赖减弱，把创建细节隐藏。虽然目前的代码中，我们创建对象的过程并不复杂，但从代码设计角度来讲不易于扩展。现在，我们用简单工厂模式对代码进行优化。
<!--more-->
先增加课程 PythonCourse 类：
``` java
public class PythonCourse implements ICourse {
    public void record() {
    System.out.println("录制 Python 课程");
    }
}
```
创建 CourseFactory 工厂类：
``` java
public class CourseFactory {
    public ICourse create(String name){
        if("java".equals(name)){
            return new JavaCourse();
         }else if("python".equals(name)){
            return new PythonCourse();
         }else {
            return null;
         }
     }
}
```
修改客户端调用代码：
``` java 
    public class SimpleFactoryTest {
        public static void main(String[] args) {
        CourseFactory factory = new CourseFactory();
        factory.create("java");
        }
    }
```
客户端调用是简单了，但如果我们业务继续扩展，要增加前端课程，那么工厂中的
create()就要根据产品链的丰富每次都要修改代码逻辑。不符合开闭原则。因此，我们
对简单工厂还可以继续优化，可以采用反射技术：
``` java
public ICourse create(Class<? extends ICourse> clazz){
    try {
        if (null != clazz) {
        return clazz.newInstance();
        }
    }catch (Exception e){
    e.printStackTrace();
    }
    return null;
}
```
优化客户端代码：
``` java
public static void main(String[] args) {
CourseFactory factory = new CourseFactory();
ICourse course = factory.create(JavaCourse.class);
course.record();
}
```
类图：
![](https://i.loli.net/2019/03/07/5c80c65aabf5f.png)
经常使用的日志工具LoggerFactory中就使用的简单工厂模式
``` java
public static Logger getLogger(String name) {
    ILoggerFactory iLoggerFactory = getILoggerFactory();
    return iLoggerFactory.getLogger(name);
}
public static Logger getLogger(Class clazz) {
    return getLogger(clazz.getName());
}
```
[代码示例](https://github.com/JunminZhang/pattern/blob/master/src/main/java/com/zjm/pattern/factory/simplefactory/SimpleFactoryTest.java)
## 工厂方法模式
`工厂方法模式`（Fatory Method Pattern）是指定义一个创建对象的接口，但让实现这个接口的类来决定实例化哪个类，工厂方法让类的实例化推迟到子类中进行。在工厂方法模式中用户只需要关心所需产品对应的工厂，无须关心创建细节，而且加入新的产品符合`开闭原则`。
工厂方法模式主要解决产品扩展的问题，在简单工厂中，随着`产品链`的丰富，如果每个课程的创建逻辑有区别的话，工厂的职责会变得越来越多，有点像万能工厂，并不便于维护。根据`单一职责`原则我们将职能继续拆分，专人干专事。Java 课程由 Java 工厂创建，Python 课程由 Python 工厂创建，对工厂本身也做一个抽象。来看代码，先
创建 ICourseFactory 接口：
``` java
public interface ICourseFactory {
    ICourse create();
}
```
在分别创建子工厂，JavaCourseFactory 类：
``` java
public class JavaCourseFactory implements ICourseFactory {
    public ICourse create() {
    return new JavaCourse();
    }
}
```

PythonCourseFactory 类：
``` java
public class PythonCourseFactory implements ICourseFactory {
    public ICourse create() {
    return new PythonCourse();
    }
}
```
看测试代码：
``` java
public static void main(String[] args) {
    ICourseFactory factory = new PythonCourseFactory();
    ICourse course = factory.create();
    course.record();
    factory = new JavaCourseFactory();
    course = factory.create();
    course.record();
}
```
工厂方法适用于以下场景：
1、创建对象需要大量重复的代码。
2、客户端（应用层）不依赖于产品类实例如何被创建、实现等细节。
3、一个类通过其子类来指定创建哪个对象。
工厂方法也有缺点：
1、类的个数容易过多，增加复杂度。
2、增加了系统的抽象性和理解难度。
[代码示例](https://github.com/JunminZhang/pattern/blob/master/src/main/java/com/zjm/pattern/factory/factorymethod/FactoryMethodTest.java)
## 抽象工厂模式
`抽象工厂模式`（Abastract Factory Pattern）是指提供一个创建一系列相关或相互依赖对象的接口，无须指定他们具体的类。客户端（应用层）不依赖于产品类实例如何被创建、实现等细节，强调的是一系列相关的产品对象（属于同一产品族）一起使用创建对象需要大量重复的代码。需要提供一个产品类的库，所有的产品以同样的接口出现，从而使客户端不依赖于具体实现。
还是以课程为例，
每个课程不仅要提供课程的录播视频，而且还要提供老师的课堂笔记。
相当于现在的业务变更为同一个课程不单纯是一个课程信息，要同时包含录播视频、课堂笔记甚至还要提供源码才能构成一个完整的课程。在产品等级中增加两个产品
IVideo 录播视频和 INote 课堂笔记。
IVideo 接口：
```java
public interface IVideo {
void record();
}
```
INote 接口：
```java
public interface INote {
void edit();
}
```
然后创建一个抽象工厂 CourseFactory 类：
```java
/**
* 抽象工厂是用户的主入口
* 在 Spring 中应用得最为广泛的一种设计模式
* 易于扩展
*/
public interface CourseFactory {
    INote createNote();
    IVideo createVideo();
}
```
接下来，创建 Java 产品族，Java 视频 JavaVideo 类:
```java
public class JavaVideo implements IVideo {
    public void record() {
    System.out.println("录制 Java 视频");
    }
}
```
扩展产品等级 Java 课堂笔记 JavaNote 类：
```java
public class JavaNote implements INote {
    public void edit() {
    System.out.println("编写 Java 笔记");
    }
}
```
创建 Java 产品族的具体工厂 JavaCourseFactory:
```java
public class JavaCourseFactory implements CourseFactory {
    public INote createNote() {
    return new JavaNote();
}
public IVideo createVideo() {
    return new JavaVideo();
    }
}
```
然后创建 Python 产品，Python 视频 PythonVideo 类：
```java
public class PythonVideo implements IVideo {
    public void record() {
    System.out.println("录制 Python 视频");
    }
}
```
扩展产品等级 Python 课堂笔记 PythonNote 类：
```java
public class PythonNote implements INote {
    public void edit() {
    System.out.println("编写 Python 笔记");
    }
}
```
创建 Python 产品族的具体工厂 PythonCourseFactory:
```java
public class PythonCourseFactory implements CourseFactory {
    public INote createNote() {
    return new PythonNote();
    }
]
```
```java
public IVideo createVideo() {
    return new PythonVideo();
    }
}
```
来看客户端调用：
```JAVA
public static void main(String[] args) {
JavaCourseFactory factory = new JavaCourseFactory();
    factory.createNote().edit();
    factory.createVideo().record();
}
```
[代码示例](https://github.com/JunminZhang/pattern/blob/master/src/main/java/com/zjm/pattern/factory/abstractfactory/AbstractFactoryTest.java)
上面的代码完整地描述了两个产品族 Java 课程和 Python 课程，也描述了两个产品等级视频和手记。抽象工厂非常完美清晰地描述这样一层复杂的关系。但是，如果我们再继续扩展产品等级，将源码 Source 也加入到课程中，那么我们的代码从抽象工厂，到具体工厂要全部调整，很显然不符合开闭原则。因此抽象
工厂也是有缺点的：
1、规定了所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口。
2、增加了系统的抽象性和理解难度。
但在实际应用中，我们千万不能犯强迫症甚至有洁癖。在实际需求中产品等级结构升级是非常正常的一件事情。我们可以根据实际情况，只要不是频繁升级，可以不遵循开闭原则。代码每半年升级一次或者每年升级一次又有何不可呢？