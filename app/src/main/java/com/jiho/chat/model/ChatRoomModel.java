package com.jiho.chat.model;

public class ChatRoomModel { // 누구에게 메세지를 보내는지  . . .

    private String toUserEmail;

    public ChatRoomModel(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    } // 누구에게 보내는지 전달받을 매개변수를 가지고 있는 생성자

    public String getToUserEmail() {
        return toUserEmail;
    }

    public void setToUserEmail(String toUserEmail) {
        this.toUserEmail = toUserEmail;
    }

    @Override
    public String toString() {
        return "ChatRoomModel{" +
                "toUserEmail='" + toUserEmail + '\'' +
                '}';
    }
}
