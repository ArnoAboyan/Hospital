package filter;

import entitys.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.io.IOException;

//filter check role doctor
@WebFilter(urlPatterns = {"/admin.jsp" ,"/appointmentlist.jsp", "/patientlist.jsp", "/hospitalcardfornurse.jsp", "/hospitalcardlist.jsp", "/patientlistbydoctoradmin.jsp"})
public class RoleManagerFilter implements Filter {

    static final Logger logger = Logger.getLogger(CheckRigisterUserFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Doctor currentDoctor = (Doctor) req.getSession().getAttribute("currentUser");
        logger.info("Get current user = " + currentDoctor);
        HttpServletResponse resp = (HttpServletResponse) response;

        if(currentDoctor==null) {
            resp.sendRedirect("index.jsp");
        } else {

            String role = currentDoctor.getRole().getTitle();

            if (!role.equalsIgnoreCase("doctor")) {
                logger.error("User rol not doctor");
                chain.doFilter(req, response);


            } else {
                logger.info("Doctor find and conformed ... OK ");
                resp.sendRedirect("index.jsp");
            }
        }
    }

}
