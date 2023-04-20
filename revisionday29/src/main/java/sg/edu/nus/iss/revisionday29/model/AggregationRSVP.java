package sg.edu.nus.iss.revisionday29.model;

import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class AggregationRSVP {
    private String _id;
    private List<String> names;
    private int count;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static AggregationRSVP createFromDocument(Document d) {
        AggregationRSVP a = new AggregationRSVP();
        a.set_id(d.getString("_id"));
        a.setNames(d.getList("names", String.class));
        a.setCount(d.getInteger("count"));
        return a;
    }

    public JsonObjectBuilder toJSON() {
        JsonArrayBuilder jsArr = Json.createArrayBuilder();
        for (String string : names) {
            jsArr.add(string);
        }
        return Json.createObjectBuilder()
                .add("_id", this._id)
                .add("names", jsArr)
                .add("count", this.count);
    }

}
