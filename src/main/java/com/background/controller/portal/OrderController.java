package com.background.controller.portal;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.background.common.Const;
import com.background.common.ResponseCode;
import com.background.common.ServerResponse;
import com.background.pojo.User;
import com.background.service.IOrderService;
import com.google.common.collect.Maps;
import net.sf.jsqlparser.schema.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService iOrderService;


    @RequestMapping(value = "/mini_pay.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse miniPay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount){
        return iOrderService.pay(bizNo,barCode,codeType,deviceSn,totalAmount);
    }

    @RequestMapping(value = "/pay.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse pay(String barCode)throws Exception{
        return iOrderService.scanCodeToPay(barCode);
    }

    @RequestMapping("alipay_callback.do")
    @ResponseBody
    public Object alipayCallback(HttpServletRequest request){
        Map<String,String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for(Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext();){
            String name = (String)iterator.next();
            String[] values = (String[])requestParams.get(name);
            String valueStr = "";
            for(int i = 0; i < values.length; i++){
                valueStr = (i==values.length-1)?valueStr + values[i]:valueStr + values[i] +",";
            }
            params.put(name,valueStr);
        }
        logger.info("支付宝回调，sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //非常重要，验证回调的正确定，是不是支付宝发的，还要避免重复通知
        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
            if(!alipayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求，验证不通过！");
            }
        } catch (AlipayApiException e) {
            logger.info("支付宝验证回调异常！",e);
            e.printStackTrace();
        }
        //todo 验证各种数据
        /*
        ServerResponse serverResponse = iOrderService.alipayCallback(params);
        if(serverResponse.isSuccess()){
            return Const.AlipayCallback.RESPONSE_SUCCESS;
        }
        return Const.AlipayCallback.RESPONSE_FAILED;

         */
        return null;
    }

    @RequestMapping(value = "auth_callback.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse authCallback(String app_id,String app_auth_code,String application_type,String source)throws AlipayApiException{
        return iOrderService.authCallback(app_id,app_auth_code);
    }
}
