package com.pkservice.controller;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.dto.ParkingLotSuitableDto;
import com.pkservice.service.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/parking-lot")
public class ParkingLotController {

    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity<List<ParkingLotDto>> getAllParkingLots() {
        return ResponseEntity.ok().body(parkingLotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotDto> getParkingLot(@PathVariable Long id) {
        return ResponseEntity.ok().body(parkingLotService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ParkingLotSuitableDto> getSuitableParkingLot(@RequestPart("image") MultipartFile image){
        return ResponseEntity.ok().body(parkingLotService.getSuitableParkingLot(image));
    }


}
