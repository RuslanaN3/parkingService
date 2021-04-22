package com.pkservice.controller;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.service.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parking-lot")
public class ParkingLotController {

    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity<List<ParkingLotDto>> getAllParkingLots(){
        return ResponseEntity.ok().body(parkingLotService.findAll());
    }

    //
    //@GetMapping("/{parkingLotId}")
    //public ResponseEntity<ParkingLotDto> getParkingLot(){
    //
    //}

    //
    //@GetMapping("/{buildingId}")
    //public ResponseEntity<ParkingLotDto> getParkingLot(){
    //
    //}


}
