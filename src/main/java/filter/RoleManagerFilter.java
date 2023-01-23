package filter;

import entitys.Doctor;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

//filter check role user
@WebFilter(urlPatterns = {"/admin.jsp" ,"/appointmentlist.jsp", "/patientlist.jsp"})
public class RoleManagerFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Doctor currentDoctor = (Doctor) req.getSession().getAttribute("currentUser");
        HttpServletResponse resp = (HttpServletResponse) response;

        if(currentDoctor==null) {
            resp.sendRedirect("index.jsp");
        } else {

            String role = currentDoctor.getRole().getTitle();

            if (!role.equalsIgnoreCase("doctor")) {
                chain.doFilter(req, response);


            } else {
                resp.sendRedirect("index.jsp");
            }
        }
    }

}
