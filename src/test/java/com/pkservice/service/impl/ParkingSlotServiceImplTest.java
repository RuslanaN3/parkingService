package com.pkservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import com.pkservice.dto.ParkingLotUpdateDto;
import com.pkservice.dto.ParkingSlotDto;
import com.pkservice.entity.ParkingSlot;
import com.pkservice.enums.Status;
import com.pkservice.repository.ParkingSlotRepository;
import com.pkservice.restClient.RestClient;
import com.pkservice.service.ParkingLotService;
import com.pkservice.service.ParkingSlotService;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.mock.web.MockMultipartFile;


@ExtendWith(MockitoExtension.class)
class ParkingSlotServiceImplTest {

    @Mock
    RestClient restClient;

    @Mock
    ModelMapper modelMapper;

    @Mock
    ParkingSlotRepository parkingSlotRepository;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @Mock
    ParkingLotService parkingLotService;


    @InjectMocks
    ParkingSlotServiceImpl parkingSlotService;

    @Test
    void updateParkingSlotStatuses() {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "file".getBytes());
        ParkingSlotDto parkingSlotDto = ParkingSlotDto.builder().slotNumber(576).slotStatus(Status.VACANT).build();
        ParkingLotUpdateDto parkingLotUpdateDto = ParkingLotUpdateDto.builder()
            .id(1L)
            .parkingSlots(Set.of(parkingSlotDto))
            .build();

        ParkingSlot parkingSlotNew = ParkingSlot.builder().slotNumber(576).slotStatus(Status.VACANT).build();
        ParkingSlot parkingSlotToUpdate = ParkingSlot.builder().slotNumber(576).slotStatus(Status.OCCUPIED).build();

        when(restClient.getParkingSlotsStatusPrediction(mockMultipartFile)).thenReturn(parkingLotUpdateDto);
        when(modelMapper.map(parkingSlotDto, ParkingSlot.class)).thenReturn(parkingSlotNew);
        when(parkingSlotRepository.findAllByParkingLot_Id(1L)).thenReturn(Set.of(parkingSlotToUpdate));
        when(parkingSlotRepository.saveAll(anySet())).thenReturn(List.of(parkingSlotNew));
        when(modelMapper.map(List.of(parkingSlotNew), new TypeToken<Set<ParkingSlotDto>>() {}.getType())).thenReturn(Set.of(parkingSlotDto));
        when(parkingLotService.findAll()).thenReturn(Collections.emptyList());

        ParkingLotUpdateDto actual = parkingSlotService.updateParkingSlotStatuses(1L, mockMultipartFile);

        assertEquals(parkingLotUpdateDto, actual);
        verify(messagingTemplate).convertAndSend("/topic/parking-slots-update", Collections.emptyList());
    }
}