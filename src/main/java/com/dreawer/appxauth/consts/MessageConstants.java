package com.dreawer.appxauth.consts;

/**
 * <CODE>ResourceConstants</CODE> 国际化资源常量类。<br/>
 * 该类用于定义“国际化（本地化）资源”的代码规范性常量，以统一工程中有关于对象、属性名称的代码规范。
 *
 * @author David Dai
 * @version 1.0
 * @since Dreawer 2.0
 */
public final class MessageConstants {

    /**
     * 资源文件名
     */
    public static final String FILE_MESSAGE_RESOURCE = "messageResource";
    /**
     * APPX前缀名
     */
    public static final String APPX_USER_PREFIX = "appx_";

    /**
     * 交互信息（专题未找到）
     */
    public static final String MSG_TOPIC_NOTFOUND = "msg.topic.notfound";

    // --------------------------------------------------------------------------------
    // 交互信息
    // --------------------------------------------------------------------------------
    /**
     * 交互信息（服务范围未找到）
     */
    public static final String MSG_CATEGORY_NOTFOUND = "msg.category.notfound";
    /**
     * 交互信息（应用名重复）
     */
    public static final String MSG_APP_NAME_DUPLUN = "msg.app.name.duplun";
    /**
     * 交互信息（应用未找到）
     */
    public static final String MSG_APP_NOTFOUND = "msg.app.notfound";
    /**
     * 交互信息（服务器忙）
     */
    public static final String MSG_SYSTEM_BUSY = "APPX服务器忙，请稍后再试！";
    /**
     * 交互信息（方案列表错误）
     */
    public static final String MSG_REQ_CASE_ERROR = "方案列表获取失败";
    /**
     * 交互信息（预览错误）
     */
    public static final String MSG_REQ_PREVIEW_ERROR = "预览列表获取失败";
    /**
     * 交互信息（主题错误）
     */
    public static final String MSG_THEMES_NOTFOUND = "主题列表获取失败";

    /**
     * 交互信息（主题色错误）
     */
    public static final String MSG_THEME_COLOR_NOTFOUND = "主题色列表获取失败";

    /**
     * 交互信息（默认主题预览图错误）
     */
    public static final String MSG_DEFAULT_THEME_IMAGE_NOTFOUND = "默认主题预览图获取失败";

    /**
     * 交互信息（主题预览图错误）
     */
    public static final String MSG_PREVIEW_IMAGE_NOTFOUND = "预览图获取失败!";

    /**
     * 交互信息（商品信息错误）
     */
    public static final String MEG_MERCHANDISE_NOTFOUND = "未找到商品信息!";

    /**
     * 交互信息（商品信息错误）
     */
    public static final String MEG_CASE_MERCHANDISE_NOTFOUND = "未找到方案商品信息!";

    /**
     * 交互信息（下单失败）
     */
    public static final String MSG_ORDER_NULL = "下单失败，请稍后再试！";

    /**
     * 交互信息（未找到订单）
     */
    public static final String MSG_ORDER_NOTFOUND = "未找到订单！";


    /**
     * 交互信息（商品状态有误）
     */
    public static final String MSG_CASE_STATUS_ERROR = "商品未支付或已经授权!";

    /**
     * 交互信息（未授权）
     */
    public static final String MSG_CASE_NOT_AUTHORIZED = "请先完成授权再发布!";
    /**
     * 交互信息（appID无效）
     */
    public static final String MSG_APP_ID_ERROR = "appId无效!";

    /**
     * 交互信息（account账户异常）
     */
    public static final String MSG_ACCOUNT_ERROR = "Account服务异常!";

    /**
     * 交互信息(未找到订单id)
     */
    public static final String MSG_ORDER_ID_NULL = "订单id无效";
    /**
     * 交互信息（未找到发布列表）
     */
    public static final String MSG_PUBLISH_LIST_NOTFOUND = "发布列表为空!";

    /**
     * 交互信息（APPID返回参数异常）
     */
    public static final String MSG_ACCOUNT_CALLBACK_ERROR = "查询appId返回参数异常!";

