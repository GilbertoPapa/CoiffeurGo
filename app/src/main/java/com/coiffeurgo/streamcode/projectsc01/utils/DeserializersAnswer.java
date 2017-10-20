package com.coiffeurgo.streamcode.projectsc01.utils;

import com.coiffeurgo.streamcode.projectsc01.models.API.Answer;
import com.coiffeurgo.streamcode.projectsc01.models.Companies;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gilbertopapa on 03/08/2017.
 */

public class DeserializersAnswer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Answer _answer = null;
        if(json.getAsJsonObject().get("status")!= null){
            boolean _status = json.getAsJsonObject().get("status").getAsBoolean();

            List<Companies> _companies = null;
            if(json.getAsJsonObject().get("companies")!= null){
                Type listType = new TypeToken<ArrayList<Companies>>(){}.getType();
                _companies = new Gson().fromJson(json.getAsJsonObject().get("companies"),listType);
            }

            String[] _errors = null;
            if(json.getAsJsonObject().get("errors")!= null){
                _errors = json.getAsJsonObject().get("errors").getAsString().split(",");
            }

            _answer = new Answer(_status,_companies,_errors);
        }
        return _answer;
    }
}
