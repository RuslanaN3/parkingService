package com.pkservice.service;

import com.pkservice.dto.ParkingLotDto;
import java.util.List;

public interface ParkingLotService {

    List<ParkingLotDto> findAll();

    //List<ParkingLotDto> updateParkingLotStatusCamData(Long parkingLotId, ParkingLotDto parkingLotDto);
}
