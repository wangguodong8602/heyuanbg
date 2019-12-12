package com.background.common;

import com.google.common.collect.Sets;

import java.util.Set;

public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String PHONE = "phone";
    public static final String USERNAME = "username";
    public static final String SHOPPERNAME = "shoppername";
    public static final String BUSSINESSLICENSE = "bussinessLicense";

    public static final String APPID = "2019121269796990";
    public static final String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvKsxtXly5jLp4WLNzDZMMNV960l423bGmO+K812eYnJ9UilCAT1UZUkQb4kvXNnmbyI3Q7pKiGpmGLeymwIhieXPiDkkMgbMCIUAhKlUQYs2k/A/xE9YaFjXwLyzyhPdvCjxKQulKGTXLWxxIBgCRjyXzQJNjDRTKwLUho1MFCc6wusSIEHKafW/QZ1srWSDsl5ITkMrofeeDRPG4lmk5JQ9IkwR6gzVV2olZb05BxZxDWvG3UAt6+NByVYm56BPzoGQlPSipleTftjVUg0puxe6hefTPqB2PtCjiRJ7kpeuyiiInKG13BJXvbL3dgDjiLYmj97m6GsJ6HokAWnNvAgMBAAECggEAA9XhEgf96cYuExKGI1/Jt3u3sn3GuFbUW/p1tYLV7t6HEpfQuW6DQ4GsohurY3P657FVgZyns0r3z5tT0jbnBZj5M5ryeIaif8pwmAlQQYKYVCN8yd+QUZPo7HBsAIduz4ye01VxvECfD+vXy6/t9UH4J8JjN8kCion7rVfhKnwKDTOt3iP2y1ei4T62NQjltF892Pta0nBgazNKaVxpQQAfg0dzYZPH/UTZ0fmLWxpWMTvCKDBlZCMRzyoT2Gq35pZkPR4YzNSSJZczGFXafXf0z+2LZiuGHCIv/y3inPhK1gg0fSw6op3uCuvBW33e9baXAYZ/sfKLB50ofUMQ4QKBgQD/sdaA5+IMJb1XygTOXhfDi7NbnJM6Oo/2umLCWEh8VXKMtX8ytCENHMTMv1NqUZArOPC59Z268xMH5EsMXEAPeBPDe5buR5/g8eWYjTrajYEcTWtpuxVjAZorCUnzyaFsEMm48LkXQPKta55XJlXKjZ4CE0lwW7t5P2JxiA1wHwKBgQCvYFg1vi6XGpU9JxrA8GGF05xHjRNdVnq4qkBFBj7UjdIsVkAfX2tVK44jkXsYczUIOEWTo/vjnQtvUIOeo08eeVs+GLsdfsuPlc2iv579pfYZLDXpJYjd8FhoSQie0m1AmYm6fo7E2E+EGpaDZLSbt6bFcsxlec2KI1TIAhpSsQKBgQCPd5VH1tP6xOIsc+mmcR0cVzJX4M6rCVjls0bejPJ+8OLJGL06Ed/Jn668w01mhkVCOzy22p2Z2/F9qBaD5O78vv833AyfvghzCscshXHUVzluS/JM7O/fEXMTWq7n3uZ34COPZWI1Lff8XjoF5DiW4kcaGwBFzvKt4Wa3FQOVOQKBgA/KgAbOygknBpsMA1euDUnshNzYcg7sc35fLgxJBK1E29DML1fj0plHo7PGLKxF61eDIZiqokqbYGU0fbVyBPS8fp+3wFlmilOybGKkGF/EasVXer80dv9s3bj+In2Seu3fJUyaiqZkx7wgXL8V+UEwJx8OOCNG9V/j0IV10wLxAoGBAJcqp8u2v5jLXwRrhYNqSu+H6HF7gT34sswY18lwXIO5kb31SRfbM36lpyigDV+LpBbKp5DU1/hkLJvTB4/0kIvG3XnXdK/RDOJvypHneMpozkDOps3at3tK8XKkD40rh4UklvopeBF5L8ssbj27s9GZbMjMGTPmUj2EeRov62Ef";
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2aQBzPylsUzuAs7qT0QicNi8ilwLCYVHVHaroc1miPUVePNg0QmsE/H8ZxBo7HcKA7Fb2rsfwdJYxFaicdgHfievlvGK2cw5F9LdQ264tbn0mM558MNSMjMRlECLC72kQiMRznoL0b4cjMr6vrqyZbupFwh0B4S7RWcb+Kq9lqh6z0jrnl08aGM2Eff9iucNpHzyA93pylvk6vOIJEcFsz8oCXhi6tgJ8zHtVlgD4xnxjkpL3iJoi0Oe02s6ICI/L8F1+DuBq83+dghRsxqdnoSv0+EsCwZWQ4NnWC1g9E8KP6aVqemxsNfCpvp2jh5Mc0JOOvndZML6ApxaRJTdwwIDAQAB";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }

    public interface Cart{
        int CHECKED = 1;//即购物车选中状态
        int UNCHECKED = 0;//购物车未选中状态

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }

    public interface Role{

        int ROLE_SHOPPER = 2;//商家
        int ROLE_CUSTOMER = 1;//普通代理
        int ROLE_ADMIN = 0;//管理员
    }
    public enum ProductStatusEnum{
        ON_SALE(1,"在线");
        private String value;
        private int code;
        ProductStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSED(60,"订单关闭");
        private String value;
        private int code;

        OrderStatusEnum(int code,String value){
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public interface AlipayCallback{
        String TRADE_STATUS_WATI_BUYER_PAY = "WATI_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_SUCCESS = "RESPONSE_SUCCESS";
        String RESPONSE_FAILED = "RESPONSE_FAILED";

    }

    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝")
        ;

        private String value;
        private int code;

        PayPlatformEnum(int code,String value){
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
