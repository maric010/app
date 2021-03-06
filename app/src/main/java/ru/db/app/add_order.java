package ru.db.app;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class add_order extends AppCompatActivity {
    Dialog dialog;
    CalendarView calendarView;
    TimePicker timePicker;
    Boolean start=true;
    static TextView otkuda,kuda;
    String start_date,stop_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);
        otkuda = findViewById(R.id.new_order_otkuda);
        kuda = findViewById(R.id.new_order_kuda);

        my.effect(findViewById(R.id.create));
    }

    public void back(View view) {
        finish();
    }

    public void create_order(View view) {
        my.reg_order(my.otkuda,my.kuda,
                ((TextView)findViewById(R.id.new_order_start_date)).getText().toString(),
                ((TextView)findViewById(R.id.new_order_stop_date)).getText().toString(),
                ((EditText)findViewById(R.id.new_order_description)).getText().toString(),
                ((CheckBox)findViewById(R.id.new_order_peoples)).isChecked()+"",
                ((EditText)findViewById(R.id.new_order_people_cost_text)).getText().toString(),
                ((EditText)findViewById(R.id.new_order_gruz_cost)).getText().toString(),
                ((CheckBox)findViewById(R.id.new_order_gruz)).isChecked()+"",
                ((EditText)findViewById(R.id.peoples_max)).getText().toString(),
                ((EditText)findViewById(R.id.gruz_max)).getText().toString());
        finish();
    }

    public void pick_date(View view) {
        start=true;
        pic();
    }
    void pic(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.calendar);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        calendarView = dialog.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                try {
                    Date date = sdf.parse(dayOfMonth+"."+(month+1)+"."+year);
                    calendarView.setDate(date.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        timePicker = dialog.findViewById(R.id.timepicker);
        timePicker.setIs24HourView(true);
        Button button=dialog.findViewById(R.id.accept);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                String sDate = sdf.format(calendarView.getDate());
                String hour = String.valueOf(timePicker.getCurrentHour());
                String minute = String.valueOf(timePicker.getCurrentMinute());
                if(minute.length()==1)
                    minute = "0"+minute;

                if(start){
                    start_date=sDate+" "+hour+":"+minute;
                    ((TextView)findViewById(R.id.new_order_start_date)).setText(start_date);
                }
                else{
                    stop_date=sDate+" "+hour+":"+minute;
                    ((TextView)findViewById(R.id.new_order_stop_date)).setText(stop_date);
                }
                dialog.cancel();
            }
        });
    }

    public void pick_stop_date(View view) {
        start=false;
        pic();
    }

    public void otkuda_onClick(View view) {
        my.is_otkuda=true;
        Intent intent = new Intent(add_order.this, country_city.class);
        startActivity(intent);
    }

    public void kuda_onClick(View view) {
        my.is_otkuda=false;
        Intent intent = new Intent(add_order.this, country_city.class);
        startActivity(intent);
    }
}