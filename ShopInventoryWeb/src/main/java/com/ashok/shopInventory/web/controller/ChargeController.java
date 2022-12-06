package com.ashok.shopInventory.web.controller;




import com.ashok.shopInventory.web.Config.ChargeRequest;
import com.ashok.shopInventory.web.Config.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Log
@Controller
@RequestMapping(ChargeController.PATH)
public class ChargeController {
    static final String PATH = "/Charge";
    @Autowired
    StripeService paymentsService;

    @PostMapping("/makePayment/{course}/{amount}")
    public String charge(ChargeRequest chargeRequest, Model model, @PathVariable("course") String course, @PathVariable("amount") String amount) throws StripeException {
        try {
            chargeRequest.setDescription("Example charge");
            chargeRequest.setCurrency(ChargeRequest.Currency.INR);
            Charge charge = paymentsService.charge(chargeRequest);
            model.addAttribute("id", charge.getId());
            model.addAttribute("status", charge.getStatus());
            model.addAttribute("chargeId", charge.getId());
            model.addAttribute("amount", amount);
            model.addAttribute("course", course);
            model.addAttribute("balance_transaction", charge.getBalanceTransaction());
            System.out.println("success");
        }
        catch (Exception e){
            model.addAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
            return "result";
        }
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        System.out.println("exception occur");
        return "result";
    }
}

