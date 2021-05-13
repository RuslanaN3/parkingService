package com.pkservice.service.impl;

import com.opencsv.bean.CsvToBeanBuilder;
import com.pkservice.dto.ParkingLotUpdateDto;
import com.pkservice.dto.ParkingSlotCsvDto;
import com.pkservice.dto.ParkingSlotDto;
import com.pkservice.entity.ParkingLot;
import com.pkservice.entity.ParkingSlot;
import com.pkservice.repository.ParkingSlotRepository;
import com.pkservice.restClient.RestClient;
import com.pkservice.service.ParkingLotService;
import com.pkservice.service.ParkingSlotService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    @Autowired
    RestClient restClient;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    ParkingLotService parkingLotService;

    public ParkingLotUpdateDto updateParkingSlotStatuses(Long parkingLotId) {
        File file = null;
        List<ParkingSlotCsvDto> beans = null;
        try {
            file = ResourceUtils.getFile("classpath:sensors_data.csv");
            beans = new CsvToBeanBuilder<ParkingSlotCsvDto>(new FileReader(file))
                .withType(ParkingSlotCsvDto.class)
                .withSeparator(';')
                .build()
                .parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Set<ParkingSlot> parkingSlotsNew = beans.stream()
            .map(bean -> modelMapper.map(bean, ParkingSlot.class))
            .collect(Collectors.toSet());

        return updateAllSlots(parkingLotId, parkingSlotsNew);
    }

    public ParkingLotUpdateDto updateParkingSlotStatuses(Long parkingLotId, MultipartFile image) {
        Set<ParkingSlotDto> parkingSlotDtos = restClient.getParkingSlotsStatusPrediction(image).getParkingSlots();
        Set<ParkingSlot> parkingSlotsNew = parkingSlotDtos.stream()
            .map(bean -> modelMapper.map(bean, ParkingSlot.class))
            .collect(Collectors.toSet());

        return updateAllSlots(parkingLotId, parkingSlotsNew);
    }


    private ParkingLotUpdateDto updateAllSlots(Long parkingLotId, Set<ParkingSlot> parkingSlotsNew) {
        Set<ParkingSlot> parkingSlotsToUpdate = parkingSlotRepository.findAllByParkingLot_Id(parkingLotId);
        Set<ParkingSlot> parkingSlotsUpdated = parkingSlotsToUpdate.stream()
            .map(psu -> updateStatus(psu, parkingSlotsNew))
            .collect(Collectors.toSet());

        Set<ParkingSlotDto> parkingSlotDtos = modelMapper
            .map(parkingSlotRepository.saveAll(parkingSlotsUpdated),
                new TypeToken<Set<ParkingSlotDto>>() {}.getType());

        messagingTemplate.convertAndSend("/topic/parking-slots-update", parkingLotService.findAll());
        return ParkingLotUpdateDto.builder().id(parkingLotId).parkingSlots(parkingSlotDtos).build();
    }

    private ParkingSlot updateStatus(ParkingSlot parkingSlotToUpdate, Set<ParkingSlot> parkingSlotsNew) {
        ParkingSlot parkingSlotNew = parkingSlotsNew.stream()
            .filter(psn -> psn.getSlotNumber() == parkingSlotToUpdate.getSlotNumber())
            .findFirst().orElseThrow(() -> new NoSuchElementException("element not found : " + parkingSlotToUpdate.getSlotNumber()));

        parkingSlotToUpdate.setSlotStatus(parkingSlotNew.getSlotStatus());

        return parkingSlotToUpdate;
    }


}
