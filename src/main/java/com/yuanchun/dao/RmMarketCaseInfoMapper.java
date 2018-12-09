package com.yuanchun.dao;

import com.yuanchun.entry.RmMarketCaseInfo;

import java.util.List;

/**
 * sql方式一：在xml中配置
 */
public interface RmMarketCaseInfoMapper {

    RmMarketCaseInfo selectByPrimaryKey(String marketCaseId);

    List<RmMarketCaseInfo> selectList(RmMarketCaseInfo record);

}