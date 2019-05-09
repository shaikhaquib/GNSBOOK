package com.digital.gnsbook.RazorPay_payment;

import android.app.Activity;
import com.razorpay.PaymentResultListener;

public class MerchantActivity extends Activity implements PaymentResultListener {

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        /**
         * Add your logic here for a successful payment response
         */
    }

    @Override
    public void onPaymentError(int code, String response) {
        /**
         * Add your logic here for a failed payment response
         */
    }
}
