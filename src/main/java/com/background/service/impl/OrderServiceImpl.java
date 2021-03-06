package com.background.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
//import com.alipay.api.domain.ExtendParams;

import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipayTradePayResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.GoodsDetail;

import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.background.common.Const;
import com.background.common.ServerResponse;
import com.background.dao.*;
import com.background.pojo.*;
import com.background.service.IOrderService;
import com.background.util.DateUtil;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import net.sf.jsqlparser.schema.Server;
import org.apache.avro.LogicalTypes;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service("iOederService")
public class OrderServiceImpl implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static AlipayTradeService tradeService;
    private static AlipayClient alipayClient;

    private static final String PAY_SUCCESS = "SUCCESS";
    private static final String PAY_USERPAYING = "USERPAYING";

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;
    @Autowired
    private ShopperMapper shopperMapper;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", Const.APPID,Const.APP_PRIVATE_KEY,
                "json", "utf8", Const.ALIPAY_PUBLIC_KEY,"RSA2");
        /**
        AlipayConfigz.init("zfbinfo.properties");

        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setAppId(AlipayConfigz.getAppid());
        certAlipayRequest.setPrivateKey(AlipayConfigz.getPrivateKey());
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset(AlipayConfigz.getCharset());
        certAlipayRequest.setSignType(AlipayConfigz.getSignType());
        certAlipayRequest.setCertPath(AlipayConfigz.getApp_cert_path());
        certAlipayRequest.setAlipayPublicCertPath(AlipayConfigz.getAlipay_cert_path());
        certAlipayRequest.setRootCertPath(AlipayConfigz.getAlipay_root_cert_path());
        try {
            alipayClient = new DefaultAlipayClient(certAlipayRequest);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
         **/
    }
    /**
     * 证书模式的支付请求
    public ServerResponse aliPay() throws AlipayApiException {

        AlipayTradePayRequest request = new AlipayTradePayRequest();
        request.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"scene\":\"bar_code\"," +
                "    \"auth_code\":\"285680975322612605\"," +//即用户在支付宝客户端内出示的付款码，使用一次即失效，需要刷新后再去付款
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"store_id\":\"NJ_001\"," +
                "    \"timeout_express\":\"2m\"," +
                "    \"total_amount\":\"0.01\"" +
                "  }"); //设置业务参数
        System.out.println(alipayClient);
        AlipayTradePayResponse response = alipayClient.certificateExecute(request,"201912BB86639a8792a442f3a55ea3149cfdcX65"); //通过alipayClient调用API，获得对应的response类
        System.out.print(response.getBody());
        if(response.isSuccess()){
            System.out.println("调用成功！");
        }else{
            System.out.println("调用失败！");
        }

        return ServerResponse.createBySuccess(response.getBody());
    }
     **/

    public ServerResponse authCallback(String app_id,String app_auth_code)throws AlipayApiException{
        AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
        request.setBizContent(
            "{" + "\"grant_type\":\"authorization_code\"," +
                "\"code\":"+ "\"" + app_auth_code + "\"" +
                "}"
        );
        HashMap<String,String> map = new HashMap<>();
        AlipayOpenAuthTokenAppResponse response =alipayClient.execute(request);
        log.info(response.getBody());
        int index = response.getBody().indexOf("app_auth_token");
        String appAuthToken = response.getBody().substring(index+17,index+57);
        index = response.getBody().indexOf("user_id");
        String userId = response.getBody().substring(index+10,index+26);
        if(response.isSuccess()){
            log.info(response.getAppAuthToken());
            map.put("账户ID",userId);
            map.put("授权码",appAuthToken);
            return ServerResponse.createBySuccess("授权成功",map);
        }else{
            return ServerResponse.createByErrorMessage("授权失败");
        }
    }

    /**
     *
     * @param barCode  码
     * @param bizNo    商户业务流水号
     * @param codeType  码的类型
     * @param deviceSn  机器的sn码
     * @param totalAmount 总金额
     * @return
     */

    public ServerResponse pay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount)throws Exception{
        if(StringUtils.isEmpty(barCode)){
            return ServerResponse.createByErrorMessage("付款码出错！");
        }
        String subString = barCode.substring(0,2);
        if("10".equals(subString) || "11".equals(subString) || "12".equals(subString) || "13".equals(subString) || "14".equals(subString) || "15".equals(subString)){
            return wxPay(bizNo,barCode,codeType,deviceSn,totalAmount);
        }else if("25".equals(subString) || "26".equals(subString) || "27".equals(subString) || "28".equals(subString) || "29".equals(subString) || "30".equals(subString)){
            return zfbPay(bizNo,barCode,codeType,deviceSn,totalAmount);
        }else{
            return ServerResponse.createByErrorMessage("付款码出错！");
        }
    }

    public ServerResponse zfbPay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount){
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        //String outTradeNo = ""+System.currentTimeMillis()
          //      + (long) (Math.random() * 10000000L);
        String outTradeNo = bizNo;

        Device device = deviceMapper.selectBySN(deviceSn);
        if(device == null){
            return ServerResponse.createByErrorMessage("对不起，该机器尚未与后台商户进行绑定，无法发起支付请求！");
        }
        Shopper shopper = shopperMapper.selectByUserId(device.getUserId());


        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = shopper.getShoppername()+"当面付消费";
        //String subject = "当面付消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        //String totalAmount = "0.01";

        // (必填) 付款条码，用户支付宝钱包手机app点击“付款”产生的付款条码
        //String authCode = "用户自己的支付宝付款码"; // 条码示例，286648048691290423
        String authCode = barCode;
        // (可选，根据需要决定是否使用) 订单可打折金额，可以配合商家平台配置折扣活动，如果订单部分商品参与打折，可以将部分商品总价填写至此字段，默认全部商品可打折
        // 如果该值未传入,但传入了【订单总金额】,【不可打折金额】 则该值默认为【订单总金额】- 【不可打折金额】
        //        String discountableAmount = "1.00"; //

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        //String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = shopper.getZfbId();
        //String sellerId = "2088912095293655";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = "购买商品共"+totalAmount+"元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        //String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        //String storeId = "201999998887777";
        String storeId = shopper.getId().toString()+"-"+shopper.getUserId().toString()+"-"+shopper.getAgentId().toString();

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088631598114491";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        //List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        //GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        //goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        //GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        //goodsDetailList.add(goods2);

        //String appAuthToken = "应用授权令牌";//根据真实值填写
        //String appAuthToken = "201912BBbe1c26617d784bdd963923710f77fD65";
        String appAuthToken = shopper.getAuthcode();

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                .setAppAuthToken(appAuthToken)
                .setOutTradeNo(outTradeNo)
                .setSubject(subject)
                .setAuthCode(authCode)
                .setTotalAmount(totalAmount)
                .setStoreId(storeId)
                //.setUndiscountableAmount(undiscountableAmount)
                .setBody(body)
                //.setOperatorId(operatorId)
                .setExtendParams(extendParams)
                .setSellerId(sellerId)
                //.setGoodsDetailList(goodsDetailList)
                .setTimeoutExpress(timeoutExpress);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
        //log.info(result.toString());
        //log.info(result.getResponse().getBody());
        //System.out.println(result);
        //System.out.println(result.toString());
        String resultString = "原始返回信息";

        log.info(result.getResponse().getBody());
        log.info(result.getTradeStatus().toString());
        log.info(result.getTradeStatus().getClass().getName());

        if("SUCCESS".equals(result.getTradeStatus().toString())){
            log.info("进入SUCCESS!!!");
            PayOrder payOrder = new PayOrder();
            payOrder.setOrderNo(Long.parseLong(outTradeNo));
            payOrder.setUserId(shopper.getUserId());
            payOrder.setPayment(BigDecimal.valueOf(Double.valueOf(totalAmount)));
            payOrder.setStatus(10);
            payOrder.setPaymentTime(new Date());
            payOrder.setEndTime(new Date());
            payOrder.setCloseTime(new Date());
            log.info("payOrder参数完毕！！");
            PayInfo payInfo = new PayInfo();
            payInfo.setOrderNo(Long.parseLong(outTradeNo));
            payInfo.setUserId(shopper.getUserId());
            payInfo.setPayPlatform(1);
            if("C".equals(codeType)){
                payInfo.setPayType(1);
            }else if("F".equals(codeType)){
                payInfo.setPayType(2);
            }else{
                payInfo.setPayType(1);
            }
            payInfo.setPlatformNumber(result.getResponse().getOutTradeNo());
            payInfo.setPlatformStatus("10");
            log.info("参数都设置完毕！！！");
            payOrderMapper.insert(payOrder);
            payInfoMapper.insert(payInfo);
            log.info("插入数据库成功！！！");
            log.info("支付宝支付成功!!!");
            resultString = "支付成功";
        }else if("FAILED".equals(result.getTradeStatus().toString())){
            log.error("支付宝支付失败!!!");
            resultString = "支付失败";
        }else if("UNKNOWN".equals(result.getTradeStatus().toString())){
            log.error("系统异常，订单状态未知!!!");
            resultString = "支付失败";
        }else{
            log.error("不支持的交易状态，交易返回异常!!!");
            resultString = "支付失败";
        }
        /**
        switch (result.getTradeStatus()) {
            case SUCCESS:
                PayOrder payOrder = new PayOrder();
                payOrder.setOrderNo(Long.parseLong(outTradeNo));
                payOrder.setUserId(shopper.getUserId());
                payOrder.setPayment(BigDecimal.valueOf(Double.valueOf(totalAmount)));
                payOrder.setStatus(10);
                payOrder.setPaymentTime(new Date());
                payOrder.setEndTime(new Date());
                payOrder.setCloseTime(new Date());
                PayInfo payInfo = new PayInfo();
                payInfo.setOrderNo(Long.parseLong(outTradeNo));
                payInfo.setUserId(shopper.getUserId());
                payInfo.setPayPlatform(1);
                if("C".equals(codeType)){
                    payInfo.setPayType(1);
                }else if("F".equals(codeType)){
                    payInfo.setPayType(2);
                }else{
                    payInfo.setPayType(1);
                }
                payInfo.setPlatformNumber(result.getResponse().getOutTradeNo());
                payInfo.setPlatformStatus("10");

                payOrderMapper.insert(payOrder);
                payInfoMapper.insert(payInfo);
                log.info("支付宝支付成功!!!");
                resultString = "支付成功";
                break;
            case FAILED:
                log.error("支付宝支付失败!!!");
                resultString = "支付失败";
                break;
            case UNKNOWN:
                log.error("系统异常，订单状态未知!!!");
                resultString = "支付失败";
                break;
            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                resultString = "支付失败";
                break;
        }
         */
        return ServerResponse.createBySuccessMessage(resultString);
    }

    public ServerResponse wxPay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount) throws Exception {


        log.info(totalAmount);
        String totalAmountNew = String.valueOf((int)(Double.valueOf(totalAmount) * 100));
        log.info(totalAmountNew);
        //String outTradeNo = "" + System.currentTimeMillis()
        //        + (long) (Math.random() * 10000000L);
        String outTradeNo = bizNo;
        Device device = deviceMapper.selectBySN(deviceSn);
        if(device == null){
            return ServerResponse.createByErrorMessage("对不起，该机器尚未与后台商户进行绑定，无法发起支付请求！");
        }
        Shopper shopper = shopperMapper.selectByUserId(device.getUserId());

        String subject = shopper.getShoppername()+"微信消费";

        MyConfig config = new MyConfig();
        WXPay wxpay = new WXPay(config);
        //String out_trade_no = DateUtil.getCurrentTime();
        Map<String, String> map = new HashMap<>(16);
        map.put("sub_mch_id",shopper.getWxId());
        map.put("attach", subject);
        map.put("auth_code", barCode);
        map.put("body", "购买商品共"+totalAmount+"元.");
        map.put("device_info", deviceSn);
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("out_trade_no", outTradeNo);
        map.put("spbill_create_ip", Const.IP);
        map.put("total_fee", totalAmountNew);
        //生成签名
        String sign = WXPayUtil.generateSignature(map, config.getKey());
        map.put("sign", sign);

        String mapToXml = null;
        try {
            //调用微信的扫码支付接口
            Map<String, String> resp = wxpay.microPay(map);
            mapToXml = WXPayUtil.mapToXml(resp);
            log.info(mapToXml);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("微信支付失败"+ e);
        }
        //判断支付是否成功
        String return_code = null;
        String return_msg = null;
        String result_code = null;
        String err_code_des = null;
        String err_code = null;
        //获取Document对象（主要是获取支付接口的返回信息）
        Document doc = DocumentHelper.parseText(mapToXml);

        //获取对象的根节点<xml>
        Element rootElement = doc.getRootElement();
        //获取对象的子节点
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            if(element.getName().equals("return_code")){
                return_code = element.getTextTrim();
                log.info("return_code"+return_code);
            } else if(element.getName().equals("result_code")){
                result_code = element.getTextTrim();
                log.info("result_code"+result_code);
            } else if(element.getName().equals("err_code_des")){
                err_code_des = element.getTextTrim();
                log.info("err_code_des"+err_code_des);
            } else if(element.getName().equals("err_code")){
                err_code = element.getTextTrim();
                log.info("err_code"+err_code);
            } else if(element.getName().equals("return_msg")){
                return_msg = element.getTextTrim();
                log.info("return_msg "+return_msg);
            }
        }
        if(PAY_SUCCESS.equals(return_code) && PAY_SUCCESS.equals(result_code)){
            log.info("微信免密支付成功！");

            PayOrder payOrder = new PayOrder();
            payOrder.setOrderNo(Long.parseLong(outTradeNo));
            payOrder.setUserId(shopper.getUserId());
            payOrder.setPayment(BigDecimal.valueOf(Double.valueOf(totalAmount)));
            payOrder.setStatus(10);
            payOrder.setPaymentTime(new Date());
            payOrder.setEndTime(new Date());
            payOrder.setCloseTime(new Date());

            PayInfo payInfo = new PayInfo();
            payInfo.setOrderNo(Long.parseLong(outTradeNo));
            payInfo.setUserId(shopper.getUserId());
            payInfo.setPayPlatform(2);
            payInfo.setPayType(1);

            payInfo.setPlatformNumber(outTradeNo);
            payInfo.setPlatformStatus("10");

            payOrderMapper.insert(payOrder);
            payInfoMapper.insert(payInfo);

            return ServerResponse.createBySuccessMessage("支付成功！");
        } else if (PAY_USERPAYING.equals(err_code)){
            for(int i = 0; i < 4; i++){
                Thread.sleep(3000);
                Map<String, String> data = new HashMap<>(16);
                data.put("out_trade_no", outTradeNo);
                data.put("sub_mch_id","1569327041");
                data.put("nonce_str", WXPayUtil.generateNonceStr());
                sign = WXPayUtil.generateSignature(data, config.getKey());
                data.put("sign", sign);
                //调用微信的查询接口
                Map<String, String> orderQuery = wxpay.orderQuery(data);
                String orderResp = WXPayUtil.mapToXml(orderQuery);
                log.info("orderResp:"+orderResp);
                String trade_state = null;

                //获取Document对象
                Document orderDoc = DocumentHelper.parseText(orderResp);
                //获取对象的根节点<xml>
                Element rootElement1 = orderDoc.getRootElement();
                //获取对象的子节点
                List<Element> elements1 = rootElement1.elements();
                for (Element element : elements1) {
                    if(element.getName().equals("trade_state")){
                        trade_state = element.getTextTrim();
                    }
                }
                log.info(trade_state);
                if(PAY_SUCCESS.equals(trade_state)){
                    log.info("微信加密支付成功！");

                    PayOrder payOrder = new PayOrder();
                    payOrder.setOrderNo(Long.parseLong(outTradeNo));
                    payOrder.setUserId(shopper.getUserId());
                    payOrder.setPayment(BigDecimal.valueOf(Double.valueOf(totalAmount)));
                    payOrder.setStatus(10);
                    payOrder.setPaymentTime(new Date());
                    payOrder.setEndTime(new Date());
                    payOrder.setCloseTime(new Date());

                    PayInfo payInfo = new PayInfo();
                    payInfo.setOrderNo(Long.parseLong(outTradeNo));
                    payInfo.setUserId(shopper.getUserId());
                    payInfo.setPayPlatform(2);
                    payInfo.setPayType(1);

                    payInfo.setPlatformNumber(outTradeNo);
                    payInfo.setPlatformStatus("10");

                    payOrderMapper.insert(payOrder);
                    payInfoMapper.insert(payInfo);

                    return ServerResponse.createBySuccessMessage("支付成功！");

                }
                log.info("正在支付" + orderResp);
            }
        }
        log.error("微信支付失败！");
        return ServerResponse.createByErrorMessage("支付失败！");
    }
}
