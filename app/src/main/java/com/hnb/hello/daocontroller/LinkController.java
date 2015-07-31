package com.hnb.hello.daocontroller;

import android.content.Context;
import android.util.Log;

import com.hnb.hello.application.MyApplication;

import java.util.List;

import greendao.ObjectLink;
import greendao.ObjectLinkDao;

/**
 * Created by USER on 7/28/2015.
 */
// DAO controller to control the DB: table Link
public class LinkController
{
    private static ObjectLinkDao getLinkDao(Context c)
    {
        return ((MyApplication) c.getApplicationContext()).getDaoSession().getObjectLinkDao();
    }

    public static void insert(Context context, ObjectLink link)
    {
        getLinkDao(context).insert(link);
    }

    public static List<ObjectLink> getAll(Context context)
    {
        return getLinkDao(context).loadAll();
    }

    public static void clearById(Context context, Long id)
    {
        getLinkDao(context).deleteByKey(id);
    }

    public static void deleteAll(Context context)
    {
        try
        {
            getLinkDao(context).deleteAll();
        }
        catch (Exception ex)
        {
            Log.e("", "");
        }

    }

    public static ObjectLink getObjectLinkByURL(Context context, String url)
    {
        List<ObjectLink> listResult = getLinkDao(context).queryRaw(" WHERE URL = ?", url);

        if (listResult.size() > 0)
        {
            return listResult.get(0);
        }
        else
        {
            return null;
        }

    }
}
