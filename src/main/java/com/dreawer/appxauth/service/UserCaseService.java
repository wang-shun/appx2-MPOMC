package com.dreawer.appxauth.service;

import com.dreawer.appxauth.domain.CaseCountForm;
import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.lang.ResultType;
import com.dreawer.appxauth.persistence.UserCaseDao;
import com.dreawer.appxauth.utils.JsonFormatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<UserCase> findAllUserCaseByCondition(UserCase userCase,
                                                     String contact,
                                                     Timestamp startTime,
                                                     Timestamp endTime,
                                                     int start,
                                                     int pageSize,
                                                     String status) {
        return userCaseDao.findAllUserCaseByCondition(userCase, contact, startTime, endTime, start, pageSize, status);
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
    public int getUserCaseByConditionCount(UserCase userCase, String contact, Timestamp startTime, Timestamp endTime, String status) {
        return userCaseDao.getUserCaseByConditionCount(userCase, contact, startTime, endTime, status);
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

    /**
     * 查询解决方案详情
     *
     * @return
     */
    public UserCase findById(String id) {
        return userCaseDao.findById(id);
    }

    /**
     * 更新用户解决方案并检查授权条件
     *
     * @param userCase
     * @param list
     * @return
     */
    public UserCase updateAuditResult(UserCase userCase, List<ResultType> list) {
        //无失败原因则授权条件具备
        if (list.size() == 0) {
            userCaseDao.updateUserCase(userCase);
            userCase.setPublishStatus(PublishStatus.AUTHORIZED);
            return userCase;
        }
        //如果个人用户购买ECS则通过授权
        if (list.size() == 1) {
            ResultType type = list.get(0);
            if (type.equals(ResultType.PRINCIPAL) && userCase.getDomain().equals("https://ecs.dreawer.com/")) {
                userCase.setPublishStatus(PublishStatus.AUTHORIZED);
                userCaseDao.updateUserCase(userCase);
                return userCase;
            }
        }

        StringBuilder auditResult = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ResultType type = list.get(i);
            if (type.equals(ResultType.PERMISSIONDENIED)) {
                auditResult.append(";用户未提供开发权限");
            }
            //如果非ECS小程序为个人主体
            if (type.equals(ResultType.PRINCIPAL) && !userCase.getDomain().equals("https://ecs.dreawer.com/")) {
                auditResult.append(";小程序主体需为企业");
            }
            if (type.equals(ResultType.NAME)) {
                auditResult.append(";小程序名称未填写");
            }
            if (type.equals(ResultType.CATEGORY)) {
                auditResult.append(";小程序类目未填写");
            }
        }
        String result = auditResult.substring(1, auditResult.length());
        userCase.setAuditResult(result);
        userCase.setPublishStatus(PublishStatus.MISSINGCONDITION);
        logger.info("更新后结果" + JsonFormatUtil.formatJson(userCase));
        userCaseDao.updateUserCase(userCase);
        return userCase;
    }
}
