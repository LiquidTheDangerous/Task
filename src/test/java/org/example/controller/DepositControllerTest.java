package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.body.ActionResultMessage;
import org.example.controller.body.ApiBody;
import org.example.controller.body.ErrorBody;
import org.example.controller.util.HttpMethodToOperationMapperImpl;
import org.example.domain.PlainDeposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.service.PlainDepositService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DepositController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class DepositControllerTest {
    @MockBean
    PlainDepositService plainDepositService;

    @MockBean
    HttpMethodToOperationMapperImpl httpMethodToOperationMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void DepositController_create_ReturnsCreated() throws Exception {
        var deposit = new PlainDeposit(1L, 1.3, LocalDate.now(), 12, null, null);
        doAnswer(invocation -> {
            var createdDeposit = invocation.getArgument(0);
            assertEquals(createdDeposit, deposit);
            return createdDeposit;
        }).when(plainDepositService).save(any(PlainDeposit.class));

        var response = mockMvc.perform(post("/api/deposit/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deposit)));

        response.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(new ApiBody<>(new ActionResultMessage("create", true), deposit))));
    }

    @Test
    public void DepositController_getAll_ReturnsAllSavedDeposits() throws Exception {
        var deposits = new ArrayList<PlainDeposit>();
        deposits.add(new PlainDeposit(null, 1.3, LocalDate.now(), 12, null, null));
        deposits.add(new PlainDeposit(null, 2.8, LocalDate.now(), 24, null, null));

        when(plainDepositService.getAll(any(Integer.class), any(Integer.class))).thenReturn(deposits);

        var response = mockMvc.perform(get("/api/deposit/getAll")
                .param("pageNumber", "1")
                .param("pageSize", "25"));

        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ApiBody<>(new ActionResultMessage("read", true), deposits)
                )));
    }

    @Test
    public void DepositController_getById_ReturnsDepositById() throws Exception {
        var deposit = new PlainDeposit(1L, 1.3, LocalDate.now(), 12, null, null);
        when(plainDepositService.getDepositById(any(Long.class))).thenReturn(deposit);

        var response = mockMvc.perform(get("/api/deposit/getById/{id}", deposit.getId()));

        response.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(
                        new ApiBody<>(new ActionResultMessage("read", true), deposit)
                )));
    }

    @Test
    public void DepositController_getById_ReturnsStatusNotFound_when_DepositByIdNotFound() throws Exception {
        var op = "read";
        var msg = "no such resource";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(plainDepositService).getDepositById(any(Long.class));

        var response = mockMvc
                .perform(get("/api/deposit/getById/{id}", 1L));

        response.andExpect(status().isNotFound())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(new ErrorBody(new ActionResultMessage(op, false), msg))));

    }

    @Test
    public void DepositController_update_AssertThatControllerUpdateDeposit() throws Exception {
        var deposit = new PlainDeposit(null, 1.3, LocalDate.now(), 12, null, null);
        doAnswer(invocation -> {
            var depositToUpdate = (PlainDeposit) invocation.getArgument(0);
            assertEquals(deposit, depositToUpdate);
            return null;
        }).when(plainDepositService).update(any(PlainDeposit.class));

        var response = mockMvc.perform(put("/api/deposit/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deposit)));

        response.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(new ActionResultMessage("update", true))
                ));
    }

    @Test
    public void DepositController_update_AssertThatControllerReturnsNotFoundStatus_when_NoDepositToUpdate() throws Exception {
        var deposit = new PlainDeposit(1L, 1.3, LocalDate.now(), 12, null, null);
        var op = "update";
        var msg = "no such resource to update";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(plainDepositService).update(any(PlainDeposit.class));

        var response = mockMvc
                .perform(put("/api/deposit/update").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deposit)));

        response.andExpect(status().isNotFound())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(new ErrorBody(new ActionResultMessage(op, false), msg))));
    }

    @Test
    public void DepositController_deleteById_AssertThatControllerDeleteDepositById() throws Exception {
        var deposit = new PlainDeposit(1L, 1.3, LocalDate.now(), 12, null, null);
        doAnswer(invocation -> {
            var depositIdToDelete = (Long) invocation.getArgument(0);
            assertEquals(deposit.getId(), depositIdToDelete);
            return null;
        }).when(plainDepositService).deleteById(any(Long.class));

        var response = mockMvc.perform(delete("/api/deposit/deleteById/{id}", deposit.getId()));

        response.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(new ActionResultMessage("delete", true))
                ));
    }

    @Test
    public void DepositController_deleteById_AssertThatControllerReturnNotFoundStatus_when_NoDepositToDelete() throws Exception {
        var deposit = new PlainDeposit(1L, 1.3, LocalDate.now(), 12, null, null);
        var op = "delete";
        var msg = "no such resource to delete";
        doAnswer(invocation -> {
            throw new ResourceNotFoundException(msg, op);
        }).when(plainDepositService).deleteById(any(Long.class));

        var response = mockMvc
                .perform(delete("/api/deposit/deleteById/{id}", deposit.getId()));

        response.andExpect(status().isNotFound())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(new ErrorBody(new ActionResultMessage(op, false), msg))));

    }


}
