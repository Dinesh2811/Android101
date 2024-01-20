package com.dinesh.android.rv.java.nested.v0;

import java.util.List;

public class ParentModelClass {

    String title;
    List<ChildModelClass> childModelClassList;

    public ParentModelClass(String title, List<ChildModelClass> childModelClassList) {
        this.title = title;
        this.childModelClassList = childModelClassList;
    }

    public ParentModelClass() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChildModelClass> getChildModelClassList() {
        return childModelClassList;
    }

    public void setChildModelClassList(List<ChildModelClass> childModelClassList) {
        this.childModelClassList = childModelClassList;
    }

    @Override
    public String toString() {
        return "ParentModelClass{" +
                "title='" + title + '\'' +
                ", childModelClassList=" + childModelClassList +
                '}';
    }
}
