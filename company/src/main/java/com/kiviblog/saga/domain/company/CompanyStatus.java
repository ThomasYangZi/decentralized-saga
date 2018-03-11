package com.kiviblog.saga.domain.company;

/**
 * @author yangzifeng
 */
public enum  CompanyStatus {
    // 公司初始状态
    COMPANY_INIT,
    // 公司已创建
    COMPANY_CREATED,
    WAITING_USER,
    // 检查其他服务消费情况
    FALLBACK_CHECKED,
    // 公司创建已确认
    COMPANY_CONFIRMED,
    // 公司已删除
    COMPANY_DELETED
}
