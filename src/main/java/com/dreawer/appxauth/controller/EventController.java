package com.dreawer.appxauth.controller;

import com.dreawer.appxauth.domain.UserCase;
import com.dreawer.appxauth.lang.PublishStatus;
import com.dreawer.appxauth.manager.TokenManager;
import com.dreawer.appxauth.model.XmlParser;
import com.dreawer.appxauth.utils.aes.AesException;
import com.dreawer.appxauth.utils.aes.WXBizMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;

import static com.dreawer.appxauth.consts.ThirdParty.*;

/**
 * <CODE>EventController</CODE>
 * 消息事件接受控制器
 *
 * @author fenrir
 * @Date 18-7-3
 */
@RestController
@RequestMapping("/event")
@Slf4j
public class EventController extends BaseController {


    /**
     * 接收微信定时推送的component_verify_ticket
     *
     * @param req
     */
    @RequestMapping("/ticket")
    public HttpServletResponse receiveTicket(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String timestamp = req.getParameter("timestamp");
            log.error("timestamp_" + timestamp);
            String nonce = req.getParameter("nonce");
            log.error("nonce_" + nonce);
            String msg_signature = req.getParameter("msg_signature");
            log.error("msg_signature_" + msg_signature);
            StringBuilder sb = new StringBuilder();
            BufferedReader in = req.getReader();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            String encryptMsg = sb.toString();

            log.error("encryptMsg_" + encryptMsg);
            log.error("PROGRAM_VALIDATE_TOKEN" + "====" + PROGRAM_VALIDATE_TOKEN);
            log.error("PROGRAM_ENCODING_AES_KEY" + "====" + PROGRAM_ENCODING_AES_KEY);
            log.error("APPX_THIRDPARTY_APPID" + "====" + APPX_THIRDPARTY_APPID);

            Document document = new SAXReader().read(new ByteArrayInputStream(encryptMsg.getBytes("UTF-8")));
            String msg = document.getRootElement().element("Encrypt").getStringValue();
            //解密消息
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(PROGRAM_VALIDATE_TOKEN, PROGRAM_ENCODING_AES_KEY, APPX_THIRDPARTY_APPID);
            String decryptMsg = wxcpt.decrypt(msg);
            //xml转换为对象
            JAXBContext context = JAXBContext.newInstance(XmlParser.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(decryptMsg);
            XmlParser parser = (XmlParser) unmarshaller.unmarshal(sr);
            String componentVerifyTicket = parser.getComponentVerifyTicket();
            log.error("ticket=" + componentVerifyTicket);
            //保存令牌
            TokenManager.setVerifyTicket(componentVerifyTicket);
            resp.getWriter().write("success");
            return resp;
        } catch (Exception e) {
            log.error("error on Exception :", e);
            return null;
        }
    }

    /**
     * 监听审核结果
     *
     * @param req
     * @param appid
     */
    @PostMapping(value = "/callback/{appid}")
    public void getPendingResult(HttpServletRequest req, @PathVariable String appid) throws IOException, DocumentException, AesException, JAXBException {

        StringBuilder sb = new StringBuilder();
        BufferedReader in = req.getReader();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        String xmlContext = sb.toString();
        log.debug(xmlContext);
        //解密接受xml数据
        Document document = new SAXReader().read(new ByteArrayInputStream(xmlContext.getBytes("UTF-8")));
        String encryptMsg = document.getRootElement().element("Encrypt").getStringValue();
        WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(PROGRAM_VALIDATE_TOKEN, PROGRAM_ENCODING_AES_KEY, APPX_THIRDPARTY_APPID);
        String decryptMsg = wxcpt.decrypt(encryptMsg);
        //xml转换为对象
        JAXBContext context = JAXBContext.newInstance(XmlParser.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        StringReader sr = new StringReader(decryptMsg);
        XmlParser parser = (XmlParser) unmarshaller.unmarshal(sr);
        log.debug("appid=" + appid + " " + parser.toString());
        UserCase userCase = userCaseService.findUserCaseByAppId(appid);
        if (userCase == null) {
            return;
        }
        //判断是否审核成功
        if (StringUtils.isEmpty(parser.getFailTime()) || parser.getEvent().equals("weapp_audit_success")) {
            //发送消息到APPX
            userCase.setPublishStatus(PublishStatus.UNPUBLISHED);
        }
        if (StringUtils.isEmpty(parser.getSuccTime()) || parser.getEvent().equals("weapp_audit_fail")) {
            log.error("审核不通过,原因:" + parser.getReason());
            //发送信息
            userCase.setPublishStatus(PublishStatus.AUDITFAILED);
            userCase.setAuditResult(parser.getReason());
        }
        userCaseService.updateUserCase(userCase);

    }

}
