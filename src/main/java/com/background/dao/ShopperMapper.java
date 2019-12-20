package com.background.dao;

import com.background.pojo.Shopper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopperMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shopper record);

    int insertSelective(Shopper record);

    Shopper selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shopper record);

    int updateByPrimaryKey(Shopper record);

    List<Shopper> selectShopperByCondition(@Param("userID") Integer userID, @Param("hostID") Integer hostID, @Param("agentID") Integer agentID);

    Shopper selectByUserId(Integer userID);

    List<Shopper> selectShopperByIdList(@Param("idList") List idList);
}