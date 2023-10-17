package com.kaas.svjmchitfund;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kaas.svjmchitfund.Api.RetrofitClient;
import com.kaas.svjmchitfund.Module.CreateBillModel;
import com.kaas.svjmchitfund.Module.SessionModel;
import com.kaas.svjmchitfund.databinding.ActivityCreateBillBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateBillActivity extends AppCompatActivity {
    ActivityCreateBillBinding b;
    SessionManager sessionManager;
    SessionModel sessionModel;
    String group_id = "";
    ArrayAdapter<String> adapter;
    String customer_id = "";
    String staff_id = "";
    //    String name = "";
    String mobile = "";
    //    String month = "";
    String place = "";
    String installment = "";
    String route = "";
    String total_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityCreateBillBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);
        sessionManager = new SessionManager(CreateBillActivity.this);
        sessionModel = new Gson().fromJson(sessionManager.getLoginSession(), SessionModel.class);
        b.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkForm()) {
                    createBill();
                }
            }
        });


    }

    private boolean checkForm() {
        group_id = b.edtGrpId.getText().toString().trim();
        customer_id = b.edtCustomerId.getText().toString().trim();
        place = b.edtPlace.getText().toString().trim();
        mobile = b.edtMobile.getText().toString().trim();
        staff_id = b.edtStaffId.getText().toString().trim();
        installment = b.edtInstallment.getText().toString().trim();
        route = b.edtRoute.getText().toString().trim();
        total_amount = b.edtTotalAmount.getText().toString().trim();


        return true;
    }

    private void createBill() {

        Call<CreateBillModel> call = RetrofitClient.getInstance().getApi().createBill(String.format("Bearer %s",
                        sessionModel.token), group_id, customer_id, mobile, staff_id
                , place, installment, route, "1000");
        call.enqueue(new Callback<CreateBillModel>() {
            @Override
            public void onResponse(Call<CreateBillModel> call, Response<CreateBillModel> response) {
                if (response.isSuccessful()) ;
                Toast.makeText(CreateBillActivity.this, "Created Successfully", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<CreateBillModel> call, Throwable t) {

            }
        });
    }

}