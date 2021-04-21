package com.rana.abjadity;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class RegisterActivityTest  {

 @Test
    public void Check_password(){

     assertThat(RegisterActivity.isValidPassword("Abeer123456_")).isTrue();

 }

    @Test
    public void Check_Email(){
     assertThat(RegisterActivity.isValidEmailId("Abeerm.am32gmail.com")).isFalse();

    }



}