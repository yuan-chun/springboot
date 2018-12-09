package com.yuanchun.dao;

import com.yuanchun.entry.RmMenuInfo;
import com.yuanchun.vo.RmMenuInfoVo;

import java.util.List;

public interface RmMenuInfoMapper {
    int insert(RmMenuInfo record);

    int insertSelective(RmMenuInfo record);

    List<RmMenuInfoVo> queryMenuList();
}