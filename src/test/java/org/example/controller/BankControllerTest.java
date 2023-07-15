package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.domain.Bank;
import org.example.service.BankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = BankController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankService bankService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Bank testBank;

    public BankControllerTest() {
        testBank = new Bank(null,"Open",123456789L,null);
    }

    @Test
    public void BankController_Create_ReturnCreated() throws Exception{
        ResultActions response = mockMvc
                .perform(post("/api/bank/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testBank)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(objectMapper
                                .writeValueAsString(Collections
                                        .singletonMap("create",Boolean.TRUE))));

    }

}
