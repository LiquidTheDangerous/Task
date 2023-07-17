package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.body.ActionResultMessage;
import org.example.controller.body.ApiBody;
import org.example.controller.body.ErrorBody;
import org.example.controller.util.HttpMethodToOperationMapperImpl;
import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.service.BankServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = BankController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BankControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    HttpMethodToOperationMapperImpl httpMethodToOperationMapper;

    @MockBean
    BankServiceImpl bankService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void BankController_create_ReturnCreated() throws Exception {
        var testBank = new Bank(null, "Open", 123456789);

        doAnswer(invocation -> invocation.getArgument(0)).when(bankService).save(testBank);
        var response = mockMvc
                .perform(post("/api/bank/create")
                        .contentType(MediaType.APPLICATION_JSON_VALUE )
                        .content(objectMapper.writeValueAsString(testBank)));


        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper
                        .writeValueAsString(
                                ApiBody.<Bank>builder()
                                        .body(testBank)
                                        .actionResult(
                                                new ActionResultMessage("create", true))
                                        .build())));
    }

    @Test
    public void BankControllerTest_getById_ReturnsBankById() throws Exception {
        var bank = new Bank(null, "Open", 123456789);
        when(bankService.getBankById(any())).thenReturn(bank);
        var response = mockMvc.perform(
                get("/api/bank/getById/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper
                        .writeValueAsString(ApiBody.<Bank>builder()
                                .body(bank)
                                .actionResult(new ActionResultMessage("read", true))
                                .build())));
    }

    @Test
    public void BankControllerTest_getById_ReturnsNotFoundStatus_when_BankNotFount() throws Exception {
        String msg = "no such bank ...";
        String op = "read";
        when(bankService.getBankById(any())).thenThrow(new ResourceNotFoundException(msg, op));
        var response = mockMvc.perform(get("/api/bank/getById/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ErrorBody(new ActionResultMessage(op, false), msg))));
    }

    @Test
    public void BankControllerTest_getByName_ReturnsBankByName() throws Exception {
        var bank = new Bank(null, "Open", null);
        when(bankService.getBankByName(any(String.class))).thenReturn(bank);

        var response = mockMvc.perform(get("/api/bank/getByName")
                .param("name", "bankName"));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper
                                .writeValueAsString(
                                        ApiBody.<Bank>builder()
                                                .body(bank)
                                                .actionResult(new ActionResultMessage("read", true))
                                                .build())
                ));
    }

    @Test
    public void BankController_getByName_ReturnsNotFoundStatus_when_BankNotFound() throws Exception {
        String msg = "no such bank ...";
        String op = "read";
        when(bankService.getBankByName(any()))
                .thenThrow(new ResourceNotFoundException(msg, op));

        var response = mockMvc.perform(get("/api/bank/getByName")
                .contentType(MediaType.APPLICATION_JSON)
                .param("name", "bankName"));

        response.andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ErrorBody(new ActionResultMessage(op, false), msg))));
    }

    @Test
    public void BankControllerTest_getByBikCode_ReturnsBankByBikCode() throws Exception {
        var code = 123456789;
        var bank = new Bank(null, "Open", code);
        when(bankService.getBankByBikCode(any(Integer.class))).thenReturn(bank);

        var response = mockMvc.perform(get("/api/bank/getByBikCode")
                .param("bikCode", Integer.toString(code)));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper
                                .writeValueAsString(
                                        ApiBody.<Bank>builder()
                                                .body(bank)
                                                .actionResult(new ActionResultMessage("read", true))
                                                .build())
                ));
    }

    @Test
    public void BankControllerTest_getDepositByBankId_returnsDepositsByBankId() throws Exception {
        var deposits = new ArrayList<Deposit>();
        deposits.add(new Deposit(null, 1.3, LocalDate.now(), 12, null, null));
        deposits.add(new Deposit(null, 2.8, LocalDate.now(), 24, null, null));

        when(bankService.getBankDepositByBankId(any(Long.class))).thenReturn(deposits);

        var response = mockMvc.perform(get("/api/bank/getDepositByBankId/1"));

        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        objectMapper.writeValueAsString(
                                ApiBody.<List<Deposit>>builder()
                                        .actionResult(new ActionResultMessage("read", true))
                                        .body(deposits)
                                        .build())));
    }

    @Test
    public void BankControllerTest_getDepositByBankId_ReturnsNotFoundStatus_when_BankIdNotFount() throws Exception {
        var op = "read";
        var msg = "resource not found";
        when(bankService.getBankDepositByBankId(any(Long.class)))
                .thenThrow(new ResourceNotFoundException(msg, op));
        var response = mockMvc.perform(get("/api/bank/getDepositByBankId/1"));

        response.andExpect(status().isNotFound())
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(
                                        new ErrorBody(
                                                new ActionResultMessage(op, false), msg))));
    }

    @Test
    public void BankControllerTest_deleteById_DeleteBankById() throws Exception {
        var bankId = 1L;
        doAnswer(invocation -> {
            var bankIdToDelete = invocation.getArgument(0);
            Assertions.assertEquals(bankId, bankIdToDelete);
            return null;
        }).when(bankService).deleteById(any(Long.class));
        var response = mockMvc.perform(delete("/api/bank/deleteById/{id}", bankId));

        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper
                        .writeValueAsString(new ActionResultMessage("delete", true))));

    }

    @Test
    public void BankControllerTest_deleteById_ReturnsNotFoundStatus_when_BankNotFound() throws Exception {
        var op = "delete";
        var message = "no such bank to delete";

        doAnswer(invocation -> {
            throw new ResourceNotFoundException(message, op);
        }).when(bankService).deleteById(any(Long.class));

        var response = mockMvc.perform(delete("/api/bank/deleteById/1"));

        response.andExpect(status().isNotFound())
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(
                                        new ErrorBody(
                                                new ActionResultMessage(op, false),
                                                message))));
    }

    @Test
    public void BankControllerTest_update_AssertThatControllerUpdateBank() throws Exception {
        var op = "update";
        var bank = new Bank(1L, "Open", 123456789);

        doAnswer(invocation -> {
            var bankToUpdate = invocation.getArgument(0);
            Assertions.assertEquals(bankToUpdate, bank);
            return null;
        })
                .when(bankService)
                .update(any(Bank.class));

        var response = mockMvc.perform(put("/api/bank/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bank)));

        response.andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper
                                .writeValueAsString(
                                        new ActionResultMessage(op, true))));


    }


}
