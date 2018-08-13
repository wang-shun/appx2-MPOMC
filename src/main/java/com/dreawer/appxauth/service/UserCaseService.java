package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.CaseCountForm;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.persistence.UserCaseDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * <CODE>UserCaseService</CODE>
 * 用户账户小程序服务
 *
 * @author fenrir
 * @Date 17-11-7
 */

@Service
public class UserCaseService {

    private Logger logger = Logger.getLogger(this.getClass()); // 日志记录器


    @Autowired
    private UserCaseDao userCaseDao;

    @Autowired
    private CaseService caseService;

    @Autowired
    private CaseThemeService caseThemeService;


    /**
     * 通过用户id查询所有账户下小程序
     *
     * @param id
     * @return 小程序列表
     */
    public List<UserCase> findAllUserCaseByUserId(String id) {
        return userCaseDao.findAllUserCaseByUserId(id);
    }

    /**
     * 通过订单id查询用户方案
     *
     * @param orderId
     * @return 用户方案
     */
    public UserCase findUserCaseByOrderId(String orderId) {
        return userCaseDao.findUserCaseByOrderId(orderId);
    }

    /**
     * 更新用户方案
     *
     * @param userCase
     */
    public void updateUserCase(UserCase userCase) {
        userCaseDao.updateUserCase(userCase);
    }

    /**
     * 生成用户方案
     *
     * @param userCase
     */
    public void addUserCase(UserCase userCase) {
        userCaseDao.addUserCase(userCase);
    }

    /**
     * 通过appid查询用户下单信息
     *
     * @param appid
     * @return
     */
    public UserCase findUserCaseByAppId(String appid) {
        return userCaseDao.findUserCaseByAppId(appid);
    }

    /**
     * 通过id查询用户订单信息
     *
     * @param userCaseId
     * @return
     */
    public UserCase findUserCaseById(String userCaseId) {
        return userCaseDao.findUserCaseById(userCaseId);
    }

    /**
     * 通过发布状态优先顺序查看订单信息
     *
     * @param publishStatus
     * @param start
     * @param pageSize
     * @return
     */
    public List<UserCase> findAllUserCaseByCondition(PublishStatus publishStatus, int start, int pageSize) {
        return userCaseDao.findAllUserCaseByCondition(publishStatus, start, pageSize);
    }

    /**
     * 通过条件查看订单信息
     *
     * @param userCase
     * @param contact
     * @param startTime
     * @param endTime
     * @param start
     * @param pageSize  @return
     */
    public List<UserCase> findAllUserCaseByCondition(UserCase userCase, String contact, Timestamp startTime, Timestamp endTime, int start, int pageSize) {
        return userCaseDao.findAllUserCaseByCondition(userCase, contact, startTime, endTime, start, pageSize);
    }

    /**
     * 查询所有订单数量
     *
     * @param status
     * @return
     */
    public int getCaseCount(PublishStatus status) {
        return userCaseDao.getCaseCount(status);
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
        return userCaseDao.getUserCaseByConditionCount(userCase, contact, startTime, endTime);
    }

    /**
     * 通过用户ID列表查看解决方案数量
     *
     * @param userIds
     */
    public List<CaseCountForm> getUserCaseByIdCount(List<String> userIds, Timestamp yellowAlert, Timestamp redAlert) {
        return userCaseDao.getUserCaseByIdCount(userIds, yellowAlert, redAlert);
    }

    /**
     * 查询指定期限内到期的解决方案列表
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public List<UserCase> findAllByExpireTime(Timestamp startTime, Timestamp endTime) {
        return userCaseDao.findAllByExpireTime(startTime, endTime);
    }
}
