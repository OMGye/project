package com.util;

import com.common.Const;
import com.pojo.User;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by upupgogogo on 2018/8/29.下午12:06
 */
public class UserListener implements HttpSessionListener {


    public static final List<User> CURUSERLIST = new LinkedList();

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        User cur = (User)session.getAttribute(Const.CURRENT_USER);
        CURUSERLIST.remove(cur);
    }
}
