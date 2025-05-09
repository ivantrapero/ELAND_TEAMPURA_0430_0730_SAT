package com.trapero.cchoice.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.trapero.cchoice.models.ChatMessage; // Use ChatMessage
import com.trapero.cchoice.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MessageViewModel extends ViewModel {

    // Use MutableLiveData<List<ChatMessage>>
    private final MutableLiveData<List<ChatMessage>> messages = new MutableLiveData<>(new ArrayList<>());

    public MessageViewModel() {
        loadMessages();
    }

    private void loadMessages() {
        List<ChatMessage> dummyList = new ArrayList<>();
        //  ChatMessage objects
        String timestamp = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(Calendar.getInstance().getTime());
        dummyList.add(new ChatMessage("John Doe", "Hello buyer, masaya kaming makatulong!", timestamp, R.drawable.ic_cat_logo, false));
        messages.setValue(dummyList);
    }

    // Use LiveData<List<ChatMessage>>
    public LiveData<List<ChatMessage>> getMessages() {
        return messages;
    }
}
