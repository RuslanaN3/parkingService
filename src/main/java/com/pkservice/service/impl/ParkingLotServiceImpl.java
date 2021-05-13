package com.pkservice.service.impl;

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
import java.util.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    BuildingRepository buildingRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    RestClient restClient;

    @Override
    public List<ParkingLotDto> findAll() {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll();
        parkingLots.forEach(apk -> apk.setVacantParkingSlotsCount(countVacantSlots(apk.getParkingSlots())));
        return modelMapper.map(parkingLots, new TypeToken<List<ParkingLotDto>>() {
        }.getType());

    }

    @Override
    public ParkingLotDto findById(Long id) {
        ParkingLot parkingLot = parkingLotRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Element not found with id:" + id));
        return modelMapper.map(parkingLot, ParkingLotDto.class);
    }

    @Override
    public ParkingLotSuitableDto getSuitableParkingLot(MultipartFile image) {
        PlateResultDto plateResultDto = restClient.getPlateRecognitionResults(image);
        if (!plateResultDto.getPresent()){
            ParkingLotSuitableDto parkingLotSuitableDto = ParkingLotSuitableDto.builder().plateNotDetected(true).build();
            messagingTemplate.convertAndSend("/topic/suitable-parking-slot", parkingLotSuitableDto);
            return  parkingLotSuitableDto;
        }
        Optional<Building> building = buildingRepository.findBuildingByVehicleLicensePlate(plateResultDto.getPlate());
        if(building.isEmpty()){
            ParkingLotSuitableDto parkingLotSuitableDto = ParkingLotSuitableDto.builder().plateNotPresentInDB(true).build();
            messagingTemplate.convertAndSend("/topic/suitable-parking-slot", parkingLotSuitableDto);
            return  parkingLotSuitableDto;
        }

        Set<ParkingLot> availableParkingLots = building.get().getParkingLots();

        availableParkingLots
            .forEach(apk -> apk.setVacantParkingSlotsCount(countVacantSlots(apk.getParkingSlots())));

        ParkingLot suitableParkingLot = availableParkingLots
            .stream()
            .max(Comparator.comparing(ParkingLot::getVacantParkingSlotsCount))
            .orElseThrow(() -> new NoSuchElementException("Element not found"));
        ParkingSlot suitableParkingSlot = suitableParkingLot.getParkingSlots()
            .stream()
            .filter(ps -> ps.getSlotStatus().equals(Status.VACANT))
            .findFirst().orElseThrow(() -> new NoSuchElementException("Element not found"));

        ParkingLotSuitableDto parkingLotSuitableDto = ParkingLotSuitableDto
            .builder()
            .suitableParkingLotId(suitableParkingLot.getId())
            .suitableParkingSlotNumber(suitableParkingSlot.getSlotNumber())
            .parkingLotDtos(modelMapper.map(availableParkingLots, new TypeToken<Set<ParkingLotDto>>() {
            }.getType()))
            .plateNotDetected(false)
            .plateNotPresentInDB(false)
            .build();
        messagingTemplate.convertAndSend("/topic/suitable-parking-slot", parkingLotSuitableDto);
        return parkingLotSuitableDto;
    }

    private long countVacantSlots(Set<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().filter(ps -> ps.getSlotStatus().equals(Status.VACANT)).count();
    }

}
