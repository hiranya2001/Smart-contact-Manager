package com.example.demo.ssonhlp;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {
	
	public void removeMessageFromSession() {
		try {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			
			if (requestAttributes != null) {
				requestAttributes.getRequest().getSession().removeAttribute("message");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
