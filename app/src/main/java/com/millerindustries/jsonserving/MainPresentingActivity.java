package com.millerindustries.jsonserving;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPresentingActivity extends AppCompatActivity {
    private ListView list;
    private ArrayAdapter<String> adapter;
    private List<String> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_presenting);

        foodList = new ArrayList<>();

        list = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, R.layout.row, foodList);

        try {
            JSONArray jsonArray = new JSONArray(getIntent().getSerializableExtra("Json").toString());
            Map<String, String> foodMap = new HashMap<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String foodName = item.getString("name");

                JSONArray ingredients = item.getJSONArray("ingredients");
                List<String> ingredientsList = getIngredientsList(ingredients);

                JSONArray steps = item.getJSONArray("steps");
                List<String> stepList = getStepsList(steps);

                foodList.add(foodName);
                foodList.add(" ");
                foodList.addAll(ingredientsList);
                foodList.add(" ");
                foodList.addAll(stepList);
                foodList.add("\n\n\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.addAll(foodList);
        list.setAdapter(adapter);
    }

    public List<String> getIngredientsList(JSONArray ingredients) {
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Ingredients: ");
        for (int i = 0; i < ingredients.length(); i++) {
            try {
                JSONObject singleIngredient = ingredients.getJSONObject(i);
                String ingredientName = singleIngredient.getString("name");
                String ingredientQuantity = singleIngredient.getString("quantity");

                ingredientsList.add(i+1 + ". " + ingredientName + ", quantity: " +ingredientQuantity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return ingredientsList;
    }

    public List<String> getStepsList(JSONArray steps) {
        List<String> stepList = new ArrayList<>();
        stepList.add("Steps:");
        for(int i =0; i<steps.length(); i++){
            try {
                stepList.add(i+1+". " + steps.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return stepList;
    }
}
