/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package weatherproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import static weatherproject.JASONBulkData.LOCATION;

/**
 * FXML Controller class
 *
 * @author User
 */
public class Five_dayController implements Initializable {

    @FXML
    private Label main1;

    @FXML
    private Label main2;

    @FXML
    private Label main3;

    @FXML
    private Label main4;

    @FXML
    private Label main5;

    @FXML
    private Label min_temp1;

    @FXML
    private Label min_temp2;

    @FXML
    private Label min_temp3;

    @FXML
    private Label min_temp4;

    @FXML
    private Label min_temp5;

    @FXML
    private Label max_temp1;

    @FXML
    private Label max_temp2;

    @FXML
    private Label max_temp3;

    @FXML
    private Label max_temp4;

    @FXML
    private Label max_temp5;

    @FXML
    private ImageView detailicon;

    @FXML
    private ImageView icon1;

    @FXML
    private ImageView icon2;

    @FXML
    private ImageView icon3;

    @FXML
    private ImageView icon4;

    @FXML
    private ImageView icon5;

    @FXML
    private Label day1;

    @FXML
    private Label day2;

    @FXML
    private Label day3;

    @FXML
    private Label day4;

    @FXML
    private Label day5;

    @FXML
    private Label deg;

    @FXML
    private Label description;

    @FXML
    private Label feels_like;

    @FXML
    private Label humidity;

    @FXML
    private Label location;

    @FXML
    private Label pressure;

    @FXML
    private Label speed;

    @FXML
    private Label temp;

    @FXML
    private Label visibility;

    @FXML
    private TextField search;

    /**
     * Initializes the controller class.
     */
    int i = 0;
    
    @FXML
    void searchMethod(MouseEvent event) {
        JASONBulkData.getData("http://api.openweathermap.org/data/2.5/forecast?q=" + search.getText() + "&cnt=40&appid=85e2bf12407292d2571cae8391915d14&units=metric");
        setValue2();
        location.setText(search.getText().toUpperCase());
        //search.setText("");
        
    }

    @FXML
    void d1m(MouseEvent event) {
        i = 0;
        setDetails();
    }

    @FXML
    void d2m(MouseEvent event) {
        i = 1;
        setDetails();
    }

    @FXML
    void d3m(MouseEvent event) {
        i = 2;
        setDetails();
    }

    @FXML
    void d4m(MouseEvent event) {
        i = 3;
        setDetails();
    }

    @FXML
    void d5m(MouseEvent event) {
        i = 4;
        setDetails();
    }

    private Scene scene;
    private Stage stage;
    private Parent root;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    void back(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
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
        setValue2();
        search.setPromptText("City");
    }

    public void setDetails() {
        //LOCATION = search.getText().toUpperCase();
        temp.setText(JASONBulkData.days[i][0] + "°C");

        deg.setText("Wind Direction " + JASONBulkData.days[i][9] + " °");

        description.setText(JASONBulkData.days[i][5]);

        feels_like.setText("Feels Like " + JASONBulkData.days[i][1] + "°C");

        humidity.setText("Humidity " + JASONBulkData.days[i][6] + "%");

        //location.setText(JASONBulkData.LOCATION);

        pressure.setText("Air Pressure " + JASONBulkData.days[i][4] + " hPa");

        speed.setText("Wind speed " + JASONBulkData.days[i][8] + " m/s");

        visibility.setText("Visibility " + JASONBulkData.days[i][7] + " km");
        
        Image image6 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[i][11] + "@2x.png");
        detailicon.setImage(image6);
    }

    public void setValue2() {
        if(!JSONDataCollection.LOCATION.equals(""))
        LOCATION = JSONDataCollection.LOCATION;
        
        temp.setText(JASONBulkData.days[i][0] + "°C");

        deg.setText("Wind Direction " + JASONBulkData.days[i][9] + " °");

        description.setText(JASONBulkData.days[i][5]);

        feels_like.setText("Feels Like " + JASONBulkData.days[i][1] + "°C");

        humidity.setText("Humidity " + JASONBulkData.days[i][6] + "%");

        location.setText(JSONDataCollection.LOCATION.toUpperCase());

        pressure.setText("Air Pressure " + JASONBulkData.days[i][4] + " hPa");

        speed.setText("Wind speed " + JASONBulkData.days[i][8] + " m/s");

        visibility.setText("Visibility " + JASONBulkData.days[i][7] + " km");

        long dt1 = Long.parseLong(JASONBulkData.days[0][13]);
        java.util.Date time1 = new java.util.Date((long) dt1 * 1000);
        day1.setText(time1.toString().substring(3, 10));

        long dt2 = Long.parseLong(JASONBulkData.days[1][13]);
        java.util.Date time2 = new java.util.Date((long) dt2 * 1000);
        day2.setText(time2.toString().substring(3, 10));

        long dt3 = Long.parseLong(JASONBulkData.days[2][13]);
        java.util.Date time3 = new java.util.Date((long) dt3 * 1000);
        day3.setText(time3.toString().substring(3, 10));

        long dt4 = Long.parseLong(JASONBulkData.days[3][13]);
        java.util.Date time4 = new java.util.Date((long) dt4 * 1000);
        day4.setText(time4.toString().substring(3, 10));

        long dt5 = Long.parseLong(JASONBulkData.days[4][13]);
        java.util.Date time5 = new java.util.Date((long) dt5 * 1000);
        day5.setText(time5.toString().substring(3, 10));

        Image image1 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[0][11] + "@2x.png");
        icon1.setImage(image1);

        Image image2 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[1][11] + "@2x.png");
        icon2.setImage(image2);

        Image image3 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[2][11] + "@2x.png");
        icon3.setImage(image3);

        Image image4 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[3][11] + "@2x.png");
        icon4.setImage(image4);

        Image image5 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[4][11] + "@2x.png");
        icon5.setImage(image5);

        Image image6 = new Image("http://openweathermap.org/img/wn/" + JASONBulkData.days[i][11] + "@2x.png");
        detailicon.setImage(image6);

        max_temp1.setText(JASONBulkData.days[0][3] + "°C");
        max_temp2.setText(JASONBulkData.days[1][3] + "°C");
        max_temp3.setText(JASONBulkData.days[2][3] + "°C");
        max_temp4.setText(JASONBulkData.days[3][3] + "°C");
        max_temp5.setText(JASONBulkData.days[4][3] + "°C");

        min_temp1.setText(JASONBulkData.days[0][2] + "°C");
        min_temp2.setText(JASONBulkData.days[1][2] + "°C");
        min_temp3.setText(JASONBulkData.days[2][2] + "°C");
        min_temp4.setText(JASONBulkData.days[3][2] + "°C");
        min_temp5.setText(JASONBulkData.days[4][2] + "°C");

        main1.setText(JASONBulkData.days[0][12]);
        main2.setText(JASONBulkData.days[1][12]);
        main3.setText(JASONBulkData.days[2][12]);
        main4.setText(JASONBulkData.days[3][12]);
        main5.setText(JASONBulkData.days[4][12]);
    }

}
