package web.filter;

import entity.User;
import web.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static web.Constants.ROLE_USER;

@WebFilter(value = "/app/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        String[] pathTokens = uri.split("/");
        String path = pathTokens[2];
        User user = (User) request.getSession().getAttribute("user");


        if ("admin".equals(path)) {
            if (user == null) {
                response.sendRedirect("/app/login");
                return;
            }
            String authority = user.getAuthority();
            if(ROLE_USER.equals(authority)){
                response.sendError(403);
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        if("user".equals(path)){
            if (user == null) {
                response.sendRedirect("/app/login");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
