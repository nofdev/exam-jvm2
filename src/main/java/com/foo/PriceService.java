package com.foo;

import java.math.BigDecimal;

/**
 * 价格服务
 */
public interface PriceService {
    /**
     * 根据 skuid 获取价格, 这是个简化版的接口, 不考虑各种吊牌价渠道价之类的, 就是一个销售价
     * @param skuId
     * @return 商品售价, 保留小数点后两位
     */
    BigDecimal getBySkuId(String skuId);
}
