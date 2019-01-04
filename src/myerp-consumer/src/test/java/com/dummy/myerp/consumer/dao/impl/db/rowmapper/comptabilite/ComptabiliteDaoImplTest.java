package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.*;
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
    public void getSequenceEcritureComptable() throws NotFoundException {

        SequenceEcritureComptable seq = dao.getSequenceEcritureComptable("BQ", 2016);
        SequenceEcritureComptable seq2 = dao.getSequenceEcritureComptable("TT", 1325);

        Assert.assertNotNull(seq);
        Assert.assertNull(seq2);
    }

    @Test
    public void loadEcritureComptable(){
        EcritureComptable vEcriture  = new EcritureComptable();
        vEcriture.setId(-1);
        dao.loadListLigneEcriture(vEcriture);
        Assert.assertEquals(3, vEcriture.getListLigneEcriture().size());
    }

    @Test
    public void insertEcritureComptable() throws NotFoundException {

        EcritureComptable pEcriture  = new EcritureComptable();
        Date currentDate = new Date();
        int currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        pEcriture.setJournal(new JournalComptable("OD", "TestInsert"));
        pEcriture.setReference("TT-" + currentYear + "/00055");
        pEcriture.setDate(currentDate);
        pEcriture.setLibelle("Test");

        pEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(123),
                "Test1", new BigDecimal(200),new BigDecimal(100)));
        pEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(234),
                "Test2", new BigDecimal(150),new BigDecimal(100)));
        pEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(345),
                "Test3", new BigDecimal(100),new BigDecimal(250)));

        dao.insertEcritureComptable(pEcriture);
        EcritureComptable ecritureBis = dao.getEcritureComptableByRef("TT-" + currentYear + "/00055");

        Assert.assertEquals(pEcriture.getReference(), ecritureBis.getReference());
        Assert.assertEquals(pEcriture.getLibelle(), ecritureBis.getLibelle());
    }

    @Test
    public void updateEcritureComptableTest() throws NotFoundException{

        EcritureComptable pEcriture  = new EcritureComptable();
        Date currentDate = new Date();
        int currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        pEcriture.setJournal(new JournalComptable("OD", "TestInsert"));
        pEcriture.setReference("TT-" + currentYear + "/00055");
        pEcriture.setDate(currentDate);
        pEcriture.setLibelle("Test");

        pEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(123),
                "Test1", new BigDecimal(200),new BigDecimal(100)));
        pEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(234),
                "Test2", new BigDecimal(150),new BigDecimal(100)));
        pEcriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(345),
                "Test3", new BigDecimal(100),new BigDecimal(250)));

        dao.insertEcritureComptable(pEcriture);
        EcritureComptable ecritureBis = dao.getEcritureComptableByRef("TT-" + currentYear + "/00055");

        ecritureBis.setLibelle("TestUpdate");

        dao.updateEcritureComptable(ecritureBis);

        Assert.assertEquals("TestUpdate", dao.getEcritureComptableByRef("TT-" + currentYear + "/00055").getLibelle());
        dao.deleteEcritureComptable(ecritureBis.getId());
    }

}
