/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlightModule.vidmot;

import FlightModule.src.BookingController;
import FlightModule.src.Flight;
import FlightModule.src.Seat;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


/**
 * FXML Controller class
 *
 * @author Sverrir Heiðar Davíðsson, shd4@hi.is
 */
public class BookingInterfaceController implements Initializable {
    private SearchInterfaceController searchInterfaceController;
    private BookingController bookingController;
    private Flight selectedFlight;
    @FXML
    private TableView<Flight> jTableView1;
    @FXML
    private TableColumn<Flight, String> jDepartureLocation;
    @FXML
    private TableColumn<Flight, String> jArrivalLocation;
    @FXML
    private TableColumn<Flight, String> jDepartureTime;
    @FXML
    private TableColumn<Flight, String> jArrivalTime;
    @FXML
    private TableColumn<Flight, String> jFlightNumber;
    @FXML
    private TableColumn<Flight, String> jAirline;
    @FXML
    private TableColumn<Flight, String> jAirplaneType;
    @FXML
    private TableView<Flight> jTableView2;
    @FXML
    private TableColumn<Flight, String> jFirstClassPrice;
    @FXML
    private TableColumn<Flight, String> jCoachPrice;
    @FXML
    private TableColumn<Flight, String> jTotalSeatsFirstClass;
    @FXML
    private TableColumn<Flight, String> jTotalSeatsCoach;
    @FXML
    private TableColumn<Flight, String> jReservedSeatsFirstClass;
    @FXML
    private TableView<Flight> jTableView3;
    @FXML
    private TableColumn<Flight, String> jReservedSeatsCoach;
    @FXML
    private TableColumn<Flight, String> jAverageRating;
    @FXML
    private TableColumn<Flight, String> jHasMeal;
    @FXML
    private TableColumn<Flight, String> jHasVEgiterianMeal;
    @FXML
    private TableColumn<Flight, String> jHasEntertainment;
    
    private ObservableList<Flight> flights;
    private ArrayList<Seat> seats;
    private ArrayList<Seat> activeSeats;
    private ArrayList<Seat> seatsInRow;
    private Seat selectedSeat;
    private int selectedRow = 0;
    private int numberOfRows = 0;
    private int numberOfFirstClassRows = 0;
    @FXML
    private RadioButton firstClassRadio;
    private ObservableList<String> bookedSeatsList;
    
