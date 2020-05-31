package com.hhu.cshelper.ui.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hhu.cshelper.R;
import com.hhu.cshelper.adapter.BaseAdapter;
import com.hhu.cshelper.database.NoteDatabaseHelper;
import com.hhu.cshelper.entity.Note;

import java.util.List;

/** 设置笔记块的一些属性
 * @name  AllNoteFragment
 * @description 设置笔记块的一些属性
 * @author  dyh
 * @date  2020/4/29
 */
public class AllNoteFragment extends BaseNoteListFragment  {

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }




    private OnFragmentInteractionListener mListener;

    @Override
    void onRefreshData() {
        mNotes = NoteDatabaseHelper.getInstance().getAllNotes();
        mAdapter.refreshData(mNotes);
    }

    @Override
    List<Note> onGetNotes() {
        mNotes = NoteDatabaseHelper.getInstance().getAllNotes();
        //Log.d("test" , mNotes.toString());
        return mNotes;
    }

    @Override
    void onNoteItemLongClick(View v, int position) {
        //TODO
    }



//    public AllNoteFragment() {
//        // Required empty public constructor
//    }











//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}