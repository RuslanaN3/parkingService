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
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
    RestClient restClient;

    @Override
    public List<ParkingLotDto> findAll() {
        return modelMapper.map(parkingLotRepository.findAll(), new TypeToken<List<ParkingLotDto>>() {
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
        Building building = buildingRepository.findBuildingByVehicleLicensePlate(plateResultDto.getPlate());
        Set<ParkingLot> availableParkingLots = building.getParkingLots();

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
        return ParkingLotSuitableDto
            .builder()
            .suitableParkingLotId(suitableParkingLot.getId())
            .suitableParkingSlotNumber(suitableParkingSlot.getSlotNumber())
            .parkingLotDtos(modelMapper.map(availableParkingLots, new TypeToken<Set<ParkingLotDto>>() {}.getType()))
            .build();
    }

    private long countVacantSlots(Set<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().filter(ps -> ps.getSlotStatus().equals(Status.VACANT)).count();
    }

}
