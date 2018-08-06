package com.dreawer.appxauth.domain;

import com.dreawer.appxauth.lang.ThemeStatus;
import com.dreawer.domain.BaseDomain;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <CODE>AppCaseTheme</CODE>
 * 小程序方案定制类目实体类
 *
 * @author fenrir
 * @Date 17-10-26
 */
@Data
public class AppCaseTheme extends BaseDomain {

    private static final long serialVersionUID = -2224561293954454034L;

    private String name = null; //类目名称

    private String caseId = null; //方案id

    private String templateId = null; //小程序模板id

    private String spuId = null; //主题spuId

    private Integer sortOrder = null; //排序顺序

    private ThemeStatus status = null; //类目状态

    private String createrId = null; // 创建者

    private Timestamp createTime = null; // 创建时间

    private String updaterId = null; // 更新者

    private Timestamp updateTime = null; // 更新时间


}
