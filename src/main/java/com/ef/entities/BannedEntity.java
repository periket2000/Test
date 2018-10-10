package com.ef.entities;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "banned")
public class BannedEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "run")
    private String run;

    @Column(name = "banned_ip")
    private String banned_ip;

    @Column(name = "requests")
    private int requests;

    @Column(name = "comment")
    private String comment;

    @Column(name = "start_date")
    private Date start_date;

    @Column(name = "end_date")
    private Date end_date;

    /*
     * GETTERS AND SETTERS
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getBanned_ip() {
        return banned_ip;
    }

    public void setBanned_ip(String banned_ip) {
        this.banned_ip = banned_ip;
    }

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /*
     * FLUENT API
     */

    public BannedEntity withId(final Long id) {
        this.id = id;
        return this;
    }

    public BannedEntity run(final String run) {
        this.run = run;
        return this;
    }

    public BannedEntity banned_ip(final String banned_ip) {
        this.banned_ip = banned_ip;
        return this;
    }

    public BannedEntity requests(final int requests) {
        this.requests = requests;
        return this;
    }

    public BannedEntity comment(final String comment) {
        this.comment = comment;
        return this;
    }

    public BannedEntity start_date(final Date start_date) {
        this.start_date = start_date;
        return this;
    }

    public BannedEntity end_date(final Date end_date) {
        this.end_date = end_date;
        return this;
    }
}

