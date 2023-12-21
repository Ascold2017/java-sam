package Tools;

import Engine.FlightObject.Point;

/**
 * The vector factory.
 *
 * @method Vector3D
 * @param {x} The first (x) Cartesian coordinate.
 * @param {number} y The second (y) Cartesian coordinate.
 * @param {number} z The third (z) Cartesian coordinate.
 * @constructor
 */
public class Vector3D {

    private double _x = 0;
    private double _y = 0;
    private  double _z = 0;

    public Vector3D(double x, double y, double z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public Vector3D(Point point) {
        _x = point.x;
        _y = point.y;
        _z = point.z;
    }

    /**
     * Returns a deep copy of the vector.
     *
     * @method copy
     * @memberOf Vector3D
     * @return {Vector3D} A new vector which is the copy of the current one.
     */
    public Vector3D copy() {
        return new Vector3D(_x, _y, _z);
    }
    /**
     * Returns the X coordinate.
     *
     * @method x
     * @memberOf Vector3.
     * @return {double} The value of the X coordinate.
     */
    public double x() {
        return _x;
    }
    /**
     * Sets the X coordinate.
     *
     * @method x
     * @memberOf Vector3D
     * @param {double} x Value for the X coordinate.
     * @return {Vector3D} The new vector with the X coordinate as provided.
     */
    public  Vector3D x(double x) {
        return new Vector3D(x, _y, _z);
    }

    /**
     * Returns the Y coordinate.
     *
     * @method y
     * @memberOf Vector3D.
     * @return {double} The value of the Y coordinate.
     */
    public double y() {
        return _y;
    }
    /**
     * Sets the Y coordinate.
     *
     * @method y
     * @memberOf Vector3D
     * @param {double} y Value for the Y coordinate.
     * @return {Vector3D} The new vector with the Y coordinate as provided.
     */
    public  Vector3D y(double y) {
        return new Vector3D(_x, y, _z);
    }

    /**
     * Returns the Z coordinate.
     *
     * @method z
     * @memberOf Vector3D.
     * @return {double} The value of the Z coordinate.
     */
    public double z() {
        return _z;
    }
    /**
     * Sets the Z coordinate.
     *
     * @method z
     * @memberOf Vector3D
     * @param {double} z Value for the Z coordinate.
     * @return {Vector3D} The new vector with the Z coordinate as provided.
     */
    public  Vector3D z(double z) {
        return new Vector3D(_x, _y, z);
    }

    /**
     * Scales the vector by the specified value.
     *
     * @method scale
     * @memberOf Vector3D
     * @param {number} value Scaling factor.
     * @return {Vector3D} A new vector which is parallel to the original and scaled by the scaling factor.
     */
    public Vector3D scale(double value) {
        return new Vector3D(_x * value, _y * value, _z * value);
    }

    /**
     * Returns or sets the vector's length.
     *
     * @method r
     * @memberOf Vector3D
     * @returns {double} The length of the vector
     */
    public double r() {
        return Math.sqrt( Math.pow(_x, 2.0) + Math.pow(_y, 2.0) + Math.pow(_z, 2.0));

    }

    /**
     * Returns or sets the vector's length.
     *
     * @method r
     * @memberOf Vector3D
     * @param {double} value The length to set. If not provided, the vector's length is returned.
     * @return {Vector3D} The length of the vector or a new vector which is parallel to the original and has the
     * specified length.
     */
    public Vector3D r(double value) {
        final double length = Math.sqrt( Math.pow(_x, 2.0) + Math.pow(_y, 2.0) + Math.pow(_z, 2.0));
        return this.scale(value / length);
    }

    /**
     * Returns the inclination of the vector in spherical coordinates.
     *
     * @method inclination
     * @memberOf Vector3d
     * @return {number} The inclination of the vector
     */
    public double inclination() {
        return Math.atan2(Math.sqrt(Math.pow(_x, 2.0) + Math.pow(_y, 2.0)), this._z);
    }

    /**
     * Sets the inclination of the vector in spherical coordinates.
     *
     * @method inclination
     * @memberOf Vector3d
     * @param {number} value The vector's inclination to set.
     * @return {Vector3D} The new vector with the same length and azimuth but the
     * inclination is set as specified.
     */
    public Vector3D inclination(double value) {
        final double length = Math.sqrt( Math.pow(_x, 2.0) + Math.pow(_y, 2.0) + Math.pow(_z, 2.0));
        final double r = r();
        final double azimuth = azimuth();
        return new Vector3D(
                r * Math.cos(azimuth) * Math.sin(value),
                r * Math.sin(azimuth) * Math.sin(value),
                r * Math.cos(value)
        );

    }

    /**
     * Returns the azimuth of the vector in spherical coordinates.
     *
     * @method azimuth
     * @memberOf Vector3d
     * @return {number} The azimuth of the vector in rads
     */
    public  double azimuth() {
        return Math.atan2(_y, _x);

    }
    /**
     * Sets the azimuth of the vector in spherical coordinates.
     *
     * @method azimuth
     * @memberOf Vector3d
     * @param {number} value The vector's azimuth to set
     * @return {Vector3D} The azimuth of the vector or a new vector with the same length and inclination but the
     * azimuth is set as specified.
     */
    public Vector3D azimuth(double value) {
        final double r = r();
        final double inclination = inclination();
        return new Vector3D(
                r * Math.cos(value) * Math.sin(inclination),
                r * Math.sin(value) * Math.sin(inclination),
                r * Math.cos(inclination)
        );
    }

    /**
     * Adds a vector to the current vector.
     *
     * @method add
     * @memberOf Vector3D
     * @param {Vector3D} vec The vector to be added to the current vector.
     * @return {Vector3D} A new vector that is the sum of the two vectors.
     */
    public  Vector3D add(Vector3D vec) {
        return new Vector3D(
                _x + (vec.x()),
                _y + (vec.y()),
                _z + (vec.z())
        );
    }

    /**
     * Subtracts a vector from the current vector.
     *
     * @method sub
     * @memberOf Vector3D
     * @param {Vector3D} vec The vector to be subtracted from the current vector.
     * @return {Vector3D} A new vector that is the difference of the two vectors.
     */
    public  Vector3D sub(Vector3D vec) {
        return new Vector3D(
                _x - vec.x(),
                _y - vec.y(),
                _z - vec.z()
        );
    }

    /**
     * Returns the dot (scalar) product with another vector.
     *
     * @method dot
     * @memberOf Vector3d
     * @param {Vector3D} vec Vector to multiply the current vector with.
     * @return {number} The scalar product of the two vectors.
     */
    public  double dot(Vector3D vec) {
        return _x * vec.x() +
                _y * vec.y() +
                _z * vec.z();
    }

    /**
     * Returns the cross (vector) product with another vector.
     *
     * @method cross
     * @memberOf Vector3d
     * @param {Vector3D} vec Vector to multiply the current vector with.
     * @return {Vector3D} The vector product of the two vectors.
     */
    public Vector3D cross(Vector3D vec) {
        return new Vector3D(
                _y * vec.z() - _z * vec.y(),
                _z * vec.x() - this._x * vec.z(),
                _x * vec.y() - this._y * vec.x()
        );
    }



    @Override
    public String toString() {
        return "X:" + _x + "|Y:" + _y + "|Z:" + _z;
    }
}
