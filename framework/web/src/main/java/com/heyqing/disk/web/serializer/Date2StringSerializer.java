package com.heyqing.disk.web.serializer;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * ClassName:Date2StringSerializer
 * Package:com.heyqing.disk.web.serializer
 * Description:
 *
 * @Date:2024/1/6
 * @Author:Heyqing
 */
public class Date2StringSerializer extends JsonSerializer<Data> {
    @Override
    public void serialize(Data data, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (Objects.isNull(data)){
            jsonGenerator.writeString(StringUtils.EMPTY);
        }else{
            jsonGenerator.writeString(DateUtil.formatDateTime((Date) data));
        }
    }
}
