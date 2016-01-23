package com.example.mymathapp.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyDBName.db";
   
   public static final String chapter_TABLE_NAME = "chapter";
   public static final String chapter_COLUMN_CHAPTER_ID = "chapter_id";
   public static final String chapter_COLUMN_CHAPTER = "chapter_text";
   
   public static final String theory_TABLE_NAME = "theory";
   public static final String theory_COLUMN_THEORY_ID = "theory_id";
   public static final String theory_COLUMN_CHAPTER = "chapter_id";
   public static final String theory_COLUMN_THEORY_TEXT = "theory_text";
   public static final String theory_COLUMN_PICTURES = "pictures";

   public static final String exercise_TABLE_NAME = "exercise";
   public static final String exercise_COLUMN_QUESTION_ID = "question_id";
   public static final String exercise_COLUMN_CHAPTER = "chapter_id";
   public static final String exercise_COLUMN_THEORY_ID = "theory_id";
   public static final String exercise_COLUMN_QUESTION_TEXT = "question_text";
   
   public static final String solution_TABLE_NAME = "solution";
   public static final String solution_COLUMN_SOLUTION_ID = "solution_id";
   public static final String solution_COLUMN_THEORY_ID = "theory_id";
   public static final String solution_COLUMN_QUESTION_ID = "question_id";
   public static final String solution_COLUMN_SOLUTION_TEXT = "solution_text";

   public static final String user_TABLE_NAME = "user";
   public static final String user_COLUMN_USER_ID = "user_id";
   public static final String user_COLUMN_USER_NAME = "user_name";
   public static final String user_COLUMN_NEW_USER = "new_user";
   
   public static final String user_answers_TABLE_NAME = "user_answers";
   public static final String user_answers_COLUMN_USER_ID = "user_id";
   public static final String user_answers_COLUMN_QUESTION_ID = "question_id";
   public static final String user_answers_COLUMN_USER_ANSWER = "user_answer";
   public static final String user_answers_COLUMN_ERROR = "error";
   
   
   public static final String user_stats_TABLE_NAME = "user_stats";
   public static final String user_stats_COLUMN_USER_ID = "user_id";
   public static final String user_stats_COLUMN_CHAPTER = "chapter";
   public static final String user_stats_COLUMN_QUESTION_ID = "question_id";
   public static final String user_stats_COLUMN_CORRECT_ANSWER_ID = "correct_answer_id";
   public static final String user_stats_COLUMN_WRONG_ANSWER_ID = "wrong_answer_id";
   public static final String user_stats_COLUMN_BLANK_ANSWER_ID = "blank_answer_id";
   public static final String user_stats_COLUMN_SUCCESS = "success";
   
   private static final String CREATE_TABLE_CHAPTER = "CREATE TABLE "
           + chapter_TABLE_NAME + "(" + chapter_COLUMN_CHAPTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + chapter_COLUMN_CHAPTER
           + " TEXT"+ ")";
   
   private static final String CREATE_TABLE_USER = "CREATE TABLE "
		   + user_TABLE_NAME + "(" + user_COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + user_COLUMN_USER_NAME
           + " TEXT, "+ user_COLUMN_NEW_USER 
		   + " INTEGER" + " )";
   
   
   
   private HashMap hp;

   public DBHelper(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
	 db.execSQL(CREATE_TABLE_CHAPTER); 
      db.execSQL(
      "create table theory " +
      "(theory_id integer primary key, chapter_id integer, theory_text text,pictures blob, foreign key(chapter_id) references theory(chapter_id))"
      );
      db.execSQL(
    	      "create table exercise " +
    	      "(question_id integer primary key autoincrement,  theory_id integer ,chapter_id integer, question_text text, foreign key(theory_id) references theory(theory_id),  foreign key(chapter_id) references chapter(chapter_id))"
    	      );
      db.execSQL(
      		 "create table solution"+
    		  "(solution_id integer primary key autoincrement, question_id integer, theory_id, solution_text text, foreign key(question_id) references exercise(question_id), foreign key(theory_id) references theory(theory_id))");
      db.execSQL(CREATE_TABLE_USER);
      db.execSQL(
    	      "create table user_answers " +
    	      "(user_id integer,  question_id integer , user_answer text,  error integer, foreign key(user_id) references user(user_id), foreign key(question_id) references exercise(question_id))"
    	      );
      db.execSQL(
    		  "create table user_stats "+
      "(user_id integer, chapter text, question_id integer, correct_answer_id integer, wrong_answer_id integer, blank_answer_id integer, success, foreign key(user_id) references user(user_id), foreign key(question_id) references exercise(question_id))"
    		  );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
	  db.execSQL("DROP TABLE IF EXISTS chapter");
      db.execSQL("DROP TABLE IF EXISTS theory");
      db.execSQL("DROP TABLE IF EXISTS exercise");  
      db.execSQL("DROP TABLE IF EXISTS solution");    
      db.execSQL("DROP TABLE IF EXISTS user");
      db.execSQL("DROP TABLE IF EXISTS user_answers");
      onCreate(db);
   }

