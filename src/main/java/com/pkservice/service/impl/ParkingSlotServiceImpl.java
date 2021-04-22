package com.pkservice.service.impl;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.entity.ParkingSlot;
import com.pkservice.repository.ParkingSlotRepository;
import com.pkservice.service.ParkingSlotService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public boolean saveSensorData() {
        return true;
    }

    @Override
    public Object saveCamData(MultipartFile image) {


        return getPrediction(image);
    }

    private Object getPrediction(MultipartFile image) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> fileValueMap = new LinkedMultiValueMap<>();
        fileValueMap.add("file", image.getResource());


        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(fileValueMap, headers);
        String url = "http://localhost:5000/api/predict";
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class).getBody();
    }
}
