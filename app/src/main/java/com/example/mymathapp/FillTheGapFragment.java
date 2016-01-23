package com.example.mymathapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;






import org.json.JSONException;
import org.json.JSONObject;

import com.example.mymathapp.db.DBHelper;
import com.example.mymathapp.db.User;
import com.example.mymathapp.db.UserAnswers;
import com.example.mymathapp.db.UserStats;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link FillTheGapFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link FillTheGapFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class FillTheGapFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String EX_QUESTION = "ex_questions";
	private static final String EX_SOLUTION = "ex_solutions";
	private static final String USER_ID = "user_id";
	private static final String CHAPTER = "chapter";
	private static final String QUESTION_IDS = "question_ids";
	// TODO: Rename and change types of parameters
	private ArrayList<String> m_questions;
	private ArrayList<String> m_solutions;
	private int user_id;
	private String chapter;
	private ArrayList<Integer> question_ids;
	private int mStacklevel = 0;
	DBHelper mydb;

	private OnFragmentInteractionListener mListener;
	View view;
	UserAnswers user_answer;
	UserStats user_stats;
	ArrayList<UserStats> spec_user_stats = new ArrayList<UserStats>();
	ArrayList<String> chapter_stats = new ArrayList<String>();

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment FillTheGapFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static FillTheGapFragment newInstance(ArrayList<String> param1, ArrayList<String> param2, int user_id, String chapter, ArrayList<Integer> question_ids) {
		FillTheGapFragment fragment = new FillTheGapFragment();
		Bundle args = new Bundle();
		args.putStringArrayList(EX_QUESTION,param1);
		args.putStringArrayList(EX_SOLUTION,param2);
		args.putInt(USER_ID, user_id);
		args.putString(CHAPTER, chapter);
		args.putIntegerArrayList(QUESTION_IDS, question_ids);
		fragment.setArguments(args);
		return fragment;
	}

	public FillTheGapFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (getArguments() != null) {
			m_questions = getArguments().getStringArrayList(EX_QUESTION);
			m_solutions = getArguments().getStringArrayList(EX_SOLUTION);
			user_id = getArguments().getInt(USER_ID);
			chapter = getArguments().getString(CHAPTER);
			question_ids = getArguments().getIntegerArrayList(QUESTION_IDS);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ScrollView scroll = new ScrollView(getActivity());
		RelativeLayout.LayoutParams scroll_params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		scroll.setLayoutParams(scroll_params);
		scroll.setFillViewport(true);
		scroll.setBackgroundResource(R.drawable.green_chalk_board);
		
		LinearLayout ll = new LinearLayout(getActivity());      
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(params);
        ll.setOrientation(LinearLayout.VERTICAL);
//        ll.setBackgroundResource(R.drawable.green_chalk_board);
     
		// Inflate the layout for this fragment
		final int N = 10; // total number of textviews to add
		final int n_questions = m_questions.size();
		final int called = 0;
		
		
		
		final TextView[] myTextViews = new TextView[n_questions]; // create an empty array;
        final EditText[] et = new EditText[n_questions];

		
		// first of all add the title of the exercise
		final TextView title_question = new TextView(getActivity());
		title_question.setText("Βρείτε τα αποτελέσματα των πράξεων");
		title_question.setTypeface(null, Typeface.BOLD_ITALIC);
		title_question.setTextColor(getResources().getColor(R.color.LemonChiffon));
		ll.addView(title_question);
		
		for (int i = 0; i < n_questions; i++) {
			
		    // create a new textview
			LinearLayout l = new LinearLayout(getActivity());
			l.setLayoutParams(params);
            l.setOrientation(LinearLayout.HORIZONTAL);
           // EditText et = new EditText(getActivity());
            
//            l.addView(et,lp);
		    final TextView rowTextView = new TextView(getActivity());
		    final EditText rowEditText = new EditText(getActivity());
		    // set some properties of rowTextView or something
		    rowTextView.setText(m_questions.get(i));
		    rowTextView.setTextColor(getResources().getColor(R.color.LemonChiffon));
		    
		    System.out.println("im in FillTheGap and question_ids are: "+question_ids);
		    System.out.println("im in FillTheGap and solutions are: "+m_solutions);

		    // add the textview to the linearlayout
		    
		    l.addView(rowTextView);
		    System.out.println("Add rowTextView :"+rowTextView.getText().toString());
		    l.addView(rowEditText);
		    
		    ll.addView(l);
		    
//		    view = ll;
		    // save a reference to the textview for later
		    myTextViews[i] = rowTextView;
		    System.out.println(myTextViews[i].getText().toString());
		    et[i] = rowEditText;
		    
		    
		}
		final Button done = new Button(getActivity());
		done.setText("OK");
		done.setTextColor(getResources().getColor(R.color.LemonChiffon));
		done.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

            	// from here i get the values of user and save at database
            	//int numAnswers = 0;
            	//int numBlank = 0;
            	int numUserRows = 0;
            	mydb = new DBHelper(getActivity());
            	numUserRows = mydb.numberOfRowsOfUser();
            	int numUserStatsRows = mydb.numberOfRowsOfUserStats();
            	
            	ArrayList<Integer> answered = new ArrayList<Integer>();
            	ArrayList<Integer> blank = new ArrayList<Integer>();
            	ArrayList<Integer> correct = new ArrayList<Integer>();
            	ArrayList<Integer> wrong = new ArrayList<Integer>();
            	ArrayList<UserAnswers> user_answers_correct = new ArrayList<UserAnswers>();
            	ArrayList<UserAnswers> user_answers_wrong = new ArrayList<UserAnswers>();
              	ArrayList<User> user = new ArrayList<User>();
            	double success = 0.0;
            	
    			FragmentManager fragmentManager = getFragmentManager();

            	System.out.println(m_solutions);
            	
            	for (int i=0;i<et.length;i++){
            		
            		// first of all, how many questions are answered => how many edit texts are not empty
            		if (et[i].getText().toString().trim().length() != 0){

            			answered.add(i+1); 
            			if (et[i].getText().toString().equalsIgnoreCase(m_solutions.get(i))){
            				// if the answer that user gave is the same as the solution then error = 0
            				// question_id = i+1 because question_id starts at 3, user_id is the user given from the input, user_answer is the answer given by the user 
            				user_answer = mydb.getSpecificUserAnswer(user_id, question_ids.get(i));
            				System.out.println("This is fillin and user_answer is "+user_answer.getUser_id()+user_answer.getQuestion_id()+user_answer.getUser_answer());
            				if (!(user_answer.getUser_id()==0)){
            					mydb.updateUserAnswer(user_id, question_ids.get(i) ,et[i].getText().toString(), 0);
            					mydb.updateUserStats(user_id, chapter, question_ids.get(i), 1, 0, 0, 0.1);
            				}
            				if (user_answer.getUser_id()==0){
            					mydb.insertUserAnswer(user_id, question_ids.get(i), et[i].getText().toString(), 0);
            					mydb.insertUserStats(user_id, chapter, question_ids.get(i), 1, 0, 0, 0.1);
            					//async.execute(user_id, question_ids.get(i),et[i].getText().toString(),0);
            				}
            				correct.add(i+1);
            			}
            			if (!(et[i].getText().toString().equalsIgnoreCase(m_solutions.get(i)))){
            				// if the answer that user gave is different than the solution then error = 1
            				user_answer = mydb.getSpecificUserAnswer(user_id, question_ids.get(i));
            				if (!(user_answer.getUser_id()==0)){
            					mydb.updateUserAnswer(user_id, question_ids.get(i) ,et[i].getText().toString(), 1);
            					mydb.updateUserStats(user_id, chapter, question_ids.get(i), 0, 1, 0, 0);

            				}
            				if (user_answer.getUser_id()==0){
            					mydb.insertUserAnswer(user_id, question_ids.get(i), et[i].getText().toString(), 1);
            					mydb.insertUserStats(user_id, chapter, question_ids.get(i), 0, 1, 0, 0);

            				}
            				wrong.add(i+1);
            			}
            			
            		}
            		if (et[i].getText().toString().trim().length() == 0){
            			
            			spec_user_stats = mydb.getSpecificUserStats(user_id, chapter);
            			for(int j=0;j<spec_user_stats.size();j++){
            				chapter_stats.add(spec_user_stats.get(j).getChapter());
            			}
            			
            			if (!(chapter_stats.size()==0)){
        					//mydb.updateUserAnswer(user_id, question_ids.get(i) ,et[i].getText().toString(), 1);
        					mydb.updateUserStats(user_id, chapter, question_ids.get(i), 0, 0, 1, 0);
            			}
            			if (chapter_stats.size()==0){
        					//mydb.updateUserAnswer(user_id, question_ids.get(i) ,et[i].getText().toString(), 1);
        					mydb.insertUserStats(user_id, chapter, question_ids.get(i), 0, 0, 1, 0);
            			}
            			blank.add(i+1);
            		}
            	}
            	user_answers_correct = mydb.getCorrectUserAnswers(user_id, 0);
            	user_answers_wrong = mydb.getWrongUserAnswers(user_id, 1);

            	if (((correct.size()+wrong.size()+blank.size())!=0)){
            		success = (double)correct.size()/(correct.size()+wrong.size()+blank.size());
            		System.out.println("Success is: "+success);
            		System.out.println("(FillTheGap)USER_ID IS: ");
            		System.out.println(correct.size()+wrong.size()+blank.size());
            	}
            	     
            	if (blank.size()==10){
            		
            		showDialogInfo("Δεν απάντησες σε καμία ερώτηση! Ξαναπροσπάθησε!","no_stats");

            	}
            	if (blank.size()<10){
            		showDialog(correct, wrong, blank, success, user_id);
            	}
            }
        });
		ll.addView(done);
		scroll.addView(ll);
		view = scroll;
		return view;
	}

	void showDialog(ArrayList<Integer> correct, ArrayList<Integer> wrong, ArrayList<Integer> blank, double success, int user_id) {

	    // DialogFragment.show() will take care of adding the fragment
	    // in a transaction.  We also want to remove any currently showing
	    // dialog, so make our own transaction and take care of that here.
	    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
	    if (prev != null) {
	        ft.remove(prev);
	    }
	    //ft.addToBackStack(null);

	    // Create and show the dialog.
	    DialogFragment user_statistics = UserStatisticsFragment.newInstance(correct,  wrong,  blank,  success,  user_id, chapter);
	    user_statistics.setTargetFragment(this, 1);
//	    user_statistics.setTargetFragment(ChapterFragment.newInstance(user_id, 0), 2);
	    user_statistics.show(ft, "dialog");
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        switch(requestCode) {
	            case 1:

	                if (resultCode == Activity.RESULT_OK) {
	                    // After Ok code.
	                	 getActivity().getSupportFragmentManager().beginTransaction()
		                    .replace(R.id.fragment_container, SectionFragment.newInstance(chapter, user_id, 0)).addToBackStack(null).commit();
	                	
	                } else if (resultCode == Activity.RESULT_CANCELED){
	                    // After Cancel code.
	                	 getActivity().getSupportFragmentManager().beginTransaction()
		                    .replace(R.id.fragment_container, ChapterFragment.newInstance(user_id, 0)).addToBackStack(null).commit();
	                }
	           
	                break;
	        }
	}
	
	public void onBackPressed()
	{
	    FragmentManager fm = getActivity().getSupportFragmentManager();
	    fm.popBackStack();
	}
	
	
	void showDialogInfo(String message,String stats) {
		DialogFragment info_dialog = InfoDialogFragment.newInstance(message,stats,user_id);
	    info_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
	}
	
	
	public void onPrepareOptionsMenu(Menu menu) {  
		
			menu.findItem(R.id.logout).setVisible(false).setEnabled(false);
		
			menu.findItem(R.id.login).setVisible(false).setEnabled(false);
	    return;
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
			showDialogInfo("Συμπλήρωσε τα κενά και πατώντας το ΟΚ θα δεις το ποσοστό επιτυχίας σου! Οι σωστές απαντήσεις βαθμολογούνται με 1 μονάδα. Δεν υπάρχει ποινή για τις λάθος απαντήσεις.","no_stats");
		
			return false;
		}
		if (id==R.id.stats){
			showDialogInfo(String.valueOf(user_id),"user_stats");
		}
		if (id==R.id.logout){
			
			try {
				this.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.fragment_container, SectionFragment.newInstance(chapter, user_id, 1)).addToBackStack(null)
            .commit();
			Toast.makeText(getActivity()
					, "Αποσυνδεθήκατε επιτυχώς!",
     			   Toast.LENGTH_LONG).show();
			return true;
		}
		if (id==R.id.exit){
			
			getActivity().finish();
		}
		return super.onOptionsItemSelected(item);
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

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    ((MainActivity)getActivity()).getSupportActionBar()
	        .setTitle(chapter+"-Ασκήσεις");
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
