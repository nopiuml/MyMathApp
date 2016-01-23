package com.example.mymathapp;

import java.util.ArrayList;

import com.example.mymathapp.db.DBHelper;
import com.example.mymathapp.db.User;
import com.example.mymathapp.db.UserStats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link InfoDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events. Use the {@link InfoDialogFragment#newInstance} factory
 * method to create an instance of this fragment.
 *
 */
public class InfoDialogFragment extends DialogFragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	
	private static final String ARG_PARAM2 = "param2";
	
	private static final String USER_ID = "user_id";
	
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private int user_id;
	DBHelper mydb;
	UserStats user_stats;
	ArrayList<UserStats> us_st = new ArrayList<UserStats>();
	ArrayList<String> chapter = new ArrayList<String>();
	ArrayList<Integer> correct_ans_pr = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_pr = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_pr = new ArrayList<Integer>();
	ArrayList<Integer> correct_ans_af = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_af = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_af = new ArrayList<Integer>();
	ArrayList<Integer> correct_ans_pol = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_pol = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_pol = new ArrayList<Integer>();
	ArrayList<Integer> correct_ans_d = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_d = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_d = new ArrayList<Integer>();
	ArrayList<Integer> correct_ans_af_n = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_af_n = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_af_n = new ArrayList<Integer>();
	ArrayList<Integer> correct_ans_pol_n = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_pol_n = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_pol_n = new ArrayList<Integer>();
	ArrayList<Integer> correct_ans_d_n = new ArrayList<Integer>();
	ArrayList<Integer> wrong_ans_d_n = new ArrayList<Integer>();
	ArrayList<Integer> blank_ans_d_n = new ArrayList<Integer>();
	ArrayList<Double> success=  new ArrayList<Double>();
	

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment InfoDialogFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static InfoDialogFragment newInstance(String param1, String param2, int user_id) {
		InfoDialogFragment fragment = new InfoDialogFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		args.putInt(USER_ID, user_id);
		fragment.setArguments(args);
		return fragment;
	}

	public InfoDialogFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
			user_id = getArguments().getInt(USER_ID);
		}
	}

	 /* (non-Javadoc)
	 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the Builder class for convenient dialog construction
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        mydb = new DBHelper(getActivity());
	        
	        if (mParam2=="user_stats"){
     		
     		   System.out.println("Info Dialog and USER_ID is:"+user_id);
     		   us_st = mydb.getUserStats(user_id);
     		   //if (!us_st.equals(null)){
     			   
	     		   int prosthesi = 0;
	     		   int afairesi = 0;
	     		   int polsmos = 0;
	     		   int diairesi = 0;
	     		   
	     		   for(int i=0;i<us_st.size();i++ ){
	     			   
	     			   chapter.add(us_st.get(i).getChapter());
	     			   
	     		   }
	     		   for (int i=0;i<chapter.size();i++){
	     			   
	     			   if (chapter.get(i).equalsIgnoreCase("Πρόσθεση")){
	     		   			//correct_ans_pr = mydb.getCorrectAnswers(1, user_id, "Πρόσθεση");
	     				   prosthesi = prosthesi+1;
	     			   }
	     			   if (chapter.get(i).equalsIgnoreCase("Αφαίρεση")){
	     		   			//correct_ans_pr = mydb.getCorrectAnswers(1, user_id, "Πρόσθεση");
	     				   afairesi = afairesi+1;
	     			   }
	     			   if (chapter.get(i).equalsIgnoreCase("Πολλαπλασιασμός")){
	     				   polsmos = polsmos+1;
	     			   }
	     			   if (chapter.get(i).equalsIgnoreCase("Διαίρεση")){
	     				   diairesi = diairesi+1;
	     			   }
	     		   }
	     		   
	     		   System.out.println(" InfoDialog => Prosthesi: "+prosthesi+" Afairesi: "+afairesi+" Polsmos: "+polsmos+" Diairesi: "+diairesi);
	     		   
	     		   if (prosthesi>0){
	     			   correct_ans_pr = mydb.getCorrectAnswers(1, user_id, "Πρόσθεση");
	     			   wrong_ans_pr = mydb.getWrongAnswers(1,user_id, "Πρόσθεση");
	     			   blank_ans_pr = mydb.getBlankAnswers(1,user_id, "Πρόσθεση");
	     		   }
	     		   
	     		   if (afairesi>0){
	     			   correct_ans_af = mydb.getCorrectAnswers(1, user_id, "Αφαίρεση");
	     			   wrong_ans_af= mydb.getWrongAnswers(1,user_id, "Αφαίρεση");
	     			   blank_ans_af = mydb.getBlankAnswers(1,user_id,"Αφαίρεση");
	     			   
	     		   }
	     		   if (polsmos>0){
	     			   correct_ans_pol = mydb.getCorrectAnswers(1, user_id, "Πολλαπλασιασμός");
	     			   wrong_ans_pol= mydb.getWrongAnswers(1,user_id, "Πολλαπλασιασμός");
	     			   blank_ans_pol = mydb.getBlankAnswers(1,user_id,"Πολλαπλασιασμός");
	     			   
	     		   }
	     		   if (diairesi>0){
	     			   correct_ans_d = mydb.getCorrectAnswers(1, user_id, "Διαίρεση");
	     			   wrong_ans_d= mydb.getWrongAnswers(1,user_id, "Διαίρεση");
	     			   blank_ans_d = mydb.getBlankAnswers(1,user_id,"Διαίρεση");
	     			   
	     		   }
	     		   
	     		  ScrollView scroll = new ScrollView(getActivity());
	     			RelativeLayout.LayoutParams scroll_params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	     			scroll.setLayoutParams(scroll_params);
	     			scroll.setFillViewport(true);
	     		   
	     		  LinearLayout ll = new LinearLayout(getActivity());      
	     	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	     	                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	     			ll.setLayoutParams(params);
	     	        ll.setOrientation(LinearLayout.VERTICAL);
	     	       
	     	        if (prosthesi>0){
	     	        	
	     	        	final TextView chapter = new TextView(getActivity());
	     		        final TextView correct_list = new TextView(getActivity());
	     			    final TextView wrong_list = new TextView(getActivity());
	     			    final TextView blank_list = new TextView(getActivity());
	     			    final TextView space_1 = new TextView(getActivity());
	     			    final TextView space_2 = new TextView(getActivity());
	     			    chapter.setText("Κεφάλαιο: Πρόσθεση");
	     			    space_1.setText(" ");
	     			    correct_list.setText("Απάντησες σωστά στις: "+correct_ans_pr);
	     			    wrong_list.setText("Απάντησες λάθος στις: "+wrong_ans_pr);
	     			   // blank_list.setText("Δεν απάντησες στις: "+blank_ans_pr);
	     			    space_2.setText(" ");
	     			   chapter.setTypeface(null, Typeface.BOLD_ITALIC);
	     			  ll.addView(chapter);
	     				ll.addView(space_1);
	   				    ll.addView(correct_list);
	   				    ll.addView(wrong_list);
	   				    ll.addView(blank_list);
  				    ll.addView(space_2);
	     		  }
	     		   
	     	       if (afairesi>0){
	    	        	
	    	        	final TextView chapter = new TextView(getActivity());
	    		        final TextView correct_list = new TextView(getActivity());
	    			    final TextView wrong_list = new TextView(getActivity());
	    			    final TextView blank_list = new TextView(getActivity());
	    			    final TextView space_1 = new TextView(getActivity());
	     			    final TextView space_2 = new TextView(getActivity());
	    			    chapter.setText("Κεφάλαιο: Αφαίρεση");
	     			    space_1.setText(" ");
	     			    for (Integer i:correct_ans_af){
	     			    	correct_ans_af_n.add(i-10);
	     			    }
	     			    for (Integer i:wrong_ans_af){
	     			    	wrong_ans_af_n.add(i-10);
	     			    }
	     			    for (Integer i:blank_ans_af){
	     			    	blank_ans_af_n.add(i-10);
	     			    }
	    			    correct_list.setText("Απάντησες σωστά στις: "+correct_ans_af_n);
	    			    wrong_list.setText("Απάντησες λάθος στις: "+wrong_ans_af_n);
	    			//    blank_list.setText("Δεν απάντησες στις: "+blank_ans_af_n);
	    			   chapter.setTypeface(null, Typeface.BOLD_ITALIC);
	    			   space_2.setText(" ");
	    			   ll.addView(chapter);
	     				ll.addView(space_1);
	   				    ll.addView(correct_list);
	   				    ll.addView(wrong_list);
	   				    ll.addView(blank_list);
	   				    ll.addView(space_2);

	    		  }
	     	       
	     	      if (polsmos>0){
	   	        	
	   	        	final TextView chapter = new TextView(getActivity());
	   		        final TextView correct_list = new TextView(getActivity());
	   			    final TextView wrong_list = new TextView(getActivity());
	   			    final TextView blank_list = new TextView(getActivity());
	   			 final TextView space_1 = new TextView(getActivity());
  			    final TextView space_2 = new TextView(getActivity());
	   			    chapter.setText("Κεφάλαιο: Πολλαπλασιασμός");
	   			    space_1.setText(" ");
	   			 for (Integer i:correct_ans_pol){
  			    	correct_ans_pol_n.add(i-20);
  			    }
  			    for (Integer i:wrong_ans_pol){
  			    	wrong_ans_pol_n.add(i-20);
  			    }
  			    for (Integer i:blank_ans_pol){
  			    	blank_ans_pol_n.add(i-20);
  			    }
	   			    correct_list.setText("Απάντησες σωστά στις: "+correct_ans_pol_n);
	   			    wrong_list.setText("Απάντησες λάθος στις: "+wrong_ans_pol_n);
	   			  // blank_list.setText("Δεν απάντησες στις: "+blank_ans_pol_n);
	   			   chapter.setTypeface(null, Typeface.BOLD_ITALIC);
	   			   space_2.setText(" ");
	   			   		ll.addView(chapter);
	     				ll.addView(space_1);
	   				    ll.addView(correct_list);
	   				    ll.addView(wrong_list);
	   				    ll.addView(blank_list);
    				    ll.addView(space_2);

	   		  }
	     	      
	     	     if (diairesi>0){
	  	        	
	  	        	final TextView chapter = new TextView(getActivity());
	  		        final TextView correct_list = new TextView(getActivity());
	  			    final TextView wrong_list = new TextView(getActivity());
	  			    final TextView blank_list = new TextView(getActivity());
	  			  final TextView space_1 = new TextView(getActivity());
   			    final TextView space_2 = new TextView(getActivity());
	  			    chapter.setText("Κεφάλαιο: Διαίρεση");
	  			    space_1.setText(" ");
	  			  for (Integer i:correct_ans_d){
   			    	correct_ans_d_n.add(i-30);
   			    }
   			    for (Integer i:wrong_ans_d){
   			    	wrong_ans_d_n.add(i-30);
   			    }
   			    for (Integer i:blank_ans_d){
   			    	blank_ans_d_n.add(i-30);
   			    }
	  			    correct_list.setText("Απάντησες σωστά στις: "+correct_ans_d_n);
	  			    wrong_list.setText("Απάντησες λάθος στις: "+wrong_ans_d_n);
	  			  //  blank_list.setText("Δεν απάντησες στις: "+blank_ans_d_n);
	  			   chapter.setTypeface(null, Typeface.BOLD_ITALIC);
	  			   space_2.setText(" ");
	  			   ll.addView(chapter);
	  			   ll.addView(space_1);
				    ll.addView(correct_list);
				    ll.addView(wrong_list);
				    ll.addView(blank_list);
				    ll.addView(space_2);

	     	     }
	     	     
	     	    
	     	     // an den exei lusei kamia askhsh
	     	     if(prosthesi==0 & afairesi==0 & diairesi==0 & polsmos==0){
		        	
	     	    	 builder.setMessage("Δεν έχεις λύσει καμία άσκηση!")
	     	    	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int id) {
	                        // FIRE ZE MISSILES!
	                 	   
	                 		
	                    }
	                });
	     	     }
		        //}
	     	     // an exei lusei kapoia ask apo kapoio kefalaio
	     	    if (prosthesi>0 | afairesi>0 | diairesi>0 | polsmos>0 ){
	     	    	User user = mydb.getSpecificUser(user_id);
	     	    	builder.setTitle("Στατιστικά επίδοσης '"+user.getUser_name()+"'");
	     	    	scroll.addView(ll);
		     	     builder.setView(scroll)
		     	    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                public void onClick(DialogInterface dialog, int id) {
		                    // FIRE ZE MISSILES!
		             	   
		             		
		                }
		            });
	     	    }
		        
	       	}
		    else{
		        	builder.setMessage(mParam1)
		        
		               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		                   public void onClick(DialogInterface dialog, int id) {
		                       // FIRE ZE MISSILES!
		                	   
		                		
		                   }
		               });
		      }
	        
	        // Create the AlertDialog object and return it
	        return builder.create();
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
