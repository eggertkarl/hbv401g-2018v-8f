/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlightModule.vidmot;

import FlightModule.src.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.RadioButton;

import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;


/**
 * FXML Controller class
 *
 * @author Sverrir Heiðar Davíðsson, shd4@hi.is
 */
public class SearchInterfaceController implements Initializable {

    private SearchController searchController;
    
    @FXML
    private ComboBox<String> departureLocationBox;
    @FXML
    private ComboBox<String> arrivalLocationBox;
    @FXML
    private ComboBox<String> airlineBox;
    
    private ObservableList<Flight> flights;
    
    private int activeIndex=0;
    
    @FXML
    private TableView<Flight> jTableView;
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
    private TableColumn<Flight, String> jReservedSeatsCoach;
    @FXML
    private TableColumn<Flight, String> jAverageRating;
    @FXML
    private TableColumn<Flight, String> jHasMeal;
    @FXML
    private TableColumn<Flight, String> jHasVEgiterianMeal;
    @FXML
    private TableColumn<Flight, String> jHasEntertainment;
    @FXML
    private Label lowestPriceLabel;
    @FXML
    private Label highestPriceLable;
    @FXML
    private Label lowestPriceLabel1;
    @FXML
    private Label highestPriceLable1;
    @FXML
    private Slider sliderMin;
    @FXML
    private Slider sliderMax;
    @FXML
    private TextField minPriceTextField;
    @FXML
    private TextField maxPriceTextField;
    @FXML
    private DatePicker jCalendarFrom;
    @FXML
    private RadioButton jFlexibleRadio;
    @FXML
    private DatePicker jCalendarTo;
    //@FXML
    @FXML
    private BookingInterfaceController bookingInterfaceController;
    @FXML
    private Tab bookingTab;
    private double sliderValueMax;
    private double sliderValueMin;
    private Flight selectedFlight;
    @FXML
    private Accordion jAccordion;
    @FXML
    private TitledPane jFlightParameters;

    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize all controllers and variables
        searchController = new SearchController();
        flights = FXCollections.observableArrayList(); 
        jAccordion.setExpandedPane(jFlightParameters);
        
        //uppfæra comboBoxa með gildum
        initializeComboBoxes();
        
        //Listener for when an object in tableView is selected, updates active index
        initializeTableView();
        
        //sets a listener for the date object
        initializeCalendar();
        
        //Initializes the highest and lowest price ranges and sets listeners
        initializePriceSliders();
        
