package com.devcolibri.servlet;

import com.devcolibri.servlet.database.BankAccountsDao;
import com.devcolibri.servlet.database.BlockedBankAccountsDao;
import com.devcolibri.servlet.database.CardsDao;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.BlockedBankAccount;
import com.devcolibri.servlet.objects.Card;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = {
                "/blockbankaccount"
        }
)
public class PaymentBlockBankAccountServlet extends HttpServlet {

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
            }
        }
        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.getRequestDispatcher("blockbankaccount.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int blockRes = 0;
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
                BankAccountsDao bankAccountsDao = new BankAccountsDao();
                BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);

                BlockedBankAccountsDao blockedBankAccountsDao = new BlockedBankAccountsDao();
                BlockedBankAccount blockedBankAccount = new BlockedBankAccount(bankAccount.getId());
                blockRes = blockedBankAccountsDao.insert(blockedBankAccount);
                if (blockRes == 0) {
                    String errmsg = "This bank account is already blocked";
                    req.setAttribute("errmsg", errmsg);
                }
                req.setAttribute("bankAccount", bankAccount);
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("blockRes", blockRes);
            req.getRequestDispatcher("blockbankaccount.jsp").forward(req, resp);
        }
    }
}