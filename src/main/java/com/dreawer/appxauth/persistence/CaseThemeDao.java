package com.dreawer.appxauth.persistence;

import com.dreawer.appxauth.domain.AppCaseTheme;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <CODE>CaseCustomDao</CODE>
 * 方案定制DAO
 *
 * @author fenrir
 * @Date 17-10-26
 */

@Repository
public class CaseThemeDao extends MyBatisBaseDao<AppCaseTheme> {

    /**
     * 通过方案id查询主题列表
     *
     * @param id
     * @return 主题集合
     */
    public List<AppCaseTheme> findThemeListByCaseId(String id) {
        return selectList("findThemeListByCaseId", id);
    }

    /**
     * 通过类目id集合查询主题列表
     *
     * @param themeList
     * @return 主题集合
     */
    public List<AppCaseTheme> findThemes(List<String> themeList) {
        return selectList("findThemesByIds", themeList);
    }

    /**
     * 通过方案ID查询所有预览主题
     *
     * @param caseId
     * @return
     */
    public List<AppCaseTheme> findPreviewThemesByCaseId(String caseId) {
        return selectList("findPreviewThemesByCaseId", caseId);
    }

    /**
     * 通过条件查询主题对象
     *
     * @param caseTheme
     * @return 主题对象
     */
    public AppCaseTheme findThemes(AppCaseTheme caseTheme) {
        return selectOne("findThemesByCondition", caseTheme);
    }

    /**
     * 通过caseId查询主题列表
     *
     * @param caseId
     * @return
     */
    public List<AppCaseTheme> findThemesByCaseId(String caseId) {
        return selectList("findThemesByCaseId", caseId);
    }

    /**
     * 通过主题id查询skuId
     *
     * @param themeId
     * @return
     */
    public String findMerchandiseSkuByThemeId(String themeId) {
        return selectOne("findMerchandiseSkuByThemeId", themeId);
    }

    /**
     * 通过主题id查询主题
     *
     * @param themeId
     * @return
     */
    public AppCaseTheme findThemeById(String themeId) {
        return selectOne("findThemeById", themeId);
    }

    /**
     * 通过skuId查询主题id
     *
     * @param themeSkuId
     * @return
     */
    public AppCaseTheme findThemeBySkuId(String themeSkuId) {
        return selectOne("findThemeBySkuId", themeSkuId);
    }

    /**
     * 更新tempId
     *
     * @param themeId
     * @param tempId
     */
    public void updateTempIdByThemId(String themeId, String tempId) {
        AppCaseTheme caseTheme = new AppCaseTheme();
        caseTheme.setId(themeId);
        caseTheme.setTemplateId(tempId);
        update("updateTempIdByThemId", caseTheme);
    }

    /**
     * 查询所有主题列表
     *
     * @return
     */
    public List<AppCaseTheme> findAllThemes() {
        return selectList("findAllThemes");
    }

    /**
     * 通过SpuId查询主题
     *
     * @param merchandiseId
     * @return
     */
    public AppCaseTheme findThemeBySpuId(String merchandiseId) {
        return selectOne("findThemeBySpuId", merchandiseId);
    }
}
