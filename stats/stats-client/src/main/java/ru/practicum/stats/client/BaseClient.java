package ru.practicum.stats.client;

import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class BaseClient {
    protected final RestTemplate rest;

    public BaseClient(RestTemplate rest) {
        this.rest = rest;
    }

    protected ResponseEntity<Object> get(String path) {
        return get(path, null, null);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return get(path, parameters, null);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters, @Nullable Map<String, String> headersMap) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, headersMap, null);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return post(path, null, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, @Nullable Map<String, Object> parameters, T body) {
        return post(path, parameters, null, body);
    }

    protected <T> ResponseEntity<Object> post(String path, @Nullable Map<String, Object> parameters, @Nullable Map<String, String> headersMap, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, parameters, headersMap, body);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable Map<String, String> headersMap, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, createHeaders(headersMap));

        ResponseEntity<Object> statsServerResponse;
        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, Object.class);
            }
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareStatsServerResponse(statsServerResponse);
    }

    private HttpHeaders createHeaders(@Nullable Map<String, String> headersMap) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        if (headersMap != null) {
            headersMap.forEach(headers::set);
        }
        return headers;
    }

    private static ResponseEntity<Object> prepareStatsServerResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}