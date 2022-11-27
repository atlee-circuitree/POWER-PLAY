package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name ="TestPath", group = "drive")
public class TestPath extends LinearOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //The coordinates are measured in inches from the center of the robot/odometry wheels
        Pose2d startPose = new Pose2d(-10, 0, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        Trajectory traj1 = drive.trajectoryBuilder(startPose)
                .splineToSplineHeading(new Pose2d(0, 0, Math.toRadians(90)), Math.toRadians(0))
                .splineToSplineHeading(new Pose2d(20, 20, Math.toRadians(180)), Math.toRadians(90))
                .splineToSplineHeading(new Pose2d(0, 40, Math.toRadians(270)), Math.toRadians(180))
                .splineToSplineHeading(new Pose2d(-20, 20, Math.toRadians(0)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(0, 0, Math.toRadians(90)), Math.toRadians(0))
                .build();
        drive.followTrajectory(traj1);
    }
}