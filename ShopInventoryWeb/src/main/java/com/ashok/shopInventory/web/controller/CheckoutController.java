package com.ashok.shopInventory.web.controller;





import com.ashok.shopInventory.web.Config.ChargeRequest;
import com.ashok.shopInventory.web.Config.DateUtils;
import com.ashok.shopInventory.web.service.ICourseServiceImpl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Controller
@RequestMapping(CheckoutController.PATH)
public class CheckoutController {
    static final String PATH = "/Checkout";
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private ICourseServiceImpl courseService;

    public static final String DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final String CACHE_CONTROL = "Cache-Control";
    public static final String PRAGMA = "Pragma";
    public static final String EXPIRES = "Expires";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String NO_CACHE = "no-cache";
    public static final String MAX_AGE_2592000_PUBLIC = "max-age=2592000;public";
    public static final String PNG = "png";
    public static final String IMAGE_PNG = "image/png";

    @RequestMapping(path ="/Buy/{course}")
    public String Buy(Model model,@PathVariable("course") String course) {
        Double amount = 0.0;
        String QrCodeData="";
        amount =courseService.getCourseAmount(course);
        try {
            if(amount==null)
                amount=0.0;
            QrCodeData = createQrCodeData(amount, course);
        }catch (Exception e){

        }
        model.addAttribute("amount",amount);
        model.addAttribute("finalAmount",amount*100);
        model.addAttribute("course",course);
        model.addAttribute("qrCodeData",QrCodeData);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.INR);
        return "paymentIndex";
    }
    @RequestMapping(path = "/makePayment/{course}/{amount}",method = RequestMethod.POST)
    public String MakePayment(Model model,@PathVariable("course") String course,@PathVariable("amount") double amount) {
        return "";
    }


    private String createQrCodeData(Double amount, String course) throws UnsupportedEncodingException {
//        String url = "upi://pay?pa=" +   // payment method.
//                "9982306229@ybl" +         // VPA number.
//                "&am="+ amount +       // this param is for fixed amount (non editable).
//                "&pn=Ashok Kumar"+      // to showing your name in app.
//                "&cu=INR";                 // Currency code.
                //"&mode=02" +                 // mode O2 for Secure QR Code.//If the transaction is initiated by any PSP app then the respective orgID needs to be passed.
                //"&sign=QXNob2s";   // Base 64 encoded Digital signature needs to be passed in this ta
        String url = "upi://pay?pa=" +   // payment method.
                "9982306229@ybl" +         // VPA number.
                "&am="+ amount +       // this param is for fixed amount (non editable).
                "&pn=Ashok%20Kumar"+      // to showing your name in app.
                "&cu=INR" +                  // Currency code.
                "&mode=02" +                 // mode O2 for Secure QR Code.
                //If the transaction is initiated by any PSP app then the respective orgID needs to be passed.
                "&sign=QXNob2sgS3VtYXI=";  // Base 64 encoded Digital signature needs to be passed in this tag
        return URLEncoder.encode(url, String.valueOf(StandardCharsets.UTF_8));
    }

    @RequestMapping("/manual/view/getQRCode")
    public void getQRCode(@RequestParam(required = false, value = "code") String code, HttpServletResponse response) throws IOException, WriterException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(code, BarcodeFormat.QR_CODE, 1000, 1000);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream);

        response.setHeader("Cache-Control", "max-age=2592000;public");
        response.setHeader("Pragma", "no-cache");
        response.setHeader(EXPIRES, dateFormat.format(DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DAY_OF_MONTH, 1)));
        response.setContentType("image/png");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(outputStream.toByteArray());
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}