// ----------------------------------------------------------> CRUD for "chapter" <------------------------------------------------------------------------------------
   
   /*
    * check if db is empty
    */
   public int numberOfRowsOfChapter(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, chapter_TABLE_NAME);
	      return numRows;
	   }
   
   
   
   public long  insertChapter(String chapter_text)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("chapter_text", chapter_text);
      
      long chapter_id = db.insert("chapter", null, contentValues);
      return chapter_id;
   }
   
   
   
   
   public boolean updateChapter (Integer chapter_id, String chapter_text)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("chapter_id", chapter_id);
      contentValues.put("chapter_text", chapter_text);
      
      db.update("chapter", contentValues, "chapter_id = ? ", new String[] { Integer.toString(chapter_id) } );
      return true;
   }

   public int deleteChapter (int chapter_id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("chapter", 
      "chapter_id = ? ", 
      new String[] { Integer.toString(chapter_id) });
   }
   
   public ArrayList<String> getAllChapters()
   {
	    ArrayList<String> chapters = new ArrayList<String>();
	    
	    //hp = new HashMap();
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select chapter_text from chapter", null );
	 
	 // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Chapter chapter = new Chapter();
                chapter.setChapter_text((c.getString(c.getColumnIndex(chapter_COLUMN_CHAPTER))));
 
                // adding to tags list
                chapters.add(chapter.getChapter_text());
            } while (c.moveToNext());
        }
        return chapters;
    }
   
   public ArrayList<Integer> getAllChapterIds()
   {
	    ArrayList<Integer> chapters_ids = new ArrayList<Integer>();
	    
	    //hp = new HashMap();
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select chapter_id from chapter", null );
	 
	 // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Chapter chapter = new Chapter();
                chapter.setChapter_id((c.getInt((c.getColumnIndex(chapter_COLUMN_CHAPTER_ID)))));
 
                // adding to tags list
                chapters_ids.add(chapter.getChapter_id());
            } while (c.moveToNext());
        }
        return chapters_ids;
    }
   
   
   // ----------------------------------------------------------> CRUD for "theory" <------------------------------------------------------------------------------------
   /*
    * check if db is empty
    */
   public int numberOfRowsOfTheory(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, theory_TABLE_NAME);
	      return numRows;
	   }
   
   
   public boolean insertTheoryToChapter(int theory_id, int chapter_id, String theory_text, String pictures)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("theory_id", theory_id);
      contentValues.put("chapter_id", chapter_id);
      contentValues.put("theory_text", theory_text);
      contentValues.put("pictures", pictures);
      
      db.insert("theory", null, contentValues);
      return true;
   }
   
   public Theory getTheoryOfChapter(int theory_id, int chapter_id){
	 SQLiteDatabase db = this.getReadableDatabase();
     Cursor c =  db.rawQuery( "select * from theory where theory_id="+theory_id+" and chapter_id="+chapter_id, null );
     if (c != null)
         c.moveToFirst();
  
     Theory theory = new Theory();
     theory.setTheory_id(c.getInt(c.getColumnIndex(theory_COLUMN_THEORY_ID)));
     theory.setTheory_text(c.getString(c.getColumnIndex(theory_COLUMN_THEORY_TEXT)));
     theory.setPictures(c.getString(c.getColumnIndex(theory_COLUMN_PICTURES)));
  
     return theory;
   }
   
   public boolean updateTheoryOfChapter (Integer theory_id, Integer chapter_id, String theory_text, String pictures )
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("theory_text", theory_text);
      contentValues.put("pictures", pictures);
      
      db.update("theory", contentValues, "theory_id = ? and chapter_id = ? ", new String[] { Integer.toString(theory_id) , Integer.toString(chapter_id)});
      return true;
   }

   public int deleteTheoryOfChapter (int theory_id, int chapter_id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("theory", 
      "theory_id = ? and chapter_id=?", 
      new String[] { Integer.toString(theory_id), Integer.toString(chapter_id) });
   }
   
   public int deleteAllTheoriesOfChapter(int chapter_id){
	   
	   SQLiteDatabase db = this.getWritableDatabase();
	      return db.delete("theory", 
	      "chapter_id=?", 
	      new String[] {String.valueOf(chapter_id) });
   }
   

   public ArrayList<Integer> getTheoryIds(){
	   
	  ArrayList<Integer> theory_id = new ArrayList<Integer>();
	 SQLiteDatabase db = this.getReadableDatabase();
	 Cursor res =  db.rawQuery( "select theory_id from theory",null );
	 res.moveToFirst();
	 
	 while(res.isAfterLast() == false){
		       theory_id.add(res.getInt(res.getColumnIndex(theory_COLUMN_THEORY_ID)));
		       res.moveToNext();
		    }
	 return theory_id;
   }
   
   
   public ArrayList<Theory> getAllTheories()
   {
	    ArrayList<Theory> theory_list = new ArrayList<Theory>();
	    
	    //hp = new HashMap();
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from theory", null );
	 
	 // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Theory theory = new Theory();
                theory.setTheory_id((c.getInt((c.getColumnIndex(theory_COLUMN_THEORY_ID)))));
                theory.setTheory_text(c.getString(c.getColumnIndex(theory_COLUMN_THEORY_TEXT)));
                theory.setPictures(c.getString(c.getColumnIndex(theory_COLUMN_PICTURES)));
                theory.setChapter_id(c.getInt(c.getColumnIndex(theory_COLUMN_CHAPTER)));
                // adding to tags list
               theory_list.add(theory);
            } while (c.moveToNext());
        }
        return theory_list;
    }
   
   
