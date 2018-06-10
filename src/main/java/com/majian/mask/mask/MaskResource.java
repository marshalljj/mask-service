package com.majian.mask.mask;

public class MaskResource {
    private String appId;
    private String[] paths;
    private String[] methods;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCode() {
        return ResourceCodeGenerator.generateCode(paths, methods);
    }


    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

    public String[] getMethods() {
        return methods;
    }

    public void setMethods(String[] methods) {
        this.methods = methods;
    }
}
