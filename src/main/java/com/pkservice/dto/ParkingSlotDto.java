package com.pkservice.dto;

import com.opencsv.bean.CsvBindByName;
import com.pkservice.entity.DataSource;
import com.pkservice.entity.ParkingLot;
import com.pkservice.enums.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingSlotDto {

    @CsvBindByName(column = "id")
    private long id;

    @CsvBindByName(column = "slot_number")
    private long slotNumber;

    @CsvBindByName(column = "slot_state")
    private State slotState;

    @CsvBindByName(column = "parking_lot_id")
    private String parkingLotId;

    @CsvBindByName(column = "data_source_id")
    private String dataSourceId;
}
