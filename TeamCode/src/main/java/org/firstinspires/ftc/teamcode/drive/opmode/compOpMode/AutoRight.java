package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.OpenCV.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.ArrayList;

@Autonomous(name ="Auto Right", group = "drive")
public class AutoRight extends BaseOpMode {
    @Override
    public void runOpMode() {

       // waitForStart();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //GetHardware();
        //The coordinates are measured in inches from the center of the robot/odometry wheels
        //Pose2d startPose = new Pose2d(35, 61.5, Math.toRadians(270));

        if (isStopRequested()) return;

        Pose2d startPose = new Pose2d(-36, 63.5, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj = drive.trajectorySequenceBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-36, 19.5))
                //.splineTo(new Vector2d(36, 8.5), Math.toRadians(195))
                //.lineToConstantHeading(new Vector2d(34, -1.5))
                .lineToSplineHeading(new Pose2d(-34, 6, Math.toRadians(345)))
                .forward(1)
                .back(1.5)
                .build();

        drive.followTrajectorySequence(traj);

    }

}