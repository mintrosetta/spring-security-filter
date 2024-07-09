package vn.cloud.servlet_security_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import jakarta.servlet.DispatcherType;

@SpringBootApplication
public class ServletSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletSecurityDemoApplication.class, args);
	}
	
	@Bean
	FilterRegistrationBean<MyAPIKeyFilter> myAPIKeyFilter() {
		MyAPIKeyFilter apiKeyFilter = new MyAPIKeyFilter();
		
		FilterRegistrationBean<MyAPIKeyFilter> filterBean = new FilterRegistrationBean<>(apiKeyFilter);
		
		/*
		
			ใช้การกำหนดการจัดการ (dispatch) ที่ฟิลเตอร์จะทำงาน 
			โดยจะมีประเภทดังนี้
				1. REQUEST	-> เมื่อเป็น request ปกติ
				2. FORWARD	-> เมื่อเป็น forward request
				3. INCLUDE	-> เมือเป็น include request
				4. ERROR 	-> เมื่อเกิดข้อผิดพลาด
				5. ASYNC	-> ทำงานในสถาณการณ์แบบ asynchronous
		 
		*/
		filterBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR); // สั่งให้ filter นี้ทำงานเมื่อ client ส่งคำขอมา
		filterBean.addUrlPatterns("/api/*"); // สั่งให้ filter ทำงานเมื่อรูปแบบของ url ตรงกับที่ระบุ
		filterBean.setOrder(-104); // สั่งให้ filter ไปอยู่ที่ order -104
		
		return filterBean;
	}

}

// next: https://youtu.be/RBnDv3uZuBQ?si=5fpDG9N735SgGp6e
