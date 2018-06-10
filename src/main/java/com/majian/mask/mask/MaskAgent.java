package com.majian.mask.mask;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServerHttpRequest;

public class MaskAgent {
    private String appId;
    private MaskClient client;

    public List<MaskItem> getMaskItems(ServerHttpRequest serverHttpRequest,
        MethodParameter methodParameter) {

        List<MaskItem> allMaskItems = getAllMaskItems(methodParameter);
        String account = getAccount(serverHttpRequest);
        return filterWhiteList(allMaskItems, account);
    }

    private List<MaskItem> filterWhiteList(List<MaskItem> allMaskItems, String account) {
        return allMaskItems.stream()
            .filter(item -> item.isAccountExcluded(account))
            .collect(Collectors.toList());
    }

    private List<MaskItem> getAllMaskItems(MethodParameter methodParameter) {
        String code = getCode(methodParameter);
        return client.getMaskItems(appId, code);
    }

    private String getCode(MethodParameter methodParameter) {
        String[] paths = getPaths(methodParameter);
        String[] httpMethods = getMethods(methodParameter);
        return ResourceCodeGenerator.generateCode(paths, httpMethods);
    }



    private String[] getMethods(MethodParameter methodParameter) {
        // TODO: 2018/6/8
        return null;
    }

    private String[] getPaths(MethodParameter methodParameter) {
        // TODO: 2018/6/8
        return null;
   }

    private String getAccount(ServerHttpRequest serverHttpRequest) {
        // TODO: 2018/6/8
        return null;
    }


    public void register(MaskResource maskResource) {
        maskResource.setAppId(appId);
        client.register(maskResource);
    }
}
