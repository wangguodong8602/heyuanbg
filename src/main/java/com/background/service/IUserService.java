package com.background.service;

import com.background.common.ServerResponse;
import com.background.pojo.Shopper;
import com.background.pojo.User;
import com.background.vo.OrderSearch;
import com.background.vo.ShopperDevice;
import com.background.vo.ShopperSearch;
import com.background.vo.UserSearch;

import java.math.BigDecimal;
import java.util.Map;

public interface IUserService {

    ServerResponse<User> login(String username, String password);
    ServerResponse<String> register(User user);
    ServerResponse<String> checkValid(String str, String type);
    ServerResponse selectQuestion(String username);
    ServerResponse<String> checkAnswer(String username, String question, String answer);
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, User user);
    ServerResponse<User> updateInformation(User user);
    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
    ServerResponse getUserList(int userID, int pageNum, int pageSize, UserSearch userSearch);

    ServerResponse<User> updateUser(User user);
    ServerResponse<String> deleteUserById(int id);
    ServerResponse getShopperList(int userID, int pageNum, int pageSize, ShopperSearch shopperSearch);
    ServerResponse<String> addShopper(ShopperDevice shopperDevice);

    ServerResponse<Shopper> updateShopper(Shopper shopper);
    ServerResponse<String> deleteShopperById(int id);

    ServerResponse getOrderList(int userID, int pageNum, int pageSize, OrderSearch orderSearch);

    Map<String, BigDecimal> getOrderAccount(int userID, OrderSearch orderSearch);
}
