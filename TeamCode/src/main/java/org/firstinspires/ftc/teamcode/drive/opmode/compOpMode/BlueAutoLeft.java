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

        int SelectedLane = 3;
        int Lane1 = 1;
        int Lane2 = 2;
        int Lane3 = 3;

        int SelectedSide = 0;
        int Left = 1;
        int Right = -1;
        int Side = 1;


        if (SelectedSide == 1) {
            Side = -1;
        }

        waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //The coordinates are measured in inches from the center of the robot/odometry wheels
        //Pose2d startPose = new Pose2d(35, 61.5, Math.toRadians(270));

        if (isStopRequested()) return;

        Pose2d startPose = new Pose2d(Side * 36, 63.5, Math.toRadians(270));
        //Pose2d trajStartPose = new Pose2d(Side * 34, 3, Math.toRadians(270 - (75 * Side)));

        drive.setPoseEstimate(startPose);
        //drive.setPoseEstimate(trajStartPose);
        //trajStart.end() <- this is for copy and paste

            TrajectorySequence trajStart = drive.trajectorySequenceBuilder(startPose)
                    //.lineToConstantHeading(new Vector2d(Side * 36, 10))
                    .lineToConstantHeading(new Vector2d(Side * 36, 19.5))
                    //.splineToLinearHeading(new Pose2d(38, 35), 280)
                    //.splineToLinearHeading(new Pose2d(36, 19.5), 270)
                    .lineToSplineHeading(new Pose2d(Side * 34, 7, Math.toRadians(270 - (75 * Side))))
                    .strafeLeft(Side * 9)
                    .forward(6)
                    .back(3)
                    .build();
            drive.followTrajectorySequence(trajStart);


        if ((Side == Left && SelectedLane == Lane1) || (Side == Right && SelectedLane == Lane3)) {
        Trajectory traj1 = drive.trajectoryBuilder(trajStart.end())
                .lineTo(new Vector2d(Side * 68, 16))
                .build();

        drive.followTrajectory(traj1);
        }

        if (SelectedLane == Lane2) {
            Trajectory traj2 = drive.trajectoryBuilder(trajStart.end())
                    .lineTo(new Vector2d(Side * 38, 20))
                    .build();

            drive.followTrajectory(traj2);
        }

        if ((Side == Left && SelectedLane == Lane3) || (Side == Right && SelectedLane == Lane1)) {
            TrajectorySequence traj3 = drive.trajectorySequenceBuilder(trajStart.end())
                    .lineTo(new Vector2d(Side * 38, 17))
                    .lineTo(new Vector2d(Side * 8, 17))
                    .turn(Side * Math.toRadians(-15))
                    .build();

            drive.followTrajectorySequence(traj3);
        }

    }
}