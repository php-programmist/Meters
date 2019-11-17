package ru.phpprogrammist.counters.pojo;

import android.content.SharedPreferences;

import ru.phpprogrammist.counters.helpers.MathHelper;

public class Preferences {
    private Double cost_units;
    private Boolean preferential_rate;
    private Double preferential_units;
    private Double preferential_cost_units;
    private String currency;

    public Preferences(SharedPreferences sharedPreferences, String typeSuffix) {
        this.cost_units = MathHelper.toDouble(sharedPreferences.getString("cost_units_"+typeSuffix,"0"));
        this.preferential_rate = sharedPreferences.getBoolean("preferential_rate_"+typeSuffix,false);
        this.preferential_units = MathHelper.toDouble(sharedPreferences.getString("preferential_units_"+typeSuffix,"0")) ;
        this.preferential_cost_units = MathHelper.toDouble(sharedPreferences.getString("preferential_cost_units_"+typeSuffix,"0"));
        this.currency = sharedPreferences.getString("currency","Руб");
    }

    public Double getCost_units() {
        return cost_units;
    }

    public void setCost_units(Double cost_units) {
        this.cost_units = cost_units;
    }

    public Boolean getPreferential_rate() {
        return preferential_rate;
    }

    public void setPreferential_rate(Boolean preferential_rate) {
        this.preferential_rate = preferential_rate;
    }

    public Double getPreferential_units() {
        return preferential_units;
    }

    public void setPreferential_units(Double preferential_units) {
        this.preferential_units = preferential_units;
    }

    public Double getPreferential_cost_units() {
        return preferential_cost_units;
    }

    public void setPreferential_cost_units(Double preferential_cost_units) {
        this.preferential_cost_units = preferential_cost_units;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


}
