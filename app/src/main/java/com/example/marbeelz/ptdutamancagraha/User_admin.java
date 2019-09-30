package com.example.marbeelz.ptdutamancagraha;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User_admin extends Fragment {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Users;
    EditText username,password;
    Button button;
    FloatingActionButton floatingActionButton;
    ListView listView;
    ArrayList<String> list;
    //ArrayAdapter<String> adapter;
    User user;;
    SimpleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("User");
        final View view = inflater.inflate(R.layout.fragment_3, container, false);
        final List<HashMap<String,String>> listItems = new ArrayList<>();
        adapter = new SimpleAdapter(getActivity(),listItems,R.layout.user_list,new String[]{"1","2"},
                new int[]{R.id.username_info,R.id.password_info});
        list = new ArrayList<>();
        floatingActionButton = view.findViewById(R.id.tambah_user);
        listView = view.findViewById(R.id.listViewUser);
        setHasOptionsMenu(true);
        registerForContextMenu(listView);
        Users = FirebaseDatabase.getInstance().getReference("Users");
        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    user = ds.getValue(User.class);
                    HashMap<String,String> results = new HashMap<>();
                    results.put("1",user.getUsername());
                    results.put("2",user.getPassword());
                    listItems.add(results);
                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahUser_admin tambahUseradmin = new TambahUser_admin();
                HomeActivity homeActivity = (HomeActivity) getContext();
                homeActivity.switchContent(R.id.fragment_container, tambahUseradmin);
            }
        });

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.hapus_item:
                HashMap<String,String> temp;
                temp = (HashMap) adapter.getItem(info.position);
                Users.child(temp.get("1")).removeValue();
                User_admin useradmin = new User_admin();
                HomeActivity homeActivity = (HomeActivity) getContext();
                homeActivity.switchContent(R.id.fragment_container, useradmin);
            default:
                return super.onContextItemSelected(item);
        }
    }
}

