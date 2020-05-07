package com.example.ldemo.service;

public abstract class Dialog {


    public void renderWindow() {
        // ... other code ...

        Insure okButton = createButton();
        okButton.quote();
    }
    public abstract Insure createButton();

}
