package com.pkservice.dto;

import com.opencsv.bean.CsvBindByName;
import com.pkservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ParkingSlotCsvDto {

    @CsvBindByName(column = "id")
    private long id;

    @CsvBindByName(column = "slot_number")
    private long slotNumber;

    @CsvBindByName(column = "slot_status")
    private Status slotStatus;

    @CsvBindByName(column = "parking_lot_id")
    private String parkingLotId;

    @CsvBindByName(column = "data_source_id")
    private String dataSourceId;
}
