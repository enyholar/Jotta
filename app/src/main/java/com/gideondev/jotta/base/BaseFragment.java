package com.gideondev.jotta.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import com.gideondev.jotta.base.presenter.Presenter;

public abstract class BaseFragment extends Fragment
    implements View.OnClickListener {



//	protected ContactModel mContactModel;

	protected AppApplication mApplication;

//	protected DAOFactory mSqliteDAO;


	protected FragmentManager mFragmentManager;

//	protected ImageLoader mImageLoader = ImageLoader.getInstance();

	protected Handler mHandler;

//	public DisplayImageOptions mOptions;

	public abstract Presenter getPresenter();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//mActivity = (DoctorSignUpActivity) getActivity();



		mApplication = (AppApplication) getActivity().getApplication();

//		mSqliteDAO = DAOFactory.getDAOFactory(getActivity()
//				.getApplicationContext(), DAOFactory.SQLITE_DATABASE);

		mFragmentManager = getFragmentManager();

		mHandler = new Handler();

//		mOptions = new DisplayImageOptions.Builder()
//				.showImageOnLoading(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.noimage)
//				.showImageOnFail(R.drawable.noimage).cacheInMemory(true)
//				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public void onStart() {
		super.onStart();
	}


	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onClick(View v) {

	}

	abstract protected void injectInjector() ;


	@Override
	public void onResume() {
		super.onResume();

		if (null != getPresenter()) {
			getPresenter().resume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (null != getPresenter()) {
			getPresenter().pause();
		}
	}

	@Override
	public void onDestroy() {
		if (null != getPresenter()) {
			getPresenter().destroy();
		}
		super.onDestroy();
	}


	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		// DoctorSignUpActivity mainActivity = (DoctorSignUpActivity) getActivity();
		// mainActivity.setTitleText(title);
	}

	/**
	 * Init all model when onCreate activity here
	 */
	public abstract void initModels();

	/**
	 * Init all views when onCreate activity here
	 */
	public abstract void initViews(View view);

	/**
	 * Remove previous show dialog fragment by tag
	 * 
	 * @param tag
	 *            tag of dialog fragment
	 */
	protected void removePreviousDialog(String tag) {
		// DialogFragment.show() will take care of adding the fragment
		// in a transaction. We also want to remove any currently showing
		// dialog, so make our own transaction and take care of that here.
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		Fragment prev = mFragmentManager.findFragmentByTag(tag);
		if (prev != null) {
			ft.remove(prev);
		}
		ft.commit();
	}

	// /**
	// * show alert message
	// *
	// * @param message
	// */
	// public void showAlertDialog(Context context, String message) {
	// // clear all state previous
	// removePreviousDialog("alert_dialog");
	// mAlertDialog = null;
	// // create dialog
	// mAlertDialog = AlertDialogFragment.newInstance(
	// getString(R.string.app_name), message,
	// getString(android.R.string.ok), getString(android.R.string.ok));
	// mAlertDialog.showOnlyOneButton(true);
	// // show dialog
	// mAlertDialog.show(mFragmentManager, "alert_dialog");
	// }

	// /**
	// * show alert
	// *
	// * @param context
	// * @param title
	// * @param message
	// * @param leftText
	// * @param rightText
	// * @param listener
	// */
	// public void showAlertDialog(Context context, String title, String
	// message,
	// String leftText, String rightText, AlertListener listener) {
	// // clear all state previous
	// removePreviousDialog("alert_dialog");
	// mAlertDialog = null;
	// // create dialog
	// mAlertDialog = AlertDialogFragment.newInstance(context, title, message,
	// leftText, rightText, listener);
	// // show dialog
	// mAlertDialog.show(mFragmentManager, "alert_dialog");
	// }

	/**
	 * Finish current fragment
	 */
	public void finishFragment() {
		try {
			mFragmentManager.popBackStack();
		} catch (Exception e) {
		//	LogUtils.e(e.getMessage());
		}
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
//		super.onCreateOptionsMenu(menu, inflater);
	}
}
