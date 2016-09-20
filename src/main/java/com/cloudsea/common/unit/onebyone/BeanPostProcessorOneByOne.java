package com.cloudsea.common.unit.onebyone;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class BeanPostProcessorOneByOne implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Map<Method, OnebyOne> methodMap = new HashMap<Method, OnebyOne>();
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(OnebyOne.class)) {
                methodMap.put(method, method.getAnnotation(OnebyOne.class));
            }
        }
        if (methodMap.size() > 0){
            return createOneByOneProxyBean(bean, methodMap);
        } else {            
            return bean;
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Object createOneByOneProxyBean(final Object bean, final Map<Method, OnebyOne> methodMap) {
        final Class<?> clazz = bean.getClass();
        Class<?>[] interfaceClasses = clazz.getInterfaces();
        ClassLoader classLoader = clazz.getClassLoader();

        if (interfaceClasses.length > 0) {
            InvocationHandler invocationHandler = new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                    return handleCallback(bean, methodMap, method, args);
                }
            };
            ProxyFactory factory = new ProxyFactory(classLoader, interfaceClasses, invocationHandler);
            return factory.createProxyInstance();

        } else {
            Callback callback = new MethodInterceptor() {
                public Object intercept(Object obj, final Method method, final Object[] args, MethodProxy proxy)
                        throws Exception {
                    return handleCallback(bean, methodMap, method, args);
                }
            };
            CglibFactory factory = new CglibFactory(clazz, callback);
            return factory.createCglibInstance();
        }
    }

    private Object handleCallback(final Object bean, final Map<Method, OnebyOne> methodMap,
            final Method method, final Object[] args) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        for (Entry<Method, OnebyOne> entry : methodMap.entrySet()){
            Method m = entry.getKey();
            OnebyOne onebyone = entry.getValue();
            if (isSameMethod(method, m)) {
                String bizId = getBizId(m, args);
                Object o = runInOneByOne(bean, method, args, onebyone.bizType(), bizId);
                return o;
            }
        }
        return method.invoke(bean, args);
    }

    private Object runInOneByOne(final Object bean, final Method method, final Object[] args, String bizType,
            String bizId) {
    	CallBack<Object> callBack = new CallBack<Object>() {
    		public Object invoke() {
    			try {
    				return method.invoke(bean, args);
    			} catch (Exception e) {
    				throw new RuntimeException(e);
    			}
    		}
    	};
    	Lock lock = new RedisLock(bizType, bizId);
    	return RunInLock.run(lock, callBack);
    }

    private boolean isSameMethod(Method method, Method m) {
        if (!method.getName().equals(m.getName())){
            return false;
        }
        Class<?>[] parameterTypes1 = method.getParameterTypes();
        Class<?>[] parameterTypes2 = m.getParameterTypes();
        if (parameterTypes1.length != parameterTypes2.length){
            return false;                        
        }
        for (int i = 0; i < parameterTypes1.length; i++){
            if (parameterTypes1[i] != parameterTypes2[i]){                            
                return false;
            }
        }
        return true;
    }

    private String getBizId(Method method, Object[] args) throws RuntimeException {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations(); // 获得所有参数注解数组
        if (parameterAnnotations.length == 0) {
            throw new RuntimeException("此方法没有参数，无法读取BizId注解");
        }
        
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotationsOfType = parameterAnnotations[i];
            if (annotationsOfType.length > 0) {
                for (Annotation annotation : annotationsOfType) {
                    if (annotation.annotationType() == BizId.class) {
                        if (!checkBizIdArg(args[i])) {
                            new RuntimeException("BizId注解的参数，不符合BizId类型要求，只能数值型和字符串型");
                        }
                        return args[i].toString();
                    }
                }
            }
        }
        throw new RuntimeException("方法参数中没有找到BizId注解");
    }

    private static boolean checkBizIdArg(Object arg) {
        if (arg == null) {
            throw new RuntimeException("判断类型时，入参数为空");
        }

        Class<?> cls = arg.getClass();
        if (cls.isPrimitive()) {
            return true;
        }

        Class<?>[] classes = new Class<?>[] { Byte.class, Character.class, Short.class, Integer.class, Long.class,
                Float.class, Double.class, String.class, StringBuffer.class, StringBuilder.class, BigInteger.class,
                BigDecimal.class };

        for (Class<?> clazz : classes) {
            if (cls == clazz) {
                return true;
            }
        }

        return false;
    }
}
