package com.majian.mask.mask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

@Component
public class MaskMessageConverter implements HttpMessageConverter<Object> {

    @Autowired
    private ObjectMapper objectMapper ;

    private static final Set<MediaType> SUPPORT_MEDIA = Sets.newHashSet(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8);


    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.isAnnotationPresent(Masks.class) && SUPPORT_MEDIA.contains(mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Lists.newArrayList(SUPPORT_MEDIA);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {

        String json = null;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        DocumentContext documentContext = JsonPath.parse(json);
        Masks masks = o.getClass().getAnnotation(Masks.class);
        for (Mask item : masks.value()) {
            documentContext.map(item.jsonPath(), new MapFunction() {
                @Override
                public Object map(Object currentValue, Configuration configuration) {
                    return MaskHelper.mask((String)currentValue, item.from(), item.to());
                }
            });
        }

        String string = documentContext.jsonString();
        outputMessage.getBody().write(string.getBytes());

    }
}
