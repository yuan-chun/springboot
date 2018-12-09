package com.yuanchun.service.inter;

import com.yuanchun.entry.RmMarketCaseInfo;

import java.util.List;

public interface ITestService {
    List<RmMarketCaseInfo> queryList(RmMarketCaseInfo rmMarketCaseInfo);
}
