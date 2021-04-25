package com.example.demo.common;

import com.example.demo.exception.NotAllowedException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HeaderRequest {
    public boolean isAdmin(HttpServletRequest request) {
        String header = request.getHeader("X-User-Role");
        if (header==null) {
            throw new NotAllowedException("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        return header.equals("admin");
    }

    public String getUserId(HttpServletRequest request) {
        return request.getHeader("X-User-Id");
    }

}
