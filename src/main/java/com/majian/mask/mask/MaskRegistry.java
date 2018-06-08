package com.majian.mask.mask;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class MaskRegistry {

    private List<RequestMappingHandlerMapping> mappingHandlerMappings;
    private MaskAgent maskAgent;

    public MaskRegistry(
        List<RequestMappingHandlerMapping> mappingHandlerMappings,
        MaskAgent maskAgent) {
        this.mappingHandlerMappings = mappingHandlerMappings;
        this.maskAgent = maskAgent;
    }

    /**
     * 启动时将带有@UseMask的接口注册到配置处
     */
    public void registry() {
        for (RequestMappingHandlerMapping mappingHandlerMapping : mappingHandlerMappings) {

            Map<RequestMappingInfo, HandlerMethod> handlerMethods = mappingHandlerMapping.getHandlerMethods();
            for (Entry<RequestMappingInfo, HandlerMethod> entey : handlerMethods
                .entrySet()) {
                HandlerMethod handlerMethod = entey.getValue();
                if (handlerMethod.hasMethodAnnotation(UseMask.class)) {
                    String[] patterns = getPaths(handlerMethod);
                    String[] methods = getHttpMethods(handlerMethod);
                    RegistryEntity registryEntity = new RegistryEntity();
                    registryEntity.setPaths(patterns);
                    registryEntity.setMethods(methods);
                    maskAgent.register(registryEntity);
                }
            }
        }
    }

    private String[] getHttpMethods(HandlerMethod handlerMethod) {
        // TODO: 2018/6/8
        return new String[0];
    }

    private String[] getPaths(HandlerMethod handlerMethod) {
        // TODO: 2018/6/8
        return new String[0];
    }

}
