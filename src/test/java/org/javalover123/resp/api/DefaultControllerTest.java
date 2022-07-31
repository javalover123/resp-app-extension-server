package org.javalover123.resp.api;

import org.javalover123.resp.model.DataFormatter;
import org.javalover123.resp.model.DataFormattersIdDecodePost400Response;
import org.javalover123.resp.model.DecodePayload;
import org.javalover123.resp.model.EncodePayload;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.uri.UriTemplate;
import io.micronaut.http.cookie.Cookie;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.core.type.Argument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Assertions;
import jakarta.inject.Inject;
import reactor.core.publisher.Mono;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * API tests for DefaultController
 */
@MicronautTest
public class DefaultControllerTest {

    @Inject
    EmbeddedServer server;

    @Inject
    @Client
    HttpClient client;

    @Inject
    DefaultController controller;

    /**
     * This test is used to validate the implementation of dataFormattersGet() method
     *
     * The method should: 
     *
     * Returns a list of data formatters
     *
     * TODO fill in the parameters and test return value.
     */
    @Test
    // @Disabled("Not Implemented")
    void dataFormattersGetMethodTest() {
        // given

        // when
        List<DataFormatter> result = controller.dataFormattersGet().block();

        // then
        Assertions.assertTrue(true);
    }

    /**
     * This test is used to check that the api available to client through
     * '/data-formatters' to the features of dataFormattersGet() works as desired.
     *
     * TODO fill in the request parameters and test response.
     */
    @Test
    @Disabled("Not Implemented")
    void dataFormattersGetClientApiTest() throws IOException {
        // given
        String uri = UriTemplate.of("/data-formatters").expand(new HashMap<>());
        MutableHttpRequest<?> request = HttpRequest.GET(uri)
            .accept("application/json");

        // when
        HttpResponse<?> response = client.toBlocking().exchange(request, Argument.of(List.class, DataFormatter.class));

        // then
        Assertions.assertEquals(HttpStatus.OK, response.status());
    }

    /**
     * This test is used to validate the implementation of dataFormattersIdDecodePost() method
     *
     * The method should: 
     *
     * TODO fill in the parameters and test return value.
     */
    @Test
    // @Disabled("Not Implemented")
    void dataFormattersIdDecodePostMethodTest() {
        // given
        String id = "1";
        DecodePayload decodePayload = new DecodePayload();
        decodePayload.data(Base64.getEncoder().encodeToString("1651032779850".getBytes()));

        // when
        String result = controller.dataFormattersIdDecodePost(id, decodePayload).block();

        // then
        Assertions.assertTrue(true);
    }

    /**
     * This test is used to check that the api available to client through
     * '/data-formatters/{id}/decode' to the features of dataFormattersIdDecodePost() works as desired.
     *
     * TODO fill in the request parameters and test response.
     */
    @Test
    @Disabled("Not Implemented")
    void dataFormattersIdDecodePostClientApiTest() throws IOException {
        // given
        DecodePayload body = new DecodePayload();
        String uri = UriTemplate.of("/data-formatters/{id}/decode").expand(new HashMap<String, Object>(){{
            // Fill in the path variables
            put("id", "1");
        }});
        MutableHttpRequest<?> request = HttpRequest.POST(uri, body)
            .accept("application/json");

        // when
        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        // then
        Assertions.assertEquals(HttpStatus.OK, response.status());
    }

    /**
     * This test is used to validate the implementation of dataFormattersIdEncodePost() method
     *
     * The method should: 
     *
     * TODO fill in the parameters and test return value.
     */
    @Test
    @Disabled("Not Implemented")
    void dataFormattersIdEncodePostMethodTest() {
        // given
        String id = "example";
        EncodePayload encodePayload = new EncodePayload();

        // when
        String result = controller.dataFormattersIdEncodePost(id, encodePayload).block();

        // then
        Assertions.assertTrue(true);
    }

    /**
     * This test is used to check that the api available to client through
     * '/data-formatters/{id}/encode' to the features of dataFormattersIdEncodePost() works as desired.
     *
     * TODO fill in the request parameters and test response.
     */
    @Test
    @Disabled("Not Implemented")
    void dataFormattersIdEncodePostClientApiTest() throws IOException {
        // given
        EncodePayload body = new EncodePayload();
        String uri = UriTemplate.of("/data-formatters/{id}/encode").expand(new HashMap<String, Object>(){{
            // Fill in the path variables
            put("id", "example");
        }});
        MutableHttpRequest<?> request = HttpRequest.POST(uri, body)
            .accept("application/json");

        // when
        HttpResponse<?> response = client.toBlocking().exchange(request, String.class);

        // then
        Assertions.assertEquals(HttpStatus.OK, response.status());
    }

}
