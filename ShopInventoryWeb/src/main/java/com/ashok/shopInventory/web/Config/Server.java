package com.ashok.shopInventory.web.Config;

public class Server {
//    private static Gson gson = new Gson();
//
//    static class CreatePayment {
//        @SerializedName("items")
//        Object[] items;
//
//        public Object[] getItems() {
//            return items;
//        }
//    }
//
//    static class CreatePaymentResponse {
//        private String clientSecret;
//        public CreatePaymentResponse(String clientSecret) {
//            this.clientSecret = clientSecret;
//        }
//    }
//
//    static int calculateOrderAmount(Object[] items) {
//        // Replace this constant with a calculation of the order's amount
//        // Calculate the order total on the server to prevent
//        // people from directly manipulating the amount on the client
//        return 1400;
//    }
//
//    public static void main(String[] args) {
//        port(4242);
//        staticFiles.externalLocation(Paths.get("public").toAbsolutePath().toString());
//
//        // This is your test secret API key.
//        Stripe.apiKey = "sk_test_51Lvly3SEfEGRppExb63AZIvcL5UgYaGcmjMsi0xEDH6p9588PR1vVWfCgG2ZfDDlxw10laJXyty8LCo2lS6aUBOO00LCWjKBKQ";
//
//        post("/create-payment-intent", (request, response) -> {
//            response.type("application/json");
//
//            CreatePayment postBody = gson.fromJson(request.body(), CreatePayment.class);
//            PaymentIntentCreateParams params =
//                    PaymentIntentCreateParams.builder()
//                            .setAmount(new Long(calculateOrderAmount(postBody.getItems())))
//                            .setCurrency("inr")
//                            .setAutomaticPaymentMethods(
//                                    PaymentIntentCreateParams.AutomaticPaymentMethods
//                                            .builder()
//                                            .setEnabled(true)
//                                            .build()
//                            )
//                            .build();
//
//            // Create a PaymentIntent with the order amount and currency
//            PaymentIntent paymentIntent = PaymentIntent.create(params);
//
//            CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
//            return gson.toJson(paymentResponse);
//        });
//    }
}