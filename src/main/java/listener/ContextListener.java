package listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
//        logger.info("ContextListener#contextInitialized get Servlet context = " + ctx);


        // file name with locales descriptions
        String localesFileName = ctx.getInitParameter("locales");

        // real path on server
        String localesFileRealPath = ctx.getRealPath(localesFileName);

        // local descriptions
        Properties locales = new Properties();
        try {
            locales.load(new FileInputStream(localesFileRealPath));
        } catch (IOException e) {
//            logger.error("ContextListener#contextInitialized get error IOException = " + e);
        }

        // save descriptions to servlet context
        ctx.setAttribute("locales", locales);

//        logger.debug("locales ==> " + locales);
    }
}
