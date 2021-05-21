package com.ex.pojos;

import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Date;

public class ReimbursementRequest {
    public ReimbursementRequest() {
    }

    public ReimbursementRequest(Integer amount, Instant timestamp, ObjectId employeeId, ObjectId managerId, String status) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.employeeId = employeeId;
        this.managerId = managerId;
        this.status = status;
    }

    private Integer amount;
    private Instant timestamp;
    private ObjectId employeeId;
    private ObjectId managerId;
    private String status;

    private ObjectId id;

    public static enum STATUS {Pending, Approved, Denied}

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public ObjectId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(ObjectId employeeId) {
        this.employeeId = employeeId;
    }

    public ObjectId getManagerId() {
        return managerId;
    }

    public void setManagerId(ObjectId managerId) {
        this.managerId = managerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReimbursementRequest{" +
                "amount='" + amount + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", managerId=" + managerId + '\'' +
                ", status=" + status + '\'' +
                ", id=" + id +
                '}';
    }
}