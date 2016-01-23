package com.example.mymathapp;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link MultipleChoiceFragment.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link MultipleChoiceFragment#newInstance}
 * factory method to create an instance of this fragment.
 *
 */
public class MultipleChoiceFragment extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String EX_QUESTION = "ex_question";
	private static final String EX_CHOICE = "ex_choice";

	// TODO: Rename and change types of parameters
	private ArrayList<String> m_questions;
	private ArrayList<String> m_choices;

	private OnFragmentInteractionListener mListener;
	
	View view;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment MultipleChoiceFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static MultipleChoiceFragment newInstance(ArrayList<String> param1,
			ArrayList<String> param2) {
		MultipleChoiceFragment fragment = new MultipleChoiceFragment();
		Bundle args = new Bundle();
		args.putStringArrayList(EX_QUESTION,param1);
		args.putStringArrayList(EX_CHOICE, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public MultipleChoiceFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			m_questions = getArguments().getStringArrayList(EX_QUESTION);
			m_choices = getArguments().getStringArrayList(EX_CHOICE);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		ScrollView scroll = new ScrollView(getActivity());
		RelativeLayout.LayoutParams scroll_params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		scroll.setLayoutParams(scroll_params);
		scroll.setFillViewport(true);
		
		LinearLayout ll = new LinearLayout(getActivity());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(params);
        ll.setOrientation(LinearLayout.VERTICAL);
        
        
        final TextView title_question = new TextView(getActivity());
		title_question.setText(m_questions.get(0));
		title_question.setTypeface(null, Typeface.BOLD_ITALIC);
		ll.addView(title_question);
		
        for (int i=1;i<m_questions.size();i++){
        	
        	TextView question = new TextView(getActivity());
        	question.setText(m_questions.get(i));
        	RadioGroup rg = new RadioGroup(getActivity());
    		
    		for (int j=1;j<3;j++){
    			
    			RadioButton rb = new RadioButton(getActivity());
    			rb.setId(j);
    			rb.setText(m_choices.get(j));
    			rg.addView(rb);
    		}
    		ll.addView(question);
    		ll.addView(rg);
    		
        }
        scroll.addView(ll);
		view = scroll;

		
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
