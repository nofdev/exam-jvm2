package com.foo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelInventoryDTO implements Serializable{

    private static final long serialVersionUID = -6826888764584805082L;
    /**
     * 渠道编码, 目前包含: MIAO, TMALL, INTIME 3个渠道
     */
    private String channelCode;
    /**
     * 库存数量, 保留小数点后2位
     */
    private BigDecimal inventory;

}
