package com.foo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChannelInventoryDTO implements Serializable{

    /**
     * 渠道编码, 目前包含: MIAO, TMALL, INTIME 3个渠道
     */
    private String channelCode;
    /**
     * 库存数量, 保留小数点后2位
     */
    private BigDecimal inventory;

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public BigDecimal getInventory() {
        return inventory;
    }

    public void setInventory(BigDecimal inventory) {
        this.inventory = inventory;
    }
}
