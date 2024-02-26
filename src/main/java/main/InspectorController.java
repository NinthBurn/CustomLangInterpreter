package main;

import controller.ProgramStateController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.stage.Stage;

import model.ProgramState;
import model.statements.IStmt;
import model.values.IValue;
import repository.MemoryRepository;

import java.util.Map.Entry;

class TableItem2Columns {
    private final StringProperty column1;
    private final StringProperty column2;

    class TableItem3Columns {
        private final StringProperty column1;
        private final StringProperty column2;
        private final StringProperty column3;

        public TableItem3Columns() {
            column1 = new SimpleStringProperty(this, "name");
            column2 = new SimpleStringProperty(this, "value1");
            column3 = new SimpleStringProperty(this, "value2");
        }

        public TableItem3Columns(String name,
                                 String value1, String value2) {
            this();
            setColumn1(name);
            setColumn2(value1);
            setColumn3(value2);
        }

        public StringProperty column1Property() {
            return column1;
        }

        public String getColumn1() {
            return column1.get();
        }

        public void setColumn1(String column1) {
            this.column1.set(column1);
        }

        public StringProperty column2Property() {
            return column2;
        }

        public String getColumn2() {
            return column2.get();
        }

        public void setColumn2(String column2) {
            this.column2.set(column2);
        }
        public StringProperty column3Property() {
            return column3;
        }

        public String getColumn3() {
            return column3.get();
        }

        public void setColumn3(String column3) {
            this.column3.set(column3);
        }
    }

    public TableItem2Columns() {
        column1 = new SimpleStringProperty(this, "name");
        column2 = new SimpleStringProperty(this, "value");
    }

    public TableItem2Columns(String name,
                             String value) {
        this();
        setColumn1(name);
        setColumn2(value);
    }

    public StringProperty column1Property() {
        return column1;
    }

    public String getColumn1() {
        return column1.get();
    }

    public void setColumn1(String column1) {
        this.column1.set(column1);
    }

    public StringProperty column2Property() {
        return column2;
    }

    public String getColumn2() {
        return column2.get();
    }

    public void setColumn2(String column2) {
        this.column2.set(column2);
    }
}

public class InspectorController {
    @FXML
    private Button execute_once_button;
    @FXML
    private Button return_button;
    @FXML
    private Button execute_all_button;
    @FXML
    private ListView<String> output_list;
    @FXML
    private ListView<IStmt> execution_stack;
    @FXML
    private ListView<String> file_table;
    @FXML
    private TableView<TableItem2Columns> symbol_table;
    @FXML
    private TableColumn<TableItem2Columns, String> symbol_table_identifier_column;
    @FXML
    private TableColumn<TableItem2Columns, String> symbol_table_value_column;
    @FXML
    private TableView<TableItem2Columns> heap_table;
    @FXML
    private TableColumn<TableItem2Columns, String> heap_table_address_column;
    @FXML
    private TableColumn<TableItem2Columns, String> heap_table_value_column;
    @FXML
    private TableView<TableItem2Columns> latch_table;
    @FXML
    private TableColumn<TableItem2Columns, String> latch_table_index_column;
    @FXML
    private TableColumn<TableItem2Columns, String> latch_table_value_column;
    @FXML
    private ListView<String> program_list;
    @FXML
    private TextField number_of_programs_field;
    @FXML
    private TextField current_program_field;
    @FXML
    private Label finished_execution_label;

    private IStmt original_statement;
    private ProgramStateController controller;
    private MemoryRepository repository;
    private ProgramState selected_program;

    public InspectorController(){
        repository = null;
        controller = null;
    }

    public void initialize(){
        Platform.runLater(() -> {
            ProgramState program = new ProgramState(original_statement);
            repository = new MemoryRepository("program_" + program.getId() + "_log.txt");
            repository.add(program);
            controller = new ProgramStateController(repository);
            selected_program = program;

            program_list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    System.out.println("selected " + program_list.getSelectionModel().getSelectedItem());
                    if(program_list.getSelectionModel().getSelectedItem() != null)
                        onProgramListSelection();
                }
            });

