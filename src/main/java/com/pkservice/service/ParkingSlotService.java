package com.pkservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ParkingSlotService {

    boolean saveSensorData();

    Object saveCamData(MultipartFile image);

    Object recognise(MultipartFile image);
}
