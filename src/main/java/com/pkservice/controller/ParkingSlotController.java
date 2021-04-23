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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/parking-slot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private ModelMapper modelMapper;

    // PatchMapping("/sensors") updateParkingSlotsState
    // maybe another controller like "ParkingSlotStateController" ?
    @PostMapping("/sensors-data")
    public ResponseEntity<List<ParkingSlotDto>> saveSensorsData() throws FileNotFoundException {
        //
        File file = ResourceUtils.getFile("classpath:sensors_data.csv");
        List<ParkingSlotDto> beans = new CsvToBeanBuilder<ParkingSlotDto>(new FileReader(file))
            .withType(ParkingSlotDto.class)
            .withSeparator(';')
            .build()
            .parse();
        List<ParkingSlot> parkingSlots =
            beans.stream().map(bean -> modelMapper.map(bean, ParkingSlot.class)).collect(Collectors.toList());
        parkingSlotRepository.saveAll(parkingSlots);
        return ResponseEntity.ok().body(beans);
        //return ResponseEntity.ok().body(parkingSlotService.saveSensorData());
    }

    @PostMapping(value = "/cam-data", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity saveCamData(@RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok().body(parkingSlotService.saveCamData(image));
    }


    //should not be there
    @PostMapping(value = "/car", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity recognize(@RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok().body(parkingSlotService.recognise(image));
    }
}
