package com.yuanchun.vo;

import com.yuanchun.entry.RmMenuInfo;

import java.util.List;

public class RmMenuInfoVo extends RmMenuInfo {
    private List<RmMenuInfoVo> childMenus;

    public List<RmMenuInfoVo> getChildMenus() {
        return childMenus;
    }

    public void setChildMenus(List<RmMenuInfoVo> childMenus) {
        this.childMenus = childMenus;
    }
}
