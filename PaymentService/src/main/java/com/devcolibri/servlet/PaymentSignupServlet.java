package com.devcolibri.servlet;

import com.devcolibri.servlet.database.UsersDao;
import com.devcolibri.servlet.database.UsersRolesDao;
import com.devcolibri.servlet.objects.User;
import com.devcolibri.servlet.objects.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = {
            "/signup"
        }
)
public class PaymentSignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("regRes", 0);
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();

        try {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String passHash = req.getParameter("pass");
            String firstName = req.getParameter("first-name");
            String lastName = req.getParameter("last-name");
            User user = new User(username, email, passHash, firstName, lastName);

            UsersDao usersDao = new UsersDao();
            int userId = usersDao.insert(user);

            UsersRolesDao usersRolesDao = new UsersRolesDao();
            UserRole userRole = new UserRole(userId, UsersRolesDao.USER_ID);
            int userRoleId = usersRolesDao.insert(userRole);

            req.setAttribute("regRes", userId & userRoleId);
            req.getRequestDispatcher("signup.jsp").forward(req, resp);

        } catch(Exception ex) {
            pw.println("Connection to BD failed..");
            pw.println(ex);

        } finally {
            pw.close();
        }
    }
}