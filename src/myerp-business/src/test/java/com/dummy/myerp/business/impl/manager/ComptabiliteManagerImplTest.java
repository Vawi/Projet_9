package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.dummy.myerp.technical.exception.NotFoundException;
import com.dummy.myerp.testbusiness.business.BusinessTestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import static com.dummy.myerp.consumer.ConsumerHelper.getDaoProxy;


public class ComptabiliteManagerImplTest extends BusinessTestCase {

    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    private EcritureComptable vEcritureComptable = new EcritureComptable();

    @Before
    public void setEcriture() {
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));
    }

    @Test
    public void checkEcritureComptableUnit() throws Exception {

        this.setEcriture();

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitViolation() throws Exception {

        this.setEcriture();

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG2() throws Exception {

        this.setEcriture();

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3() throws Exception {

        this.setEcriture();

        manager.checkEcritureComptableUnit(vEcritureComptable);
    }

    @Test
    public void getListCompteComptable() {
        List<CompteComptable> pList = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        Assert.assertNotNull(pList);
    }

    @Test
    public void getListJournalComptable() {
        List<CompteComptable> pList = getDaoProxy().getComptabiliteDao().getListCompteComptable();
        Assert.assertTrue(pList.size() >= 1);
    }

    @Test
    public void getListEcritureComptable() {
        List<EcritureComptable> pList = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        Assert.assertTrue(pList.size()>1);
    }

    @Test
    public void addReference() throws NotFoundException {
        manager.addReference(vEcritureComptable);
        System.out.println(vEcritureComptable.getReference());
        Assert.assertNotNull(vEcritureComptable.getReference());
    }

    @Test
    public void checkEcritureComptable() {
    }

    @Test
    public void checkEcritureComptableContext() {
    }

    @Test
    public void insertEcritureComptable() {
    }

    @Test
    public void updateEcritureComptable() {
    }

    @Test
    public void deleteEcritureComptable() {
    }
}
