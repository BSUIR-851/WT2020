package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.BankAccountsDao;
import com.devcolibri.servlet.objects.BankAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(
        urlPatterns = {
                "/addfunds"
        }
)
public class PaymentAddFundsServlet extends HttpServlet {
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
                try {
                    int userId = (int) userIdObj;
                    BankAccountsDao bankAccountsDao = new BankAccountsDao();
                    BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);
                    req.setAttribute("bankAccount", bankAccount);
                } catch (Exception ex) {
                    isErr = true;
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.getRequestDispatcher("addfunds.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int addRes = 0;
        String errmsg = null;
        boolean isErr = false;
        BankAccount bankAccount = null;

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
                    bankAccount = bankAccountsDao.selectOneByUserId(userId);
                    if (bankAccount == null) {
                        errmsg = "Something gone wrong";
                    } else {
                        if (bankAccount.getUserId() != userId) {
                            errmsg = "Something gone wrong";
                        } else {
                            String fundsAmountString = req.getParameter("funds-amount");
                            try {
                                float fundsAmount = Float.parseFloat(fundsAmountString);
                                if (fundsAmount < 0) {
                                    errmsg = "Incorrect amount!";
                                } else {
                                    bankAccount.setBalance(bankAccount.getBalance() + fundsAmount);
                                    addRes = bankAccountsDao.update(bankAccount);
                                    if (addRes == 0) {
                                        errmsg = "Something gone wrong";
                                    }
                                }
                            } catch (Exception ex) {
                                errmsg = "Incorrect amount!";
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
            req.setAttribute("addRes", addRes);
            req.setAttribute("errmsg", errmsg);
            req.setAttribute("bankAccount", bankAccount);
            req.getRequestDispatcher("addfunds.jsp").forward(req, resp);
        }
    }
}
