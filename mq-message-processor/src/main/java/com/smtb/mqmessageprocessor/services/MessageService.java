package com.smtb.mqmessageprocessor.services;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
	public String fetchMessageFromRestCall(){
		return "this is a dummy text!";
	}
}
