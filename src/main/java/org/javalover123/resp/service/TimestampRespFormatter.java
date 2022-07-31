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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Milliseconds to date time
 *
 * @author javalover123
 * @date 2022/7/30
 */
public class TimestampRespFormatter extends BaseRespFormatter {

    private static final Pattern PATTERN_TIMESTAMP = Pattern.compile("^[1-9]\\d{12,18}$");

    @Override
    protected String decode(byte[] input) throws IOException {
        String str = new String(input, StandardCharsets.UTF_8);
        str = str.replace("\"", "");
        final Matcher matcher = PATTERN_TIMESTAMP.matcher(str);
        final int length = str.length();
        if (matcher.matches() && (length == 13 || length == 19)) {
            final long mills = Long.parseLong(str.substring(0, 13));
            LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(mills), ZoneId.systemDefault());
            return JsonUtil.getFormatter().format(dateTime);
        }
        throw new IOException("not timestamp");
    }

}