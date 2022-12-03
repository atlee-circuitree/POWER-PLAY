package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.OpenCV.AprilTagDetectionPipeline;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.ArrayList;

@Autonomous(name ="Auto Left", group = "drive")

public class AutoLeft extends BaseOpMode {
    OpenCvCamera camera;
    AprilTagDetectionPipeline aprilTagDetectionPipeline;

    static final double FEET_PER_METER = 3.28084;

    // Lens intrinsics
    // UNITS ARE PIXELS
    // NOTE: this calibration is for the C920 webcam at 800x448.
    // You will need to do your own calibration for other configurations!
    double fx = 578.272;
    double fy = 578.272;
    double cx = 402.145;
    double cy = 221.506;

    // UNITS ARE METERS
    double tagsize = 0.166;

    // Tag ID 1,2,3 from the 36h11 family
    int LEFT = 1;
    int MIDDLE = 2;
    int RIGHT = 3;

    AprilTagDetection tagOfInterest = null;

    @Override
    public void runOpMode() {



        int SelectedLane = 1;
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


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        aprilTagDetectionPipeline = new AprilTagDetectionPipeline(tagsize, fx, fy, cx, cy);

        camera.setPipeline(aprilTagDetectionPipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(800,448, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

        telemetry.setMsTransmissionInterval(50);

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (!isStarted() && !isStopRequested())
        {
            ArrayList<AprilTagDetection> currentDetections = aprilTagDetectionPipeline.getLatestDetections();

            if(currentDetections.size() != 0)
            {
                boolean tagFound = false;

                for(AprilTagDetection tag : currentDetections)
                {
                    if(tag.id == LEFT || tag.id == MIDDLE || tag.id == RIGHT)
                    {
                        tagOfInterest = tag;
                        SelectedLane = tag.id;
                        tagFound = true;
                        break;
                    }
                }

                if(tagFound)
                {
                    telemetry.addLine("Tag of interest is in sight!\n\nLocation data:");
                    tagToTelemetry(tagOfInterest);
                }
                else
                {
                    telemetry.addLine("Don't see tag of interest :(");

                    if(tagOfInterest == null)
                    {
                        telemetry.addLine("(The tag has never been seen)");
                    }
                    else
                    {
                        telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                        tagToTelemetry(tagOfInterest);
                    }
                }

            }
            else
            {
                telemetry.addLine("Don't see tag of interest :(");

                if(tagOfInterest == null)
                {
                    telemetry.addLine("(The tag has never been seen)");
                }
                else
                {
                    telemetry.addLine("\nBut we HAVE seen the tag before; last seen at:");
                    tagToTelemetry(tagOfInterest);
                }

            }

            telemetry.update();
            sleep(20);
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

        //.splineToLinearHeading(new Pose2d(38, 35), 280) <- saved code for other tests
        //.splineToLinearHeading(new Pose2d(36, 19.5), 270)
        //.lineToConstantHeading(new Vector2d(Side * 36, 10))
        //.forward(7)
        //.turn(Side * Math.toRadians(-90))

            TrajectorySequence trajStart = drive.trajectorySequenceBuilder(startPose)
                    .lineToConstantHeading(new Vector2d(Side * 36, 6))
                    .lineToConstantHeading(new Vector2d(Side * 36, 20))
                    .lineToSplineHeading(new Pose2d(Side * 33, 4, Math.toRadians(270 - (75 * Side))))
                    .strafeLeft(Side * 3.75)
                    .forward(6)
                    .back(4)
                    .build();
            drive.followTrajectorySequence(trajStart);

            //put arm movements here

        if ((Side == Left && SelectedLane == Lane1) || (Side == Right && SelectedLane == Lane3)) {
        Trajectory traj1 = drive.trajectoryBuilder(trajStart.end())
                //.lineTo(new Vector2d(Side * 67, 16)) went too far and hit 5 stack cones
                .lineTo(new Vector2d(Side * 57, 10))
                .build();

        drive.followTrajectory(traj1);
        }

        if (SelectedLane == Lane2) {
            Trajectory traj2 = drive.trajectoryBuilder(trajStart.end())
                    .lineTo(new Vector2d(Side * 37, 20))
                    .build();

            drive.followTrajectory(traj2);
        }

        if ((Side == Left && SelectedLane == Lane3) || (Side == Right && SelectedLane == Lane1)) {
            TrajectorySequence traj3 = drive.trajectorySequenceBuilder(trajStart.end())
                    .lineTo(new Vector2d(Side * 38, 13))
                    .lineTo(new Vector2d(Side * 11, 13))
                    .turn(Side * Math.toRadians(-15))
                    .build();

            drive.followTrajectorySequence(traj3);
        }

    }
    void tagToTelemetry(AprilTagDetection detection)
    {
        telemetry.addLine(String.format("\nDetected tag ID=%d", detection.id));
        //telemetry.addLine(String.format("Translation X: %.2f feet", detection.pose.x*FEET_PER_METER));
        //telemetry.addLine(String.format("Translation Y: %.2f feet", detection.pose.y*FEET_PER_METER));
        //telemetry.addLine(String.format("Translation Z: %.2f feet", detection.pose.z*FEET_PER_METER));
        //telemetry.addLine(String.format("Rotation Yaw: %.2f degrees", Math.toDegrees(detection.pose.yaw)));
        //telemetry.addLine(String.format("Rotation Pitch: %.2f degrees", Math.toDegrees(detection.pose.pitch)));
        //telemetry.addLine(String.format("Rotation Roll: %.2f degrees", Math.toDegrees(detection.pose.roll)));
    }
}