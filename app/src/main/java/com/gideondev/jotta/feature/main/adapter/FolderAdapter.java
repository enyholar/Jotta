package com.gideondev.jotta.feature.main.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gideondev.jotta.BR;
import com.gideondev.jotta.model.FolderModel;
import com.gideondev.jotta.model.NoteModel;
import com.gideondev.jotta.R;
import com.gideondev.jotta.databases.dao.NoteDAO;
import com.gideondev.jotta.databases.dao.SqliteDAOFactory;
import com.gideondev.jotta.databinding.ItemNoteFolderBinding;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Enny on 20/10/17.
 */

public class FolderAdapter
    extends RecyclerView.Adapter<FolderAdapter.RecyclerViewHolder>  {

    private List<FolderModel> mFolderList;
    private FolderListner mListener;

    private Context mContext;
    private NoteDAO mNoteDao;
    ItemNoteFolderBinding binding;

    public FolderAdapter(Context context, List<FolderModel> folde, FolderListner listener) {
        mContext = context;
        this.mFolderList = folde;
        this.mListener = listener;
    }

    @Override
    public FolderAdapter.RecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        binding= DataBindingUtil.inflate(inflater, R.layout.item_note_folder, parent, false);
        // set the view's size, margins, paddings and layout parameters
       // binding.setCallback(projectClickCallback);
        return new FolderAdapter.RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FolderAdapter.RecyclerViewHolder holder, int position) {
        final int p = position;
        final FolderModel model = mFolderList.get(p);
        holder.bind(model);
        binding.itemCount.setText(String.valueOf(notelistCount(model)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.OnItemClick(model, p);
            }
        });
    }

    public int notelistCount(FolderModel model){
        List<NoteModel> notelist = new ArrayList<>();
        mNoteDao = new NoteDAO(mContext, new SqliteDAOFactory(mContext).getConnection());
        notelist = mNoteDao.getNoteModelsByTableName(model.getTableName());
//        notifyDataSetChanged();
        return notelist.size();
    }

    @Override
    public int getItemCount() {
        return null == mFolderList ? 0 : mFolderList.size();
    }

    static class RecyclerViewHolder
        extends RecyclerView.ViewHolder {
        private ItemNoteFolderBinding mBinding;

        public RecyclerViewHolder(ItemNoteFolderBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public void bind(FolderModel folderModel) {
            mBinding.setVariable(BR.folderModel, folderModel);
            mBinding.executePendingBindings();
        }
    }
}
