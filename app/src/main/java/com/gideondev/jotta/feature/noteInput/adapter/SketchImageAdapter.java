package com.gideondev.jotta.feature.noteInput.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.gideondev.jotta.model.Attach;
import com.gideondev.jotta.R;
import java.io.File;
import java.util.List;

/**
 * Created by ENNY on 1/17/2018.
 */

public class SketchImageAdapter extends BaseAdapter {
  private List<Attach> imagePaths;
  private Context context;
  private SketchImageListner sketchImageListner;

  public SketchImageAdapter( Context con,List<Attach> imagePath, SketchImageListner imageListner){
    this.sketchImageListner = imageListner;
    this.imagePaths = imagePath;
    this.context = con;
  }

  @Override
  public int getCount() {
    return imagePaths == null ? 0 : imagePaths.size();
  }

  @Override
  public Object getItem(int i) {
    return imagePaths == null ? null: imagePaths.get(i);
  }

  @Override
  public long getItemId(int i) {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup) {
    if (view == null) {
      final LayoutInflater layoutInflater = LayoutInflater.from(context);
      view = layoutInflater.inflate(R.layout.item_note_sketch_image, null);
    }
    final ImageView imageView = (ImageView)view.findViewById(R.id.img_sketch);
    final Attach attach = imagePaths.get(i);
    showImagefromFilePath(imageView,attach.attachPath);
    imageView.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View view) {
        sketchImageListner.Onclick(attach.attachPath);
        return true;
      }
    });
    return view;
  }

  private void showImagefromFilePath(ImageView mNoteImage, String filepath) {
    File imgFile = new File(filepath);

    if (imgFile.exists()) {

      Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
      mNoteImage.setVisibility(View.VISIBLE);
      mNoteImage.setImageBitmap(myBitmap);

    }
  }


}
