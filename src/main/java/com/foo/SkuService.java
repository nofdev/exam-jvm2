package com.foo;

import java.util.List;

/**
 * SKU 服务
 */
public interface SkuService {
    /**
     * 根据 sku id 获取 sku 基本信息, 对于没有找到的 sku 会自动忽略
     * @param ids 不能超过20个
     * @return
     */
    List<SkuInfoDTO> findByIds(List<String> ids);
}
