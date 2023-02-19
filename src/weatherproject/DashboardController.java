package weatherproject;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.NumberAxis;

import java.net.URL;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import static weatherproject.JASONBulkData.LOCATION;

public class DashboardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label airPressure;

    @FXML
    private TextField searchField;

    @FXML
    private Label dayMonthYear;

    @FXML
    private HBox exitButton;

    @FXML
    private Label feelsLike;

    @FXML
    private Label humidity;

    @FXML
    private Label location;

    @FXML
    private Label monthYear;

    @FXML
    private Label sunRise;

    @FXML
    private Label sunSet;

    @FXML
    private Label temperature;

    @FXML
    private Label time;

    @FXML
    private Label visibility;

    @FXML
    private Label windSpeed;

    @FXML
    private Label weatherState;

    @FXML
    private ImageView weatherIcon;

    @FXML
    private BarChart<CategoryAxis, CategoryAxis> barChart;

    @FXML
    void exit(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void search(MouseEvent event) {
        JSONDataCollection.LOCATION = searchField.getText().replace(" ", "");
        //JSONDataCollection.getData("http://api.openweathermap.org/data/2.5/weather?q=" + searchField.getText() + "&APPID=85e2bf12407292d2571cae8391915d14"); //(city,country)
        JSONDataCollection.getData1("http://api.openweathermap.org/data/2.5/weather?q=" + searchField.getText() + "&appid=85e2bf12407292d2571cae8391915d14"); //(city)
        setValues();
        searchField.setText("");
    }

    private Scene scene;
    private Stage stage;
    private Parent root;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void map(MouseEvent event) throws IOException {
        int end = JSONDataCollection.LOCATION.indexOf(',');
        MapController.location = JSONDataCollection.LOCATION;
        Parent root = FXMLLoader.load(getClass().getResource("map.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void fiveDayMethod(MouseEvent event) throws IOException {
        JASONBulkData.LOCATION = JSONDataCollection.LOCATION;
        JASONBulkData.getData("http://api.openweathermap.org/data/2.5/forecast?q=" + JSONDataCollection.LOCATION + "&cnt=40&appid=85e2bf12407292d2571cae8391915d14&units=metric");
        Parent root = FXMLLoader.load(getClass().getResource("Five_day.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        searchField.setPromptText("City");
        JSONDataCollection.LOCATION = "dhaka";
        JSONDataCollection.getData1("http://api.openweathermap.org/data/2.5/weather?q=dhaka&APPID=85e2bf12407292d2571cae8391915d14");
        setValues();
        //JASONBulkData.getData();
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("x axis");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("y axis");
        
        barChart = new BarChart(xAxis, yAxis);
        
        XYChart.Series data = new XYChart.Series();
        data.setName("Name");
        
        data.getData().add(new XYChart.Data("A", 3000));
        data.getData().add(new XYChart.Data("B", 4000));
        data.getData().add(new XYChart.Data("C", 1000));
        
        barChart.getData().add(data);
    }

    void setValues() {

        String values[] = new String[16];
        try {
            File file = new File("data.txt");
            Scanner scan = new Scanner(file);

            int i = 0;
            while (scan.hasNext()) {
                values[i] = scan.next();
                i++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        int st = Integer.parseInt(dtf.format(now).toString().substring(0, 2));
        String ampm, currentTime;
        if (st > 12) {
            st = st - 12;
            ampm = " PM";
            currentTime = Integer.toString(st) + dtf.format(now).toString().substring(2, 5) + ampm;
        } else {
            ampm = " AM";
            currentTime = dtf.format(now).toString() + ampm;
        }

        airPressure.setText(values[7] + " hPa");

        dayMonthYear.setText(dtf2.format(now));

        float fl = Float.parseFloat(values[4]);
        fl = fl - 273;
        feelsLike.setText(Integer.toString((int) fl) + "° C");

        humidity.setText(values[8] + "%");

        location.setText(values[14] + "," + values[11]);

        //monthYear.setText(values[0]);
        long tmp = Long.parseLong(values[12]);
        java.util.Date tm = new java.util.Date((long) tmp * 1000);
        sunRise.setText(tm.toString().substring(4, 16));

        long tmp2 = Long.parseLong(values[13]);
        java.util.Date tm2 = new java.util.Date((long) tmp2 * 1000);
        sunSet.setText(tm2.toString().substring(4, 16));

        float tp = Float.parseFloat(values[3]);
        tp = tp - 273;
        temperature.setText(Integer.toString((int) tp) + "° C");

        time.setText(currentTime);

        float vs = Float.parseFloat(values[9]);
        vs = vs / 1000;
        visibility.setText(Float.toString(vs) + " km");

        windSpeed.setText(values[0] + " m/s");

        weatherState.setText(values[2]);

        Image image = new Image("http://openweathermap.org/img/wn/" + values[15] + "@2x.png");
        weatherIcon.setImage(image);

    }

}
