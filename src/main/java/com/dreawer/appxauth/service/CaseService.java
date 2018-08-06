package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.AppCase;
import com.dreawer.appxauth.persistence.CaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <CODE>CaseService</CODE>
 * 方案信息服务
 *
 * @author fenrir
 * @Date 17-10-25
 */
@Service
public class CaseService {

    @Autowired
    private CaseDao caseDao;

    /**
     * 获取所有方案
     *
     * @return 方案列表
     */
    public List<AppCase> findAll() {
        return caseDao.findAll();
    }

    /**
     * 通过id查询方案
     *
     * @param caseId
     * @return 方案信息
     */
    public AppCase findById(String caseId) {
        return caseDao.findById(caseId);
    }

    /**
     * 通过caseId查询商品skuId
     *
     * @param caseId
     * @return
     */
    public String findMerchandiseSkuByCaseId(String caseId) {
        return caseDao.findMerchandiseSkuByCaseId(caseId);
    }


}
