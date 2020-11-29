package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.BankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.BlockedBankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.CardsDao;
import com.devcolibri.servlet.database.DaoImpl.UsersDao;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.BlockedBankAccount;
import com.devcolibri.servlet.objects.Card;
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
            ""
        }
)
public class PaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            Object userIdObj = httpSession.getAttribute("userId");
            if (userIdObj != null) {
                int userId = (int) userIdObj;
                CardsDao cardsDao = new CardsDao();
                ArrayList<Card> cards = cardsDao.selectAllByUserId(userId);
                req.setAttribute("cards", cards);

                UsersDao usersDao = new UsersDao();
                User user = usersDao.selectOneById(userId);
                req.setAttribute("user", user);

                BankAccountsDao bankAccountsDao = new BankAccountsDao();
                BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);
                req.setAttribute("bankAccount", bankAccount);

                BlockedBankAccountsDao blockedBankAccountsDao = new BlockedBankAccountsDao();
                BlockedBankAccount blockedBankAccount = blockedBankAccountsDao.selectOneByBankAccountId(bankAccount.getId());
                boolean isBlocked = false;
                if (blockedBankAccount != null) {
                    isBlocked = !isBlocked;
                }
                req.setAttribute("isBlocked", isBlocked);
            }
        }
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
