package com.gideondev.jotta.feature.noteInput.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ListCallback;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.getbase.floatingactionbutton.FloatingActionsMenu.OnFloatingActionsMenuUpdateListener;
import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.model.NoteCheckListWrap;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.model.SettingsModel;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.view.ExpandableHeightGridView;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.presenter.Presenter;
import com.gideondev.jotta.databases.sqlite.DatabaseHelper;
import com.gideondev.jotta.feature.noteInput.adapter.SketchImageAdapter;
import com.gideondev.jotta.feature.noteInput.adapter.SketchImageListner;
import com.gideondev.jotta.feature.noteInput.presenter.view.NoteInputPresenter;
import com.gideondev.jotta.feature.noteInput.presenter.view.NoteInputView;
import com.gideondev.jotta.feature.sketch.NoteActionActivity;
import com.gideondev.jotta.internal.di.component.DaggerProjectComponent;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class NoteInputActivity
    extends BaseActionbarActivity implements NoteInputView {

  private EditText mNoteEdit;
  String mNote;
  @Inject
  NoteInputPresenter mPresenter;
  private Toolbar toolbar;
  private EditText mNoteTitle;
  private String mTitle;
  private LinearLayout mLnCheckView;
  private LinearLayout mLnAlarm;
  private View childCheckItem;
  private EditText mCheckNote;
  private CheckBox mCheckItem;
  private ImageView mMenuList;
  String isChecked;
  RelativeLayout mRootView;
  private List<NoteCheckListWrap> mNoteCheckList = new ArrayList<>();
  private List<NoteCheckListWrap> mNoteShareCheckList = new ArrayList<>();
  private TextView mLastEdit;
  Boolean isClicked = false;
  private View mMenuNoteLayout;
  private View mParentRootView;
  private String PageColor = "white";
  private CardView mCardMenu;
  private AppBarLayout appBarLayout;
  Boolean changeColor = false;
  private FragmentManager fm = getFragmentManager();
  private int mYears, mMonths, mHours, mMinutes, mDays;
  private long mRepeatTimes;
  private String mTimes;
  private String mDates;
  private String mRepeats;
  private String mRepeatNos;
  private String mRepeatTypes;
  private String mActives;
  private TextView mDateTime;
  public static final String EXTRA_REMINDER_ID = "Reminder_ID";
  public static final String ARGS_FRAGMENT_TYPE = "action";
  public static final String ARGS_FRAGMENT_FILE_PATH = "file_path";
  public static final int ACTION_SKETCH = 1;
  private ExpandableHeightGridView imageSketchGridview;
  private List<Attach> attachList = new ArrayList<>();
  private SketchImageAdapter sketchImageAdapter;
  private String imageFilePath;
  private DatabaseHelper databaseHelper = null;
  private FloatingActionsMenu menuMultipleActions;

  @Override
  public void initView() {
    mNoteEdit = (EditText) findViewById(R.id.note_edit);
    mNoteTitle = (EditText) findViewById(R.id.note_edit_title);
    imageSketchGridview = (ExpandableHeightGridView) findViewById(R.id.grid_image);
    imageSketchGridview.setExpanded(true);
    mLastEdit = (TextView) findViewById(R.id.last_edited);
    mLnCheckView = (LinearLayout) findViewById(R.id.ln_check_item);
    mRootView = (RelativeLayout) findViewById(R.id.item);
    mParentRootView = (View) findViewById(R.id.parent_root);
    mMenuList = (ImageView) findViewById(R.id.menu_list);
   mCardMenu = (CardView) findViewById(R.id.card_view);
    appBarLayout = (AppBarLayout) findViewById(R.id.tool);
    mLnAlarm = (LinearLayout) findViewById(R.id.ln_alarm);
    mDateTime = (TextView) findViewById(R.id.text_alarm);
    menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
    menuMultipleActions.setOnFloatingActionsMenuUpdateListener(
        new OnFloatingActionsMenuUpdateListener() {
          @Override
          public void onMenuExpanded() {
            menuMultipleActions.setBackgroundColor(getResources().getColor(R.color.white));
          }

          @Override
          public void onMenuCollapsed() {
            menuMultipleActions.setBackgroundColor(getResources().getColor(R.color.transparent));
          }
        });

    mLnAlarm.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        openReminderFRagment();
      }
    });

    mMenuList.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!isClicked) {
          LayoutInflater inflater = LayoutInflater.from(getContext());
          mMenuNoteLayout = inflater.inflate(R.layout.note_edit_menu_list, null);
          mMenuNoteLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              return true;
            }
          });
          //if (mPresenter.getmNoteModel() != null){
          //    ChangePageColorUtil.ChangeBackGroundMenu(getContext(),mMenuNoteLayout,mPresenter.getmNoteModel());
          //}
          RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          lp.addRule(RelativeLayout.ABOVE, R.id.card_view);
          mMenuNoteLayout.setLayoutParams(lp);
          mMenuNoteLayout.findViewById(R.id.share_note)
              .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  setUpValueForShareCheckBox();
                  mPresenter
                      .ShareNote(mNoteEdit.getText().toString(), mNoteTitle.getText().toString(),
                          mNoteShareCheckList);
                }
              });

          mMenuNoteLayout.findViewById(R.id.add_reminder)
              .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  openReminderFRagment();
                }
              });

          mMenuNoteLayout.findViewById(R.id.add_check_box)
              .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  AddCheckBox();
                  mRootView.removeView(mMenuNoteLayout);
                  isClicked = false;
                }
              });

          mMenuNoteLayout.findViewById(R.id.sketch)
              .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  startNoteActionActivity(ACTION_SKETCH);
                  mRootView.removeView(mMenuNoteLayout);
                  isClicked = false;
                }
              });

          mRootView.addView(mMenuNoteLayout);
          isClicked = true;
        } else {
          mRootView.removeView(mMenuNoteLayout);
          isClicked = false;
        }

      }
    });

  }

  private void openReminderFRagment() {
    ReminderSetUpFragment reminderSetUpFragment = ReminderSetUpFragment
        .newInstance(mPresenter.getReminder(),
            new ReminderSetUpFragment.ItemClickListenter() {
              @Override
              public void onItemClick(int mYear, int mMonth, int mHour, int mMinute,
                  int mDay, long mRepeatTime,
                  String mTime, String mDate, String mRepeat, String mRepeatNo,
                  String mRepeatType,
                  String mActive) {
                mLnAlarm.setVisibility(View.VISIBLE);
                mDateTime.setText(mDate + " " + "/" + mTime);
                mYears = mYear;
                mMonths = mMonth;
                mHours = mHour;
                mMinutes = mMinute;
                mDays = mDay;
                mRepeatTimes = mRepeatTime;
                mTimes = mTime;
                mDates = mDate;
                mRepeats = mRepeat;
                mRepeatNos = mRepeatNo;
                mRepeatTypes = mRepeatType;
                mActives = mActive;
              }
            });
    reminderSetUpFragment.show(fm, "");
  }


  private void AddCheckBox() {
    childCheckItem = getLayoutInflater().inflate(R.layout.custom_check_box_with_edit_text, null);
    mCheckNote = (EditText) childCheckItem.findViewById(R.id.item_edit_text);
    mLnCheckView.addView(childCheckItem);
  }

  public void startNoteActionActivity(int action) {
    Intent intent = new Intent(this, NoteActionActivity.class);
    intent.putExtra(ARGS_FRAGMENT_TYPE, action);
    intent.putExtra(ARGS_FRAGMENT_FILE_PATH,imageFilePath );
    startActivityForResult(intent, action);
  }


  private void setUpValueForCheckBox() {
    for (int i = 0; i < mLnCheckView.getChildCount(); i++) {
      NoteCheckListWrap model = new NoteCheckListWrap();
      View view = mLnCheckView.getChildAt(i);
      mCheckItem = (CheckBox) view.findViewById(R.id.item_check);
      mCheckNote = (EditText) view.findViewById(R.id.item_edit_text);
      String checknote = mCheckNote.getText().toString();
      if (mCheckItem.isChecked()) {
        isChecked = "true";
      } else {
        isChecked = "false";
      }
      model.setCheckItem(isChecked);
      model.setItemWord(checknote);
      mNoteCheckList.add(model);
    }
  }

  private void setUpValueForUpdateCheckBox() {
    for (int i = 0; i < mLnCheckView.getChildCount(); i++) {
      NoteCheckListWrap model = new NoteCheckListWrap();
      View view = mLnCheckView.getChildAt(i);
      mCheckItem = (CheckBox) view.findViewById(R.id.item_check);
      mCheckNote = (EditText) view.findViewById(R.id.item_edit_text);
      String checknote = mCheckNote.getText().toString();
      if (mCheckItem.isChecked()) {
        isChecked = "true";
      } else {
        isChecked = "false";
      }
      model.setCheckItem(isChecked);
      model.setItemWord(checknote);
      mNoteCheckList.add(model);
    }
  }

  private void setUpValueForShareCheckBox() {
    for (int i = 0; i < mLnCheckView.getChildCount(); i++) {
      NoteCheckListWrap model = new NoteCheckListWrap();
      View view = mLnCheckView.getChildAt(i);
      mCheckItem = (CheckBox) view.findViewById(R.id.item_check);
      mCheckNote = (EditText) view.findViewById(R.id.item_edit_text);
      String checknote = mCheckNote.getText().toString();
      if (mCheckItem.isChecked()) {
        isChecked = "true";
      } else {
        isChecked = "false";
      }
      model.setCheckItem(isChecked);
      model.setItemWord(checknote);
      mNoteShareCheckList.add(model);
    }
  }

  private void iniActionbar() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(getString(R.string.app_name));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void SetUpReminderView(Reminder model) {
    if (model != null) {
      mLnAlarm.setVisibility(View.VISIBLE);
      mDateTime.setText(model.getDate() + " " + model.getTime());
    }
  }

  @SuppressLint("SetTextI18n")
  @Override
  public void SetUpView(NoteModel model, List<NoteCheckListWrap> mNoteCheckList) {
    mNoteEdit.setText(model.getNoteWord());
    mNoteTitle.setText(model.getTitle());
    if (model.getLastEdited() != null && !model.getLastEdited().isEmpty()) {
      mLastEdit.setText("Edited:" + " " + model.getLastEdited());
    } else {
      mLastEdit.setText("Edited:" + " " + mPresenter.getCurrentTimeStamp());
    }
    //if (model.getColor() != null && !model.getColor().isEmpty()){
    //    ChangePageColorUtil.ChangeBackGroundOfNote(getContext(),this,mParentRootView,appBarLayout,mCardMenu,model);
    //}

    if (!mNoteCheckList.isEmpty()) {
      for (int i = 0; i < mNoteCheckList.size(); i++) {
        childCheckItem = getLayoutInflater()
            .inflate(R.layout.custom_check_box_with_edit_text, null);
        mCheckItem = (CheckBox) childCheckItem.findViewById(R.id.item_check);
        mCheckNote = (EditText) childCheckItem.findViewById(R.id.item_edit_text);
        mCheckNote.setText(mNoteCheckList.get(i).getItemWord());
        if (mNoteCheckList.get(i).getCheckItem().equals("true")) {
          mCheckItem.setChecked(true);
        } else {
          mCheckItem.setChecked(false);
        }
        mLnCheckView.addView(childCheckItem);
      }
    }

    if (getNoteAttachFilePath() != null && getNoteAttachFilePath().size() > 0){
      imageSketchGridview.setVisibility(View.VISIBLE);
    }


  }

  @Override
  public void SetUpTextFormat(SettingsModel model) {
//    SettingsUtilClass.ChangeTextSize(model, mNoteEdit);
//    SettingsUtilClass.ChangeTextForm(model, mNoteEdit);
  }

  @Override
  public void setUpDefaultAlarmValue(Reminder reminder) {
    if (reminder != null) {
      mDates = reminder.getDate();
      mTimes = reminder.getTime();
      mRepeats = reminder.getRepeat();
      mRepeatNos = reminder.getRepeatNo();
      mRepeatTypes = reminder.getRepeatType();
      mActives = reminder.getActive();

      String[] mDateSplit = mDates.split("/");
      String[] mTimeSplit = mTimes.split(":");

      mDays = Integer.parseInt(mDateSplit[0]);
      mMonths = Integer.parseInt(mDateSplit[1]);
      mYears = Integer.parseInt(mDateSplit[2]);
      mHours = Integer.parseInt(mTimeSplit[0]);
      mMinutes = Integer.parseInt(mTimeSplit[1]);
    }

  }

  @Override
  public void initModel() {

  }


  @Override
  public void onBackPressed() {
    mNote = mNoteEdit.getText().toString();
    mTitle = mNoteTitle.getText().toString();
    if (!mNote.isEmpty()) {
      if (!mPresenter.getmNoteCheckListWrap().isEmpty()) {
        setUpValueForUpdateCheckBox();
        mPresenter.SaveNote(mNote, mTitle, mNoteCheckList, PageColor, changeColor, mYears, mMonths,
            mHours, mMinutes,
            mDays, mRepeatTimes, mTimes, mDates, mRepeats, mRepeatNos, mRepeatTypes, mActives,
            attachList);
        sendResultBack();
      } else {
        setUpValueForCheckBox();
        mPresenter.SaveNote(mNote, mTitle, mNoteCheckList, PageColor, changeColor, mYears, mMonths,
            mHours, mMinutes, mDays, mRepeatTimes, mTimes, mDates, mRepeats, mRepeatNos, mRepeatTypes, mActives,
            attachList);
        sendResultBack();
      }
    } else {
      Toast.makeText(getContext(), "Cannot save empty note", Toast.LENGTH_SHORT).show();
    }

    super.onBackPressed();
  }


  private void sendResultBack() {
    if (mPresenter.getReminderId() != null) {
      if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN) {
        this.finishAffinity();
      } else {

      }

    } else {
      Intent intent = new Intent();
      setResult(2, intent);
      finish();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case (ACTION_SKETCH): {
        if (resultCode == Activity.RESULT_OK) {
          imageSketchGridview.setVisibility(View.VISIBLE);
          String result = data.getStringExtra("picture");
          Attach attach = new Attach(result);
          attachList.add(attach);
          sketchImageAdapter.notifyDataSetChanged();
        }
        break;
      }
    }
  }


  @Override
  public Presenter getPresenter() {
    return mPresenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    //  ChangeThemeUtils.onActivityCreateSetTheme(getActivity(), getContext());
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_note_input);
    initView();
    iniActionbar();
    injectInjector();
  }

  private void setUpSketchImageAdapter() {
    sketchImageAdapter = new SketchImageAdapter(this, attachList, new SketchImageListner() {
      @Override
      public void Onclick(String filePath) {
        List<String> items = Arrays.asList(getResources().getStringArray(R.array.attachments_actions));
        new MaterialDialog.Builder(NoteInputActivity.this)
            .items(items.toArray(new String[items.size()]))
            .itemsCallback(new ListCallback() {
              @Override
              public void onSelection(MaterialDialog dialog, View itemView, int which,
                  CharSequence text) {

              }
            })
            .build()
            .show();
     //   popAlertDialogSketch(filePath);
      }
    });
    imageSketchGridview.setAdapter(sketchImageAdapter);
  }

  private void performAttachmentAction(int attachmentPosition, int i) {
    switch (getResources().getStringArray(R.array.attachments_actions_values)[i]) {
      case "share":

        break;
      case "delete":
        break;
      case "edit":
        break;
      default:
    }
  }


  private void popAlertDialogSketch(final String filePath) {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    final View dialogView = inflater.inflate(R.layout.view_sketch_action, null);
    TextView mEditSketch = dialogView.findViewById(R.id.edit_sketch);
    final AlertDialog b = dialogBuilder.create();
    mEditSketch.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        imageFilePath = filePath;
        startNoteActionActivity(ACTION_SKETCH);
        b.dismiss();
      }
    });

    dialogBuilder.setView(dialogView);

    dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        //pass
      }
    });

    b.show();
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void injectInjector() {
    DaggerProjectComponent.builder()
        .projectModule(new ProjectModule(this))
        .build()
        .inject(this);
    mPresenter.setView(this);
    mPresenter.Start();
    mPresenter.getExtra(getIntent());
    if (mPresenter.getmNoteModel() == null) {
      mLastEdit.setText("Edited:" + " " + mPresenter.getCurrentTimeStamp());
    }
    setUpSketchImageAdapter();
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //getMenuInflater().inflate(R.menu.menu_for_note_input, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        mNote = mNoteEdit.getText().toString();
        mTitle = mNoteTitle.getText().toString();
        if (!mNote.isEmpty()) {
          if (!mPresenter.getmNoteCheckListWrap().isEmpty()) {
            setUpValueForUpdateCheckBox();
            mPresenter
                .SaveNote(mNote, mTitle, mNoteCheckList, PageColor, changeColor, mYears, mMonths,
                    mHours, mMinutes,
                    mDays, mRepeatTimes, mTimes, mDates, mRepeats, mRepeatNos, mRepeatTypes,
                    mActives, attachList);
            sendResultBack();
          } else {
            setUpValueForCheckBox();
            mPresenter
                .SaveNote(mNote, mTitle, mNoteCheckList, PageColor, changeColor, mYears, mMonths,
                    mHours, mMinutes,
                    mDays, mRepeatTimes, mTimes, mDates, mRepeats, mRepeatNos, mRepeatTypes,
                    mActives, attachList);
            sendResultBack();
          }
        } else {
          Toast.makeText(getContext(), "Cannot save empty note", Toast.LENGTH_SHORT).show();
        }
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public Activity getActivity() {
    return this;
  }

  @Override
  public void showLineLoading() {

  }

  @Override
  public void hideLineLoading() {

  }

  @Override
  public void showError(String message) {

  }

  @Override
  public Context getContext() {
    return this;
  }

  @Override
  public List<Attach> getNoteAttachFilePath() {
    return attachList;
  }

  @Override
  public DatabaseHelper getHelper() {
    if (databaseHelper == null) {
      databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
    }
    return databaseHelper;
  }

}
