package com._604robotics.subsystemwidget.widget;

import com._604robotics.subsystemwidget.data.PositionState;
import com._604robotics.subsystemwidget.data.type.PositionStateType;
import edu.wpi.first.shuffleboard.api.data.types.NumberType;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;

@Description(
    name = "Arm Display",
    dataTypes = PositionStateType.class,
    summary = "Displays the robot arm position given the angle"
)
@ParametrizedController("ArmDisaplyWidget.fxml")
public final class ArmDisplayWidget extends SimpleAnnotatedWidget<PositionState> {

  @FXML
  private Pane root;

  @FXML
  private ImageView mountView;
  private Image mountImage;

  private Rotate rotate;

  private Group armGroup;
  private Rectangle arm;
  private Rectangle armUpright;
  private Rectangle armCross;

  private double heightOffset;
  private double widthOffset;

  private double fullXOffset;
  private double fullYOffset;


  @FXML
  private void initialize() {
    /* -------------------------------- Image and Setup -------------------------------- */

    // Download svg image of arm mount.
    URL imageurl = getClass().getResource("/images/mount.png");
    mountImage = new Image(imageurl.toString());

    //Set add the srm mount image to the fxmlImageView.
    mountView.setImage(mountImage);

    //Offset all objects on the Pane.
    fullXOffset = -30;
    fullYOffset = 0;

    //Calculating offsets to layout objects in the arm mount's coordinate frame.
    //This is useful for going from robot svg to JavaFX shapes.
    heightOffset = root.getMinHeight() - mountImage.getHeight();
    widthOffset = root.getMinWidth() - mountImage.getWidth();

    //Moving the arm mount to the bottom of the "frame".
    mountView.setY(locateY(0));
    mountView.setX(locateX(0));

    /* -------------------------------- Drawing Arm -------------------------------- */

    //The width of a 2x1 in px(a 1x1 is half this width).
    double twoByOneScale = 16;

    //The length of the arm in px.
    double armLength = 125;

    //The length of the arm upright in px.
    double uprightLength = 55;

    //(X px, Y px) of where the arm will pivot around.
    double[] armMountPoint = {149, 10};

    //How much the arm sticks out past the pivot point in px.
    double armStickOut = 9;

    //Shifting points to get X and Y coordinates of objects based on top left corner.
    double armX = armMountPoint[0] - (twoByOneScale / 2);
    double armY = (armMountPoint[1] + armStickOut) - armLength;
    double uprightX = armX - uprightLength + twoByOneScale;

    //Drawing the arm at (armX, armY) with the thickness of a 2x1 and the given length.
    arm = new Rectangle( locateX(armX), locateY(armY), twoByOneScale, armLength );

    //Drawing the arm upright, perpendicular to the arm  at (uprightX, armY) with the thickness of a 2x1 and the given length.
    armUpright = new Rectangle( locateX(uprightX), locateY(armY), uprightLength, twoByOneScale );

    //Drawing the arm cross bar which is shifted in position from the upright and has the thickness of a 1x1.
    //The length doesnt matter as it si covered by the arm and the upright.
    armCross = new Rectangle( locateX(uprightX - 15), locateY(armY + 40), 75, (twoByOneScale / 2) );

    //Decorative circles,
    Circle decorativeCircle = new Circle(locateX(armMountPoint[0]), locateY(armMountPoint[1]), 6.75 );
    decorativeCircle.setFill(Color.web("#b7b7b7ff"));
    Circle decorativeCircle1 = new Circle(locateX(armMountPoint[0]), locateY(armMountPoint[1]), 5 );
    decorativeCircle1.setFill(Color.web("#434343ff"));
    Circle decorativeCircle2 = new Circle( locateX(uprightX - 5), locateY(armY + 8), 10 );
    decorativeCircle2.setFill(Color.web("#434343ff"));

    //Rounding corners.
    arm.setArcHeight(5);
    arm.setArcWidth(5);
    arm.setFill(Color.web("#d9d9d9ff"));

    armUpright.setArcHeight(5);
    armUpright.setArcWidth(5);
    armUpright.setFill(Color.web("#d9d9d9ff"));

    armCross.setFill(Color.web("#d9d9d9ff"));
    armCross.setRotate(55);

    /* -------------------------------- Rotation -------------------------------- */

    rotate = new Rotate();
    rotate.setPivotX(locateX(armMountPoint[0]));
    rotate.setPivotY(locateY(armMountPoint[1]));

    //Grouping the rotating objects.
    armGroup = new Group();
    armGroup.getChildren().addAll(armCross, arm, armUpright, decorativeCircle2);
    armGroup.getTransforms().add(rotate);

    //Listener to update rotation angle when data is changed.
    dataOrDefault.addListener((__, prev, cur) -> {
      Timeline timeline = new Timeline(
              new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), rotate.getAngle())),
              new KeyFrame(Duration.seconds(cur.getDt()), new KeyValue(rotate.angleProperty(), cur.getPosition())));
      timeline.play();
    });

    //Adding everything to the pane.
    root.getChildren().addAll(armGroup, decorativeCircle, decorativeCircle1);
  }

  private double locateY(double y){
    return heightOffset + y + fullYOffset;
  }

  private double locateX(double x){
    return widthOffset + x + fullXOffset;
  }

  @Override
  public Pane getView() {
    return root;
  }
}
