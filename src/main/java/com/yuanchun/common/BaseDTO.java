package com.yuanchun.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class BaseDTO  implements Serializable {

    private static final String SUCCESS = new String("0");


    /**
     * 默认成功
     */

    @JSONField(name = "RETURN_CODE")
    private String returnCode = SUCCESS;

    @JSONField(name = "RETURN_MSG")
    private boolean returnMsg = true;

    /**
     * 返回值
     */
    @JSONField(name = "DATA")
    private Object data;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(boolean returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseDTO [returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", data=" + data + "]";
    }


}
