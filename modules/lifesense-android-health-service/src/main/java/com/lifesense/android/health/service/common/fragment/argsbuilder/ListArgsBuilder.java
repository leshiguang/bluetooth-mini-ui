package com.lifesense.android.health.service.common.fragment.argsbuilder;

import android.os.Bundle;

import com.hannesdorfmann.fragmentargs.bundler.ArgsBundler;

import java.util.ArrayList;

/**
 * Create by qwerty
 * Create on 2020/12/30
 **/
public class ListArgsBuilder implements ArgsBundler<ArrayList> {
    @Override
    public void put(String key, ArrayList value, Bundle bundle) {
        bundle.putParcelableArrayList(key,value);
    }

    @Override
    public <V extends ArrayList> V get(String key, Bundle bundle) {
        return (V)bundle.getParcelableArrayList(key);
    }
}
