package com.devcolibri.servlet;

import com.devcolibri.servlet.database.BankAccountsDao;
import com.devcolibri.servlet.database.UsersDao;
import com.devcolibri.servlet.database.UsersRolesDao;
import com.devcolibri.servlet.model.Utils;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.User;
import com.devcolibri.servlet.objects.UserRole;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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

        UsersDao usersDao = new UsersDao();
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        String passHash = Utils.hashString(pass);
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        User user = new User(username, email, passHash, firstName, lastName);
        int userId = usersDao.insert(user);

        UsersRolesDao usersRolesDao = new UsersRolesDao();
        UserRole userRole = new UserRole(userId, UsersRolesDao.USER_ID);
        int userRoleId = usersRolesDao.insert(userRole);


        int regRes = userId & userRoleId;
        if (regRes == 0) {
            req.setAttribute("errmsg", "Please, fill your data correctly (user with this username or email is exists)");
        } else {
            BankAccountsDao bankAccountsDao = new BankAccountsDao();
            BankAccount bankAccount = new BankAccount(userId, 0F, Utils.generateBankAccountNumber(userId));
            int bankAccountId = bankAccountsDao.insert(bankAccount);
        }
        req.setAttribute("regRes", regRes);
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }
}