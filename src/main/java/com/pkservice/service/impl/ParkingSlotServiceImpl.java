package com.pkservice.service.impl;

import com.pkservice.entity.ParkingSlot;
import com.pkservice.repository.ParkingSlotRepository;
import com.pkservice.service.ParkingSlotService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Override
    public boolean saveSensorData() {
        return true;
    }
}
