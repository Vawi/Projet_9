package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Test;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

    @Test
    public void insertEcritureComptable() throws NotFoundException {
        EcritureComptable ecriture  = new EcritureComptable();
        Date currentDate = new Date();
        int currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        ecriture.setJournal(new JournalComptable("OD", "TestInsert"));
        ecriture.setReference("TT-" + currentYear + "/00200");
        ecriture.setDate(currentDate);
        ecriture.setLibelle("LibTest");

        ecriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(111111),
                "Test", new BigDecimal(200),new BigDecimal(200)));

        dao.insertEcritureComptable(ecriture);
        EcritureComptable ecritureBis = dao.getEcritureComptableByRef("TT-" + currentYear + "/00200");

        Assert.assertTrue(ecriture.getReference().equals(ecritureBis.getReference()));
        Assert.assertTrue(ecriture.getLibelle().equals(ecritureBis.getLibelle()));

        dao.deleteEcritureComptable(ecritureBis.getId());
    }

}
