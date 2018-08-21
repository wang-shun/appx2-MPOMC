CREATE TABLE auth_info
(
  id        varchar(32) PRIMARY KEY
  COMMENT '主键',
  appid     char(32)     NOT NULL
  COMMENT '小程序id',
  auth_code varchar(255) NOT NULL
  COMMENT '授权码',
  acc_tkn   varchar(255) NOT NULL
  COMMENT '授权令牌',
  ref_tkn   varchar(255) NOT NULL
  COMMENT '刷新令牌',
  expire_in timestamp COMMENT '到期时间',
  app_type  char(32) COMMENT '小程序类型',
  cre_id    char(32) COMMENT '用户id',
  cre_tim   timestamp DEFAULT current_timestamp
  COMMENT '创建时间',
  upd_tim   timestamp DEFAULT current_timestamp
  COMMENT '更新时间'
);

DROP TABLE IF EXISTS `application`;
CREATE TABLE application
(
  id    varchar(32) PRIMARY KEY
  COMMENT '主键',
  appid char(32) NOT NULL
  COMMENT '小程序id',
  oid   char(32) NOT NULL
  COMMENT '小程序应用id'
);
DROP TABLE IF EXISTS `app_user`;
CREATE TABLE app_user
(
  id          varchar(32) PRIMARY KEY
  COMMENT '主键',
  appid       char(32)     NOT NULL
  COMMENT '小程序id',
  oid         char(32)     NOT NULL
  COMMENT '小程序应用id',
  open_id     varchar(255) NOT NULL
  COMMENT '用户身份认证Id',
  session_key varchar(255) NOT NULL
  COMMENT '会话令牌',
  cre_tim     timestamp DEFAULT current_timestamp
  COMMENT '创建时间'
);

DROP TABLE IF EXISTS `appx_user_case`;
CREATE TABLE `appx_user_case` (
  `id`          char(32)      NOT NULL
  COMMENT 'ID序列号',
  `name`        varchar(128)  NOT NULL
  COMMENT '方案名称',
  `logo`        varchar(255)           DEFAULT NULL
  COMMENT '商标',
  `sto_id`      char(32)
  COMMENT '店铺Id',
  `bac_url`     char(128)              DEFAULT NULL,
  `pre_qr`      varchar(128)           DEFAULT NULL
  COMMENT '预览图url',
  `rel_qr`      varchar(128)           DEFAULT NULL
  COMMENT '发布图url',
  `cat_id`      varchar(128)           DEFAULT NULL
  COMMENT '类目id',
  `app_id`      char(32)               DEFAULT NULL
  COMMENT '小程序appId',
  `domain`      varchar(128)           DEFAULT NULL
  COMMENT '小程序域名',
  `category`    varchar(128)           DEFAULT NULL
  COMMENT '类目名称',
  `appCategory` TEXT COMMENT '类目',
  `sal_mode`    char(32)               DEFAULT 'DEFAULT'
  COMMENT '/*发布状态(DEFAULT-默认,ADVANCE-预售) */',
  `cli_nam`     varchar(32)            DEFAULT NULL
  COMMENT '客户名称',
  `cli_cnt`     char(50)               DEFAULT NULL
  COMMENT '联系方式',
  `dele_ph`     varchar(50)            DEFAULT NULL,
  `pub_sts`     char(32)      NOT NULL DEFAULT 'UNPAID'
  COMMENT '发布状态(PUBLISHED-已发布,UNPUBLISHED-未发布,DELEGATEDPUBLISHING-委托发布中,PENDING-审核中,UNPAID-未支付)',
  `dur_type`    varchar(50)   NOT NULL
  COMMENT '付费周期',
  `exp_date`    timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '到期时间',
  `crer_id`     char(32)               DEFAULT NULL
  COMMENT '创建者用户ID号',
  `cre_tim`     timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP
  COMMENT '创建时间',
  `updr_id`     char(32)               DEFAULT NULL
  COMMENT '更新者用户ID号',
  `upd_tim`     datetime               DEFAULT NULL
  COMMENT '更新时间',
  `orders`      varchar(1000) NOT NULL
  COMMENT '订单id列表',
  `thm_nam`     varchar(128)           DEFAULT NULL
  COMMENT '用户选择主题名称',
  `app_nam`     varchar(128)           DEFAULT NULL
  COMMENT '用户授权小程序名称',
  `aud_res`     varchar(1000)          DEFAULT NULL
  COMMENT '审核结果',
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT ='用户已购方案表';