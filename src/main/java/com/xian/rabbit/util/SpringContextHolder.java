package com.xian.rabbit.util;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext
 * 可在任何代码任何地方任何时候取出ApplicaitonContext.
 */
@Service
public class SpringContextHolder implements ApplicationContextAware, BeanFactoryPostProcessor, DisposableBean {

    private static ApplicationContext applicationContext = null;
    private static ConfigurableListableBeanFactory beanFactory = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 获取字符串key
     *
     * @param key
     * @return
     */
    public static String getProperty(String key, String defaultValue) {
        return applicationContext.getEnvironment().getProperty(key, defaultValue);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static <T> Map <String, T> getBeansOfTypeMap(Class<T> baseType){
        return applicationContext.getBeansOfType(baseType);
    }

    public static <T> List<T> getBeansOfTypeList(Class<T> baseType){
        return new ArrayList <>( applicationContext.getBeansOfType(baseType).values() );
    }


    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearHolder() {
        if (logger.isDebugEnabled()) {
            logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
        }
        applicationContext = null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 实现DisposableBean接口, 在Context关闭时清理静态变量.
     */
    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void assertContextInjected() {
        Validate.validState(applicationContext != null, "SpringContextHolder中的applicaitonContext对象未能获取.");
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringContextHolder.beanFactory = configurableListableBeanFactory;
    }

    /**
     * 将bean对象注册到bean工厂
     *
     * @param beanName
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> boolean registerBean(String beanName, T bean) {
        // 将bean对象注册到bean工厂
        beanFactory.registerSingleton(beanName, bean);
        return true;
    }
}