        //Initializes the BookingInterfaceController
        initializeBookingInterfaceController();
    }    
    
    private void initializeBookingInterfaceController(){
        bookingInterfaceController.initializeBookingInterfaceController(this);
    }
    
    
    private void initializeCalendar(){
        jFlexibleRadio.selectedProperty().addListener((new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                jCalendarTo.setDisable(!newValue);
            }
        }));
        
        jCalendarFrom.valueProperty().addListener((new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                    LocalDate oldValue, LocalDate newValue) {
                if(jFlexibleRadio.selectedProperty().getValue() && jCalendarTo.valueProperty().isNotNull().getValue()){
                    searchController.filterSetDepartureTimeInterval(
                            newValue.atStartOfDay(), 
                            jCalendarTo.valueProperty().getValue().atTime(23, 59));
                } else {
                    searchController.filterSetDepartureTimeEqualTo(
                            newValue.atStartOfDay());
                }
            }
        }));
        
        jCalendarTo.valueProperty().addListener((new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable,
                    LocalDate oldValue, LocalDate newValue) {
                    
                    searchController.filterSetDepartureTimeInterval(
                        jCalendarFrom.valueProperty().getValue().atStartOfDay(), 
                        newValue.atTime(23, 59));
                   
                }
            }
        ));
    }
    
    private void initializePriceSliders(){
        setSlidersMinMax();
        setSlidersListeners();
    }
    

    private void setSlidersMinMax(){
        //Find the minimum and maximum values of 
        int lowestPrice = Math.min(searchController.getMinMaxPriceFirstClass()
                .get(0), searchController.getMinMaxPriceCoach().get(0));
        
        int highestPrice = Math.max(searchController.getMinMaxPriceFirstClass()
                .get(1), searchController.getMinMaxPriceCoach().get(1));
        //Set the lables next to the sliders 
        lowestPriceLabel.setText(lowestPrice + "");
        highestPriceLable.setText(highestPrice + "");
        lowestPriceLabel1.setText(lowestPrice + "");
        highestPriceLable1.setText(highestPrice + "");
        //Set minimum and maximum values of sliders
        sliderMin.setMin(lowestPrice);
        sliderMin.setMax(highestPrice);
        sliderMax.setMin(lowestPrice);
        sliderMax.setMax(highestPrice);
        
        sliderMax.setValue(highestPrice);
        
        minPriceTextField.setText(lowestPrice +"");
        maxPriceTextField.setText(highestPrice +"");
    }

    private void setSlidersListeners(){
        sliderMin.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                if((double)new_val>sliderMax.getValue()){
                    sliderMax.setValue((double)new_val);
                    minPriceTextField.setText(String.format("%.0f", new_val));
                } else{
                    minPriceTextField.setText(String.format("%.0f", new_val));
                }
                updatePriceFilter();
            }
        });
        
        
        sliderMax.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
                if((double)new_val<sliderMin.getValue()){
                    sliderMin.setValue((double)new_val);
                    maxPriceTextField.setText(String.format("%.0f", new_val));
                } else{
                    maxPriceTextField.setText(String.format("%.0f", new_val));
                }
                updatePriceFilter();
                    
            }
        });
                
               
    }
    
    private void updatePriceFilter(){
        searchController.filterSetPriceCoachInterval(
                ((Double)sliderMin.getValue()).intValue(), 
                ((Double)sliderMax.getValue()).intValue());
    }
    
    
    @FXML
    private void updateSliderPriceMin(ActionEvent event) {
        sliderMin.setValue(Double.parseDouble(minPriceTextField.getText()));
    }

    @FXML
    private void updateSliderPriceMax(ActionEvent event) {
        sliderMax.setValue(Double.parseDouble(maxPriceTextField.getText()));
    }
    
    
    private void initializeComboBoxes(){
        
        //Strings for names of "no filter" option in ComboBoxes
        String noLocationFilter = "Any Location";
        String noAirlineFilter = "Any airline";
        
        //Add "no filter" option to list
        ObservableList<String> locations = arrayListToObservableListString(searchController.getListOfLocations());
        locations.add(0, noLocationFilter);
        ObservableList<String> airlines = arrayListToObservableListString(searchController.getListOfAirlines());
        airlines.add(0, noAirlineFilter);
        
        //Add values to lists
        airlineBox.setItems(airlines);
        departureLocationBox.setItems(locations);
        arrivalLocationBox.setItems(locations);

        //Listener for the comboboxes to change the results if the combobox changes
        arrivalLocationBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ObservableList<String> valdirTimar = FXCollections.observableArrayList();;
                    if(newValue == null){
                        return;
                    }
                    if (newValue.equals(noLocationFilter))  {
                        searchController.filterRemoveArrivalLocation();
                    } 
                    else {
                        searchController.filterSetArrivalLocationEqualTo(newValue);
                    }
                    //updateTableView();
                    }
        });
        
        
        
        departureLocationBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ObservableList<String> valdirTimar = FXCollections.observableArrayList();;
                    if(newValue == null){
                        return;
                    }
                    if (newValue.equals(noLocationFilter))  {
                        searchController.filterRemoveDepartureLocation();
                    } 
                    else {
                        searchController.filterSetDepartureLocationEqualTo(newValue);
                    }
                    //
                    }
        });
        
        
        
        airlineBox.getSelectionModel()
                .selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    ObservableList<String> valdirTimar = FXCollections.observableArrayList();;
                    if(newValue == null){
                        return;
                    }
                    if (newValue.equals(noAirlineFilter))  {
                        searchController.filterRemoveAirline();
                    } 
                    else {
                        searchController.filterSetAirlineEqualTo(newValue);
                        
                    }
                    //updateTableView();
                    }
        });
        
        arrivalLocationBox.getSelectionModel().select(0);
        departureLocationBox.getSelectionModel().select(0);
        airlineBox.getSelectionModel().select(0);
        
    }
    //Updates TableView with current flight list
    private void updateTableView(){
        flights = arrayListToObservableListFlight(searchController.searchForFlights());
        jTableView.setItems(flights);
    }
    
    private void initializeTableView(){
        fillTableWithData();
        
        MultipleSelectionModel<Flight> indexListener = jTableView.getSelectionModel();
        indexListener.selectedItemProperty().addListener(new ChangeListener<Flight>() {
            public void changed(ObservableValue<? extends Flight> observable, Flight oldValue, Flight newValue) {
                selectedFlight = newValue;
                searchController.fetchSeats(selectedFlight);
                
            }
        });
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
                        .getAirplaneType().toString()));
        
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
                        .getReservedSeatsFirstClass().toString()));
        
        jReservedSeatsCoach.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getReservedSeatsCoach().toString()));
        
        jAverageRating.setCellValueFactory(cellData -> 
                new ReadOnlyStringWrapper(cellData.getValue()
                        .getAverageRating() == null ? "Ekkert gildi" : Math.round(
                                100*Double.parseDouble(cellData.getValue().getAverageRating().toString()))/100.0 + ""));
        
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
    
    

    
    public ObservableList<String> getFlightsObservableListString(){
        this.flights = arrayListToObservableListFlight(searchController.searchForFlights());
        ObservableList<String> newList = FXCollections.observableArrayList();
        for(Flight a: flights){
            newList.add(a.toString());
        }
        return newList;
    }
    
    private ObservableList<Flight> arrayListToObservableListFlight(ArrayList<Flight> list){
        ObservableList<Flight> newList = FXCollections.observableArrayList();
        for(Flight a: list){
            newList.add(a);
        }
        return newList;
    }
    
    private ObservableList<String> arrayListToObservableListString(ArrayList<String> list){
        ObservableList<String> newList = FXCollections.observableArrayList();;
        for(String a: list){
            newList.add(a);
        }
        return newList;
    }


    @FXML
    private void searchFlightHandler(ActionEvent event) {
        updateTableView();
    }

    @FXML
    private void bookFlightHandler(ActionEvent event) {
        bookingTab.setDisable(false);
        System.out.println(selectedFlight);
        bookingInterfaceController.clearParameters();
        bookingInterfaceController.setSelectedFlight(selectedFlight);
        
    }

    @FXML
    private void filterByMeal(ActionEvent event) {
        if(((RadioButton)event.getSource()).isSelected()){
            searchController.filterSetHasMealEqualTo(true);
        } else {
            searchController.filterRemoveHasMeal();
        }
    }

    @FXML
    private void filterByVegiterianMeal(ActionEvent event) {
        if(((RadioButton)event.getSource()).isSelected()){
            searchController.filterSetHasVegeterianMealEqualTo(true);
        } else {
            searchController.filterRemoveHasVegeterianMeal();
        }
        
    }

    @FXML
    private void filterByEntertainment(ActionEvent event) {
        if(((RadioButton)event.getSource()).isSelected()){
            searchController.filterSetHasEntertainmentEqualTo(true);
        } else {
            searchController.filterRemoveHasEntertainment();
        }
    }

    @FXML
    private void clearParameters(ActionEvent event) {
        
    }


}
