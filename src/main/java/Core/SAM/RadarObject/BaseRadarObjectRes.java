package Core.SAM.RadarObject;

import Core.Engine.FlightObject.Point;

public record BaseRadarObjectRes(String id, Point currentPoint, double currentRotation, double visibilityK) {};