    /**
     * 交互信息（未找到商品）
     */
    public static final String MSG_MERCHANDISE_NOTFOUND = "未找到商品!";

    /**
     * 交互信息（未找到商品SPU）
     */
    public static final String MSG_SPUID_NOTFOUND = "未查询到商品SPU!";

    /**
     * 交互信息（未找到主题）
     */
    public static final String MSG_THEME_NOTFOUND = "未查询到相关主题!";

    /**
     * 交互信息（未找到方案）
     */
    public static final String MSG_CASE_NOTFOUND = "未查询到方案";

    /**
     * 交互信息（未找到商品详情!）
     */
    public static final String MSG_MERCHANDISE_DETAIL_NOTFOUND = "未找到商品详情!";

    /**
     * 交互信息（微信系统繁忙）
     */
    public static final String MSG_WX_SYS_BUSY = "微信系统忙或网络异常";

    /**
     * 交互信息（accessToken无效）
     */
    public static final String MSG_INVALID_TOKEN = "access_token无效!";

    /**
     * 交互信息（accessToken无效）
     */
    public static final String MSG_TOKEN_REFRESH_ERROR = "Token刷新失败!";

    /**
     * 交互信息（优惠码不存在）
     */
    public static final String MSG_COUPON_NOTFOUN = "优惠码不存在或已失效";

    // --------------------------------------------------------------------------------
    // 验证信息
    // --------------------------------------------------------------------------------
    /**
     * 验证信息（定制状态为空）
     */
    public static final String VAL_IS_CUSTOM_NOTEMPTY = "定制状态不能为空!";
    /**
     * 验证信息（方案id为空）
     */
    public static final String VAL_CASE_NOTEMPTY = "方案id不能为空";

    /**
     * 验证信息（方案ID号为空）
     */
    public static final String VAL_CASE_ID_NOTEMPTY = "方案ID不能为空!";

    /**
     * 验证信息（方案期限为空）
     */
    public static final String VAL_CASE_DURATION_NOTEMPTY = "方案期限不能为空!";

    /**
     * 验证信息（服务期限为空）
     */
    public static final String VAL_SERVICE_DURATION_NOTEMPTY = "服务期限不能为空!";


    /**
     * 验证信息（名称为空）
     */
    public static final String VAL_NAME_NOTEMPTY = "产品名称不能为空!";

    /**
     * 验证信息（logo为空）
     */
    public static final String VAL_LOGO_NOTEMPTY = "logo不能为空!";

    /**
     * 验证信息（类别为空）
     */
    public static final String VAL_CATEGORY_NOTEMPTY = "类别不能为空!";

    /**
     * 验证信息（行业为空）
     */
    public static final String VAL_INDUSTRY_NOTEMPTY = "行业不能为空!";

    /**
     * 验证信息（一句话介绍为空）
     */
    public static final String VAL_SHORTINTRO_NOTEMPTY = "一句话介绍不能为空!";

    /**
     * 验证信息（订单id为空）
     */
    public static final String VAL_ORDER_ID_NOTEMPTY = "订单id不能为空!";

    /**
     * 验证信息（详细介绍为空）
     */
    public static final String VAL_INTRO_NOTEMPTY = "详细介绍不能为空!";

    /**
     * 警告信息（专题未找到）
     */
    public static final String WARN_TOPIC_NOTFOUND = "warn.topic.notfound";


    // --------------------------------------------------------------------------------
    // 警告信息
    // --------------------------------------------------------------------------------
    /**
     * 警告信息（服务范围未找到）
     */
    public static final String WARN_CATEGORY_NOTFOUND = "warn.category.notfound";
    /**
     * 警告信息（应用未找到）
     */
    public static final String WARN_APP_NOTFOUND = "warn.app.notfound";
    /**
     * 警告信息（应用重复）
     */
    public static final String WARN_APP_NAME_DUPLUN = "warn.app.name.duplun";
    /**
     * 错误信息（其他异常）
     */
    public static final String ERR_OTHER = "其他错误！";

    // --------------------------------------------------------------------------------
    // 错误信息
    // --------------------------------------------------------------------------------

    /**
     * 私有构造器。
     */
    private MessageConstants() {
    }

}
