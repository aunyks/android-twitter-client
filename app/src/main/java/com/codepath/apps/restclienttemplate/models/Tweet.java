package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    //list out the attributes
    public String body;
    public long uid; //database ID for the tweet
    public User user;
    public String createdAt;
    public Entity entity;
    public boolean hasEntities;

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract the values from JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
       // tweet.entity = Entity.fromJSON()

        JSONObject entityObject = jsonObject.getJSONObject("entities");
        if (entityObject.has("media")) {
            JSONArray mediaEndpoint = entityObject.getJSONArray("media");
            if (mediaEndpoint!=null && mediaEndpoint.length()!=0) {
                tweet.entity=Entity.fromJSON(jsonObject.getJSONObject("entities"));
                tweet.hasEntities=true;
            } else {
                tweet.hasEntities=false;
            }
        }
        return tweet;
    }
}
