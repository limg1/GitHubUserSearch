package com.limg1.githubsrchuser.data.remote.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Limg1 on 2017-08-04.
 */

public class GitUsersList {
    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("items")
    @Expose
    private List<GitUser> items = new ArrayList<GitUser>();

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<GitUser> getItems() {
        return items;
    }

    public void setItems(List<GitUser> items) {
        this.items = items;
    }
}
