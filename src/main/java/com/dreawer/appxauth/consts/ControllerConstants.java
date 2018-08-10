package com.dreawer.appxauth.consts;

/**
 * <CODE>RequestConstants</CODE> 控制器层常量类。
 * 该类用于定义“请求链接”和“页面地址”的代码规范性常量，以统一工程中有关于对象、属性名称的代码规范。
 *
 * @author David Dai
 * @version 1.0
 * @since Dreawer 1.0
 */
public final class ControllerConstants {

    /**
     * 首页控制器
     */
    public static final String INDEX_CONTROLLER = "indexController";

    // --------------------------------------------------------------------------------
    // 控制器
    // --------------------------------------------------------------------------------
    /**
     * 方案控制器
     */
    public static final String CASE_CONTROLLER = "caseController";
    /**
     * 服务控制器
     */
    public static final String SERVICE_CONTROLLER = "serviceController";
    /**
     * 小程序发布控制器
     */
    public static final String PUBLISH_CONTROLLER = "publishController";
    /**
     * 授权管理控制器
     */
    public static final String AUTHORIZE_CONTROLLER = "authorizeController";
    /**
     * 第三方接口调用控制器
     */
    public static final String THIRD_PARTY_CONTROLLER = "thirdPartyController";
    /**
     * 优惠码控制器
     */
    public static final String COUPON_CONTROLLER = "cupnController";
    /**
     * 请求 "方案"
     */
    public static final String REQ_CASE = "/case";

    // --------------------------------------------------------------------------------
    // 请求地址
    // --------------------------------------------------------------------------------
    /**
     * 请求 "服务"
     */
    public static final String REQ_SERVICE = "/service";
    /**
     * 请求“生成”
     */
    public static final String REQ_CREATE = "/create";
    /**
     * 请求“发布”
     */
    public static final String REQ_PUBLISH = "/publish";
    /**
     * 请求“令牌”
     */
    public static final String REQ_TOKEN = "/getToken";
    /**
     * 请求“初始化令牌”
     */
    public static final String INIT_TOKEN = "/initToken";
    /**
     * 请求“APP_KEY”
     */
    public static final String REQ_APP_KEY = "/getAppKey";
    /**
     * 请求 "详情"
     */
    public static final String DETAIL = "/detail";
    /**
     * 请求“优惠券”
     */
    public static final String REQ_COUPON = "/cupn";
    /**
     * 请求 "预售状态"
     */
    public static final String SALEMODE_STATUS = "/saleMode/status";
    /**
     * 请求 "预览"
     */
    public static final String IMAGE_PREVIEW = "/preview/image";
    /**
     * 请求 "查询"
     */
    public static final String REQ_QUERY = "/query";


    /**
     * 请求 "根据用户ID查询"
     */
    public static final String COUNT_BY_ID = "/countById";


    /**
     * 请求 "通过ID查询"
     */
    public static final String REQ_BY_ID = "/byId";
    /**
     * 请求 "定制"
     */
    public static final String CUSTOMIZE = "/custom";
    /**
     * 请求 "更新"
     */
    public static final String UPDATE = "/update";
    /**
     * 请求 "生成订单"
     */
    public static final String ORDER_CREATE = "/order/create";
    /**
     * 请求 "获取商品id"
     */
    public static final String THEME_TO_MERCHANDISE = "/theme/merchandiseId";
    /**
     * 请求 "更新用户手机号"
     */
    public static final String UPDATE_MOBILE = "/mobile/update";
    /**
     * 订单基础url
     */
    public static final String ORDER_BASE_URL = "/order";
    /**
     * 请求"提交自主发布"
     */
    public static final String SELFPUBLISH_ADD = "/selfPublish/add";
    /**
     * 请求"列表"
     */
    public static final String LIST = "/list";

    /**
     * 请求"提交"
     */
    public static final String COMMIT = "/commit";
    /**
     * 请求"图片"
     */
    public static final String IMAGE = "/image";
    /**
     * 请求"审核"
     */
    public static final String AUDIT = "/audit";
    /**
     * 请求"生成用户方案"
     */
    public static final String CREATE = "/userCase/create";
    /**
     * 请求"添加订单"
     */
    public static final String ORDER_ADD = "/order/add";
    /**
     * 请求"授权"
     */
    public static final String REQ_AUTH = "/auth";
    /**
     * 请求"更改小程序记录支付状态"
     */
    public static final String UPDATE_PAID = "/userCase/updatePaid";
    /**
     * 请求"更改小程序记录为委托发布中"
     */
    public static final String UPDATE_DELEGATE = "/updateDelegating";
    /**
     * 请求"更改小程序为已授权"
     */
    public static final String UPDATE_AUTHORIZED = "/userCase/updateAuthorized";
    /**
     * 请求"查询小程序发布状态"
     */
    public static final String AUTHORIZE_STATUS = "/userCase/authorizeStatus";
    /**
     * 请求"查询小程序最新一次提交的审核状态"
     */
    public static final String LATEST_AUDIT_STATUS = "/last_auditStatus";
    /**
     * 用户请求 URL
     */
    public static final String REQ_URL = "requestUrl";

    // --------------------------------------------------------------------------------
    // 其他常量
    // --------------------------------------------------------------------------------
    /**
     * 用户请求 费用
     */
    public static final String REQ_FEE = "/fee";
    /**
     * 错误信息
     */
    public static final String ERRORS = "errors";
    /**
     * 错误信息
     */
    public static final String ERROR = "error";
    /**
     * 商品url
     **/
    public static final String MERCHANDISE_BASE_URL = "/appx";
    /**
     * 账户url
     **/
    public static final String ACCOUNT_BASE_URL = "/account";
    /**
     * app详情url
     **/
    public static final String APP_DETAIL = "/app/detail";
    /**
     * 查询商品详情
     **/
    public static final String SKU_QUERY = "/sku/query";
    /**
     * 刷新token
     **/
    public static final String REFRESH_TOKEN = "/thirdPart/refreshToken";
    /**
     * 第三方
     **/
    public static final String THIRD_PARTY = "/thirdParty";

    /**
     * 私有构造器。
     */
    private ControllerConstants() {
    }


}
