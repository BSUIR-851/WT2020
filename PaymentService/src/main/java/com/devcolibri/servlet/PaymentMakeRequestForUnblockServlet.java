package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.BankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.BlockedBankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.RequestsForUnblockDao;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.BlockedBankAccount;
import com.devcolibri.servlet.objects.RequestForUnblock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = {
                "/requestforunblock"
        }
)
public class PaymentMakeRequestForUnblockServlet extends HttpServlet {

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
            req.getRequestDispatcher("requestforunblock.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int unblockRes = 0;
        boolean isErr = false;
        String errmsg = null;
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
                    BankAccountsDao bankAccountsDao = new BankAccountsDao();
                    BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);
                    if (bankAccount == null) {
                        errmsg = "Something gone wrong!";
                    } else {
                        req.setAttribute("bankAccount", bankAccount);
                        BlockedBankAccountsDao blockedBankAccountsDao = new BlockedBankAccountsDao();
                        BlockedBankAccount blockedBankAccount = blockedBankAccountsDao.selectOneByBankAccountId(bankAccount.getId());
                        if (blockedBankAccount == null) {
                            errmsg = "This bank account is not blocked!";
                        } else {
                            RequestsForUnblockDao requestsForUnblockDao = new RequestsForUnblockDao();
                            RequestForUnblock requestForUnblock = new RequestForUnblock(blockedBankAccount.getId());
                            unblockRes = requestsForUnblockDao.insert(requestForUnblock);
                            if (unblockRes == 0) {
                                errmsg = "The request was already sent!";
                            }
                        }
                    }
                } catch (Exception ex) {
                    isErr = true;
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("unblockRes", unblockRes);
            req.setAttribute("errmsg", errmsg);
            req.getRequestDispatcher("requestforunblock.jsp").forward(req, resp);
        }
    }
}