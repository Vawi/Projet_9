package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.junit.Assert;
import org.junit.Test;


import java.util.List;

public class ComptabiliteDaoImplTest {


    @Test
    public void getListJournalComptable() {

        List<JournalComptable> vList = ComptabiliteDaoImpl.getInstance().getListJournalComptable();

        System.out.println(vList);

        Assert.assertTrue(vList.size() >= 1);

    }

}
