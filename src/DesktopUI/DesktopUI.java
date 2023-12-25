package DesktopUI;

import Engine.*;
import Engine.LoopEngine.*;
import SAM.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import java.util.List;
import java.util.Objects;

public class DesktopUI {
    private final Engine engine;
    private final SAM sam;

    private final MainRadar mainRadar;
    public DesktopUI(Stage stage, Engine engine, SAM sam) {
        this.engine = engine;
        this.sam = sam;
        Pane root = new Pane();
        this.mainRadar = new MainRadar(sam, root);
        this.engine.addFixedLoop("MainRadarLoop", (t) -> this.mainRadar.render(), 1000);

        Scene scene = new Scene(root, 600, 600, Color.BLACK);
        stage.setTitle("JavaFX SAM");
        stage.setScene(scene);
        stage.show();

    }
}
