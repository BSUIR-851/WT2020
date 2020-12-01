package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.BankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.UsersDao;
import com.devcolibri.servlet.database.DaoImpl.UsersRolesDao;
import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.User;
import com.devcolibri.servlet.objects.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = {
            "/signup"
        }
)
public class PaymentSignupServlet extends HttpServlet {

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
            req.setAttribute("regRes", 0);
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String errmsg = null;

        UsersDao usersDao = new UsersDao();
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        String passHash = Utils.hashString(pass);
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        User user = new User(username, email, passHash, firstName, lastName);
        int userId = usersDao.update(user);

        UsersRolesDao usersRolesDao = new UsersRolesDao();
        UserRole userRole = new UserRole(userId, UsersRolesDao.USER_ID);
        int userRoleId = usersRolesDao.insert(userRole);

        int regRes = userId * userRoleId;
        if (regRes == 0) {
            errmsg = "Please, fill your data correctly (user with this username or email is exists)";
        } else {
            BankAccountsDao bankAccountsDao = new BankAccountsDao();
            String bankAccountNumber = Utils.generateBankAccountNumber(userId);
            BankAccount bankAccount = new BankAccount(userId, 0F, bankAccountNumber);
            int bankAccountId = bankAccountsDao.insert(bankAccount);
        }
        req.setAttribute("regRes", regRes);
        req.setAttribute("errmsg", errmsg);
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }
}