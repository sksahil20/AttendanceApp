package com.example.attendance;

public class ClassItem {

    private String department;
    private String subject;

    public ClassItem( long cid,String department, String subject) {
        this.cid = cid;
        this.department = department;
        this.subject = subject;
    }

     long cid;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }
}
