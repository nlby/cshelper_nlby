package com.hhu.cshelper.ui.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.hhu.cshelper.R;
import com.hhu.cshelper.adapter.MainPageAdapter;
import com.hhu.cshelper.adapter.OnItemClickListener;
import com.hhu.cshelper.adapter.OnItemLongClickListener;
import com.hhu.cshelper.entity.Note;
import com.hhu.cshelper.ui.activity.MainActivity;
import com.hhu.cshelper.ui.activity.NoteDetailActivity;
import com.hhu.cshelper.ui.activity.SearchActivity;

import java.util.Collections;
import java.util.List;

public abstract class BaseNoteListFragment extends Fragment {
    protected View mView;
    protected MainPageAdapter mAdapter;
    protected RecyclerView mRecyclerView;
    protected MainActivity mMainActivity;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected List<Note> mNotes;

    private boolean isGridMode = true;

    abstract View createView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState);

    //abstract void initData();

    //abstract void initView();

    abstract void onRefreshData();

    abstract List<Note> onGetNotes();

    abstract void onNoteItemLongClick(View v, int position);

    private OnFragmentInteractionListener mListener;




    public BaseNoteListFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        if(!hidden) resumeUI();
//    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        resumeUI();
//    }
//
//    private void resumeUI() {
//        onRefreshData();
//    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment BaseNoteListFragment.
     */




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = createView(inflater , container , savedInstanceState);
        return mView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        mMainActivity = (MainActivity) getActivity();
        init();

    }

    //在recyclerview中初始化原始的note_list

    /**
     * @name
     * @description 在recyclerview中初始化原始的note_list,刚开始设置成网格显示，设置刷新事件和note item的点击事件
     * @param
     * @return
     * @date
     */
    @SuppressLint("ResourceAsColor")
    private void init() {
        mRecyclerView = mView.findViewById(R.id.notes_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mView.getContext() , 2));
        mRecyclerView.setHasFixedSize(true);  //true if adapter changes cannot affect the size of the RecyclerView.

        mAdapter = new MainPageAdapter(getActivity(), onGetNotes());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = mView.findViewById(R.id.main_page);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.swipe_fresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {  //进入note详情
            @Override
            public void onItemClick(View v, int position) {
                gotoNoteDetailActivity(position);                   //本身的回退栈效果
            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {  //开一个popupwindow
            @Override
            public void onItemLongClick(View v, int position) {
                //TODO
            }
        });
    }

    //跳转到noteactivity时传递参数以定位
    private void gotoNoteDetailActivity(int position) {
        Note note = mNotes.get(position);
        Intent intent = new Intent(mMainActivity, NoteDetailActivity.class);
        intent.putExtra("note_item", note  );
        intent.putExtra("pos", position);
        intent.putExtra("id" , note.getId());
        startActivity(intent);
    }

    public void swichMode(boolean isGridMode){
        if(isGridMode){
            mRecyclerView.setLayoutManager(new GridLayoutManager(mView.getContext() , 2));
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setIsGridMode(false);
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * @name  onOptionsItemSelected
     * @description 设置主toolbar各个item的选择事件
     * @param
     * @return
     * @date  2020/4/29
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_search:{
                //TODO
                //Toast.makeText(getActivity() , R.string.to_be_perfect , Toast.LENGTH_SHORT).show();
                gotoSearchActivity();
                break;
            }
            case R.id.menu_sort:{
                doSort();
                break;
            }
            case R.id.menu_settings:{
                //TODO

                break;
            }
            case R.id.switch_mode:{
                isGridMode = !isGridMode;
                swichMode(isGridMode);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void gotoSearchActivity() {
        Bundle options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                getActivity().findViewById(R.id.menu_search),
                getString(R.string.scene_transition_search)).toBundle();
        startActivity(new Intent(getActivity(), SearchActivity.class), options);
    }

    /**
     * @name  doSort
     * @description 按时间顺序排序
     * @param
     * @return
     * @date  2020/4/29
     */
    private void doSort() {

        PopupMenu popupMenu = new PopupMenu(mMainActivity , getActivity().findViewById(R.id.menu_sort));
        popupMenu.inflate(R.menu.sort);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.sort_incline:{
                        Collections.sort(mNotes);
                        mAdapter.refreshData(mNotes);
                        break;
                    }
                    case R.id.sort_decline:{
                        Collections.sort(mNotes , Collections.<Note>reverseOrder());
                        mAdapter.refreshData(mNotes);
                        break;
                    }
                }

                return false;
            }
        });
        popupMenu.show();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
