package com.microservice.user.UserService.util;


public enum RoomType {

    AC,
    NON_AC_ROOM,
    PRIVATE_POOL_ROOM;


    public static RoomType permissiveValueOf(String name) {
        for (RoomType roomType : values()) {
            if (roomType.toString().equals(name)) {
                return roomType;
            }
        }
        return null;
    }
}
