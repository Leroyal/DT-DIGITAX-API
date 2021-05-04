package com.digitax.endpoint;

import java.io.File;

import org.springframework.stereotype.Component;

import gov.irs.a2a.mef.mefheader.TestCdType;
import gov.irs.mef.ApplicationContext;
import gov.irs.mef.services.ServiceContext;
import gov.irs.mef.services.data.ETIN;
import gov.irs.mef.services.msi.LoginClient;
import gov.irs.mef.services.msi.LoginResult;
@Component
public class LoginRequestEndPoint {
	//private static final Logger logger = LoggerFactory.getLogger(LoginRequestEndpoint.class);

	   
    private ETIN etin;

    ApplicationContext applicationContext;
   
    @SuppressWarnings("static-access")
	public  LoginResult  getLogin() throws gov.irs.mef.exception.ToolkitException, gov.irs.mef.exception.ServiceException {
    	File keyStoreFile = new File("/DT-DIGITAX-API/clientkeystore.jks");
        String keyStorePassword = "testPassword";
        String keyAlias = "digitaltaxusa";
        String keyStoreType = "PKCS12";
       
        //char[] chars = new char[keyStorePassword.length()];
       
       // applicationContext.setToolkitHome("Downloads/Version_1120210314105352/Version_11/A2A_Toolkit_v11.1/MeF_Client_SDK/Java");
        String appSysID = "digitaltax"; //TODO replace with real value
        ServiceContext context = new ServiceContext(etin, appSysID, TestCdType.T);
       
        //gov.irs.mef.ApplicationContext.setToolkitHome("Downloads/Version_1120210314105352/Version_11/A2A_Toolkit_v11.1/MeF_Client_SDK/Java");
        applicationContext.getInstance();
        File f=applicationContext.getToolkitHome();
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
