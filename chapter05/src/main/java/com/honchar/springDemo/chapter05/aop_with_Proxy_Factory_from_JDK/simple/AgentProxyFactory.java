package com.honchar.springDemo.chapter05.aop_with_Proxy_Factory_from_JDK.simple;

import org.springframework.aop.framework.ProxyFactory;

public class AgentProxyFactory {
    public static void main(String[] args) {
        Agent target = new Agent();

        ProxyFactory pf = new ProxyFactory();
        pf.addAdvice(new AgentDecorator());
        pf.setTarget(target);

        Agent proxy = (Agent) pf.getProxy();

        target.speak();
        System.out.println("\n");
        proxy.speak();
    }
}