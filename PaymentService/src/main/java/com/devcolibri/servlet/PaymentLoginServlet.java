package com.devcolibri.servlet;

import com.devcolibri.servlet.database.UsersDao;
import com.devcolibri.servlet.model.Utils;
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
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
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
                req.setAttribute("errmsg", "Password is incorrect!");
            }
        } else {
            req.setAttribute("errmsg", "User '" + username + "' not found!");
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}