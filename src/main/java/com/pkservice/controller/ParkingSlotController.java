package com.pkservice.controller;

import com.opencsv.bean.CsvToBeanBuilder;
import com.pkservice.dto.ParkingSlotDto;
import com.pkservice.entity.ParkingSlot;
import com.pkservice.repository.ParkingSlotRepository;
import com.pkservice.service.ParkingSlotService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking-slot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/sensors-data")
    public ResponseEntity<List<ParkingSlotDto>> saveSensorsData() throws FileNotFoundException {
        //
        File file = ResourceUtils.getFile("classpath:sensors_data.csv");
        List<ParkingSlotDto> beans = new CsvToBeanBuilder<ParkingSlotDto>(new FileReader(file))
                                        .withType(ParkingSlotDto.class)
                                        .withSeparator(';')
                                        .build()
                                        .parse();
        List<ParkingSlot> parkingSlots = beans.stream().map(bean -> modelMapper.map(bean, ParkingSlot.class)).collect(Collectors.toList());
        parkingSlotRepository.saveAll(parkingSlots);
        return ResponseEntity.ok().body(beans);
        //return ResponseEntity.ok().body(parkingSlotService.saveSensorData());
    }

    //@PostMapping("/cam-data")
    //public ResponseEntity saveCamData() {
    //
    //
    //
    //}
}
