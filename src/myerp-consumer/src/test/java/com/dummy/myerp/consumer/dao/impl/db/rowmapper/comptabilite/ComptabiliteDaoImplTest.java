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
    private EcritureComptable vEcritureComptable = new EcritureComptable();
    private int currentYear = 0;

    private void setEcriture() {
        Date currentDate = new Date();
        currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        vEcritureComptable.setJournal(new JournalComptable("OD", "TestInsert"));
        vEcritureComptable.setReference("TT-" + currentYear + "/00055");
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

        this.setEcriture();

        dao.insertEcritureComptable(vEcritureComptable);
        EcritureComptable ecritureBis = dao.getEcritureComptableByRef("TT-" + currentYear + "/00055");

        Assert.assertEquals(vEcritureComptable.getReference(), ecritureBis.getReference());
        Assert.assertEquals(vEcritureComptable.getLibelle(), ecritureBis.getLibelle());
    }

    @Test
    public void insertSequenceEcritureComptable() {

        SequenceEcritureComptable seq = new SequenceEcritureComptable();
        seq.setDerniereValeur(42);
        seq.setAnnee(2019);

        dao.insertSequenceEcritureComptable(seq, "AC");
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

        EcritureComptable ecriture = dao.getEcritureComptableByRef("TT-2019/00055");
        ecriture.setLibelle("update");
        dao.updateEcritureComptable(ecriture);
        Assert.assertEquals("update", dao.getEcritureComptableByRef("TT-2019/00055").getLibelle());
    }

    @Test(expected = NotFoundException.class)
    public void deleteEcritureComptableTest() throws NotFoundException { // Ca marche bien, mais a tester

        this.setEcriture();

        dao.insertEcritureComptable(vEcritureComptable);

        EcritureComptable ec = dao.getEcritureComptable(7);

        Assert.assertNotNull(ec);

        dao.deleteEcritureComptable(7);

    }

}
