package com.pkservice.dto;

import java.util.Set;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParkingLotUpdateDto {

    private long id;

    private Set<ParkingSlotDto> parkingSlots;

}

