package com.gideondev.jotta.feature.main.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.model.Reminder;
import com.gideondev.jotta.R;
import com.gideondev.jotta.databases.dao.ReminderNoteDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class NoteAdapter
    extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements SectionIndexer {

  private List<NoteModel> mNoteList;
  private NoteListner mListener;

  private Context mContext;
  private ArrayList<Integer> mSectionPositions;
  private ReminderNoteDAO mReminderDao;

  public NoteAdapter(Context context, List<NoteModel> folde, NoteListner listner) {
    mContext = context;
    this.mNoteList = folde;
    this.mListener = listner;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View noteView;
    noteView = inflater.inflate(R.layout.item_note_read, parent, false);
    return new ViewHolder(noteView);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    final int p = position;
    final NoteModel model = mNoteList.get(p);
    holder.mNoteText.setText(model.getNoteWord());
    holder.mNoteTime.setText(model.getTime());
    holder.mNoteTitle.setText(model.getTitle());
    setUpReminder(model,holder);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mListener.OnItemClick(model, p);
      }
    });

    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        mListener.OnLongClick(model, p);
        return true;
      }
    });
  }

  private void setUpReminder(NoteModel model, ViewHolder holder) {
    mReminderDao = new ReminderNoteDAO(mContext, new SqliteDAOFactory(mContext).getConnection());
    Reminder reminder = mReminderDao.getReminderByID(String.valueOf(model.getId()));
    if (reminder != null){
      setReminderRepeatInfo(holder,reminder.getRepeat(),reminder.getRepeatNo(),reminder.getRepeatType());
      setActiveImage(holder,reminder.getActive());
    }else {
      holder.mAlarmImage.setVisibility(View.GONE);
      holder.mAlarmRepeatDetails.setVisibility(View.GONE);
    }
  }

  private void setReminderRepeatInfo(ViewHolder holder,String repeat, String repeatNo, String repeatType) {
    if(repeat.equals("true")){
      holder.mAlarmRepeatDetails.setText("Every " + repeatNo + " " + repeatType + "(s)");
    }else if (repeat.equals("false")) {
      holder.mAlarmRepeatDetails.setText("Repeat Off");
    }
  }

  public void setActiveImage(ViewHolder holder, String active){
    if(active.equals("true")){
      holder.mAlarmImage.setImageResource(R.drawable.ic_notifications_active_black_24dp);
    }else if (active.equals("false")) {
      holder.mAlarmImage.setImageResource(R.drawable.ic_notifications_off_black_24dp);
    }
  }

  @Override
  public int getItemCount() {
    return null == mNoteList ? 0 : mNoteList.size();
  }

  @Override
  public Object[] getSections() {
    List<String> sections = new ArrayList<>(26);
    mSectionPositions = new ArrayList<>(26);
    for (int i = 0, size = mNoteList.size(); i < size; i++) {
      String section;
      if (mNoteList.get(i).getTitle() != null && !mNoteList.get(i).getTitle().isEmpty()) {
        section = String.valueOf(mNoteList.get(i).getTitle().charAt(0)).toUpperCase();
      } else {
        section = String.valueOf(mNoteList.get(i).getNoteWord().charAt(0)).toUpperCase();
      }
      if (!sections.contains(section)) {
        sections.add(section);
        Collections.sort(sections);
        mSectionPositions.add(i);
      }
    }
    return sections.toArray(new String[0]);
  }

  @Override
  public int getPositionForSection(int i) {
    return mSectionPositions.get(i);
  }

  @Override
  public int getSectionForPosition(int i) {
    return 0;
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    AppCompatTextView mAlarmRepeatDetails;
    AppCompatTextView mNoteTitle;
    AppCompatTextView mNoteText;
    AppCompatTextView mNoteTime;
    ImageView mAlarmImage;
   // LinearLayout mRootView;
    View mItemView;

    ViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
      mNoteText = (AppCompatTextView) itemView.findViewById(R.id.note_text);
      mNoteTitle = (AppCompatTextView) itemView.findViewById(R.id.note_text_title);
      mNoteTime = (AppCompatTextView) itemView.findViewById(R.id.time);
      mAlarmRepeatDetails = (AppCompatTextView) itemView.findViewById(R.id.repeat_details);
      mAlarmImage = (ImageView) itemView.findViewById(R.id.active_image);
   //   mRootView = (LinearLayout) itemView.findViewById(R.id.item_note);
    }

  }
}
