package top.luxcis.renthouse.serial;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import top.luxcis.renthouse.enums.CodeEnum;

import java.io.IOException;

/**
 * @author zhuang
 */
public class EnumJsonSerializer extends JsonSerializer<CodeEnum<?>> {
    @Override
    public void serialize(CodeEnum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.getCode());
    }
}
