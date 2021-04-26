package com.pkservice.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingLotDto {

    private long id;

    private String lotType;

    private Set<ParkingSlotCsvDto> parkingSlots;

    private Set<BuildingDto> buildings;
}
