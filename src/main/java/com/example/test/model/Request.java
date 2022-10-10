package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.Date;

@Entity
@Table(name = "requests")
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contact;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @ManyToOne
    @JoinColumn(name = "house_id")
    private House house;
    private Date created;
    private Date updated;

    private String flat;

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public enum RequestType{
        repair,
        complaint,
        meter,
        other
    }

    @Enumerated(EnumType.STRING)
    private RequestType type;

    private String text;

    private Boolean isNewRequest=true;

    private Boolean isDoneRequest=false;

    public String getAddress() {
        return house.getAddress();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getNewRequest() {
        return isNewRequest;
    }

    public void setNewRequest(Boolean newRequest) {
        isNewRequest = newRequest;
    }

    public Boolean getDoneRequest() {
        return isDoneRequest;
    }

    public void setDoneRequest(Boolean doneRequest) {
        isDoneRequest = doneRequest;
    }



    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }




    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }


    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    public Request() {
    }
}
