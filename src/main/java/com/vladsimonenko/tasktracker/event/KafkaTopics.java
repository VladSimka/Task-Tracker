package com.vladsimonenko.tasktracker.event;

public enum KafkaTopics {
    USER_CREATED_EVENTS_TOPIC;


    public String getName() {
        return formatEnumName(toString());
    }

    private String formatEnumName(String enumName) {
        return enumName.replace("_", "-").toLowerCase();
    }
}

