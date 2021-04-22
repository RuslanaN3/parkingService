package com.pkservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ParkingSlotService {

    public boolean saveSensorData();

    public Object saveCamData(MultipartFile image);

}
