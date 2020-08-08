package com.jiho.chat;
/*디자인 패턴 중의 하나. 이렇게 코드를 만들면 이런식으로 디자인을 만들 수 있다(?)의 의미..?*/
/*원래 클래스로는 여러가지 객체를 생성할 수 있지만 싱글톤은 하나의 객체만 생성할 수 있게 함. 고유의 이메일 값을 유지할 수 있도록...*/

public class MyemailSingleton {

    private static final MyemailSingleton instance = new MyemailSingleton();

    private MyemailSingleton(){ // 객체를 private로 만듦. 다른 곳에서 생성자를 호출 불가.

    }

    private String myEmail;

    public String getMyEmail() { // 이메일 받아갈 수 있는 메서드
        return myEmail;
    }

    public void setMyEmail(String myEmail) { // 이메일 저장해 놓을 수 있는 메서드
        this.myEmail = myEmail;
    }

    public static MyemailSingleton getInstance(){ // 객체를 받아가려면 getInstance메서드를 이용하여 받아가야함
        return instance; // 생성자 위에 생성된 instance 객체 즉 final로 선언된 자기 자신 객체만 객체로 사용할 수 있도록 설정
    }
}
