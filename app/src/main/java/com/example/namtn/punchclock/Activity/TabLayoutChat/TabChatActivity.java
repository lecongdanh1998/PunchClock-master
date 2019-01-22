package com.example.namtn.punchclock.Activity.TabLayoutChat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.namtn.punchclock.Activity.BaseActivity;
import com.example.namtn.punchclock.Activity.TabLayoutChat.Activate.ActivateFragment;
import com.example.namtn.punchclock.Activity.TabLayoutChat.Message.MessageFragment;
import com.example.namtn.punchclock.Activity.User.UserActivity;
import com.example.namtn.punchclock.Adapter.AdapterInforRecyr;
import com.example.namtn.punchclock.Adapter.AdapterTabLayoutFrangment;
import com.example.namtn.punchclock.Model.ModelInfoUser.ContructorInfoUser;
import com.example.namtn.punchclock.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TabChatActivity extends BaseActivity implements View.OnClickListener
{
    public TabLayout tab_layout;
    public ViewPager mViewPager;
    ArrayList listFragment;
    ArrayList<String> listTitle;
    AdapterTabLayoutFrangment adapterTabLayoutFrangment;
    public TextView tabOne, tabTwo;
    public int index_change;
    ArrayList<ContructorInfoUser> arrayListOnline;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReferenceChatUserCheckConnect;
    ImageView img_logout, img_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tab_chat;
    }

    @Override
    protected void initView() {
        img_account = findViewById(R.id.img_account);
        img_logout = findViewById(R.id.img_logout);
        tab_layout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        tab_layout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initEventControl() {
        img_account.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tabOne.setText(getResources().getString(R.string.txt_message));
                        tabOne.setTextColor(getResources().getColor(R.color.colorTextBlue));
                        tab_layout.getTabAt(0).setCustomView(tabOne);
                        if (arrayListOnline.size() > 0) {
                            tabTwo.setText(getResources().getString(R.string.txt_activate) + " (" + arrayListOnline.size() + ")");
                        } else {
                            tabTwo.setText(getResources().getString(R.string.txt_activate));
                        }
                        tabTwo.setTextColor(getResources().getColor(R.color.colorTextBlack));
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        break;
                    case 1:
                        tabOne.setText(getResources().getString(R.string.txt_message));
                        tabOne.setTextColor(getResources().getColor(R.color.colorTextBlack));
                        tab_layout.getTabAt(0).setCustomView(tabOne);

                        if (arrayListOnline.size() > 0) {
                            tabTwo.setText(getResources().getString(R.string.txt_activate) + " (" + arrayListOnline.size() + ")");
                        } else {
                            tabTwo.setText(getResources().getString(R.string.txt_activate));
                        }
                        tabTwo.setTextColor(getResources().getColor(R.color.colorTextBlue));
                        tab_layout.getTabAt(1).setCustomView(tabTwo);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initData() {
        arrayListOnline = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReferenceChatUserCheckConnect = FirebaseDatabase.getInstance().getReference("Users");
        databaseReferenceChatUserCheckConnect.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayListOnline.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ContructorInfoUser user = snapshot.getValue(ContructorInfoUser.class);
                    assert user != null;
                    assert firebaseUser != null;
                    if (!user.getId().equals(firebaseUser.getUid())) {
                        if (user.getOnline().equals("on")) {
                            arrayListOnline.add(user);
                        }
                    }
                }
                listFragment = new ArrayList();
                listFragment.add(new MessageFragment());
                listFragment.add(new ActivateFragment());
                listTitle = new ArrayList<>();
                listTitle.add(getResources().getString(R.string.txt_message));
                if (arrayListOnline.size() > 0) {
                    listTitle.add(getResources().getString(R.string.txt_activate) + " (" + arrayListOnline.size() + ")");
                } else {
                    listTitle.add(getResources().getString(R.string.txt_activate));
                }

                adapterTabLayoutFrangment = new AdapterTabLayoutFrangment(
                        getSupportFragmentManager(), getApplicationContext(), listTitle, listFragment
                );

                mViewPager.setAdapter(adapterTabLayoutFrangment);
                createTabIcons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError: ", "" + databaseError);
            }
        });

    }

    private void createTabIcons() {
        tabOne = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tabsarena, null);
        tabOne.setText(getResources().getString(R.string.txt_message));
        tabOne.setTextColor(getResources().getColor(R.color.colorTextBlue));
        tab_layout.getTabAt(0).setCustomView(tabOne);
        // icon Hôm nay
        tabTwo = (TextView) LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tabsarena, null);
        if (arrayListOnline.size() > 0) {
            tabTwo.setText(getResources().getString(R.string.txt_activate) + " (" + arrayListOnline.size() + ")");
        } else {
            tabTwo.setText(getResources().getString(R.string.txt_activate));
        }
        tabTwo.setTextColor(getResources().getColor(R.color.colorTextBlack));
        tab_layout.getTabAt(1).setCustomView(tabTwo);
        // icon Tham quan
        mViewPager.setCurrentItem(index_change);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_account:
                Intent intent = new Intent(this,UserActivity.class);
                startActivity(intent);
                break;
        }
    }
}
