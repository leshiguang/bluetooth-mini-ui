package com.lifesense.android.health.service.common.fragment.argsbuilder;

import android.os.Bundle;

import com.hannesdorfmann.fragmentargs.bundler.ArgsBundler;

import java.io.Serializable;

public class SerializableArgsBuilder implements ArgsBundler<Serializable> {
    @Override
    public void put(String key, Serializable value, Bundle bundle) {
        bundle.putSerializable(key,value);
    }

    @Override
    public <V extends Serializable> V get(String key, Bundle bundle) {
        return (V)bundle.getSerializable(key);
    }
}
