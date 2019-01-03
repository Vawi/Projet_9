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
        EcritureComptable ecriture  = new EcritureComptable();
        Date currentDate = new Date();
        int currentYear = LocalDateTime.ofInstant(currentDate.toInstant(), ZoneId.systemDefault()).toLocalDate().getYear();
        ecriture.setJournal(new JournalComptable("OD", "Opérations Diverses"));
        ecriture.setReference("AC-" + currentYear + "/00200");
        ecriture.setDate(currentDate);
        ecriture.setLibelle("Sandwichs");

        ecriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(606),"Club saumon", new BigDecimal(10),null));
        ecriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(4456),"TVA 20%", new BigDecimal(2),null));
        ecriture.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),"Facture S110001", null,new BigDecimal(12)));

        dao.insertEcritureComptable(ecriture);
        EcritureComptable ecritureBis = dao.getEcritureComptableByRef("AC-" + currentYear + "/00200");

        ecritureBis.setLibelle("panini");

        dao.updateEcritureComptable(ecritureBis);

        Assert.assertEquals("panini", dao.getEcritureComptableByRef("AC-" + currentYear + "/00200").getLibelle());
        dao.deleteEcritureComptable(ecritureBis.getId());
    }

}
