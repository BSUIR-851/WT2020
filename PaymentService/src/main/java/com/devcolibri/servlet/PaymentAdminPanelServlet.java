package com.devcolibri.servlet;

import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.database.DaoImpl.RequestsForUnblockDao;
import com.devcolibri.servlet.database.DaoImpl.UsersDao;
import com.devcolibri.servlet.database.DaoImpl.UsersRolesDao;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.RequestForUnblock;
import com.devcolibri.servlet.objects.User;

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
                "/adminpanel"
        }
)
public class PaymentAdminPanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        boolean isErr = false;
        HttpSession httpSession = req.getSession(false);
        if (httpSession == null) {
            isErr = true;
        } else {
            Object userIdObj = httpSession.getAttribute("userId");
            if (userIdObj == null) {
                isErr = true;
            } else {
                Object privilegeLevelObj = httpSession.getAttribute("privilegeLevel");
                if (privilegeLevelObj == null) {
                    isErr = true;
                } else {
                    try {
                        int privilegeLevel = (int) privilegeLevelObj;
                        if (privilegeLevel != UsersRolesDao.USER_VALUE + UsersRolesDao.ADMIN_VALUE) {
                            isErr = true;
                        } else {
                            UsersDao usersDao = new UsersDao();
                            ArrayList<User> users = usersDao.selectAll();
                            req.setAttribute("users", users);

                            RequestsForUnblockDao requestsForUnblockDao = new RequestsForUnblockDao();
                            ArrayList<RequestForUnblock> requestsForUnblock = requestsForUnblockDao.selectAll();
                            ArrayList<BankAccount> requestedBlockedBankAccounts = Utils.getRequestedBlockedAccounts(requestsForUnblock);
                            req.setAttribute("requestedBlockedBankAccounts", requestedBlockedBankAccounts);
                        }
                    } catch (Exception ex) {
                        isErr = true;
                    }
                }
            }
        }
        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.getRequestDispatcher("adminpanel.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    }
}
