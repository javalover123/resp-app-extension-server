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

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * kryo Serializer
 *
 * @author javalover123
 * @date 2022/7/31
 */
public class KryoSerializer<T> {

    private static final int BUFFER_SIZE = 5120;
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final ThreadLocal<Kryo> KRYOS = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.register(BigDecimal.class, 101);
        kryo.register(LocalDateTime.class, 102);
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    private final Class clazz;

    public KryoSerializer(Class clazz) {
        this.clazz = clazz;
    }

    public byte[] serialize(T t) {
        if (null == t) {
            return EMPTY_BYTE_ARRAY;
        }
        // -1 means no limit
        Output output = new Output(BUFFER_SIZE, -1);
        Kryo kryo = KRYOS.get();
        kryo.writeObject(output, t);
        output.flush();
        return output.toBytes();
    }

    public T deserialize(byte[] bytes) {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        Input input = new Input(bytes);
        Kryo kryo = KRYOS.get();
        T t = (T) kryo.readObject(input, clazz);
        input.close();
        return t;
    }

    @Override
    public String toString() {
        return "KryoSerializer{" +
                "clazz=" + clazz +
                '}';
    }

}
