package com.majian.mask.mask;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public class MaskAdvice implements ResponseBodyAdvice{

    private MaskAgent maskAgent;
    private RequestMappingHandlerMapping handlerMapping;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return (AnnotatedElementUtils.hasAnnotation(methodParameter.getContainingClass(), UseMask.class) || methodParameter.hasMethodAnnotation(UseMask.class));
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass,
        ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        List<MaskItem> maskItems = maskAgent.getMaskItems(serverHttpRequest, methodParameter);
        DocumentContext documentContext = JsonPath.parse(o);
        maskItems.stream()
            .forEach(item -> {
                documentContext.map(item.getJsonPath(), new MapFunction() {
                    @Override
                    public Object map(Object currentValue, Configuration configuration) {
                        return MaskHelper.maskMiddle((String)currentValue, item.getFrom(), item.getTo());
                    }
                });
            });

        return documentContext.jsonString();
    }


}
