package com.majian.mask.mask;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class MaskConfig implements BeanPostProcessor {
    @Autowired
    private List<RequestMappingHandlerMapping> mappingHandlerMappings;
    @Autowired
    private MaskAgent maskAgent;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().equals(RequestMappingHandlerAdapter.class)) {
            List returnValueHandlers = new ArrayList<>();
            returnValueHandlers.add(new MaskAdvice());
            ((RequestMappingHandlerAdapter) bean).setResponseBodyAdvice(returnValueHandlers);
        }
        return bean;
    }

    @Bean
    public MaskRegistry maskRegistry() {
        MaskRegistry maskRegistry = new MaskRegistry(mappingHandlerMappings, maskAgent);
        maskRegistry.registry();
        return maskRegistry;
    }
}
