package main;

import model.statements.*;
import model.types.*;
import model.values.*;
import model.expressions.*;
import model.ProgramState;
import model.adt.IDictionary;
import model.adt.MyDictionary;
import repository.*;
import controller.*;
import view.*;
import view.commands.*;
public class Main {
	public static void typeCheckStatements(IStmt stmt) {
		try {
			IDictionary<String, IType> type_env = new MyDictionary<String, IType>();
			stmt.typeCheck(type_env);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String args[]) {
		Tests a = new Tests();
		a.testValues();

		// assignments 1-3
		TextMenu a23 = new SubMenu();	
			// int v;v=2;Print(v)
			IStmt ex1 = new CompoundStmt(new DeclarationStmt(new IntType(), "v"), new CompoundStmt(
					new AssignStmt("v", new ValueExpr(new IntValue(2))), new PrintStmt(new VariableExpr("v"))));

			typeCheckStatements(ex1);
			ProgramState prg1 = new ProgramState(ex1);
			IRepository repo1 = new MemoryRepository("log1.txt");
			repo1.add(prg1);
			ProgramStateController ctrl1 = new ProgramStateController(repo1);

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
			typeCheckStatements(ex2);
			ProgramState prg2 = new ProgramState(ex2);
			IRepository repo2 = new MemoryRepository("log2.txt");
			repo2.add(prg2);
			ProgramStateController ctrl2 = new ProgramStateController(repo2);

			// bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
			IStmt ex3 = new CompoundStmt(new DeclarationStmt(new BoolType(), "a"),
					new CompoundStmt(new DeclarationStmt(new IntType(), "v"),
							new CompoundStmt(new AssignStmt("a", new ValueExpr(new BoolValue(true))), new CompoundStmt(
									new IfStmt(new VariableExpr("a"), new AssignStmt("v", new ValueExpr(new IntValue(2))),
											new AssignStmt("v", new ValueExpr(new IntValue(3)))),
									new PrintStmt(new VariableExpr("v"))))));

			typeCheckStatements(ex3);
			ProgramState prg3 = new ProgramState(ex3);
			IRepository repo3 = new MemoryRepository("log3.txt");
			repo3.add(prg3);
			ProgramStateController ctrl3 = new ProgramStateController(repo3);

			// int x; x = 7; if(x >= 3 && x <= 6) print(x is in [3,6]) else print(x is not in [3,6])
			IStmt ex4 = new CompoundStmt(new DeclarationStmt(new IntType(), "x"),
					new CompoundStmt(
							new AssignStmt("x", new ValueExpr(new IntValue(5))),
							new IfStmt(
									new BoolExpr(new RelationalExpr(new VariableExpr("x"), new ValueExpr(new IntValue(3)), ">="),
									new RelationalExpr(new VariableExpr("x"), new ValueExpr(new IntValue(6)), "<="), "and"),
							new PrintStmt(new ValueExpr(new StringValue("x is in [3,6]"))),
							new PrintStmt(new ValueExpr(new StringValue("x is not in [3,6]"))))));
			typeCheckStatements(ex4);
			ProgramState prg4 = new ProgramState(ex4);
			IRepository repo4 = new MemoryRepository("log4.txt");
			repo4.add(prg4);
			ProgramStateController ctrl4 = new ProgramStateController(repo4);
			
			// string varf; varf="test.in"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)
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
			typeCheckStatements(ex5);
			ProgramState prg5 = new ProgramState(ex5);
			IRepository repo5 = new MemoryRepository("log5.txt");
			repo5.add(prg5);
			ProgramStateController ctrl5 = new ProgramStateController(repo5);
			
			// int x; while(x < 6){ println("this is a loop :D"),}
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
					
			typeCheckStatements(ex6);
			ProgramState prg6 = new ProgramState(ex6);
			IRepository repo6 = new MemoryRepository("log6.txt");
			repo6.add(prg6);
			ProgramStateController ctrl6 = new ProgramStateController(repo6);
			
			a23.addCommand(new ExitSubMenuCommand("0", "Exit"));
			a23.addCommand(new RunAllSteps("1", ex1.toString(), ctrl1));
			a23.addCommand(new RunAllSteps("2", ex2.toString(), ctrl2));
			a23.addCommand(new RunAllSteps("3", ex3.toString(), ctrl3));
			a23.addCommand(new RunAllSteps("4", ex4.toString(), ctrl4));
			a23.addCommand(new RunAllSteps("5", ex5.toString(), ctrl5));

		
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

		typeCheckStatements(ex7);
		ProgramState prg7 = new ProgramState(ex7);
		IRepository repo7 = new MemoryRepository("log7.txt");
		repo7.add(prg7);
		ProgramStateController ctrl7 = new ProgramStateController(repo7);
		
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
//											new PrintlnStmt(new ArithmeticExpr(new HeapReadExpr(new HeapReadExpr(new VariableExpr("a"))), new ValueExpr(new IntValue(5)), "+"))
										)
								)							
						)	
				)
			);
		
		typeCheckStatements(ex8);
		ProgramState prg8 = new ProgramState(ex8);
		IRepository repo8 = new MemoryRepository("log8.txt");
		repo8.add(prg8);
		ProgramStateController ctrl8 = new ProgramStateController(repo8);

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
		
		typeCheckStatements(ex9);
		ProgramState prg9 = new ProgramState(ex9);
		IRepository repo9 = new MemoryRepository("log9.txt");
		repo9.add(prg9);
		ProgramStateController ctrl9 = new ProgramStateController(repo9);
		
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
		
		typeCheckStatements(ex10);
		ProgramState prg10 = new ProgramState(ex10);
		IRepository repo10 = new MemoryRepository("log10.txt");
		repo10.add(prg10);
		ProgramStateController ctrl10 = new ProgramStateController(repo10);
		TextMenu main_menu = new TextMenu();
		
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
		typeCheckStatements(ex11);
		ProgramState prg11 = new ProgramState(ex11);
		IRepository repo11 = new MemoryRepository("log11.txt");
		repo11.add(prg11);
		ProgramStateController ctrl11 = new ProgramStateController(repo11);

		main_menu.addCommand(new ExitCommand("0", "Exit"));
		main_menu.addCommand(new SubMenuCommand("1", "Assignments 2 & 3", a23));
		main_menu.addCommand(new RunAllSteps("2", ex6.toString(), ctrl6));
		main_menu.addCommand(new RunAllSteps("3", ex7.toString(), ctrl7));
		main_menu.addCommand(new RunAllSteps("4", ex8.toString(), ctrl8));
		main_menu.addCommand(new RunAllSteps("5", ex9.toString(), ctrl9));
		main_menu.addCommand(new RunAllSteps("6", ex10.toString(), ctrl10));
		main_menu.addCommand(new RunAllSteps("7", ex11.toString(), ctrl11));
		main_menu.run();
	}
}
