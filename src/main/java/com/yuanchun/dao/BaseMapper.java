package com.yuanchun.dao;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanchun.util.CommonUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BaseMapper extends SqlSessionTemplate {

    public BaseMapper(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
        // TODO Auto-generated constructor stub
    }

    public String pageNumStr = "pageNum";
    public String pageSizeStr = "pageSize";

    /**
     * 查询分页信息
     * @date: 2017-8-2
     * @author: zhangjj_crmpd
     * @title: selectPage
     * @param statement sql映射名称.id
     * @param pageNum 当前页
     * @param pageSize 每页条数
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public PageInfo selectPage(String statement,int pageNum,int pageSize){
        return this.selectPage(statement, null, pageNum, pageSize);
    }

    /**
     * 查询分页信息
     * @date: 2017-8-2
     * @author: zhangjj_crmpd
     * @title: selectPage
     * @param statement sql映射名称.id
     * @param param 查询条件,param里边必须包含pageNum和pageSize，否则会报错
     * @return
     * @exception:
     * @version: 1.0
     * @description:
     * update_version: update_date: update_author: update_note:
     */
    public PageInfo selectPage(String statement,Map param){
        int pageNum = CommonUtils.getIntFromMap(param, pageNumStr);
        int pageSize = CommonUtils.getIntFromMap(param, pageSizeStr);
        if (pageNum == 0 || pageSize == 0) {
            throw new RuntimeException();
        }
        return this.selectPage(statement, param,pageNum , pageSize);
    }

    /***
     * 查询分页信息基础方法
     * @param statement SQL映射路径.ID
     * @param param 参数
     * @param pageNum 查询第多个页
     * @param pageSize 每页多少条数据
     * @return
     */
    public <T> PageInfo selectPage(final String statement,final T param,int pageNum,int pageSize){
        return selectPage(statement, param, true, pageNum, pageSize);
    }

    /***
     * 查询分页数据，不查询数据总条数
     * @param <T>
     * @param statement SQL映射路径.ID
     * @param param 参数
     * @param pageNum 查询第多个页
     * @param pageSize 每页多少条数据
     * @return
     */
    public <T> PageInfo selectPageNoCount(final String statement,T param,int pageNum,int pageSize){
        return selectPage(statement, param, false, pageNum, pageSize);
    }


    /***
     * 查询分页数据,不查询总条数
     * @param statement sql映射名称.id
     * @param param 查询条件,param里边必须包含pageNum和pageSize，否则会报错
     * @return
     */
    public PageInfo selectPageNoCount(String statement,Map param){
        int pageNum = CommonUtils.getIntFromMap(param, pageNumStr);
        int pageSize = CommonUtils.getIntFromMap(param, pageSizeStr);
        if (pageNum == 0 || pageSize == 0) {
            throw new RuntimeException();
        }
        return selectPageNoCount(statement, param,pageNum , pageSize);
    }
    /***
     * 查询分页信息基础方法
     * @param <T>
     * @param statement SQL映射路径.ID
     * @param param 参数
     * @param selectCount 是否进行count查询
     * @param pageNum 查询第多个页
     * @param pageSize 每页多少条数据
     * @return
     */
    public <T> PageInfo selectPage(final String statement, final T param, boolean selectCount, int pageNum, int pageSize){
        final BaseMapper baseMapper = this;
        PageInfo info = PageHelper.startPage(pageNum, pageSize,selectCount).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect(){
                baseMapper.selectList(statement,param);
            }
        });
        return info;
    }

}

