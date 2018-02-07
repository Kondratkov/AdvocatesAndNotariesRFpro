package kondratkov.advocatesandnotariesrfpro.input;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

import kondratkov.advocatesandnotariesrfpro.R;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private DatePickedListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ��������� ������� ���� � ���������� ����
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // �������� ��������� ������ DatePickerDialog � ������ ���
        Log.d("qwerty", "1" + String.valueOf(getActivity()));
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onAttach(Activity activity) {
        // whenthefragmentisinitiallyshown (i.e. attachedtotheactivity),
        // casttheactivitytothecallbackinterfacetype
        super.onAttach(activity);
        try {
            mListener = (DatePickedListener) activity;
            Log.d("qwerty", "2"+ String.valueOf(mListener));
        } catch (ClassCastException e) {Log.d("qwerty", "2.2");
            throw new ClassCastException(activity.toString()
                    + " mustimplement " + DatePickedListener.class.getName());

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // ������������ ������ ����. ������� ��� � ��������� �����
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        Log.d("qwerty", "3" + String.valueOf(mListener));
        mListener.onDatePicked(c);
    }

    public static interface DatePickedListener {
        public void onDatePicked(Calendar time);

    }
}
