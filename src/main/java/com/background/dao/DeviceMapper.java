package com.background.dao;

import com.background.pojo.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    Device selectByUserId(int id);

    Device selectBySN(String deviceSN);

    List<Device> selectDeviceByCondition(@Param("userID") Integer userID, @Param("hostID") Integer hostID, @Param("agentID") Integer agentID);

    List<Device> selectDeviceByIdList(@Param("idList") List idList);
}