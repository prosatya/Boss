package com.matictechnology.leadersrating.util;

import com.matictechnology.leadersrating.classes.User;

import java.util.ArrayList;

/**
 * Created by matic on 8/6/16.
 */
public interface FetchDataListener
{
    void onFetchAllUser(ArrayList<User> user);
}
