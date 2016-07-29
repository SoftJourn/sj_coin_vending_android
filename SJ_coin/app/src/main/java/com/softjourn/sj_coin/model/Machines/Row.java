package com.softjourn.sj_coin.model.Machines;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Created by Ad1 on 29.07.2016.
 */
@Data
public class Row {
    @SerializedName("id")
    private Integer id;

    @SerializedName("rowId")
    private String rowId;

    @SerializedName("fields")
    private List<Field> fields = new ArrayList<Field>();
}
