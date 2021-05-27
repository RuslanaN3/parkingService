package com.pkservice.dto;

import com.pkservice.enums.Status;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class ParkingSlotDto {

    private long slotNumber;

    private Status slotStatus;

}
