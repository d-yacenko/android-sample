package com.samsung.myitschool.testsimplexml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.Date;

/**
 * Created by d.yacenko on 11.02.16.
 */
@Root
public class Contact {
    @Element
    String name;
    @Element(required = false)
    Date birthday;
    @Attribute
    long number;

    public Contact(@Element(name = "name") String name,@Element(name = "birthday") Date birthday,@Attribute(name = "number") long number){
        this.name=name;
        this.birthday=birthday;
        this.number=number;
    }

    public String toString(){
        return name+"  тел:"+number+"  дата рожд:"+birthday.toLocaleString();
    }

}
