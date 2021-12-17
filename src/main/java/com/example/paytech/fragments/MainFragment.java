package com.example.paytech.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paytech.R;
import com.example.paytech.model.Payment;
import com.example.paytech.utils.MoneyTextWatcher;
import com.google.android.material.textfield.TextInputEditText;

import java.text.NumberFormat;
import java.util.Locale;

public class MainFragment extends Fragment {
    private RadioGroup paymentFormat, numberParcel;
    private RadioButton credit, debit,parcel1x,parcel2x,parcel3x,parcel4x,parcel5x,parcel6x,parcel7x,parcel8x,parcel9x,parcel10x,parcel11x,parcel12x;
    private TextInputEditText description, valueProduct;
    private Locale ptBr = new Locale("pt","BR");
    private View viewFrag;
    private Payment payment;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewFrag = inflater.inflate(R.layout.fragment_main, container, false);
        payment = new Payment();
        valueProduct = viewFrag.findViewById(R.id.tx_value_product);
        numberParcel = viewFrag.findViewById(R.id.rg_number_parcel);
        paymentFormat = viewFrag.findViewById(R.id.rg_payment_format);
        credit = viewFrag.findViewById(R.id.rb_credit);
        debit = viewFrag.findViewById(R.id.rb_debit);
        description = viewFrag.findViewById(R.id.tx_description_product);
        parcel1x = viewFrag.findViewById(R.id.rb_parcel1x);
        parcel2x = viewFrag.findViewById(R.id.rb_parcel2x);
        parcel3x = viewFrag.findViewById(R.id.rb_parcel3x);
        parcel4x = viewFrag.findViewById(R.id.rb_parcel4x);
        parcel5x = viewFrag.findViewById(R.id.rb_parcel5x);
        parcel6x = viewFrag.findViewById(R.id.rb_parcel6x);
        parcel7x = viewFrag.findViewById(R.id.rb_parcel7x);
        parcel8x = viewFrag.findViewById(R.id.rb_parcel8x);
        parcel9x = viewFrag.findViewById(R.id.rb_parcel9x);
        parcel10x = viewFrag.findViewById(R.id.rb_parcel10x);
        parcel11x = viewFrag.findViewById(R.id.rb_parcel11x);
        parcel12x = viewFrag.findViewById(R.id.rb_parcel12x);
        payment.setPaymentFormat("DÉBITO");
        valueProductChange();
        radioGroupNumberParcel();
        getNumberParcelSelected();

