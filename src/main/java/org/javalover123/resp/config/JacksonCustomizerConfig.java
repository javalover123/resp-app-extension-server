/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.javalover123.resp.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * jackson全局配置java8 LocalDateTime的序列化 全局配置时间返回格式
 *
 * @author javalover123
 */
// @Configuration
public class JacksonCustomizerConfig {

    private static final Logger log = LoggerFactory.getLogger(JacksonCustomizerConfig.class);

    public static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(8);

    /**
     * description:适配自定义序列化和反序列化策略
     */
    public static ObjectMapper customize(ObjectMapper mapper, String dateFormat) {
        SimpleModule module = new SimpleModule();
        module.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        module.addSerializer(Double.class, new DoubleSerializer());
        module.addSerializer(LocalTime.class, new LocalTimeSerializer());
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateFormat));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateFormat));

        mapper.registerModule(module);
        return mapper;
    }

    /**
     * description:序列化 BigDecimal序列化为字符串
     */
    public static class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

        @Override
        public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                gen.writeNumber(value.setScale(6, RoundingMode.HALF_DOWN).toString());
            }
        }
    }

    /**
     * description:序列化 BigDecimal序列化为字符串
     */
    public static class DoubleSerializer extends JsonSerializer<Double> {

        @Override
        public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value != null) {
                gen.writeNumber(new BigDecimal(value.toString()).toString());
            }
        }
    }

    /**
     * description:序列化 LocalDate序列化为毫秒级时间戳
     */
    public static class LocalDateSerializer extends JsonSerializer<LocalDate> {

        @Override
        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                long timestamp = value.atStartOfDay().toInstant(ZONE_OFFSET).toEpochMilli();
                gen.writeNumber(timestamp);
            }
        }
    }

    public static class LocalTimeSerializer extends JsonSerializer<LocalTime> {
        @Override
        public void serialize(LocalTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value != null) {
                String str = value.format(DateTimeFormatter.ISO_LOCAL_TIME);
                gen.writeString(str);
            }
        }
    }

    public static class LocalTimeDeserializer extends JsonDeserializer<LocalTime> {

        @Override
        public LocalTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            String str = p.getValueAsString();
            if (str == null || str.isEmpty()) {
                return null;
            }
            return LocalTime.parse(str, DateTimeFormatter.ISO_LOCAL_TIME);
        }
    }


    /**
     * description:反序列化 毫秒级时间戳序列化为LocalDate
     */
    public static class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            String str = p.getValueAsString();
            if (str == null || str.isEmpty()) {
                return null;
            }
            long timestamp = Long.parseLong(str);
            if (timestamp > 0) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZONE_OFFSET).toLocalDate();
            } else {
                return null;
            }
        }
    }

    /**
     * description:序列化 LocalDateTime序列化为毫秒级时间戳
     */
    public static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

        private String format;

        private DateTimeFormatter formatter;

        public LocalDateTimeSerializer() {
        }

        public LocalDateTimeSerializer(String format) {
            this.format = format;
            if (format != null && !format.trim().isEmpty()) {
                formatter = DateTimeFormatter.ofPattern(Objects.requireNonNull(format));
            }
        }

        @Override
        public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            if (value == null) {
                return;
            }

            if (formatter == null) {
                long timestamp = value.toInstant(ZONE_OFFSET).toEpochMilli();
                gen.writeNumber(timestamp);
            } else {
                String str = formatter.format(value);
                gen.writeString(str);
            }
        }
    }

    /**
     * description:反序列化 毫秒级时间戳序列化为LocalDateTime
     */
    public static class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

        private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(
                DEFAULT_DATE_FORMAT);

        private String format;

        private DateTimeFormatter formatter;

        public LocalDateTimeDeserializer() {
        }

        public LocalDateTimeDeserializer(String format) {
            this.format = format;
            if (format != null && !format.trim().isEmpty()) {
                formatter = DateTimeFormatter.ofPattern(Objects.requireNonNull(format));
            }
        }

        @Override
        public LocalDateTime deserialize(JsonParser p, DeserializationContext deserializationContext)
                throws IOException {
            String str = p.getValueAsString();
            if (str == null || str.isEmpty()) {
                return null;
            }

            DateTimeFormatter tempFormatter = formatter;
            if (DEFAULT_DATE_FORMAT.length() == str.length()) {
                tempFormatter = DEFAULT_DATE_FORMATTER;
            }
            if (tempFormatter != null && format.length() <= str.length()) {
                try {
                    return LocalDateTime.parse(str, tempFormatter);
                } catch (Exception e) {
                    log.error(str + ",LocalDateTimeDeserializer error," + e);
                    return null;
                }
            }

            long timestamp = Long.parseLong(str);
            if (timestamp > 0) {
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
            } else {
                return null;
            }
        }
    }

}
