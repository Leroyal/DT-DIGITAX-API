package com.digitax.endpoint;

import com.digitax.soap.service.LoginService;
import gov.irs.mef.exception.ServiceException;
import gov.irs.mef.exception.ToolkitException;
import gov.irs.mef.services.ServiceContext;
import gov.irs.mef.services.data.ETIN;
import gov.irs.mef.services.msi.LoginClient;
import gov.irs.mef.services.msi.LoginResult;
import gov.irs.mef.services.util.KeyStoreUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.io.File;

@Endpoint
public class LoginRequestEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(LoginRequestEndpoint.class);

    private LoginService loginService;
    private ETIN etin;

    @Autowired
    public LoginRequestEndpoint(LoginService loginService) {
        this.loginService = loginService;
    }

    @PayloadRoot(namespace = "NAMESPACE_URI", localPart = "getLoginRequest")
    public @ResponsePayload
    LoginResult getLogin() throws ToolkitException, ServiceException {
        File keyStoreFile = new File("DT-DIGITAX-API/clientkeystore/trd.jks");
        String keyStorePassword = "testPassword";
        String keyAlias = "digitaltaxusa";
        String keyStoreType = "PKCS12";

        char[] chars = new char[keyStorePassword.length()];
        KeyStoreUtil.loadKeyStore(keyStoreFile, chars);

        String appSysID = "47762"; //TODO replace with real value
        ServiceContext context = new ServiceContext(etin, appSysID, null);
        // get ETIN
        etin = context.getEtin();

        // initialize login client
        LoginClient loginClient = new LoginClient();
        LoginResult result = loginClient.invoke(
                context,
                keyStoreFile,
                keyStorePassword,
                keyAlias,
                null,
                java.security.Security.getProperty(keyStoreType)
        );
        return result;
    }
}

