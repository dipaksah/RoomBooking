package com.dipak.roombooking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView name, etFirstDate, etLastDate, TotalDays, total, GrandTotal;
    EditText NoofRoom;
    Button btnCalculate, btnTax;
    Spinner spinnerCountry, spinnerRoomType;
    String RoomType[] = {"Deluxe", "Platinum", "presidential"};
    String countries[] = {"Nepal", "china", "India", "Bhutan", "pakistan", "afghanistan", "UK", "USA", "Japan", "German"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Binding();
        ArrayAdapter adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                RoomType
        );
        spinnerRoomType.setAdapter(adapter);
        spinnerRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, spinnerRoomType.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayAdapter adpt = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                countries
        );
        spinnerCountry.setAdapter(adpt);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, spinnerCountry.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculation();
                validation();
            }
        });

        btnTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaxCalculate();
                validation();
                Calculation();
            }
        });


    }


    public void Binding() {
        name = findViewById(R.id.etName);
        spinnerCountry = findViewById(R.id.spinCountry);
        spinnerRoomType = findViewById(R.id.spinRoomType);
        etFirstDate = findViewById(R.id.etCheckIN);
        etLastDate = findViewById(R.id.etCheckOut);
        TotalDays = findViewById(R.id.tvDays);
        NoofRoom = findViewById(R.id.etroom);
        total = findViewById(R.id.tvTotal);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnTax = findViewById(R.id.btnTaxCalculate);
        GrandTotal = findViewById(R.id.tvGrandTotal);

        etFirstDate.setOnClickListener(this);
        etLastDate.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.etCheckIN: {
                validation();
                final Calendar cal1 = Calendar.getInstance();
                int days = cal1.get(Calendar.DAY_OF_MONTH);
                int months = cal1.get(Calendar.MONTH);
                int years = cal1.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + month + 1 + "/" + year;
                        etFirstDate.setText("CheckIn Date: " + date);
                    }
                }, days, months, years);
                datePickerDialog.show();
                break;
            }


            case R.id.etCheckOut: {
                final Calendar cal1 = Calendar.getInstance();
                int days = cal1.get(Calendar.DAY_OF_MONTH);
                int months = cal1.get(Calendar.MONTH);
                int years = cal1.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + month + 1 + "/" + year;
                        etLastDate.setText("CheckOut Date: " + date);
                    }
                }, days, months, years);
                datePickerDialog.show();
                break;
            }
        }
    }


    public void Calculation() {
        validation();
        String first = etFirstDate.getText().toString();
        String last = etLastDate.getText().toString();

        String check1[] = first.split("/", 3);
        String check2[] = last.split("/", 3);
        int checkdayIn = Integer.parseInt(check1[1]);
        int checkdayout = Integer.parseInt(check2[1]);

        if (checkdayout > checkdayIn) {
            int diff = Integer.parseInt(check2[1]) - Integer.parseInt(check1[1]);
            TotalDays.setText("total days you want to stay: " + diff);
            int room = Integer.parseInt(NoofRoom.getText().toString());
            if (spinnerRoomType.getSelectedItem().toString() == "Deluxe") {
                int price = 2000;
                int totalCost = diff * price * room;
                total.setText("Total cost of Room is:" + totalCost);
            } else if (spinnerRoomType.getSelectedItem().toString() == "Platinum") {
                int price = 4000;
                int totalCost = diff * price * room;
                total.setText("Total cost of room is:" + totalCost);
            } else if (spinnerRoomType.getSelectedItem().toString() == "presidential") {
                int price = 7000;
                int totalcost = diff * price * room;
                total.setText("Total cost of room is:" + totalcost);
            }
        } else {
            etLastDate.setError("Please select valid Date");
        }

    }


    public void TaxCalculate() {
        Calculation();
        total.getText().toString();
        int value = Integer.parseInt(total.getText().toString());
        if (value < 10000) {
            int tax = 1;
            int CostIncludeTax = value + (value * tax / 100);
            GrandTotal.setText("Total cost include Tax is: " + CostIncludeTax);
        } else if (value >= 10000 && value < 50000) {
            int tax = 2;
            int CostIncludeTax = value + (value * tax / 100);
            GrandTotal.setText("Total cost include Tax is: " + CostIncludeTax);
        } else if (value >= 50000 && value < 150000) {
            int tax = 4;
            int CostIncludeTax = value + (value * tax / 100);
            GrandTotal.setText("Total cost include Tax is: " + CostIncludeTax);
        } else if (value >= 150000) {
            int tax = 8;
            int CostIncludeTax = value + (value * tax / 100);
            GrandTotal.setText("Total cost include Tax is: " + CostIncludeTax);
        }

    }


    public void validation() {
        if (TextUtils.isEmpty(name.getText().toString())) {

            name.setError("Please Enter Name");
            name.requestFocus();
            return;
        } else if (TextUtils.isEmpty(etFirstDate.getText().toString())) {

            etFirstDate.setError("Invalid Date");
            etFirstDate.requestFocus();
            return;
        } else if (TextUtils.isEmpty(NoofRoom.getText().toString())) {
            NoofRoom.setError("Please insert how many room you want");
            NoofRoom.requestFocus();
            return;
        }

    }
}
