package com.pkservice.controller;

import static org.junit.jupiter.api.Assertions.*;


import com.pkservice.service.ParkingSlotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class ParkingSlotControllerTest {

    private static final String parkingSlotLink = "/api/parking-slot";

    private MockMvc mockMvc;

    @Mock
    ParkingSlotService parkingSlotService;

    @InjectMocks
    ParkingSlotController parkingSlotController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(parkingSlotController).build();
    }

    @Test
    void updateParkingSlotStatusSensorsData() throws Exception {
        this.mockMvc.perform(patch(parkingSlotLink + "/sensors-data/1"))
            .andExpect(status().isOk());

        verify(parkingSlotService).updateParkingSlotStatuses(1L);
    }

    @Test
    void updateParkingSlotStatusCamData() throws Exception {
        MockMultipartFile mockMultipartFile =
            new MockMultipartFile("image", "", "multipart/form-data", "image".getBytes());

        MockMultipartHttpServletRequestBuilder builder =
            MockMvcRequestBuilders.multipart(parkingSlotLink + "/cam-data/1");
        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        this.mockMvc.perform(builder
            .file(mockMultipartFile))
            .andExpect(status().isOk());

        verify(parkingSlotService).updateParkingSlotStatuses(1L, mockMultipartFile);
    }
}