// ----------------------------------------------------------> CRUD for "exercise" <------------------------------------------------------------------------------------
   
   /*
    * check if db is empty
    */
   public int numberOfRowsOfExercise(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, exercise_TABLE_NAME);
	      return numRows;
	   }
   
   
   public boolean insertExerciseToTheory(int theory_id, int chapter_id, String question_text)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("theory_id", theory_id);
      contentValues.put("chapter_id", chapter_id);
      contentValues.put("question_text", question_text);
      
      db.insert("exercise", null, contentValues);
      return true;
   }
   
   
   
   public ArrayList<Exercise> getAllExercisesOfTheory(int theory_id)
   {
	    ArrayList<Exercise> ex_th = new ArrayList<Exercise>();
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from exercise where theory_id= ?", new String[] { String.valueOf(theory_id)} );
	 
	 // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Exercise ex = new Exercise();
                ex.setQuestion_id(c.getInt(c.getColumnIndex(exercise_COLUMN_QUESTION_ID)));
                ex.setTheory_id(c.getInt(c.getColumnIndex(exercise_COLUMN_THEORY_ID)));
                ex.setChapter_id(c.getInt(c.getColumnIndex(exercise_COLUMN_CHAPTER)));
                ex.setQuestion_text(c.getString(c.getColumnIndex(exercise_COLUMN_QUESTION_TEXT)));
               ex_th.add(ex);
            } while (c.moveToNext());
        }
        return ex_th;
    }
   
   
   public ArrayList<String> getAllExerciseTextOfTheory(int theory_id){
	   
	   ArrayList<String> questions = new ArrayList<String>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select question_text from exercise where theory_id="+theory_id, null );
	 
	    res.moveToFirst();
	    
	    while(res.isAfterLast() == false){
	       questions.add(res.getString(res.getColumnIndex(exercise_COLUMN_QUESTION_TEXT)));
	       res.moveToNext();
	    }

	return questions;
	   
   }
   
      public ArrayList<Exercise> getAllExercises()
   {
	    ArrayList<Exercise> ex_list = new ArrayList<Exercise>();
	    
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from exercise", null );
	 
	 // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Exercise ex = new Exercise();
                ex.setQuestion_id(c.getInt(c.getColumnIndex(exercise_COLUMN_QUESTION_ID)));
                ex.setTheory_id(c.getInt(c.getColumnIndex(exercise_COLUMN_THEORY_ID)));
                ex.setChapter_id(c.getInt(c.getColumnIndex(exercise_COLUMN_CHAPTER)));
                ex.setQuestion_text(c.getString(c.getColumnIndex(exercise_COLUMN_QUESTION_TEXT)));
                // adding to tags list
               ex_list.add(ex);
            } while (c.moveToNext());
        }
        return ex_list;
    }
   
      
      public ArrayList<Integer> getTheoryIdsOfExercise(){
   	   
    	  ArrayList<Integer> theory_id = new ArrayList<Integer>();
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 Cursor res =  db.rawQuery( "select theory_id from exercise",null );
    	 res.moveToFirst();
    	 
    	 while(res.isAfterLast() == false){
    		       theory_id.add(res.getInt(res.getColumnIndex(exercise_COLUMN_THEORY_ID)));
    		       res.moveToNext();
    		    }
    	 return theory_id;
       }
   
      
      public Exercise getTextOfQuestionId(int question_id){
 		 SQLiteDatabase db = this.getReadableDatabase();
 	     Cursor c =  db.rawQuery( "select * from exercise where question_id="+question_id, null );
 	     
 	     if (c != null)
  	         c.moveToFirst();
 	     
 	     Exercise ex = new Exercise();
 	     ex.setChapter_id(c.getInt(c.getColumnIndex(exercise_COLUMN_CHAPTER)));
 	     ex.setQuestion_id(c.getInt(c.getColumnIndex(exercise_COLUMN_QUESTION_ID)));
 	     ex.setQuestion_text(c.getString(c.getColumnIndex(exercise_COLUMN_QUESTION_TEXT)));
 	     ex.setTheory_id(c.getInt(c.getColumnIndex(exercise_COLUMN_THEORY_ID)));
 	      
 	     return ex;
 	   }
   
   
