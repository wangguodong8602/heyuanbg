package com.background.dao;

import com.background.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    int checkPhone(String phone);

    User selectLogin(@Param("username") String username, @Param("password") String password);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username,@Param("passwordNew") String passwordNew);

    int checkPassword(@Param("password") String password,@Param("userId") Integer userId);

    int checkPhoneByUserId(@Param("phone") String phone,@Param("userId") Integer userId);

    List<User> selectList(Integer userID);

    List<User> selectUserChildrenByParentId(@Param("userID") Integer userID,@Param("realname") String realname,@Param("role") Integer role);

    User selectByRealname(String realname);

    List<User> selectUserByIdList(@Param("idList") List idList);
}