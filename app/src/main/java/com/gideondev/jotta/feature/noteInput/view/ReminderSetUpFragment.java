package com.gideondev.jotta.feature.noteInput.view;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.util.Calendar;


public class ReminderSetUpFragment extends DialogFragment implements
    TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {

  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  private Calendar mCalendar;
  private int mYear, mMonth, mHour, mMinute, mDay;
  private long mRepeatTime;
  private String mTitle;
  private String mTime;
  private String mDate;
  private String mRepeat;
  private String mRepeatNo;
  private String mRepeatType;
  private String mActive;
  private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;

  private static final String KEY_TITLE = "title_key";
  private static final String KEY_TIME = "time_key";
  private static final String KEY_DATE = "date_key";
  private static final String KEY_REPEAT = "repeat_key";
  private static final String KEY_REPEAT_NO = "repeat_no_key";
  private static final String KEY_REPEAT_TYPE = "repeat_type_key";
  private static final String KEY_ACTIVE = "active_key";

  // Constant values in milliseconds
  private static final long milMinute = 60000L;
  private static final long milHour = 3600000L;
  private static final long milDay = 86400000L;
  private static final long milWeek = 604800000L;
  private static final long milMonth = 2592000000L;
  private ItemClickListenter mItemClickListener;
  private FloatingActionButton mFAB1;
  private FloatingActionButton mFAB2;
  private View view;
  private Reminder mReminderModel;
  private String[] mDateSplit;
  private String[] mTimeSplit;
  private Switch mRepeatSwitch;


  public ReminderSetUpFragment() {
    // Required empty public constructor
  }
  @SuppressLint("ValidFragment")
  public ReminderSetUpFragment(ReminderSetUpFragment.ItemClickListenter listenter) {
    mItemClickListener = listenter;
  }


  public static ReminderSetUpFragment newInstance(Reminder reminder, ReminderSetUpFragment.ItemClickListenter listenter) {
    ReminderSetUpFragment fragment = new ReminderSetUpFragment(listenter);
    Bundle args = new Bundle();
    args.putSerializable("model",reminder);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    readArgs(savedInstanceState);
    setReminderValueToView(mReminderModel);

  }


  private void readArgs(Bundle savedInstanceState) {
    Bundle source = getArguments() != null ? getArguments() : savedInstanceState;
    mReminderModel = (Reminder) source.getSerializable("model");
  }

  private void setReminderValueToView(Reminder mReceivedReminder){
    if (mReceivedReminder != null){
      mDate = mReceivedReminder.getDate();
      mTime = mReceivedReminder.getTime();
      mRepeat = mReceivedReminder.getRepeat();
      mRepeatNo = mReceivedReminder.getRepeatNo();
      mRepeatType = mReceivedReminder.getRepeatType();
      mActive = mReceivedReminder.getActive();
      mDateText.setText(mDate);
      mTimeText.setText(mTime);
      mRepeatNoText.setText(mRepeatNo);
      mRepeatTypeText.setText(mRepeatType);
      mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
      // Setup up active buttons
      if (mActive.equals("false")) {
        mFAB1.setVisibility(View.VISIBLE);
        mFAB2.setVisibility(View.GONE);

      } else if (mActive.equals("true")) {
        mFAB1.setVisibility(View.GONE);
        mFAB2.setVisibility(View.VISIBLE);
      }

      // Setup repeat switch
      if (mRepeat.equals("false")) {
        mRepeatSwitch.setChecked(false);
        mRepeatText.setText(R.string.repeat_off);

      } else if (mRepeat.equals("true")) {
        mRepeatSwitch.setChecked(true);
      }

      // Obtain Date and Time details
      mCalendar = Calendar.getInstance();

      mDateSplit = mDate.split("/");
      mTimeSplit = mTime.split(":");

      mDay = Integer.parseInt(mDateSplit[0]);
      mMonth = Integer.parseInt(mDateSplit[1]);
      mYear = Integer.parseInt(mDateSplit[2]);
      mHour = Integer.parseInt(mTimeSplit[0]);
      mMinute = Integer.parseInt(mTimeSplit[1]);
    }else {
      setUpDefaultValue();
    }


  }

//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//
//
//  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_reminder_set_up, container, false);
    RelativeLayout relDate = (RelativeLayout) view.findViewById(R.id.date);
    RelativeLayout relTime = (RelativeLayout) view.findViewById(R.id.time);
    RelativeLayout relRepeatType = (RelativeLayout) view.findViewById(R.id.RepeatType);
    RelativeLayout relRepeatNo = (RelativeLayout) view.findViewById(R.id.RepeatNo);
    mFAB1 = (FloatingActionButton)view.findViewById(R.id.starred1);
    mFAB2 = (FloatingActionButton) view.findViewById(R.id.starred2);
    mRepeatSwitch = (Switch) view.findViewById(R.id.repeat_switch);
    mRepeatSwitch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        onSwitchRepeat(view);
      }
    });

    relRepeatNo.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        setRepeatNo(view);
      }
    });
    relDate.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        setDate(view);
      }
    });
    relTime.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        setTime(view);
      }
    });

    relRepeatType.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        selectRepeatType(view);
      }
    });
    mDateText = (TextView) view.findViewById(R.id.set_date);
    mTimeText = (TextView) view.findViewById(R.id.set_time);
    mRepeatText = (TextView) view.findViewById(R.id.set_repeat);
    mRepeatNoText = (TextView) view.findViewById(R.id.set_repeat_no);
    mRepeatTypeText = (TextView) view.findViewById(R.id.set_repeat_type);
    Button save = (Button) view.findViewById(R.id.save_alarm);
    Button cancel = (Button) view.findViewById(R.id.reminder_cancel);
    cancel.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        dismiss();
      }
    });
    mFAB1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        selectFab1();
      }
    });
    mFAB2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        selectFab2();
      }
    });

    save.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (mItemClickListener != null) {
          mItemClickListener.onItemClick(mYear,mMonth,mHour,mMinute,mDay,mRepeatTime,mTime,mDate,mRepeat,mRepeatNo,mRepeatType,mActive);
        }
        dismiss();
      }
    });


    return view;
  }

  public void setTime(View v) {
    Calendar now = Calendar.getInstance();
    TimePickerDialog tpd = TimePickerDialog.newInstance(
        this,
        now.get(Calendar.HOUR_OF_DAY),
        now.get(Calendar.MINUTE),
        false
    );
    tpd.setThemeDark(false);
    tpd.show(getFragmentManager(), "Timepickerdialog");
  }

  // On clicking Time picker
  public void setDate(View v) {
    Calendar now = Calendar.getInstance();
    DatePickerDialog dpd = DatePickerDialog.newInstance(
        this,
        now.get(Calendar.YEAR),
        now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH)
    );
    dpd.show(getFragmentManager(), "Datepickerdialog");
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    monthOfYear++;
    mDay = dayOfMonth;
    mMonth = monthOfYear;
    mYear = year;
    mDate = dayOfMonth + "/" + monthOfYear + "/" + year;
    mDateText.setText(mDate);
  }

  @Override
  public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
    mHour = hourOfDay;
    mMinute = minute;
    if (minute < 10) {
      mTime = hourOfDay + ":" + "0" + minute;
    } else {
      mTime = hourOfDay + ":" + minute;
    }
    mTimeText.setText(mTime);
  }

  public void onSwitchRepeat(View view) {
    boolean on = ((Switch) view).isChecked();
    if (on) {
      mRepeat = "true";
      mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
    } else {
      mRepeat = "false";
      mRepeatText.setText(R.string.repeat_off);
    }
  }

  // On clicking repeat type button
  public void selectRepeatType(View v) {
    final String[] items = new String[5];

    items[0] = "Minute";
    items[1] = "Hour";
    items[2] = "Day";
    items[3] = "Week";
    items[4] = "Month";

    // Create List Dialog
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle("Select Type");
    builder.setItems(items, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int item) {

        mRepeatType = items[item];
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
      }
    });
    AlertDialog alert = builder.create();
    alert.show();
  }


  public void setRepeatNo(View v) {
    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
    alert.setTitle("Enter Number");

    // Create EditText box to input repeat number
    final EditText input = new EditText(getActivity());
    input.setInputType(InputType.TYPE_CLASS_NUMBER);
    alert.setView(input);
    alert.setPositiveButton("Ok",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {

            if (input.getText().toString().length() == 0) {
              mRepeatNo = Integer.toString(1);
              mRepeatNoText.setText(mRepeatNo);
              mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            } else {
              mRepeatNo = input.getText().toString().trim();
              mRepeatNoText.setText(mRepeatNo);
              mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
            }
          }
        });
    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        // do nothing
      }
    });
    alert.show();
  }

  private void setUpDefaultValue() {
    // Initialize default values
    mActive = "true";
    mRepeat = "true";
    mRepeatNo = Integer.toString(1);
    mRepeatType = "Hour";

    mCalendar = Calendar.getInstance();
    mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
    mMinute = mCalendar.get(Calendar.MINUTE);
    mYear = mCalendar.get(Calendar.YEAR);
    mMonth = mCalendar.get(Calendar.MONTH) + 1;
    mDay = mCalendar.get(Calendar.DATE);

    mDate = mDay + "/" + mMonth + "/" + mYear;
    mTime = mHour + ":" + mMinute;

    mDateText.setText(mDate);
    mTimeText.setText(mTime);
    mRepeatNoText.setText(mRepeatNo);
    mRepeatTypeText.setText(mRepeatType);
    mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
  }

  public void selectFab1() {
    mFAB1.setVisibility(View.GONE);
    mFAB2.setVisibility(View.VISIBLE);
    mActive = "true";
  }

  // On clicking the inactive button
  public void selectFab2() {
    mFAB2.setVisibility(View.GONE);
    mFAB1.setVisibility(View.VISIBLE);
    mActive = "false";
  }

  public interface ItemClickListenter {

    void onItemClick(int mYear, int mMonth, int mHour, int mMinute, int mDay, long mRepeatTime,
        String mTime,
        String mDate,
        String mRepeat,
        String mRepeatNo,
        String mRepeatType,
        String mActive);
  }

}
