package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testconsumer.consumer.ConsumerTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ComptabiliteDaoImplTest extends ConsumerTestCase {

    private ComptabiliteDao dao = getDaoProxy().getComptabiliteDao();
    private EcritureComptable vEcritureComptable = new EcritureComptable();
    private int currentYear = 0;

    @Before
    public void initEcritureComptable() {
        Date currentDate = new Date();
        currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        vEcritureComptable.setJournal(new JournalComptable("OD", "TestInsert"));
        vEcritureComptable.setReference("TT-2019/00055");
        vEcritureComptable.setDate(currentDate);
        vEcritureComptable.setLibelle("Test");

        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                "Test1", new BigDecimal(200),new BigDecimal(100)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                "Test2", new BigDecimal(150),new BigDecimal(100)));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(512),
                "Test3", new BigDecimal(100),new BigDecimal(250)));
    }

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
        Assert.assertTrue(vList.size() >= 1);
    }

    @Test
    public void getEcritureComptable() throws NotFoundException {
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

        dao.insertEcritureComptable(vEcritureComptable);

        EcritureComptable ecritureBis = dao.getEcritureComptableByRef("TT-" + currentYear + "/00055");

        dao.deleteEcritureComptable(ecritureBis.getId());
        Assert.assertEquals(vEcritureComptable.getReference(), ecritureBis.getReference());
        Assert.assertEquals(vEcritureComptable.getLibelle(), ecritureBis.getLibelle());
    }

    @Test
    public void insertSequenceEcritureComptable() throws NotFoundException {
        SequenceEcritureComptable seq = new SequenceEcritureComptable();
        seq.setDerniereValeur(42);
        seq.setAnnee(2019);
        dao.insertSequenceEcritureComptable(seq, "AC");

        SequenceEcritureComptable seqBis = dao.getSequenceEcritureComptable("AC", 2019);

        Assert.assertEquals(42, (int) seqBis.getDerniereValeur());

        dao.deleteSequenceEcritureComptable(seqBis, "AC");
    }

    @Test
    public void updateSequenceEcritureComptable() throws NotFoundException {

        SequenceEcritureComptable seq = dao.getSequenceEcritureComptable("AC", 2016);
        seq.setDerniereValeur(22456);
        dao.updateSequenceEcritureComptable(seq, "AC");
        seq = dao.getSequenceEcritureComptable("AC", 2016);
        Assert.assertEquals(22456, (int) seq.getDerniereValeur());
    }

    @Test
    public void updateEcritureComptableTest() throws NotFoundException{

        vEcritureComptable.setReference("UT-2019/00444");
        dao.insertEcritureComptable(vEcritureComptable);
        EcritureComptable ecriture = dao.getEcritureComptableByRef("UT-2019/00444");
        ecriture.setLibelle("update");
        dao.updateEcritureComptable(ecriture);
        Assert.assertEquals("update", ecriture.getLibelle());
        dao.deleteEcritureComptable(ecriture.getId());
    }

    @Test
    public void deleteEcritureComptableTest() throws NotFoundException {

        Date currentDate = new Date();
        currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        vEcritureComptable.setJournal(new JournalComptable("OD", "TestDel"));
        vEcritureComptable.setReference("OD-2019/00011");
        vEcritureComptable.setDate(currentDate);
        vEcritureComptable.setLibelle("TestDel");

        dao.insertEcritureComptable(vEcritureComptable);

        EcritureComptable bis = dao.getEcritureComptableByRef("OD-2019/00011");
        Assert.assertNotNull(bis);
        dao.deleteEcritureComptable(bis.getId());
    }

}
