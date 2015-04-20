/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2730.finalProject.web;

import edu.pitt.sis.infsci2730.finalProject.model.CustomerDBModel;
import edu.pitt.sis.infsci2730.finalProject.model.TransactionDBModel;
import edu.pitt.sis.infsci2730.finalProject.service.TransactionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wu
 */
@Controller
@RequestMapping("/orderhistory")
public class OrderHistoryController {

    private TransactionService transactionServie = new TransactionService();

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView mypage(HttpSession session) {
        try {
            CustomerDBModel customer = (CustomerDBModel) session.getAttribute("customer");
            Map<String, Object> myModel = new HashMap<String, Object>();
            if (customer == null) {
                return new ModelAndView("index");
            } else {
                List<TransactionDBModel> l = this.transactionServie.GetTransactionByCustomerID(customer.getCustomer_id()+"");
                for (int i = 0; i < l.size(); i++) {
                    TransactionDBModel t = l.get(i);
                    t.setTotoalAmount(this.transactionServie.GetTranactionTotalAmount(t.getTransaction_id()+""));
                }
                myModel.put("transactionList", l);
                return new ModelAndView("orderHistory", "modelMap", myModel);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderHistoryController.class.getName()).log(Level.SEVERE, null, ex);
            return new ModelAndView("500");
        }
    }
}
