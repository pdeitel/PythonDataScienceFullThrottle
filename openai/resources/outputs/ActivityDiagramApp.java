import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ActivityDiagramApp extends Application {
   @Override
   public void start(Stage stage) {
      Pane pane = new Pane();
      pane.setPrefSize(1000, 800);  // Set larger bounds for Pane

      // Load SVG content from the file
      java.nio.file.Path svgFilePath = Paths.get("/mnt/data/ForLoopUMLActivityDiagram.svg");
      try {
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         Document document = builder.parse(Files.newInputStream(svgFilePath));
         document.getDocumentElement().normalize();

         // Extract and render lines
         NodeList lineList = document.getElementsByTagName("line");
         for (int i = 0; i < lineList.getLength(); i++) {
            try {
               double x1 = Double.parseDouble(lineList.item(i).getAttributes().getNamedItem("x1").getNodeValue());
               double y1 = Double.parseDouble(lineList.item(i).getAttributes().getNamedItem("y1").getNodeValue());
               double x2 = Double.parseDouble(lineList.item(i).getAttributes().getNamedItem("x2").getNodeValue());
               double y2 = Double.parseDouble(lineList.item(i).getAttributes().getNamedItem("y2").getNodeValue());

               Line line = new Line(x1, y1, x2, y2);
               line.setStrokeWidth(2);
               line.setStroke(Color.BLACK);
               pane.getChildren().add(line);
            } catch (Exception e) {
               System.out.println("Error parsing line element: " + e.getMessage());
            }
         }

         // Extract and render rectangles
         NodeList rectList = document.getElementsByTagName("rect");
         for (int i = 0; i < rectList.getLength(); i++) {
            try {
               double x = Double.parseDouble(rectList.item(i).getAttributes().getNamedItem("x").getNodeValue());
               double y = Double.parseDouble(rectList.item(i).getAttributes().getNamedItem("y").getNodeValue());
               double width = Double.parseDouble(rectList.item(i).getAttributes().getNamedItem("width").getNodeValue());
               double height = Double.parseDouble(rectList.item(i).getAttributes().getNamedItem("height").getNodeValue());

               Rectangle rectangle = new Rectangle(x, y, width, height);
               rectangle.setArcWidth(10);
               rectangle.setArcHeight(10);
               rectangle.setFill(Color.LIGHTBLUE);
               rectangle.setStroke(Color.BLACK);
               pane.getChildren().add(rectangle);
            } catch (Exception e) {
               System.out.println("Error parsing rectangle element: " + e.getMessage());
            }
         }

         // Extract and render circles
         NodeList circleList = document.getElementsByTagName("circle");
         for (int i = 0; i < circleList.getLength(); i++) {
            try {
               double cx = Double.parseDouble(circleList.item(i).getAttributes().getNamedItem("cx").getNodeValue());
               double cy = Double.parseDouble(circleList.item(i).getAttributes().getNamedItem("cy").getNodeValue());
               double radius = Double.parseDouble(circleList.item(i).getAttributes().getNamedItem("r").getNodeValue());

               Circle circle = new Circle(cx, cy, radius);
               circle.setFill(Color.LIGHTGREEN);
               circle.setStroke(Color.BLACK);
               pane.getChildren().add(circle);
            } catch (Exception e) {
               System.out.println("Error parsing circle element: " + e.getMessage());
            }
         }

         // Extract and render paths
         NodeList pathList = document.getElementsByTagName("path");
         for (int i = 0; i < pathList.getLength(); i++) {
            try {
               String pathData = pathList.item(i).getAttributes().getNamedItem("d").getNodeValue();

               SVGPath svgPath = new SVGPath();
               svgPath.setContent(pathData);
               svgPath.setFill(Color.TRANSPARENT);
               svgPath.setStroke(Color.BLACK);
               pane.getChildren().add(svgPath);
            } catch (Exception e) {
               System.out.println("Error parsing path element: " + e.getMessage());
            }
         }

      } catch (Exception e) {
         System.out.println("Error loading SVG: " + e.getMessage());
      }

      // Set up the scene
      Scene scene = new Scene(pane, 1000, 800);
      stage.setTitle("Activity Diagram Exact Recreation with JavaFX 23");
      stage.setScene(scene);
      stage.show();
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
