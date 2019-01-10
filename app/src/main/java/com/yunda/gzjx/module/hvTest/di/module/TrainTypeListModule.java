package com.yunda.gzjx.module.hvTest.di.module;

import com.yunda.gzjx.module.hvTest.mvp.contract.TrainTypeListContract;
import com.yunda.gzjx.module.hvTest.mvp.model.TrainTypeListModel;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class TrainTypeListModule {

    @Binds
    abstract TrainTypeListContract.Model bindsRecanditionModel(TrainTypeListModel model);
}