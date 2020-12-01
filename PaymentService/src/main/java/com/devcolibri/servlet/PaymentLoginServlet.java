package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.UsersDao;
import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.database.DaoImpl.UsersRolesDao;
import com.devcolibri.servlet.objects.User;
import com.devcolibri.servlet.objects.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = {
                "/login"
        }
)
public class PaymentLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean isErr = false;
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            Object userIdObj = httpSession.getAttribute("userId");
            if (userIdObj != null) {
                isErr = true;
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errmsg = null;

        String username = req.getParameter("username");
        String pass = req.getParameter("pass");
        String passHash = Utils.hashString(pass);

        UsersDao usersDao = new UsersDao();
        User user = usersDao.selectOneByUsername(username);
        if (user == null) {
            errmsg = "User '" + username + "' not found!";
        } else {
            if (!passHash.equals(user.getPassHash())) {
                errmsg = "Password is incorrect!";
            } else {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("userId", user.getId());
                UsersRolesDao usersRolesDao = new UsersRolesDao();
                ArrayList<UserRole> userRoles = usersRolesDao.selectByUserId(user.getId());
                int privilegeLevel = Utils.getPrivilegeLevel(userRoles);
                httpSession.setAttribute("privilegeLevel", privilegeLevel);
            }
        }

        req.setAttribute("errmsg", errmsg);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}