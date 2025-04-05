package com.stdu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.stdu.entity.Company;
import com.stdu.mapper.CompanyMapper;
import com.stdu.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 企业管理服务实现类
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public IPage<Company> listCompanies(Integer page, Integer size, String name, String code, String contactPerson) {
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        if (StringUtils.hasText(name)) {
            wrapper.like(Company::getName, name);
        }
        if (StringUtils.hasText(code)) {
            wrapper.like(Company::getCode, code);
        }
        if (StringUtils.hasText(contactPerson)) {
            wrapper.like(Company::getContactPerson, contactPerson);
        }
        return page(new Page<>(page, size), wrapper);
    }

    @Override
    public Company getCompanyById(Long id) {
        return getById(id);
    }

    @Override
    public Long addCompany(Company company) {
        // 检查企业编码是否已存在
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getCode, company.getCode());
        if (count(wrapper) > 0) {
            throw new RuntimeException("企业编码已存在");
        }
        save(company);
        return company.getId();
    }

    @Override
    public void updateCompany(Long id, Company company) {
        // 检查企业是否存在
        Company existingCompany = getById(id);
        if (existingCompany == null) {
            throw new RuntimeException("企业不存在");
        }
        // 检查企业编码是否已被其他企业使用
        LambdaQueryWrapper<Company> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Company::getCode, company.getCode())
                .ne(Company::getId, id);
        if (count(wrapper) > 0) {
            throw new RuntimeException("企业编码已被其他企业使用");
        }
        company.setId(id);
        updateById(company);
    }

    @Override
    public void deleteCompany(Long id) {
        // 检查企业是否存在
        if (!removeById(id)) {
            throw new RuntimeException("企业不存在");
        }
    }

    @Override
    public void updateCompanyStatus(Long id, Integer status) {
        // 检查企业是否存在
        Company company = getById(id);
        if (company == null) {
            throw new RuntimeException("企业不存在");
        }
        // 更新状态
        company.setStatus(status.byteValue());
        updateById(company);
    }
}
