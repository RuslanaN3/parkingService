package com.pkservice.dto;

import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParkingLotDto {

    private long id;

    private String lotType;

    private Set<ParkingSlotDto> parkingSlots;

    private Set<BuildingDto> buildings;

    private Long vacantParkingSlotsCount;
}
