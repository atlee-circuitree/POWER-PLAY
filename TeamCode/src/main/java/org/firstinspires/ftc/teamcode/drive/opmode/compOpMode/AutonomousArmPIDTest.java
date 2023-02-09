package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name ="AutonomousPIDTest", group = "drive")
public class AutonomousArmPIDTest extends BaseOpMode {
    @Override
    public void runOpMode() {

        GetHardware();
        GetIMU();
        waitForStart();


        while (opModeIsActive()) {
                if (behaviorStep == 1) {
                    transferClaw.setPosition(TRANSFER_CLAW_CLOSE);
                    vertArmState = vertArmMech(VERT_ARM_EXTENDING, vArmHigh, ENCODER_ERROR_THRESHOLD);
                    transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_BACK);
                    transferArmTop.setPosition(TRANSFER_ARM_TOP_BACK);
                    if (vertArmState == VERT_ARM_EXTENDED) {
                        behaviorStep = 2;
                    }
                }
                if (behaviorStep == 2) {
                    vertArmState = vertArmMech(VERT_ARM_RETRACTING, vArmMid, ENCODER_ERROR_THRESHOLD);
                    if (vertArmState == VERT_ARM_RETRACTING) {
                        transferClaw.setPosition(TRANSFER_CLAW_OPEN);
                        if (vertArmState == VERT_ARM_RETRACTING && transferClawState == TRANSFER_CLAW_OPEN) {
                            behaviorStep = 3;
                        }
                    }

                }
                if (behaviorStep == 3) {
                    transferArmTop.setPosition(TRANSFER_ARM_TOP_FRONT);
                    transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_FRONT);
                    if (transferArmTopState == TRANSFER_ARM_TOP_FRONT && transferArmBottomState == TRANSFER_ARM_BOTTOM_FRONT) {
                        behaviorStep = 4;
                    }
                }
                if (behaviorStep == 4) {
                    vertArmState = vertArmMech(VERT_ARM_RETRACTED, vArmPickup, ENCODER_ERROR_THRESHOLD);
                    if (vertArmState == VERT_ARM_RETRACTED) {
                        behavior = BEHAVIOR_FINISHED;
                    }
                }
            }
        }
    }



// was using master and this was red
            // if (horizArmState == HORIZ_ARM_EXTENDING) {
            //    if (horizencoder() == horizencoder target) {
            //        horizArmState = HORIZ_ARM_EXTENDED;
            //    } else{
            //       horizArmPIDLoop(hArmExtend);
            //   }
            //  }
            // if (angleArmState == ANGLE_ARM_EXTENDING) {
            //  if (horizencoder() == horizencoder target) {
            //       horizArmState = ANGLE_ARM_EXTENDED;
            //   } else{
            //       horizArmPIDLoop(hArmExtend);
            //   }
            // }