// ----------------------------------------------------------> CRUD for "solution" <------------------------------------------------------------------------------------
      /*
       * check if db is empty
       */
      public int numberOfRowsOfSolution(){
   	      SQLiteDatabase db = this.getReadableDatabase();
   	      int numRows = (int) DatabaseUtils.queryNumEntries(db, solution_TABLE_NAME);
   	      return numRows;
   	   }
      
      public boolean insertSolutionToQuestion(int question_id, int theory_id, String solution_text)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("question_id", question_id);
      contentValues.put("theory_id", theory_id);
      contentValues.put("solution_text", solution_text);
      
      db.insert("solution", null, contentValues);
      return true;
   }
   
      public Solution getSolutionOfQuestion(int question_id){
    		 SQLiteDatabase db = this.getReadableDatabase();
    	     Cursor c =  db.rawQuery( "select * from solution where question_id="+question_id, null );
    	     
    	     if (c != null)
     	         c.moveToFirst();
    	     
    	     Solution solution = new Solution();
    	     solution.setSolution_id(c.getInt(c.getColumnIndex(solution_COLUMN_SOLUTION_ID)));
    	     solution.setQuestion_id(c.getInt(c.getColumnIndex(solution_COLUMN_QUESTION_ID)));
    	     solution.setTheory_id(c.getInt(c.getColumnIndex(solution_COLUMN_THEORY_ID)));
    	     solution.setSolution_text(c.getString(c.getColumnIndex(solution_COLUMN_SOLUTION_TEXT)));
    	      
    	     return solution;
    	   }
      
      public Solution getAllSolutions(){
 		 SQLiteDatabase db = this.getReadableDatabase();
 	     Cursor c =  db.rawQuery( "select * from solution ", null );
 	     if (c != null){
 	         c.moveToFirst();
 	     }
 	     Solution solution = new Solution();
 	     solution.setSolution_id(c.getInt(c.getColumnIndex(solution_COLUMN_SOLUTION_ID)));
 	     solution.setTheory_id(c.getInt(c.getColumnIndex(solution_COLUMN_THEORY_ID)));
 	     solution.setQuestion_id(c.getInt(c.getColumnIndex(solution_COLUMN_QUESTION_ID)));
 	     solution.setSolution_text(c.getString(c.getColumnIndex(solution_COLUMN_SOLUTION_TEXT)));
 	  
 	     return solution;
 	   }
      
      public ArrayList<Integer> getQuestionIdsOfSolution(){
    	  
    	  ArrayList<Integer> question_id = new ArrayList<Integer>();
     	 SQLiteDatabase db = this.getReadableDatabase();
     	 Cursor res =  db.rawQuery( "select question_id from solution",null );
     	 res.moveToFirst();
     	 
     	 while(res.isAfterLast() == false){
     		       question_id.add(res.getInt(res.getColumnIndex(solution_COLUMN_QUESTION_ID)));
     		       res.moveToNext();
     		    }
     	 return question_id;
      }
      
      public ArrayList<Integer> getTheoryIdsOfSolution(){
      	   
    	  ArrayList<Integer> theory_id = new ArrayList<Integer>();
    	 SQLiteDatabase db = this.getReadableDatabase();
    	 Cursor res =  db.rawQuery( "select theory_id from solution",null );
    	 res.moveToFirst();
    	 
    	 while(res.isAfterLast() == false){
    		       theory_id.add(res.getInt(res.getColumnIndex(solution_COLUMN_THEORY_ID)));
    		       res.moveToNext();
    		    }
    	 return theory_id;
       }
