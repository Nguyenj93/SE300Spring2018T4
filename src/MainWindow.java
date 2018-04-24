/**
 * @author Adam Babbit
 * @date 04/02/2018
 * 
 * @version 3.2
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MainWindow extends Application{
	
	// Instantiates the main window for the program 
	private Stage primaryStage;
	private Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
	private AcademicPlan academicPlan;
	private MasterCourseList m = MasterCourseList.get();
	
	// Standard start program required to use JavaFX
	public void start(Stage stage)
	{
		academicPlan = new AcademicPlan();
		
		//academicPlan.getPersonalPlan().getSemesters().addListener((ListChangeListener<Semester>) e -> System.out.println("Chage Made"));
		academicPlan.getPersonalPlan().addSemester(new Semester(Term.FALL, 2018));
		academicPlan.getPersonalPlan().getSemesterList().get(0).addCourse((new Course(new BaseCourse(DepartmentID.EGR, 101, "Intro to engineering", 2 , null, null))));
		
		primaryStage = stage;
		
		primaryStage.setTitle("Degree Plan");
		
		// Setting the size of the window to the size of the screen, maximizing the window.
		primaryStage.setX(screenSize.getMinX());
		primaryStage.setY(screenSize.getMinY());
		primaryStage.setHeight(screenSize.getHeight());
		primaryStage.setWidth(screenSize.getWidth());

		//Limiting the minimum size of the window as to not affect the UI display
		primaryStage.setMinHeight(300);
		primaryStage.setMinWidth(500);
		
		//Creating all the notes 
		primaryStage.setScene(setupScene());
		
		primaryStage.show();
		
	}
	
	/**
	 * Sets up the first scene of the program including the Semesters, Classes, Class Pool, and Toolbars
	 * 
	 * @return scene		The Scene in which the other scenes are stored, This should be added directly to the Stage
	 */
	
	private Scene setupScene()
	{
		BorderPane highestPane = new BorderPane(); // Highest level container for the window
		BorderPane topBorderPane = new BorderPane(); // placed in the tap border section of the highestPane
		//BorderPane listViewPanes = new BorderPane();
		Scene scene = new Scene(highestPane);
		GridPane semesterGrid = setUpSemesterPane();
		ListView<BaseCourse> list = showCoursePool(); // the listview of all the courses
		
		// forcing the course pool to be right above the list of semeste
		
		BorderPane.setAlignment(list,Pos.BOTTOM_RIGHT);
		topBorderPane.setTop(makeMenu());
		highestPane.setRight(list);
		highestPane.setTop(topBorderPane);
		highestPane.setCenter(semesterGrid);
		
		return scene;
	}
	
	private ListView<BaseCourse> showCoursePool()
	{
		ListView<BaseCourse> coursePool = new ListView<BaseCourse>();
		
		coursePool.setMaxHeight(screenSize.getHeight() * 0.15);
		coursePool.setPrefWidth(screenSize.getWidth() * .5);
		
		coursePool.setOrientation(Orientation.HORIZONTAL);

		coursePool.setCellFactory(new Callback<ListView<BaseCourse>, ListCell<BaseCourse>>() 
		{	
			public ListCell<BaseCourse> call(ListView<BaseCourse> param)
			{
				ListCell<BaseCourse> cell = new ListCell<BaseCourse>()
				{
						public void updateItem(BaseCourse item, boolean empty)
						{
							super.updateItem(item, empty);
							if (!empty)
							{
								setText(item.toStringDetails());
								
								ContextMenu rightClickMenu = new ContextMenu();
								MenuItem addToSemesterOption = new MenuItem();
								
								addToSemesterOption.setOnAction(e -> {System.out.println(item.toString());});
								
								rightClickMenu.getItems().add(addToSemesterOption);
							}
						}
					};
					
					ContextMenu rightClickMenu = new ContextMenu();
					MenuItem addToSemesterOption = new MenuItem("Add to Semester");
					
					addToSemesterOption.setOnAction(e -> {
						GridPane grid = new GridPane();
						Scene scene = new Scene(grid);
						Stage stage = new Stage();
						ComboBox<Semester> semesters = new ComboBox<Semester>();
				        Label label1 = new Label("Select Semester:");
				        Button button1 = new Button("Add Semester");
				        
				        semesters.setItems(academicPlan.getPersonalPlan().getSemesters());
				        
				        button1.setOnAction(active -> {
				        	PersonalPlan p = academicPlan.getPersonalPlan();
				        	p.getSemesters().get(p.getSemesterIndex(semesters.getValue())).addCourse(new Course(cell.getItem()));
				        	
				        System.out.println(academicPlan.getPersonalPlan().getSemesterIndex(semesters.getValue()));
				        	stage.close();
				        	start(new Stage());
				        });
				        
				        grid.add(semesters, 1, 0);
				        grid.add(label1, 0, 0);
				        grid.add(button1, 0, 1);
				        
				        stage.setTitle("Add Semester");
				        stage.setScene(scene);
				        stage.show();
						
					});
					
					rightClickMenu.getItems().add(addToSemesterOption);

					cell.setOnContextMenuRequested(e -> {rightClickMenu.show(cell, e.getSceneX(), e.getSceneY());});
					
					return cell;
				}
			});
		
		
		coursePool.setItems(m.getCourseList());
		
		return coursePool;
	}
	
	private GridPane setUpSemesterPane()
	{
		PersonalPlan p = academicPlan.getPersonalPlan();
		GridPane semesterGrid = new GridPane();
		
		for (int i = 0; i < p.getSemesterList().size(); i++)
		{
			ListView<BaseCourse> semesterPane = new ListView<BaseCourse>();
			Semester sTemp = p.getSemesterList().get(i);
			ObservableList<BaseCourse> test = setUpCourseList(sTemp);
			
			semesterPane.setItems(test);
			semesterGrid.add(semesterPane, i, 0);
		}

		semesterGrid.prefHeightProperty().bind(primaryStage.heightProperty().multiply(.6666));
		semesterGrid.prefWidthProperty().bind(primaryStage.widthProperty());

		return semesterGrid;
	}
	
	
	private ObservableList<BaseCourse> setUpCourseList(Semester activeSemester)
	{
		ObservableList<BaseCourse> listOfCourses = FXCollections.observableArrayList();
		
		//listOfCourses.add(activeSemester.getTerm().toString() + " " + activeSemester.getYear());
		
		for (int k = 0; k < activeSemester.getCourses().size(); k ++)
		{	
			Course activeCourse = activeSemester.getCourses().get(k);
			listOfCourses.add(activeCourse.getBaseCourse());	
		}
		
		return listOfCourses;
	}
	
	/**
	 * Creates the frame for the new menu
	 * 
	 * @return toolbar	The menu bar to be added in the window.
	 */
	private MenuBar makeMenu()
	{
		MenuBar toolBar = new ApplicationMenu(academicPlan).getMenuBar();
		MenuItem newCourse = new MenuItem("Display Master Course List");
		Menu toAdd = new Menu("Open List");
		
		toAdd.getItems().add(newCourse);
		
		newCourse.setOnAction(e -> {
			MasterCourseListWindow.startAsChild(primaryStage);});
		
		toolBar.getMenus().add(toAdd);
		
		return toolBar;
	}
	
	public static void main(String[] args)
	{
		launch();
	}
}
