package com.coiffeurgo.streamcode.projectsc01.models.API;

import com.coiffeurgo.streamcode.projectsc01.models.Companies;

import java.util.List;

/**
 * Created by gilbertopapa on 03/08/2017.
 */

public class Answer {
    private boolean status;
    private List<Companies> companies;
    private String[] errors;

    public Answer(){};
    public Answer(boolean status,List<Companies> companies,String[] errors){
        this.status = status;
        this.companies = companies;
        this.errors = errors;
    };

    public boolean isStatus() {
        return status;
    }

    public List<Companies> getCompanies() {
        return companies;
    }

    public String[] getErrors() {
        return errors;
    }
}
