package com.heyqing.disk.web.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.heyqing.disk.core.utils.IdUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * ClassName:IdEncryptSerializer
 * Package:com.heyqing.disk.web.serializer
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */
public class IdEncryptSerializer extends JsonSerializer<Long> {
    @Override
    public void serialize(Long aLong, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(aLong)){
            jsonGenerator.writeString(StringUtils.EMPTY);
        }else {
            jsonGenerator.writeString(IdUtil.encrypt(aLong));
        }
    }
}
