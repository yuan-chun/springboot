package com.yuanchun.service.impl;

import com.yuanchun.common.BaseClass;
import com.yuanchun.dao.RmMarketCaseInfoMapper;
import com.yuanchun.entry.RmMarketCaseInfo;
import com.yuanchun.service.inter.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

//控制事务，作用域为整个类,也可以指定控制某个方法
@Transactional
@Service("testService")
public class TestService extends BaseClass implements ITestService {



    @Resource
    private RmMarketCaseInfoMapper rmMarketCaseInfoMapper;

    @Override
    public List<RmMarketCaseInfo> queryList(RmMarketCaseInfo rmMarketCaseInfo) {
        List<RmMarketCaseInfo> rmMarketCaseInfos = rmMarketCaseInfoMapper.selectList(rmMarketCaseInfo);
        List<RmMarketCaseInfo> outList = rmMarketCaseInfos;
        logger.debug("============queryList.outList=========="+outList);
        return outList;
    }
}
