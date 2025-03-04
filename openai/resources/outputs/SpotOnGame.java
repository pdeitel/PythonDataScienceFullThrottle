import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class SpotOnGame extends Application {

   private static final int INITIAL_LIVES = 3;
   private static final int MAX_LIVES = 7;
   private static final int SPOT_RADIUS = 50;
   private static final double MIN_SPOT_SCALE = 0.25;

   private Pane gamePane;
   private int lives = INITIAL_LIVES;
   private int level = 1;
   private int score = 0;
   private int spotsClicked = 0;

   private javafx.scene.media.AudioClip hitSound;
   private javafx.scene.media.AudioClip missSound;
   private javafx.scene.media.AudioClip disappearSound;

   private Random random = new Random();

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) {
      preloadSounds();
      gamePane = new Pane();
      gamePane.setPrefSize(800, 600);
      gamePane.setStyle("-fx-background-color: white;");
      gamePane.setOnMouseClicked(event -> handleMiss());

      Scene scene = new Scene(gamePane);
      primaryStage.setTitle("SpotOn Game");
      primaryStage.setScene(scene);
      primaryStage.show();

      createInitialSpots();
   }

   private void preloadSounds() {
      hitSound = new javafx.scene.media.AudioClip(java.nio.file.Path.of(System.getProperty("user.home"), "Documents/examples/ch19/resources/SpotOn/sounds/hit.mp3").toUri().toString());
      missSound = new javafx.scene.media.AudioClip(java.nio.file.Path.of(System.getProperty("user.home"), "Documents/examples/ch19/resources/SpotOn/sounds/miss.mp3").toUri().toString());
      disappearSound = new javafx.scene.media.AudioClip(java.nio.file.Path.of(System.getProperty("user.home"), "Documents/examples/ch19/resources/SpotOn/sounds/disappear.mp3").toUri().toString());
   }

   private void createInitialSpots() {
      Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> createSpot()));
      timeline.setCycleCount(5);
      timeline.play();
   }

   private void createSpot() {
      Spot spot = new Spot();
      spot.setOnMouseClicked(event -> handleSpotClick(spot));
      gamePane.getChildren().add(spot);
      spot.animate();
   }

   private void handleSpotClick(Spot spot) {
      gamePane.getChildren().remove(spot);
      score += 10 * level;
      spotsClicked++;

      hitSound.play();

      if (spotsClicked == 10) {
         level++;
         spotsClicked = 0;
         if (lives < MAX_LIVES) {
            lives++;
         }
      }

      createSpot();
   }

   private void handleMiss() {
      score -= 15 * level;
      missSound.play();
   }

   private void loseLife() {
      lives--;
      disappearSound.play();
      if (lives <= 0) {
         endGame();
      }
   }

   private void endGame() {
      javafx.scene.text.Text gameOverText = new javafx.scene.text.Text("GAME OVER");
      gameOverText.setFill(javafx.scene.paint.Color.RED);
      gameOverText.setStyle("-fx-font-size: 48px; -fx-font-weight: bold;");
      gameOverText.setX(gamePane.getWidth() / 2 - 150);
      gameOverText.setY(gamePane.getHeight() / 2);
      gamePane.getChildren().add(gameOverText);

      gamePane.setOnMouseClicked(event -> System.exit(0));
   }

   private class Spot extends Circle {
      private ParallelTransition animation;

      public Spot() {
         super(SPOT_RADIUS);
         setRandomPosition();
         setFill(new ImagePattern(new Image(random.nextBoolean() ? java.nio.file.Path.of(System.getProperty("user.home"), "Documents/examples/ch19/resources/SpotOn/images/orange_spot.png").toUri().toString() : java.nio.file.Path.of(System.getProperty("user.home"), "Documents/examples/ch19/resources/SpotOn/images/blue_spot.png").toUri().toString())));

         PathTransition pathTransition = createPathTransition();
         ScaleTransition scaleTransition = createScaleTransition();
         animation = new ParallelTransition(this, pathTransition, scaleTransition);
         animation.setOnFinished(event -> handleSpotMiss());
      }

      public void animate() {
         animation.play();
      }

      private void setRandomPosition() {
         double x = random.nextDouble() * (gamePane.getWidth() - SPOT_RADIUS * 2) + SPOT_RADIUS;
         double y = random.nextDouble() * (gamePane.getHeight() - SPOT_RADIUS * 2) + SPOT_RADIUS;
         setCenterX(x);
         setCenterY(y);
      }

      private PathTransition createPathTransition() {
         double endX = random.nextDouble() * (gamePane.getWidth() - SPOT_RADIUS * 2) + SPOT_RADIUS;
         double endY = random.nextDouble() * (gamePane.getHeight() - SPOT_RADIUS * 2) + SPOT_RADIUS;

         PathTransition pathTransition = new PathTransition(Duration.seconds(3 - level * 0.2), this);
         pathTransition.setInterpolator(Interpolator.LINEAR);
         var path = new javafx.scene.shape.Path();
         path.getElements().add(new javafx.scene.shape.MoveTo(getCenterX(), getCenterY()));
         path.getElements().add(new javafx.scene.shape.LineTo(endX, endY));
         pathTransition.setPath(path);
         return pathTransition;
      }

      private ScaleTransition createScaleTransition() {
         ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(3 - level * 0.2), this);
         scaleTransition.setToX(MIN_SPOT_SCALE);
         scaleTransition.setToY(MIN_SPOT_SCALE);

         return scaleTransition;
      }

      private void handleSpotMiss() {
         gamePane.getChildren().remove(this);
         loseLife();
         createSpot();
      }
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
