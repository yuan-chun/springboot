package com.yuanchun.dao;

import com.yuanchun.entry.RmAuditInfo;

public interface RmAuditInfoMapper {
    int deleteByPrimaryKey(String serialId);

    int insert(RmAuditInfo record);

    int insertSelective(RmAuditInfo record);

    RmAuditInfo selectByPrimaryKey(String serialId);

    int updateByPrimaryKeySelective(RmAuditInfo record);

    int updateByPrimaryKey(RmAuditInfo record);
}