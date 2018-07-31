package com.foo;

import java.util.List;

/**
 * 库存服务
 */
public interface InventoryService {
    /**
     * 根据 skuid 获取各渠道库存
     * @param skuId
     * @return
     */
    List<ChannelInventoryDTO> getBySkuId(String skuId);
}
