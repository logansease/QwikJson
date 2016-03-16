package com.q.QwikJson;

import android.app.ProgressDialog;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.q.ApiDateHelpers;
import com.q.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by lsease on 4/28/15.
 *
 * This is a base api data object that all tables or models should extend if you wish to
 * pass them to the API. It will handle serializing and deserializing to json
 */
public abstract class QwikJson implements Serializable {


    /********** HELPER METHODS *************/

    @Override
    public String toString() {
        return this.getClass().getName();
    }


    /****************************************************************
     *
     * JSON SERIALIZATION METHODS
     *
     *******************************************************************/

    /******** API DATE CLASSES DEFINED FOR SERIALIZATION TO AND FROM API ****/

    /**
     * This class is used to represent datetime objects coming from the api
     * and handles all the parsing to and from json
     */
    public final static class ApiDateTime extends Date {

        private DateFormat df = new SimpleDateFormat(ApiDateHelpers.API_DATE_TIME_FORMAT);
        public ApiDateTime(String jsonString)
        {
            super();
            try{
                //remove the quotes from around the string
                jsonString = jsonString.replace("\"","");

                //parse the date and set this objects time to match its.
                Date parsedDate = df.parse(jsonString);
                setTime(parsedDate.getTime());
            }
            catch(Exception e)
            {
                Log.e("", e.getMessage());
            }
        }
        public String toApiString()
        {
            return df.format(this);
        }
        public ApiDateTime()
        {
            super();
        }
        public ApiDateTime(Date date)
        {
            super();
            setTime(date.getTime());
        }

        /**
         * Create a datetime from a date + time
         * @param date
         * @param time
         */
        public static ApiDateTime withDateAndTime(ApiDate date, ApiTime time )
        {
            ApiDateTime result = new ApiDateTime(date);
            String formattedString = date.toApiString() + " " + time.toApiString();
            DateFormat combinedFormatter = new SimpleDateFormat(ApiDateHelpers.API_DATE_FORMAT + " " + ApiDateHelpers.API_TIME_FORMAT);
            try{
                Date parsedDate = combinedFormatter.parse(formattedString);
                result.setTime(parsedDate.getTime());
            }
            catch (Exception e) {

            }
            return result;
        }

        public String toString()
        {
            ApiDate date = new ApiDate(this);
            ApiTime time = new ApiTime(this);

            return date.toString() + " at " + time;
        }

    }

    /**
     * This class is used to represent time objects coming from the api
     * and handles all the parsing to and from json
     */
    public final static class ApiTime extends Date{

        private DateFormat df = new SimpleDateFormat(ApiDateHelpers.API_TIME_FORMAT);
        public ApiTime(String jsonString)
        {
            super();
            try{
                //remove the quotes from around the string
                jsonString = jsonString.replace("\"","");

                //parse the date and set this objects time to match its.
                Date parsedDate = df.parse(jsonString);
                setTime(parsedDate.getTime());
            }
            catch(Exception e)
            {
                Log.e("",e.getMessage());
            }
        }
        public String toApiString()
        {
            return df.format(this);
        }
        public ApiTime()
        {
            super();
        }
        public ApiTime(Date date)
        {
            super();
            setTime(date.getTime());
        }
        public ApiTime(long time)
        {
            super();
            setTime(time);
        }

        public String toString()
        {
            DateFormat df = new SimpleDateFormat(DateUtils.DISPLAY_TIME_FORMAT);
            return df.format(this);
        }
    }



    /**
     * This class is used to represent time objects coming from the api
     * and handles all the parsing to and from json
     */
    public final static class ApiTimeStamp extends Date{

        public ApiTimeStamp(String jsonString)
        {
            super();
            try{
                //remove the quotes from around the string
                jsonString = jsonString.replace("\"","");

                //parse the date and set this objects time to match its.
                setTime(Long.parseLong(jsonString));
            }
            catch(Exception e)
            {
                Log.e("",e.getMessage());
            }
        }
        public String toApiString()
        {
            return String.valueOf(getTime());
        }
        public ApiTimeStamp()
        {
            super();
        }
        public ApiTimeStamp(Date date)
        {
            super();
            setTime(date.getTime());
        }
        public ApiTimeStamp(long time)
        {
            super();
            setTime(time);
        }

        public String toString()
        {
            DateFormat df = new SimpleDateFormat(DateUtils.DISPLAY_DATE_FORMAT);
            return df.format(this);
        }
    }


    /**
     * This class is used to represent date objects coming from the api
     * and handles all the parsing to and from json
     */
    public final static class ApiDate extends Date{

        private DateFormat df = new SimpleDateFormat(ApiDateHelpers.API_DATE_FORMAT);
        public ApiDate(String jsonString)
        {
            super();
            try{
                //remove the quotes from around the string
                jsonString = jsonString.replace("\"","");

                //parse the date and set this objects time to match its.
                Date parsedDate = df.parse(jsonString);
                setTime(parsedDate.getTime());
            }
            catch(Exception e)
            {
                Log.e("",e.getMessage());
            }
        }
        public String toApiString()
        {
            return df.format(this);
        }
        public ApiDate()
        {
            super();
        }
        public ApiDate(Date date)
        {
            super();
            setTime(date.getTime());
        }
        public ApiDate(long time)
        {
            super();
            setTime(time);
        }

        public String toString()
        {
            DateFormat df = new SimpleDateFormat(DateUtils.DISPLAY_DATE_FORMAT);
            return df.format(this);
        }
    }

    /**** SERIALIZATION HELPERS ******/