//   public ArrayList<String> getSolutionOfQuestion(int question_id){
//	   
//	  ArrayList<String> solutions = new ArrayList<String>();
//      SQLiteDatabase db = this.getReadableDatabase();
//      Cursor res =  db.rawQuery( "select solution_text from solution where question_id="+question_id, null );
//      res.moveToFirst();
//      
//      while(res.isAfterLast() == false){
//  	       solutions.add(res.getString(res.getColumnIndex(solution_COLUMN_SOLUTION_TEXT)));
//  	       res.moveToNext();
//  	    }
//      return solutions;
//   }
   
   
   
   public boolean updateSolutionOfQuestion (Integer question_id, Integer solution_id, String solution_text)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("solution_text", solution_text);
      
      db.update("solution", contentValues, "question_id = ? and solution_id = ? ", new String[] { Integer.toString(question_id) , Integer.toString(solution_id)});
      return true;
   }

   public int deleteSolutionOfQuestion(int question_id, int solution_id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("solution", 
      "question_id = ? and solution_id=?", 
      new String[] { Integer.toString(question_id), Integer.toString(solution_id) });
   }
   
   public ArrayList<String> getAllSolutionsOfQuestion(int question_id)
   {
	    ArrayList<String> array_list = new ArrayList<String>();
	    
	    //hp = new HashMap();
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select * from solution where question_id="+question_id, null );
	 
	    res.moveToFirst();
	    
	    while(res.isAfterLast() == false){
	       array_list.add(res.getString(res.getColumnIndex(solution_COLUMN_SOLUTION_TEXT)));
	       res.moveToNext();
	    }

    return array_list;
    }
