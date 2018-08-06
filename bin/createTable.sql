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
CREATE TABLE application
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