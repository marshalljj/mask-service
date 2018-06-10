package com.majian.mask.mask;

import java.util.List;

/**
 * 用于与获取配置的数据
 *
 */
public interface MaskClient {

    /**
     * 获取url对应的脱敏配置
     * @param appId
     * @param path
     * @param method
     * @return
     */
    List<MaskItem> getMaskItems(String appId, String code);

    /**
     * 注册需脱敏的url
     * @param maskResource
     */
    void register(MaskResource maskResource);
}
