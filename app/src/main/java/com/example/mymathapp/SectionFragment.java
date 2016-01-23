package com.example.mymathapp;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.mymathapp.db.DBHelper;
import com.example.mymathapp.db.Exercise;
import com.example.mymathapp.db.Solution;
import com.example.mymathapp.db.Theory;
import com.example.mymathapp.db.UserAnswers;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link SectionFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link SectionFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class SectionFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String CHAPTER = "chapter";
	private static final String USER_ID = "user_id";
	private static final String VISITOR = "visitor";

	// TODO: Rename and change types of parameters
	private String chapter;
	private int user_id;
	private int visitor;

	private OnFragmentInteractionListener mListener;

	DBHelper mydb;
	private Theory theory;
	private Solution solution_pr;
	private Solution solution_af;
	private Solution solution_pol;
	private Solution solution_d;
	private Solution solution_dyn;
	private ArrayList<String> ex_question = new ArrayList<String>();
	private ArrayList<String> ex_solution = new ArrayList<String>();
	private ArrayList<Exercise> ex = new ArrayList<Exercise>();
	
	
//	private ArrayList<String> ex_question = new ArrayList<String>();
//	private ArrayList<String> ex_solution = new ArrayList<String>();
	private ArrayList<String> user_answers_wrong_sol = new ArrayList<String>();
//	private ArrayList<Exercise> ex = new ArrayList<Exercise>();
	private Solution solution_test;
	private Solution solution_to_wrong;
	private ArrayList<String> user_answers_wrong_text = new ArrayList<String>();
	
	Exercise ex_obj  = new Exercise();

	/**
	 * 
	 */
	// TODO: Rename and change types and number of parameters

		
	public static SectionFragment newInstance(String chapter, int user_id, int visitor) {
		SectionFragment fragment = new SectionFragment();
		Bundle args = new Bundle();
		args.putString(CHAPTER, chapter);
		args.putInt(USER_ID, user_id);
		args.putInt(VISITOR, visitor);
		fragment.setArguments(args);
		return fragment;
	}

	public SectionFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);

		if (getArguments() != null) {
			chapter = getArguments().getString(CHAPTER);
			user_id = getArguments().getInt(USER_ID);
			visitor = getArguments().getInt(VISITOR);
		}
		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		

		
		View view = inflater.inflate(R.layout.fragment_section,
			    container, false);
		Button theory_bt = (Button) view.findViewById(R.id.theory);
		Button exercise = (Button) view.findViewById(R.id.exercise);
		//Button test = (Button) view.findViewById(R.id.test);
		mydb = new DBHelper(getActivity());
		theory = new Theory();

		
		if (chapter=="Πρόσθεση"){
			
		
			int chapter_id = 1;
			int theory_id = 1;
			final ArrayList<Integer> question_ids = new ArrayList<Integer>();
			int numRowsSol;
			
			
			theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
			ex_question = mydb.getAllExerciseTextOfTheory(theory_id);
			ex = mydb.getAllExercisesOfTheory(theory_id);
			
			// Now i want to get all question_ids. => So, from ex (ArrayList with Exercise objects i am taking column 0 which corresponds to question_id)
			for (int i = 0;i< ex.size(); i++){
				question_ids.add(ex.get(i).getQuestion_id());
			}

			numRowsSol = mydb.numberOfRowsOfSolution();
			System.out.println("Now we are Section Fragment and question_ids: "+question_ids);

			// for every question_id i am getting the appropriate solution
			for (int i=0;i< question_ids.size(); i++){
				
				if (numRowsSol>0){
					solution_pr = mydb.getSolutionOfQuestion(question_ids.get(i));
					ex_solution.add(solution_pr.getSolution_text());
				}
			}
			
			
			theory_bt.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				
				// theory Fragment must be v4 not app.Fragment!!!!!!!!!!!SOS//	
					 getFragmentManager().beginTransaction()
			            .replace(R.id.fragment_container, TheoryFragment.newInstance(theory.getTheory_text(),chapter, R.drawable.additionfacttable,user_id, visitor)).addToBackStack(null).commit();

					//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση(Θεωρία)");
			}
           
        });
			
	       
				exercise.setOnClickListener(new OnClickListener(){
		
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
		//				
						// theory Fragment must be v4 not app.Fragment!!!!!!!!!!!SOS
						if (visitor==0){
						
							getFragmentManager().beginTransaction()
						            .replace(R.id.fragment_container, FillTheGapFragment.newInstance(ex_question, ex_solution , user_id, chapter, question_ids) ).addToBackStack(null).commit();
							
							//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση(Ασκήσεις)");
						}
						if (visitor==1){
							Toast.makeText(getActivity(), "Δε μπορείτε να συμμετέχετε!",
				        			   Toast.LENGTH_LONG).show();
						}
					}
		//			
				});
	       
	       
