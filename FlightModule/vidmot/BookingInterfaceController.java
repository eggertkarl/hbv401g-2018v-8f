/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlightModule.vidmot;

import FlightModule.src.Flight;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Sverrir Heiðar Davíðsson, shd4@hi.is
 */
public class BookingInterfaceController implements Initializable {
    
    private SearchInterfaceController searchInterfaceController;
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTableWithData();
        flights = FXCollections.observableArrayList(); 
    }    
    
    public void initializeBookingInterfaceController(SearchInterfaceController controller){
        searchInterfaceController = controller;
    }
    
    public void setSelectedFlight(Flight flight){
        selectedFlight = flight;
        updateTableView();
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
        flights.add(selectedFlight);
        jTableView1.setItems(flights);
        jTableView2.setItems(flights);
        jTableView3.setItems(flights);
    }
    
}
