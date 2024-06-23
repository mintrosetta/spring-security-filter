package vn.cloud.servlet_security_demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

// @Component
// @Order(-104) // ลำดับการทำงานของ filter, ยิ่งเลขน้อย ยิ่งได้ทำงานก่อน
@Slf4j // log4j
public class MyAPIKeyFilter implements Filter {
	
	private final String apiKey;
	
	public MyAPIKeyFilter() {
		this.apiKey = Base64.getEncoder().encodeToString("password".getBytes(StandardCharsets.UTF_8));
		log.info("ApiKey: " + this.apiKey);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest)request;
		HttpServletResponse servletResponse = (HttpServletResponse)response;
		
		// ดึงข้อมูล api key จาก header
		String apiKey = servletRequest.getHeader("x-api-key");
		
		// ถ้าหาก api key ที่ส่งมาจาก client ไม่ตรงกับในระบบ
		if (!this.apiKey.equals(apiKey)) {
			// response ด้วย error 401
			servletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "api key is invalid");
			
			// จบการทำงาน
			return;
		}
		
		// ให้ใช้งาน servlet filter ตัวถัดไป
		chain.doFilter(servletRequest, servletResponse);
	}

}