//	       s
//	       }
		}
		
		if (chapter=="Αφαίρεση"){
			
			
			int chapter_id = 2;
			int theory_id = 2;
			final ArrayList<Integer> question_ids = new ArrayList<Integer>();
			int numRowsSol;
	
			theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
			ex_question = mydb.getAllExerciseTextOfTheory(theory_id);
			ex = mydb.getAllExercisesOfTheory(theory_id);
			
			// Now i want to get all question_ids. => So, from ex (ArrayList with Exercise objects i am taking column 0 which corresponds to question_id)
			for (int i = 0;i< ex.size(); i++){
				question_ids.add(ex.get(i).getQuestion_id());
			}

			numRowsSol = mydb.numberOfRowsOfSolution();
			// for every question_id i am getting the appropriate solution
			System.out.println("Now we are Section Fragment and question_ids: "+question_ids);
			for (int i=0;i< question_ids.size(); i++){
				
				if (numRowsSol>0){
					solution_af = mydb.getSolutionOfQuestion(question_ids.get(i));
					ex_solution.add(solution_af.getSolution_text());
				}
			}
						
				theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
				
	           theory_bt.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	//				
						 getActivity().getSupportFragmentManager().beginTransaction()
				            .replace(R.id.fragment_container, TheoryFragment.newInstance(theory.getTheory_text(), chapter,R.drawable.subtable,user_id,visitor)).addToBackStack(null).commit();
	
				
				}
	           
	        });
			
			exercise.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	
					if (visitor==0){
						
						getFragmentManager().beginTransaction()
					            .replace(R.id.fragment_container, FillTheGapFragment.newInstance(ex_question, ex_solution , user_id, chapter, question_ids) ).addToBackStack(null).commit();
						
						//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση(Ασκήσεις)");
					}
					if (visitor==1){
						Toast.makeText(getActivity(), "Δε μπορείτε να συμμετέχετε!",
			        			   Toast.LENGTH_LONG).show();
					}
					
				}
	//			
			});
		}
		
		
		
		if (chapter=="Πολλαπλασιασμός"){
			
			
			int chapter_id = 3;
			int theory_id = 3;
			final ArrayList<Integer> question_ids = new ArrayList<Integer>();
			int numRowsSol;
	
			theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
			ex_question = mydb.getAllExerciseTextOfTheory(theory_id);
			ex = mydb.getAllExercisesOfTheory(theory_id);
			
			// Now i want to get all question_ids. => So, from ex (ArrayList with Exercise objects i am taking column 0 which corresponds to question_id)
			for (int i = 0;i< ex.size(); i++){
				question_ids.add(ex.get(i).getQuestion_id());
			}

			numRowsSol = mydb.numberOfRowsOfSolution();
			// for every question_id i am getting the appropriate solution
			System.out.println("Now we are Section Fragment and question_ids: "+question_ids);
			for (int i=0;i< question_ids.size(); i++){
				
				if (numRowsSol>0){
					solution_pol = mydb.getSolutionOfQuestion(question_ids.get(i));
					ex_solution.add(solution_pol.getSolution_text());
				}
			}
						
				theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
				
	           theory_bt.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	//				
						 getActivity().getSupportFragmentManager().beginTransaction()
				            .replace(R.id.fragment_container, TheoryFragment.newInstance(theory.getTheory_text(), chapter,R.drawable.pinakaspollaplasiasmou,user_id,visitor)).addToBackStack(null).commit();
	
				
				}
	           
	        });
			
			exercise.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	
					if (visitor==0){
						
						getFragmentManager().beginTransaction()
					            .replace(R.id.fragment_container, FillTheGapFragment.newInstance(ex_question, ex_solution , user_id, chapter, question_ids) ).addToBackStack(null).commit();
						
						//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση(Ασκήσεις)");
					}
					if (visitor==1){
						Toast.makeText(getActivity(), "Δε μπορείτε να συμμετέχετε!",
			        			   Toast.LENGTH_LONG).show();
					}
					
				}
	//			
			});
		}
		
		
		if (chapter=="Διαίρεση"){
			
			
			int chapter_id = 4;
			int theory_id = 4;
			final ArrayList<Integer> question_ids = new ArrayList<Integer>();
			int numRowsSol;
	
			theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
			ex_question = mydb.getAllExerciseTextOfTheory(theory_id);
			ex = mydb.getAllExercisesOfTheory(theory_id);
			
			// Now i want to get all question_ids. => So, from ex (ArrayList with Exercise objects i am taking column 0 which corresponds to question_id)
			for (int i = 0;i< ex.size(); i++){
				question_ids.add(ex.get(i).getQuestion_id());
			}

			numRowsSol = mydb.numberOfRowsOfSolution();
			// for every question_id i am getting the appropriate solution
			System.out.println("Now we are Section Fragment and question_ids: "+question_ids);
			for (int i=0;i< question_ids.size(); i++){
				
				if (numRowsSol>0){
					solution_d = mydb.getSolutionOfQuestion(question_ids.get(i));
					ex_solution.add(solution_d.getSolution_text());
				}
			}
						
				theory = mydb.getTheoryOfChapter(theory_id, chapter_id);
				
	           theory_bt.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	//				
						 getActivity().getSupportFragmentManager().beginTransaction()
				            .replace(R.id.fragment_container, TheoryFragment.newInstance(theory.getTheory_text(), chapter,R.drawable.diairesh_upoloipo,user_id,visitor)).addToBackStack(null).commit();
	
				
				}
	           
	        });
			
			exercise.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
	
					if (visitor==0){
						
						getFragmentManager().beginTransaction()
					            .replace(R.id.fragment_container, FillTheGapFragment.newInstance(ex_question, ex_solution , user_id, chapter, question_ids) ).addToBackStack(null).commit();
						
						//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση(Ασκήσεις)");
					}
					if (visitor==1){
						Toast.makeText(getActivity(), "Δε μπορείτε να συμμετέχετε!",
			        			   Toast.LENGTH_LONG).show();
					}
					
				}
	//			
			});
		}
		
		
		
		return view;
	}

	public String readFile(String filename) throws IOException {
		
		
		//Read text from file
		StringBuilder text = new StringBuilder();
			try {
			    BufferedReader br = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(filename), "UTF-8"));
			    String line;
	
			    while ((line = br.readLine()) != null) {
			        text.append(line);
			        text.append('\n');
			    }
			    br.close();
			}
		
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}
		return text.toString();

	}
	
	public ArrayList<String> readLines(String filename) throws Exception {
		ArrayList<String> list = new ArrayList<>();

		// New BufferedReader.
		BufferedReader reader = new BufferedReader(new InputStreamReader(getActivity().getAssets().open(filename), "UTF-8"));

		// Add all lines from file to ArrayList.
		while (true) {
		    String line = reader.readLine();
		    if (line == null) {
			break;
		    }
		    list.add(line);
		}

		// Close it.
		reader.close();
		
		// Print size of ArrayList.
		System.out.println("Lines: " + list.size());

		// Print each line.
		for (String line : list) {
		    System.out.println(line);
		}

	    return list;
	  }
	
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    ((MainActivity)getActivity()).getSupportActionBar()
	        .setTitle(this.chapter+"-Επιλογές");
	}
	