        return viewFrag;
    }


    private void radioGroupNumberParcel(){
        paymentFormat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_credit){
                    if(!valueProduct.getText().toString().equals("") && !valueProduct.getText().toString().equals("R$0,00") ){
                        checkNumberParcel();
                        numberParcel.setVisibility(View.VISIBLE);
                        payment.setPaymentFormat("CRÉDITO");
                        getNumberParcelSelected();
                    }else{
                        valueProduct.setError("Preencha o campo para continuar");
                    }

                }else{
                    numberParcel.setVisibility(View.GONE);
                    payment.setNumberParcel(1);
                    payment.setPaymentFormat("DÉBITO");
                }
            }
        });
    }

    private void checkNumberParcel(){
        String valueProductFormat = valueProduct.getText().toString().substring(2);
        float valueProductFloat = 0;
        if(valueProductFormat.length() > 6){
            valueProductFloat =  Float.parseFloat(valueProductFormat.replaceAll("\\.", "").replaceAll(",", "."));
        }else{
            valueProductFloat =  Float.parseFloat(valueProductFormat.replaceAll(",", "."));
        }

        if(!valueProduct.getText().toString().equals("R$0,00")){
            parcel1x.setVisibility(View.VISIBLE);
            payment.setValueProduct(valueProductFloat);
            parcel1x.setText("1x de "+NumberFormat.getCurrencyInstance(ptBr).format(valueProductFloat));

            if(valueProductFloat >= 10) {
                if (valueProductFloat / 2 >= 5) {
                    parcel2x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 2;
                    parcel2x.setText("2x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));

                } else {
                    if(parcel2x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel2x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 3 >= 5) {
                    parcel3x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 3;
                    parcel3x.setText("3x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel3x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel3x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 4 >= 5) {
                    parcel4x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 4;
                    parcel4x.setText("4x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel4x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel4x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 5 >= 5) {
                    parcel5x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 5;
                    parcel5x.setText("5x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel5x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel5x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 6 >= 5) {
                    parcel6x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 6;
                    parcel6x.setText("6x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel6x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel6x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 7 >= 5) {
                    parcel7x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 7;
                    parcel7x.setText("7x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel7x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel7x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 8 >= 5) {
                    parcel8x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 8;
                    parcel8x.setText("8x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel8x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel8x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 9 >= 5) {
                    parcel9x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 9;
                    parcel9x.setText("9x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel9x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel9x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 10 >= 5) {
                    parcel10x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 10;
                    parcel10x.setText("10x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel10x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel10x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 11 >= 5) {
                    parcel11x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 11;
                    parcel11x.setText("11x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel11x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel11x.setVisibility(View.GONE);
                }
                if (valueProductFloat / 12 >= 5) {
                    parcel12x.setVisibility(View.VISIBLE);
                    float value = valueProductFloat / 12;
                    parcel12x.setText("12x de "+ NumberFormat.getCurrencyInstance(ptBr).format(value));
                } else {
                    if(parcel12x.isChecked()){
                        parcel1x.setChecked(true);
                    }
                    parcel12x.setVisibility(View.GONE);
                }
            }else{
                if(!parcel1x.isChecked()){
                    parcel1x.setChecked(true);
                }
                parcel2x.setVisibility(View.GONE);
                parcel3x.setVisibility(View.GONE);
                parcel4x.setVisibility(View.GONE);
                parcel5x.setVisibility(View.GONE);
                parcel6x.setVisibility(View.GONE);
                parcel7x.setVisibility(View.GONE);
                parcel8x.setVisibility(View.GONE);
                parcel9x.setVisibility(View.GONE);
                parcel10x.setVisibility(View.GONE);
                parcel11x.setVisibility(View.GONE);
                parcel12x.setVisibility(View.GONE);
            }
        }else{
            parcel1x.setVisibility(View.GONE);
            parcel2x.setVisibility(View.GONE);
            parcel3x.setVisibility(View.GONE);
            parcel4x.setVisibility(View.GONE);
            parcel5x.setVisibility(View.GONE);
            parcel6x.setVisibility(View.GONE);
            parcel7x.setVisibility(View.GONE);
            parcel8x.setVisibility(View.GONE);
            parcel9x.setVisibility(View.GONE);
            parcel10x.setVisibility(View.GONE);
            parcel11x.setVisibility(View.GONE);
            parcel12x.setVisibility(View.GONE);
            valueProduct.setError("Valor não pode ser R$ 0,00");
        }


    }

    private void valueProductChange(){
        valueProduct.addTextChangedListener(new MoneyTextWatcher(valueProduct));
        valueProduct.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() || actionId == EditorInfo.IME_ACTION_DONE) {
                    checkNumberParcel();
                    hideSoftwareKeyboard();
                    if(credit.isChecked() && !valueProduct.getText().toString().equals("R$0,00")){
                        numberParcel.setVisibility(View.VISIBLE);
                        getNumberParcelSelected();
                    }
                }
                return false;
            }
        });
    }

    public void getNumberParcelSelected(){
        payment.setNumberParcel(1);
        payment.setTextNumberParcel(parcel1x.getText().toString());
        numberParcel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_parcel1x){
                    payment.setNumberParcel(1);
                    payment.setTextNumberParcel(parcel1x.getText().toString());
                }else if(checkedId == R.id.rb_parcel2x){
                    payment.setNumberParcel(2);
                    payment.setTextNumberParcel(parcel2x.getText().toString());
                }else if(checkedId == R.id.rb_parcel3x){
                    payment.setNumberParcel(3);
                    payment.setTextNumberParcel(parcel3x.getText().toString());
                }else if(checkedId == R.id.rb_parcel4x){
                    payment.setNumberParcel(4);
                    payment.setTextNumberParcel(parcel4x.getText().toString());
                }else if(checkedId == R.id.rb_parcel5x){
                    payment.setNumberParcel(5);
                    payment.setTextNumberParcel(parcel5x.getText().toString());
                }else if(checkedId == R.id.rb_parcel6x){
                    payment.setNumberParcel(6);
                    payment.setTextNumberParcel(parcel6x.getText().toString());
                }else if(checkedId == R.id.rb_parcel7x){
                    payment.setNumberParcel(7);
                    payment.setTextNumberParcel(parcel7x.getText().toString());
                }else if(checkedId == R.id.rb_parcel8x){
                    payment.setNumberParcel(8);
                    payment.setTextNumberParcel(parcel8x.getText().toString());
                }else if(checkedId == R.id.rb_parcel9x){
                    payment.setNumberParcel(9);
                    payment.setTextNumberParcel(parcel9x.getText().toString());
                }else if(checkedId == R.id.rb_parcel10x){
                    payment.setNumberParcel(10);
                    payment.setTextNumberParcel(parcel10x.getText().toString());
                }else if(checkedId == R.id.rb_parcel11x){
                    payment.setNumberParcel(11);
                    payment.setTextNumberParcel(parcel11x.getText().toString());
                }else if(checkedId == R.id.rb_parcel12x){
                    payment.setNumberParcel(12);
                    payment.setTextNumberParcel(parcel12x.getText().toString());
                }
            }
        });
    }

    public boolean checkComponentsEmpty(){
        boolean result = false;
        if (description.getText().toString().equals("")) {
            description.setError("Preencha o campo para continuar");
        }else if(valueProduct.getText().toString().equals("") || valueProduct.getText().toString().equals("R$0,00") ) {
            valueProduct.setError("Preencha o campo para continuar");

        }else {
            radioGroupNumberParcel();
            payment.setDescription(description.getText().toString());
            String valueProductFormat = valueProduct.getText().toString().substring(2);
            float valueProductFloat = 0;
            if(valueProductFormat.length() > 6){
                valueProductFloat =  Float.parseFloat(valueProductFormat.replaceAll("\\.", "").replaceAll(",", "."));
            }else{
                valueProductFloat =  Float.parseFloat(valueProductFormat.replaceAll(",", "."));
            }
            payment.setValueProduct(valueProductFloat);
            result = true;
        }
        return result;
    }

    protected void hideSoftwareKeyboard(){
        final Activity activity = getActivity();
        final InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public Payment returnPayment(){
        return payment;
    }

}