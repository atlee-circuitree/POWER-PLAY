package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name ="Blue Auto Left", group = "drive")

public class BlueAutoLeft extends BaseOpMode {
    @Override
    public void runOpMode() {

        waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //GetHardware();
        //The coordinates are measured in inches from the center of the robot/odometry wheels
        //Pose2d startPose = new Pose2d(35, 61.5, Math.toRadians(270));

        if (isStopRequested()) return;

        Pose2d startPose = new Pose2d(36, 63.5, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj = drive.trajectorySequenceBuilder(startPose)
                .lineToConstantHeading(new Vector2d(36, 19.5))
                //.splineTo(new Vector2d(36, 8.5), Math.toRadians(195))
                //.lineToConstantHeading(new Vector2d(34, -1.5))
                .lineToSplineHeading(new Pose2d(34, 6, Math.toRadians(195)))
                .strafeLeft(9)
                .forward(6)
                .back(3)
                .build();

        drive.followTrajectorySequence(traj);

    }
}