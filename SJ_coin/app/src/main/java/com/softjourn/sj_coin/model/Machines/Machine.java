package com.softjourn.sj_coin.model.Machines;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Ad1 on 29.07.2016.
 */
@Data
public class Machine {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("rows")
    private List<Row> rows = new ArrayList<Row>();
}
