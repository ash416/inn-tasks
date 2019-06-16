package ru.inno.stc14.servlet;

import ru.inno.stc14.dao.ConnectionManager;
import ru.inno.stc14.dao.jdbc.DBConnectionManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppContextListener implements ServletContextListener {
    private Logger logger = Logger.getLogger(AppContextListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();

        String dbURL = ctx.getInitParameter("dbURL");
        String user = ctx.getInitParameter("dbUser");
        String pwd = ctx.getInitParameter("dbPassword");

        try {
            final String dbInit = ";INIT=RUNSCRIPT FROM '" + ctx.getRealPath("/db/data.sql").replace("\\", "/") + "';";
            ConnectionManager connectionManager = new DBConnectionManager(dbURL + dbInit, user, pwd);
            ctx.setAttribute("DBConnection", connectionManager.getConnection());
            System.out.println("DB Connection initialized successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while initializing the database connection.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Connection con = (Connection) sce.getServletContext().getAttribute("DBConnection");
        try {
            con.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "An error occurred while closing the database connection.", e);
        }
    }
}
