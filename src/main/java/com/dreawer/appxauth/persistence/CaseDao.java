package com.dreawer.appxauth.persistence;

import com.dreawer.appxauth.domain.AppCase;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <CODE>CaseDao</CODE>
 * 方案 DAO 类 负责对方案信息进行操作
 *
 * @author fenrir
 * @Date 17-10-25
 */
@Repository
public class CaseDao extends MyBatisBaseDao<AppCase> {

    /**
     * 获取所有方案
     *
     * @return 方案列表
     */
    public List<AppCase> findAll() {
        return selectList("findAllAppCase");
    }

    /**
     * 通过id查询方案
     *
     * @param id
     * @return 方案信息
     */
    public AppCase findById(String id) {
        return selectOne("findById", id);
    }

    /**
     * 通过caseId查询商品skuId
     *
     * @param id
     * @return 商品skuId
     */
    public String findMerchandiseSkuByCaseId(String id) {
        return selectOne("findMerchandiseSkuByCaseId", id);
    }

}
