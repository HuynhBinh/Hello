package com.hnb.hello.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.hnb.hello.R;
import com.hnb.hello.adapter.MessagesListAdapter;
import com.hnb.hello.base.BaseActivity;
import com.hnb.hello.daocontroller.LinkController;
import com.hnb.hello.model.Message;
import com.hnb.hello.service.APIService;
import com.hnb.hello.utils.Detector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import greendao.ObjectLink;

/**
 * Created by USER on 7/28/2015.
 */
public class ChatActivity extends BaseActivity
{

    // view
    private Button btnSend;
    private EditText inputMsg;
    // Chat messages list adapter
    private MessagesListAdapter adapter;
    private List<Message> listMessages;
    private ListView listViewMessages;

    // static field
    public static final String MENTION = "mentions";
    public static final String EMOTICON = "emoticons";
    public static final String LINKS = "links";
    public static final String URL = "url";
    public static final String TITLE = "title";


    public int CURRENT_TEST_DATA_POS = 0;
    public static final List<String> listSampleTestData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initTestData();
        initView();
        registerReceiver();


        // init list message adapter
        listMessages = new ArrayList<Message>();
        adapter = new MessagesListAdapter(this, listMessages);
        // set adapter to listview
        listViewMessages.setAdapter(adapter);
    }

    private void initTestData()
    {
        listSampleTestData.add("@chris you around?");
        listSampleTestData.add("Good morning! (megusta) (coffee)");
        listSampleTestData.add("see you tmr (troll)(megusta) @Luck");
        listSampleTestData.add("@Chris @Ben @Cassey how r you? (megusta)(happy)(cool)(troll)");
        listSampleTestData.add("Olympics are starting soon; http://www.nbcolympics.com");
        listSampleTestData.add("@Chris @Ben @Cassey how r you? have you guys check this link (https://github.com/square/leakcanary) this must be cool (happy) (cool)");
        listSampleTestData.add("@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016");

    }


    // start service to load url title in background
    // input: the whole message that user typed
    private void startService(String input)
    {
        Intent intent = new Intent(ChatActivity.this, APIService.class);
        intent.setAction(APIService.ACTION_GET_TITLE);
        intent.putExtra(APIService.EXTRA_INPUT, input);

        startService(intent);
    }


    // register receiver for broadcast from background intent service
    private void registerReceiver()
    {
        if (activityReceiver != null)
        {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(APIService.RECEIVER_GET_TITLE);
            registerReceiver(activityReceiver, intentFilter);
        }
    }


    private void setNextTestData(EditText editText)
    {
        if (CURRENT_TEST_DATA_POS < listSampleTestData.size())
        {
            editText.setText(listSampleTestData.get(CURRENT_TEST_DATA_POS));
            CURRENT_TEST_DATA_POS++;
        }
        else
        {
            editText.setText("");
        }
    }


    // init view, map view with Object and set listener...
    private void initView()
    {
        btnSend = (Button) findViewById(R.id.btnSend);
        inputMsg = (EditText) findViewById(R.id.inputMsg);
        listViewMessages = (ListView) findViewById(R.id.list_view_messages);

        //inputMsg.setText(listSampleTestData.get(CURRENT_TEST_DATA_POS));
        setNextTestData(inputMsg);

        btnSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String input = inputMsg.getText().toString().trim();
                if (!input.isEmpty())
                {
                    Message m = new Message("Me", input, true);
                    listMessages.add(m);
                    adapter.notifyDataSetChanged();

                    // detect data
                    parseData(inputMsg.getText().toString());

                    // Clearing the input filed once message was sent
                    //inputMsg.setText("");
                    setNextTestData(inputMsg);
                }
                else
                {
                    showToast("Please type something to chat!");
                }
            }
        });


    }


    // parse the data user had typed to detect mentions, emoticons and links
    private void parseData(String input)
    {
        Detector myDetector = new Detector();
        ArrayList<String> listLink = myDetector.detectLinks(input);

        JSONObject jsonObject = new JSONObject();

        if (listLink.size() > 0) // if have link, load link title, then please see the activityReceiver callback
        {
            startService(input);
        }
        else // if no link, just parse mentions, emoticons and show data
        {
            List<String> listMention = myDetector.detectMentions(input);
            List<String> listEmoticon = myDetector.detectEmoticons(input);

            if (listMention.size() > 0)
            {
                JSONArray jsonArray = new JSONArray();
                for (String str : listMention)
                {
                    jsonArray.put(str);
                }
                try
                {

                    jsonObject.put(MENTION, jsonArray);
                    jsonObject.toString();
                }
                catch (Exception ex)
                {
                    // not yet handle
                }

            }


            if (listEmoticon.size() > 0)
            {

                JSONArray jsonArray = new JSONArray();
                for (String str : listEmoticon)
                {
                    jsonArray.put(str);
                }
                try
                {

                    jsonObject.put(EMOTICON, jsonArray);
                    jsonObject.toString();
                }
                catch (Exception ex)
                {
                    // not yet handle
                }

            }

            int length = jsonObject.length();

            String result = "Hello hello hello, How are you!";

            if (length != 0)
            {
                result = jsonObject.toString();
            }

            onMessageReceived(result);
        }


    }


    // create message, then add to message list then show on screen
    private void onMessageReceived(String message)
    {
        Message m = new Message("Robot", message, false);

        listMessages.add(m);

        adapter.notifyDataSetChanged();
    }


    // on destroy of activity
    // un register the broadcast receiver, avoid leak
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (activityReceiver != null)
        {
            unregisterReceiver(activityReceiver);
        }
    }


    // parse data if there is link inside
    private void parseDataIfLink(String input)
    {
        Detector myDetector = new Detector();

        JSONObject jsonObject = new JSONObject();

        List<String> listMention = myDetector.detectMentions(input);
        List<String> listEmoticon = myDetector.detectEmoticons(input);

        if (listMention.size() > 0)
        {
            JSONArray jsonArray = new JSONArray();
            for (String str : listMention)
            {
                jsonArray.put(str);
            }
            try
            {
                jsonObject.put(MENTION, jsonArray);

            }
            catch (Exception ex)
            {
            }

        }


        List<ObjectLink> links = LinkController.getAll(ChatActivity.this);

        if (links.size() > 0)
        {

            try
            {
                JSONArray jsonArray = new JSONArray();
                for (ObjectLink link : links)
                {
                    JSONObject obj = new JSONObject();
                    obj.put(TITLE, link.getTitle());
                    obj.put(URL, link.getUrl());
                    jsonArray.put(obj);
                }

                jsonObject.put(LINKS, jsonArray);

            }
            catch (Exception ex)
            {
            }
        }


        if (listEmoticon.size() > 0)
        {

            JSONArray jsonArray = new JSONArray();
            for (String str : listEmoticon)
            {
                jsonArray.put(str);
            }
            try
            {
                jsonObject.put(EMOTICON, jsonArray);

            }
            catch (Exception ex)
            {
            }

        }

        int length = jsonObject.length();

        String result = "Hello hello hello, How are you!";

        if (length != 0)
        {
            result = jsonObject.toString();
        }

        onMessageReceived(result);
    }


    // receiver to receive broadcast from background intent service
    private BroadcastReceiver activityReceiver = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent)
        {
            // check if correct receiver
            if (intent.getAction().equalsIgnoreCase(APIService.RECEIVER_GET_TITLE))
            {
                String result = intent.getStringExtra(APIService.EXTRA_RESULT);
                // check if return data is OK
                if (result.equals(APIService.RESULT_OK))
                {
                    String input = intent.getStringExtra(APIService.EXTRA_RESULT_MESSAGE);
                    parseDataIfLink(input);
                }
                else if (result.equals(APIService.RESULT_FAIL)) // check if return data is FAIL
                {
                    String input = intent.getStringExtra(APIService.EXTRA_RESULT_MESSAGE);
                    parseDataIfLink(input);
                }
                else if (result.equals(APIService.RESULT_NO_INTERNET)) // check if return data is stated: No internet
                {
                    showToast("No internet!");
                    String input = intent.getStringExtra(APIService.EXTRA_RESULT_MESSAGE);
                    parseDataIfLink(input);
                }
            }
        }
    };

}
