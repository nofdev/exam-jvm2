package com.foo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取依赖服务 bean 的工厂类, 用 ServiceBeanFactory.getInstance().getServiceBean(SkuService.class) 获取需要的依赖服务的实例
 */
public enum ServiceBeanFactory {

    /**
     *
     */
    INSTANCE;

    public static ServiceBeanFactory getInstance() {
        return INSTANCE;
    }

    private final Map<String, SkuEntity> skuEntityMap = new HashMap<>();

    private final SkuService skuService = new SkuService() {
        @Override
        public List<SkuInfoDTO> findByIds(List<String> ids) {
            if (ids.size() > 20) {
                throw new RuntimeException("不能超过20个 skuId");
            }
            List<SkuInfoDTO> skuInfoDTOS = ids.stream().map(it -> {
                SkuEntity skuEntity = ServiceBeanFactory.getInstance().skuEntityMap.get(it);
                if (skuEntity != null) {
                    return new SkuInfoDTO(skuEntity.getId(), skuEntity.getName(), skuEntity.getArtNo(),
                        skuEntity.getSpuId(), skuEntity.getSkuType());
                } else {
                    return null;
                }
            }).collect(Collectors.toList());
            skuInfoDTOS.removeIf(Objects::isNull);
            return skuInfoDTOS;
        }
    };

    private final PriceService priceService = new PriceService() {
        @Override
        public BigDecimal getBySkuId(String skuId) {
            SkuEntity skuEntity = ServiceBeanFactory.getInstance().skuEntityMap.get(skuId);
            if (skuEntity != null) {
                return skuEntity.getPrice();
            } else {
                return null;
            }
        }
    };

    private final InventoryService inventoryService = new InventoryService() {
        @Override
        public List<ChannelInventoryDTO> getBySkuId(String skuId) {
            SkuEntity skuEntity = ServiceBeanFactory.getInstance().skuEntityMap.get(skuId);
            if (skuEntity != null) {
                return skuEntity.getInventoryDTOS();
            } else {
                return null;
            }
        }
    };

    public <T> T getServiceBean(Class<T> interfaceClass) {
        if (interfaceClass == SkuService.class) {
            return (T)this.skuService;
        } else if (interfaceClass == PriceService.class) {
            return (T)this.priceService;
        } else if (interfaceClass == InventoryService.class) {
            return (T)this.inventoryService;
        } else {
            throw new RuntimeException("不支持的接口类型");
        }

    }

    ServiceBeanFactory() {
        // 初始化了100个 sku entity
        for (int i = 1; i <= 100; i++) {
            // mock 有几个 skuid 找不到
            if (i == 4 || i == 100 || i == 65) {
                continue;
            }
            String id = String.valueOf(i);
            SkuEntity skuEntity = new SkuEntity();
            skuEntity.setId(id);
            skuEntity.setName("测试商品" + id);
            if (i <= 40) {
                skuEntity.setSkuType("ORIGIN");
                skuEntity.setArtNo("xyz" + Math.floorMod(i, 3));
            } else {
                skuEntity.setSkuType("DIGITAL");
                skuEntity.setSpuId("1000" + Math.floorMod(i, 3));
            }
            BigDecimal price = new BigDecimal(new Random().nextInt(100) - 1, new MathContext(2, RoundingMode.HALF_UP));
            skuEntity.setPrice(price);
            List<ChannelInventoryDTO> inventoryDTOS = new ArrayList<>();
            BigDecimal miao = new BigDecimal(new Random().nextInt(50) - 1, new MathContext(2, RoundingMode.HALF_UP));
            inventoryDTOS.add(new ChannelInventoryDTO("MIAO", miao));
            BigDecimal tmall = new BigDecimal(new Random().nextInt(100) - 1, new MathContext(2, RoundingMode.HALF_UP));
            inventoryDTOS.add(new ChannelInventoryDTO("TMALL", tmall));

            skuEntity.setInventoryDTOS(inventoryDTOS);

            skuEntityMap.put(id, skuEntity);
        }
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class SkuEntity {
    private String id;
    private String name;
    private String artNo;
    private String spuId;
    private String skuType;
    private BigDecimal price;
    /**
     * 本来在领域层不应该依赖接口的 dto, 这里为了写着方便, 就不要太纠结了
     */
    private List<ChannelInventoryDTO> inventoryDTOS;
}

