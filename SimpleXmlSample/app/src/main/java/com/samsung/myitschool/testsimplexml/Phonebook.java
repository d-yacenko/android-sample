package com.samsung.myitschool.testsimplexml;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by d.yacenko on 11.02.16.
 */
@Root
public class Phonebook {
    @ElementList
    List<Contact> contacts;

    public Phonebook(){
        contacts=new ArrayList<Contact>();
    }

    public String toString(){
        String str="";
        for (Contact contact:contacts)
            str+=contact+"\n";
        return str;
    }
}
