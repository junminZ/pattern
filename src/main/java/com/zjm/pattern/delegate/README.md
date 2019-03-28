# Spring中的委派模式
`AbstractApplicationContext`的`obtainFreshBeanFactory()`方法就是通过委派模式调用了子类`AbstractRefreshableApplicationContext
`的`refreshBeanFactory()`方法
[](https://i.loli.net/2019/03/28/5c9c760e69095.png)
在`refreshBeanFactory()`方法中又通过委派模式调用了`loadBeanDefinitions`方法
[](https://i.loli.net/2019/03/28/5c9c76d85979d.png)
[](https://i.loli.net/2019/03/28/5c9c7745eba19.png)

委派模式在`DispatcherServlet`中的应用：
用户发送请求——>DispatcherServlet，前端控制器收到请求后自己不进行处理，而是委托给其他的解析器进行处理，作为统一访问点，进行全局的流程控制。

DispatcherServlet——>HandlerMapping，映射处理器将会把请求映射为HandlerExecutionChain对象（包含一个Handler处理器（页面控制器）对象、多个HandlerInterceptor拦截器）对象。

DispatcherServlet——>HandlerAdapter，处理器适配器将会把处理器包装为适配器，从而支持多种类型的处理器，即适配器设计模式的应用，从而很容易支持很多类型的处理器。

DispatcherServlet——> ViewResolver， 视图解析器将把ModelAndView对象（包含模型数据、逻辑视图名）解析为具体的View。

DispatcherServlet——>View，View会根据传进来的Model模型数据进行渲染，此处的Model实际是一个Map数据结构。

返回控制权给DispatcherServlet，由DispatcherServlet返回响应给用户，到此一个流程结束。
