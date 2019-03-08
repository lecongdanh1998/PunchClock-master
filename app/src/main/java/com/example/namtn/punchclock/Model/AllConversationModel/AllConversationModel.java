package com.example.namtn.punchclock.Model.AllConversationModel;

import com.example.namtn.punchclock.Adapter.AllConversation.ConversationAdapter;

public interface AllConversationModel {

    interface OnConversationListener{
        void fetchAllConversationSuccess(ConversationAdapter adapter);
    }

    void addConversation(OnConversationListener listener, String data);

    void fetchConversation(OnConversationListener listener);
}
