package org.example.service;

import org.example.domain.PlainDeposit;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.PlainDepositRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlainDepositServiceImplTest {
    @Mock
    PlainDepositRepository depositRepository;

    @InjectMocks
    PlainDepositServiceImpl depositService;

    @Test
    public void PlainDepositServiceImpl_getAll_ReturnsAllSavedDeposits() {
        var deposits = new ArrayList<PlainDeposit>();
        when(depositRepository.findAll()).thenReturn(deposits);
        var savedDeposits = depositService.getAll();
        assertSame(savedDeposits,deposits);
    }

    @Test
    public void PlainDepositServiceImpl_getDepositById_ReturnsSavedDeposit() {
        var deposit = new PlainDeposit();
        when(depositRepository.findById(any())).thenReturn(Optional.of(deposit));
        var savedDeposit = depositService .getDepositById(1L);
        assertSame(deposit,savedDeposit);
    }

    @Test
    public void PlainDepositServiceImpl_save_ServiceSaveDeposit() {
        var deposit = new PlainDeposit();
        when(depositRepository.save(any())).thenReturn(deposit);
        var savedDeposit = depositService.save(deposit);
        assertSame(savedDeposit,deposit);
    }

    @Test
    public void PlainDepositServiceImpl_update_AssertServiceThrowResourceNotFoundException_when_DepositToUpdateNotFound() {
        when(depositRepository.existsById(any())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()-> depositService.update(new PlainDeposit()));
    }

    @Test
    public void PlainDepositServiceImpl_update_AssertThatServiceUpdateDeposit() {
        var deposit = new PlainDeposit();
        when(depositRepository.existsById(any())).thenReturn(true);
        doAnswer(invocation->{
            var depositToUpdate = (PlainDeposit)invocation.getArgument(0);
            assertSame(depositToUpdate,deposit);
            return depositToUpdate;
        }).when(depositRepository).save(any());
        depositService.update(deposit);
    }

    @Test
    public void PlainDepositServiceImpl_delete_AssertThatServiceThrowResourceNotFoundException_when_DepositToDeleteNotFound() {
        when(depositRepository.existsById(any())).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()-> depositService.deleteById(1L));
    }

    @Test
    public void PlainDepositServiceImpl_delete_AssertThatServiceDeleteDeposit() {
        when(depositRepository.existsById(any())).thenReturn(true);
        Long depositIdToDelete = 1L;
        doAnswer(invocation->{
            var idToDelete = invocation.getArgument(0);
            assertSame(depositIdToDelete,idToDelete);
            return null;
        }).when(depositRepository).deleteById(any());
        depositService.deleteById(depositIdToDelete);
    }



}