    @FXML
    private Button seatA;
    @FXML
    private Button seatB;
    @FXML
    private Button seatC;
    @FXML
    private Button seatF;
    @FXML
    private Button seatD;
    @FXML
    private Button seatE;
    @FXML
    private ComboBox<String> rowComboBox;
    @FXML
    private TextField jNameField;
    @FXML
    private TextField jPassportNumberField;
    @FXML
    private TextField jBags;
    @FXML
    private Label priceLabel;
    @FXML
    private ListView<String> jSeatListView;
    @FXML
    private Button jConfirm;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTableWithData();
        flights = FXCollections.observableArrayList(); 
        activeSeats = new ArrayList<Seat>();
        bookingController = new BookingController();
        bookedSeatsList = FXCollections.observableArrayList();
    }    
    
    public void initializeBookingInterfaceController(SearchInterfaceController controller){
        searchInterfaceController = controller;
        bookingController = new BookingController();
        
    }
    
    public void setSelectedFlight(Flight flight){
        selectedFlight = flight;
        bookingController.selectFlight(flight);
        updateTableView();
        initializeSeats();
    }
    
    private void fillTableWithData(){
        jDepartureLocation.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getDepartureLocation()));
        
        jArrivalLocation.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getArrivalLocation()));
        
        jDepartureTime.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getDepartureTime().toString()));
        
        jArrivalTime.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getArrivalTime().toString()));
        
        jFlightNumber.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getFlightNumber()));
        
        jAirline.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getAirline()));
        
        jAirplaneType.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getAirplaneType()));
        
        jFirstClassPrice.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getPriceFirstClass().toString()));
        
        jCoachPrice.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getPriceCoach().toString()));
        
        //Ath, listi af sætum. Geta takka sem sýnir sætin
        /*
        jSeats.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                       .getSeats().size()+""));
        */
        
        jTotalSeatsFirstClass.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getTotalSeatsFirstClass().toString()));
        
        jTotalSeatsCoach.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getTotalSeatsCoach().toString()));
        
        jReservedSeatsFirstClass.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getReservedSeatsCoach().toString()));
        
        jReservedSeatsCoach.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getReservedSeatsFirstClass().toString()));
        
        jAverageRating.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getAverageRating() == null ? "Ekkert gildi" : Math.round(
                                100*Double.parseDouble(cellData.getValue()
                                        .getAverageRating().toString()))/100.0 + ""));
        
        jHasMeal.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .hasMeal().toString()));
        
        jHasVEgiterianMeal.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .hasVegeterianMeal().toString()));
        
        jHasEntertainment.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .hasEntertainment().toString()));
        
    
        
    }
    
    private void updateTableView(){
        flights.clear();
        flights.add(selectedFlight);
        jTableView1.setItems(flights);
        jTableView2.setItems(flights);
        jTableView3.setItems(flights);
    }


    private void initializeSeats(){
        seats = selectedFlight.getSeats();
        activeSeats.clear();
        if(firstClassRadio.isSelected()){
            for(Seat a: seats){
                if(a.isFirstClass()){
                    activeSeats.add(a);
                }
            }
        } else {
            for(Seat a: seats){
                if(!a.isFirstClass()){
                    activeSeats.add(a);
                }
            }
        }
        ObservableList<String> rows = FXCollections.observableArrayList(); 
        for(Seat a: activeSeats){
            if(!rows.contains(a.getRow().toString())){
                rows.add(a.getRow().toString());
            }
        }
        
        rowComboBox.setItems(rows);
        rowComboBox.getSelectionModel().select(0);
        selectedRow = Integer.parseInt(rowComboBox.getSelectionModel()
                .getSelectedItem());
        rowComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue != null){
                    selectedRow = Integer.parseInt(newValue);
                    showSeats();
                }
                
            }
        });

    }
    
    private void showSeats(){
        seatA.setVisible(true);
        seatF.setVisible(true);
        
        seatsInRow = new ArrayList<Seat>();
        for(Seat a: activeSeats){
            if(a.getRow() == selectedRow){
                seatsInRow.add(a);
            }
        }
        if(seatsInRow.size() == 4){
            seatA.setVisible(false);
            seatB.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(0)));
            seatC.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(1)));
            seatD.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(2)));
            seatE.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(3)));
            seatF.setVisible(false);
            
            seatB.setText("A");
            seatC.setText("B");
            seatD.setText("C");
            seatE.setText("D");
        } else {
            seatA.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(0)));
            seatB.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(1)));
            seatC.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(2)));
            seatD.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(3)));
            seatE.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(4)));
            seatF.setDisable(bookingController.isSeatReserved(selectedFlight, seatsInRow.get(5)));

            seatA.setText("A");
            seatB.setText("B");
            seatC.setText("C");
            seatD.setText("D");
            seatE.setText("E");
            seatF.setText("F");
        }
        
    }
    
    @FXML
    private void firstClassHandler(ActionEvent event) {
        initializeSeats();
    }
    
    @FXML
    private void clickOnSeat(ActionEvent event) {
        showSeats();
        Button button = (Button)event.getSource();
        for(Seat a: seatsInRow){
            if(a.getColumn().equals(button.getText())){
                selectedSeat = a;
            }
        }
        button.setDisable(true);
        System.out.println("Seat in row: " + selectedSeat.getRow() + " and column: " + selectedSeat.getColumn());
        activateTextFields();
    }
    
    private void activateTextFields(){
        jNameField.setDisable(false);
        jPassportNumberField.setDisable(false);
    }
    
    @FXML
    private void subtractBags(ActionEvent event) {
        int nr = Integer.parseInt(jBags.getText());
        if(nr>0){
            nr--;
            jBags.setText(nr + "");
        }
    }

    @FXML
    private void addBags(ActionEvent event) {
        int nr = Integer.parseInt(jBags.getText());
        nr++;
        jBags.setText(nr + "");
    }

    @FXML
    private void bookSeatHandler(ActionEvent event) {
        String name = jNameField.getText();
        String passportNumber = jPassportNumberField.getText();
        int bags = Integer.parseInt(jBags.getText());
        
        if(!name.isEmpty() && !passportNumber.isEmpty()){
            if(bookingController.addSeat(name, passportNumber, selectedSeat, bags)){
                updateSeatListView(name);
                jConfirm.setDisable(false);
            }
        }
    }
    
    private void updateSeatListView(String name){
        String price = (selectedSeat.isFirstClass() ? 
                selectedFlight.getPriceFirstClass() + "": 
                selectedFlight.getPriceCoach()) + "";
        
        String seat = " Seat: " + selectedSeat.getColumn() + selectedSeat.getRow();
 
        String reservation = price + seat + " -- Name: " + name;
        bookedSeatsList.add(reservation);
        jSeatListView.setItems(bookedSeatsList);
        
        updateTotalPrice();
    }
    
    public void updateTotalPrice(){
        String price = priceLabel.getText();
        int number = price.indexOf(".00kr");
        int priceTotal = Integer.parseInt(price.substring(0,number));
        if(selectedSeat.isFirstClass()){
            priceTotal += selectedFlight.getPriceFirstClass();
        } else {
            priceTotal += selectedFlight.getPriceCoach();
        }
        priceLabel.setText(priceTotal + ".00kr");
    }

    @FXML
    private void confirmReservationHandler(ActionEvent event) {
        if (bookingController.confirmReservation()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Booking");
            alert.setHeaderText(null);
            alert.setContentText("Booking has been received");
            ButtonType iLagi = new ButtonType("Close");
            alert.getButtonTypes().setAll(iLagi);
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Booking");
            alert.setHeaderText(null);
            alert.setContentText("Error in booking.");
            ButtonType iLagi = new ButtonType("Close");
            alert.getButtonTypes().setAll(iLagi);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }
    
    public void clearParameters(){
    jNameField.setText("");
    jNameField.setDisable(true);
    jPassportNumberField.setText("");
    jPassportNumberField.setDisable(true);
    jBags.setText("1");
    bookedSeatsList = FXCollections.observableArrayList();
    jSeatListView.setItems(bookedSeatsList);
    priceLabel.setText("0.00kr");
        
    }
}
