package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Autonomous(name ="Blue Auto Right", group = "drive")
public class BlueAutoRight extends BaseOpMode {
    @Override
    public void runOpMode() {
        waitForStart();
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        GetHardware();

        //The coordinates are measured in inches from the center of the robot/odometry wheels
        Pose2d startPose = new Pose2d(-35, 61.5, Math.toRadians(270));

        drive.setPoseEstimate(startPose);

        TrajectorySequence traj1 = drive.trajectorySequenceBuilder(startPose)
                .forward(61.5)
                .turn(Math.toRadians(117))
                .addDisplacementMarker(1, () -> {
                    //add motor movement
                })
                .build();

        drive.followTrajectorySequence(traj1);
    }
}