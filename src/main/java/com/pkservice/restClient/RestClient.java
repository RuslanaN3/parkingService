package com.pkservice.restClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

public class RestClient {

    @Autowired
    private RestTemplate restTemplate;

    public Object getParkingSlotsStatePrediction(MultipartFile image) {
        String url = "http://localhost:5000/api/predict";

        MultiValueMap<String, Object> fileValueMap = new LinkedMultiValueMap<>();
        fileValueMap.add("file", image.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(fileValueMap, setHeaders());
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class).getBody();
    }

    public Object getPlateRecognitionResults(MultipartFile image) {
        String url = "https://api.platerecognizer.com/v1/plate-reader/";
        String token = "49618803d2b549de1dc84d8051ebb44fcdf3167a";

        HttpHeaders headers = setHeaders();
        headers.set("Authorization", "Token " + token);
        MultiValueMap<String, Object> fileValueMap = new LinkedMultiValueMap<>();
        fileValueMap.add("upload", image.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(fileValueMap, headers);
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class).getBody();
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

}
