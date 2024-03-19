package sio.devoir2graphiques;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sio.devoir2graphiques.Entities.DatasGraph;
import sio.devoir2graphiques.Tools.ConnexionBDD;
import sio.devoir2graphiques.Tools.GraphiqueController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class Devoir2GraphiquesController implements Initializable
{
    ConnexionBDD maCnx;

    GraphiqueController graphiqueController;
    TreeMap<String,Integer> datasGraphique1;
    TreeMap<String,ArrayList<DatasGraph>> datasGraphique2;
    HashMap<String,Double> datasGraphique3;
    XYChart.Series<String,Integer> serieGraph;

    @FXML
    private Button btnGraph1;
    @FXML
    private Button btnGraph2;
    @FXML
    private Button btnGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private LineChart graph1;
    @FXML
    private Label lblTitre;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private PieChart graph3;
    @FXML
    private BarChart graph2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitre.setText("Devoir : Graphique n°1");
        apGraph1.toFront();
        try {
            maCnx = new ConnexionBDD();
            graphiqueController = new GraphiqueController();
            datasGraphique1 = graphiqueController.getDatasGraphique1();
            serieGraph = new XYChart.Series<>();
            graph1.getData().clear();
            serieGraph.setName("Moyenne");
            for(String age : datasGraphique1.keySet())
            {
                serieGraph.getData().add(new XYChart.Data(age,datasGraphique1.get(age)));
            }

            graph1.getData().add(serieGraph);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void menuClicked(Event event) throws SQLException {
        if(event.getSource() == btnGraph1)
        {
            lblTitre.setText("Devoir : Graphique n°1");
            apGraph1.toFront();
            datasGraphique1 = graphiqueController.getDatasGraphique1();
            serieGraph = new XYChart.Series<>();
            serieGraph.setName("Moyenne");
            graph1.getData().clear();
            for(String age : datasGraphique1.keySet())
            {
                serieGraph.getData().add(new XYChart.Data(age,datasGraphique1.get(age)));
            }
            graph1.getData().add(serieGraph);
        }
        else if(event.getSource() == btnGraph2)
        {
            lblTitre.setText("Devoir : Graphique n°2");
            apGraph2.toFront();
            datasGraphique2 = graphiqueController.getDatasGraphique2();
            serieGraph = new XYChart.Series<>();
            serieGraph.setName("Moyenne");
            graph2.getData().clear();
            for(String sexe : datasGraphique2.keySet())
            {
                serieGraph = new XYChart.Series<>();
                serieGraph.setName(sexe);
                for (DatasGraph datasGraph : datasGraphique2.get(sexe))
                {
                    serieGraph.getData().add(new XYChart.Data(datasGraph.getTranche(),datasGraph.getNb()));
                }
                graph2.getData().add(serieGraph);
            }
        }
        else
        {
            lblTitre.setText("Devoir : Graphique n°3");
            apGraph3.toFront();
            graph3.getData().clear();
            datasGraphique3 = graphiqueController.getDatasGraphique3();
            ObservableList<PieChart.Data> serieGraph3 = FXCollections.observableArrayList();
            for (String sexe : datasGraphique3.keySet())
            {
                serieGraph3.add(new PieChart.Data(sexe,datasGraphique3.get(sexe) ));
            }
            graph3.setData(serieGraph3);
        }
    }
}