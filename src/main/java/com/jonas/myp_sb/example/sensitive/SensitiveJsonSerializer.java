package com.jonas.myp_sb.example.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.jonas.myp_sb.annotation.Sensitive;

import java.io.IOException;
import java.util.Objects;

public class SensitiveJsonSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveStrategy strategy;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(strategy.getDesensitizer().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        Sensitive annotation = beanProperty.getAnnotation(Sensitive.class);
        if(Objects.nonNull(annotation) && Objects.equals(String.class, beanProperty.getType().getRawClass())){
            this.strategy = annotation.strategy();
            return this;
        }
        return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
    }
}
