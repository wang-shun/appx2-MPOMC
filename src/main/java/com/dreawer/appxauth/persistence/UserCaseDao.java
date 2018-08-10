package com.dreawer.appxauth.persistence;

import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.domain.CaseCountForm;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.persistence.mybatis.MyBatisBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <CODE>UserCaseDao</CODE>
 * 用户小程序DAO
 *
 * @author fenrir
 * @Date 17-11-7
 */

@Repository
public class UserCaseDao extends MyBatisBaseDao<UserCase> {


    /**
     * 通过用户id查询所有账户下小程序
     *
     * @param id
     * @return 小程序列表
     */
    public List<UserCase> findAllUserCaseByUserId(String id) {
        return selectList("findAllUserCaseByUserId", id);
    }

    /**
     * 通过订单id查询用户方案
     *
     * @param orderId
     * @return 用户方案
     */
    public UserCase findUserCaseByOrderId(String orderId) {
        return selectOne("findUserCaseByOrderId", orderId);
    }

    /**
     * 更新用户方案
     *
     * @param userCase
     */
    public void updateUserCase(UserCase userCase) {
        update("updateUserCase", userCase);
    }

    /**
     * 生成用户方案
     *
     * @param userCase
     */
    public void addUserCase(UserCase userCase) {
        insert("addUserCase", userCase);
    }

    /**
     * 通过appid查询用户下单信息
     *
     * @param appid
     * @return
     */
    public UserCase findUserCaseByAppId(String appid) {
        return selectOne("findUserCaseByAppId", appid);
    }

    /**
     * 通过id查询用户订单信息
     *
     * @param userCaseId
     * @return
     */
    public UserCase findUserCaseById(String userCaseId) {
        return selectOne("findUserCaseById", userCaseId);
    }

    /**
     * 查看订单信息
     *
     * @param publishStatus
     * @param start
     * @param pageSize
     * @return
     */
    public List<UserCase> findAllUserCaseByCondition(PublishStatus publishStatus, int start, int pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("publishStatus", publishStatus);
        param.put("start", start);
        param.put("pageSize", pageSize);
        return selectList("findAllUserCaseByCondition", param);
    }

    /**
     * 查看订单信息
     *
     * @param userCase
     * @param contact
     * @param startTime
     * @param endTime
     * @param start
     * @param pageSize  @return
     */
    public List<UserCase> findAllUserCaseByCondition(UserCase userCase, String contact, Timestamp startTime, Timestamp endTime, int start, int pageSize) {
        Map<String, Object> param = new HashMap<>();
        param.put("userCase", userCase);
        param.put("contact", contact);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        param.put("start", start);
        param.put("maxResults", pageSize);
        return selectList("findAllUserCaseByUserCase", param);
    }

    /**
     * 查询所有订单数量
     *
     * @param status
     * @return
     */
    public int getCaseCount(PublishStatus status) {
        Map<String, Object> param = new HashMap<>();
        param.put("publishStatus", status);
        return selectOne("getCaseCount", param);
    }

    /**
     * 通过条件查看订单信息
     *
     * @param userCase
     * @param contact
     * @param startTime
     * @param endTime
     * @return
     */
    public int getUserCaseByConditionCount(UserCase userCase, String contact, Timestamp startTime, Timestamp endTime) {
        Map<String, Object> param = new HashMap<>();
        param.put("userCase", userCase);
        param.put("contact", contact);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        return count("getUserCaseByConditionCount", param);
    }

    /**
     * 通过用户ID列表查看解决方案数量
     *
     * @param userIds
     */
    public List<CaseCountForm> getUserCaseByIdCount(List<String> userIds, Timestamp yellowAlert, Timestamp redAlert) {
        Map<String, Object> param = new HashMap<>();
        param.put("userIds", userIds);
        param.put("yellowAlert", yellowAlert);
        param.put("redAlert", redAlert);
        //return selectList("getUserCaseByConditionCount", param);
        return this.getSqlSession().selectList("getUserCaseByIdCount", param);
    }
}