//	public void onBackPressed() {
//	    if (getFragmentManager().getBackStackEntryCount() == 0) {
//	        this.getActivity().finish();
//
//	    } else {
//	        getFragmentManager().popBackStack();
//	    }
//	}
	public void onBackPressed()
	{
	    FragmentManager fm = getActivity().getSupportFragmentManager();
	    fm.popBackStack();
	}
	
	
	void showDialogInfo(String message, String stats) {
		DialogFragment info_dialog = InfoDialogFragment.newInstance(message,stats,user_id);
	    info_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu) {

		menu.findItem(R.id.exit).setVisible(true).setEnabled(true);
		menu.findItem(R.id.chapters).setVisible(false).setEnabled(false);
		menu.findItem(R.id.stats).setVisible(true).setEnabled(true);
		menu.findItem(R.id.info).setVisible(true).setEnabled(true);

		if (visitor==0){
			menu.findItem(R.id.login).setVisible(false).setEnabled(false);
			menu.findItem(R.id.logout).setVisible(true).setEnabled(true);
		}
		if (visitor==1){
			menu.findItem(R.id.logout).setVisible(false).setEnabled(false);
			menu.findItem(R.id.login).setVisible(true).setEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		int id = item.getItemId();
		
		if (id==R.id.info){
//			Toast.makeText(this.getApplicationContext()
//					, "This is info",
//     			   Toast.LENGTH_LONG).show();
			showDialogInfo("Διάλεξε την ενότητα 'Θεωρία' για να διαβάσεις. Διάλεξε την ενότητα 'Ασκήσεις' για να εξασκήσεις τη θεωρία που διάβασες.","no_stats");
			mListener.onInfoPressedFromSection(true);
	            return true;
		}
		if (id==R.id.stats){
			showDialogInfo(String.valueOf(user_id),"user_stats");
			return true;
		}
		//if user chooses logout then the fragments in backstack should empty
		if (id==R.id.logout){
			visitor=1;
			user_id = 0;
//			try {
//				this.finalize();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

			//empty backstack
			getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

			//return to chapter fragment
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
//			try {
//				this.finalize();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			showDialog();
//			getActivity().getSupportFragmentManager().beginTransaction()
//            .replace(R.id.fragment_container, SectionFragment.newInstance(chapter, user_id, visitor)).addToBackStack(null)
//            .commit();
//			showDialog();
//			Toast.makeText(getActivity()
//			, "Συνδεθήκατε επιτυχώς!",
//			   Toast.LENGTH_LONG).show();
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
	
	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onSectionFragmentInteraction(uri);
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
		public void onSectionFragmentInteraction(Uri uri);
		public boolean onInfoPressedFromSection(boolean show);
	}

	
	
}

