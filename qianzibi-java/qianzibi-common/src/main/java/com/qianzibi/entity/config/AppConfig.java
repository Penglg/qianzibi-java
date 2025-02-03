package com.qianzibi.entity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *  配置文件
 */
@Configuration
public class AppConfig {
    @Value("${project.folder:}")
    private String projectFolder;

    @Value("${super.admin.phones:}")
    private String superAdminPhones;


    @Value("${jwt.common.secret:}")
    private String jwtCommonSecret;

    @Value("${app.name:EasyJob}")
    private String appName;

    @Value("${app.domain:}")
    private String appDomain;


    public String getJwtCommonSecret() {
        return jwtCommonSecret;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public String getSuperAdminPhones() {
        return superAdminPhones;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppDomain() {
        return appDomain;
    }
}
