package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name ="OldTestAutonomous", group = "drive")
public class OldTestAutonomous extends BaseOpMode {
    @Override
    public void runOpMode() {

        AutoSide SelectedAuto = AutoSide.BLUELEFT;

        int sideDirection = 1;
        int CordX = 0;
        double CordY = 0;
        int StartingAngle = 0;

        waitForStart();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        if (SelectedAuto == AutoSide.BLUELEFT) {
            sideDirection = -1;
            CordX = 35;
            CordY = 61.5;
            StartingAngle = 270;
        } else if (SelectedAuto == AutoSide.BLUERIGHT) {
            sideDirection = 1;
            CordX = -35;
            CordY = 61.5;
            StartingAngle = 270;
        } else if (SelectedAuto == AutoSide.REDLEFT) {
            sideDirection = -1;
            CordX = -35;
            CordY = -61.5;
            StartingAngle = 90;
        } else if (SelectedAuto == AutoSide.REDRIGHT) {
            sideDirection = 1;
            CordX = 35;
            CordY = -61.5;
            StartingAngle = 90;
        }

        //The coordinates are measured in inches from the center of the robot/odometry wheels
        Pose2d startPose = new Pose2d(CordX, CordY, Math.toRadians(StartingAngle));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)
                .forward(61.5)
                .turn(Math.toRadians(sideDirection * 117))
                .build();

        drive.followTrajectorySequence(traj1);
    }
}