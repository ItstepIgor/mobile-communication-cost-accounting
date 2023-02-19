package com.importservice.service;

import com.importservice.entity.Call;

public interface CallService {

    void create(Call call);

    void readFromExcel(String file);

}
