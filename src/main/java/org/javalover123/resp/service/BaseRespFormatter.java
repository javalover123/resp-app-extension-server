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

import org.javalover123.resp.model.DecodePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Base64;

/**
 * redis desktop manager base formatter
 *
 * @author javalover123
 * @date 2022/7/30
 */
public abstract class BaseRespFormatter {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * decode data
     *
     * @param decodePayload data
     * @return
     */
    public String decode(DecodePayload decodePayload) throws IOException {
        byte[] input = Base64.getDecoder().decode(decodePayload.getData());
        return decode(input);
    }

    /**
     * decode data
     *
     * @param input data
     * @return
     */
    protected abstract String decode(byte[] input) throws IOException;

}