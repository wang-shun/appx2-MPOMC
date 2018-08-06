package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.AppCaseTheme;
import com.dreawer.appxauth.persistence.CaseThemeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <CODE>CaseCategoryService</CODE>
 *
 * @author fenrir
 * @Date 17-10-26
 */

@Service
public class CaseThemeService {

    @Autowired
    private CaseThemeDao caseThemeDao;


    /**
     * 通过caseId查找主题列表
     *
     * @param caseId
     * @return
     */
    public List<AppCaseTheme> findThemesByCaseId(String caseId) {
        return caseThemeDao.findThemesByCaseId(caseId);
    }

    /**
     * 通过主题id查询skuId
     *
     * @param themeId
     * @return
     */
    public String findMerchandiseSkuByThemeId(String themeId) {
        return caseThemeDao.findMerchandiseSkuByThemeId(themeId);
    }

    /**
     * 通过主题id查询主题
     *
     * @param themeId
     * @return
     */
    public AppCaseTheme findThemeById(String themeId) {
        return caseThemeDao.findThemeById(themeId);
    }

    /**
     * 通过skuId查询主题id
     *
     * @param themeSkuId
     * @return
     */
    public AppCaseTheme findThemeBySkuId(String themeSkuId) {
        return caseThemeDao.findThemeBySkuId(themeSkuId);
    }

    /**
     * 更新tempid
     *
     * @param themeId
     * @param tempId
     */
    public void updateTempIdByThemId(String themeId, String tempId) {
        caseThemeDao.updateTempIdByThemId(themeId, tempId);
    }

    /**
     * 查询所有主题列表
     *
     * @return
     */
    public List<AppCaseTheme> findAllThemes() {
        return caseThemeDao.findAllThemes();
    }

    /**
     * 通过SpuId查询主题
     *
     * @param merchandiseId
     * @return
     */
    public AppCaseTheme findThemeBySpuId(String merchandiseId) {
        return caseThemeDao.findThemeBySpuId(merchandiseId);
    }
}
