package com.devcolibri.servlet;

import com.devcolibri.servlet.database.DaoImpl.BankAccountsDao;
import com.devcolibri.servlet.database.DaoImpl.CardsDao;
import com.devcolibri.servlet.Utils.Utils;
import com.devcolibri.servlet.objects.BankAccount;
import com.devcolibri.servlet.objects.Card;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

@WebServlet(
        urlPatterns = {
                "/createcard"
        }
)
public class PaymentCreateCardServlet extends HttpServlet {

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
            req.getRequestDispatcher("createcard.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int createRes = 0;
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

                    String number = Utils.generateCardNumber(userId, bankAccount.getId());

                    String pin = Utils.generateRandomNumericString(4);
                    String pinHash = Utils.hashString(pin);
                    req.setAttribute("pin", pin);

                    String cvv = Utils.generateRandomNumericString(3);
                    String cvvHash = Utils.hashString(cvv);
                    req.setAttribute("cvv", cvv);

                    java.util.Date date = new java.util.Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.YEAR, 2);
                    date = cal.getTime();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    Card card = new Card(userId, bankAccount.getId(), number, 0f, pinHash, cvvHash, sqlDate);
                    CardsDao cardsDao = new CardsDao();
                    createRes = cardsDao.insert(card);
                    req.setAttribute("card", card);
                } catch (Exception ex) {
                    isErr = true;
                }
            }
        }

        if (isErr) {
            resp.sendRedirect(req.getContextPath());
        } else {
            req.setAttribute("createRes", createRes);
            req.getRequestDispatcher("createcard.jsp").forward(req, resp);
        }
    }
}
