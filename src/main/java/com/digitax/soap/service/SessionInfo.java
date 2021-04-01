package com.digitax.soap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;



import com.digitax.util.LogUtil;

public class SessionInfo {
	private Map<String, String> savedCookies;
	private Element SAMLToken;
	private static final Logger logger = LogUtil.getLogger(SessionInfo.class.getName());

	Map<String, List<String>> getSessionCookieValue() {
		// Transform saved cookie map into http cookie header
		if( this.savedCookies == null){
			return null;
		}
		Map<String, List<String>> cookieHeader = new HashMap<String, List<String>>();
		List<String> cookies = new ArrayList<String>(1);
		StringBuilder sbuf = new StringBuilder();
		Set<Entry<String,String>> entrySet = this.savedCookies.entrySet();
		boolean firstTime = true;
		for(Entry<String,String> entry : entrySet){
			if( !firstTime){
				sbuf.append("; ");
			}
			String cookie = entry.getKey() + "=" + entry.getValue();
			sbuf.append(cookie);
			firstTime = false;
		}
		String outCookie = new String(sbuf);
		logger.info("Outbound cookie: "+ outCookie);
		cookies.add(outCookie);
		cookieHeader.put("Cookie", cookies);
		return cookieHeader;
	}

	
	void setSessionCookieValue(List<String> anIncomingCookieList) {
		if (anIncomingCookieList != null) {
			synchronized (this) {
				if (this.savedCookies == null) {
					this.savedCookies = new HashMap<String, String>();
				}
			}
			
			for (String cookie : anIncomingCookieList) {
				logger.info("Inbound cookie: "+ cookie);
				StringTokenizer st = new StringTokenizer(cookie, ";");
				while(st.hasMoreTokens()) {
					String nameValuePair = st.nextToken();
					if(nameValuePair.length() >= 3){
						// Minimum name-value pair is "a=b"
						StringTokenizer st2 = new StringTokenizer(nameValuePair, "=");
						while(st2.hasMoreTokens()){
							String name  = st2.nextToken();
							String value = st2.nextToken();
							if( (name != null) && (value != null) ){
								savedCookies.put(name, value);
							}
						}
					}
					break; 
				}
			}
		}
	}
	
	
	public void setSAMLToken(Element samlElement) {
		this.SAMLToken = samlElement;
	}
	
	
	public Element getSAMLToken() {
		return this.SAMLToken;
	}
}
