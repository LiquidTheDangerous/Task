package org.example.service;

import org.example.domain.Bank;
import org.example.domain.Deposit;
import org.example.exceptions.ResourceAlreadyExistsException;
import org.example.exceptions.ResourceNotFoundException;
import org.example.repository.BankRepository;
import org.example.repository.DepositRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankServiceImplTest {
    @Mock
    BankRepository bankRepository;
    @Mock
    DepositRepository depositRepository;
    @InjectMocks
    BankServiceImpl bankService;

    @Test
    public void BankServiceImpl_getAll_ReturnsAllSavedBanks() {
        var bankList = new ArrayList<Bank>();
        when(bankRepository.findAll()).thenReturn(bankList);
        var savedBanks = bankService.getAll();

        assertSame(bankList, savedBanks);
    }

    @Test
    public void BankServiceImpl_getBankById_ReturnsBankById() {
        var bank = new Bank();
        when(bankRepository.findById(Mockito.any())).thenReturn(Optional.of(bank));
        var savedBank = bankService.getBankById(101L);
        assertSame(bank, savedBank.get());
    }

    @Test void BankServiceImpl_getBankDepositByBankId_ReturnsAllBankDeposits(){
        var bankDeposits = new ArrayList<Deposit>();
        doAnswer(invocation->bankDeposits).when(depositRepository).getAllByBankId(any());
        var savedBankDeposits = bankService.getBankDepositByBankId(1L);
        assertSame(savedBankDeposits,bankDeposits);
    }

    @Test
    public void BankServiceImpl_save_throwsResourceAlreadyExistsException_whenBankAlreadyExists() {
        when(bankRepository.existsById(Mockito.any())).thenReturn(true);
        assertThrows(ResourceAlreadyExistsException.class, () -> bankService.save(new Bank()));
    }

    @Test
    public void BankServiceImpl_save_AssertThatServiceSaveBank() {
        doAnswer(invocation -> false).when(bankRepository).existsById(any());
        var bank = Bank.builder().id(1L).build();
        doAnswer(invocation -> {
            var bankToSave = (Bank) invocation.getArgument(0);
            assertEquals(bank.getId(), bankToSave.getId());
            return bankToSave;
        })
                .when(bankRepository)
                .save(any(Bank.class));
        bankService.save(bank);
    }

    @Test
    public void BankServiceImpl_update_AssertThrowsResourceNotFoundException_when_BankToUpdateNotFound() {
        doAnswer(invocation -> false).when(bankRepository).existsById(any());
        assertThrows(ResourceNotFoundException.class, () -> bankService.update(new Bank()));
    }

    @Test
    public void BankServiceImpl_update_AssertThatServiceUpdateBank() {
        var bank = Bank.builder().id(1L).build();
        doAnswer(invocation->true).when(bankRepository).existsById(any());
        doAnswer(invocation -> {
            var bankToUpdate = (Bank) invocation.getArgument(0);
            assertEquals(bankToUpdate.getId(),bank.getId());
            return bankToUpdate;
        }).when(bankRepository).save(any(Bank.class));
        bankService.update(bank);
    }

    @Test
    public void BankServiceImpl_deleteById_AssertThrowsResourceNotFoundException_when_BankToDeleteNotFound() {
        doAnswer(invocation->false).when(bankRepository).existsById(any());
        assertThrows(ResourceNotFoundException.class,()->bankService.deleteById(1L));
    }

    @Test
    public void BankServiceImpl_deleteById_AssertThatServiceDeleteBank() {
        var bank = Bank.builder().id(1L).build();
        doAnswer(invocation->true).when(bankRepository).existsById(any());
        doAnswer(invocation->{
            var bankIdToDelete = (Long)invocation.getArgument(0);
            assertEquals(bank.getId(),bankIdToDelete);
            return null;
        }).when(bankRepository).deleteById(any());
        bankService.deleteById(bank.getId());
    }
}
