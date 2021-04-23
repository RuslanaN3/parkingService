package com.pkservice.service.impl;

import com.pkservice.repository.ParkingSlotRepository;
import com.pkservice.restClient.RestClient;
import com.pkservice.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    RestClient restClient;


    @Override
    public boolean saveSensorData() {
        return true;
    }

    @Override
    public Object saveCamData(MultipartFile image) {
        return restClient.getParkingSlotsStatePrediction(image);
    }

    @Override
    public Object recognise(MultipartFile image) {
        return restClient.getPlateRecognitionResults(image);
    }


}
