package com.pkservice.service.impl;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.repository.ParkingLotRepository;
import com.pkservice.service.ParkingLotService;
import java.util.List;
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
}
