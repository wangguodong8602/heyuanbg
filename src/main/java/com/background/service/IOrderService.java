package com.background.service;

import com.alipay.api.AlipayApiException;
import com.background.common.ServerResponse;

public interface IOrderService {
    ServerResponse pay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount);

    ///ServerResponse aliPay() throws AlipayApiException;

    ServerResponse authCallback(String app_id,String app_auth_code)throws AlipayApiException;

    ServerResponse scanCodeToPay(String auth_code) throws Exception;
}
