package com.importservice.service.impl;

import com.importservice.entity.Call;
import com.importservice.entity.OneTimeCallService;
import com.importservice.reposiitory.OneTimeCallServiceRepository;
import com.importservice.service.OneTimeCallServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OneTimeCallServiceServiceImpl implements OneTimeCallServiceService {

    private final OneTimeCallServiceRepository oneTimeCallServiceRepository;

    @Override
    public Set<String> findOneTimeCallServiceName() {
        return oneTimeCallServiceRepository.findOneTimeCallServiceName();
    }

    @Override
    public void saveOneTimeCallService(List<Call> calls) {
        Set<String> oneTimeCallServiceName = findOneTimeCallServiceName();

        Set<OneTimeCallService> oneTimeCallServices = calls.stream()
                .map(Call::getCallService)
                .distinct()
                .map(OneTimeCallService::new)
                .filter(oneTimeCallService -> !oneTimeCallServiceName
                        .contains(oneTimeCallService.getOneTimeCallServiceName()))
                .collect(Collectors.toSet());
        oneTimeCallServiceRepository.saveAll(oneTimeCallServices);
    }
}
