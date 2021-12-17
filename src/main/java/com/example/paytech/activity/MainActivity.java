package com.example.paytech.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.paytech.R;
import com.example.paytech.fragments.MainFragment;
import com.example.paytech.fragments.PaymentSummaryFragment;
import com.example.paytech.model.APILio;
import com.example.paytech.model.Payment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import cielo.orders.domain.CheckoutRequest;
import cielo.orders.domain.Credentials;
import cielo.orders.domain.Order;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.ServiceBindListener;
import cielo.sdk.order.payment.PaymentCode;
import cielo.sdk.order.payment.PaymentError;
import cielo.sdk.order.payment.PaymentListener;

public class MainActivity extends AppCompatActivity implements PaymentSummaryFragment.OnBtnBackListener {
    private Button btnNext,btnFinish;
    private MainFragment mainFragment;
    private PaymentSummaryFragment paymentSummaryFragment;
    Locale ptBr = new Locale("pt", "BR");
    private Payment payment;
    private APILio apiLio =  new APILio();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNext = findViewById(R.id.btn_next);
        btnFinish = findViewById(R.id.btn_finish);

        mainFragment = new MainFragment();
        paymentSummaryFragment = new PaymentSummaryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout,mainFragment);
        transaction.commit();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = mainFragment.checkComponentsEmpty();
                if(result){
                    btnNext.setVisibility(View.GONE);
                    btnFinish.setVisibility(View.VISIBLE);
                    apiLio.initSDK(getApplicationContext());
                    Bundle bundle = new Bundle();
                    payment = mainFragment.returnPayment();
                    bundle.putSerializable("DataPayment", payment );
                    paymentSummaryFragment.setArguments(bundle);
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.framelayout,paymentSummaryFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    apiLio.initOrder();
                    apiLio.addItemToPay(payment);
                    apiLio.placeOrder();
                    apiLio.payment(payment);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onBtnBack() {
        btnNext.setVisibility(View.VISIBLE);
        btnFinish.setVisibility(View.GONE);
    }

   


}
