package com.greenbite.test; // Coincide exactamente con la línea 1 de tu captura

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.greenbite.bff.infrastructure.MicroserviceClient; 

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest { 

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MicroserviceClient microserviceClient; 

    @Test
    @DisplayName("BFF REST - GET /api/dashboard - Debe responder exitosamente al Frontend")
    void testBffGetEndpoint() throws Exception {

        mockMvc.perform(get("/api/dashboard") 
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()); 
    }
}