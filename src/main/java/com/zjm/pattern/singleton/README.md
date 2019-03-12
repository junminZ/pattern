---
title: 单例模式
date: 2019-03-11 14:46:00
tags: 设计模式
categories: 设计模式
---
# 单例模式
## 单例模式的应用场景
`单例模式`（Singleton Pattern）是指确保一个类在任何情况下都绝对只有一个实例，并提供一个全局访问点。单例模式是创建型模式。单例模式在现实生活中应用也非常广泛。例如，国家主席、公司 CEO、部门经理等。在 J2EE 标准`ServletContext`、
`ServletContextConfig` 等；在 Spring 框架应用中`ApplicationContext`；数据库的连接池也都是单例形式。
## 饿汉式单例
饿汉式单例是在类加载的时候就立即初始化，并且创建单例对象。绝对线程安全，在线程还没出现以前就是实例化了，不可能存在访问安全问题。
优点：没有加任何的锁、执行效率比较高，在用户体验上来说，比懒汉式更好。
缺点：类加载的时候就初始化，不管用与不用都占着空间，浪费了内存，有可能占着茅坑不拉屎。
类图：![](https://i.loli.net/2019/03/11/5c860b8758021.png)
Spring 中 IOC 容器 ApplicationContext 本身就是典型的饿汉式单例。接下来看一段代码：
```java
public class HungrySing {
    private HungrySing() { }

    private static final HungrySing hungrySing = new HungrySing();

    public static HungrySing getInstance() {
        return hungrySing;
    }
}
```
另外一种写法，利用静态代码块的机制：
```java
public class HungrySing {
    private HungrySing() { }

    private static final HungrySing hungrySing ;

    static {
        hungrySing =  new HungrySing();
    }

    public static HungrySing getInstance() {
        return hungrySing;
    }
}
```
<!-- more -->
## 懒汉式单例
`懒汉式单例`的特点是：`被外部类调用的时候内部类才会加载`，下面看懒汉式单例的简单实现
```java
public class LazySimpleSing {
    private LazySimpleSing() {}

    private static LazySimpleSing lazySing;

    public static LazySimpleSing getInstance() {
        if (lazySing == null) {
            lazySing = new LazySimpleSing();
        }
        return lazySing;
    }
}
```
然后写一个线程类 ExectorThread 类：
```java
public class ExectorThread implements Runnable{
    public void run() {
        LazySimpleSing lazySimpleSing = LazySimpleSing.getInstance();
        System.out.println(Thread.currentThread().getName()+":----"+lazySimpleSing);
    }
}
```
客户端测试代码：
```java
public class LazySimpleSignTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());

        t1.start();
        t2.start();

        System.out.println("=======================");
    }
}
```
运行结果：
![](https://i.loli.net/2019/03/11/5c860b874dc97.png)
一定几率出现创建两个不同结果的情况，意味着上面的单例存在线程安全隐患。然后使用调试运行再具体看一下，使用IDEA线程模式调试，手动控制线程的执行顺序来跟踪内存的变化状态。先给 ExectorThread 类打上断点：
![](https://i.loli.net/2019/03/11/5c863362cbfe1.png)
右键点击断点，切换为 Thread 模式，如下图：
![](https://i.loli.net/2019/03/11/5c863362cecc9.png)
然后，给 LazySimpleSing 类打上断点，同样标记为 Thread 模式：
![](https://i.loli.net/2019/03/11/5c863362cd1a8.png)
切回到客户端测试代码，同样也打上断点，同时改为 Thread 模式，如下图:
![](https://i.loli.net/2019/03/11/5c863362cd6ff.png)
开始 debug 之后，会看到 debug 控制台可以自由切换 Thread 的运行状态：
![](https://i.loli.net/2019/03/11/5c863362c9902.png)
通过不断切换线程，并观测其内存状态，我们发现在线程环境下 LazySimpleSing。有时，我们得到的运行结果可能是相同的两个对象，实际上是被后面执行的线程覆盖了，我们看到了一个假象，线程安全隐患依旧存在。那么，我们如何来优化代码，使得懒汉式单例在线程环境下安全呢？来看下面的代码，给 getInstance()加上 synchronized 关键字，使这个方法变成线程同步方法：
```java
public class LazySimpleSing {
    private LazySimpleSing() {}

    private static LazySimpleSing lazySing;

    public synchronized static LazySimpleSing getInstance() {
        if (lazySing == null) {
            lazySing = new LazySimpleSing();
        }
        return lazySing;
    }
}
```
这时候，我们再来调试。当我们将其中一个线程执行并调用 getInstance()方法时，另一个线程在调用 getInstance()方法，线程的状态由 RUNNING 变成了 `MONITOR`,出现阻塞。直到第一个线程执行完，第二个线程才恢复 RUNNING 状态继续调用 getInstance()方法。如下图所示：
![](https://i.loli.net/2019/03/11/5c86336349cf4.png)
完美的展现了 `synchronized 监视锁`的运行状态，线程安全的问题便解决了。但是，用synchronized 加锁，在线程数量比较多情况下，如果 CPU 分配压力上升，会导致大批量线程出现阻塞，从而导致程序运行性能大幅下降。那么，有没有一种更好的方式，既兼顾线程安全又提升程序性能呢？答案是肯定的。我们来看双重检查锁的单例模式：
```java
public class LazyDoubleCheckSing {
    private LazyDoubleCheckSing() {}

    private volatile static LazyDoubleCheckSing lazySing;

    public static LazyDoubleCheckSing getInstance() {
        if (lazySing == null) {
            synchronized (LazyDoubleCheckSing.class) {
                if (lazySing == null) {
                    lazySing = new LazyDoubleCheckSing();
                }
            }
        }
        return lazySing;
    }
}
```
当第一个线程调用 getInstance()方法时，第二个线程也可以调用 getInstance()。当第一个线程执行到 synchronized 时会上锁，第二个线程就会变成 `MONITOR` 状态，出现阻塞。此时，阻塞并不是基于整个 LazySimpleSing 类的阻塞，而是在 getInstance()方法内部阻塞，只要逻辑不是太复杂，对于调用者而言感知不到。
但是，用到 synchronized 关键字，总归是要上锁，对程序性能还是存在一定影响的。难道就真的没有更好的方案吗？当然是有的。我们可以从类初始化角度来考虑，看下面的代码，采用静态内部类的方式：
```java
public class LazyInnerClassSing {

    private LazyInnerClassSing(){}

    public static final LazyInnerClassSing getInstance() {
        return LazyHolder.LAZY;
    }

    private static class LazyHolder {
        private static final LazyInnerClassSing LAZY = new LazyInnerClassSing();
    }
}
```
## 反射破坏单例
上面介绍的单例模式的构造方法除了加上 private 以外，没有做任何处理。如果我们使用反射来调用其构造方法，然后，再调用 getInstance()方法，应该就会两个不同的实例。现在来看一段测试代码，以 LazyInnerClassSingleton 为例：
```java
public class LazyInnerClassSingTest {
    public static void main(String[] args) {
        try{
        //很无聊的情况下，进行破坏
        Class<?> clazz = LazyInnerClassSing.class;
        //通过反射拿到私有的构造方法
        Constructor c = clazz.getDeclaredConstructor(null);
        //强制访问，强吻，不愿意也要吻
        c.setAccessible(true);
        //暴力初始化
        Object o1 = c.newInstance();
        //调用了两次构造方法，相当于 new 了两次
        //犯了原则性问题，
        Object o2 = c.newInstance();
        System.out.println(o1 == o2);
        // Object o2 = c.newInstance();
        }catch (Exception e){
        e.printStackTrace();
        }
    }
}
```
现在，我们在其构造方法中做一些限制，一旦出现多次重复创建，则直接抛出异常。来看优化后的代码：
```java
public class LazyInnerClassSing {
    private LazyInnerClassSing(){
        if(LazyHolder.LAZY != null){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static final LazyInnerClassSing getInstance() {
        return LazyHolder.LAZY;
    }

    private static class LazyHolder {
        private static final LazyInnerClassSing LAZY = new LazyInnerClassSing();
    }
}
```
上述写法可以防止被反射恶意或意外破坏单例
## 序列化破坏单例
当我们将一个单例对象创建好，有时候需要将对象序列化然后写入到磁盘，下次使用时再从磁盘中读取到对象，反序列化转化为内存对象。反序列化后的对象会重新分配内存，即重新创建。那如果序列化的目标的对象为单例对象，就违背了单例模式的初衷，相当于破坏了单例，来看一段代码：
```java
public class SeriableSing implements Serializable {

        public final static SeriableSing INSTANCE = new SeriableSing();

        private SeriableSing(){}

        public static SeriableSing getInstance(){
            return INSTANCE;
        }

}
```
编写测试代码：
```java
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
```
运行结果：
![](https://i.loli.net/2019/03/11/5c863363392c9.png)
运行结果中，可以看出，反序列化后的对象和手动创建的对象是不一致的，实例化了两次，违背了单例的设计初衷。那么，我们如何保证序列化的情况下也能够实现单例？其实很简单，只需要增加 readResolve()方法即可。来看优化代码：
```java
public class SeriableSing implements Serializable {

        public final static SeriableSing INSTANCE = new SeriableSing();

        private SeriableSing(){}

        public static SeriableSing getInstance(){
            return INSTANCE;
        }
        private Object readResolve(){
            return INSTANCE;
        }
}
```
再次运行结果：
![](https://i.loli.net/2019/03/11/5c86336335857.png)
这是什么原因呢？为什么要这样写？看上去很神奇的样子，也让人有些费解。进入JDK源码ObjectInputStream 类的 readObject()方法，代码如下：
![](https://i.loli.net/2019/03/11/5c8635a292905.png)
我们发现在readObject中又调用了我们重写的readObject0()方法。进入readObject0()方法，代码如下
![](https://i.loli.net/2019/03/11/5c8635a275a63.png)
我们看到 TC_OBJECTD 中判断，调用了 ObjectInputStream 的 readOrdinaryObject()方法，我们继续进入看源码：
![](https://i.loli.net/2019/03/11/5c8635a28e1a8.png)
发现调用了 ObjectStreamClass 的 isInstantiable()方法，而 isInstantiable()里面的代码如下：
![](https://i.loli.net/2019/03/11/5c8635a26a85f.png)
代码非常简单，就是判断一下构造方法是否为空，构造方法不为空就返回 true。意味着，只要有无参构造方法就会实例化。这时候，其实还没有找到为什么加上 readResolve()方法就避免了单例被破坏的真正原因。我再回到 ObjectInputStream 的readOrdinaryObject()方法继续往下看：
![](https://i.loli.net/2019/03/11/5c8635a279e10.png)
判断无参构造方法是否存在之后，又调用了 hasReadResolveMethod()方法，来看代码：
![](https://i.loli.net/2019/03/11/5c8635a2c4cb2.png)
逻辑非常简单，就是判断 readResolveMethod 是否为空，不为空就返回 true。那么readResolveMethod 是在哪里赋值的呢？通过全局查找找到了赋值代码在私有方法ObjectStreamClass()方法中给 readResolveMethod 进行赋值，来看代码：
![](https://i.loli.net/2019/03/11/5c8635a2d7392.png)
上面的逻辑其实就是通过反射找到一个无参的 readResolve()方法，并且保存下来。现在再回到ObjectInputStream的 readOrdinaryObject()方法继续往下看，如果readResolve()存在则调用 invokeReadResolve()方法，来看代码：
![](https://i.loli.net/2019/03/11/5c8635a3045ae.png)
我们可以看到在invokeReadResolve()方法中用反射调用了readResolveMethod方法。通过 JDK 源码分析我们可以看出，虽然，增加 readResolve()方法返回实例，解决了单例被破坏的问题。但是，我们通过分析源码以及调试，我们可以看到实际上实例化了两次，只不过新创建的对象没有被返回而已。创建对象的动作发生频率增大，就意味着内存分配开销也就随之增大，下面介绍一种注册式单例，也许能从根本上解决问题。
## 注册式单例
`注册式单例`又称为`登记式单例`，就是将每一个实例都登记到某一个地方，使用唯一的标识获取实例。注册式单例有两种写法：一种为`容器缓存`，一种为`枚举登记`。先来看枚举式单例的写法，来看代码，创建 `EnumSingleton` 类：
```java
public enum EnumSing {

        INSTANCE;
        private Object data;

        public Object getData() {
            return data;
        }
        public void setData(Object data) {
            this.data = data;
        }
        public static EnumSing getInstance(){
            return INSTANCE;
        }
}
```
来看测试代码：
![](https://i.loli.net/2019/03/11/5c8676d54b973.png)
没有做任何处理，我们发现运行结果和我们预期的一样。那么枚举式单例如此神奇，它的神秘之处在哪里体现呢？下面我们通过分析源码来揭开它的神秘面纱。
使用 Java 反编译工具 Jad（下载地址：https://varaneckas.com/jad/ ） 反编译EnumSing.class可以看到：
![](https://i.loli.net/2019/03/11/5c8676d51bcba.png)
原来，枚举式单例在静态代码块中就给 INSTANCE 进行了赋值，是饿汉式单例的实现。
那么序列化我们能否破坏枚举式单例呢？我们不妨再来看一下 JDK源码，还是回到 ObjectInputStream 的 readObject0()方法：
![](https://i.loli.net/2019/03/11/5c8676d5174ea.png)
我们看到在 readObject0()中调用了 readEnum()方法，来看 readEnum()中代码实现：
![](https://i.loli.net/2019/03/11/5c8676d53ea28.png)
我们发现枚举类型其实通过类名和 Class 对象类找到一个唯一的枚举对象。因此，枚举对象不可能被类加载器加载多次。
下面来看看反射是否能破坏枚举式单例：
```java
 public static void main(String[] args) {
            try {
                Class clazz = EnumSing.class;
                Constructor c = clazz.getDeclaredConstructor();
                c.newInstance();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
```
运行结果：
![](https://i.loli.net/2019/03/11/5c8676d53366f.png)
报的是 java.lang.NoSuchMethodException 异常，意思是没找到无参的构造方法。这时候，我们打开 java.lang.Enum 的源码代码，查看它的构造方法，只有一个 protected的构造方法，代码如下：
``` C
protected Enum(String name, int ordinal) {
    this.name = name;
    this.ordinal = ordinal;
}
```
那我们再来做一个这样的测试：
```java
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
```
运行结果：
![](https://i.loli.net/2019/03/11/5c8676d576b5c.png)
这时错误已经非常明显了，告诉我们 Cannot reflectively create enum objects不能用反射来创建枚举类型。还是习惯性地想来看看 JDK 源码，进入 Constructor 的newInstance()方法：
![](https://i.loli.net/2019/03/11/5c8676d5a9bec.png)
在 newInstance()方法中做了强制性的判断，如果修饰符是 Modifier.ENUM 枚举类型，直接抛出异常。到这为止，我们是不是已经非常清晰明了呢？枚举式单例也是《EffectiveJava》书中推荐的一种单例实现写法。在 JDK 枚举的语法特殊性，以及反射也为枚举保驾护航，让枚举式单例成为一种比较优雅的实现。
## 容器式单例
`容器式单例`是`注册式单例`的另一种写法，首先创建 ContainerSing 类：
```java
public class ContainerSing {

    private static Map<String, Object> ioc = new ConcurrentHashMap<String, Object>();

    public static Object getBean(String className) {
        synchronized (ioc) {
            if (!ioc.containsKey(className)) {
                Object object = null;
                try {
                    object = Class.forName(className).newInstance();
                    ioc.put(className, object);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return object;
            } else {
                return ioc.get(className);
            }
        }
    }
}
```
Spring中的容器式单例的实现代码：
```java
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
implements AutowireCapableBeanFactory {
    /** Cache of unfinished FactoryBean instances: FactoryBean name --> BeanWrapper */
    private final Map<String, BeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(16);
    ...
}
```
## ThreadLocal 线程单例
ThreadLocal 不能保证其创建的对象是全局唯一，但是能保证在单个线程中是唯一的，天生的线程安全。
代码示例：
```java
public class ThreadLocalSing {
    private static final ThreadLocal<ThreadLocalSing> threadLocalInstance = new ThreadLocal<ThreadLocalSing>(){
        @Override
        protected ThreadLocalSing initialValue() {
            return new ThreadLocalSing();
        }
    };
    private ThreadLocalSing(){}

    public static ThreadLocalSing getInstance() {
        return threadLocalInstance.get();
    }
}
```
ThreadLocal将所有的对象全部放在 ThreadLocalMap 中，为每个线程都提供一个对象，实际上是以空间换时间来实现线程间隔离的。