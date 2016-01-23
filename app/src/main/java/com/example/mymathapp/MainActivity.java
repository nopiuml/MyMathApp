package com.example.mymathapp;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import com.example.mymathapp.db.DBHelper;

import android.app.ActionBar;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ChapterFragment.OnFragmentInteractionListener,SectionFragment.OnFragmentInteractionListener
,TheoryFragment.OnFragmentInteractionListener,
FillTheGapFragment.OnFragmentInteractionListener,
MultipleChoiceFragment.OnFragmentInteractionListener,
UserDialogFragment.OnFragmentInteractionListener,
UserStatisticsFragment.OnFragmentInteractionListener,
InfoDialogFragment.OnFragmentInteractionListener{

	private DBHelper mydb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_main);
		
		// Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or elses
            // we could end up with overlapping fragments.
        	TextView t1 = (TextView)findViewById(R.id.textView1);
//    		int imageResource = getResources().getIdentifier(IMAGE_URI, null, getActivity().getPackageName());
    		ImageView imageView= (ImageView)findViewById(R.id.image);
    		imageView.setImageResource(R.drawable.math_signs_correct);
//    		Activity act = getActivity();
//    		Drawable res = act.getDrawable(imageResource);
//    		
//    		imageView.setImageDrawable(res);
    		try {
				t1.setText(readFile("kalwsorisma.txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if (savedInstanceState != null) {
                return;
            }
         // Set a toolbar which will replace the action bar.
            getSupportActionBar().setTitle("Τα Μαθηματικά μου");
            
            
    		
            mydb = new DBHelper(this);
            fillDb("Πρόσθεση");
            fillDb("Αφαίρεση");
            fillDb("Πολλαπλασιασμός");
            fillDb("Διαίρεση");
            fillDb("Επαναληπτικό Διαγώνισμα");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            showDialog();
           
            }
    }
        
	

	void showDialog() {
	    DialogFragment user_dialog = new UserDialogFragment();
	    user_dialog.show(getSupportFragmentManager(), "dialog");
	}
	
	
	
	
	
	public void fillDb(String chapter){
		
		
		if (chapter=="Πρόσθεση"){
		
			int chapter_id = 1;
			int theory_id = 1;
			String theory_text;
			ArrayList<String> questions = new ArrayList<String>();
			ArrayList<String> solutions = new ArrayList<String>();
			ArrayList<Integer> question_ids = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_db = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_ex = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_sol = new ArrayList<Integer>();
			ArrayList<Integer> question_ids_sol = new ArrayList<Integer>();
			boolean inserted  = false;
			int numRowsTh;
			int numRowsEx;
			int numRowsSol;
			int theory_found = 0;
			int theory_ex_found = 0;
			int theory_sol_found = 0;
			
			
			numRowsTh = mydb.numberOfRowsOfTheory();
			if (numRowsTh == 0){
				try {
					theory_text = readFile("prosthesi.txt");				
					mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
					if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
			if (numRowsTh!=0){
				theory_id_db = mydb.getTheoryIds();
				for (int i=0;i<theory_id_db.size();i++){
					if (theory_id_db.get(i)==theory_id){
						theory_found = theory_found + 1;
					}
						
					
				}
				if (theory_found==0){
				try {
						theory_text = readFile("prosthesi.txt");				
						mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
						if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
		
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				
			}
			
			
			numRowsEx = mydb.numberOfRowsOfExercise();
			if (numRowsEx == 0){
				try{
					questions = readLines("prosth_ex.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< questions.size(); i++){
						mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(i));
						System.out.println("this is MainActivity inserting "+questions.get(i)+" to exercise");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (numRowsEx!=0){
				
				theory_id_ex = mydb.getTheoryIdsOfExercise();
				for (int i=0;i<theory_id_ex.size();i++){
					if (theory_id_ex.get(i)==theory_id){
						theory_ex_found = theory_ex_found +1;
					}
				}
					if (theory_ex_found==0){
						try{
							questions = readLines("prosth_ex.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j=0; j< questions.size(); j++){
								mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(j));
								System.out.println("this is MainActivity inserting "+questions.get(j)+" to exercise");
							}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
					}
			}
			numRowsSol = mydb.numberOfRowsOfSolution();
			if (numRowsSol == 0){
				try{
					solutions = readLines("prosth_sol.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< solutions.size(); i++){
						mydb.insertSolutionToQuestion(i+1, theory_id, solutions.get(i));
						System.out.println("this is MainActivity inserting "+solutions.get(i)+" to solution");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsSol!=0){
				theory_id_sol = mydb.getTheoryIdsOfSolution();
				question_ids_sol = mydb.getQuestionIdsOfSolution();
				int last_question_id_sol = question_ids_sol.get(question_ids_sol.size()-1);
				for (int i=0;i<theory_id_sol.size();i++){
					if (theory_id_sol.get(i)==theory_id){
						theory_sol_found = theory_sol_found +1;
					}
				}
				if (theory_sol_found==0){	
					try{
							solutions = readLines("prosth_sol.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j =0; j< solutions.size(); j++){
								mydb.insertSolutionToQuestion(last_question_id_sol+j+1,theory_id, solutions.get(j));
								System.out.println("this is MainActivity inserting "+solutions.get(j)+" to solution");
							}
						}
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}


		
		if (chapter=="Αφαίρεση"){
			
			int chapter_id = 2;
			int theory_id = 2;
			String theory_text;
			boolean inserted = false;
			ArrayList<String> questions = new ArrayList<String>();
			ArrayList<String> solutions = new ArrayList<String>();
			ArrayList<Integer> question_ids = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_db = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_ex = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_sol = new ArrayList<Integer>();
			ArrayList<Integer> question_ids_sol = new ArrayList<Integer>();
 			int numRowsTh;
			int numRowsEx;
			int numRowsSol;
			int theory_found = 0;
			int theory_ex_found = 0;
			int theory_sol_found = 0;
			
			numRowsTh = mydb.numberOfRowsOfTheory();
			if (numRowsTh == 0){
				try {
					theory_text = readFile("afairesh.txt");				
					mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
					if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
			
			if (numRowsTh!=0){
				theory_id_db = mydb.getTheoryIds();
				for (int i=0;i<theory_id_db.size();i++){
					if (theory_id_db.get(i)==theory_id){
						theory_found = theory_found + 1;
					}
						
					
				}
				if (theory_found==0){
				try {
						theory_text = readFile("afairesh.txt");				
						mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
						if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
		
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				
			}
			
			
			numRowsEx = mydb.numberOfRowsOfExercise();
			if (numRowsEx == 0){
				try{
					questions = readLines("afairesh_ex.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< questions.size(); i++){
						mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(i));
						System.out.println("this is MainActivity inserting "+questions.get(i)+" to exercise");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsEx!=0){
				
				theory_id_ex = mydb.getTheoryIdsOfExercise();
				for (int i=0;i<theory_id_ex.size();i++){
					if (theory_id_ex.get(i)==theory_id){
						theory_ex_found = theory_ex_found +1;
					}
				}
					if (theory_ex_found==0){
						try{
							questions = readLines("afairesh_ex.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j=0; j< questions.size(); j++){
								mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(j));
								System.out.println("this is MainActivity inserting "+questions.get(j)+" to exercise");
							}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
					}
			}
			numRowsSol = mydb.numberOfRowsOfSolution();
			if (numRowsSol == 0){
				try{
					solutions = readLines("afairesh_sol.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< solutions.size(); i++){
						mydb.insertSolutionToQuestion(i+1, theory_id, solutions.get(i));
						System.out.println("this is MainActivity inserting "+solutions.get(i)+" to solution");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsSol!=0){
				theory_id_sol = mydb.getTheoryIdsOfSolution();
				question_ids_sol = mydb.getQuestionIdsOfSolution();
				int last_question_id_sol = question_ids_sol.get(question_ids_sol.size()-1);
				for (int i=0;i<theory_id_sol.size();i++){
					if (theory_id_sol.get(i)==theory_id){
						theory_sol_found = theory_sol_found +1;
					}
				}
				if (theory_sol_found==0){	
					try{
							solutions = readLines("afairesh_sol.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j =0; j< solutions.size(); j++){
								mydb.insertSolutionToQuestion(last_question_id_sol+j+1,theory_id, solutions.get(j));
								System.out.println("this is MainActivity inserting "+solutions.get(j)+" to solution");
							}
						}
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
		if (chapter=="Πολλαπλασιασμός"){
					
					int chapter_id = 3;
					int theory_id = 3;
					String theory_text;
					boolean inserted = false;
					ArrayList<String> questions = new ArrayList<String>();
					ArrayList<String> solutions = new ArrayList<String>();
					ArrayList<Integer> question_ids = new ArrayList<Integer>();
					ArrayList<Integer> theory_id_db = new ArrayList<Integer>();
					ArrayList<Integer> theory_id_ex = new ArrayList<Integer>();
					ArrayList<Integer> theory_id_sol = new ArrayList<Integer>();
					ArrayList<Integer> question_ids_sol = new ArrayList<Integer>();
		 			int numRowsTh;
					int numRowsEx;
					int numRowsSol;
					int theory_found = 0;
					int theory_ex_found = 0;
					int theory_sol_found = 0;
					
					numRowsTh = mydb.numberOfRowsOfTheory();
					if (numRowsTh == 0){
						try {
							theory_text = readFile("polsmos.txt");				
							mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
							if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
			
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
					}
					
					if (numRowsTh!=0){
						theory_id_db = mydb.getTheoryIds();
						for (int i=0;i<theory_id_db.size();i++){
							if (theory_id_db.get(i)==theory_id){
								theory_found = theory_found + 1;
							}
								
							
						}
						if (theory_found==0){
						try {
								theory_text = readFile("polsmos.txt");				
								mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
								if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
				
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}	
						
					}
					
					
					numRowsEx = mydb.numberOfRowsOfExercise();
					if (numRowsEx == 0){
						try{
							questions = readLines("polsmos_ex.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int i =0; i< questions.size(); i++){
								mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(i));
								System.out.println("this is MainActivity inserting "+questions.get(i)+" to exercise");
							}
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (numRowsEx!=0){
						
						theory_id_ex = mydb.getTheoryIdsOfExercise();
						for (int i=0;i<theory_id_ex.size();i++){
							if (theory_id_ex.get(i)==theory_id){
								theory_ex_found = theory_ex_found +1;
							}
						}
							if (theory_ex_found==0){
								try{
									questions = readLines("polsmos_ex.txt");
									//System.out.println("this is SectionFragment inserting to exercise"+questions);
									for (int j=0; j< questions.size(); j++){
										mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(j));
										System.out.println("this is MainActivity inserting "+questions.get(j)+" to exercise");
									}
							}catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						
							}
					}
					numRowsSol = mydb.numberOfRowsOfSolution();
					if (numRowsSol == 0){
						try{
							solutions = readLines("polsmos_sol.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int i =0; i< solutions.size(); i++){
								mydb.insertSolutionToQuestion(i+1, theory_id, solutions.get(i));
								System.out.println("this is MainActivity inserting "+solutions.get(i)+" to solution");
							}
						}catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					if (numRowsSol!=0){
						theory_id_sol = mydb.getTheoryIdsOfSolution();
						question_ids_sol = mydb.getQuestionIdsOfSolution();
						int last_question_id_sol = question_ids_sol.get(question_ids_sol.size()-1);
						for (int i=0;i<theory_id_sol.size();i++){
							if (theory_id_sol.get(i)==theory_id){
								theory_sol_found = theory_sol_found +1;
							}
						}
						if (theory_sol_found==0){	
							try{
									solutions = readLines("polsmos_sol.txt");
									//System.out.println("this is SectionFragment inserting to exercise"+questions);
									for (int j =0; j< solutions.size(); j++){
										mydb.insertSolutionToQuestion(last_question_id_sol+j+1,theory_id, solutions.get(j));
										System.out.println("this is MainActivity inserting "+solutions.get(j)+" to solution");
									}
								}
								catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					}
				}
		
		
		
		
		if (chapter=="Διαίρεση"){
			
			int chapter_id = 4;
			int theory_id = 4;
			String theory_text;
			boolean inserted = false;
			ArrayList<String> questions = new ArrayList<String>();
			ArrayList<String> solutions = new ArrayList<String>();
			ArrayList<Integer> question_ids = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_db = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_ex = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_sol = new ArrayList<Integer>();
			ArrayList<Integer> question_ids_sol = new ArrayList<Integer>();
 			int numRowsTh;
			int numRowsEx;
			int numRowsSol;
			int theory_found = 0;
			int theory_ex_found = 0;
			int theory_sol_found = 0;
			
			numRowsTh = mydb.numberOfRowsOfTheory();
			if (numRowsTh == 0){
				try {
					theory_text = readFile("diairesh.txt");				
					mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
					if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
	
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			}
			
			if (numRowsTh!=0){
				theory_id_db = mydb.getTheoryIds();
				for (int i=0;i<theory_id_db.size();i++){
					if (theory_id_db.get(i)==theory_id){
						theory_found = theory_found + 1;
					}
						
					
				}
				if (theory_found==0){
				try {
						theory_text = readFile("diairesh.txt");				
						mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
						if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
		
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				
			}
			
			
			numRowsEx = mydb.numberOfRowsOfExercise();
			if (numRowsEx == 0){
				try{
					questions = readLines("diairesh_ex.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< questions.size(); i++){
						mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(i));
						System.out.println("this is MainActivity inserting "+questions.get(i)+" to exercise");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsEx!=0){
				
				theory_id_ex = mydb.getTheoryIdsOfExercise();
				for (int i=0;i<theory_id_ex.size();i++){
					if (theory_id_ex.get(i)==theory_id){ //insert apache poi jars to android project
						theory_ex_found = theory_ex_found +1;
					}
				}
					if (theory_ex_found==0){
						try{
							questions = readLines("diairesh_ex.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j=0; j< questions.size(); j++){
								mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(j));
								System.out.println("this is MainActivity inserting "+questions.get(j)+" to exercise");
							}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
					}
			}
			numRowsSol = mydb.numberOfRowsOfSolution();
			if (numRowsSol == 0){
				try{
					solutions = readLines("diairesh_sol.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< solutions.size(); i++){
						mydb.insertSolutionToQuestion(i+1, theory_id, solutions.get(i));
						System.out.println("this is MainActivity inserting "+solutions.get(i)+" to solution");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsSol!=0){
				theory_id_sol = mydb.getTheoryIdsOfSolution();
				question_ids_sol = mydb.getQuestionIdsOfSolution();
				int last_question_id_sol = question_ids_sol.get(question_ids_sol.size()-1);
				for (int i=0;i<theory_id_sol.size();i++){
					if (theory_id_sol.get(i)==theory_id){
						theory_sol_found = theory_sol_found +1;
					}
				}
				if (theory_sol_found==0){	
					try{
							solutions = readLines("diairesh_sol.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j =0; j< solutions.size(); j++){
								mydb.insertSolutionToQuestion(last_question_id_sol+j+1,theory_id, solutions.get(j));
								System.out.println("this is MainActivity inserting "+solutions.get(j)+" to solution");
							}
						}
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
		
		
		
		if (chapter=="Επαναληπτικό Διαγώνισμα"){
			
			int chapter_id = 5;
			int theory_id = 5;
			String theory_text="Επαναληπτικό Διαγώνισμα";
			boolean inserted = false;
			ArrayList<String> questions = new ArrayList<String>();
			ArrayList<String> solutions = new ArrayList<String>();
			ArrayList<Integer> question_ids = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_db = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_ex = new ArrayList<Integer>();
			ArrayList<Integer> theory_id_sol = new ArrayList<Integer>();
			ArrayList<Integer> question_ids_sol = new ArrayList<Integer>();
 			int numRowsTh;
			int numRowsEx;
			int numRowsSol;
			int theory_found = 0;
			int theory_ex_found = 0;
			int theory_sol_found = 0;
			
			numRowsTh = mydb.numberOfRowsOfTheory();
			if (numRowsTh == 0){
				mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
				if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
					
			}
			
			if (numRowsTh!=0){
				theory_id_db = mydb.getTheoryIds();
				for (int i=0;i<theory_id_db.size();i++){
					if (theory_id_db.get(i)==theory_id){
						theory_found = theory_found + 1;
					}
						
					
				}
				if (theory_found==0){
				mydb.insertTheoryToChapter(theory_id, chapter_id, theory_text, null);
				if (inserted) Log.i("DATABASE","Just inserted theory to chapter for chapter_id "+chapter_id);
				}	
				
			}
			
			
			numRowsEx = mydb.numberOfRowsOfExercise();
			if (numRowsEx == 0){
				try{
					questions = readLines("test_epanalhptiko_ex.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< questions.size(); i++){
						mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(i));
						System.out.println("this is MainActivity inserting "+questions.get(i)+" to exercise");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsEx!=0){
				
				theory_id_ex = mydb.getTheoryIdsOfExercise();
				for (int i=0;i<theory_id_ex.size();i++){
					if (theory_id_ex.get(i)==theory_id){
						theory_ex_found = theory_ex_found +1;
					}
				}
					if (theory_ex_found==0){
						try{
							questions = readLines("test_epanalhptiko_ex.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j=0; j< questions.size(); j++){
								mydb.insertExerciseToTheory(theory_id, chapter_id, questions.get(j));
								System.out.println("this is MainActivity inserting "+questions.get(j)+" to exercise");
							}
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
					}
			}
			numRowsSol = mydb.numberOfRowsOfSolution();
			if (numRowsSol == 0){
				try{
					solutions = readLines("test_epanalhptiko_sol.txt");
					//System.out.println("this is SectionFragment inserting to exercise"+questions);
					for (int i =0; i< solutions.size(); i++){
						mydb.insertSolutionToQuestion(i+1, theory_id, solutions.get(i));
						System.out.println("this is MainActivity inserting "+solutions.get(i)+" to solution");
					}
				}catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (numRowsSol!=0){
				theory_id_sol = mydb.getTheoryIdsOfSolution();
				question_ids_sol = mydb.getQuestionIdsOfSolution();
				int last_question_id_sol = question_ids_sol.get(question_ids_sol.size()-1);
				for (int i=0;i<theory_id_sol.size();i++){
					if (theory_id_sol.get(i)==theory_id){
						theory_sol_found = theory_sol_found +1;
					}
				}
				if (theory_sol_found==0){	
					try{
							solutions = readLines("test_epanalhptiko_sol.txt");
							//System.out.println("this is SectionFragment inserting to exercise"+questions);
							for (int j =0; j< solutions.size(); j++){
								mydb.insertSolutionToQuestion(last_question_id_sol+j+1,theory_id, solutions.get(j));
								System.out.println("this is MainActivity inserting "+solutions.get(j)+" to solution");
							}
						}
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
		}
	}
	
	
	
	
	
public String readFile(String filename) throws IOException {
		
		
		//Read text from file
		StringBuilder text = new StringBuilder();
			try {
			    BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(filename), "UTF-8"));
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open(filename), "UTF-8"));

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
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {  
		
			menu.findItem(R.id.login).setVisible(false).setEnabled(false);
			menu.findItem(R.id.logout).setVisible(false).setEnabled(false);
			menu.findItem(R.id.stats).setVisible(false).setEnabled(false);
			menu.findItem(R.id.info).setVisible(false).setEnabled(false);
			menu.findItem(R.id.chapters).setVisible(true).setEnabled(true);
			menu.findItem(R.id.exit).setVisible(true).setEnabled(true);
			return true;
		
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
		if (id==R.id.info){
		
			return true;
		}
		if(id==R.id.stats){
			return true;
		}
		if(id==R.id.logout){
			return true;
		}
		if(id==R.id.login){
			return true;
		}
		if (id==R.id.exit){
		
			this.finish();
		}

		if (id==R.id.chapters){

			// do something here
			Toast.makeText(this,"Show something here",Toast.LENGTH_LONG).show();
		}
		return super.onOptionsItemSelected(item);
	}

	 
	@Override
	public void onChapterFragmentInteraction(String title) {
		// TODO Auto-generated method stub
		getSupportActionBar().setTitle(title);
		
	}

	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onSectionFragmentInteraction(Uri uri);
		public boolean onInfoPressedFromSection(boolean show);
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSectionFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean onInfoPressedFromSection(boolean show){
		boolean return_=true;
		if (show=true){
			return_=false;
		}
		return return_;
	}

	
}
