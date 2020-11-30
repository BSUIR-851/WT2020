package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.UsersDao;
import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.objects.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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
        if (user != null) {
            if (passHash.equals(user.getPassHash())) {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("userId", user.getId());
            } else {
                errmsg = "Password is incorrect!";
            }
        } else {
            errmsg = "User '" + username + "' not found!";
        }

        req.setAttribute("errmsg", errmsg);
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}