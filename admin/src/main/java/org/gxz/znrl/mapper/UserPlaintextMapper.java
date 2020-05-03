package org.gxz.znrl.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.gxz.znrl.entity.UserPlaintext;
import org.gxz.znrl.entity.UserPlaintextCriteria;

public interface UserPlaintextMapper {
    int countByExample(UserPlaintextCriteria example);

    int deleteByExample(UserPlaintextCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(UserPlaintext record);

    int insertSelective(UserPlaintext record);

    List<UserPlaintext> selectByExample(UserPlaintextCriteria example);

    UserPlaintext selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserPlaintext record, @Param("example") UserPlaintextCriteria example);

    int updateByExample(@Param("record") UserPlaintext record, @Param("example") UserPlaintextCriteria example);

    int updateByPrimaryKeySelective(UserPlaintext record);

    int updateByPrimaryKey(UserPlaintext record);
}