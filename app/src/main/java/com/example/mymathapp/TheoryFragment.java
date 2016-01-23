package com.example.mymathapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
















import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TheoryFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link TheoryFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class TheoryFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String THEORY_TEXT = "theory_text";
	private static final String ARG_PARAM2 = "param2";
	private static final String IMAGE_URI = "image_uri";
	private static final String USER_ID = "user_id";
	private static final String VISITOR ="visitor";

	// TODO: Rename and change types of parameters
	private String theory_text;
	private String mParam2;
	private int image_uri;
	private int user_id;
	private int visitor;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param theory_text
	 * @param param2
	 * @param image_uri
	 * @param user_id
	 * @param visitor
	 *
	 * @return A new instance of fragment TheoryFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TheoryFragment newInstance(String theory_text, String param2, int image_uri, int user_id, int visitor) {
		TheoryFragment fragment = new TheoryFragment();
		Bundle args = new Bundle();
		args.putString(THEORY_TEXT, theory_text);
		args.putString(ARG_PARAM2, param2);
		args.putInt(IMAGE_URI, image_uri);
		args.putInt(USER_ID,user_id);
		args.putInt(VISITOR,visitor);
		fragment.setArguments(args);
		return fragment;
	}

	public TheoryFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		
		if (getArguments() != null) {
			theory_text = getArguments().getString(THEORY_TEXT);
			mParam2 = getArguments().getString(ARG_PARAM2);
			image_uri = getArguments().getInt(IMAGE_URI);
			user_id = getArguments().getInt(USER_ID);
			visitor = getArguments().getInt(VISITOR);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		// Add textview 1
		View view = inflater.inflate(R.layout.fragment_theory, container, false);
		System.out.println("This is TheoryFragment and user_id is: "+user_id);
		
		TextView t1 = (TextView) view.findViewById(R.id.textView1);
//		int imageResource = getResources().getIdentifier(IMAGE_URI, null, getActivity().getPackageName());
		ImageView imageView= (ImageView)view.findViewById(R.id.image);
		imageView.setImageResource(image_uri);
//		Activity act = getActivity();
//		Drawable res = act.getDrawable(imageResource);
//		
//		imageView.setImageDrawable(res);
		t1.setText(theory_text);

	    return view;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}
	}
	
	void showDialogInfo(String message,String stats) {
		DialogFragment info_dialog = InfoDialogFragment.newInstance(message,stats,user_id);
	    info_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {  
		if (visitor==0){
			// hide login button
			menu.findItem(R.id.login).setVisible(false).setEnabled(false);
		}
		if (visitor==1){
			//	hide logout button
			menu.findItem(R.id.logout).setVisible(false).setEnabled(false);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		boolean show_info_message = true;
		
		if (id==R.id.info){
//			Toast.makeText(this.getApplicationContext()
//					, "This is info",
//     			   Toast.LENGTH_LONG).show();
			showDialogInfo("Διάβασε τη θεωρία.","no_stats");
		
			return false;
		}
		if (id==R.id.stats){
			showDialogInfo("dont_care","user_stats");
			return true;
		}
		
		if (id==R.id.logout){
			visitor=1;
			user_id = 0;
//			try {
//				this.finalize();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			visitor=1;
			user_id=0;
//			try {
//				this.finalize();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			getActivity().getSupportFragmentManager().beginTransaction()
					.replace(R.id.fragment_container, ChapterFragment.newInstance(user_id, visitor)).addToBackStack(null)
					.commit();
			Toast.makeText(getActivity()
					, "Αποσυνδεθήκατε επιτυχώς!",
					Toast.LENGTH_LONG).show();
			return true;
		}
		
		if (id==R.id.login){
			visitor=0;

			showDialog();
			
			return true;
		}
		
		if (id==R.id.exit){
			
			getActivity().finish();
		}
				
		return super.onOptionsItemSelected(item);
	}

	
	void showDialog() {
	    DialogFragment user_dialog = new UserDialogFragment();
	    user_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    ((MainActivity)getActivity()).getSupportActionBar()
	        .setTitle(mParam2+"-Θεωρία");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
