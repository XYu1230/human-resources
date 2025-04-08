package com.stdu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.stdu.entity.Company;

/**
 * 企业管理服务接口
 */
public interface CompanyService{

    /**
     * 分页查询企业列表
     */
    IPage<Company> listCompanies(Integer page, Integer size, String name, String code, String contactPerson);

    /**
     * 根据ID获取企业详情
     */
    Company getCompanyById(Long id);

    /**
     * 新增企业
     */
    Long addCompany(Company company);

    /**
     * 更新企业信息
     */
    void updateCompany(Long id, Company company);

    /**
     * 删除企业
     */
    void deleteCompany(Long id);

    /**
     * 更新企业状态
     */
    void updateCompanyStatus(Long id, Integer status);
}