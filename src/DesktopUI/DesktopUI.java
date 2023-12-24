package DesktopUI;

import Engine.*;
import Engine.LoopEngine.*;
import SAM.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import java.util.List;

public class DesktopUI {
    private final Canvas canvas;
    private final Engine engine;
    private final SAM sam;
    public DesktopUI(Stage stage, Engine engine, SAM sam) {
        this.engine = engine;
        this.sam = sam;
        this.canvas = new Canvas(600, 600);
        GraphicsContext ctx = canvas.getGraphicsContext2D();

        initRenderLoop(ctx);

        Pane root = new Pane();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root, 600, 600, Color.BLACK);

        stage.setTitle("JavaFX SAM");
        stage.setScene(scene);
        stage.show();
    }

    private void initRenderLoop(GraphicsContext ctx) {
        LoopHandler renderLoopHandler = (double time) -> {
            ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            ctx.setFill(Color.BLACK);
            drawBackground(ctx);
            drawCircles(ctx);
            drawLines(ctx);
            drawFlightObjects(ctx);
        };
        this.engine.addLoop("RenderLoop", renderLoopHandler);

    }

    private void drawBackground(GraphicsContext ctx) {
        ctx.setFill(Color.web("rgb(15, 33, 19)"));
        ctx.fillArc(0, 0, canvas.getWidth(), canvas.getHeight(), 0, 360, ArcType.OPEN);
    }

    private void drawCircles(GraphicsContext ctx) {
        ctx.setStroke(Color.web("rgb(150, 249, 123)"));
        ctx.setLineWidth(0.1);
        for (int r = 100; r < this.canvas.getWidth() /2; r+= 100) {
            ctx.strokeArc(
                    this.canvas.getWidth()/2 - r,
                    this.canvas.getWidth()/2 - r,
                    r*2,
                    r*2,
                    0, 360,
                    ArcType.OPEN
            );
        }
    }

    private void drawLines(GraphicsContext ctx) {
        ctx.setStroke(Color.web("rgb(150, 249, 123)"));
        ctx.setLineWidth(0.05);
        for (int l = 0; l < 36; l++) {
            double sin = Math.sin(l * 10 * (Math.PI / 180) - Math.PI / 2);
            double cos = Math.cos(l * 10 * (Math.PI / 180) - Math.PI / 2);
            ctx.moveTo(cos * 50 + 300, sin * 50 + 300);
            ctx.lineTo(cos * 300 + 300, sin * 300 + 300);
            ctx.stroke();
        }
    }

    private void drawFlightObjects(GraphicsContext ctx) {
        List<DetectedFlightObject> detectedFlightObjectList = this.sam.getDetectedFlightObjects();

        for(DetectedFlightObject flightObject : detectedFlightObjectList) {

            double canvasX = flightObject.x + canvas.getWidth() /2;
            double canvasY = -flightObject.y + canvas.getHeight() / 2;

            ctx.setFill(flightObject.isMissile ? Color.RED : Color.GREEN);
            ctx.fillRect(canvasX, canvasY, 3, 3);

        }



    }
}
