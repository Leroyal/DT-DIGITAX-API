package com.digitax.service.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitax.model.DeviceMetadata;
import com.digitax.model.User;
import com.digitax.repository.DeviceMetadataRepository;
import com.digitax.security.jwt.UserSession;
import com.digitax.service.DeviceMetadataService;

@Service("DeviceMetadataService")
public class DeviceMetadataServiceImpl implements DeviceMetadataService {
	
	@Autowired
	DeviceMetadataRepository deviceMetadataRepository;
	public void saveUserActivity(HttpServletRequest request, long userId, User user) throws SocketException {
        final String clientIpAddr = getClientIpAddr(request);
        final String clientOS = getClientOS(request);
        final String clientBrowser = getClientBrowser(request);
        final String userAgent = getUserAgent(request);
        InetAddress localHost = null;
		try {
			localHost = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        NetworkInterface ni = NetworkInterface.getByInetAddress(localHost);
        byte[] hardwareAddress = ni.getHardwareAddress();
        String[] hardwareAddressDecode = new String[hardwareAddress.length];
        if (hardwareAddress != null) {
            for (int i = 0; i < hardwareAddress.length; i++) {
            	hardwareAddressDecode[i] = String.format("%02X", hardwareAddress[i]);
            }
            System.out.println(String.join("-", hardwareAddressDecode));
        }
        DeviceMetadata deviceDetails = deviceMetadataRepository.findByUniqueSessionKey(user.getUsername()+String.join("-", hardwareAddressDecode)+clientBrowser);
        DeviceMetadata deviceDetailsObj =new DeviceMetadata();
        
        deviceDetailsObj.setClientBrowser(clientBrowser);
        deviceDetailsObj.setHardwareAddress(String.join("-", hardwareAddressDecode));
        deviceDetailsObj.setUserAgent(userAgent);
        deviceDetailsObj.setClientIpAddr(clientIpAddr);
        deviceDetailsObj.setClientOS(clientOS);
        deviceDetailsObj.setIsLoggedIn(true);
        deviceDetailsObj.setUserName(user.getUsername());
        deviceDetailsObj.setEmail(user.getEmail());
        deviceDetailsObj.setPhone(user.getPhone());
        deviceDetailsObj.setUniqueSessionKey(user.getUsername()+String.join("-", hardwareAddressDecode)+clientBrowser);
        
        if(deviceDetails != null) {
        	System.out.println("deviceDetails");
        	System.out.println(deviceDetails);
        	deviceDetails.setUpdatedAt(System.currentTimeMillis());
        	deviceDetails.setClientBrowser(clientBrowser);
        	deviceDetails.setHardwareAddress(String.join("-", hardwareAddressDecode));
        	deviceDetails.setUserAgent(userAgent);
        	deviceDetails.setClientIpAddr(clientIpAddr);
        	deviceDetails.setClientOS(clientOS);
        	deviceDetails.setIsLoggedIn(true);
        	deviceDetails.setUserName(user.getUsername());
        	deviceDetails.setEmail(user.getEmail());
        	deviceDetails.setPhone(user.getPhone());
        	deviceMetadataRepository.save(deviceDetails);
        }
        else
        {
        	deviceDetailsObj.setUserId(UserSession.getUserId());
        	deviceDetailsObj.setCreatedAt(System.currentTimeMillis());
        	deviceMetadataRepository.save(deviceDetailsObj);
        }
        
        
        System.out.println("\n" +
                            "User Agent \t" + userAgent + "\n" +
                            "Operating System\t" + clientOS + "\n" +
                            "Browser Name\t" + clientBrowser + "\n" +
                            "IP Address\t" + clientIpAddr + "\n");
    }


   

    public String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public String getClientOS(HttpServletRequest request) {
        final String browserDetails = request.getHeader("User-Agent");

        //=================OS=======================
        final String lowerCaseBrowser = browserDetails.toLowerCase();
        if (lowerCaseBrowser.contains("windows")) {
            return "Windows";
        } else if (lowerCaseBrowser.contains("mac")) {
            return "Mac";
        } else if (lowerCaseBrowser.contains("x11")) {
            return "Unix";
        } else if (lowerCaseBrowser.contains("android")) {
            return "Android";
        } else if (lowerCaseBrowser.contains("iphone")) {
            return "IPhone";
        } else {
            return "UnKnown, More-Info: " + browserDetails;
        }
    }

    public String getClientBrowser(HttpServletRequest request) {
        final String browserDetails = request.getHeader("User-Agent");
        final String user = browserDetails.toLowerCase();

        String browser = "";

        //===============Browser===========================
        if (user.contains("msie")) {
            String substring = browserDetails.substring(browserDetails.indexOf("MSIE")).split(";")[0];
            browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Safari")).split(" ")[0]).split(
                    "/")[0] + "-" + (browserDetails.substring(
                    browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if (user.contains("opr") || user.contains("opera")) {
            if (user.contains("opera"))
                browser = (browserDetails.substring(browserDetails.indexOf("Opera")).split(" ")[0]).split(
                        "/")[0] + "-" + (browserDetails.substring(
                        browserDetails.indexOf("Version")).split(" ")[0]).split("/")[1];
            else if (user.contains("opr"))
                browser = ((browserDetails.substring(browserDetails.indexOf("OPR")).split(" ")[0]).replace("/",
                                                                                                           "-")).replace(
                        "OPR", "Opera");
        } else if (user.contains("chrome")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1) || (user.indexOf(
                "mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf(
                "mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1)) {
            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
            browser = "Netscape-?";

        } else if (user.contains("firefox")) {
            browser = (browserDetails.substring(browserDetails.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("rv")) {
            browser = "IE";
        } else {
            browser = "UnKnown, More-Info: " + browserDetails;
        }

        return browser;
    }

    public String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }




	

}
