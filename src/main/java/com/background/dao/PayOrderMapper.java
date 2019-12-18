package com.background.dao;

import com.background.pojo.PayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PayOrder record);

    int insertSelective(PayOrder record);

    PayOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayOrder record);

    int updateByPrimaryKey(PayOrder record);

    List<PayOrder> selectOrderByCondition(@Param("userID") Integer userID, @Param("startTime") String startTime, @Param("endTime") String endTime);

    List<PayOrder> selectOrderByUserList(@Param("userList") List userList, @Param("startTime") String startTime, @Param("endTime") String endTime);
}