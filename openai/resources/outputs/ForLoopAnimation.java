import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ForLoopAnimation extends Application {
   private int counter;
   private Label counterValueLabel;
   private Label explanationLabel;
   private Label outputLabel;
   private SequentialTransition animationSequence;
   private Rectangle highlightRect;

   @Override
   public void start(Stage primaryStage) {
      Pane root = new Pane();

      // Set up labels and buttons
      Label loopTextLabel = new Label("for (int counter = 1; counter <= 3; ++counter) {\n    System.out.printf(\"%d  \" , counter);\n}");
      loopTextLabel.setFont(javafx.scene.text.Font.font("Courier New", 36));
      loopTextLabel.setLayoutX(50);
      loopTextLabel.setLayoutY(50);
      loopTextLabel.setPrefWidth(1200);
      loopTextLabel.setPrefHeight(200);

      counterValueLabel = new Label("counter = 1");
      counterValueLabel.setLayoutX(50);
      counterValueLabel.setLayoutY(400);

      explanationLabel = new Label("Initializing control variable counter to 1");
      explanationLabel.setLayoutX(50);
      explanationLabel.setLayoutY(500);

      outputLabel = new Label("Output: ");
      outputLabel.setLayoutX(50);
      outputLabel.setLayoutY(600);

      Button playPauseButton = new Button("Play/Pause");
      playPauseButton.setLayoutX(300);
      playPauseButton.setLayoutY(1000);

      // Create transparent highlight rectangle
      highlightRect = new Rectangle();
      highlightRect.setFill(Color.TRANSPARENT);
      highlightRect.setStroke(Color.RED);
      highlightRect.setStrokeWidth(2);
      highlightRect.setOpacity(0.6);

      root.getChildren().addAll(loopTextLabel, counterValueLabel, explanationLabel, outputLabel, highlightRect, playPauseButton);

      Scene scene = new Scene(root, 1600, 1200);
      primaryStage.setScene(scene);
      primaryStage.setTitle("JavaFX Animation - For Loop Explanation");
      primaryStage.show();

      setupAnimation(loopTextLabel);
      playPauseButton.setOnAction(e -> {
         if (animationSequence.getStatus() == javafx.animation.Animation.Status.RUNNING) {
            animationSequence.pause();
         } else {
            animationSequence.play();
         }
      });
   }

   private void setupAnimation(Label loopTextLabel) {
      animationSequence = new SequentialTransition();
      double baseX = loopTextLabel.getLayoutX();
      double baseY = loopTextLabel.getLayoutY();
      double lineHeight = 40;  // Adjust based on font size and spacing
      double charWidth = 20;   // Adjust based on font size and fixed-width font

      // Step 1: Highlight "int counter = 1"
      PauseTransition highlightInit = new PauseTransition(Duration.seconds(2));
      highlightInit.setOnFinished(e -> {
         highlightRect.setLayoutX(baseX);
         highlightRect.setLayoutY(baseY);
         highlightRect.setWidth(11 * charWidth);  // "int counter = 1"
         highlightRect.setHeight(lineHeight);
         explanationLabel.setText("Initializing control variable counter to 1");
         counterValueLabel.setText("counter = 1");
         counter = 1;
      });
      animationSequence.getChildren().add(highlightInit);

      // Step 2: Highlight "counter <= 3"
      PauseTransition highlightCondition = new PauseTransition(Duration.seconds(2));
      highlightCondition.setOnFinished(e -> {
         highlightRect.setLayoutX(baseX + 14 * charWidth);
         highlightRect.setLayoutY(baseY);
         highlightRect.setWidth(11 * charWidth);  // "counter <= 3"
         highlightRect.setHeight(lineHeight);
         explanationLabel.setText("Checking if counter (" + counter + ") <= 3: " + (counter <= 3 ? "true" : "false"));
      });
      animationSequence.getChildren().add(highlightCondition);

      // Step 3: Highlight body "System.out.println("%d", counter);"
      PauseTransition highlightBody = new PauseTransition(Duration.seconds(2));
      highlightBody.setOnFinished(e -> {
         highlightRect.setLayoutX(baseX);
         highlightRect.setLayoutY(baseY + lineHeight);
         highlightRect.setWidth(38 * charWidth);  // "System.out.println("%d", counter);"
         highlightRect.setHeight(lineHeight);
         explanationLabel.setText("Executing body: printing counter = " + counter);
         outputLabel.setText(outputLabel.getText() + counter + "  ");
      });
      animationSequence.getChildren().add(highlightBody);

      // Step 4: Highlight "++counter"
      PauseTransition highlightIncrement = new PauseTransition(Duration.seconds(2));
      highlightIncrement.setOnFinished(e -> {
         highlightRect.setLayoutX(baseX + 28 * charWidth);
         highlightRect.setLayoutY(baseY);
         highlightRect.setWidth(8 * charWidth);  // "++counter"
         highlightRect.setHeight(lineHeight);
         counter++;
         explanationLabel.setText("Incrementing counter to " + counter);
         counterValueLabel.setText("counter = " + counter);
      });
      animationSequence.getChildren().add(highlightIncrement);

      // Repeat Steps 2-4 until the condition becomes false
      Timeline loopTimeline = new Timeline(
         new KeyFrame(Duration.seconds(2), e -> {
            // Highlight condition
            highlightRect.setLayoutX(baseX + 14 * charWidth);
            highlightRect.setLayoutY(baseY);
            highlightRect.setWidth(11 * charWidth);  // "counter <= 3"
            highlightRect.setHeight(lineHeight);
            if (counter <= 3) {
               explanationLabel.setText("Checking if counter (" + counter + ") <= 3: true");
            } else {
               explanationLabel.setText("Checking if counter (" + counter + ") <= 3: false. Loop terminates.");
            }
         }),
         new KeyFrame(Duration.seconds(4), e -> {
            if (counter <= 3) {
               // Highlight body
               highlightRect.setLayoutX(baseX);
               highlightRect.setLayoutY(baseY + lineHeight);
               highlightRect.setWidth(38 * charWidth);  // "System.out.println("%d", counter);"
               highlightRect.setHeight(lineHeight);
               explanationLabel.setText("Executing body: printing counter = " + counter);
               outputLabel.setText(outputLabel.getText() + counter + "  ");
            }
         }),
         new KeyFrame(Duration.seconds(6), e -> {
            if (counter <= 3) {
               // Highlight increment
               highlightRect.setLayoutX(baseX + 28 * charWidth);
               highlightRect.setLayoutY(baseY);
               highlightRect.setWidth(8 * charWidth);  // "++counter"
               highlightRect.setHeight(lineHeight);
               counter++;
               explanationLabel.setText("Incrementing counter to " + counter);
               counterValueLabel.setText("counter = " + counter);
            }
         })
      );
      loopTimeline.setCycleCount(3);
      animationSequence.getChildren().add(loopTimeline);
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
