package com.example.mymathapp;

import java.util.ArrayList;

import com.example.mymathapp.db.DBHelper;
import com.example.mymathapp.db.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link UserStatisticsFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link UserStatisticsFragment#newInstance}
 * factory method to create an instance of this fragment.
 *
 */
public class UserStatisticsFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String CORRECT = "correct";
	private static final String WRONG = "wrong";
	private static final String BLANK = "blank";
	private static final String SUCCESS = "success";
	private static final String USER_ID = "user_id";
	private static final String CHAPTER = "chapter";

	// TODO: Rename and change types of parameters
	private ArrayList<Integer> correct;
	private ArrayList<Integer> wrong;
	private ArrayList<Integer> blank;
	private double success;
	private int user_id;
	private String chapter;
	
	DBHelper mydb;
	
	private User user;
	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param correct
	 * @param wrong
	 * @param blank
	 * @param success
	 * @param user_id
	 * @param chapter
	 *
	 *
	 * @return A new instance of fragment UserStatisticsFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static UserStatisticsFragment newInstance(ArrayList<Integer> correct, ArrayList<Integer> wrong, ArrayList<Integer> blank, 
			double success, int user_id, String chapter) {
		UserStatisticsFragment fragment = new UserStatisticsFragment();
		Bundle args = new Bundle();
		args.putIntegerArrayList(CORRECT, correct);
		args.putIntegerArrayList(WRONG, wrong);
		args.putIntegerArrayList(BLANK, blank);
		args.putDouble(SUCCESS, success);
		args.putInt(USER_ID, user_id);
		args.putString(CHAPTER, chapter);
		fragment.setArguments(args);
		return fragment;
	}

	public UserStatisticsFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments() != null) {
			correct = getArguments().getIntegerArrayList(CORRECT);
			wrong = getArguments().getIntegerArrayList(WRONG);
			blank = getArguments().getIntegerArrayList(BLANK);
			success = getArguments().getDouble(SUCCESS);
			user_id = getArguments().getInt(USER_ID);
			chapter = getArguments().getString(CHAPTER);
		}
	
	}
		
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	    // Get the layout inflater
	    LayoutInflater inflater = getActivity().getLayoutInflater();
	    mydb = new DBHelper(getActivity());
	    user = new User();
	    
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    LinearLayout ll = new LinearLayout(getActivity());      
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(params);
        ll.setOrientation(LinearLayout.VERTICAL);
        if (wrong.size()>0){
	        final TextView correct_list = new TextView(getActivity());
		    final TextView wrong_list = new TextView(getActivity());
		    final TextView blank_list = new TextView(getActivity());
		    final TextView success_n = new TextView(getActivity());
		    final TextView advice = new TextView(getActivity());
		    correct_list.setText("Απάντησες σωστά στις: "+correct);
		    wrong_list.setText("Απάντησες λάθος στις: "+wrong);
		    blank_list.setText("Δεν απάντησες στις: "+blank);
		    success_n.setText("Ποσοστό επιτυχίας: "+(success*100)+"%"); // opoiadhpote timh pol/smenh epi 100
		   
		    if (!chapter.equalsIgnoreCase("Επαναληπτικό Διαγώνισμα")){
		    	advice.setText("Ξανακοίταξε το κεφάλαιο: " +chapter);
		    }
			    ll.addView(correct_list);
			    ll.addView(wrong_list);
			    ll.addView(blank_list);
			    ll.addView(success_n);
			    ll.addView(advice);
        }
        if (wrong.size()==0){
	        final TextView correct_list = new TextView(getActivity());
		    final TextView wrong_list = new TextView(getActivity());
		    final TextView blank_list = new TextView(getActivity());
		    final TextView success_n = new TextView(getActivity());
		    final TextView advice = new TextView(getActivity());
		    correct_list.setText("Απάντησες σωστά στις: "+correct);
		    wrong_list.setText("Απάντησες λάθος στις: "+wrong);
		    blank_list.setText("Δεν απάντησες στις: "+blank);
		    success_n.setText("Ποσοστό επιτυχίας: "+(success*100)+"%"); // opoiadhpote timh pol/smenh epi 100
		   
//		    advice.setText("Ξανακοίταξε το κεφάλαιο :" +chapter);
		    
			    ll.addView(correct_list);
			    ll.addView(wrong_list);
			    ll.addView(blank_list);
			    ll.addView(success_n);
			    ll.addView(advice);

        }
        
      
	    user = mydb.getSpecificUser(user_id);
	    builder.setTitle("Αποτελέσματα χρήστη '"+user.getUser_name()+"'");
	    builder.setView(ll)
	    // Add action buttons
	           .setPositiveButton("Συνέχεια", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {

//	            	   
//	            	   getActivity().getSupportFragmentManager().beginTransaction()
//	                    .replace(R.id.fragment_container, SectionFragment.newInstance(chapter, user_id, 0)).addToBackStack(null).commit();
                       getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());

	            	   
	               }
	           })
	           .setNegativeButton("Πίσω", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   
//	            	   getActivity().getSupportFragmentManager().beginTransaction()
//	                    .replace(R.id.fragment_container, ChapterFragment.newInstance(user_id, 0)).addToBackStack(null).commit();
                       getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());

	               }
	           });      
	    return builder.create();
	}
	
	
	public void onBackPressed()
	{
	    FragmentManager fm = getActivity().getSupportFragmentManager();
	    fm.popBackStack();
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
