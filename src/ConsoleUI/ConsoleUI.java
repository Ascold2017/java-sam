package ConsoleUI;

import Engine.Engine;
import Engine.FlightObject.BaseFlightObject;
import Engine.FlightObject.Point;
import Engine.LoopEngine.LoopHandler;

import java.io.IOException;


public class ConsoleUI {
    private final Engine engine;
    private final double scale = 6;
    private final int screenSize = 20;

    public ConsoleUI(Engine engine) {
        this.engine = engine;
        LoopHandler uiLoopHandler = this::update;
        this.engine.addLoop("UpdateUI", uiLoopHandler);
    }

    private void update(double time) {
        this.clearScreen();
        this.prinfFlightObjects();
        //this.drawField();
    }
    private void prinfFlightObjects() {
        this.engine.printFlightObjects();
    }

    private void clearScreen() {
        String lowerOSName = System.getProperty("os.name").toLowerCase();

        if(lowerOSName.contains("window")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                // System.out.println("\u001B[2J");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }


    private boolean isTargetOnPosition(BaseFlightObject target, int i, int j) {
        Point targetPoint = target.getCurrentPoint();
        int targetX = (int)((double) this.screenSize / 2 + (targetPoint.x / this.scale));
        int targetY = (int)((double) this.screenSize / 2 - (targetPoint.y / this.scale));

        if (targetX == j && targetY == i) return true;
        return  false;
    }
    private void drawField() {
        StringBuilder field = new StringBuilder();
        for (int i = 0; i < this.screenSize; i++) {
            field.append('|');
            for (int j = 0; j < this.screenSize; j++) {
                int finalI = i;
                int finalJ = j;
                boolean isTarget = this.engine.getFlightObjects().stream().anyMatch(fo -> this.isTargetOnPosition(fo, finalI, finalJ));
                field.append(isTarget ? '*' : i == this.screenSize / 2 ? '_' : j == this.screenSize /2 ? '|' : ' ');
            }
            field.append('|');
            field.append('\n');
        }
        System.out.println(field);
    }
}
