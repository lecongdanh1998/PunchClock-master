package com.example.namtn.punchclock.Presenter.AllConversationPresenter;

import com.example.namtn.punchclock.Adapter.AllConversation.ConversationAdapter;
import com.example.namtn.punchclock.Model.AllConversationModel.AllConversationModel;
import com.example.namtn.punchclock.View.AllConversationView;

public class AlLConversationPresenterImpl implements AlLConversationPresenter, AllConversationModel.OnConversationListener {

    private AllConversationModel model;
    private AllConversationView view;

    public AlLConversationPresenterImpl(AllConversationModel model, AllConversationView view) {
        this.model = model;
        this.view = view;
        if (model != null){
            model.fetchConversation(this);
        }
    }

    @Override
    public void fetchAllConversationSuccess(ConversationAdapter adapter) {
        if (view != null){
            view.fetchAllConversationSuccess(adapter);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
