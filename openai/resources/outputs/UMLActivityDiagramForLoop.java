import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UMLActivityDiagramForLoop extends Application {
    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        // Initial Node - Start
        Circle startCircle = new Circle(50, 50, 10);
        startCircle.setFill(Color.BLACK);
        pane.getChildren().add(startCircle);

        // Initialize control variable node
        Rectangle initializeRect = new Rectangle(100, 30);
        initializeRect.setX(80);
        initializeRect.setY(30);
        initializeRect.setArcWidth(20);
        initializeRect.setArcHeight(20);
        initializeRect.setFill(Color.LIGHTBLUE);
        Text initializeText = new Text(90, 50, "Initialize\ncontrol variable\nint count = 1");
        pane.getChildren().addAll(initializeRect, initializeText);

        // Decision diamond - Loop condition
        Polygon decisionDiamond = new Polygon();
        decisionDiamond.getPoints().addAll(200.0, 60.0, 220.0, 90.0, 200.0, 120.0, 180.0, 90.0);
        decisionDiamond.setFill(Color.WHITE);
        decisionDiamond.setStroke(Color.BLACK);
        Text decisionText = new Text(170, 95, "count <= 3");
        pane.getChildren().addAll(decisionDiamond, decisionText);

        // Display control value node
        Rectangle displayRect = new Rectangle(100, 30);
        displayRect.setX(260);
        displayRect.setY(70);
        displayRect.setArcWidth(20);
        displayRect.setArcHeight(20);
        displayRect.setFill(Color.LIGHTBLUE);
        Text displayText = new Text(270, 90, "Display\ncounter value\nSystem.out.printf");
        pane.getChildren().addAll(displayRect, displayText);

        // Increment control variable node
        Rectangle incrementRect = new Rectangle(100, 30);
        incrementRect.setX(260);
        incrementRect.setY(150);
        incrementRect.setArcWidth(20);
        incrementRect.setArcHeight(20);
        incrementRect.setFill(Color.LIGHTBLUE);
        Text incrementText = new Text(270, 170, "Increment\ncontrol variable\n++count");
        pane.getChildren().addAll(incrementRect, incrementText);

        // Final Node - End
        Circle endCircle = new Circle(220, 300, 10);
        endCircle.setFill(Color.BLACK);
        pane.getChildren().add(endCircle);

        // Arrows
        Line line1 = new Line(60, 50, 80, 50); // From start to initialize
        Line line2 = new Line(180, 90, 200, 90); // From initialize to decision
        Line line3 = new Line(220, 90, 260, 90); // From decision to display
        Line line4 = new Line(310, 90, 310, 150); // From display to increment
        Line line5 = new Line(310, 165, 310, 90); // Loop back from increment to decision
        Line line6 = new Line(220, 120, 220, 300); // From decision to end (loop termination)
        pane.getChildren().addAll(line1, line2, line3, line4, line5, line6);

        // Set the scene
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("UML Activity Diagram for Loop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

/**************************************************************************
 * (C) Copyright 1992-2025 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
