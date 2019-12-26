package com.background.controller.portal;

import com.background.common.Const;
import com.background.common.ResponseCode;
import com.background.common.ServerResponse;
import com.background.dao.ShopperMapper;
import com.background.dao.UserMapper;
import com.background.pojo.Device;
import com.background.pojo.Shopper;
import com.background.pojo.User;
import com.background.service.IOrderService;
import com.background.service.IUserService;
import com.background.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopperMapper shopperMapper;
    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //service-->mybatis->dao
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    //@ResponseBody
    public String logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return "/login.jsp";
    }

    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(HttpSession session, User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        user.setParentId(currentUser.getId());
        return iUserService.register(user);
    }

    @RequestMapping(value = "/add_device.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addDevice(HttpSession session, Device device){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser.getRole() != 4){
            return ServerResponse.createByErrorMessage("对不起！您没有添加设备权限！");
        }
        device.setUserId(currentUser.getId());
        device.setAgentId(currentUser.getParentId());
        return iUserService.addDevice(device);
    }

    @RequestMapping(value = "/check_valid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    @RequestMapping(value = "/get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户信息");
    }

    @RequestMapping(value = "/forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    @RequestMapping(value = "/forget_check_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "/forget_reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    @RequestMapping(value = "/reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session, String passwordOld, String passwordNew1, String passwordNew2){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(!passwordNew1.equals(passwordNew2)){
            return ServerResponse.createByErrorMessage("两次输入的新密码不一致，请重新输入~");
        }
        return iUserService.resetPassword(passwordOld,passwordNew1,user);
    }

    @RequestMapping(value = "/refresh_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> refreshPassword(HttpSession session, int id){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        int currUserId = user.getId();
        return iUserService.refreshPassword(currUserId,id);
    }

    @RequestMapping(value = "/update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> update_information(HttpSession session, User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        //user.setUsername(currentUser.getUsername());
        user.setRole(currentUser.getRole());
        ServerResponse<User> response = iUserService.updateInformation(user);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "/get_information.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());

    }

    @RequestMapping(value = "/get_user_list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int pageNum, @RequestParam(value = "limit",defaultValue = "10") int pageSize, UserSearch userSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getUserList(currentUser.getId(),pageNum,pageSize,userSearch);

    }

    @RequestMapping(value = "/get_shopper_list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ShopperInfo> getShopperList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int pageNum, @RequestParam(value = "limit",defaultValue = "10") int pageSize, ShopperSearch shopperSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getShopperList(currentUser.getId(),pageNum,pageSize,shopperSearch);

    }

    @RequestMapping(value = "/get_device_list.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<DeviceInfo> getDeviceList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int pageNum, @RequestParam(value = "limit",defaultValue = "10") int pageSize, ShopperSearch shopperSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getDeviceList(currentUser.getId(),pageNum,pageSize,shopperSearch);

    }

    @RequestMapping(value = "/get_order_list.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getOrderList(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int pageNum, @RequestParam(value = "limit",defaultValue = "10") int pageSize, OrderSearch orderSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getOrderList(currentUser.getId(),pageNum,pageSize,orderSearch);

    }

    @RequestMapping(value = "/get_order_show.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getOrderShow(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int pageNum, @RequestParam(value = "limit",defaultValue = "10") int pageSize, OrderSearch orderSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getOrderShow(currentUser.getId(),pageNum,pageSize,orderSearch);

    }

    @RequestMapping(value = "/get_commision_show.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getCommisionShow(HttpSession session, @RequestParam(value = "page",defaultValue = "1") int pageNum, @RequestParam(value = "limit",defaultValue = "10") int pageSize, OrderSearch orderSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        return iUserService.getCommisionShow(currentUser.getId(),pageNum,pageSize,orderSearch);

    }

    @RequestMapping(value = "/get_order_account.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map> getOrderAccount(HttpSession session, OrderSearch orderSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        Map<String, BigDecimal> accountMap = iUserService.getOrderAccount(currentUser.getId(),orderSearch);
        return ServerResponse.createBySuccess(accountMap);

    }

    @RequestMapping(value = "/get_commision_account.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Map> getCommisionAccount(HttpSession session, OrderSearch orderSearch){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录，需要强制登录status=10");
        }
        Map<String, String> accountMap = iUserService.getCommisionAccount(currentUser.getId(),orderSearch);
        return ServerResponse.createBySuccess(accountMap);

    }

    @RequestMapping(value = "/editUser.do", method = RequestMethod.GET)
    public String editUser(int id, HttpSession session) {
        User user = userMapper.selectByPrimaryKey(id);
        session.setAttribute("user", user);

        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser.getRole() == 0){
            return "/jsp/isv/editUser.jsp";
        }else if(currentUser.getRole() == 1){
            return "/jsp/first/editUser.jsp";
        }else if(currentUser.getRole() == 2){
            return "/jsp/second/editUser.jsp";
        }else if(currentUser.getRole() == 3){
            return "/jsp/third/editUser.jsp";
        }else{
            return "/jsp/forth/editUser.jsp";
        }
    }

    @RequestMapping(value = "/index.do", method = RequestMethod.GET)
    public String index(HttpSession session) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser.getRole() == 0){
            return "/jsp/isv/index.jsp";
        }else if(currentUser.getRole() == 1){
            return "/jsp/first/index.jsp";
        }else if(currentUser.getRole() == 2){
            return "/jsp/second/index.jsp";
        }else if(currentUser.getRole() == 3){
            return "/jsp/third/index.jsp";
        }else{
            return "/jsp/forth/index.jsp";
        }

    }

    @RequestMapping(value = "/editShopper.do", method = RequestMethod.GET)
    public String editShopper(int id, HttpSession session) {
        Shopper shopper = shopperMapper.selectByPrimaryKey(id);
        session.setAttribute("shopper", shopper);
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser.getRole() == 0){
            return "/jsp/isv/editShopper.jsp";
        }else if(currentUser.getRole() == 1){
            return "/jsp/first/editShopper.jsp";
        }else if(currentUser.getRole() == 2){
            return "/jsp/second/editShopper.jsp";
        }else if(currentUser.getRole() == 3){
            return "/jsp/third/editShopper.jsp";
        }else{
            return "/jsp/forth/editShopper.jsp";
        }
    }

    @RequestMapping(value = "updateUser.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateUser(EditUser editUser){
        return iUserService.updateUser(editUser);
    }


    @RequestMapping(value = "updateShopper.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Shopper> updateShopper(EditShopper editShopper){
        return iUserService.updateShopper(editShopper);
    }

    @RequestMapping(value = "deleteUserById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteUserById(HttpSession session,int id){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        User user = userMapper.selectByPrimaryKey(id);
        if(currentUser.getId() < user.getId()){
            return iUserService.deleteUserById(id);
        }else{
            return ServerResponse.createByErrorMessage("对不起，您没有删除权限！");
        }
    }

    @RequestMapping(value = "deleteShopperById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteShopperById(HttpSession session,int id){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        Shopper shopper = shopperMapper.selectByPrimaryKey(id);
        if(currentUser.getId() <= shopper.getAgentId()){
            return iUserService.deleteShopperById(id);
        }else{
            return ServerResponse.createByErrorMessage("对不起，您没有删除权限！");
        }
    }

    @RequestMapping(value = "deleteDeviceById.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> deleteDeviceById(HttpSession session,int id){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        return iUserService.deleteDeviceById(id);
    }

    @RequestMapping(value = "/addShopper.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addShopper(HttpSession session, ShopperDevice shopperDevice){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if(currentUser.getRole()!=4){
            return ServerResponse.createByErrorMessage("对不起，您没有操作权限，此操作仅适用于商户用户！");
        }
        shopperDevice.setUserId(currentUser.getId());
        shopperDevice.setAgentId(currentUser.getParentId());
        return iUserService.addShopper(shopperDevice);
    }


}
