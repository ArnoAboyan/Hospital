package filter;

import entitys.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//filter register
@WebFilter(urlPatterns = {"/admin.jsp" ,"/appointmentlist.jsp", "/hospitalcard.jsp", "/patientlist.jsp",
        "/patientlistbydoctor.jsp"})
public class CheckRigisterUserFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        Doctor currentDoctor = (Doctor) req.getSession().getAttribute("currentUser");
//            logger.info("CheckRigisterUserFilter get current user = " + currentUser);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;


        if (currentDoctor != null) {
            filterChain.doFilter(req, resp);
        } else {
            resp.sendRedirect("index.jsp");
        }

    }
}

