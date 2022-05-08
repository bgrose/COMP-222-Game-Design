/**
 * @author Bradley P Grose
 * @student_id 201611354
 * @date 2022-05-05
 * @version 1.0
 */

package comp222;

import robocode.*;
import java.awt.Color;
import java.util.Random;
import java.util.random.*;

import static robocode.util.Utils.normalRelativeAngle;


/**
 * BradleyGrose - a robot by Bradley P Grose
 */
public class BradleyGrose extends AdvancedRobot {

	/**
	 * @desc - The run method is the method that is called when the robot is
	 *       running.
	 * @param - None
	 * @return - None
	 */
	public void run() {

		// Set colors
		setBodyColor(Color.black);
		setGunColor(Color.gray);
		setRadarColor(Color.blue);
		setScanColor(Color.blue);
		setBulletColor(Color.red);

		// Keep Radar & gun still when turning
		setAdjustRadarForRobotTurn(true);
		setAdjustGunForRobotTurn(true);
		turnRadarRightRadians(Double.POSITIVE_INFINITY);

		while (true) {
			// Speed up
			setMaxVelocity(10);
			// Check if corner
			wallCheck();

			Random rand = new Random();
			turnRight(rand.nextInt(360));
			ahead(rand.nextInt(1000));

		}
	}

	/**
	 * @desc - Checks if stuck on wall, if so move away
	 * @param - None
	 * @return - None
	 */
	public void wallCheck() {
		// Check is stuck on wall
		if (getX() == 0 && getY() == 0) {
			turnRight(90);
			ahead(100);
		} else if (getX() == getBattleFieldWidth() && getY() == 0) {
			turnRight(90);
			ahead(100);
		} else if (getX() == getBattleFieldWidth() && getY() == getBattleFieldHeight()) {
			turnRight(90);
			ahead(100);
		} else if (getX() == 0 && getY() == getBattleFieldHeight()) {
			turnRight(90);
			ahead(100);
		}
	}

	/**
	 * @desc - onScannedRobot is called when the robot scans another robot.
	 * @param e - the ScannedRobotEvent to be handled
	 * @return - None
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		wallCheck();

		// Check Distance to scanned robot
		double distance = e.getDistance();
		if (distance < 100) {
			// Point gun at enemy
			double absoluteBearing = getHeadingRadians() + e.getBearingRadians();
			setTurnGunRightRadians(normalRelativeAngle(absoluteBearing - getGunHeadingRadians()));

			// Fire at scanned robot
			fire(1);
		}

	}

	/**
	 * @desc - onHitByBullet is called when the robot is hit by a bullet.
	 * @param e - the HitByBulletEvent to be handled
	 * @return - None
	 */
	public void onHitByBullet(HitByBulletEvent e) {

		// Move away from target
		setTurnRight(e.getBearing() + (getHeading() - getRadarHeading()));
		setAhead(100);
		if (getGunHeat() == 0) {
			// Fire
			fire(3);
		} else {
			fire(1);
		}

	}

	/**
	 * @desc - onHitWall is called when the robot hits a wall.
	 * @param e - the HitWallEvent to be handled
	 * @return - None
	 */
	public void onHitWall(HitWallEvent e) {
		// Move away from wall
		setTurnRight(e.getBearing() + (getHeading() - getRadarHeading()));
		setAhead(100);

	}

	/**
	 * @desc - onHitRobot is called when the robot hits another robot.
	 * @param e - the HitRobotEvent to be handled
	 * @return - None
	 */
	public void onHitRobot(HitRobotEvent e) {

		// If in easy range of gun, fire
		if (e.getBearing() > -30 && e.getBearing() < 30) {
			fire(3);
			turnRight(90);
			ahead(100);
		}

		// If Im at fault for crashing into another robot, move away
		if (e.isMyFault()) {
			turnRight(90);
			ahead(100);
		}
	}

}
