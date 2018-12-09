package com.yuanchun.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuanchun.dao.RmMenuInfoMapper;
import com.yuanchun.entry.RmMarketCaseInfo;
import com.yuanchun.service.inter.ITestService;
import com.yuanchun.vo.RmMenuInfoVo;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class SpringBootTests {
//    private final Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//    private static RmMarketCaseInfo rmMarketCaseInfo;
//    @Resource
//    private ITestService testService;
//    @Resource
//    private RmMenuInfoMapper rmMenuInfoMapper;
//
//
//
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//
//    }
//    @AfterClass
//    public static void setUpAfterClass() throws Exception {
//
//    }
//
//    @Before
//    public void before() throws Exception {
//         rmMarketCaseInfo = new RmMarketCaseInfo();
//        rmMarketCaseInfo.setMarketCaseId("11");
//    }
//
//    @After
//    public void after() throws Exception {
//
//    }
//
//
//    @Test
//    public void testMenuTree() {
//
//        List<RmMenuInfoVo> menulist = rmMenuInfoMapper.queryMenuList();
//        Map<String,Object> jsonMap = new HashMap<>();
//        jsonMap.put("menu", menulist);
//        System.out.println("===============================rootmenu==============================="+gson.toJson(jsonMap));
//
//        /**
//         * 求一级目录
//         */
//        List<RmMenuInfoVo> rootlist = new ArrayList<>();
//        for(RmMenuInfoVo menu : menulist){
//            if(menu.getParentMenuId().equals("1000")){
//                rootlist.add(menu);
//            }
//        }
//
//        /**
//         * 找一级目录子目录
//         */
//        for(RmMenuInfoVo menu : rootlist){
//            menu.setChildMenus(getChild(menu.getMenuId(),menulist));
//        }
//
//        System.out.println("===================rootlist===================="+gson.toJson(rootlist));
//
//    }
//
//    private List<RmMenuInfoVo> getChild(String menuId, List<RmMenuInfoVo> menulist) {
//        List<RmMenuInfoVo> childList = new ArrayList<>();
//        /**
//         * 根据传入目录ID找到此ID的子目录
//         */
//        for(RmMenuInfoVo menu : menulist){
//            if (menu.getParentMenuId().equals(menuId)){
//                childList.add(menu);
//            }
//        }
//
//        /**
//         * 给子目录找子目录
//         */
//        for(RmMenuInfoVo menu : childList){
//            menu.setChildMenus(getChild(menu.getMenuId(),menulist));
//        }
//        if(childList.size() == 0){
//            return new ArrayList();
//        }
//
//        return childList;
//    }
//
}