package com.hnb.hello.service;

import android.app.IntentService;
import android.content.Intent;

import com.hnb.hello.daocontroller.LinkController;
import com.hnb.hello.utils.Detector;
import com.hnb.hello.utils.StaticFunction;
import com.hnb.hello.utils.TitleExtractor;

import java.util.ArrayList;

import greendao.ObjectLink;


// API service: for long running task in background

public class APIService extends IntentService
{

    // action
    public static final String ACTION_GET_TITLE = "ACTION_GET_TITLE";
    // receiver
    public static final String RECEIVER_GET_TITLE = "RECEIVER_GET_TITLE";
    // extra code and result value
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    public static final String RESULT_OK = "RESULT_OK";
    public static final String RESULT_FAIL = "RESULT_FAIL";
    public static final String RESULT_NO_INTERNET = "RESULT_NO_INTERNET";
    public static final String EXTRA_RESULT_MESSAGE = "EXTRA_RESULT_MESSAGE";
    public static final String EXTRA_INPUT = "EXTRA_INPUT";


    public APIService()
    {
        super(APIService.class.getName());
    }


    // handle action from activity
    @Override
    protected void onHandleIntent(Intent intent)
    {
        String action = intent.getAction();

        // call to get the title of the URLs
        if (action.equals(APIService.ACTION_GET_TITLE))
        {
            String input = intent.getExtras().getString(APIService.EXTRA_INPUT);

            // if internet connection is available then call network if not return right away
            if (StaticFunction.isNetworkAvailable(APIService.this))
            {
                Detector myDetector = new Detector();
                ArrayList<String> listURL = myDetector.detectLinks(input);

                LinkController.deleteAll(APIService.this);
                boolean result = getURLTitle(listURL);

                if (result)
                {
                    sendBroadCastReceiver(APIService.RECEIVER_GET_TITLE, RESULT_OK, input);
                }
                else
                {
                    sendBroadCastReceiver(APIService.RECEIVER_GET_TITLE, RESULT_FAIL, input);
                }
            }
            else // no internet connection return right away
            {
                sendBroadCastReceiver(APIService.RECEIVER_GET_TITLE, RESULT_NO_INTERNET, input);
            }
        }

    }


    // broadcast to activity to say that the task is finished
    private void sendBroadCastReceiver(String action, String result, String message)
    {
        Intent i = new Intent();
        i.setAction(action);
        i.putExtra(APIService.EXTRA_RESULT, result);
        i.putExtra(APIService.EXTRA_RESULT_MESSAGE, message);
        sendBroadcast(i);
    }


    // get URL title
    private boolean getURLTitle(ArrayList<String> listURL)
    {
        for (String strURL : listURL)
        {
            try
            {

                // check from local db to see if this link had been loading before
                // if not, load
                // if it is in db already, save the network by load from local
                ObjectLink objectLinkFromDB = LinkController.getObjectLinkByURL(APIService.this, strURL);

                String title = "";

                if (objectLinkFromDB == null)
                {
                    title = TitleExtractor.getPageTitle(strURL);
                }
                else
                {
                    title = objectLinkFromDB.getTitle();
                }


                ObjectLink objectLink = new ObjectLink();
                objectLink.setUrl(strURL);
                objectLink.setTitle(title);

                LinkController.insert(APIService.this, objectLink);
            }
            catch (Exception ex)
            {
                // some exception happen, we cannot find the url title
                // ignore this url and continue with others
                ObjectLink objectLink = new ObjectLink();
                objectLink.setUrl(strURL);
                objectLink.setTitle("Sorry, We cannot find the tittle!");

                LinkController.insert(APIService.this, objectLink);
            }

        }

        return true;
    }


}
