package part1.lesson22.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = httpRequest.getContextPath() + "/login";
        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);

        if (isLoggedIn && (isLoginRequest)) {
            servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
        } else if (isLoggedIn || isLoginRequest) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.setAttribute("PageBody", "login.jsp");
            servletRequest.getRequestDispatcher("/layout.jsp")
                    .forward(servletRequest, servletResponse);
        }
    }
}
