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
import org.javalover123.resp.util.KryoSerializer;

import java.io.IOException;
import java.util.Arrays;

/**
 * kryo to json formatter
 *
 * @author javalover123
 * @date 2022/7/31
 */
public class KryoRespFormatter extends BaseRespFormatter {

    private KryoSerializer kryoSerializer;

    public KryoRespFormatter(Class clazz) {
        // log = buildLog();
        this.kryoSerializer = new KryoSerializer(clazz);
    }

    @Override
    public String decode(byte[] input) throws IOException {
        try {
            log.debug(kryoSerializer + "|kryo decode input|" + Arrays.toString(input));
            final Object result = kryoSerializer.deserialize(input);
            log.debug(kryoSerializer + "|kryo decode result|{}", result);
            return JsonUtil.toJson(result);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new IOException(kryoSerializer + "|kryo decode error|" + Arrays.toString(input), e);
        }
    }

}