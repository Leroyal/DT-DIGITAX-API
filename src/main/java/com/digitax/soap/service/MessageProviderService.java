package com.digitax.soap.service;

//import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


import com.digitax.soapschema.Message;


@Service
public class MessageProviderService {
	private static final Map<String, Message> messages= new HashMap<>();
	
	@PostConstruct
    public void initData() {
        		  
		  Message mess=new Message();
		  mess.setMessageID(1);
		  mess.setETIN("1245");
		  mess.setSessionIndicator("Y");
		  mess.setTestIndicator("T");
		  mess.setAppSysID("131");
		 messages.put(mess.getETIN(),mess);
		 Message mes=new Message();
		 mes.setMessageID(2);
		 mes.setETIN("1368");
		 mes.setSessionIndicator("N");
		 mes.setTestIndicator("P");
		 mes.setAppSysID("132");
		  messages.put(mes.getETIN(),mes);
		  Message messa=new Message();
		  messa.setMessageID(3);
		  messa.setETIN("1725");
		  messa.setSessionIndicator("N");
		  messa.setTestIndicator("P");
		  messa.setAppSysID("134");
		  messages.put(messa.getETIN(),messa);
	  }
	  public Message findEtin(String etin) {
			Assert.notNull(etin, "The mef etin must not be null");
			return messages.get(etin);
		}

}
