package fr.plage.reservation.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientRestControllerTest {
    @Autowired
    MockMvc mockMvc;

    private String nom = "DUPONT";
    @Test
    @Order(1)
    void testGetClients() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/clients");

        mockMvc.perform(requestBuilder)
                // On vérifie que l'on a bien 2 clients en retour (Wildcard, Valve et Riot)
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                // On vérifie que le 2ème éditeur est bien Valve
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value(nom));
    }

    @Test
    @Order(2)
    void deleteClient() throws  Exception{
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/clients/{id}", "1");

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }
}
