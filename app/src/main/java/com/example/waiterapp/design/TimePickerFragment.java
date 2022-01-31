//package com.example.waiterapp.design;
//
//import android.app.Dialog;
//import android.app.TimePickerDialog;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.TimePicker;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.DialogFragment;
//
//import com.example.waiterapp.R;
//
//import java.util.Calendar;
//
//public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
//
//    TextView time_tv_bttn;
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int min = c.get(Calendar.MINUTE);
//
//        return new TimePickerDialog(getActivity() , R.style.MyTimePickerlight , this , hour , min , android.text.format.DateFormat.is24HourFormat(getActivity()));
//    }
//
//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//        time_tv_bttn = getActivity().findViewById(R.id.time_tv_bttn);
//
//        String time = hourOfDay+":"+minute;
//        time_tv_bttn.setText(time);
//    }
//
//
//}
