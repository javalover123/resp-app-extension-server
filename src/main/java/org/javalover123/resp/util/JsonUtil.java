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

package org.javalover123.resp.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Json util
 *
 * @author javalover123
 */
public class JsonUtil {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.n";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final ObjectMapper MAPPER_PRETTY = new ObjectMapper();

    public static String toJson(Object obj) {
        return toJsonStr(obj);
    }

    public static String toJson(Object obj, boolean pretty) {
        return toJsonStr(obj, pretty);
    }

    public static String toJsonStr(Object obj) {
        return toJsonStr(obj, false);
    }

    public static String toJsonStr(Object obj, boolean pretty) {
        if (obj == null) {
            return "";
        }

        try {
            final ObjectMapper mapper = pretty ? MAPPER_PRETTY : MAPPER;
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalArgumentException("对象生成json失败|" + obj, e);
        }
    }

    public static <T> T toObject(byte[] json, Class<T> clazz) {
        if (json == null || json.length == 0) {
            return null;
        }

        try {
            T result = MAPPER.readValue(new String(json, StandardCharsets.UTF_8), clazz);
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("json转为对象失败|" + json, e);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            T result = MAPPER.readValue(json, clazz);
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("json转为对象失败|" + json, e);
        }
    }

    public static LocalDateTime toLocalDateTime(String str) {
        return LocalDateTime.parse(str, formatter);
    }

    public static DateTimeFormatter getFormatter() {
        return formatter;
    }

}