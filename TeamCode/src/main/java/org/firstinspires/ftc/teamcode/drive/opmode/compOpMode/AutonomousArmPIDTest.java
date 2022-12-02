package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.roadrunner.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name ="AutonomousPIDTest", group = "drive")
public class AutonomousArmPIDTest extends BaseOpMode {
    @Override
    public void runOpMode() {

        GetHardware();

        waitForStart();

        while (opModeIsActive()) {
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



        }

        vertArmPIDLoop(vArmLow);

        angleArmPIDLoop(aArmCone5);

        horizArmPIDLoop(hArmExtend);

        transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_FRONT);
        transferArmTop.setPosition(TRANSFER_ARM_TOP_FRONT);

        horizClaw.setPosition(HORIZONTAL_CLAW_CLOSE);

        angleArmPIDLoop(aArmConeLift);

        horizArmPIDLoop(hArmRetract);

        angleArmPIDLoop(aArmConeFlat);

        vertArmPIDLoop(vArmPickup);

        horizClaw.setPosition(HORIZONTAL_CLAW_HALF_CLOSE);

        transferClaw.setPosition(TRANSFER_CLAW_CLOSE);

        vertArmPIDLoop(vArmHigh);
        transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_BACK);
        transferArmTop.setPosition(TRANSFER_ARM_TOP_BACK);

        transferClaw.setPosition(TRANSFER_CLAW_OPEN);

    }
}