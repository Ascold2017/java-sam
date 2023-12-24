package DesktopUI;

import Engine.*;
import Engine.LoopEngine.*;
import SAM.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Date;
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

        Scene scene = new Scene(root, 600, 600, Color.WHITESMOKE);

        stage.setTitle("JavaFX SAM");
        stage.setScene(scene);
        stage.show();
    }

    private void initRenderLoop(GraphicsContext ctx) {
        LoopHandler renderLoopHandler = (double time) -> {
            ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            ctx.setFill(Color.BLACK);
            drawLines(ctx);
            drawFlightObjects(ctx);
        };
        this.engine.addLoop("RenderLoop", renderLoopHandler);

    }

    private void drawLines(GraphicsContext ctx) {

        ctx.beginPath();
        ctx.moveTo(canvas.getWidth() / 2, 0);
        ctx.lineTo(canvas.getWidth() / 2, canvas.getHeight());
        ctx.moveTo(0, canvas.getHeight() / 2);
        ctx.lineTo(canvas.getWidth(), canvas.getHeight() / 2);
        ctx.stroke();

        ctx.fillText(new Date().toString(), 400, 15);
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
