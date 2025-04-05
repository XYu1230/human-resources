package com.stdu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.stdu.common.Result;
import com.stdu.entity.Company;
import com.stdu.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 企业管理控制器
 */
@RestController
@RequestMapping("/api/company")
@Slf4j
@CrossOrigin(origins = "*")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 分页获取企业列表
     */
    @GetMapping("/list")
    public Result<Map<String, Object>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String contactPerson) {
        log.info("分页获取企业列表，page={}, size={}, name={}, code={}, contactPerson={}", page, size, name, code, contactPerson);
        IPage<Company> pageResult = companyService.listCompanies(page, size, name, code, contactPerson);
        Map<String, Object> data = new HashMap<>();
        data.put("records", pageResult.getRecords());
        data.put("total", pageResult.getTotal());
        data.put("size", pageResult.getSize());
        data.put("current", pageResult.getCurrent());
        data.put("pages", pageResult.getPages());
        return Result.success(data);
    }

    /**
     * 获取企业详情
     */
    @GetMapping("/{id}")
    public Result<Company> getById(@PathVariable Long id) {
        log.info("获取企业详情，id={}", id);
        return Result.success(companyService.getCompanyById(id));
    }

    /**
     * 新增企业
     */
    @PostMapping
    public Result<Map<String, Long>> add(@RequestBody Company company) {
        log.info("新增企业，company={}", company);
        Long id = companyService.addCompany(company);
        Map<String, Long> data = new HashMap<>();
        data.put("id", id);
        return Result.success(data);
    }

    /**
     * 更新企业
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestBody Company company) {
        log.info("更新企业，id={}, company={}", id, company);
        companyService.updateCompany(id, company);
        return Result.success();
    }

    /**
     * 删除企业
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        log.info("删除企业，id={}", id);
        companyService.deleteCompany(id);
        return Result.success();
    }

    /**
     * 更新企业状态
     */
    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        log.info("更新企业状态，id={}, status={}", id, params.get("status"));
        companyService.updateCompanyStatus(id, params.get("status"));
        return Result.success();
    }
}
