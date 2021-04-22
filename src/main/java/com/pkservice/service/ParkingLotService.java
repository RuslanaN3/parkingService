package com.pkservice.service;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.entity.ParkingLot;
import java.util.List;

public interface ParkingLotService {

    List<ParkingLotDto> findAll();
}
