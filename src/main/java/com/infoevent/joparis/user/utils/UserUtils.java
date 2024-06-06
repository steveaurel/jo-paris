package com.infoevent.joparis.user.utils;

import com.infoevent.joparis.notification.entities.NotificationRequest;
import com.infoevent.joparis.user.entities.User;

public class UserUtils {
    public static NotificationRequest buildNotificationRequest(User user){
        return NotificationRequest.builder().user(user).build();
    }
}
