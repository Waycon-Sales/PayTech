package com.example.paytech.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paytech.R;
import com.example.paytech.model.Payment;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentSummaryFragment extends Fragment {
    private View viewFragSummary;
    private ImageButton btnBack;
    private TextView description, valueProd, paymentMethod, numberParcel, tittleParcel;
    private Locale ptBr = new Locale("pt","BR");
    private OnBtnBackListener onBtnBackListener;


    public PaymentSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;

        try {
            onBtnBackListener = (OnBtnBackListener) activity;
        }catch (ClassCastException e){
            throw new ClassCastException( activity.toString()+"Algo deu errado :(");
        }
    }

    public interface OnBtnBackListener{
        public void onBtnBack();

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Payment payment = (Payment) this.getArguments().getSerializable("DataPayment");
        viewFragSummary = inflater.inflate(R.layout.fragment_payment_summary, container, false);
        btnBack = viewFragSummary.findViewById(R.id.btn_back);
        description = viewFragSummary.findViewById(R.id.tv_description);
        valueProd = viewFragSummary.findViewById(R.id.tv_value_product);
        paymentMethod = viewFragSummary.findViewById(R.id.tv_payment_format);
        numberParcel = viewFragSummary.findViewById(R.id.tv_number_parcel);
        tittleParcel = viewFragSummary.findViewById(R.id.tv_tittle_parcel_summary);

        description.setText(payment.getDescription());
        valueProd.setText(NumberFormat.getCurrencyInstance(ptBr).format(payment.getValueProduct()));
        paymentMethod.setText(payment.getPaymentFormat());

        if(payment.getPaymentFormat().equals("DÉBITO")){
            tittleParcel.setVisibility(View.GONE);
            numberParcel.setVisibility(View.GONE);
        }else{
            numberParcel.setText(payment.getTextNumberParcel());
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                onBtnBackListener.onBtnBack();

            }
        });
        return viewFragSummary;
    }



}