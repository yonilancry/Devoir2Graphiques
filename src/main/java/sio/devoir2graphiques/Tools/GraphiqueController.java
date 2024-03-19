package sio.devoir2graphiques.Tools;

import sio.devoir2graphiques.Entities.DatasGraph;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }

    public TreeMap<String,Integer> getDatasGraphique1() throws SQLException {
        TreeMap<String, Integer> datas = new TreeMap();

        cnx = ConnexionBDD.getCnx();
        ps = cnx.prepareStatement("SELECT employe.ageEmp,round(avg(employe.salaireEmp),2)\n" +
                "from employe\n" +
                "GROUP by employe.ageEmp\n" +
                "order by employe.ageEmp");
        rs = ps.executeQuery();
        while(rs.next())
        {
            datas.put(rs.getString(1), rs.getInt(2));
        }
        ps.close();
        rs.close();

        return datas;
    }
    public TreeMap<String, ArrayList<DatasGraph>> getDatasGraphique2() throws SQLException {
        TreeMap<String, ArrayList<DatasGraph>> datas = new TreeMap<>();

        cnx = ConnexionBDD.getCnx();
        ps = cnx.prepareStatement("select \"10 - 19\", sexe, count(*)\n" +
                "from employe\n" +
                "WHERE ageEmp BETWEEN 10 and 19\n" +
                "group by sexe\n" +
                "UNION\n" +
                "select \"20 - 29\", sexe, count(*)\n" +
                "from employe\n" +
                "WHERE ageEmp BETWEEN 20 and 29\n" +
                "group by sexe\n" +
                "UNION\n" +
                "select \"30 - 39\", sexe, count(*)\n" +
                "from employe\n" +
                "WHERE ageEmp BETWEEN 30 and 39\n" +
                "group by sexe\n" +
                "UNION\n" +
                "select \"40 - 49\", sexe, count(*)\n" +
                "from employe\n" +
                "WHERE ageEmp BETWEEN 40 and 49\n" +
                "group by sexe\n" +
                "UNION\n" +
                "select \"+50\", sexe, count(*)\n" +
                "from employe\n" +
                "WHERE ageEmp >=50\n" +
                "group by sexe\n" +
                "order by 1,sexe");
        rs = ps.executeQuery();
        while(rs.next())
        {
            DatasGraph datasGraph = new DatasGraph(rs.getInt(3),rs.getString(1));
            if(!datas.containsKey(rs.getString(2)))
            {
                ArrayList<DatasGraph> values = new ArrayList<>();
                values.add(datasGraph);
                datas.put(rs.getString(2),values);
            }
            else
            {
                datas.get(rs.getString(2)).add(datasGraph);
            }
        }
        ps.close();
        rs.close();

        return datas;
    }

    public HashMap<String,Double> getDatasGraphique3() throws SQLException {
        HashMap<String, Double> datas = new HashMap();

        cnx = ConnexionBDD.getCnx();
        ps = cnx.prepareStatement("SELECT employe.sexe, round((count(*)/(select count(*) FROM employe))*100,2) as nb\n" +
                "from employe\n" +
                "group by employe.sexe");
        rs = ps.executeQuery();
        while(rs.next())
        {
            datas.put(rs.getString(1), rs.getDouble(2));
        }
        ps.close();
        rs.close();

        return datas;
    }
}
