package com.importservice.service;

import com.importservice.entity.Call;

import java.util.List;
import java.util.Set;

public interface OneTimeCallServiceService {
    Set<String> findOneTimeCallServiceName();

    void saveOneTimeCallService(List<Call> calls);
}
