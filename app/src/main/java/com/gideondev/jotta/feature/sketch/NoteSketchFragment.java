package com.gideondev.jotta.feature.sketch;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import com.afollestad.materialdialogs.MaterialDialog;
import com.gideondev.jotta.R;
import com.gideondev.jotta.utils.AutoClearedValue;
import com.gideondev.jotta.utils.view.OnDrawChangedListener;
import com.gideondev.jotta.utils.view.SketchViews;
import com.gideondev.jotta.databinding.FragmentNoteSketchBinding;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener;
import com.larswerkman.holocolorpicker.OpacityBar;
import com.larswerkman.holocolorpicker.SVBar;
import it.feio.android.checklistview.utils.AlphaManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NoteSketchFragment extends Fragment implements OnDrawChangedListener {

  private AutoClearedValue<FragmentNoteSketchBinding> binding;
  private int seekBarStrokeProgress, seekBarEraserProgress;
  private View popupLayout, popupEraserLayout;
  private ImageView strokeImageView, eraserImageView;
  private ColorPicker mColorPicker;
  public static final String ARGS_FRAGMENT_FILE_PATH = "file_path";
  private String filePath;

  private int oldColor;

  FragmentNoteSketchBinding dataBinding;
  private int size;

  public NoteSketchFragment() {
    // Required empty public constructor
  }

  public static NoteSketchFragment newInstance(String filePath) {
    NoteSketchFragment fragment = new NoteSketchFragment();
    Bundle bundle = new Bundle();
    bundle.putString(ARGS_FRAGMENT_FILE_PATH, filePath);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (getArguments() != null) {
      filePath = getArguments().getString(ARGS_FRAGMENT_FILE_PATH);
    }
    if (filePath != null && !filePath.isEmpty()){
     // Uri uri = FileProvider.getUriForFile(this,getContext().getPackageName(), filePath);
      Uri uri = Uri.parse(filePath);
     // Uri.fromFile(new File(filePath);
      if (uri != null) {
       // Bitmap bmp;

          try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),
                Uri.fromFile(new File(filePath)));
            binding.get().drawing.setBackgroundBitmap(getActivity(), bitmap);
          } catch (IOException e) {
            Toast.makeText(getContext(), "error",Toast.LENGTH_LONG).show();
            e.printStackTrace();
          }
       //   bmp = BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(uri));

      }
    }
    binding.get().drawing.setOnDrawChangedListener(this);

    binding.get().sketchStroke.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (binding.get().drawing.getMode() == binding.get().drawing.STROKE) {
          showPopup(view, (binding.get().drawing.STROKE));
        } else {
          binding.get().drawing.setMode(SketchViews.STROKE);
          AlphaManager.setAlpha(binding.get().eraserView, 0.4f);
          AlphaManager.setAlpha(binding.get().sketchStroke, 1f);
        }
      }
    });

    AlphaManager.setAlpha(binding.get().sketchEraser, 0.4f);
    binding.get().sketchEraser.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        if (binding.get().drawing.getMode() == SketchViews.ERASER) {
          showPopup(view, SketchViews.ERASER);
        } else {
          binding.get().drawing.setMode(SketchViews.ERASER);
          AlphaManager.setAlpha(binding.get().sketchStroke, 0.4f);
          AlphaManager.setAlpha(binding.get().sketchEraser, 1f);
        }
      }
    });

    binding.get().sketchUndo.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        binding.get().drawing.undo();
      }
    });
    binding.get().sketchRedo.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        binding.get().drawing.redo();
      }
    });

    binding.get().sketchErases.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        askForErase();
      }
    });

    // Inflate the popup_layout.xml
    LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity()
        .LAYOUT_INFLATER_SERVICE);
    popupLayout = inflater.inflate(R.layout.popup_sketch_stroke, null);
    // And the one for eraser
    LayoutInflater inflaterEraser = (LayoutInflater) getActivity().getSystemService(getActivity()
        .LAYOUT_INFLATER_SERVICE);
    popupEraserLayout = inflaterEraser.inflate(R.layout.popup_sketch_eraser, null);

    // Actual stroke shape size is retrieved
    strokeImageView = (ImageView) popupLayout.findViewById(R.id.stroke_circle);
    final Drawable circleDrawable = getResources().getDrawable(R.drawable.circle);
    size = circleDrawable.getIntrinsicWidth();
    // Actual eraser shape size is retrieved
    eraserImageView = (ImageView) popupEraserLayout.findViewById(R.id.stroke_circle);
    size = circleDrawable.getIntrinsicWidth();

    setSeekbarProgress(SketchViews.DEFAULT_STROKE_SIZE, SketchViews.STROKE);
    setSeekbarProgress(SketchViews.DEFAULT_ERASER_SIZE, SketchViews.ERASER);

    // Stroke color picker initialization and event managing
    mColorPicker = (ColorPicker) popupLayout.findViewById(R.id.stroke_color_picker);
    mColorPicker.addSVBar((SVBar) popupLayout.findViewById(R.id.svbar));
    mColorPicker.addOpacityBar((OpacityBar) popupLayout.findViewById(R.id.opacitybar));
    mColorPicker.setOnColorChangedListener(new OnColorChangedListener() {
      @Override
      public void onColorChanged(int color) {
        binding.get().drawing.setStrokeColor(color);
      }
    });
    //  mColorPicker.setOnColorChangedListener(mSketchView::setStrokeColor);
    mColorPicker.setColor(binding.get().drawing.getStrokeColor());
    mColorPicker.setOldCenterColor(binding.get().drawing.getStrokeColor());
  }

  private void askForErase() {
    new MaterialDialog.Builder(getActivity())
        .content(R.string.erase_sketch)
        .positiveText(R.string.confirm)
        .callback(new MaterialDialog.ButtonCallback() {
          @Override
          public void onPositive(MaterialDialog dialog) {
            binding.get().drawing.erase();
          }
        })
        .build().show();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    FragmentNoteSketchBinding dataBinding = DataBindingUtil
        .inflate(inflater, R.layout.fragment_note_sketch, container, false);
    binding = new AutoClearedValue<>(this, dataBinding);
    return dataBinding.getRoot();
  }


  @Override
  public void onDrawChanged() {
    // Undo
    if (binding.get().drawing.getPaths().size() > 0) {
      AlphaManager.setAlpha(binding.get().sketchUndo, 1f);
    } else {
      AlphaManager.setAlpha(binding.get().sketchUndo, 0.4f);
    }
    // Redo
    if (binding.get().drawing.getUndoneCount() > 0) {
      AlphaManager.setAlpha(binding.get().sketchRedo, 1f);
    } else {
      AlphaManager.setAlpha(binding.get().sketchRedo, 0.4f);
    }
  }

  // The method that displays the popup.
  private void showPopup(View anchor, final int eraserOrStroke) {

    boolean isErasing = eraserOrStroke == SketchViews.ERASER;

    oldColor = mColorPicker.getColor();

    DisplayMetrics metrics = new DisplayMetrics();
    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

    // Creating the PopupWindow
    PopupWindow popup = new PopupWindow(getActivity());
    popup.setContentView(isErasing ? popupEraserLayout : popupLayout);
    popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
    popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    popup.setFocusable(true);
    popup.setOnDismissListener(new OnDismissListener() {
      @Override
      public void onDismiss() {
        if (mColorPicker.getColor() != oldColor) {
          mColorPicker.setOldCenterColor(oldColor);
        }

      }
    });

    // Clear the default translucent background
    popup.setBackgroundDrawable(new BitmapDrawable());

    // Displaying the popup at the specified location, + offsets (transformed
    // dp to pixel to support multiple screen sizes)
    popup.showAsDropDown(anchor);

    // Stroke size seekbar initialization and event managing
    SeekBar mSeekBar;
    mSeekBar = (SeekBar) (isErasing ? popupEraserLayout
        .findViewById(R.id.stroke_seekbar) : popupLayout
        .findViewById(R.id.stroke_seekbar));
    mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
      }


      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
      }


      @Override
      public void onProgressChanged(SeekBar seekBar, int progress,
          boolean fromUser) {
        // When the seekbar is moved a new size is calculated and the new shape
        // is positioned centrally into the ImageView
        setSeekbarProgress(progress, eraserOrStroke);
      }
    });
    int progress = isErasing ? seekBarEraserProgress : seekBarStrokeProgress;
    mSeekBar.setProgress(progress);
  }

  protected void setSeekbarProgress(int progress, int eraserOrStroke) {
    int calcProgress = progress > 1 ? progress : 1;

    int newSize = Math.round((size / 100f) * calcProgress);
    int offset = (size - newSize) / 2;
    Log.v("", "Stroke size " + newSize + " (" + calcProgress + "%)");

    LayoutParams lp = new LayoutParams(newSize, newSize);
    lp.setMargins(offset, offset, offset, offset);
    if (eraserOrStroke == SketchViews.STROKE) {
      strokeImageView.setLayoutParams(lp);
      seekBarStrokeProgress = progress;
    } else {
      eraserImageView.setLayoutParams(lp);
      seekBarEraserProgress = progress;
    }

    binding.get().drawing.setSize(newSize, eraserOrStroke);
  }


  public void save() {
    Bitmap bitmap = binding.get().drawing.getBitmap();
    if (bitmap != null) {
      try {
        String folder_main = "Jotta";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File f = new File(Environment.getExternalStorageDirectory(), folder_main);
        if (!f.exists()) {
          f.mkdirs();
        }
        File mypath=new File(f, "Jotta_" + timeStamp + ".png");
        FileOutputStream out = new FileOutputStream(mypath);
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
        out.close();
        if (mypath.exists()) {
          getNoteActionActivity().picturePath = mypath.getPath();
         // getMainActivity().sketchUri = uri;
        } else {
         // getMainActivity().showMessage(R.string.error, ONStyle.ALERT);
        }

      } catch (Exception e) {
        Log.e("", "Error writing sketch image data", e);
      }
    }
  }

  private NoteActionActivity getNoteActionActivity() {
    return (NoteActionActivity) getActivity();
  }

}
