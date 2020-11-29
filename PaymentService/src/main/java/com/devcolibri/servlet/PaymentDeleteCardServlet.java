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
                "/deletecard"
        }
)
public class PaymentDeleteCardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String cardIdString = req.getParameter("cardId");
        if (cardIdString != null) {
            try {
                int cardId = Integer.parseInt(cardIdString);
                CardsDao cardsDao = new CardsDao();
                Card card = cardsDao.selectOneById(cardId);
                req.setAttribute("card", card);
            } catch (Exception ex){
            }
        }
        req.getRequestDispatcher("deletecard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int deleteRes = 0;
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
                    String errmsg = "Unknown card number!";
                    req.setAttribute("errmsg", errmsg);
                } else {
                    if (card.getUserId() != userId) {
                        String errmsg = "Unknown card number!";
                        req.setAttribute("errmsg", errmsg);
                    } else {
                        BankAccountsDao bankAccountsDao = new BankAccountsDao();
                        BankAccount bankAccount = bankAccountsDao.selectOneByUserId(userId);
                        if (bankAccount == null) {
                            String errmsg = "Something gone wrong!";
                            req.setAttribute("errmsg", errmsg);
                        } else {
                            bankAccount.setBalance(bankAccount.getBalance() + card.getBalance());
                            int updateRes = bankAccountsDao.update(bankAccount);
                            if (updateRes == 0) {
                                String errmsg = "Something gone wrong!";
                                req.setAttribute("errmsg", errmsg);
                            } else {
                                deleteRes = cardsDao.deleteByNumber(cardNumber);
                                if (deleteRes == 0) {
                                    String errmsg = "Something gone wrong!";
                                    req.setAttribute("errmsg", errmsg);
                                } else {
                                    req.setAttribute("deletedCardNumber", cardNumber);
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("deleteRes", deleteRes);
            req.getRequestDispatcher("deletecard.jsp").forward(req, resp);
        }
    }
}
