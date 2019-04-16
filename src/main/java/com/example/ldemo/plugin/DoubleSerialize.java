package com.example.ldemo.plugin;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @package:        com.example.ldemo.plugin
 * @className:      DoubleSerialize
 * @description:    类作用描述
 * @author:         李臣臣
 * @createDate:     2019/4/16 14:15
 * @updateUser:     李臣臣
 * @updateDate:     2019/4/16 14:15
 * @updateRemark:   The modified content
 * @version:        1.0
 * <p>copyright: Copyright (c) 2019/4/16</p>
 *
 */
public class DoubleSerialize extends JsonSerializer<Double> {
    private DecimalFormat df = new DecimalFormat("0.00");
    @Override
    public void serialize(Double value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(value != null) {
            jsonGenerator.writeString(df.format(value));
        }
    }
}
