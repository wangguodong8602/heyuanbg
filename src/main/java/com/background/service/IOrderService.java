package com.background.service;

import com.background.common.ServerResponse;

public interface IOrderService {
    ServerResponse pay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount);
}