            updateInformation();
        });
    }

    public void updateInformation(){
        // other details
        if(repository.getAll().isEmpty()){
            finished_execution_label.setText("Program finished execution!");
            current_program_field.setText("0");
        }else{
            if(!repository.getAll().contains(selected_program))
                selected_program = repository.getAll().get(0);

            current_program_field.setText(String.valueOf(selected_program.getId()));
        }

        number_of_programs_field.setText(String.valueOf(repository.getAll().size()));

        // output
        ObservableList<String> output = FXCollections.observableArrayList(selected_program.getOutputList().toList());
        output_list.setItems(output);

        // execution stack
        ObservableList<IStmt> stack_statements = FXCollections.observableArrayList(selected_program.getStack().toList());
        execution_stack.setItems(stack_statements);

        // file table
        ObservableList<String> opened_files = FXCollections.observableArrayList(
                selected_program.getFileTable().entrySet().stream()
                        .map(e -> e.getKey())
                        .toList()
        );
        file_table.setItems(opened_files);

        // program list
        ObservableList<String> program_ids = FXCollections.observableArrayList(repository.getAll().stream().map(p -> String.valueOf(p.getId())).toList());
        if(!program_list.getItems().equals(program_ids))
            program_list.setItems(program_ids);

        // symbol table
        ObservableList<TableItem2Columns> symbols = FXCollections.observableArrayList();

        for (Entry<String, IValue> entry : selected_program.getSymbolTable().entrySet()) {
            symbols.add(new TableItem2Columns(entry.getKey(), entry.getValue().toString()));
        }

        symbol_table.setItems(symbols);
        symbol_table_identifier_column.setCellValueFactory(f -> f.getValue().column1Property());
        symbol_table_value_column.setCellValueFactory(f -> f.getValue().column2Property());

        // heap table
        ObservableList<TableItem2Columns> heap_entries = FXCollections.observableArrayList();

        for (Entry<Integer, IValue> entry : selected_program.getHeapTable().entrySet()) {
            heap_entries.add(new TableItem2Columns(entry.getKey().toString(), entry.getValue().toString()));
        }

        heap_table.setItems(heap_entries);
        heap_table_address_column.setCellValueFactory(f -> f.getValue().column1Property());
        heap_table_value_column.setCellValueFactory(f -> f.getValue().column2Property());

        // latch table
        ObservableList<TableItem2Columns> latch_entries = FXCollections.observableArrayList();

        for (Entry<Integer, Integer> entry : selected_program.getLatchTable().entrySet()) {
            latch_entries.add(new TableItem2Columns(entry.getKey().toString(), entry.getValue().toString()));
        }

        latch_table.setItems(latch_entries);
        latch_table_index_column.setCellValueFactory(f -> f.getValue().column1Property());
        latch_table_value_column.setCellValueFactory(f -> f.getValue().column2Property());
    }

    @FXML
    public void onProgramListSelection(){
        Integer new_id = Integer.parseInt(program_list.getSelectionModel().selectedItemProperty().get());
        if(new_id != null){
            ProgramState new_program = repository.getAll().stream().filter(p -> p.getId() == new_id).toList().get(0);

            selected_program = new_program;
            updateInformation();
        }
    }
    @FXML
    public void onReturnButtonClick(){
        // return to main menu
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("mainapp.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 400, 320);
            Stage stage = (Stage)return_button.getScene().getWindow();
            stage.setMinWidth(400);
            stage.setMinHeight(320);
            stage.setScene(scene);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("Returning to main menu");
    }

    @FXML
    public void onExecuteOnceButtonClick(){
        try{
            if(repository.getAll().isEmpty())
                throw new Exception("Program has finished execution.");

            controller.oneStepExecutionForAll();
            updateInformation();
        }catch(Exception e){
            ErrorWindow error_popup = new ErrorWindow(e.getMessage());

            onReturnButtonClick();
        }

    }
    @FXML
    public void onExecuteAllButtonClick(){
        try{
            if(repository.getAll().isEmpty())
                throw new Exception("Program has finished execution.");

            controller.allStepExecutionForAll();
            updateInformation();
        }catch(Exception e){
            ErrorWindow error_popup = new ErrorWindow(e.getMessage());

            onReturnButtonClick();
        }

    }
    public void setStatement(IStmt program_statement){
        original_statement = program_statement;
    }


}
