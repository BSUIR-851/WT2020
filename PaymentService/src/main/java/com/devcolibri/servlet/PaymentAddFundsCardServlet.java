package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.BankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.CardsDao;
import com.devcolibri.servlet.objects.BankAccount;
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
                "/addfundscard"
        }
)
public class PaymentAddFundsCardServlet extends HttpServlet {
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
                int userId = (int) userIdObj;
                String cardIdString = req.getParameter("cardId");
                if (cardIdString != null) {
                    try {
                        int cardId = Integer.parseInt(cardIdString);
                        CardsDao cardsDao = new CardsDao();
                        Card card = cardsDao.selectOneById(cardId);
                        req.setAttribute("card", card);

                        BankAccountsDao bankAccountsDao = new BankAccountsDao();
                        BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);
                        req.setAttribute("bankAccount", bankAccount);
                    } catch (Exception ex) {
                    }
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.getRequestDispatcher("addfundscard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int addRes = 0;
        String errmsg = null;
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

                String cardNumber = req.getParameter("card-number");
                CardsDao cardsDao = new CardsDao();
                Card card = cardsDao.selectOneByNumber(cardNumber);

                if (card == null) {
                    errmsg = "Unknown card number!";
                } else {
                    req.setAttribute("card", card);
                    if (card.getUserId() != userId) {
                        errmsg = "Unknown card number!";
                    } else {
                        String fundsAmountString = req.getParameter("funds-amount");
                        try {
                            BankAccountsDao bankAccountsDao = new BankAccountsDao();
                            BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);
                            if (bankAccount == null) {
                                errmsg = "Something gone wrong";
                            } else {
                                req.setAttribute("bankAccount", bankAccount);
                                float fundsAmount = Float.parseFloat(fundsAmountString);
                                if (fundsAmount < 0) {
                                    errmsg = "Incorrect amount!";
                                } else {
                                    bankAccount.setBalance(bankAccount.getBalance() - fundsAmount);
                                    int updateRes = bankAccountsDao.update(bankAccount);
                                    if (updateRes == 0) {
                                        errmsg = "Something gone wrong";
                                    } else {
                                        card.setBalance(card.getBalance() + fundsAmount);
                                        addRes = cardsDao.update(card);
                                        if (addRes == 0) {
                                            errmsg = "Something gone wrong!";
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            errmsg = "Incorrect amount!";
                        }
                    }
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("addRes", addRes);
            req.setAttribute("errmsg", errmsg);
            req.getRequestDispatcher("addfundscard.jsp").forward(req, resp);
        }
    }
}
