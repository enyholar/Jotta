package com.gideondev.jotta.feature.main.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.PreferenUtil.PreferenUtil;
import com.gideondev.jotta.base.BaseActionbarActivity;
import com.gideondev.jotta.base.presenter.Presenter;
import com.gideondev.jotta.databases.dao.NoteCheckDAO;
import com.gideondev.jotta.databases.dao.NoteDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.databinding.ActivityMainBinding;
import com.gideondev.jotta.feature.main.adapter.FolderAdapter;
import com.gideondev.jotta.feature.main.adapter.FolderListner;
import com.gideondev.jotta.feature.main.adapter.NoteAdapter;
import com.gideondev.jotta.feature.main.adapter.NoteListner;
import com.gideondev.jotta.feature.main.noteFolderList.FolderListFragment;
import com.gideondev.jotta.feature.main.presenter.view.MainPresenter;
import com.gideondev.jotta.feature.main.presenter.view.MainView;
import com.gideondev.jotta.internal.di.component.DaggerProjectComponent;
import com.gideondev.jotta.internal.di.module.ProjectModule;
import com.google.firebase.auth.FirebaseAuth;
import in.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import java.util.List;
import javax.inject.Inject;

public class MainActivity
    extends BaseActionbarActivity
    implements MainView {

  @Inject
  MainPresenter mPresenter;
  private ActionBarDrawerToggle mDrawerToggle;
  private Toolbar toolbar;
  private LinearLayoutManager mLayoutManager;
  private FolderAdapter mFolderAdapter;
  private IndexFastScrollRecyclerView mRecyclerNote;
  private FolderModel mCurrentFolder;
  private NoteAdapter mNoteAdapter;
  private NoteDAO mNoteDao;
  private LinearLayoutManager mLayoutManagerNOTE;
  IndexFastScrollRecyclerView mRecyclerView;
  FragmentManager fm = getSupportFragmentManager();

  private ActivityMainBinding binding;
  private NoteCheckDAO mNoteCheckDao;


  @Override
  public void initView() {
    mRecyclerNote = (IndexFastScrollRecyclerView) findViewById(R.id.rec_note);

    mRecyclerNote.setIndexTextSize(12);
    mRecyclerNote.setIndexBarColor("#33334c");
    mRecyclerNote.setIndexBarCornerRadius(0);
    mRecyclerNote.setIndexBarTransparentValue((float) 0.4);
    mRecyclerNote.setIndexbarMargin(0);
    mRecyclerNote.setIndexbarWidth(40);
    mRecyclerNote.setPreviewPadding(0);
    mRecyclerNote.setIndexBarTextColor("#FFFFFF");

    mRecyclerNote.setIndexBarVisibility(true);
    mRecyclerNote.setIndexbarHighLateTextColor("#33334c");
    mRecyclerNote.setIndexBarHighLateTextVisibility(true);
    mLayoutManager = new LinearLayoutManager(this);
    mLayoutManagerNOTE = new LinearLayoutManager(this);

  }


  @Override
  public void initModel() {

  }

  @Override
  public Presenter getPresenter() {
    return mPresenter;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // ChangeThemeUtils.onActivityCreateSetTheme(getActivity(),getContext());
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
    iniActionbar();
    initView();
    injectInjector();
    binding.setPresenter(mPresenter);
    binding.setActivity(this);


  }

  private void iniActionbar() {
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("My notes");
  }

  private void initNavigationDrawer() {
    mDrawerToggle =
        new ActionBarDrawerToggle(this, binding.draw, toolbar, R.string.app_name,
            R.string.app_name) {

          @Override
          public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
          }

          @Override
          public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
          }
        }; // Drawer Toggle Object Made
    binding.draw.setDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();
    // ChangeThemeUtils.onActivityCreateSetHeaderColor( binding.customNav.headMain.headMain);
    if(FirebaseAuth.getInstance().getCurrentUser()!= null){
      if (FirebaseAuth.getInstance().getCurrentUser().getEmail() != null && !FirebaseAuth.getInstance().getCurrentUser().getEmail().isEmpty()){
        binding.customNav.headMain.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
      }else {
        binding.customNav.headMain.email.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
      }
      binding.customNav.lnLogin.setVisibility(View.GONE);
    }else {
      binding.customNav.lnLogin.setVisibility(View.VISIBLE);
      binding.customNav.lnLogout.setVisibility(View.GONE);
    }
    mPresenter.ShowFolderData();
  }

  public void SetDefaultFolderMyNote() {
    toolbar.setTitle("My notes");
    //  mCurrentFolder = model;
    mPresenter.ShowdataDefaultNote();
    binding.draw.closeDrawers();
    binding.addNewNote.setVisibility(View.VISIBLE);
    binding.deleteAllTrashNote.setVisibility(View.GONE);
  }

  public void SetShowTrashNote() {
    toolbar.setTitle("Trash");
    //  mCurrentFolder = model;
    mPresenter.ShowTrashNote();
    binding.draw.closeDrawers();
    binding.addNewNote.setVisibility(View.GONE);
    binding.deleteAllTrashNote.setVisibility(View.VISIBLE);
  }


  @Override
  public void SetUpFolderAdapter(List<FolderModel> modelList) {
    mFolderAdapter = new FolderAdapter(getContext(), modelList, new FolderListner() {
      @Override
      public void OnItemClick(FolderModel model, int p) {
        toolbar.setTitle(model.getTableName());
        mCurrentFolder = model;
        mPresenter.ShowdataPerFolder();
        binding.draw.closeDrawers();
        binding.addNewNote.setVisibility(View.VISIBLE);
        binding.deleteAllTrashNote.setVisibility(View.GONE);

      }
    });
    binding.customNav.recFolder.setLayoutManager(mLayoutManager);
    binding.customNav.recFolder.setAdapter(mFolderAdapter);
  }

  @Override
  public void SetUpNoteAdapter(List<NoteModel> modelList) {
    mNoteAdapter = new NoteAdapter(getContext(), modelList, new NoteListner() {
      @Override
      public void OnItemClick(NoteModel model, int p) {
        String TableName = String.valueOf(toolbar.getTitle());
        if (TableName.equals("Trash")){
          Toast.makeText(MainActivity.this, "Long click to restore note back", Toast.LENGTH_SHORT).show();
        }else{
          mPresenter.SetToUpdateNote(model);
        }
      }

      @Override
      public void OnLongClick(NoteModel model, int p) {
        MainMenuForListItemDialog(model);
      }
    });
    mRecyclerNote.setLayoutManager(mLayoutManagerNOTE);
    mRecyclerNote.setAdapter(mNoteAdapter);
  }

  @Override
  protected void injectInjector() {
    DaggerProjectComponent.builder()
        .projectModule(new ProjectModule(this))
        .build()
        .inject(this);
    mPresenter.setView(this);
    mPresenter.Start();
    mPresenter.CreateDefaultSettingsScreen();
    mPresenter.GetSettingsScreen();
    mPresenter.ShowdataPerFolder();
    initNavigationDrawer();


  }

  @Override
  public void closeDrawer(){
    binding.draw.closeDrawers();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    mNoteDao = new NoteDAO(getContext(), new SqliteDAOFactory(getContext()).getConnection());
    if (requestCode == 2) {
      if (!mPresenter.getNoteModelist().isEmpty()) {
        mPresenter.getNoteModelist().clear();
      }
      if (toolbar.getTitle() != null) {
        String tableName = String.valueOf(toolbar.getTitle());
        mPresenter.getNoteModelist()
            .addAll(mNoteDao.getNoteModelsByTableName(tableName));
        mPresenter.sortNoteByDate();
      }
      binding.customNav.defaultNoteCount.setText(mPresenter.GetDefaultNoteCount());
      binding.customNav.trashNoteCount.setText(mPresenter.GetRecycleNoteCount());
      mNoteAdapter.notifyDataSetChanged();
      mPresenter.RefreshFolderList();
     // mFolderAdapter.notifyDataSetChanged();

    }

    if (requestCode == 3) {
      mPresenter.GetSettingsScreen();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.create_folder, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.create_new_folder:
        MakeNewFolderDialog();
        break;
    }
    return super.onOptionsItemSelected(item);
  }




  private void MainMenuForListItemDialog(final NoteModel noteModel) {
    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    View dialogLayout = inflater.inflate(R.layout.custom_dialog_menu_list, null);
    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    Button btnMoveTo = (Button) dialogLayout.findViewById(R.id.note_move_to);
    Button btnDelete = (Button) dialogLayout.findViewById(R.id.note_delete);
    Button btnCopy = (Button) dialogLayout.findViewById(R.id.note_copy);
    Button btnCancel = (Button) dialogLayout.findViewById(R.id.cancel);
    builder.setView(dialogLayout);

    final AlertDialog customAlertDialog = builder.create();
    customAlertDialog.show();
    btnDelete.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mNoteDao = new NoteDAO(getContext(), new SqliteDAOFactory(getContext()).getConnection());
        String TableName = String.valueOf(toolbar.getTitle());

        if (TableName.equals("Trash")){
          mPresenter.CompleteDeleteOfNote(noteModel);
        }else {
          noteModel.setTableName("Trash");
          mNoteDao.updateNote(noteModel);
        }
        if (!mPresenter.getNoteModelist().isEmpty()) {
          mPresenter.getNoteModelist().clear();
        }
        if (toolbar.getTitle() != null) {

          String tableName = String.valueOf(toolbar.getTitle());
          mPresenter.getNoteModelist().addAll(mNoteDao.getNoteModelsByTableName(tableName));
        }
        binding.customNav.defaultNoteCount.setText(mPresenter.GetDefaultNoteCount());
        binding.customNav.trashNoteCount.setText(mPresenter.GetRecycleNoteCount());
        mNoteAdapter.notifyDataSetChanged();
        mPresenter.RefreshFolderList();
       // mFolderAdapter.notifyDataSetChanged();
        customAlertDialog.dismiss();

      }
    });
    btnMoveTo.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        FolderListFragment folderListFragment = FolderListFragment.newInstance(noteModel,
            new FolderListFragment.ItemClickListenter() {
              @Override
              public void onItemClick() {
                mNoteDao = new NoteDAO(getContext(), new SqliteDAOFactory(getContext()).getConnection());
                if (!mPresenter.getNoteModelist().isEmpty()) {
                  mPresenter.getNoteModelist().clear();
                }

                if (toolbar.getTitle() != null) {
                  String tableName = String.valueOf(toolbar.getTitle());
                  mPresenter.getNoteModelist().addAll(mNoteDao.getNoteModelsByTableName(tableName));
                  mPresenter.sortNoteByDate();
                }
                binding.customNav.defaultNoteCount.setText(mPresenter.GetDefaultNoteCount());
                binding.customNav.trashNoteCount.setText(mPresenter.GetRecycleNoteCount());
               mNoteAdapter.notifyDataSetChanged();
                mPresenter.RefreshFolderList();
             //  mFolderAdapter.notifyDataSetChanged();
              }
            });
        folderListFragment.show(fm, "");
        customAlertDialog.dismiss();
      }
    });
    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        customAlertDialog.dismiss();
      }
    });
  }

  private void MakeNewFolderDialog() {
    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    View dialogLayout = inflater.inflate(R.layout.custom_dialog_new_folder, null);
    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    Button btnNewfolder = (Button) dialogLayout.findViewById(R.id.new_folder);
    Button btnCancel = (Button) dialogLayout.findViewById(R.id.cancel);
    builder.setView(dialogLayout);

    final AlertDialog customAlertDialog = builder.create();
    customAlertDialog.show();

    btnNewfolder.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       // Toast.makeText(getContext(), "Create", Toast.LENGTH_LONG).show();
        customAlertDialog.dismiss();
        CreateNewFolderWithEditText();
      }
    });
    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        customAlertDialog.dismiss();
      }
    });
  }

  private void CreateNewFolderWithEditText() {
    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    View dialogLayout = inflater.inflate(R.layout.custom_dialog_new_folder_edit_text, null);
    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    TextView title = new TextView(MainActivity.this);
    final EditText mNewFolderEditText =
        (EditText) dialogLayout.findViewById(R.id.new_folder_edit_text);
    title.setText(getApplicationContext().getResources().getString(R.string.new_folder));
    LinearLayout.LayoutParams lp =
        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);
    title.setPadding(50, 50, 0, 0);
    title.setLayoutParams(lp);
    title.setTextSize(20);
    title.setTypeface(title.getTypeface(), Typeface.BOLD);
    title.setTextColor(getApplicationContext().getResources()
        .getColor(R.color.dialog_title_black));
    builder.setCustomTitle(title);
    builder.setView(dialogLayout);
    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
        dialog.dismiss();
      }
    });

    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {
        // TODO Auto-generated method stub
        Toast.makeText(getContext(),
            "Save successfully",
            Toast.LENGTH_LONG).show();
        String folderName = mNewFolderEditText.getText().toString();
        mPresenter.AddNewFolder(folderName);
      }
    });

    AlertDialog customAlertDialog = builder.create();
    customAlertDialog.show();
    Button btnPositive = customAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
    Button btnNegative = customAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
    btnNegative.setTextColor(getApplicationContext().getResources()
        .getColor(R.color.dialog_button_black));
    btnPositive.setTextColor(getApplicationContext().getResources()
        .getColor(R.color.dialog_button_black));
    btnNegative.setTextSize(14);
    btnPositive.setTypeface(btnPositive.getTypeface(), Typeface.BOLD);
    btnNegative.setTypeface(btnPositive.getTypeface(), Typeface.BOLD);

    LinearLayout.LayoutParams layoutParams =
        (LinearLayout.LayoutParams) btnPositive.getLayoutParams();
    //layoutParams.weight = 5;
    btnPositive.setLayoutParams(layoutParams);
    btnNegative.setLayoutParams(layoutParams);
  }


  @Override
  public void showLineLoading() {

  }

  public void SetUpClearTrash() {
    mNoteDao = new NoteDAO(getContext(), new SqliteDAOFactory(getContext()).getConnection());
    mPresenter.CleanUpTrash();
    if (!mPresenter.getNoteModelist().isEmpty()) {
      mPresenter.getNoteModelist().clear();
    }
    if (toolbar.getTitle() != null) {
      String tableName = String.valueOf(toolbar.getTitle());
      mPresenter.getNoteModelist().addAll(mNoteDao.getNoteModelsByTableName(tableName));
    }
    binding.customNav.defaultNoteCount.setText(mPresenter.GetDefaultNoteCount());
    binding.customNav.trashNoteCount.setText(mPresenter.GetRecycleNoteCount());
    mNoteAdapter.notifyDataSetChanged();
   mPresenter.RefreshFolderList();
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();

  }

  @Override
  public void NotifyFolderDataSetChange() {
    mFolderAdapter.notifyDataSetChanged();
  }

  @Override
  public void hideLineLoading() {

  }

  @Override
  public FolderModel getCurrentFolder() {
    if (mCurrentFolder != null) {
      return mCurrentFolder;
    } else {
      return null;
    }
  }

  @Override
  public Activity getActivity() {
    return this;
  }

  @Override
  public void showError(String message) {

  }

  @Override
  public Context getContext() {
    return this;
  }

  public void CustomTheme() {
    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
    View dialogLayout = inflater.inflate(R.layout.custom_theme_layout, null);
    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
    ImageView mGrayTheme = (ImageView) dialogLayout.findViewById(R.id.gray_theme);
    ImageView mYellowTheme = (ImageView) dialogLayout.findViewById(R.id.yellow_theme);
    ImageView mPinkTheme = (ImageView) dialogLayout.findViewById(R.id.pink_theme);
    ImageView mGreenTheme = (ImageView) dialogLayout.findViewById(R.id.green_theme);
    ImageView mBlueTheme = (ImageView) dialogLayout.findViewById(R.id.light_blue_theme);
    Button btnCancel = (Button) dialogLayout.findViewById(R.id.close);
    builder.setView(dialogLayout);

    final AlertDialog customAlertDialog = builder.create();
    customAlertDialog.show();

    mGrayTheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setTheme(R.style.GrayTheme);
        PreferenUtil.getInstant(getContext()).SaveTheme("gray");
        //for restart activity to change theme
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
      }
    });

    mYellowTheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setTheme(R.style.YellowTheme);
        PreferenUtil.getInstant(getContext()).SaveTheme("yellow");
        //for restart activity to change theme
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
      }
    });

    mPinkTheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setTheme(R.style.PinkTheme);
        PreferenUtil.getInstant(getContext()).SaveTheme("pink");
        //for restart activity to change theme
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
      }
    });

    mGreenTheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setTheme(R.style.GreenTheme);
        PreferenUtil.getInstant(getContext()).SaveTheme("green");
        //for restart activity to change theme
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
      }
    });

    mBlueTheme.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        setTheme(R.style.BlueTheme);
        PreferenUtil.getInstant(getContext()).SaveTheme("blue");
        //for restart activity to change theme
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
      }
    });

    btnCancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        customAlertDialog.dismiss();
      }
    });
  }
}
