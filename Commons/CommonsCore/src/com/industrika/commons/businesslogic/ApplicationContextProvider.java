package com.industrika.commons.businesslogic;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextProvider implements ApplicationContextAware {
	
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:commons_context.xml");

    public static ApplicationContext getCtx() {
        return ctx;
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        ApplicationContextProvider.ctx = ctx;
    }
}