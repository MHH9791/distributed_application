package servlets;

import entities.UserEntity;
import jakarta.persistence.Query;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.List;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/login.xhtml")

public class LoginFilter implements Filter {
    public LoginFilter(){

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login.xhtml";
        boolean loggedIn = session != null && session.getAttribute("email") !=null;
        boolean loginRequest = request.getRequestURI().equals(loginURI);

        if(loggedIn || loginRequest){
            filterChain.doFilter(request,response);
        }else{
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {

    }

    public static String decrypt(String encryptKey){
        BASE64Decoder decoder = new BASE64Decoder();
        try{
            return new String(decoder.decodeBuffer(encryptKey));
        }catch (IOException e){

        }
        return null;
    }
}
