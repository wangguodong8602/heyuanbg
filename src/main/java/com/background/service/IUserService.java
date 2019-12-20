package com.background.service;

import com.background.common.ServerResponse;
import com.background.pojo.Shopper;
import com.background.pojo.User;
import com.background.vo.*;

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

    ServerResponse<String> refreshPassword(Integer currUserId,Integer id);

    ServerResponse<User> updateInformation(User user);
    ServerResponse<User> getInformation(Integer userId);

    ServerResponse checkAdminRole(User user);
    ServerResponse getUserList(int userID, int pageNum, int pageSize, UserSearch userSearch);

    ServerResponse<User> updateUser(EditUser editUser);
    ServerResponse<String> deleteUserById(int id);
    ServerResponse getShopperList(int userID, int pageNum, int pageSize, ShopperSearch shopperSearch);
    ServerResponse<String> addShopper(ShopperDevice shopperDevice);

    ServerResponse<Shopper> updateShopper(EditShopper editShopper);
    ServerResponse<String> deleteShopperById(int id);

    ServerResponse getOrderList(int userID, int pageNum, int pageSize, OrderSearch orderSearch);

    ServerResponse getOrderShow(int userID, int pageNum, int pageSize, OrderSearch orderSearch);

    ServerResponse getCommisionShow(int userID, int pageNum, int pageSize, OrderSearch orderSearch);

    Map<String, BigDecimal> getOrderAccount(int userID, OrderSearch orderSearch);

    Map<String, String> getCommisionAccount(int userID, OrderSearch orderSearch);
}
