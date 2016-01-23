package com.example.mymathapp;


import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;









import android.widget.Toast;

import com.example.mymathapp.db.Chapter;
import com.example.mymathapp.db.DBHelper;
import com.example.mymathapp.db.Exercise;
import com.example.mymathapp.db.Solution;
import com.example.mymathapp.db.UserAnswers;
import com.example.mymathapp.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the
 * ListView with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the }
 * interface.
 */
public class ChapterFragment extends Fragment implements
		AbsListView.OnItemClickListener {

	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String USER_ID = "user_id";
	private static final String VISITOR = "visitor";

	// TODO: Rename and change types of parameters
	private int user_id;
	private int visitor;

	private OnFragmentInteractionListener mListener;

	/**
	 * The fragment's ListView/GridView.
	 */
	private AbsListView mListView;

	/**
	 * The Adapter which will be used to populate the ListView/GridView with
	 * Views.
	 */
	private ArrayAdapter<String> mAdapter;
	DBHelper mydb;
	ArrayList<String> allChapters;
	ArrayList<Integer> allChapterIds;
	private ArrayList<String> ex_question = new ArrayList<String>();
	private ArrayList<String> ex_solution = new ArrayList<String>();
	private ArrayList<String> user_answers_wrong_sol = new ArrayList<String>();
	private ArrayList<Exercise> ex = new ArrayList<Exercise>();
	private Solution solution_test;
	private Solution solution_to_wrong;
	private ArrayList<String> user_answers_wrong_text = new ArrayList<String>();
	Exercise ex_obj  = new Exercise();

	// TODO: Rename and change types of parameters
	public static ChapterFragment newInstance(int user_id, int visitor) {
		ChapterFragment fragment = new ChapterFragment();
		Bundle args = new Bundle();
		args.putInt(USER_ID, user_id);
		args.putInt(VISITOR, visitor);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ChapterFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		if (getArguments() != null) {
			user_id = getArguments().getInt(USER_ID);
			visitor = getArguments().getInt(VISITOR);
		}
	
		
		mydb = new DBHelper(getActivity());
		int numOfRowsOfChapter = mydb.numberOfRowsOfChapter();
		
		// if database is empty full it 
		if (numOfRowsOfChapter==0){
			mydb.insertChapter("Πρόσθεση");
			mydb.insertChapter("Αφαίρεση");
			mydb.insertChapter("Πολλαπλασιασμός");
			mydb.insertChapter("Διαίρεση");
			mydb.insertChapter("Επαναληπτικό Διαγώνισμα");
		}
		
		allChapters = mydb.getAllChapters();
		
		
		
		//Log.i("CHAPTER",chapter_ids.get(0).toString());
		
		// TODO: Change Adapter to display your content
//		mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
//				R.layout.chapter_list_item, R.id.textView1,
//				DummyContent.ITEMS);
//		
		mAdapter = new ArrayAdapter<String>(getActivity(),
				R.layout.chapter_list_item, R.id.textView1,
				allChapters);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_chapter, container,
				false);
		
		// Set the adapter
		mListView = (AbsListView) view.findViewById(android.R.id.list);
		((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

		// Set OnItemClickListener so we can be notified on item clicks
		mListView.setOnItemClickListener(this);
		
		//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση");
		
		return view;
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
	
	void showDialogInfo(String message,String logout) {
		DialogFragment info_dialog = InfoDialogFragment.newInstance(message,logout,user_id);
	    info_dialog.show(getActivity().getSupportFragmentManager(), "dialog");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (null != mListener) {
			// Notify the active callbacks interface (the activity, if the
			// fragment is attached to one) that an item has been selected.
//			mListener
//					.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//			FragmentManager fragmentManager = getFragmentManager();
		
		
			
			mListener.onChapterFragmentInteraction(allChapters.get(position));
			Log.i("Chapter_Fragment",allChapters.get(position)+" "+position);
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			if (visitor==0){
			
				switch(position){
					case 0:
						
						
						fragmentManager.beginTransaction()
			            .replace(R.id.fragment_container, SectionFragment.newInstance("Πρόσθεση", user_id, visitor)).addToBackStack(null)
			            .commit();
	//					getActivity().getActionBar().setTitle(allChapters.get(position));
						//((MainActivity)getActivity()).getSupportActionBar().setTitle("Πρόσθεση");
						break;
						
					case 1:
						
						fragmentManager.beginTransaction()
			            .replace(R.id.fragment_container, SectionFragment.newInstance("Αφαίρεση", user_id, visitor)).addToBackStack(null)
			            .commit();
	//					getActivity().getActionBar().setTitle(allChapters.get(position));
	
						break;
						
					case 2:
						
						fragmentManager.beginTransaction()
			            .replace(R.id.fragment_container, SectionFragment.newInstance("Πολλαπλασιασμός", user_id, visitor)).addToBackStack(null)
			            .commit();
	//					getActivity().getActionBar().setTitle(allChapters.get(position));
	
						break;
					case 3:
						
						fragmentManager.beginTransaction()
			            .replace(R.id.fragment_container, SectionFragment.newInstance("Διαίρεση", user_id, visitor)).addToBackStack(null)
			            .commit();
	//					getActivity().getActionBar().setTitle(allChapters.get(position));
	
						break;
					
						
					case 4:
						
						
						int chapter_id = 5;
						int theory_id = 5;
						final ArrayList<Integer> question_ids_test_ex = new ArrayList<Integer>();
						ArrayList<UserAnswers> user_answers_correct = new ArrayList<UserAnswers>();
		            	ArrayList<UserAnswers> user_answers_wrong = new ArrayList<UserAnswers>();
		            	ArrayList<Integer> user_answers_wrong_ids = new ArrayList<Integer>();
		            	//ArrayList<String> user_answers_wrong_sol = new ArrayList<String>();
						int numRowsSol;
						int numRowsUserAnswers;
						
						int numRowsEx = mydb.numberOfRowsOfExercise();
						ex_question = mydb.getAllExerciseTextOfTheory(theory_id);
						ex= mydb.getAllExercisesOfTheory(theory_id);
						
						// Now i want to get all question_ids. => So, from ex (ArrayList with Exercise objects i am taking column 0 which corresponds to question_id)
						for (int i = 0;i< ex.size(); i++){
							question_ids_test_ex.add(ex.get(i).getQuestion_id());
						}

						numRowsSol = mydb.numberOfRowsOfSolution();
						// for every question_id i am getting the appropriate solution
						System.out.println("Now we are Chapter Fragment and question_ids_test_ex: "+question_ids_test_ex);
						for (int i=0;i< question_ids_test_ex.size(); i++){
							
							if (numRowsSol>0){
								solution_test= mydb.getSolutionOfQuestion(question_ids_test_ex.get(i));
								ex_solution.add(solution_test.getSolution_text());
							}
						}
						
						// check if user has answered exercises
						numRowsUserAnswers = mydb.numberOfRowsOfUserAnswers();
						//user_answers_correct = mydb.getCorrectUserAnswers(user_id, 0);
						
						if (numRowsUserAnswers>0){
							//get wrong question_ids for user_id user.
							user_answers_wrong = mydb.getWrongUserAnswers(user_id, 1);
						}
						
						// if user has answered wrong to some questions
						if (user_answers_wrong.size()>0){
							for (int i=0;i<user_answers_wrong.size();i++){
								user_answers_wrong_ids.add(user_answers_wrong.get(i).getQuestion_id());
							}
							
							System.out.println("Now we are Chapter Fragment and question_ids_wrong_ex: "+user_answers_wrong_ids);
							for (int i=0;i< user_answers_wrong_ids.size(); i++){
								
								if(numRowsEx>0){	
									ex_obj= mydb.getTextOfQuestionId(user_answers_wrong_ids.get(i));
									user_answers_wrong_text.add(ex_obj.getQuestion_text());
								}
								if (numRowsSol>0){
									solution_to_wrong= mydb.getSolutionOfQuestion(user_answers_wrong_ids.get(i));
									user_answers_wrong_sol.add(solution_to_wrong.getSolution_text());
								}
							}
						}
					
						
							ArrayList<Integer> question_ids = new ArrayList<Integer>();
							question_ids.addAll(question_ids_test_ex);
							question_ids.addAll(user_answers_wrong_ids);

							
							ArrayList<String> test_questions = new ArrayList<String>();
							test_questions.addAll(ex_question);
							test_questions.addAll(user_answers_wrong_text);
							
							ArrayList<String> test_solutions = new ArrayList<String>();
							test_solutions.addAll(ex_solution);
							test_solutions.addAll(user_answers_wrong_sol);
							
							fragmentManager.beginTransaction()
					            .replace(R.id.fragment_container, FillTheGapFragment.newInstance(test_questions, test_solutions, user_id, allChapters.get(4), question_ids)).addToBackStack(null).commit();
//							
						
						break;
						
						
				}
			}
			
			if (visitor==1){

				user_id =0;
				switch(position){
				case 0:
					
					
					fragmentManager.beginTransaction()
		            .replace(R.id.fragment_container, SectionFragment.newInstance("Πρόσθεση", user_id, visitor)).addToBackStack(null)
		            .commit();
//					getActivity().getActionBar().setTitle(allChapters.get(position));
					break;
					
				case 1:
					
					fragmentManager.beginTransaction()
		            .replace(R.id.fragment_container, SectionFragment.newInstance("Αφαίρεση", user_id, visitor)).addToBackStack(null)
		            .commit();
//					getActivity().getActionBar().setTitle(allChapters.get(position));

					break;
					
					
				case 2:
					
					fragmentManager.beginTransaction()
		            .replace(R.id.fragment_container, SectionFragment.newInstance("Πολλαπλασιασμός", user_id, visitor)).addToBackStack(null)
		            .commit();
//					getActivity().getActionBar().setTitle(allChapters.get(position));

					break;
					
				case 3:
					
					fragmentManager.beginTransaction()
		            .replace(R.id.fragment_container, SectionFragment.newInstance("Διαίρεση", user_id, visitor)).addToBackStack(null)
		            .commit();
//					getActivity().getActionBar().setTitle(allChapters.get(position));

					break;
				case 4:

						Toast.makeText(getActivity(), "Δε μπορείς να συμμετέχεις!",
			        			   Toast.LENGTH_LONG).show();
					break;
				
			}
			}
			
		}
		
		
		
			
		
	}
	
		public void onBackPressed()
	{
	    FragmentManager fm = getActivity().getSupportFragmentManager();
	    fm.popBackStack();

		Toast.makeText(getActivity()
				, "Back button pressed!",
				Toast.LENGTH_LONG).show();

		//getActivity().finish();
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    // Set title
	    ((MainActivity)getActivity()).getSupportActionBar()
	        .setTitle("Τα Μαθηματικά μου");
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
		boolean show_info_message = true;
		
		if (id==R.id.info){
//			Toast.makeText(this.getApplicationContext()
//					, "This is info",
//     			   Toast.LENGTH_LONG).show();
			showDialogInfo("Διάλεξε μία από τις παρακάτω ενότητες (Πρόσθεση, Αφαίρεση, Πολλαπλασιασμός, Διαίρεση) για να ξεκινήσεις το διάβασμα!","no_logout");
		
			return false;
		}
		if (id==R.id.stats){
			showDialogInfo(String.valueOf(user_id),"user_stats");
			return true;
		}
		if (id==R.id.logout){
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

//			try {
//				this.finalize();
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
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

	/**
	 * The default content for this Fragment has a TextView that is shown when
	 * the list is empty. If you would like to change the text, call this method
	 * to supply the text it should use.
	 */
	public void setEmptyText(CharSequence emptyText) {
		View emptyView = mListView.getEmptyView();

		if (emptyText instanceof TextView) {
			((TextView) emptyView).setText(emptyText);
		}
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
		public void onChapterFragmentInteraction(String title);
	}

}
