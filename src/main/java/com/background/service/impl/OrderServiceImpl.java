package com.background.service.impl;

import com.alipay.api.AlipayClient;
//import com.alipay.api.domain.ExtendParams;

import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.GoodsDetail;

import com.alipay.demo.trade.model.builder.AlipayTradePayRequestBuilder;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.result.AlipayF2FPayResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.background.common.ServerResponse;
import com.background.dao.*;
import com.background.pojo.*;
import com.background.service.IOrderService;
import org.apache.avro.LogicalTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private static AlipayTradeService tradeService;

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

    public ServerResponse pay(String bizNo,String barCode, String codeType, String deviceSn, String totalAmount){
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        //String outTradeNo = "tradepay" + System.currentTimeMillis()
        //        + (long) (Math.random() * 10000000L);
        String outTradeNo = bizNo;

        Device device = deviceMapper.selectBySN(deviceSn);
        Shopper shopper = shopperMapper.selectByUserId(device.getUserId());


        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店消费”
        String subject = shopper.getShoppername()+"当面付消费";

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
        String undiscountableAmount = "0.0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        //String sellerId = "";
        String sellerId = shopper.getZfbId();

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品3件共20.00元"
        String body = "购买商品共"+totalAmount+"元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        //String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        //String storeId = "test_store_id";
        String storeId = shopper.getId().toString()+"-"+shopper.getUserId().toString()+"-"+shopper.getAgentId().toString();

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        String providerId = "2088100200300400500";
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId(providerId);

        // 支付超时，线下扫码交易定义为5分钟
        String timeoutExpress = "5m";

        // 商品明细列表，需填写购买商品详细信息，
        //List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "xxx面包", 1000, 1);
        // 创建好一个商品后添加至商品明细列表
        //goodsDetailList.add(goods1);

        // 继续创建并添加第一条商品信息，用户购买的产品为“黑人牙刷”，单价为5.00元，购买了两件
        //GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "xxx牙刷", 500, 2);
        //goodsDetailList.add(goods2);

        //String appAuthToken = "应用授权令牌";//根据真实值填写
        String appAuthToken = shopper.getAuthcode();

        // 创建条码支付请求builder，设置请求参数
        AlipayTradePayRequestBuilder builder = new AlipayTradePayRequestBuilder()
                .setAppAuthToken(appAuthToken)
                .setOutTradeNo(outTradeNo)
                .setSubject(subject).setAuthCode(authCode)
                .setTotalAmount(totalAmount).setStoreId(storeId)
                .setUndiscountableAmount(undiscountableAmount)
                .setBody(body)
                //.setOperatorId(operatorId)
                .setExtendParams(extendParams)
                .setSellerId(sellerId)
                //.setGoodsDetailList(goodsDetailList)
                .setTimeoutExpress(timeoutExpress);

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderNo(Long.parseLong(bizNo));
        payOrder.setUserId(shopper.getUserId());
        payOrder.setPayment(BigDecimal.valueOf(Double.valueOf(totalAmount)));
        payOrder.setStatus(10);
        payOrder.setPaymentTime(new Date());
        payOrder.setEndTime(new Date());
        payOrder.setCloseTime(new Date());

        PayInfo payInfo = new PayInfo();
        payInfo.setOrderNo(Long.parseLong(bizNo));
        payInfo.setUserId(shopper.getUserId());
        payInfo.setPayPlatform(1);
        payInfo.setPayType(1);
        payInfo.setPlatformNumber("15596662963");
        payInfo.setPlatformStatus("10");

        payOrderMapper.insert(payOrder);
        payInfoMapper.insert(payInfo);

        // 调用tradePay方法获取当面付应答
        AlipayF2FPayResult result = tradeService.tradePay(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝支付成功: )");
                break;

            case FAILED:
                log.error("支付宝支付失败!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return null;
    }
}
