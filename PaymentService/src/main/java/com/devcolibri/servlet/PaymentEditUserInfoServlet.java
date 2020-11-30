package com.devcolibri.servlet;

import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.database.DaoImpl.UsersDao;
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
                "/edituserinfo"
        }
)
public class PaymentEditUserInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = null;
        boolean isErr = false;
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            isErr = true;
        } else {
            Object userIdObj = httpSession.getAttribute("userId");
            if (userIdObj == null) {
                isErr = true;
            } else {
                int userId = (int) userIdObj;
                UsersDao usersDao = new UsersDao();
                user = usersDao.selectOneById(userId);
                if (user == null) {
                    isErr = true;
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("user", user);
            req.getRequestDispatcher("edituserinfo.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int editRes = 0;
        String errmsg = null;
        boolean isErr = false;
        User user = null;

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            isErr = true;
        } else {
            Object userIdObj = httpSession.getAttribute("userId");
            if (userIdObj == null) {
                isErr = true;
            } else {
                try {
                    int userId = (int) userIdObj;
                    UsersDao usersDao = new UsersDao();
                    user = usersDao.selectOneById(userId);
                    String username = req.getParameter("username");
                    if (!username.equals("")) {
                        user.setUsername(username);
                    }

                    String email = req.getParameter("email");
                    if (!email.equals("")) {
                        user.setEmail(email);
                    }

                    String pass = req.getParameter("pass");
                    if (!pass.equals("")) {
                        user.setPassHash(Utils.hashString(pass));
                    }

                    String firstName = req.getParameter("first-name");
                    if (!firstName.equals("")) {
                        user.setFirstName(firstName);
                    }

                    String lastName = req.getParameter("last-name");
                    if (!lastName.equals("")) {
                        user.setLastName(lastName);
                    }
                    editRes = usersDao.update(user);
                } catch (Exception ex) {
                    isErr = true;
                }
            }
        }
        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("editRes", editRes);
            req.setAttribute("errmsg", errmsg);
            req.setAttribute("user", user);
            req.getRequestDispatcher("edituserinfo.jsp").forward(req, resp);
        }
    }
}
