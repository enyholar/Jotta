package com.gideondev.jotta.feature.main.noteFolderList;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.R;
import com.gideondev.jotta.databases.dao.FolderDAO;
import com.gideondev.jotta.databases.dao.NoteDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.feature.main.adapter.FolderListner;
import com.gideondev.jotta.feature.main.adapter.MoveToFolderAdapter;
import java.util.ArrayList;
import java.util.List;

public class FolderListFragment
    extends DialogFragment {
    private NoteModel mNoteModel;

    // TODO: Rename and change types of parameters
    private OnFragmentInteractionListener mListener;
    private MoveToFolderAdapter mFolderAdapter;
    private RecyclerView recFolder;
    private FolderDAO mFolderDao;
    private List<FolderModel>mFolderList = new ArrayList<>();
    private NoteDAO mNoteDao;
    private ItemClickListenter mItemClickListener;

    public FolderListFragment() {
        // Required empty public constructor
        mItemClickListener = null;
    }

    @SuppressLint("ValidFragment")
    public FolderListFragment(ItemClickListenter listenter) {
        mItemClickListener = listenter;
    }

    public static FolderListFragment newInstance(NoteModel model,ItemClickListenter listenter) {
        FolderListFragment fragment = new FolderListFragment(listenter);
        Bundle args = new Bundle();
        args.putSerializable("model",model);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        start();
        readArgs(savedInstanceState);
        getFolderList();
        SetUpFolderAdapter(mFolderList);
    }

    private void readArgs(Bundle savedInstanceState) {
        Bundle source = getArguments() != null ? getArguments() : savedInstanceState;
        mNoteModel = (NoteModel) source.getSerializable("model");
    }

    public void SetUpFolderAdapter(List<FolderModel> modelList) {
        mFolderAdapter = new MoveToFolderAdapter(getContext(), modelList, new FolderListner() {
            @Override
            public void OnItemClick(FolderModel model, int p) {
                moveToFolder(model,mNoteModel);

            }
        });

        recFolder.setAdapter(mFolderAdapter);
    }

    public void moveToFolder(FolderModel folderModel,NoteModel model){
        if (mNoteModel == null){
            return;
        }
        model.setTableName(folderModel.getTableName());
        mNoteDao.updateNote(model);
        if(mItemClickListener != null){
            mItemClickListener.onItemClick();
        }
        dismiss();

    }

  public void moveToDefaultFolder(NoteModel model){
    if (mNoteModel == null){
      return;
    }
    model.setTableName("My notes");
    mNoteDao.updateNote(model);
    if(mItemClickListener != null){
      mItemClickListener.onItemClick();
    }
    dismiss();

  }

    public void start(){
        mFolderDao = new FolderDAO(getContext(), new SqliteDAOFactory(getContext()).getConnection());
        mNoteDao = new NoteDAO(getContext(), new SqliteDAOFactory(getContext()).getConnection());
    }

    public void getFolderList(){
        mFolderList = mFolderDao.getFolderModels();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);
        recFolder = (RecyclerView) view.findViewById(R.id.rec_folder);
        view.findViewById(R.id.fold_my_note).setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View view) {
            moveToDefaultFolder(mNoteModel);
          }
        });
        return view;
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public  interface ItemClickListenter{
        void onItemClick();
    }
}
