package com.pkservice.controller;


import com.pkservice.dto.ParkingLotUpdateDto;
import com.pkservice.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/parking-slot")
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    @PatchMapping(value = "/sensors-data/{parkingLotId}")
    public ResponseEntity<ParkingLotUpdateDto> updateParkingSlotStatusSensorsData(@PathVariable Long parkingLotId) {
        return ResponseEntity.ok().body(parkingSlotService.updateParkingSlotStatuses(parkingLotId));
    }

    @PatchMapping(value = "/cam-data/{parkingLotId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ParkingLotUpdateDto> updateParkingSlotStatusCamData(@RequestPart("image") MultipartFile image,
                                                                              @PathVariable Long parkingLotId) {
        return ResponseEntity.ok().body(parkingSlotService.updateParkingSlotStatuses(parkingLotId, image));
    }

}
