package com.example.ldemo.service.impl;

import com.example.ldemo.service.Dialog;
import com.example.ldemo.service.Insure;

public class PublicDialog extends Dialog {
    @Override
    public Insure createButton() {
        return new PublicDuty();
    }
}
