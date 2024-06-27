package com.app.javaa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UserPageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button chooseServiceButton;
    private Spinner serviceSpinner;
    private TextView textView;
    private Button editButton;
    private Button deleteButton;
    private Button logoutButton;
    private TextView horarioTextView;
    private Calendar calendar = Calendar.getInstance();

    private String selectedDate = "";
    private String selectedTime = "";
    private boolean isAppointmentScheduled = false;
    private String selectedService = "";

    private DatePicker datePicker;
    private TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        button = findViewById(R.id.scheduleButton);
        chooseServiceButton = findViewById(R.id.chooseServiceButton);
        serviceSpinner = findViewById(R.id.serviceSpinner);
        textView = findViewById(R.id.horarioTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        logoutButton = findViewById(R.id.logoutButton);
        horarioTextView = findViewById(R.id.horarioTextView);

        datePicker = new DatePicker(this);
        timePicker = new TimePicker(this);

        button.setOnClickListener(this);
        chooseServiceButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        editButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
        horarioTextView.setVisibility(View.GONE);

        String[] services = {"Corte de Barba", "Corte de Cabelo", "Corte de Cabelo e Barba"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);
        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedService = services[position];
                showToast("Serviço escolhido: " + selectedService);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Ação a ser realizada quando nada é selecionado
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.horarioTextView) {
            datePicker.setVisibility(View.VISIBLE);
            timePicker.setVisibility(View.VISIBLE);
            showTimePickerDialog();
        } else if (v.getId() == R.id.editButton) {
            showDatePickerDialogForEditing();
        } else if (v.getId() == R.id.deleteButton) {
            showToast("Você desmarcou seu serviço.");
            isAppointmentScheduled = false;
            editButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            horarioTextView.setVisibility(View.GONE);
        } else if (v.getId() == R.id.scheduleButton) {
            showDatePickerDialog();
        } else if (v.getId() == R.id.logoutButton) {
            signOutUser();
        } else if (v.getId() == R.id.chooseServiceButton) {
            toggleServiceSpinnerVisibility();
        }
    }

    private void toggleServiceSpinnerVisibility() {
        if (serviceSpinner.getVisibility() == View.VISIBLE) {
            serviceSpinner.setVisibility(View.GONE);
        } else {
            serviceSpinner.setVisibility(View.VISIBLE);
        }
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    selectedDate = dateFormat.format(calendar.getTime());

                    showTimePickerDialog();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedTime = hourOfDay + ":" + minute;
                    updateTextView();
                    showButtons();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void showDatePickerDialogForEditing() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    selectedDate = dateFormat.format(calendar.getTime());

                    showTimePickerDialogForEditing();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialogForEditing() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedTime = hourOfDay + ":" + minute;
                    updateTextView();
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }

    private void updateTextView() {
        textView.setText("Serviço escolhido:\n " + selectedService + "\n\n Horário marcado:\nDia " + selectedDate + " às " + selectedTime);
        horarioTextView.setVisibility(View.VISIBLE);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showButtons() {
        isAppointmentScheduled = true;
        editButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
    }

    private void signOutUser() {
        Intent intent = new Intent(UserPageActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}