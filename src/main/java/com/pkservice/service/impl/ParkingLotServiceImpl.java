package com.pkservice.service.impl;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.dto.ParkingSlotCsvDto;
import com.pkservice.entity.ParkingLot;
import com.pkservice.entity.ParkingSlot;
import com.pkservice.repository.ParkingLotRepository;
import com.pkservice.service.ParkingLotService;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<ParkingLotDto> findAll() {
        return modelMapper.map(parkingLotRepository.findAll(), new TypeToken<List<ParkingLotDto>>() {}.getType());
    }

    //@Override
    //public List<ParkingLotDto> updateParkingLotStatusCamData(Long parkingLotId, ParkingLotDto parkingLotDto) {
    //    ParkingLot parkingLotToUpdate = parkingLotRepository.findById(parkingLotId)
    //        .orElseThrow(() -> new NoSuchElementException("Parking lot with id not found"));
    //    Set<ParkingSlotCsvDto> newParkingSlots = parkingLotDto.getParkingSlots();
    //    Set<ParkingSlot> parkingSlotsToUpdate = parkingLotToUpdate.getParkingSlots();
    //    parkingSlotsToUpdate.stream().map(ps -> ps.setSlotState())
    //
    //
    //    return null;
    //}
}
