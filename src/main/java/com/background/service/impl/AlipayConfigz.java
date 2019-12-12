package com.background.service.impl;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.configuration.Configuration;

public class AlipayConfigz {

    private static Log log = LogFactory.getLog(AlipayConfigz.class);
    private static Configuration configs;

    public static Configuration getConfigs() {
        return configs;
    }

    public static void setConfigs(Configuration configs) {
        AlipayConfigz.configs = configs;
    }

    public static String getPid() {
        return pid;
    }

    public static void setPid(String pid) {
        AlipayConfigz.pid = pid;
    }

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        AlipayConfigz.appid = appid;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(String privateKey) {
        AlipayConfigz.privateKey = privateKey;
    }

    public static String getCharset() {
        return charset;
    }

    public static void setCharset(String charset) {
        AlipayConfigz.charset = charset;
    }

    public static String getSignType() {
        return signType;
    }

    public static void setSignType(String signType) {
        AlipayConfigz.signType = signType;
    }

    public static String getApp_cert_path() {
        return app_cert_path;
    }

    public static void setApp_cert_path(String app_cert_path) {
        AlipayConfigz.app_cert_path = app_cert_path;
    }

    public static String getAlipay_cert_path() {
        return alipay_cert_path;
    }

    public static void setAlipay_cert_path(String alipay_cert_path) {
        AlipayConfigz.alipay_cert_path = alipay_cert_path;
    }

    public static String getAlipay_root_cert_path() {
        return alipay_root_cert_path;
    }

    public static void setAlipay_root_cert_path(String alipay_root_cert_path) {
        AlipayConfigz.alipay_root_cert_path = alipay_root_cert_path;
    }

    private static String pid;
    private static String appid;
    private static String privateKey;
    private static String charset;
    private static String signType;
    private static String app_cert_path;
    private static String alipay_cert_path;
    private static String alipay_root_cert_path;

    private AlipayConfigz() {
    }

    public static synchronized void init(String filePath) {
        if (configs == null) {
            try {
                configs = new PropertiesConfiguration(filePath);
            } catch (ConfigurationException var2) {
                var2.printStackTrace();
            }

            if (configs == null) {
                throw new IllegalStateException("can`t find file by path:" + filePath);
            } else {
                pid = configs.getString("pid");
                appid = configs.getString("appid");
                privateKey = configs.getString("private_key");
                charset = configs.getString("charset");
                signType = configs.getString("sign_type");
                app_cert_path = configs.getString("app_cert_path");
                alipay_cert_path = configs.getString("alipay_cert_path");
                alipay_root_cert_path = configs.getString("alipay_root_cert_path");
                log.info("配置文件名: " + filePath);
            }
        }
    }
}
