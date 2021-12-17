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

    public void pay(Payment payment, Context context){
        Credentials credentials = new Credentials("Iih0VVhzeXMULuY8OjYiwlYfZr1Ie5Y87oONuNYGaNmWqs9pru/ 0mHGPra3EvOFp1VGPRiDNR8bpev8LjhXP3LgWk0Dy7mzBUsIhd","WvqcDlISdJEUTa0XtJpycQ2eeM6a9bNUFv1XfEMOc59hmRoYLs");
        OrderManager orderManager = new OrderManager(credentials, context);

        ServiceBindListener serviceBindListener = new ServiceBindListener() {

            @Override
            public void onServiceBoundError(Throwable throwable) {
                Log.e("onServiceBoundError", throwable.getMessage());
                //Ocorreu um erro ao tentar se conectar com o serviço OrderManager
            }

            @Override
            public void onServiceBound() {
                Log.e("onServiceBound", "Conectado");
                //Você deve garantir que sua aplicação se conectou com a LIO a partir desse listener
                //A partir desse momento você pode utilizar as funções do OrderManager, caso contrário uma exceção será lançada.
            }

            @Override
            public void onServiceUnbound() {
                Log.e("onServiceUnBound", "Desconectado");
                // O serviço foi desvinculado
            }
        };

        orderManager.bind(context, serviceBindListener);

        Order order = orderManager.createDraftOrder(UUID.randomUUID().toString());


        String valueFormat = NumberFormat.getCurrencyInstance(ptBr).format(payment.getValueProduct());
        valueFormat = valueFormat.substring(3);
        valueFormat = valueFormat.replace(",", "");
        long valueProductLong = Long.parseLong(valueFormat);
        order.addItem("1", payment.getDescription(), valueProductLong, 1,"UNIDADE");

        orderManager.placeOrder(order);

        PaymentListener paymentListener = new PaymentListener() {
            @Override
            public void onStart() {
                Log.d("SDKClient", "O pagamento começou.");
            }

            @Override
            public void onPayment(@NonNull  Order order) {
                Log.d("SDKClient", "Um pagamento foi realizado.");
            }

            @Override public void onCancel() {
                Log.d("SDKClient", "A operação foi cancelada.");
            }

            @Override public void onError(@NonNull PaymentError paymentError) {
                Log.d("SDKClient", "Houve um erro no pagamento.");
            }
        };

        final PaymentCode paymentCode;
        if(payment.getPaymentFormat().equals("CRÉDITO") && payment.getNumberParcel() == 1){
            paymentCode = PaymentCode.CREDITO_AVISTA;
        }else if(payment.getPaymentFormat().equals("CRÉDITO") && payment.getNumberParcel() > 1){
            paymentCode = PaymentCode.CREDITO_PARCELADO_LOJA;
        }else{
            paymentCode = PaymentCode.DEBITO_AVISTA;
        }

        CheckoutRequest request;
        if(payment.getNumberParcel() == 1){
            request = new CheckoutRequest.Builder()
                    .orderId(order.getId()) /* Obrigatório */
                    .amount(valueProductLong) /* Opcional */
                    .paymentCode(paymentCode) /* Opcional */
                    .build();
        }else{
            request = new CheckoutRequest.Builder()
                    .orderId(order.getId()) /* Obrigatório */
                    .amount(valueProductLong) /* Opcional */
                    .installments(payment.getNumberParcel()) /* Opcional */
                    .paymentCode(paymentCode) /* Opcional */
                    .build();
        }

        orderManager.checkoutOrder(request, paymentListener);




    }


}