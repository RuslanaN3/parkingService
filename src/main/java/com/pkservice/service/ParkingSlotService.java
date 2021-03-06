package com.pkservice.service;

import com.pkservice.dto.ParkingLotUpdateDto;
import org.springframework.web.multipart.MultipartFile;

public interface ParkingSlotService {

    ParkingLotUpdateDto updateParkingSlotStatuses(Long parkingLotId);

    ParkingLotUpdateDto updateParkingSlotStatuses(Long parkingLotId, MultipartFile image);
}
