package DesktopUI;

import SAM.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class MainRadar {
    private final double canvasSize = 600;
    private final GraphicsContext ctx;

    public MainRadar(SAM sam, Pane root) {
        Canvas canvas = new Canvas();
        canvas.setWidth(this.canvasSize);
        canvas.setHeight(this.canvasSize);
        this.ctx = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
    }

    public void render() {
        this.ctx.clearRect(0, 0, this.canvasSize, this.canvasSize);
        // Draw bg
        this.ctx.setFill(Color.web("rgb(15, 33, 19)"));
        this.ctx.fillArc(0, 0, this.canvasSize, this.canvasSize, 0, 360, ArcType.OPEN);
        // Draw circles
        this.ctx.setStroke(Color.web("rgb(150, 249, 123)"));
        this.ctx.setLineWidth(0.1);
        for (int r = 50; r < this.canvasSize /2; r += 50) {
            this.ctx.strokeArc(
                    this.canvasSize/2 - r,
                    this.canvasSize/2 - r,
                    r * 2,
                    r * 2,
                    0, 360,
                    ArcType.OPEN
            );
        }

        ctx.setStroke(Color.web("rgb(150, 249, 123)"));
        ctx.setLineWidth(0.1);
        for (int l = 0; l < 36; l++) {
            double sin = Math.sin(Math.toRadians(l * 10) - Math.PI / 2);
            double cos = Math.cos(Math.toRadians(l * 10) - Math.PI / 2);
            ctx.moveTo(cos * 50 + 300, sin * 50 + 300);
            ctx.lineTo(cos * 300 + 300, sin * 300 + 300);
            ctx.stroke();
        }

    }

/*
    private void drawFlightObjects(GraphicsContext ctx) {
        List<DetectedFlightObject> detectedFlightObjectList = this.sam.getDetectedFlightObjects();

        for(DetectedFlightObject flightObject : detectedFlightObjectList) {

            double canvasX = flightObject.x + canvas.getWidth() /2;
            double canvasY = -flightObject.y + canvas.getHeight() / 2;

            ctx.setFill(flightObject.isMissile ? Color.RED : Color.GREEN);
            ctx.fillRect(canvasX, canvasY, 3, 3);
        }
    }

 */
}
