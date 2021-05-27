package com.pkservice.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.pkservice.service.ParkingLotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ParkingLotControllerTest {

    private static final String parkingLotLink = "/api/parking-lot";

    private MockMvc mockMvc;

    @Mock
    ParkingLotService parkingLotService;

    @InjectMocks
    ParkingLotController parkingLotController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(parkingLotController).build();
    }

    @Test
    void getAllParkingLots() throws Exception {
        this.mockMvc.perform(get(parkingLotLink)).andExpect(status().isOk());
        verify(parkingLotService).findAll();
    }

    @Test
    void getParkingLot() throws Exception {
        this.mockMvc.perform(get(parkingLotLink + "/1")).andExpect(status().isOk());
        verify(parkingLotService).findById(1L);
    }

    @Test
    void getSuitableParkingLot() throws Exception {
        MockMultipartFile mockMultipartFile =
            new MockMultipartFile("image", "", "multipart/form-data", "image".getBytes());
        this.mockMvc.perform(multipart(parkingLotLink)
            .file(mockMultipartFile))
            .andExpect(status().isOk());

        verify(parkingLotService).getSuitableParkingLot(mockMultipartFile);

    }
}