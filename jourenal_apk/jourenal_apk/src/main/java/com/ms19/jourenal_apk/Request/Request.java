package com.ms19.jourenal_apk.Request;

public class Request {
    private String id;  // Use `id` instead of `_id`
    private String title;

    // Getters and Setters
    public String getId() {  // Follow JavaBeans naming
        return id;
    }

    public void setId(String id) {  // Follow JavaBeans naming
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
