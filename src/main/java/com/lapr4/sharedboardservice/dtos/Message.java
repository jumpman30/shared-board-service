package com.lapr4.sharedboardservice.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.lapr4.sharedboardservice.enums.Code;

public class Message {

    @JsonProperty("version")
    public int version;

    @JsonProperty("code")
    public Code code;

    @JsonProperty("d_length_1")
    public long dLength1;

    @JsonProperty("d_length_2")
    public long dLength2;

    @JsonProperty("data")
    public String data;

    @JsonProperty("session_Id")
    public String sessionId;

    @JsonProperty("error_message")
    public String errorMessage;

    public Message() {
    }

    public Message(int version, Code code, long dLength1, long dLength2, String data) {
        this.version = version;
        this.code = code;
        this.dLength1 = dLength1;
        this.dLength2 = dLength2;
        this.data = data;
    }


    public Message(int version, Code code, long dLength1, long dLength2, String sessionId, String data) {
        this.version = version;
        this.code = code;
        this.dLength1 = dLength1;
        this.dLength2 = dLength2;
        this.sessionId = sessionId;
        this.data = data;
    }

    public Message(Code code) {
        this.code = code;
    }

    public Message(Code code, String sessionId) {
        this.code = code;
        this.sessionId = sessionId;
    }


    public Message(String data, Code code) {
        this.data = data;
        this.code = code;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}


