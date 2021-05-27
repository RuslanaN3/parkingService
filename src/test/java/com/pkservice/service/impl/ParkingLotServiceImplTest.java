package com.pkservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pkservice.dto.ParkingLotDto;
import com.pkservice.dto.ParkingLotSuitableDto;
import com.pkservice.dto.PlateResultDto;
import com.pkservice.entity.Building;
import com.pkservice.entity.ParkingLot;
import com.pkservice.entity.ParkingSlot;
import com.pkservice.enums.Status;
import com.pkservice.repository.BuildingRepository;
import com.pkservice.repository.ParkingLotRepository;
import com.pkservice.restClient.RestClient;
import com.pkservice.service.ParkingLotService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceImplTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    RestClient restClient;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @Mock
    ParkingLotRepository parkingLotRepository;

    @Mock
    BuildingRepository buildingRepository;

    @InjectMocks
    ParkingLotServiceImpl parkingLotService;

    @Test
    void findAll() {
        List<ParkingLot> parkingLots = Collections.singletonList(ParkingLot.builder()
            .id(1L)
            .lotType("ground")
            .buildings(Collections.emptySet())
            .parkingSlots(Collections.emptySet())
            .build());

        List<ParkingLotDto> expected = Collections.singletonList(ParkingLotDto.builder()
            .id(1L)
            .lotType("ground")
            .buildings(Collections.emptySet())
            .parkingSlots(Collections.emptySet())
            .vacantParkingSlotsCount(0L)
            .build());

        when(parkingLotRepository.findAll()).thenReturn(parkingLots);
        when(modelMapper.map(parkingLots, new TypeToken<List<ParkingLotDto>>() {
        }.getType())).thenReturn(expected);

        List<ParkingLotDto> actual = parkingLotService.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findById() {
        ParkingLot parkingLot = ParkingLot.builder().id(1L).lotType("underground").build();
        ParkingLotDto expected = ParkingLotDto.builder().id(1L).lotType("underground").build();
        when(parkingLotRepository.findById(1L)).thenReturn(Optional.of(parkingLot));
        when(modelMapper.map(parkingLot, ParkingLotDto.class)).thenReturn(expected);

        ParkingLotDto actual = parkingLotService.findById(1L);
        assertEquals(expected, actual);
    }

    @Test
    void getSuitableParkingLot() {
        PlateResultDto mockPlateResultDto = new PlateResultDto("plateNumber", 0.95, true);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("image", "image".getBytes());

        ParkingSlot parkingSlot = ParkingSlot.builder().id(1L).slotNumber(524).slotStatus(Status.VACANT).build();
        ParkingLot parkingLot1 = ParkingLot.builder().id(1L).parkingSlots(Collections.emptySet()).build();
        ParkingLot parkingLot2 = ParkingLot.builder().id(2L).parkingSlots(Collections.emptySet()).build();
        ParkingLot parkingLot3 = ParkingLot.builder().id(3L).parkingSlots(Set.of(parkingSlot)).build();

        Set<ParkingLot> availableParkingLots = Set.of(parkingLot1, parkingLot2, parkingLot3);
        Building building = new Building(1L, "buildingName", Collections.emptySet(), availableParkingLots);
        ParkingLotSuitableDto expected = ParkingLotSuitableDto.builder()
            .suitableParkingLotId(3L)
            .suitableParkingSlotNumber(524L)
            .plateNotPresentInDB(false)
            .plateNotDetected(false)
            .build();

        when(restClient.getPlateRecognitionResults(mockMultipartFile)).thenReturn(mockPlateResultDto);
        when(buildingRepository.findBuildingByVehicleLicensePlate(mockPlateResultDto.getPlate()))
            .thenReturn(Optional.of(building));

        ParkingLotSuitableDto actual = parkingLotService.getSuitableParkingLot(mockMultipartFile);

        assertEquals(expected, actual);

        verify(messagingTemplate).convertAndSend(any(String.class), any(ParkingLotSuitableDto.class));
    }
}