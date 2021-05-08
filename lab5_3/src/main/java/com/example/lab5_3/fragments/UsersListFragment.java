package com.example.lab5_3.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lab5_3.MainActivity;
import com.example.lab5_3.models.User;
import com.example.lab5_3.viewmodel.UserViewModel;
import com.example.lab5_3.adapters.UsersListAdapter;
import com.example.lab8_2_room_albums.databinding.FragmentUserBinding;
import com.example.lab8_2_room_albums.databinding.FragmentUsersListBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AlbumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersListFragment extends Fragment {

    private UsersListAdapter usersListAdapter;
    private boolean mIsFirstTime;
    private FragmentUsersListBinding fragmentUsersListBinding;

    protected List<User> myDataSet;

    private UserViewModel userViewModel;

    public UsersListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    /**
     * @return A new instance of fragment AlbumFragment.
     */
    public static UsersListFragment newInstance() {
        UsersListFragment fragment = new UsersListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState==null) {
            mIsFirstTime = true;
        }

        // 1. Initialiserer ViewModel-objektet:
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // 2. Lager et Observe-objekt:
        final Observer<List<User>> usersListObservator = new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> allUsers) {
                myDataSet = allUsers;
                // Fyller recycler view-lista:
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                fragmentUsersListBinding.usersRecyclerView.setLayoutManager(layoutManager);
                usersListAdapter = new UsersListAdapter(myDataSet);
                usersListAdapter.setClickListener(new UsersListAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Integer userId = myDataSet.get(position).getId();
                        // Fragmenttransaksjonen, dvs. bytte av fragment, gjøres her av aktiviteten:
                        UserFragment userFragment = UserFragment.newInstance(userId);
                        if (isAdded()) {
                            ((MainActivity)getActivity()).replaceFragmentWidth(userFragment, true);
                        }
                    }
                });
                fragmentUsersListBinding.usersRecyclerView.setAdapter(usersListAdapter);
                usersListAdapter.setUsersDataSet(allUsers);
                usersListAdapter.notifyDataSetChanged();

                fragmentUsersListBinding.progressWork.setVisibility(View.GONE);
            }
        };
        // 3. Start observering:
        userViewModel.getUserListData(mIsFirstTime).observe(this, usersListObservator);

        final Observer<Integer> progressObservator = new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable final Integer intValue) {
                fragmentUsersListBinding.errorTexts.setVisibility(intValue);
            }
        };
        userViewModel.getErrorUserList().observe(this, progressObservator);

        final Observer<String> errorObservator = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String msg) {
                fragmentUsersListBinding.errorTexts.setText(msg);
                //  userAdapter.setClickListener(null);
            }
        };
        userViewModel.getErrorMessage().observe(this, errorObservator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_users_list, container, false);
        fragmentUsersListBinding = FragmentUsersListBinding.inflate(inflater, container, false);
        return fragmentUsersListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //userRecyclerView = view.findViewById(R.id.usersRecyclerView);
        //progressBar = view.findViewById(R.id.progressWork);
        fragmentUsersListBinding.progressWork.setVisibility(View.VISIBLE);
        //errorText = view.findViewById(R.id.error_texts);

    }
}