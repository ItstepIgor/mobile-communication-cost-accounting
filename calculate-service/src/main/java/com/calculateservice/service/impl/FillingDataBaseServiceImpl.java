package com.calculateservice.service.impl;

import com.calculateservice.dto.AllCallServiceDTO;
import com.calculateservice.dto.AllExpensesByPhoneNumberDTO;
import com.calculateservice.entity.*;
import com.calculateservice.repository.*;
import com.calculateservice.service.FillingDataBaseService;
import com.calculateservice.util.ImportFeignClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FillingDataBaseServiceImpl implements FillingDataBaseService {

    private final ImportFeignClients importFeignClients;

    private final OneTimeCallServiceRepository oneTimeCallServiceRepository;

    private final MonthlyCallServiceRepository monthlyCallServiceRepository;

    private final PhoneNumberRepository phoneNumberRepository;

    private final GroupNumberRepository groupNumberRepository;

    private final MobileOperatorRepository mobileOperatorRepository;


    public void fillingDataBase(LocalDate date) {
        List<AllCallServiceDTO> allCallServiceDTOS = findAllCommonCallService(date);

        createPhoneNumber(allCallServiceDTOS);

        createOneTimeCallServices(allCallServiceDTOS);

        createMonthlyCallService(allCallServiceDTOS);


        List<AllExpensesByPhoneNumberDTO> allExpensesByPhoneNumber = findAllExpensesByPhoneNumber(date);
    }


    private List<AllCallServiceDTO> findAllCommonCallService(LocalDate date) {
        return importFeignClients.findAllCommonCallService(date).getBody();
    }

    private List<AllExpensesByPhoneNumberDTO> findAllExpensesByPhoneNumber(LocalDate date) {
        return importFeignClients.findAllExpensesByPhoneNumberMTS(date).getBody();
    }

    private void createOneTimeCallServices(List<AllCallServiceDTO> allCallServiceDTOS) {
        Set<OneTimeCallService> oneTimeCallServices = new HashSet<>();
        List<OneTimeCallService> allOneTimeCallService = findAllOneTimeCallService();

        Set<String> oneTimeCallServiceName = allOneTimeCallService.stream()
                .map(OneTimeCallService::getOneTimeCallServiceName)
                .collect(Collectors.toSet());

        allCallServiceDTOS.stream()
                .filter(AllCallServiceDTO::getOneTimeCallService)
                .filter(allCallServiceDTO -> !oneTimeCallServiceName.contains(allCallServiceDTO.getCallServiceName()))
                .forEach(allCallServiceDTO -> {
                    OneTimeCallService oneTimeCallService = new OneTimeCallService();
                    oneTimeCallService.setOneTimeCallServiceName(allCallServiceDTO.getCallServiceName());
                    oneTimeCallService.setCreationDate(LocalDateTime.now());
                    oneTimeCallServices.add(oneTimeCallService);
                });

        oneTimeCallServiceRepository.saveAll(oneTimeCallServices);
    }

    public List<OneTimeCallService> findAllOneTimeCallService() {

        return oneTimeCallServiceRepository.findAll();
    }

    private void createMonthlyCallService(List<AllCallServiceDTO> allCallServiceDTOS) {
        List<MonthlyCallService> monthlyCallServices = new ArrayList<>();

        List<PhoneNumber> phoneNumbers = phoneNumberRepository.findAll();

        List<MonthlyCallService> allMonthlyCallService = findAllMonthlyCallService();

        Set<String> monthlyCallServiceName = allMonthlyCallService.stream()
                .map(MonthlyCallService::getMonthlyCallServiceName)
                .collect(Collectors.toSet());

        allCallServiceDTOS.stream()
                .filter(allCallServiceDTO -> !allCallServiceDTO.getOneTimeCallService())
                .filter(allCallServiceDTO -> !monthlyCallServiceName.contains(allCallServiceDTO.getCallServiceName()))
                .forEach(allCallServiceDTO -> {
                    MonthlyCallService monthlyCallService = new MonthlyCallService();
                    monthlyCallService.setMonthlyCallServiceName(allCallServiceDTO.getCallServiceName());
                    monthlyCallService.setCreationDate(LocalDateTime.now());
                    monthlyCallService.setVatTax(allCallServiceDTO.getVatTax());
                    monthlyCallService.setInvoiceDate(allCallServiceDTO.getInvoiceDate());
                    monthlyCallService.setPhoneNumber(phoneNumbers.stream()
                            .filter(phoneNumber -> phoneNumber.getNumber() == allCallServiceDTO.getNumber())
                            .findFirst().orElse(null));
                    //TODO написать и обработать свою ошибку (orElseThrow()) в случае отсутствия номера
                    monthlyCallService.setSum(allCallServiceDTO.getSum());
                    monthlyCallService.setSumWithNDS(allCallServiceDTO.getSumWithNDS());
                    monthlyCallServices.add(monthlyCallService);
                });
        monthlyCallServiceRepository.saveAll(monthlyCallServices);
    }

    public List<MonthlyCallService> findAllMonthlyCallService() {

        return monthlyCallServiceRepository.findAll();
    }

    public static <T> Predicate<T> distinctByField(
            Function<? super T, ?> sumExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(sumExtractor.apply(t), Boolean.TRUE) == null;
    }

    private void createPhoneNumber(List<AllCallServiceDTO> allCallServiceDTOS) {
        List<PhoneNumber> allPhoneNumber = findAllPhoneNumber();

        Set<Long> phoneNumberName = allPhoneNumber.stream()
                .map(PhoneNumber::getNumber)
                .collect(Collectors.toSet());

        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        allCallServiceDTOS.stream()
                .filter(distinctByField(AllCallServiceDTO::getNumber))
                .filter(allCallServiceDTO -> !phoneNumberName.contains(allCallServiceDTO.getNumber()))
                .forEach(allCallServiceDTO -> {
                    PhoneNumber phoneNumber = new PhoneNumber();
                    phoneNumber.setNumber(allCallServiceDTO.getNumber());
                    phoneNumber.setCreationDate(LocalDateTime.now());
                    phoneNumber.setGroupNumber(groupNumberRepository.findById(7L).orElse(null));
                    phoneNumber.setMobileOperator(mobileOperatorRepository
                            .findById((long) allCallServiceDTO.getMobileOperator()).orElse(null));
                    phoneNumbers.add(phoneNumber);
                });

        phoneNumberRepository.saveAll(phoneNumbers);
    }

    public List<PhoneNumber> findAllPhoneNumber() {

        return phoneNumberRepository.findAll();
    }
}
