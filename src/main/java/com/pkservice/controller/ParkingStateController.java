package com.pkservice.controller;

import com.pkservice.dto.ParkingStateDto;
import com.pkservice.dto.SlotDto;
import com.pkservice.enums.State;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/parking-state")
public class ParkingStateController {

    @GetMapping
    public ResponseEntity<List<ParkingStateDto>> getParkingState(){
        // get last parking state from database
        ParkingStateDto parkingStateDto = new ParkingStateDto();
        SlotDto slotDto1 = new SlotDto();
        slotDto1.setId(0);
        slotDto1.setState(State.VACANT);
        SlotDto slotDto2 = new SlotDto();
        slotDto2.setId(1);
        slotDto2.setState(State.OCCUPIED);
        parkingStateDto.setParkingLotId(7239);
        List<SlotDto> slots = new ArrayList<>();
        slots.add(slotDto1);
        slots.add(slotDto2);
        parkingStateDto.setSlots(slots);

        return ResponseEntity.ok().body(Collections.singletonList(parkingStateDto));
    }

}
