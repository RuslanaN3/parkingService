package com.pkservice.dto;

import com.pkservice.entity.Section;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BuildingDto {

    private long id;

    private String buildingName;

    private Set<ParkingLotDto> parkingLot;
}
