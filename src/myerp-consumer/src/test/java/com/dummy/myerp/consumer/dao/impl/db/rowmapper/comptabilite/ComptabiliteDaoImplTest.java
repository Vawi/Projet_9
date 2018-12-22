package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;


import java.util.List;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();

    @Test
    public void getListJournalComptable() {
        List<JournalComptable> vList = dao.getListJournalComptable();
        Assert.assertTrue(vList.size() >= 1);
    }

    @Test
    public void getListCompteComptable() {
        List<CompteComptable> vList = dao.getListCompteComptable();
        Assert.assertTrue(vList.size() >= 1);
    }

    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> vList = dao.getListEcritureComptable();
        System.out.println(vList);
        Assert.assertTrue(vList.size() >= 1);
    }

    @Test
    public void getEcritureComptable() throws NotFoundException { // Id négatif a corriger ?
        EcritureComptable ec = dao.getEcritureComptable(-2);
        Assert.assertNotNull(ec);
    }

    @Test
    public void getEcritureComptableByRef() throws NotFoundException {
        EcritureComptable ec = dao.getEcritureComptableByRef("AC-2016/00001");
        Assert.assertNotNull(ec);
    }

}
