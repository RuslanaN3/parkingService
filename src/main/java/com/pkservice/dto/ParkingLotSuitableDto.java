package com.pkservice.dto;

import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParkingLotSuitableDto {

    Long suitableParkingLotId;

    Long suitableParkingSlotNumber;

    Set<ParkingLotDto> parkingLotDtos;
}
