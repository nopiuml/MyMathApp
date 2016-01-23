//package com.example.mymathapp;
//
//import java.util.ArrayList;
//
//import com.example.mymathapp.db.DBHelper;
//import com.example.mymathapp.db.User;
//
//import android.app.Activity;
//import android.net.Uri;
//import android.os.Bundle;
//import android.app.Fragment;
//import android.graphics.Typeface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.LinearLayout.LayoutParams;
//
///**
// * A simple {@link Fragment} subclass. Activities that contain this fragment
// * must implement the {@link AllStatsFragment.OnFragmentInteractionListener}
// * interface to handle interaction events. Use the
// * {@link AllStatsFragment#newInstance} factory method to create an instance of
// * this fragment.
// *
// */
//public class AllStatsFragment extends Fragment {
//	// TODO: Rename parameter arguments, choose names that match
//	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//	private static final String CHAPTER = "chapter";
//	private static final String CORRECT_ANS = "correct_ans";
//	private static final String WRONG_ANS = "wrong_ans";
//	private static final String BLANK_ANS = "blank_ans";
//	private static final String SUCCESS = "success";
//	private static final String USER_ID = "user_id";
//	
//
//	// TODO: Rename and change types of parameters
//	private String[] chapter;
//	private ArrayList<Integer> correct_ans;
//	private ArrayList<Integer> wrong_ans;
//	private ArrayList<Integer> blank_ans;
//	private double success;
//	private int user_id;
//	private DBHelper mydb;
//	private User user;
//	
//	private OnFragmentInteractionListener mListener;
//
//	/**
//	 * Use this factory method to create a new instance of this fragment using
//	 * the provided parameters.
//	 *
//	 * @param param1
//	 *            Parameter 1.
//	 * @param param2
//	 *            Parameter 2.
//	 * @return A new instance of fragment AllStatsFragment.
//	 */
//	// TODO: Rename and change types and number of parameters
//	public static AllStatsFragment newInstance(String[] chapter, ArrayList<Integer> wrong_ans, ArrayList<Integer> correct_ans, ArrayList<Integer> blank_ans, int user_id, double success) {
//		AllStatsFragment fragment = new AllStatsFragment();
//		Bundle args = new Bundle();
//		args.putStringArray(CHAPTER, chapter);
//		args.putIntegerArrayList(WRONG_ANS, wrong_ans);
//		args.putIntegerArrayList(CORRECT_ANS, correct_ans);
//		args.putIntegerArrayList(BLANK_ANS, blank_ans);
//		args.putInt(USER_ID, user_id);
//		args.putDouble(SUCCESS, success);
//		fragment.setArguments(args);
//		return fragment;
//	}
//
//	public AllStatsFragment() {
//		// Required empty public constructor
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		if (getArguments() != null) {
//			chapter = getArguments().getStringArray(CHAPTER);
//			wrong_ans = getArguments().getIntegerArrayList(WRONG_ANS);
//			correct_ans = getArguments().getIntegerArrayList(CORRECT_ANS);
//			blank_ans = getArguments().getIntegerArrayList(BLANK_ANS);
//			user_id = getArguments().getInt(USER_ID);
//			success = getArguments().getDouble(SUCCESS);
//		}
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		// Inflate the layout for this fragment
//		//return inflater.inflate(R.layout.fragment_all_stats, container, false);
//		ScrollView scroll = new ScrollView(getActivity());
//		RelativeLayout.LayoutParams scroll_params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		scroll.setLayoutParams(scroll_params);
//		scroll.setFillViewport(true);
//		scroll.setBackgroundResource(R.drawable.green_chalk_board);
//		
//		LinearLayout ll = new LinearLayout(getActivity());      
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		ll.setLayoutParams(params);
//        ll.setOrientation(LinearLayout.VERTICAL);
////        ll.setBackgroundResource(R.drawable.green_chalk_board);
//        final int n_chapters = chapter.length;
//		
//        
//        // get Username of user_id
//		user = mydb.getSpecificUser(user_id);
//		
//		final TextView[] myTextViews = new TextView[n_chapters]; // create an empty array;
//
//		
//		// first of all add the title of the exercise
//		final TextView title_question = new TextView(getActivity());
//		title_question.setText("Στατιστικά επίδοσης του: "+user.getUser_name());
//		title_question.setTypeface(null, Typeface.BOLD_ITALIC);
//		title_question.setTextColor(getResources().getColor(R.color.LemonChiffon));
//		ll.addView(title_question);
//		
//		for (int i = 0; i < n_chapters; i++) {
//			
//		    // create a new textview
//			LinearLayout l = new LinearLayout(getActivity());
//			l.setLayoutParams(params);
//            l.setOrientation(LinearLayout.VERTICAL);
//           // EditText et = new EditText(getActivity());
//            
////            l.addView(et,lp);
//		    final TextView chapter = new TextView(getActivity());
//		    final TextView correct_answers = new TextView(getActivity());
//		    final TextView wrong_answers = new TextView(getActivity());
//		    final TextView blank_answers = new TextView(getActivity());
//		    final TextView success = new TextView(getActivity());
//		    // set some properties of rowTextView getor something
//		    rowTextView.setText(m_questions.get(i));
//		    rowTextView.setTextColor(getResources().getColor(R.color.LemonChiffon));
//		    
//		    System.out.println("im in FillTheGap and question_ids are: "+question_ids);
//
//		    // add the textview to the linearlayout
//		    
//		    l.addView(rowTextView);
//		    l.addView(rowEditText);
//		    
//		    ll.addView(l);
//		    
////		    view = ll;
//		    // save a reference to the textview for later
//		    myTextViews[i] = rowTextView;
//		    et[i] = rowEditText;
//		    
//		    
//		}
//		
//		return scroll;
//	}
//
//	// TODO: Rename method, update argument and hook method into UI event
//	public void onButtonPressed(Uri uri) {
//		if (mListener != null) {
//			mListener.onFragmentInteraction(uri);
//		}
//	}
//
//	@Override
//	public void onAttach(Activity activity) {
//		super.onAttach(activity);
//		try {
//			mListener = (OnFragmentInteractionListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
//	}
//
//	@Override
//	public void onDetach() {
//		super.onDetach();
//		mListener = null;
//	}
//
//	/**
//	 * This interface must be implemented by activities that contain this
//	 * fragment to allow an interaction in this fragment to be communicated to
//	 * the activity and potentially other fragments contained in that activity.
//	 * <p>
//	 * See the Android Training lesson <a href=
//	 * "http://developer.android.com/training/basics/fragments/communicating.html"
//	 * >Communicating with Other Fragments</a> for more information.
//	 */
//	public interface OnFragmentInteractionListener {
//		// TODO: Update argument type and name
//		public void onFragmentInteraction(Uri uri);
//	}
//
//}
