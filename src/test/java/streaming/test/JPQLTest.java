/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.test;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.Assert;
import org.junit.Test;
import streaming.entity.Film;

/**
 *
 * @author Administrateur
 */
public class JPQLTest {

    @Test
    public void req1() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT f FROM Film f WHERE f.id=4");
        Film film = (Film) query.getSingleResult();
        Assert.assertEquals("Fargo", film.getTitre());
    }

    @Test
    public void req2() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f");
        long n = (long) query.getSingleResult();
        Assert.assertEquals(4, n);
    }

    @Test
    public void req3() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT MIN(f.annee) FROM Film f");
        int minA = (int) query.getSingleResult();
        Assert.assertEquals(1968, minA);
    }

    @Test
    public void req4() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(l) "
                + "FROM Lien l "
                + "JOIN l.film f "
                + "WHERE f.titre='Big Lebowski' ");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(0, count);
    }

    @Test
    public void req5() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.realisateurs r WHERE r.nom='Polanski'");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(2, count);
    }

    @Test
    public void req6() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.acteurs a WHERE a.nom='Polanski'");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(1, count);
    }

    @Test
    public void req7() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f"
                + " JOIN f.acteurs a"
                + " JOIN f.realisateurs r "
                + "WHERE a.nom='Polanski' "
                + "AND r.nom='Polanski'");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(1, count);
    }

    @Test
    public void req8() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(""
                + "SELECT f.titre "
                + "FROM Film f "
                + "JOIN f.genre g "
                + "JOIN f.realisateurs r "
                + "JOIN f.pays P "
                + "WHERE g.nom='Horreur' "
                + " AND r.nom='Polanski' "
                + "AND p.nom='UK'");
        String t = (String) query.getSingleResult();
        Assert.assertEquals("Le bal des vampires", t);
    }

    @Test
    public void req9() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.realisateurs r WHERE r.nom='Coen'and r.prenom='Joel'");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(2, count);
    }

    @Test
    public void req10() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(""
                + "SELECT f FROM Film f JOIN f.realisateurs r WHERE r.nom='Coen' AND r.prenom='Ethan'"
                + "INTERSECT "
                + "SELECT f FROM Film f JOIN f.realisateurs r WHERE r.nom='Coen' AND r.prenom='Joel'");
        long count = query.getResultList().size();
        Assert.assertEquals(2, count);
    }

    @Test
    public void req11() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(""
                + "SELECT f FROM Film f JOIN f.realisateurs r WHERE r.nom='Coen' AND r.prenom='Ethan'"
                + "INTERSECT "
                + "SELECT f FROM Film f JOIN f.realisateurs r JOIN f.acteurs a WHERE r.nom='Coen' AND r.prenom='Joel' AND a.prenom='Steve' and a.nom='Buscemi'");
        long count = query.getResultList().size();
        Assert.assertEquals(2, count);
    }

    @Test
    public void req12() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery(""
                + "SELECT f FROM Film f JOIN f.realisateurs r JOIN f.genre g WHERE r.nom='Coen' AND r.prenom='Ethan' AND g.nom='Policier'"
                + "INTERSECT "
                + "SELECT f FROM Film f JOIN f.realisateurs r JOIN f.acteurs a WHERE r.nom='Coen' AND r.prenom='Joel' AND a.prenom='Steve' and a.nom='Buscemi'");
        long count = query.getResultList().size();
        Assert.assertEquals(1, count);
    }

    @Test
    public void req13() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(s) FROM Saison s JOIN s.serie sr WHERE sr.titre='Dexter' ");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(8, count);
    }

    @Test
    public void req14() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(e) FROM Episode e JOIN e.saison sa JOIN sa.serie sr  WHERE sa.numSaison='8' and sr.titre='Dexter' ");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(12, count);
    }

    @Test
    public void req15() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(e) FROM Episode e JOIN e.saison ss JOIN ss.serie sr WHERE sr.titre='Dexter' ");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(96, count);
    }
    @Test
    public void req16() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(l) FROM Lien l JOIN l.film f JOIN f.genre g JOIN f.pays p WHERE p.nom='USA' AND g.nom='Policier'");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(3, count);
    }
    
    @Test
    public void req17() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(l) FROM Lien l JOIN l.film f JOIN f.genre g JOIN f.acteurs a  WHERE a.nom='Polanski' AND g.nom='Horreur'");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(1, count);
    }    
    
    @Test
    public void req18() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT f FROM Film f JOIN f.genre g WHERE g.nom='Horreur' "
                + " EXCEPT "
                + "SELECT f FROM Film f JOIN f.acteurs a  WHERE a.nom='Polanski' ");
        long count = query.getResultList().size();
        Assert.assertEquals(0, count);
    }
    
    @Test
    public void req19() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.acteurs a  WHERE a.nom='Polanski' ");
        long count = (long) query.getSingleResult();
        Assert.assertEquals(1, count);
    }
    
    @Test
    public void req20() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
        Query query = em.createQuery("SELECT f FROM Film f JOIN f.acteurs a  WHERE a.nom='Polanski' "
                + " UNION "
                + "SELECT f FROM Film f JOIN f.genre g  WHERE g.nom='horreur'");
        long count = (long) query.getResultList().size();
        Assert.assertEquals(1, count);
    }  
    
    
   @Test
    public void req21() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query = em.createQuery("SELECT count(f) FROM Film f GROUP BY f.genre");
        Query query = em.createQuery("SELECT g.nom,count(f) FROM Film f JOIN f.genre g GROUP BY g");

        List<Object[]> l = query.getResultList();
           for(Object[] o:l ){
            System.out.println(o[0]+"   "+o[1]);
        }
    }  
    
     @Test
    public void req22() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query = em.createQuery("SELECT count(f) FROM Film f GROUP BY f.genre");
        Query query = em.createQuery("SELECT r.prenom, r.nom, count(f) nbFilms FROM Film f JOIN f.realisateurs r GROUP BY r ORDER BY nbfilms, r.nom, r.prenom");

        List<Object[]> l = query.getResultList();
           for(Object[] o:l ){
            System.out.println(String.format("%s %s %d", o[0],o[1],o[2]));
        }
           
    }  
    
        @Test
    public void req23() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query = em.createQuery("SELECT count(f) FROM Film f GROUP BY f.genre");
        Query query = em.createQuery("SELECT r.nom, r.prenom, count(f) nbf FROM Film f JOIN f.realisateurs r GROUP BY r "
                + "HAVING nbf>1 "
                + "ORDER BY nbf ");//having est le where de order by

        List<Object[]> l = query.getResultList();
           for(Object[] o:l ){
            System.out.println(String.format("%s %s %d", o[0],o[1],o[2]));
        }
    }   
    
       
        @Test
    public void req24() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query = em.createQuery("SELECT count(f) FROM Film f GROUP BY f.genre");
        Query query = em.createQuery("SELECT sr.titre, count(sa) nsa  FROM Serie sr JOIN sr.saisons sa GROUP BY sr ORDER BY nsa, sr.titre ");//having est le where de order by

        List<Object[]> l = query.getResultList();
           for(Object[] o:l ){
            System.out.println(String.format("%s %d", o[0],o[1]));
        }
    } 
    
            @Test
    public void req25() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();
//        Query query = em.createQuery("SELECT count(f) FROM Film f GROUP BY f.genre");
        Query query = em.createQuery("SELECT sr.titre, count(ep) nep  FROM Serie sr JOIN sr.saisons sa JOIN sa.episodes ep GROUP BY sr HAVING nep>5 "
                + " ORDER BY nep");//having est le where de order by

        List<Object[]> l = query.getResultList();
           for(Object[] o:l ){
            System.out.println(String.format("%s %d", o[0],o[1]));
        }
    }    
   
}
