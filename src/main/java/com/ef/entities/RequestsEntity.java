package com.ef.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * JPA Entity that reference to the banned DB table.
 */
@Entity(name = "requests")
public class RequestsEntity {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "request")
    private String request;

    @Column(name = "request_status")
    private String request_status;

    @Column(name = "request_agent")
    private String request_agent;

    @Column(name = "request_date")
    private Date request_date;

    /*
     * GETTERS AND SETTERS
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getRequest_agent() {
        return request_agent;
    }

    public void setRequest_agent(String request_agent) {
        this.request_agent = request_agent;
    }

    public Date getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Date request_date) {
        this.request_date = request_date;
    }

    /*
     * FLUENT API
     */

    public RequestsEntity withId(final Long id) {
        this.id = id;
        return this;
    }

    public RequestsEntity ip(final String ip) {
        this.ip = ip;
        return this;
    }

    public RequestsEntity request(final String request) {
        this.request = request;
        return this;
    }

    public RequestsEntity request_status(final String request_status) {
        this.request_status = request_status;
        return this;
    }

    public RequestsEntity request_agent(final String request_agent) {
        this.request_agent = request_agent;
        return this;
    }

    public RequestsEntity request_date(final Date request_date) {
        this.request_date = request_date;
        return this;
    }
}

