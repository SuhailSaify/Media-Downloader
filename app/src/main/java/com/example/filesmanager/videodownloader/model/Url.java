
package com.example.filesmanager.videodownloader.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Url {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("size")
    @Expose
    private String size;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }



}
