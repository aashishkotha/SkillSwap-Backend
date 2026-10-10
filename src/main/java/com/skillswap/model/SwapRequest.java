package com.skillswap.model;

import jakarta.persistence.*;

@Entity
@Table(name = "swap_requests")
public class SwapRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(optional = false)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(nullable = false)
    private String skillOffered;

    @Column(nullable = false)
    private String skillRequested;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    private String contactMedium;

    private String contactDetails;

    @Column(length = 1000)
    private String message;

    @Column(length = 1000)
    private String receiverNote;

    public enum Status {
        PENDING,
        ACCEPTED,
        REJECTED,
        COMPLETED
    }

    public SwapRequest() {
    }

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getSkillOffered() {
        return skillOffered;
    }

    public String getSkillRequested() {
        return skillRequested;
    }

    public Status getStatus() {
        return status;
    }

    public String getContactMedium() {
        return contactMedium;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public String getMessage() {
        return message;
    }

    public String getReceiverNote() {
        return receiverNote;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public void setSkillOffered(String skillOffered) {
        this.skillOffered = skillOffered;
    }

    public void setSkillRequested(String skillRequested) {
        this.skillRequested = skillRequested;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setContactMedium(String contactMedium) {
        this.contactMedium = contactMedium;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReceiverNote(String receiverNote) {
        this.receiverNote = receiverNote;
    }
}