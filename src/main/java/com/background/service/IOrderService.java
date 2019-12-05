package com.background.service;

import com.background.common.ServerResponse;

public interface IOrderService {
    ServerResponse pay(String barCode, String codeType, String deviceSn);
}
