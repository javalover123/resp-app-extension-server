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

package org.javalover123.resp.service;

import org.javalover123.resp.util.JsonUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * json milliseconds to date time
 *
 * @author javalover123
 * @date 2022/7/31
 */
public class JsonRespFormatter extends BaseRespFormatter {

    private static final Pattern PATTERN_TIMESTAMP = Pattern.compile("^[1-9]\\d{12,18}$");

    @Override
    public String decode(byte[] input) throws IOException {
        String str = new String(input, StandardCharsets.UTF_8).trim();
        log.debug("decode input|{}", str);
        if (str.startsWith("{")) {
            Map<String, Object> map = JsonUtil.toObject(str, Map.class);
            map = (Map<String, Object>) handleValue(map);
            return JsonUtil.toJson(map);
        } else if (str.startsWith("[")) {
            List<Object> list = JsonUtil.toObject(str, List.class);
            list = (List<Object>) handleValue(list);
            return JsonUtil.toJson(list);
        }
        throw new IOException("not timestamp");
    }

    Object handleValue(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) value;
            for (Entry<String, Object> entry : map.entrySet()) {
                final Object temp = entry.getValue();
                entry.setValue(handleValue(temp));
            }
        } else if (value instanceof List) {
            List<Object> list = (List<Object>) value;
            for (ListIterator<Object> iterator = list.listIterator(); iterator.hasNext(); ) {
                Object temp = iterator.next();
                iterator.set(handleValue(temp));
            }
        } else {
            return handleTimestamp(value);
        }
        return value;
    }

    Object handleTimestamp(Object value) {
        String str = Objects.toString(value, "").replace("\"", "");
        final Matcher matcher = PATTERN_TIMESTAMP.matcher(str);
        final int length = str.length();
        if (matcher.matches() && (length == 13 || length == 19)) {
            final long mills = Long.parseLong(str.substring(0, 13));
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(mills), ZoneId.systemDefault());
            return dateTime;
        }
        return value;
    }

}