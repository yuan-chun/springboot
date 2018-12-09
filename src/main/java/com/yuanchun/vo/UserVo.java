package com.yuanchun.vo;

import com.yuanchun.entry.User;

public class UserVo extends User {

    private int index;

    private String operTimeStartString;

    private String operTimeEndString;

    private String orderByClause;

    private String dealType;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOperTimeStartString() {
        return operTimeStartString;
    }

    public void setOperTimeStartString(String operTimeStartString) {
        this.operTimeStartString = operTimeStartString;
    }

    public String getOperTimeEndString() {
        return operTimeEndString;
    }

    public void setOperTimeEndString(String operTimeEndString) {
        this.operTimeEndString = operTimeEndString;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }


}
