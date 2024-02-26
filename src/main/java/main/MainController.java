package main;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import model.adt.IDictionary;
import model.adt.MyDictionary;
import model.expressions.*;
import model.statements.*;
import model.types.*;
import model.values.*;

public class MainController {
    @FXML
    private Button run_program_button;
    @FXML
    private ListView<IStmt> program_list;
    private final ObservableList<IStmt> items = FXCollections.observableArrayList();

    public void initialize(){
        IStmt ex1 = new CompoundStmt(new DeclarationStmt(new IntType(), "v"), new CompoundStmt(
                new AssignStmt("v", new ValueExpr(new IntValue(2))), new PrintStmt(new VariableExpr("v"))));

        // int a; int b; a=2+3*5; b=a+1; Print(b)
        IStmt ex2 = new CompoundStmt(new DeclarationStmt(new IntType(), "a"),
                new CompoundStmt(new DeclarationStmt(new IntType(), "b"),
                        new CompoundStmt(
                                new AssignStmt("a",
                                        new ArithmeticExpr(new ValueExpr(new IntValue(2)),
                                                new ArithmeticExpr(new ValueExpr(new IntValue(3)),
                                                        new ValueExpr(new IntValue(5)), "*"),
                                                "+")),
                                new CompoundStmt(
                                        new AssignStmt("b",
                                                new ArithmeticExpr(new VariableExpr("a"),
                                                        new ValueExpr(new IntValue(1)), "+")),
                                        new PrintStmt(new VariableExpr("b"))))));

        // bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStmt ex3 = new CompoundStmt(new DeclarationStmt(new BoolType(), "a"),
                new CompoundStmt(new DeclarationStmt(new IntType(), "v"),
                        new CompoundStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompoundStmt(
                                new IfStmt(new VariableExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))),
                                        new AssignStmt("v", new ValueExpr(new IntValue(3)))),
                                new PrintStmt(new VariableExpr("v"))))));

        IStmt ex4 = new CompoundStmt(new DeclarationStmt(new IntType(), "x"),
                new CompoundStmt(
                        new AssignStmt("x", new ValueExpr(new IntValue(5))),
                        new IfStmt(
                                new BoolExpr(new RelationalExpr(new VariableExpr("x"), new ValueExpr(new IntValue(3)), ">="),
                                        new RelationalExpr(new VariableExpr("x"), new ValueExpr(new IntValue(6)), "<="), "and"),
                                new PrintStmt(new ValueExpr(new StringValue("x is in [3,6]"))),
                                new PrintStmt(new ValueExpr(new StringValue("x is not in [3,6]"))))));

        IStmt ex5 = new CompoundStmt(
                new DeclarationStmt(new StringType(), "varf"),
                new CompoundStmt(
                        new AssignStmt("varf", new ValueExpr(new StringValue("test.in"))),
                        new CompoundStmt(
                                new OpenRFileStmt(new VariableExpr("varf")),
                                new CompoundStmt(
                                        new DeclarationStmt(new IntType(), "varc"),
                                        new CompoundStmt(
                                                new ReadFileStmt(new VariableExpr("varf"), "varc"),
                                                new CompoundStmt(
                                                        new PrintlnStmt(new VariableExpr("varc")),
                                                        new CompoundStmt(
                                                                new ReadFileStmt(new VariableExpr("varf"), "varc"),
                                                                new CompoundStmt(
                                                                        new PrintlnStmt(new VariableExpr("varc")),
                                                                        new CloseRFileStmt(new VariableExpr("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex6 = new CompoundStmt(
                new DeclarationStmt(new IntType(), "x"),
                new CompoundStmt(
                        new WhileStmt(new RelationalExpr(new VariableExpr("x"), new ValueExpr(new IntValue(6)), "<"),
                                new CompoundStmt(
                                        new PrintlnStmt(new ValueExpr(new StringValue("this is a loop :D"))),
                                        new AssignStmt("x", new ArithmeticExpr(new VariableExpr("x"), new ValueExpr(new IntValue(1)), "+"))
                                )),

                        new NopStmt()
                )

        );


        IStmt ex7 = new CompoundStmt(
                new DeclarationStmt(new ReferenceType(new IntType()), "v"),
                new CompoundStmt(
                        new HeapAllocStmt("v", new ValueExpr(new IntValue(20))),
                        new CompoundStmt(
                                new DeclarationStmt(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStmt(
                                        new HeapAllocStmt("a", new VariableExpr("v")),
                                        new CompoundStmt(
                                                new DeclarationStmt(new ReferenceType(new ReferenceType(new ReferenceType(new IntType()))), "z"),
                                                new CompoundStmt(
                                                        new HeapAllocStmt("z", new VariableExpr("a")),
                                                        new CompoundStmt(
                                                                new PrintlnStmt(new VariableExpr("v")),
                                                                new CompoundStmt(
                                                                        new PrintlnStmt(new VariableExpr("a")),
                                                                        new PrintlnStmt(new VariableExpr("z"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex8 = new CompoundStmt(
                new DeclarationStmt(new ReferenceType(new IntType()), "v"),
                new CompoundStmt(
                        new HeapAllocStmt("v", new ValueExpr(new IntValue(20))),
                        new CompoundStmt(
                                new DeclarationStmt(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStmt(
                                        new HeapAllocStmt("a", new VariableExpr("v")),
                                        new CompoundStmt(
                                                new PrintlnStmt(new HeapReadExpr(new VariableExpr("v"))),
                                                new PrintlnStmt(new ArithmeticExpr(new RecursiveHeapReadExpr(new VariableExpr("a")), new ValueExpr(new IntValue(5)), "+"))
                                        )
                                )
                        )
                )
        );

        IStmt ex9 = new CompoundStmt(
                new DeclarationStmt(new ReferenceType(new IntType()), "v"),
                new CompoundStmt(
                        new HeapAllocStmt("v", new ValueExpr(new IntValue(20))),
                        new CompoundStmt(
                                new PrintlnStmt(new HeapReadExpr(new VariableExpr("v"))),
                                new CompoundStmt(
                                        new HeapWriteStmt("v", new ValueExpr(new IntValue(30))),
                                        new PrintlnStmt(new ArithmeticExpr(new HeapReadExpr(new VariableExpr("v")), new ValueExpr(new IntValue(5)), "+"))
                                )
                        )
                )
        );

        IStmt ex10 = new CompoundStmt(
                new DeclarationStmt(new ReferenceType(new IntType()), "v"),
                new CompoundStmt(
                        new HeapAllocStmt("v", new ValueExpr(new IntValue(20))),
                        new CompoundStmt(
                                new DeclarationStmt(new ReferenceType(new ReferenceType(new IntType())), "a"),
                                new CompoundStmt(
                                        new HeapAllocStmt("a", new VariableExpr("v")),
                                        new CompoundStmt(
                                                new HeapAllocStmt("v", new ValueExpr(new IntValue(30))),
                                                new CompoundStmt(
                                                        new PrintlnStmt(new HeapReadExpr(new HeapReadExpr(new VariableExpr("a")))),
                                                        new HeapWriteStmt("a", new VariableExpr("v"))
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex11_fork_stmt = new CompoundStmt(
                new HeapWriteStmt("a", new ValueExpr(new IntValue(30))),
                new CompoundStmt(
                        new AssignStmt("v", new ValueExpr(new IntValue(32))),
                        new CompoundStmt(
                                new PrintlnStmt(new VariableExpr("v")),
                                new PrintlnStmt(new HeapReadExpr(new VariableExpr("a")))
                        )
                ));

        IStmt ex11 = new CompoundStmt(
                new DeclarationStmt(new IntType(), "v"),
                new CompoundStmt(
                        new DeclarationStmt(new ReferenceType(new IntType()), "a"),
                        new CompoundStmt(
                                new AssignStmt("v", new ValueExpr(new IntValue(10))),
                                new CompoundStmt(
                                        new HeapAllocStmt("a", new ValueExpr(new IntValue(22))),
                                        new CompoundStmt(
                                                new ForkStmt(ex11_fork_stmt),
                                                new CompoundStmt(
                                                        new PrintlnStmt(new VariableExpr("v")),
                                                        new PrintlnStmt(new HeapReadExpr(new VariableExpr("a")))
                                                )
                                        )
                                )
                        )
                )
        );

        IStmt ex12 = new CompoundStmt(
            new DeclarationStmt(new ReferenceType(new IntType()), "a"),
            new CompoundStmt(
                    new DeclarationStmt(new ReferenceType(new IntType()), "b"),
                    new CompoundStmt(
                            new DeclarationStmt(new IntType(), "v"),
                            new CompoundStmt(
                                    new HeapAllocStmt("a", new ValueExpr(new IntValue(0))),
                                    new CompoundStmt(
                                            new HeapAllocStmt("b", new ValueExpr(new IntValue(0))),
                                            new CompoundStmt(
                                                    new HeapWriteStmt("a", new ValueExpr(new IntValue(1))),
                                                    new CompoundStmt(
                                                            new HeapWriteStmt("b", new ValueExpr(new IntValue(2))),
                                                            new CompoundStmt(
                                                                    new ConditionalAssignStmt("v", new RelationalExpr(new HeapReadExpr(new VariableExpr("a")), new HeapReadExpr(new VariableExpr("b")), "<"), new ValueExpr(new IntValue(100)), new ValueExpr(new IntValue(200))),
                                                                    new CompoundStmt(
                                                                            new PrintlnStmt(new VariableExpr("v")),
                                                                            new CompoundStmt(
                                                                                    new ConditionalAssignStmt("v", new RelationalExpr(new ArithmeticExpr(new HeapReadExpr(new VariableExpr("b")), new ValueExpr(new IntValue(2)), "-"), new HeapReadExpr(new VariableExpr("a")), ">"), new ValueExpr(new IntValue(100)), new ValueExpr(new IntValue(200))),
                                                                                    new PrintlnStmt(new VariableExpr("v"))
                                                                                    )
                                                                    )
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            )
        );

        IStmt ex13_3 = new CompoundStmt(
                new HeapWriteStmt("v3", new ArithmeticExpr(new HeapReadExpr(new VariableExpr("v3")), new ValueExpr(new IntValue(10)), "*")),
                new CompoundStmt(
                        new PrintlnStmt(new HeapReadExpr(new VariableExpr("v3"))),
                        new CountDownStmt("cnt")
                )
        );

        IStmt ex13_2 = new CompoundStmt(
                new HeapWriteStmt("v2", new ArithmeticExpr(new HeapReadExpr(new VariableExpr("v2")), new ValueExpr(new IntValue(10)), "*")),
                new CompoundStmt(
                        new PrintlnStmt(new HeapReadExpr(new VariableExpr("v2"))),
                        new CompoundStmt(
                                new CountDownStmt("cnt"),
                                new ForkStmt(ex13_3)
                        )
                )
        );

        IStmt ex13_1 = new CompoundStmt(
                new HeapWriteStmt("v1", new ArithmeticExpr(new HeapReadExpr(new VariableExpr("v1")), new ValueExpr(new IntValue(10)), "*")),
                new CompoundStmt(
                        new PrintlnStmt(new HeapReadExpr(new VariableExpr("v1"))),
                        new CompoundStmt(
                                new CountDownStmt("cnt"),
                                new ForkStmt(ex13_2)
                        )
                )
        );



        IStmt ex13 = new CompoundStmt(
                new DeclarationStmt(new ReferenceType(new IntType()), "v1"),
                new CompoundStmt(
                        new DeclarationStmt(new ReferenceType(new IntType()), "v2"),
                        new CompoundStmt(
                                new DeclarationStmt(new ReferenceType(new IntType()), "v3"),
                                new CompoundStmt(
                                        new DeclarationStmt(new IntType(), "cnt"),
                                        new CompoundStmt(
                                                new HeapAllocStmt("v1", new ValueExpr(new IntValue(2))),
                                                new CompoundStmt(
                                                        new HeapAllocStmt("v2", new ValueExpr(new IntValue(3))),
                                                        new CompoundStmt(
                                                                new HeapAllocStmt("v3", new ValueExpr(new IntValue(4))),
                                                                new CompoundStmt(
                                                                        new NewLatchStmt("cnt", new HeapReadExpr(new VariableExpr("v2"))),
                                                                        new CompoundStmt(
                                                                                new ForkStmt(ex13_1),
                                                                                new CompoundStmt(
                                                                                        new AwaitStmt("cnt"),
                                                                                        new CompoundStmt(
                                                                                                new PrintlnStmt(new ValueExpr(new IntValue(100))),
                                                                                                new CompoundStmt(
                                                                                                        new CountDownStmt("cnt"),
                                                                                                        new PrintlnStmt(new ValueExpr(new IntValue(100)))
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        items.add(ex1);
        items.add(ex2);
        items.add(ex3);
        items.add(ex4);
        items.add(ex5);
        items.add(ex6);
        items.add(ex7);
        items.add(ex8);
        items.add(ex9);
        items.add(ex10);
        items.add(ex11);
        items.add(ex12);
        items.add(ex13);

        program_list.setItems(items);
    }

    private void createInspectorWindow(){
        try{
            // run type checker
            IStmt current_statement = (IStmt)program_list.getSelectionModel().getSelectedItem();
            IDictionary<String, IType> type_env = new MyDictionary<String, IType>();
            current_statement.typeCheck(type_env);

            // create inspector window
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("program_inspector2.fxml"));

            Scene inspector_scene = new Scene(fxmlLoader.load(), 640, 560);
            Stage stage = (Stage)run_program_button.getScene().getWindow();
            stage.setMinWidth(640);
            stage.setMinHeight(560);
            stage.setScene(inspector_scene);

            InspectorController inspector = fxmlLoader.getController();
            inspector.setStatement(current_statement);

        }catch(Exception e){
            System.out.println(e.getMessage());
            ErrorWindow error_popup = new ErrorWindow(e.getMessage());
        }
    }

    @FXML
    protected void onRunProgramButtonClick() {
        if(program_list.getSelectionModel().getSelectedItem() == null){
            System.out.println("No program selected");
            ErrorWindow err = new ErrorWindow("No program selected.");
            return;
        }

        System.out.println("Selected: " + program_list.getSelectionModel().getSelectedItem() + "\nwith index: " + program_list.getSelectionModel().getSelectedIndex());
        createInspectorWindow();
    }
}