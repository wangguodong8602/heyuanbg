package com.background.service.impl;

import com.background.common.Const;
import com.background.common.ServerResponse;
import com.background.common.TokenCache;
import com.background.dao.*;
import com.background.pojo.*;
import com.background.service.IUserService;
import com.background.util.MD5Util;
import com.background.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ShopperMapper shopperMapper;
    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private PayOrderMapper payOrderMapper;
    @Autowired
    private PayInfoMapper payInfoMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        //todo 密码登录MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    public ServerResponse<String> register(User user){
//        int resultCount = userMapper.checkUsername(user.getUsername());
//        if(resultCount > 0){
//            return ServerResponse.createByErrorMessage("用户名已存在");
//        }
//
        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

//        resultCount = userMapper.checkEmail(user.getEmail());
//        if(resultCount > 0){
//            return ServerResponse.createByErrorMessage("email已存在");
//        }

//        validResponse = this.checkValid(user.getPhone(),Const.PHONE);
//        if(!validResponse.isSuccess()){
//            return validResponse;
//        }

        //user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            //开始校验
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
            if(Const.PHONE.equals(type)){
                int resultCount = userMapper.checkPhone(str);
                if(resultCount > 0){
                    return ServerResponse.createByErrorMessage("phone已存在");
                }
            }
        }else {
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在！");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccessMessage(question);
        }
        return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    public ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount > 0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createBySuccessMessage("问题的答案错误");
    }

    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        if(StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，token需要传递！");
        }
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在！");
        }
        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("token无效或者过期");
        }
        if(StringUtils.equals(forgetToken,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username,md5Password);
            if(rowCount > 0){
                return ServerResponse.createBySuccessMessage("重置密码成功！");
            }
        }else{
            return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByErrorMessage("修改密码失败！");
    }

    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        //防止横向越权，要校验一下这个用户的旧密码，一定要指定是这个用户，因为我们会查询一个count(1)，如果不指定id,那么结果就是true啦
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误！");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功！");
        }
        return ServerResponse.createByErrorMessage("密码更新失败！");
    }

    public ServerResponse<String> refreshPassword(Integer currUserId,Integer id){
        //防止横向越权，要校验一下这个用户的旧密码，一定要指定是这个用户，因为我们会查询一个count(1)，如果不指定id,那么结果就是true啦
        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,currUserId);
        Set<Integer> idSet = Sets.newHashSet();
        for(User userItem:userSet){
            idSet.add(userItem.getId());
        }
        if(!idSet.contains(id)){
            return  ServerResponse.createByErrorMessage("对不起，权限不足！");
        }else{
            User user = userMapper.selectByPrimaryKey(id);
            user.setPassword(MD5Util.MD5EncodeUtf8("123456"));
            int updateCount = userMapper.updateByPrimaryKeySelective(user);
            if(updateCount > 0){
                return ServerResponse.createBySuccessMessage("密码重置成功！");
            }
            return ServerResponse.createByErrorMessage("密码重置失败！");
        }

    }

    public ServerResponse<User> updateInformation(User user){
        //username是不能被更新的
        //phone也要进行一个校验，校验新的phone是不是已经存在，并且存在的phone如果相同的话，不能是我们当前的这个用户的
        int resultCount = userMapper.checkPhoneByUserId(user.getPhone(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByErrorMessage("phone已经存在，请更换phone再尝试更新");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setParentId(user.getParentId());
        updateUser.setUsername(user.getUsername());
        updateUser.setRealname(user.getRealname());
        updateUser.setPhone(user.getPhone());
        updateUser.setIdentityId(user.getIdentityId());
        updateUser.setBankId(user.getBankId());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        updateUser.setRole(user.getRole());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新个人信息失败！");
    }

    public ServerResponse<User> getInformation(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户！");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }


    //backend

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    public ServerResponse getUserList(int userID, int pageNum, int pageSize, UserSearch userSearch){
        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID,userSearch);
        List<User> userList = Lists.newArrayList();
        List<Integer> idList = Lists.newArrayList();
        for(User userItem : userSet){
            if(userItem.getId() != userID){
                idList.add(userItem.getId());
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        userList = userMapper.selectUserByIdList(idList);
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //升序
                return o1.getRole().compareTo(o2.getRole());
            }
        });
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        int count=(int)pageInfo.getTotal();
        return ServerResponse.createBySuccess("",count,pageInfo.getList());
    }

    public ServerResponse getShopperList(int userID, int pageNum, int pageSize, ShopperSearch shopperSearch){
        //PageHelper.startPage(pageNum,pageSize);
        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID);
        List<User> userList = Lists.newArrayList();
        for(User userItem : userSet){
            userList.add(userItem);
        }

        User hostUser = userMapper.selectByRealname(shopperSearch.getHostname());
        User agentUser = userMapper.selectByRealname(shopperSearch.getAgentname());
        Integer hostUserId;
        Integer agentUserId;
        if(hostUser != null){
            hostUserId = hostUser.getId();
        }else{
            hostUserId = null;
        }
        if(agentUser != null){
            agentUserId = agentUser.getId();
        }else{
            agentUserId = null;
        }
        List<ShopperInfo> shopperInfoList = Lists.newArrayList();
        List<Shopper> shopperList = Lists.newArrayList();
        Set<Integer> idSet = new HashSet<Integer>();
        List<Integer> idList = Lists.newArrayList();

        for(User userItem : userList){
            List<Shopper> shopperListtemp = shopperMapper.selectShopperByCondition(userItem.getId(),hostUserId,agentUserId);
            if(shopperList == null){
                continue;
            }
            for(Shopper shopperItem : shopperListtemp){
                if(!idSet.contains(shopperItem.getId())){
                    idSet.add(shopperItem.getId());
                    idList.add(shopperItem.getId());
                }
            }
        }
        Collections.sort(idList);
        PageHelper.startPage(pageNum,pageSize);
        shopperList = shopperMapper.selectShopperByIdList(idList);
        for(Shopper shopperItem:shopperList){
            User userHost = userMapper.selectByPrimaryKey(shopperItem.getUserId());
            User userAgent = userMapper.selectByPrimaryKey(shopperItem.getAgentId());
            ShopperInfo shopperInfo = new ShopperInfo();
            shopperInfo.setId(shopperItem.getId());
            shopperInfo.setHostname(userHost.getRealname());
            shopperInfo.setAgentname(userAgent.getRealname());
            shopperInfo.setShoppername(shopperItem.getShoppername());
            shopperInfo.setAddress(shopperItem.getAddress());
            shopperInfo.setPhone(shopperItem.getPhone());
            shopperInfo.setZfbId(shopperItem.getZfbId());
            shopperInfo.setWxId(shopperItem.getWxId());
            shopperInfo.setYsfId(shopperItem.getYsfId());
            shopperInfo.setBussinessLicense(shopperItem.getBussinessLicense());
            shopperInfo.setAuthcode(shopperItem.getAuthcode());
            shopperInfo.setCreateTime(shopperItem.getCreateTime());
            shopperInfo.setUpdateTime(shopperItem.getUpdateTime());

            shopperInfoList.add(shopperInfo);
        }

        Collections.sort(shopperInfoList, new Comparator<ShopperInfo>() {
            @Override
            public int compare(ShopperInfo o1, ShopperInfo o2) {
                //升序
                return o1.getId().compareTo(o2.getId());
            }
        });
        PageInfo pageInfo = new PageInfo(shopperList);
        pageInfo.setList(shopperInfoList);
        int count = (int)pageInfo.getTotal();
        return ServerResponse.createBySuccess("",count,pageInfo.getList());
    }

    public ServerResponse getOrderList(int userID, int pageNum, int pageSize, OrderSearch orderSearch){

        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID);  //获取所有的子用户到userSet中
        List<User> userList = Lists.newArrayList();
        for(User userItem : userSet){
            userList.add(userItem);
            System.out.println(userItem.getId());
        }
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime;
        String endTime;
        if(orderSearch.getStartTime() != null ){
            startTime = sdf.format(orderSearch.getStartTime());
        }else{
            startTime = null;
        }
        if(orderSearch.getEndTime() != null){
            endTime = sdf.format(orderSearch.getEndTime());
        }else{
            endTime = null;
        }
        /**
        if(startTime != null && endTime != null){
            if(orderSearch.getStartTime().after(orderSearch.getEndTime())  ||  orderSearch.getEndTime().after(new Date())){
                return ServerResponse.createByErrorMessage("日期选择错误！");
            }
        }
         **/
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        Set<Integer> idSet = new HashSet<Integer>();   //用来存放payOrder的id的集合
        List<Integer> idList = Lists.newArrayList();


        List<PayOrder> orderListTemp = payOrderMapper.selectOrderByUserList(userList,startTime,endTime);//通过用户集合获取到所有的订单
        for(PayOrder orderItem:orderListTemp){
            if(!idSet.contains(orderItem.getId())){
                idSet.add(orderItem.getId());
                idList.add(orderItem.getId());
            }
        }
        Collections.sort(idList);
        PageHelper.startPage(pageNum,pageSize);
        List<PayOrder> orderList = payOrderMapper.selectOrderByIdList(idList);
        for(PayOrder orderItem : orderList){
            PayInfo payInfo = payInfoMapper.selectByOrderNo(orderItem.getOrderNo());
            User userHost = userMapper.selectByPrimaryKey(orderItem.getUserId());
            User userAgent = userMapper.selectByPrimaryKey(userHost.getParentId());
            Shopper shopper = shopperMapper.selectByUserId(userHost.getId());
            String plateform;
            if(payInfo.getPayPlatform()==1){
                plateform = "支付宝";
            }else if(payInfo.getPayPlatform() == 2){
                plateform = "微信";
            }else if(payInfo.getPayPlatform() == 3){
                plateform = "云闪付";
            }else{
                plateform = "未知平台";
            }
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setId(orderItem.getId());
            orderInfo.setOrderNo(orderItem.getOrderNo());
            orderInfo.setUsername(userHost.getRealname());
            orderInfo.setAgentname(userAgent.getRealname());
            orderInfo.setShoppername(shopper.getShoppername());
            orderInfo.setPayment(orderItem.getPayment());
            orderInfo.setPayPlatform(plateform);
            orderInfo.setUpdateTime(orderItem.getUpdateTime());

            orderInfoList.add(orderInfo);
        }

        Collections.sort(orderInfoList, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                //升序
                return o1.getId().compareTo(o2.getId());
            }
        });
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderInfoList);

        int count=(int)pageInfo.getTotal();
        return ServerResponse.createBySuccess("",count,pageInfo.getList());
    }

    public ServerResponse getOrderShow(int userID, int pageNum, int pageSize, OrderSearch orderSearch){

        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID);  //获取所有的子用户到userSet中
        List<User> userList = Lists.newArrayList();
        List<OrderShow> orderShowList = Lists.newArrayList();
        List<Integer> idList = Lists.newArrayList();
        idList.add(userID);
        for(User userItem : userSet){
            if(userItem.getRole() != 4){
                idList.add(userItem.getId());
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        userList = userMapper.selectUserByIdList(idList);
        for(User userItem:userList){
            Map<String, BigDecimal> amountMap = getOrderAccount(userItem.getId(),orderSearch);
            OrderShow orderShow = new OrderShow();
            orderShow.setId(userItem.getId());
            orderShow.setRealname(userItem.getRealname());
            if(userItem.getRole() == 0){
                orderShow.setRole("服务商");
            }else if(userItem.getRole() == 1){
                orderShow.setRole("一级代理商");
            }else if(userItem.getRole() == 2){
                orderShow.setRole("二级代理商");
            }else if(userItem.getRole() == 3){
                orderShow.setRole("三级代理商");
            }
            User pUser = userMapper.selectByPrimaryKey(userItem.getParentId());
            orderShow.setAgentname(pUser.getRealname());
            orderShow.setZfbAmount(amountMap.get("zfbAccount"));
            orderShow.setWxAmount(amountMap.get("wxAccount"));
            orderShow.setYsfAmount(amountMap.get("ysfAccount"));
            orderShow.setTotalAmount(amountMap.get("totalAccount"));

            orderShowList.add(orderShow);
        }

        PageInfo pageInfo = new PageInfo(userList);
        pageInfo.setList(orderShowList);

        int count=(int)pageInfo.getTotal();
        return ServerResponse.createBySuccess("",count,pageInfo.getList());
    }

    public ServerResponse getCommisionShow(int userID, int pageNum, int pageSize, OrderSearch orderSearch){

        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID);  //获取所有的子用户到userSet中
        List<User> userList = Lists.newArrayList();
        List<CommisionShow> commisionShowList = Lists.newArrayList();
        List<Integer> idList = Lists.newArrayList();
        idList.add(userID);
        for(User userItem : userSet){
            if(userItem.getRole() != 4){
                idList.add(userItem.getId());
            }
        }
        PageHelper.startPage(pageNum,pageSize);
        userList = userMapper.selectUserByIdList(idList);
        for(User userItem:userList){
            Map<String, String> amountMap = getCommisionAccount(userItem.getId(),orderSearch);
            CommisionShow commisionShow = new CommisionShow();
            commisionShow.setId(userItem.getId());
            commisionShow.setRealname(userItem.getRealname());
            if(userItem.getRole() == 0){
                commisionShow.setRole("服务商");
            }else if(userItem.getRole() == 1){
                commisionShow.setRole("一级代理商");
            }else if(userItem.getRole() == 2){
                commisionShow.setRole("二级代理商");
            }else if(userItem.getRole() == 3){
                commisionShow.setRole("三级代理商");
            }
            User pUser = userMapper.selectByPrimaryKey(userItem.getParentId());
            commisionShow.setAgentname(pUser.getRealname());
            commisionShow.setZfbCommision(amountMap.get("zfbCommision"));
            commisionShow.setWxCommision(amountMap.get("wxCommision"));
            commisionShow.setYsfCommision(amountMap.get("ysfCommision"));
            commisionShow.setTotalCommision(amountMap.get("totalCommision"));

            commisionShowList.add(commisionShow);
        }

        PageInfo pageInfo = new PageInfo(userList);
        pageInfo.setList(commisionShowList);

        int count=(int)pageInfo.getTotal();
        return ServerResponse.createBySuccess("",count,pageInfo.getList());
    }


    //计算总流水
    public Map<String, BigDecimal> getOrderAccount(int userID, OrderSearch orderSearch){

        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID);
        List<User> userList = Lists.newArrayList();
        for(User userItem : userSet){
            userList.add(userItem);
        }
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //升序
                return o1.getId().compareTo(o2.getId());
            }
        });
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime;
        String endTime;
        if(orderSearch.getStartTime() != null ){
            startTime = sdf.format(orderSearch.getStartTime());
        }else{
            startTime = null;
        }
        if(orderSearch.getEndTime() != null){
            endTime = sdf.format(orderSearch.getEndTime());
        }else{
            endTime = null;
        }
        /**
         if(startTime != null && endTime != null){
         if(orderSearch.getStartTime().after(orderSearch.getEndTime())  ||  orderSearch.getEndTime().after(new Date())){
         return ServerResponse.createByErrorMessage("日期选择错误！");
         }
         }
         **/
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        Set<OrderInfo> orderInfoSet = Sets.newHashSet();
        Set<Integer> idSet = new HashSet<Integer>();
        for(User userItem : userList){
            List<PayOrder> orderList = payOrderMapper.selectOrderByCondition(userItem.getId(),startTime,endTime);
            if(orderList == null){
                continue;
            }
            for(PayOrder orderItem : orderList){
                PayInfo payInfo = payInfoMapper.selectByOrderNo(orderItem.getOrderNo());
                User userHost = userMapper.selectByPrimaryKey(orderItem.getUserId());
                User userAgent = userMapper.selectByPrimaryKey(userHost.getParentId());
                Shopper shopper = shopperMapper.selectByUserId(userHost.getId());
                String plateform;
                if(payInfo.getPayPlatform()==1){
                    plateform = "支付宝";
                }else if(payInfo.getPayPlatform() == 2){
                    plateform = "微信";
                }else if(payInfo.getPayPlatform() == 3){
                    plateform = "云闪付";
                }else{
                    plateform = "未知平台";
                }
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setId(orderItem.getId());
                orderInfo.setOrderNo(orderItem.getOrderNo());
                orderInfo.setUsername(userHost.getRealname());
                orderInfo.setAgentname(userAgent.getRealname());
                orderInfo.setShoppername(shopper.getShoppername());
                orderInfo.setPayment(orderItem.getPayment());
                orderInfo.setPayPlatform(plateform);
                orderInfo.setUpdateTime(orderItem.getUpdateTime());
                if(!idSet.contains(orderInfo.getId())){
                    idSet.add(orderInfo.getId());
                    orderInfoSet.add(orderInfo);
                }
            }

        }
        for(OrderInfo orderInfoIten:orderInfoSet){
            orderInfoList.add(orderInfoIten);
        }

        Collections.sort(orderInfoList, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                //升序
                return o1.getId().compareTo(o2.getId());
            }
        });

        BigDecimal totalAccount = new BigDecimal(0);
        BigDecimal zfbAccount = new BigDecimal(0);
        BigDecimal wxAccount = new BigDecimal(0);
        BigDecimal ysfAccount = new BigDecimal(0);
        for(OrderInfo orderInfoItem:orderInfoList){
            if(orderInfoItem.getPayPlatform().equals("支付宝")){
                zfbAccount =  zfbAccount.add(orderInfoItem.getPayment());
            }
            if(orderInfoItem.getPayPlatform().equals("微信")){
                wxAccount =  wxAccount.add(orderInfoItem.getPayment());
            }
            if(orderInfoItem.getPayPlatform().equals("云闪付")){
                ysfAccount =  ysfAccount.add(orderInfoItem.getPayment());
            }
            totalAccount = totalAccount.add(orderInfoItem.getPayment());
        }

        HashMap<String,BigDecimal> accountMap = new HashMap<>();
        accountMap.put("totalAccount",totalAccount);
        accountMap.put("zfbAccount",zfbAccount);
        accountMap.put("wxAccount",wxAccount);
        accountMap.put("ysfAccount",ysfAccount);
        return accountMap;
    }

    public Map<String, String> getCommisionAccount(int userID, OrderSearch orderSearch){

        Set<User> userSet = Sets.newHashSet();
        findChildUser(userSet,userID);
        List<User> userList = Lists.newArrayList();
        for(User userItem : userSet){
            userList.add(userItem);
        }
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                //升序
                return o1.getId().compareTo(o2.getId());
            }
        });
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startTime;
        String endTime;
        if(orderSearch.getStartTime() != null ){
            startTime = sdf.format(orderSearch.getStartTime());
        }else{
            startTime = null;
        }
        if(orderSearch.getEndTime() != null){
            endTime = sdf.format(orderSearch.getEndTime());
        }else{
            endTime = null;
        }
        /**
         if(startTime != null && endTime != null){
         if(orderSearch.getStartTime().after(orderSearch.getEndTime())  ||  orderSearch.getEndTime().after(new Date())){
         return ServerResponse.createByErrorMessage("日期选择错误！");
         }
         }
         **/
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        Set<OrderInfo> orderInfoSet = Sets.newHashSet();
        Set<Integer> idSet = new HashSet<Integer>();
        for(User userItem : userList){
            List<PayOrder> orderList = payOrderMapper.selectOrderByCondition(userItem.getId(),startTime,endTime);
            if(orderList == null){
                continue;
            }
            for(PayOrder orderItem : orderList){
                PayInfo payInfo = payInfoMapper.selectByOrderNo(orderItem.getOrderNo());
                User userHost = userMapper.selectByPrimaryKey(orderItem.getUserId());
                User userAgent = userMapper.selectByPrimaryKey(userHost.getParentId());
                Shopper shopper = shopperMapper.selectByUserId(userHost.getId());
                String plateform;
                if(payInfo.getPayPlatform()==1){
                    plateform = "支付宝";
                }else if(payInfo.getPayPlatform() == 2){
                    plateform = "微信";
                }else if(payInfo.getPayPlatform() == 3){
                    plateform = "云闪付";
                }else{
                    plateform = "未知平台";
                }
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setId(orderItem.getId());
                orderInfo.setOrderNo(orderItem.getOrderNo());
                orderInfo.setUsername(userHost.getRealname());
                orderInfo.setAgentname(userAgent.getRealname());
                orderInfo.setShoppername(shopper.getShoppername());
                orderInfo.setPayment(orderItem.getPayment());
                orderInfo.setPayPlatform(plateform);
                orderInfo.setUpdateTime(orderItem.getUpdateTime());
                if(!idSet.contains(orderInfo.getId())){
                    idSet.add(orderInfo.getId());
                    orderInfoSet.add(orderInfo);
                }
            }

        }
        for(OrderInfo orderInfoIten:orderInfoSet){
            orderInfoList.add(orderInfoIten);
        }

        Collections.sort(orderInfoList, new Comparator<OrderInfo>() {
            @Override
            public int compare(OrderInfo o1, OrderInfo o2) {
                //升序
                return o1.getId().compareTo(o2.getId());
            }
        });

        BigDecimal totalAccount = new BigDecimal(0);
        BigDecimal zfbAccount = new BigDecimal(0);
        BigDecimal wxAccount = new BigDecimal(0);
        BigDecimal ysfAccount = new BigDecimal(0);
        BigDecimal totalCommision = new BigDecimal(0);
        BigDecimal zfbCommision = new BigDecimal(0);
        BigDecimal wxCommision = new BigDecimal(0);
        BigDecimal ysfCommision = new BigDecimal(0);
        for(OrderInfo orderInfoItem:orderInfoList){
            if(orderInfoItem.getPayPlatform().equals("支付宝")){
                zfbAccount =  zfbAccount.add(orderInfoItem.getPayment());
            }
            if(orderInfoItem.getPayPlatform().equals("微信")){
                wxAccount =  wxAccount.add(orderInfoItem.getPayment());
            }
            if(orderInfoItem.getPayPlatform().equals("云闪付")){
                ysfAccount =  ysfAccount.add(orderInfoItem.getPayment());
            }
            totalAccount = totalAccount.add(orderInfoItem.getPayment());
        }
        User user = userMapper.selectByPrimaryKey(userID);
        BigDecimal rate = new BigDecimal(user.getRate());
        BigDecimal r = new BigDecimal(100);
        rate = rate.divide(r);
        totalCommision = totalAccount.multiply(rate);
        zfbCommision = zfbAccount.multiply(rate);
        wxCommision = wxAccount.multiply(rate);
        ysfCommision = ysfAccount.multiply(rate);
        HashMap<String,String> accountMap = new HashMap<>();
        accountMap.put("totalAccount",totalAccount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("zfbAccount",zfbAccount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("wxAccount",wxAccount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("ysfAccount",ysfAccount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("totalCommision",totalCommision.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("zfbCommision",zfbCommision.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("wxCommision",wxCommision.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        accountMap.put("ysfCommision",ysfCommision.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        return accountMap;
    }

    //递归算法，算出子节点
    private Set<User> findChildUser(Set<User> userSet,Integer userID,UserSearch userSearch){
        User user = userMapper.selectByPrimaryKey(userID);
        if(user != null){
            userSet.add(user);
        }
        //查找子节点，递归算法一定要有一个退出的条件
        List<User> userList = userMapper.selectUserChildrenByParentId(userID,userSearch.getRealname(),userSearch.getRole());
        for(User userItem: userList){
            findChildUser(userSet,userItem.getId(),userSearch);
        }
        return userSet;
    }

    private Set<User> findChildUser(Set<User> userSet,Integer userID){
        User user = userMapper.selectByPrimaryKey(userID);
        if(user != null){
            userSet.add(user);
        }
        //查找子节点，递归算法一定要有一个退出的条件
        List<User> userList = userMapper.selectUserChildrenByParentId(userID,null,null);
        for(User userItem: userList){
            findChildUser(userSet,userItem.getId());
        }
        return userSet;
    }

    private Set<User> findShopperChildUser(Set<User> userSet,Integer userID){
        User user = userMapper.selectByPrimaryKey(userID);
        if(user != null && user.getRole() == 4){
            userSet.add(user);
        }
        //查找子节点，递归算法一定要有一个退出的条件
        List<User> userList = userMapper.selectUserChildrenByParentId(userID,null,4);
        for(User userItem: userList){
            findShopperChildUser(userSet,userItem.getId());
        }
        return userSet;
    }

    public ServerResponse<User> updateUser(EditUser editUser){
        User oldUser = userMapper.selectByPrimaryKey(editUser.getId());

        User updateUser = new User();
        updateUser.setId(editUser.getId());
        updateUser.setParentId(editUser.getParentId());
        updateUser.setUsername(oldUser.getUsername());
        updateUser.setRealname(oldUser.getRealname());
        updateUser.setPhone(editUser.getPhone());
        updateUser.setIdentityId(oldUser.getIdentityId());
        updateUser.setBankId(editUser.getBankId());
        updateUser.setQuestion(oldUser.getQuestion());
        updateUser.setAnswer(oldUser.getAnswer());
        updateUser.setRole(oldUser.getRole());
        updateUser.setRate(editUser.getRate());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新成功",updateUser);
        }
        return ServerResponse.createByErrorMessage("更新失败！");
    }

    public ServerResponse<Shopper> updateShopper(EditShopper editShopper){
        Shopper oldShopper = shopperMapper.selectByPrimaryKey(editShopper.getId());

        Shopper updateShopper = new Shopper();
        updateShopper.setId(editShopper.getId());
        updateShopper.setUserId(oldShopper.getUserId());
        updateShopper.setAgentId(oldShopper.getAgentId());
        updateShopper.setPhone(editShopper.getPhone());
        updateShopper.setAddress(oldShopper.getAddress());
        updateShopper.setBussinessLicense(oldShopper.getBussinessLicense());
        updateShopper.setZfbId(editShopper.getZfbId());
        updateShopper.setWxId(editShopper.getWxId());
        updateShopper.setYsfId(editShopper.getYsfId());
        updateShopper.setAuthcode(editShopper.getAuthcode());
        updateShopper.setShoppername(editShopper.getShoppername());

        int updateCount = shopperMapper.updateByPrimaryKeySelective(updateShopper);
        if(updateCount > 0){
            return ServerResponse.createBySuccess("更新成功",updateShopper);
        }
        return ServerResponse.createByErrorMessage("更新失败！");
    }

    public ServerResponse<String> deleteUserById(int id){
        int resultCount = userMapper.deleteByPrimaryKey(id);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("删除成功！");
        }
        return ServerResponse.createByErrorMessage("删除失败！");
    }

    public ServerResponse<String> deleteShopperById(int id){

        Shopper shopper = shopperMapper.selectByPrimaryKey(id);
        Device device = deviceMapper.selectByUserId(shopper.getUserId());
        if(device != null){
            deviceMapper.deleteByPrimaryKey(device.getId());
        }
        int resultCount = shopperMapper.deleteByPrimaryKey(id);
        if(resultCount > 0){
            return ServerResponse.createBySuccessMessage("删除成功！");
        }
        return ServerResponse.createByErrorMessage("删除失败！");
    }

    public ServerResponse<String> addShopper(ShopperDevice shopperDevice){ //更新shopper的信息
        Shopper insertShopper = new Shopper();
        Device insertDevice = new Device();

        insertShopper.setUserId(shopperDevice.getUserId());
        insertShopper.setAgentId(shopperDevice.getAgentId());
        insertShopper.setShoppername(shopperDevice.getShoppername());
        insertShopper.setPhone(shopperDevice.getPhone());
        insertShopper.setZfbId(shopperDevice.getZfbId());
        insertShopper.setWxId(shopperDevice.getWxId());
        insertShopper.setYsfId(shopperDevice.getYsfId());
        insertShopper.setAddress(shopperDevice.getAddress());
        insertShopper.setAuthcode(shopperDevice.getAuthcode());
        insertShopper.setBussinessLicense(shopperDevice.getBussinessLicense());


        insertDevice.setUserId(shopperDevice.getUserId());
        insertDevice.setAgentId(shopperDevice.getAgentId());
        insertDevice.setDeviceId(shopperDevice.getDeviceId());
        insertDevice.setDeviceKey(shopperDevice.getDeviceKey());
        insertDevice.setDeviceType(shopperDevice.getDeviceType());
        insertDevice.setActiveCode(shopperDevice.getActiveCode());

        int resultCountShopper = shopperMapper.insert(insertShopper);
        int resultCountDevice = deviceMapper.insert(insertDevice);

        if(resultCountShopper > 0 && resultCountDevice > 0){
            return ServerResponse.createBySuccessMessage("添加成功~");
        }else if(resultCountShopper > 0 && resultCountDevice == 0){
            return ServerResponse.createBySuccessMessage("设备添加失败~");
        }else if(resultCountShopper == 0 && resultCountDevice > 0){
            return ServerResponse.createBySuccessMessage("商家添加失败~");
        }else{
            return ServerResponse.createByErrorMessage("添加失败");
        }

    }
}
