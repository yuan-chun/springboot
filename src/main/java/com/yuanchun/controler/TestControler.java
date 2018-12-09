package com.yuanchun.controler;

import com.yuanchun.common.BaseClass;
import com.yuanchun.entry.RmMarketCaseInfo;
import com.yuanchun.service.impl.TestService;
import com.yuanchun.service.inter.ITestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
//@RestController = @Controller + @ResponseBody
@Controller
@RequestMapping(value ="/testControler",produces = "application/json;charset=UTF-8")
public class TestControler extends BaseClass {

    @Resource
    private ITestService testService;

    @ResponseBody
    @RequestMapping("/query.do")
    public List<RmMarketCaseInfo> queryList(@RequestBody RmMarketCaseInfo rmMarketCaseInfo){
        logger.debug("==============TestControler.queryList==============");
        return testService.queryList(rmMarketCaseInfo);
    }

}
