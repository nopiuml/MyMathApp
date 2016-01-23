package com.example.mymathapp;

import java.util.ArrayList;

import com.example.mymathapp.db.DBHelper;
import com.example.mymathapp.db.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link UserDialogFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link UserDialogFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class UserDialogFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	DBHelper mydb;
	private User user;
	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment UserDialogFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static UserDialogFragment newInstance(String param1, String param2) {
		UserDialogFragment fragment = new UserDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public UserDialogFragment() {
		// Required empty public constructor
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		
		
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
	    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	   
	    mydb = new DBHelper(getActivity());
	    user = new User();
	    final int numRowsUser = mydb.numberOfRowsOfUser();
	    // Inflate and set the layout for the dialog
	    // Pass null as the parent view because its going in the dialog layout
	    LinearLayout ll = new LinearLayout(getActivity());      
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(params);
        ll.setOrientation(LinearLayout.VERTICAL);
	    final EditText et = new EditText(getActivity());
	    et.setInputType(InputType.TYPE_CLASS_TEXT);
	    et.setHint("Εισάγετε όνομα χρήστη");
	    ll.addView(et);
	    
	    //inflater.inflate(R.layout.fragment_user_dialog, null)
	    builder.setTitle("Σύνδεση");
	    builder.setView(ll)
	    // Add action buttons
	           .setPositiveButton("Εισαγωγή", new DialogInterface.OnClickListener() {
	               @Override
	               public void onClick(DialogInterface dialog, int id) {

	            	   ArrayList<User> user = new ArrayList<User>();
	            	   
	            	   if (numRowsUser==0){
	            		   boolean inserted;
	            		   inserted = mydb.insertUser(et.getText().toString(), 1);
	            		   if (inserted){
	            			   System.out.println("THIS IS USER :"+et.getText().toString());
	            		   }
	            	   }
	            	   int same_user = 0;
	            	   if (numRowsUser!=0){
	            		   ArrayList<String> user_name = new ArrayList<String>();

	            		   // get USER NAMES to see if this is a new user
	            		   user_name = mydb.getUsernames();	            		   
	            		   for (int i=0;i<user_name.size(); i++){
	            			   System.out.println(user_name.get(i));
	            			   if ((et.getText().toString().equals(user_name.get(i)))){

	            				   same_user = same_user +1;
	            			   }
	            		   }
	            		   if (same_user==0){
	            			  
	            			   mydb.insertUser(et.getText().toString(),1);
	            			   
	            		   }
	            		   else{
	            			   Toast.makeText(getActivity(), "Καλωσήρθες '"+et.getText().toString()+"'!",
	                    			   Toast.LENGTH_LONG).show();
	            		   }
	            		   
	            	   }
	            	   
	            	   user = mydb.getUsers();
	            	   int user_id = 0;
	            	   //int user_id = user.get((user.size()-1)).getUser_id();
	            	   for (int i=0;i<user.size();i++){
	            		   
	            		   if (user.get(i).getUser_name().equals(et.getText().toString())){
	            			   user_id = user.get(i).getUser_id();
	            		   }
	            	   }
	            	   //System.out.println(user_id);
	            	   
	            	   // clear backstack
	            	   getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

					   // chapter fragment
					   getActivity().getSupportFragmentManager().beginTransaction()
	                    .add(R.id.fragment_container, ChapterFragment.newInstance(user_id, 0)).addToBackStack(null).commit();
	            	   Toast.makeText(getActivity()
	            				,  "Καλωσήρθες '"+et.getText().toString()+"'!",
	            				   Toast.LENGTH_LONG).show();
	            	   
	               }
	           })
	           .setNegativeButton("Ακύρωση", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {

					   // cancel dialog
	                   getDialog().cancel();
	                   
					   // clear backstack
					   getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

					   // go to chapter fragment
	            	   getActivity().getSupportFragmentManager().beginTransaction() // user_id = 0, visitor = 1
	                    .add(R.id.fragment_container, ChapterFragment.newInstance(0, 1)).addToBackStack(null).commit();

					   Toast.makeText(getActivity(), "Χρησιμοποιείς την εφαρμογή σαν επισκέπτης ",
							   Toast.LENGTH_LONG).show();
	               }
	           });      
	    return builder.create();
	}
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
