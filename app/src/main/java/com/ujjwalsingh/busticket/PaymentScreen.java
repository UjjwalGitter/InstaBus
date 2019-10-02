package com.ujjwalsingh.busticket;

import android.app.Activity;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentScreen extends AppCompatActivity {

    private PaymentsClient paymentsClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);
//        Wallet.WalletOptions walletOptions = new Wallet.WalletOptions.Builder().
//                setEnvironment(WalletConstants.ENVIRONMENT_TEST).build();
//
//        paymentsClient = Wallet.getPaymentsClient(this,walletOptions);
    }


    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }


    private static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject(){{      put("type", "PAYMENT_GATEWAY");
            put("parameters", new JSONObject(){{        put("gateway", "example");
                put("gatewayMerchantId", "exampleGatewayMerchantId");
            }
            });
        }};
    }

    private static JSONArray getAllowedCardNetworks() {
        return new JSONArray()
                .put("AMEX")
                .put("DISCOVER")
                .put("INTERAC")
                .put("JCB")
                .put("MASTERCARD")
                .put("VISA");
    }

    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }


    private static JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", getAllowedCardAuthMethods());
        parameters.put("allowedCardNetworks", getAllowedCardNetworks());
        // Optionally, you can add billing address/phone number associated with a CARD payment method.
        parameters.put("billingAddressRequired", true);

        JSONObject billingAddressParameters = new JSONObject();
        billingAddressParameters.put("format", "FULL");

        parameters.put("billingAddressParameters", billingAddressParameters);

        cardPaymentMethod.put("parameters", parameters);

        return cardPaymentMethod;
    }


    private static JSONObject getCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = getBaseCardPaymentMethod();
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());

        return cardPaymentMethod;
    }

//    public static PaymentsClient createPaymentsClient(Activity activity) {
//        Wallet.WalletOptions walletOptions =
//           //     new Wallet.WalletOptions.Builder().setEnvironment(Constants.).build();
//        return Wallet.getPaymentsClient(activity, walletOptions);
//    }


}


