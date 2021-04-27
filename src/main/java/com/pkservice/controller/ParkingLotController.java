package com.pkservice.controller;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.service.ParkingLotService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking-lot")
public class ParkingLotController {

    @Autowired
    ParkingLotService parkingLotService;

    @GetMapping
    public ResponseEntity<List<ParkingLotDto>> getAllParkingLots(){
        return ResponseEntity.ok().body(parkingLotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingLotDto>  getParkingLot(@PathVariable Long id){
        return ResponseEntity.ok().body(parkingLotService.findById(id));
    }

    //
    //@GetMapping("/{buildingId}")
    //public ResponseEntity<ParkingLotDto> getParkingLot(){
    //
    //}


}
