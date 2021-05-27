package com.pkservice.dto;

import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"parkingLotDtos"})
@Builder
public class ParkingLotSuitableDto {

    Long suitableParkingLotId;

    Long suitableParkingSlotNumber;

    Set<ParkingLotDto> parkingLotDtos;

    Boolean plateNotDetected;

    Boolean plateNotPresentInDB;
}
