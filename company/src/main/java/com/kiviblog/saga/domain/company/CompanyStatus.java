package com.kiviblog.saga.domain.company;

/**
 * @author yangzifeng
 */
public enum  CompanyStatus {
    // 公司初始状态
    COMPANY_INIT,
    // 公司已创建
    COMPANY_CREATED,
    // UAA消费完成
    UAA_CHECKED,
    // RES消费完成
    RES_CHECKED,
    // 公司创建已确认
    COMPANY_CONFIRMED,
    // 公司已删除
    COMPANY_DELETED
}