    /**
     * This serializer allows string json types to be set into number fields. This is needed for any
     * numeric fields that the api might return to us as strings.
     */
    private static JsonDeserializer<Number> numberDeserializer = new JsonDeserializer<Number>() {
        @Override
        public Number deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {

            if(json == null) {
                return null;
            }
            else {
                return json.getAsNumber();
            }
        }
    };

    /**
     * The following will serialize booleans to 0 / 1
     */
    private static JsonSerializer<Boolean> booleanSerializer = new JsonSerializer<Boolean>() {
        @Override
        public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext
                context) {
            if(src == null)
            {
                return null;
            }
            if(src == true)
            {
                return new JsonPrimitive(1);
            }
            else if (src == false)
            {
                return new JsonPrimitive(0);
            }
            else
            {
                return null;
            }
        }
    };


    private static JsonSerializer<ApiDateTime> dateTimeSerializer = new JsonSerializer<ApiDateTime>() {
        @Override
        public JsonElement serialize(ApiDateTime src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.toApiString());
        }
    };

    private static JsonDeserializer<ApiDateTime> dateTimeDeserializer = new JsonDeserializer<ApiDateTime>() {
        @Override
        public ApiDateTime deserialize(JsonElement json, Type typeOfT,
                                       JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new ApiDateTime(json.toString());
        }
    };
    private static JsonSerializer<ApiTime> timeSerializer = new JsonSerializer<ApiTime>() {
        @Override
        public JsonElement serialize(ApiTime src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.toApiString());
        }
    };

    private static JsonDeserializer<ApiTime> timeDeserializer = new JsonDeserializer<ApiTime>() {
        @Override
        public ApiTime deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new ApiTime(json.toString());
        }
    };
    private static JsonSerializer<ApiTimeStamp> timeStampSerializer = new JsonSerializer<ApiTimeStamp>() {
        @Override
        public JsonElement serialize(ApiTimeStamp src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.toApiString());
        }
    };

    private static JsonDeserializer<ApiTimeStamp> timeStampDeserializer = new JsonDeserializer<ApiTimeStamp>() {
        @Override
        public ApiTimeStamp deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new ApiTimeStamp(json.toString());
        }
    };

    private static JsonSerializer<ApiDate> dateSerializer = new JsonSerializer<ApiDate>() {
        @Override
        public JsonElement serialize(ApiDate src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.toApiString());
        }
    };

    private static JsonDeserializer<ApiDate> dateDeserializer = new JsonDeserializer<ApiDate>() {
        @Override
        public ApiDate deserialize(JsonElement json, Type typeOfT,
                                   JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new ApiDate(json.toString());
        }
    };

    //build our json parser object and set our custom type adapters, defined above
    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(ApiDateTime.class, dateTimeDeserializer)
            .registerTypeAdapter(ApiDateTime.class, dateTimeSerializer)
            .registerTypeAdapter(ApiTime.class, timeDeserializer)
            .registerTypeAdapter(ApiTime.class, timeSerializer)
            .registerTypeAdapter(ApiDate.class, dateDeserializer)
            .registerTypeAdapter(ApiDate.class, dateSerializer)
            .registerTypeAdapter(Number.class, numberDeserializer)
            .registerTypeAdapter(Boolean.class,booleanSerializer)
            .registerTypeAdapter(ApiTimeStamp.class, timeStampSerializer)
            .registerTypeAdapter(ApiTimeStamp.class, timeStampDeserializer)
            .create();

    /**
     * Convert this object to a JSON Object so it can be passed to the API
     * @return null if could not convert
     */
    public JSONObject toJSONObject()
    {
        try {
            String wrapperString = gson.toJson(this);
            JSONObject result = new JSONObject(wrapperString);
            return  result;
        }
        catch(JSONException e)
        {
            return null;
        }
    }

    /**
     *
     * @param json api result that represents this data object
     * @param resultClass The class that this object is being converted to
     * @param <T> The class that this is being converted to will be the return type
     * @return an object of the desired class
     */
    public static<T extends QwikJson> T fromJSON(JSONObject json, Class resultClass)
    {
        return (T)gson.fromJson(json.toString(),resultClass);
    }

    /**
     * Convert an array into an array list of data objects
     * @param jsonArray the json array response
     * @param resultClass the class to convert to
     * @param <T> the class to convert to
     * @return an arrayList of the disired objects. Those that could not convert will not return
     */
    public static<T extends QwikJson> ArrayList<T> fromJSONArray(JSONArray jsonArray, Class resultClass)
    {
        // //since this conversion takes an unfortunate long amount of time, show the loading dialog
        // ProgressDialog loading = new ProgressDialog(MainApplication.getApplication().getCurrentActivity());
        // loading.show();

        ArrayList result = new ArrayList<T>();
        for(int i = 0 ; i < jsonArray.length(); i++)
        {
            //parse the json object into an object of the desired type and add it to the array
            //if successful
            try
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                BaseApiDataObject object = T.fromJSON(jsonObject,resultClass);
                if(jsonObject != null)
                {
                    result.add(object);
                }
            }
            catch(JSONException e)
            {}
        }

        return result;
    }

    /**
     * convert an array of objects into a jsonArray to pass into the api
     * @param array list of objects to pass in
     * @param resultClass the class to convert to
     * @param <T> the class to convert to
     * @return an array list of the objects
     */
    public static<T extends QwikJson> JSONArray toJSONArray(ArrayList<T> array, Class resultClass)
    {
        JSONArray result = new JSONArray();

        for(T object : array)
        {
            JSONObject jsonObject = object.toJSONObject();
            result.put(jsonObject);
        }

        return result;
    }
}