// ----------------------------------------------------------> CRUD for "user" <------------------------------------------------------------------------------------
   /*
    * check if db is empty
    */
   public int numberOfRowsOfUser(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, user_TABLE_NAME);
	      return numRows;
	   }
   
   public boolean insertUser( String user_name, int new_user)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("user_name", user_name);
      contentValues.put("new_user", new_user);
      
      
      db.insert("user", null, contentValues);
      return true;
   
   }
   
   
   
   
   public User getSpecificUser(int user_id){
	   
	   SQLiteDatabase db = this.getReadableDatabase();
	     Cursor c =  db.rawQuery( "select * from user where user_id="+user_id, null );
	     if (c != null)
	         c.moveToFirst();
	  
	     User user = new User();
	     user.setUser_name(c.getString(c.getColumnIndex(user_COLUMN_USER_NAME)));
	     user.setNew_user(c.getInt(c.getColumnIndex(user_COLUMN_NEW_USER)));
	  
	     return user;
	   
   }
   
//   public String getUsernameOfUser(int user_id){
//	   String username;
//	   
//	   return username;
//   }
   
   public ArrayList<String> getUsernames(){
	   
	   ArrayList<String> user_name = new ArrayList<String>();
	   
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select user_name from user", null );
	 
	    res.moveToFirst();
	    
	    while(res.isAfterLast() == false){
	       user_name.add(res.getString(res.getColumnIndex(user_COLUMN_USER_NAME)));
	       res.moveToNext();
	    }
	   return user_name;
   }
   
   public ArrayList<User> getUsers(){
	   
	   ArrayList<User> user_list = new ArrayList<User>();
	    SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from user", null );
	 
	 // looping through all rows and adding to list
       if (c.moveToFirst()) {
           do {
        	   User user = new User();
        	   user.setUser_id(c.getInt(c.getColumnIndex(user_COLUMN_USER_ID)));
        	   user.setUser_name(c.getString(c.getColumnIndex(user_COLUMN_USER_NAME)));
        	   user.setNew_user(c.getInt(c.getColumnIndex(user_COLUMN_NEW_USER)));
              user_list.add(user);
           } while (c.moveToNext());
       }
	   
	   
	   return user_list;
   }
   
// ----------------------------------------------------------> CRUD for "user_answer" <------------------------------------------------------------------------------------
  
   public int numberOfRowsOfUserAnswers(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, user_answers_TABLE_NAME);
	      return numRows;
	   }

   
   public boolean insertUserAnswer(int user_id, int question_id ,String user_answer, int error)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("user_id", user_id);
      contentValues.put("question_id", question_id);
      contentValues.put("user_answer", user_answer);
      contentValues.put("error", error);
      
      db.insert("user_answers", null, contentValues);
      return true;
   
   }
   
   public UserAnswers getSpecificUserAnswer(int user_id, int question_id){
	   
	   SQLiteDatabase db = this.getReadableDatabase();
	   
		 UserAnswers user_answer = new UserAnswers();

	     Cursor c =  db.rawQuery( "select * from user_answers where user_id="+user_id+" and question_id="+question_id, null );
	     if (c.moveToFirst()){
	    	   while(!c.isAfterLast()){
	    		   	
	    		   	 user_answer.setUser_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_USER_ID)));
	    			 user_answer.setQuestion_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_QUESTION_ID)));
	    			 user_answer.setUser_answer(c.getString(c.getColumnIndex(user_answers_COLUMN_USER_ANSWER)));
	    			 user_answer.setError(c.getInt(c.getColumnIndex(user_answers_COLUMN_ERROR)));
	    	      c.moveToNext();
	    	   }
	    	}
	    	c.close();
	   return user_answer;
   }
   
   public boolean updateUserAnswer (int user_id, int question_id ,String user_answer, int error)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      //contentValues.put("user_id", user_id);
      contentValues.put("user_answer", user_answer);
      contentValues.put("error", error);

      
      db.update("user_answers", contentValues, "user_id = ? and question_id = ?", new String[] { Integer.toString(user_id), Integer.toString(question_id) } );
      
      return true;
   }
   
   public ArrayList<UserAnswers> getCorrectUserAnswers(int user_id, int error){
	   
	   ArrayList<UserAnswers> user_answers = new ArrayList<UserAnswers>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from user_answers where error="+error+" and user_id="+user_id, null );
	    
	 
	 // looping through all rows and adding to list
      if (c.moveToFirst()) {
          do {
        	  UserAnswers us_an = new UserAnswers();
        	  us_an.setUser_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_USER_ID)));
        	  us_an.setQuestion_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_QUESTION_ID)));
        	  us_an.setUser_answer(c.getString(c.getColumnIndex(user_answers_COLUMN_USER_ANSWER)));
        	  us_an.setError(c.getInt(c.getColumnIndex(user_answers_COLUMN_ERROR)));
        	  user_answers.add(us_an);
          } while (c.moveToNext());
          
	   
      }
	   return user_answers;

   }
   
   
   public ArrayList<UserAnswers> getWrongUserAnswers(int user_id, int error){
	   
	   ArrayList<UserAnswers> user_answers = new ArrayList<UserAnswers>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from user_answers where error="+error+" and user_id="+user_id, null );
	 
	 // looping through all rows and adding to list
      if (c.moveToFirst()) {
          do {
        	  UserAnswers us_an = new UserAnswers();
        	  us_an.setUser_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_USER_ID)));
        	  us_an.setQuestion_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_QUESTION_ID)));
        	  us_an.setUser_answer(c.getString(c.getColumnIndex(user_answers_COLUMN_USER_ANSWER)));
        	  us_an.setError(c.getInt(c.getColumnIndex(user_answers_COLUMN_ERROR)));
        	  user_answers.add(us_an);
          } while (c.moveToNext());
          
	   
      }
	   return user_answers;

   }
   
   
   public ArrayList<UserAnswers> getAllUserAnswers(int user_id){
	   
	   ArrayList<UserAnswers> user_answers = new ArrayList<UserAnswers>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from user_answers where user_id="+user_id, null );
	 
	 // looping through all rows and adding to list
      if (c.moveToFirst()) {
          do {
        	  UserAnswers us_an = new UserAnswers();
        	  us_an.setUser_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_USER_ID)));
        	  us_an.setQuestion_id(c.getInt(c.getColumnIndex(user_answers_COLUMN_QUESTION_ID)));
        	  us_an.setUser_answer(c.getString(c.getColumnIndex(user_answers_COLUMN_USER_ANSWER)));
        	  us_an.setError(c.getInt(c.getColumnIndex(user_answers_COLUMN_ERROR)));
        	  user_answers.add(us_an);
          } while (c.moveToNext());
          
	   
      }
	   return user_answers;

   }
   
   
   
   
