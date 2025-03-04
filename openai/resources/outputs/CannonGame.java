import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CannonGame extends Application {
    private final String path = java.nio.file.Path.of(System.getProperty("user.home"), "Documents", "examples", "ch19", "resources", "Cannon").toString();

    private Pane root;
    private int shotsFired = 0;
    private double timeRemaining = 10.0;
    private List<Target> targets = new ArrayList<>();
    private Blocker blocker;
    private Timeline timer;
    private final AudioClip cannonFireSound = new AudioClip(java.nio.file.Path.of(path, "cannon_fire.wav").toUri().toString());
    private final AudioClip targetHitSound = new AudioClip(java.nio.file.Path.of(path, "target_hit.wav").toUri().toString());
    private final AudioClip blockerHitSound = new AudioClip(java.nio.file.Path.of(path, "blocker_hit.wav").toUri().toString());
    private boolean cannonBallInFlight = false;
    private Label timerLabel;

    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        Scene scene = new Scene(root, 1024, 768);

        // Timer label setup
        timerLabel = new Label("Time remaining: " + String.format("%.1f", timeRemaining) + " seconds");
        timerLabel.setStyle("-fx-font-size: 24px;"); // Increase timer label font size
        timerLabel.setLayoutX(20);
        timerLabel.setLayoutY(20);
        root.getChildren().add(timerLabel);

        Cannon cannon = new Cannon(0, 384); // Adjusted position to place the cannon at the far left edge
        root.getChildren().addAll(cannon.getShapes());

        // Targets setup
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            Target target = new Target(600 + (i * 40), 100);
            targets.add(target);
            root.getChildren().add(target.getShape());
            target.startMovement(random.nextBoolean(), random.nextDouble() * 1.5 + 1.0); // Randomize direction and speed
        }

        // Blocker setup
        blocker = new Blocker(450, 200); // Move blocker farther to the right
        root.getChildren().add(blocker.getShape());
        blocker.startMovement(random.nextBoolean(), random.nextDouble() * 1.5 + 1.0);

        // Timer setup
        timer = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            timeRemaining = Math.max(0.0, timeRemaining - 0.1); // Ensure time does not go below 0
            timerLabel.setText("Time remaining: " + String.format("%.1f", timeRemaining) + " seconds");
            if (timeRemaining <= 0) {
                Platform.runLater(() -> endGame(false));
            }
        }));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

        // Mouse click listener for firing cannon
        scene.setOnMouseClicked(e -> {
            if (!cannonBallInFlight) {
                shotsFired++;
                CannonBall cannonBall = cannon.fire(e.getX(), e.getY());
                cannonFireSound.play();
                root.getChildren().add(cannonBall.getShape());
                cannonBall.startMovement(targets, blocker);
                cannonBallInFlight = true;
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("Cannon Game");
        primaryStage.show();
    }

    private void endGame(boolean won) {
        timer.stop();
        stopAllAnimations();
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText(won ? "You won! Shots fired: " + shotsFired : "You lost! Shots fired: " + shotsFired);
            alert.showAndWait();
        });
    }

    private void stopAllAnimations() {
        // Stop all targets and blocker animations
        for (Target target : targets) {
            target.stopMovement();
        }
        if (blocker != null) {
            blocker.stopMovement();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Cannon Class
    class Cannon {
        private Rectangle barrel;
        private Arc base;

        public Cannon(double x, double y) {
            // Create the base (semicircle)
            base = new Arc(x, y, 60, 60, -90, 180); // Flipped semicircle so the straight edge is on the left side
            base.setType(ArcType.ROUND);
            base.setFill(Color.BLACK);

            // Create the barrel
            barrel = new Rectangle(x, y - 15, 120, 30); // Adjusted position to align with the semicircle base, ensuring it starts from the center left of the semicircle
            barrel.setFill(Color.BLACK);
        }

        public List<Shape> getShapes() {
            List<Shape> shapes = new ArrayList<>();
            shapes.add(base);
            shapes.add(barrel);
            return shapes;
        }

        public CannonBall fire(double targetX, double targetY) {
            double angle = Math.atan2(targetY - (barrel.getY() + barrel.getHeight() / 2), targetX - (barrel.getX() + barrel.getWidth() / 2));
            double pivotX = barrel.getX(); // The left edge of the barrel remains fixed
            double pivotY = barrel.getY() + barrel.getHeight() / 2;

            barrel.getTransforms().clear(); // Clear any previous transforms
            barrel.getTransforms().add(new javafx.scene.transform.Rotate(Math.toDegrees(angle), pivotX, pivotY)); // Rotate around the left edge

            return new CannonBall(barrel.getX() + barrel.getWidth(), barrel.getY() + barrel.getHeight() / 2, angle); // Fire from the end of the barrel
        }
    }

    // CannonBall Class
    class CannonBall {
        private Circle shape;
        private double angle;
        private double speed = 10; // Increased speed for better gameplay

        public CannonBall(double x, double y, double angle) {
            shape = new Circle(x, y, 10, Color.BLACK); // Increased size for better visibility
            this.angle = angle;
        }

        public Circle getShape() {
            return shape;
        }

        public void startMovement(List<Target> targets, Blocker blocker) {
            AnimationTimer movement = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    shape.setCenterX(shape.getCenterX() + Math.cos(angle) * speed);
                    shape.setCenterY(shape.getCenterY() + Math.sin(angle) * speed);

                    // Check collisions with targets
                    for (Target target : new ArrayList<>(targets)) {
                        if (Shape.intersect(shape, target.getShape()).getBoundsInLocal().getWidth() != -1) {
                            root.getChildren().remove(target.getShape());
                            root.getChildren().remove(shape);
                            targets.remove(target);
                            timeRemaining += 3;
                            timerLabel.setText("Time remaining: " + String.format("%.1f", timeRemaining) + " seconds");
                            targetHitSound.play();
                            stop();
                            cannonBallInFlight = false;
                            if (targets.isEmpty()) {
                                Platform.runLater(() -> endGame(true));
                            }
                            return;
                        }
                    }

                    // Check collision with blocker
                    if (Shape.intersect(shape, blocker.getShape()).getBoundsInLocal().getWidth() != -1) {
                        timeRemaining = Math.max(0.0, timeRemaining - 3);
                        timerLabel.setText("Time remaining: " + String.format("%.1f", timeRemaining) + " seconds");
                        blockerHitSound.play();
                        angle = Math.PI - angle; // Ricochet the cannonball in the opposite direction
                    }

                    // Check if cannonball is out of bounds
                    if (shape.getCenterX() < 0 || shape.getCenterX() > 1024 || shape.getCenterY() < 0 || shape.getCenterY() > 768) {
                        root.getChildren().remove(shape);
                        stop();
                        cannonBallInFlight = false;
                    }
                }
            };
            movement.start();
        }
    }

    // Target Class
    class Target {
        private Rectangle shape;
        private TranslateTransition transition;

        public Target(double x, double y) {
            shape = new Rectangle(x, y, 30, 80); // Adjusted size to match image
            shape.setFill(Color.BLUE);
        }

        public Rectangle getShape() {
            return shape;
        }

        public void startMovement(boolean moveUpInitially, double speed) {
            transition = new TranslateTransition(Duration.seconds(3 / speed), shape);
            transition.setByY(500);
            transition.setAutoReverse(true);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setRate(moveUpInitially ? -1 : 1);
            transition.play();
        }

        public void stopMovement() {
            if (transition != null) {
                transition.stop();
            }
        }
    }

    // Blocker Class
    class Blocker {
        private Rectangle shape;
        private TranslateTransition transition;

        public Blocker(double x, double y) {
            shape = new Rectangle(x, y, 50, 150); // Adjusted size to match image
            shape.setFill(Color.BLACK);
        }

        public Rectangle getShape() {
            return shape;
        }

        public void startMovement(boolean moveUpInitially, double speed) {
            transition = new TranslateTransition(Duration.seconds(3 / speed), shape);
            transition.setByY(400);
            transition.setAutoReverse(true);
            transition.setCycleCount(TranslateTransition.INDEFINITE);
            transition.setRate(moveUpInitially ? -1 : 1);
            transition.play();
        }

        public void stopMovement() {
            if (transition != null) {
                transition.stop();
            }
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
