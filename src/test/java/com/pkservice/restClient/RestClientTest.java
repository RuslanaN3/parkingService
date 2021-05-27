package com.pkservice.restClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pkservice.dto.ParkingLotUpdateDto;
import com.pkservice.dto.PlateRecognitionResponse;
import com.pkservice.dto.PlateResultDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RestClientTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    RestClient restClient;

    @Test
    void getParkingSlotsStatusPrediction() {
        MockMultipartFile image = new MockMultipartFile("image", "image".getBytes());
        MultiValueMap<String, Object> fileValueMap = new LinkedMultiValueMap<>();
        fileValueMap.add("file", image.getResource());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(fileValueMap, headers);
        ParkingLotUpdateDto parkingLotUpdateDto = new ParkingLotUpdateDto();

        when(restTemplate.exchange("http://localhost:5000/api/predict", HttpMethod.POST, requestEntity, JsonNode.class))
            .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(objectMapper.convertValue(null, ParkingLotUpdateDto.class)).thenReturn(parkingLotUpdateDto);

        ParkingLotUpdateDto actual = restClient.getParkingSlotsStatusPrediction(image);

        assertEquals(parkingLotUpdateDto, actual);
        verify(restTemplate)
            .exchange("http://localhost:5000/api/predict", HttpMethod.POST, requestEntity, JsonNode.class);
    }

    @Test
    void getPlateRecognitionResults() {
        MockMultipartFile image = new MockMultipartFile("image", "image".getBytes());
        String url = "https://api.platerecognizer.com/v1/plate-reader/";
        String token = "49618803d2b549de1dc84d8051ebb44fcdf3167a";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", "Token " + token);
        MultiValueMap<String, Object> fileValueMap = new LinkedMultiValueMap<>();
        fileValueMap.add("upload", image.getResource());
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(fileValueMap, headers);
        PlateResultDto plateResultDto1 = PlateResultDto.builder().plate("plate").score(0.85).build();
        PlateResultDto expected = PlateResultDto.builder().plate("plate").score(0.96).build();
        PlateRecognitionResponse plateRecognitionResponse =
            PlateRecognitionResponse.builder().plateResults(List.of(plateResultDto1, expected)).build();

        when(restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonNode.class))
            .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        when(objectMapper.convertValue(null, PlateRecognitionResponse.class))
            .thenReturn(plateRecognitionResponse);

        PlateResultDto actual = restClient.getPlateRecognitionResults(image);

        assertEquals(expected, actual);
        verify(restTemplate).exchange(url, HttpMethod.POST, requestEntity, JsonNode.class);
    }
}