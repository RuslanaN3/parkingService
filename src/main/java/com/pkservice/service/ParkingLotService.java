package com.pkservice.service;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.dto.ParkingLotSuitableDto;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ParkingLotService {

    List<ParkingLotDto> findAll();

    ParkingLotDto findById(Long id);

    ParkingLotSuitableDto getSuitableParkingLot(MultipartFile image);
}
