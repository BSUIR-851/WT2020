package com.devcolibri.servlet;

import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.database.DaoImpl.CardsDao;
import com.devcolibri.servlet.database.DaoImpl.TransactionsDao;
import com.devcolibri.servlet.objects.Card;
import com.devcolibri.servlet.objects.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;

@WebServlet(
        urlPatterns = {
                "/maketransaction"
        }
)
public class PaymentMakeTransactionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ArrayList<Card> cards = new ArrayList<Card>();
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
                    CardsDao cardsDao = new CardsDao();
                    cards = cardsDao.selectAllByUserId(userId);
                } catch (Exception ex) {
                    isErr = true;
                }
            }
        }
        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("cards", cards);
            req.getRequestDispatcher("maketransaction.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int makeRes = 0;
        boolean isErr = false;
        String errmsg = null;
        ArrayList<Card> cards = new ArrayList<Card>();

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
                    CardsDao cardsDao = new CardsDao();
                    cards = cardsDao.selectAllByUserId(userId);
                    String scardIdString = req.getParameter("scard-id");
                    int scardId = Integer.parseInt(scardIdString);
                    Card scard = cardsDao.selectOneById(scardId);
                    if (scard == null) {
                        errmsg = "Source card does not exist!";
                    } else {
                        String fundsAmountString = req.getParameter("funds-amount");
                        float fundsAmount = Float.parseFloat(fundsAmountString);
                        if ((fundsAmount < 0) || (scard.getBalance() < fundsAmount)) {
                            errmsg = "Incorrect amount!";
                        } else {
                            String pinHash = Utils.hashString(req.getParameter("pin-scard"));
                            String cvvHash = Utils.hashString(req.getParameter("cvv-scard"));
                            if ((!pinHash.equals(scard.getPinHash())) || (!cvvHash.equals(scard.getCvvHash()))) {
                                errmsg = "Incorrect PIN or CVV!";
                            } else {
                                scard.setBalance(scard.getBalance() - fundsAmount);
                                int scardUpdateRes = cardsDao.update(scard);
                                if (scardUpdateRes == 0) {
                                    errmsg = "Something gone wrong";
                                } else {
                                    String dcardNumber = req.getParameter("dcard-number");
                                    Card dcard = cardsDao.selectOneByNumber(dcardNumber);
                                    if (dcard == null) {
                                        errmsg = "Destination card does not exist!";
                                    } else {
                                        dcard.setBalance(dcard.getBalance() + fundsAmount);
                                        int dcardUpdateRes = cardsDao.update(dcard);
                                        if (dcardUpdateRes == 0) {
                                            scard.setBalance(scard.getBalance() + fundsAmount);
                                            scardUpdateRes = cardsDao.update(scard);
                                            errmsg = "Something gone wrong";
                                        } else {
                                            TransactionsDao transactionsDao = new TransactionsDao();
                                            Date date = new Date();
                                            Timestamp ts = new Timestamp(date.getTime());
                                            Transaction transaction = new Transaction(scard.getId(), dcard.getId(), fundsAmount, ts);
                                            makeRes = transactionsDao.insert(transaction);
                                            if (makeRes == 0) {
                                                scard.setBalance(scard.getBalance() + fundsAmount);
                                                scardUpdateRes = cardsDao.update(scard);
                                                dcard.setBalance(dcard.getBalance() - fundsAmount);
                                                dcardUpdateRes = cardsDao.update(dcard);
                                                errmsg = "Something gone wrong";
                                            } else {
                                                req.setAttribute("scardNumber", scard.getNumber());
                                                req.setAttribute("dcardNumber", dcard.getNumber());
                                                req.setAttribute("fundsAmount", fundsAmount);
                                            }
                                        }
                                    }
                                }
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
            req.setAttribute("makeRes", makeRes);
            req.setAttribute("errmsg", errmsg);
            req.setAttribute("cards", cards);
            req.getRequestDispatcher("maketransaction.jsp").forward(req, resp);
        }
    }
}
