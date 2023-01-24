package filter;

import DAO.impl.DoctorDao;
import entitys.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

//filter register
@WebFilter(urlPatterns = {"/admin.jsp" ,"/appointmentlist.jsp", "/hospitalcard.jsp", "/patientlist.jsp",
        "/patientlistbydoctor.jsp", "/hospitalcardlist.jsp", "/hospitalcardfornurse.jsp", "/patientlistbydoctoradmin.jsp"})
public class CheckRigisterUserFilter implements Filter {


    static final Logger logger = Logger.getLogger(CheckRigisterUserFilter.class);
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
            logger.info("Get current user = " + currentDoctor);
        HttpServletResponse resp = (HttpServletResponse) servletResponse;


        if (currentDoctor != null) {
            logger.info("User find and conformed ... OK ");
            filterChain.doFilter(req, resp);
        } else {
            logger.error("User == null");
            resp.sendRedirect("index.jsp");
        }

    }
}