// ----------------------------------------------------------> CRUD for "user_stats" <------------------------------------------------------------------------------------
   public int numberOfRowsOfUserStats(){
	      SQLiteDatabase db = this.getReadableDatabase();
	      int numRows = (int) DatabaseUtils.queryNumEntries(db, user_stats_TABLE_NAME);
	      return numRows;
	   }
   
   public boolean insertUserStats(int user_id, String chapter, int question_id, int correct_answer_id, int wrong_answer_id, int blank_answer_id, double success)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("user_id", user_id);
      contentValues.put("chapter",chapter);
      contentValues.put("question_id", question_id);
      contentValues.put("correct_answer_id", correct_answer_id);
      contentValues.put("wrong_answer_id",wrong_answer_id);
      contentValues.put("blank_answer_id", blank_answer_id);
      contentValues.put("success",success);
      
      
      db.insert("user_stats", null, contentValues);
      return true;
      
      
   }
   
   
   public boolean updateUserStats(int user_id, String chapter, int question_id, int correct_answer_id, int wrong_answer_id, int blank_answer_id, double success)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("user_id", user_id);
      contentValues.put("chapter",chapter);
      contentValues.put("question_id", question_id);
      contentValues.put("correct_answer_id", correct_answer_id);
      contentValues.put("wrong_answer_id",wrong_answer_id);
      contentValues.put("blank_answer_id", blank_answer_id);
      contentValues.put("success",success);
      
      
      db.update("user_stats", contentValues, "user_id = ? and question_id = ? and chapter= ?", new String[] { Integer.toString(user_id), Integer.toString(question_id) , chapter} );
      return true;
      
      
   }
   
   public ArrayList<UserStats> getUserStats(int user_id){
	   
	   ArrayList<UserStats> user_stats = new ArrayList<UserStats>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from user_stats where user_id="+user_id, null );
	 
	 // looping through all rows and adding to list
      if (c.moveToFirst()) {
          do {
        	  UserStats user_st = new UserStats();
        	  user_st.setUser_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_USER_ID)));
        	  user_st.setChapter(c.getString(c.getColumnIndex(user_stats_COLUMN_CHAPTER)));
        	  user_st.setQuestion_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_QUESTION_ID)));
        	  user_st.setCorrect_answer_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_CORRECT_ANSWER_ID)));
        	  user_st.setWrong_answer_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_WRONG_ANSWER_ID)));
        	  user_st.setBlank_answer_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_BLANK_ANSWER_ID)));
        	  user_st.setSuccess(c.getDouble(c.getColumnIndex(user_stats_COLUMN_SUCCESS)));
        	  user_stats.add(user_st);
          } while (c.moveToNext());
          
	   
      }
	   return user_stats;
	   
   }
   
   public ArrayList<UserStats> getSpecificUserStats(int user_id, String chapter){
	   
	   ArrayList<UserStats> user_stats = new ArrayList<UserStats>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor  c =  db.rawQuery( "select * from user_stats where user_id="+user_id+" and chapter='"+chapter+"'", null );
	 
	 // looping through all rows and adding to list
      if (c.moveToFirst()) {
          do {
        	  UserStats user_st = new UserStats();
        	  user_st.setUser_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_USER_ID)));
        	  user_st.setChapter(c.getString(c.getColumnIndex(user_stats_COLUMN_CHAPTER)));
        	  user_st.setQuestion_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_QUESTION_ID)));
        	  user_st.setCorrect_answer_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_CORRECT_ANSWER_ID)));
        	  user_st.setWrong_answer_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_WRONG_ANSWER_ID)));
        	  user_st.setBlank_answer_id(c.getInt(c.getColumnIndex(user_stats_COLUMN_BLANK_ANSWER_ID)));
        	  user_st.setSuccess(c.getDouble(c.getColumnIndex(user_stats_COLUMN_SUCCESS)));
        	  user_stats.add(user_st);
          } while (c.moveToNext());
          
	   
      }
	   return user_stats;
	   
   }
   
   
   public ArrayList<Integer> getCorrectAnswers(int correct_ans, int user_id, String chapter){
	   
	   ArrayList<Integer> correct_question_ids = new ArrayList<Integer>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select question_id from user_stats where correct_answer_id="+correct_ans+" and chapter='"+chapter+"' and user_id="+user_id, null );
	 
	    res.moveToFirst();
	    
	    while(res.isAfterLast() == false){
	       correct_question_ids.add(res.getInt(res.getColumnIndex(user_stats_COLUMN_QUESTION_ID)));
	       res.moveToNext();
	    }
	   return correct_question_ids;
   }
   
   
   public ArrayList<Integer> getWrongAnswers(int wrong_ans, int user_id, String chapter){
	   
	   ArrayList<Integer> wrong_question_ids = new ArrayList<Integer>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select question_id from user_stats where wrong_answer_id="+wrong_ans+" and chapter='"+chapter+"' and user_id="+user_id, null );
	 
	    res.moveToFirst();
	    
	    while(res.isAfterLast() == false){
	       wrong_question_ids.add(res.getInt(res.getColumnIndex(user_stats_COLUMN_QUESTION_ID)));
	       res.moveToNext();
	    }
	   return wrong_question_ids;
   }
   
   
   public ArrayList<Integer> getBlankAnswers(int blank_ans, int user_id, String chapter){
	   
	   ArrayList<Integer> blank_question_ids = new ArrayList<Integer>();
	   SQLiteDatabase db = this.getReadableDatabase();
	    Cursor res =  db.rawQuery( "select question_id from user_stats where blank_answer_id="+blank_ans+" and chapter='"+chapter+"' and user_id="+user_id, null );
	 
	    res.moveToFirst();
	    
	    while(res.isAfterLast() == false){
	      blank_question_ids.add(res.getInt(res.getColumnIndex(user_stats_COLUMN_QUESTION_ID)));
	       res.moveToNext();
	    }
	   return blank_question_ids;
   }
}