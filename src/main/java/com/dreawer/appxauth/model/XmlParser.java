package com.dreawer.appxauth.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <CODE>XmlParser</CODE>
 * XML解析实体类
 *
 * @author fenrir
 * @Date 17-11-21
 */

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlParser {

    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private String createTime;

    @XmlElement(name = "MsgType")
    private String msgType;

    @XmlElement(name = "Event")
    private String event;

    @XmlElement(name = "AppId")
    private String appId;

    @XmlElement(name = "InfoType")
    private String infoType;

    @XmlElement(name = "ComponentVerifyTicket")
    private String componentVerifyTicket;

    @XmlElement(name = "SuccTime")
    private String succTime;

    @XmlElement(name = "Reason")
    private String reason;

    @XmlElement(name = "FailTime")
    private String failTime;


}
