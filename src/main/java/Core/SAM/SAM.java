package Core.SAM;

import Core.Engine.Engine;
import Core.Engine.FlightObject.Enemy;
import Core.Engine.FlightObject.GuidanceMethod;
import Core.Engine.FlightObject.Missile;
import Core.Engine.LoopEngine.LoopHandler;
import Core.SAM.RadarObject.BaseRadarObject;
import Core.SAM.RadarObject.DetectedRadarObject;
import Core.SAM.RadarObject.SnowRadarObject;
import Core.SAM.RadarObject.UndetectedRadarObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SAM {
    final Engine engine;
    private final ArrayList<BaseRadarObject> radarObjects = new ArrayList<>();
    private final ArrayList<String> selectedObjectIds = new ArrayList<>();
    private final ArrayList<MissileChannel> missileChannels = new ArrayList<>();
    public boolean isEnabled = false;
    private int missilesLeft = SAM_PARAMS.MISSILES_COUNT;

    public SAM(Engine engine) {
        this.engine = engine;
        LoopHandler updateRadar = this::updateRadar;
        this.updateRadar(0);
        this.engine.addFPSLoop("updateRadar", updateRadar, 40);
        for (int i = 0; i < SAM_PARAMS.MISSILES_CHANNEL_COUNT; i++) {
            this.missileChannels.add(new MissileChannel(i));
        }
    }


    public void setIsEnabled(boolean value) {
        this.isEnabled = value;
    }

    void updateRadar(double time) {
        List<Enemy> enemies = this.engine.getFlightObjects()
                .stream()
                .filter(fo -> fo instanceof Enemy && BaseRadarObject.getDistance(fo.getCurrentPoint()) < SAM_PARAMS.MAX_DISTANCE)
                .sorted(DetectedRadarObject.sortByVisibilityComparator)
                .map(fo -> (Enemy) fo)
                .toList();

        List<Enemy> detectedEnemies = enemies
                .stream()
                .filter(e -> {
                    double distance = BaseRadarObject.getDistance(e.getCurrentPoint());
                    return distance < SAM_PARAMS.MAX_CAPTURE_RANGE && distance > SAM_PARAMS.MIN_CAPTURE_RANGE;
                })
                .limit(SAM_PARAMS.RADAR_MAX_DETECT_COUNT)
                .toList();

        List<Enemy> undetectedEnemies = enemies
                .stream()
                .filter(e -> detectedEnemies.stream().noneMatch(de -> de.id.equals(e.id)))
                .toList();

        List<Missile> missiles = this.engine.getFlightObjects()
                .stream()
                .filter(fo -> fo instanceof Missile)
                .map(fo -> (Missile) fo)
                .toList();

        List<DetectedRadarObject> detectedRadarObjects = detectedEnemies
                .stream()
                .map(DetectedRadarObject::new)
                .filter(fo -> fo.isVisible)
                .toList();

        this.radarObjects.clear();
        this.radarObjects.addAll(detectedRadarObjects);
        this.radarObjects.addAll(undetectedEnemies.stream().map(UndetectedRadarObject::new).filter(fo -> fo.isVisible).toList());
        this.radarObjects.addAll(missiles.stream().map(DetectedRadarObject::new).toList());
        for (int i = 0; i < 50; i++) {
            this.radarObjects.add(new SnowRadarObject());
        }
        // remove disapperead selected objects
        this.selectedObjectIds.removeIf(selectedObjectId -> detectedRadarObjects.stream().anyMatch(dro -> Objects.equals(dro.id, selectedObjectId)));
        // free missile channels with disappered targets
        for (MissileChannel missileChannel : this.missileChannels) {
            if (missileChannel.target != null && detectedRadarObjects.stream().noneMatch(dro -> Objects.equals(dro.id, missileChannel.target.id))) {
                missileChannel.reset();
            }
        }
    }

    public ArrayList<BaseRadarObject> getRadarObjects() {
        return new ArrayList<>(this.radarObjects);
    }

    public ArrayList<String> getSelectedObjectIds() {
        return new ArrayList<>(this.selectedObjectIds);
    }

    public ArrayList<MissileChannel> getMissileChannels() {
        return new ArrayList<>(this.missileChannels);
    }

    public int getMissilesLeft() {
        return this.missilesLeft;
    }

    public void launchMissile(String targetId, int channelId, GuidanceMethod method) {
        List<DetectedRadarObject> target = this.radarObjects
                .stream()
                .filter(f -> f.id.equals(targetId) && f instanceof DetectedRadarObject && !((DetectedRadarObject) f).isMissile)
                .map(f -> (DetectedRadarObject) f)
                .toList();
        List<String> radarObjects = this.radarObjects
                .stream()
                .map(f -> f.getClass().toString())
                .toList();
        final MissileChannel channel = this.missileChannels.get(channelId);
        if (!target.isEmpty() && this.missilesLeft > 0 && channel.missile == null && this.selectedObjectIds.stream().anyMatch(soi -> Objects.equals(soi, targetId))) {
            final Missile missile = new Missile(this.engine, (Enemy) target.getFirst().getFlightObject(), method);
            this.missilesLeft--;
            channel.set(target.getFirst(), missile);
            this.engine.addFlightObject(missile);
        }
    }

    public void resetMissile(int channelId) {
        this.missileChannels.get(channelId).reset();
    }

    public void selectTarget(String targetId) {
        final List<DetectedRadarObject> radarObject = this.radarObjects
                .stream()
                .filter(ro -> ro instanceof DetectedRadarObject)
                .map(ro -> (DetectedRadarObject) ro)
                .filter(dro -> Objects.equals(dro.id, targetId))
                .toList();

        if (!radarObject.isEmpty() && this.selectedObjectIds.stream().noneMatch(id -> Objects.equals(id, targetId)) && this.selectedObjectIds.size() < SAM_PARAMS.RADAR_MAX_SELECTED_COUNT) {
            this.selectedObjectIds.add(radarObject.getFirst().id);
        }
    }

    public void unselectTarget(String targetId) {
        if (this.selectedObjectIds.stream().anyMatch(id -> Objects.equals(id, targetId))) {
            this.missileChannels.forEach(missileChannel -> {
                if (missileChannel.target != null && Objects.equals(missileChannel.target.id, targetId)) {
                    missileChannel.reset();
                }
            });
            this.selectedObjectIds.removeIf(id -> !Objects.equals(id, targetId));
        }
    }

    public void resetTargets() {
        this.selectedObjectIds.clear();
        this.missileChannels.forEach(MissileChannel::reset);
    }
}
