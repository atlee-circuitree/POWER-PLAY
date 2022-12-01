package org.firstinspires.ftc.teamcode.drive.opmode.compOpMode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.opmode.Bases.BaseOpMode;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Config
@TeleOp(name="Arm PID Test", group="Linear Opmode")
public class ArmPIDTest extends BaseOpMode {
    @Override

    public void runOpMode() {


        GetHardware();

        double servoPosition = .5;
        int transferClawPosition = 0;

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            horizArmPIDLoop();
            vertArmPIDLoop();
            angleArmPIDLoop();

            //  double y_stick = -gamepad1.left_stick_y;
            //  double x_stick = gamepad1.left_stick_x;
            double forward = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double rightX = -gamepad1.right_stick_x;
            //Field Orientation Code
            double pi = 3.1415926;
            double gyro_degrees = -imu.getAngularOrientation().firstAngle;
            double gyro_radians = gyro_degrees * pi/180;

            //double y_joystick = -y_stick;
            //double y_joystick = y_stick * Math.cos(gyro_radians) + x_stick * Math.sin(gyro_radians);
            // x_stick = -y_stick * Math.sin(gyro_radians) + x_stick * Math.cos(gyro_radians);

            double temp = forward * Math.cos(gyro_radians) +
                    strafe * Math.sin(gyro_radians);
            strafe = -forward * Math.sin(gyro_radians) +
                    strafe * Math.cos(gyro_radians);
            //  double y_joystick = temp;
            forward = temp;

            // At this point, Joystick X/Y (strafe/forwrd) vectors have been
            // rotated by the gyro angle, and can be sent to drive system

            //Mecanum Drive Code
            frontLeft.setPower((forward + strafe + rightX) * SD);
            rearLeft.setPower((forward - strafe + rightX) * SD);
            frontRight.setPower((forward - strafe - rightX) * SD);
            rearRight.setPower((forward + strafe - rightX) * SD);


            /* double r = Math.hypot(x_stick, y_joystick);
            double robotAngle = Math.atan2(y_joystick, x_stick) - Math.PI / 4;

            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            frontLeft.setPower(v1 * SD);
            rearLeft.setPower(v3 * SD);
            frontRight.setPower(v2 * SD);
            rearRight.setPower(v4 * SD);

            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
*/

            //Show encoder values on the phone
            if (testModeV == 1) {
                telemetry.addData("Test Mode ", testModeV);
            } else if (testModeV == 2) {
                telemetry.addData("Servo Test Mode", testModeV);
            } else {
                telemetry.addData("Driver Mode ", testModeV);
            }
            telemetry.addData("Horiz Arm Power", horizArm.getPower());
            telemetry.addData("Vert Arm Power", vertArm.getPower());
            telemetry.addData("Angle Arm Power", angleArm.getPower());
            telemetry.addData("RearLeftSensor", RLdistance);
            telemetry.addData("RearRightSensor", RRdistance);
            //telemetry.addData("FrontSensor",FrontColor);
            telemetry.update();


            //Controller 1 Auto Tele-op
            if (gamepad1.guide && gamepad1.start) { //guide button = mode button
                //0 = Driver Mode
                //1 = Test Mode
                //2 = Servo Mode
                if (testModeV == 0) {
                    testModeV = 1;
                } else if (testModeV == 1) {
                    testModeV = 2;
                } else {
                    testModeV = 0;
                }
            }

            //Test Mode
            if (testModeV == 1) {

                if (gamepad1.left_stick_button) {
                    SD = .25;
                } else {
                    SD = 1;
                }

                //Resets NavX heading
                if (gamepad1.back) {
                    zeroGyro();
                }


                if (gamepad1.x) {
                    horizArmPIDTarget = 1000;
                }

                if (gamepad1.y) {
                    horizArmPIDTarget += 100;
                }

                if (gamepad1.a) {
                    horizArmPIDTarget = 0;
                }

                if (gamepad1.b) {
                    horizArmPIDTarget -= 100;
                }

                //Opens horizClaw
                if (gamepad1.dpad_down) {
                    horizClaw.setPosition(HORIZONTAL_CLAW_OPEN);
                }

                //Closes horizClaw
                if (gamepad1.dpad_up) {
                    horizClaw.setPosition(HORIZONTAL_CLAW_CLOSE);
                }

                if (gamepad1.dpad_left) {
                    horizClaw.setPosition((HORIZONTAL_CLAW_MIDDLE));
                }

                if (gamepad1.dpad_right) {
                    horizClaw.setPosition(HORIZONTAL_CLAW_HALF_CLOSE);
                }

                //Moves angleArm up and down

                if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
                    angleArmPIDTarget = 1000;
                }

                if (gamepad1.right_bumper) {
                    angleArmPIDTarget += 100;
                }

                if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
                    angleArmPIDTarget = 0;
                }

                if (gamepad1.left_bumper) {
                    angleArmPIDTarget -= 100;
                }

                if (gamepad2.right_trigger > TRIGGER_THRESHOLD) {
                    vertArmPIDTarget = 1000;
                }

                if (gamepad2.right_bumper) {
                    vertArmPIDTarget += 100;
                }

                if (gamepad2.left_trigger > TRIGGER_THRESHOLD) {
                    vertArmPIDTarget = 0;
                }

                if (gamepad2.left_bumper) {
                    vertArmPIDTarget -= 100;
                }

                //Opens and Closes Transfer Claw
                //Opens transfer claw
                if (gamepad2.dpad_left) {
                    transferClaw.setPosition(TRANSFER_CLAW_OPEN);
                }

                //Close transfer claw
                if (gamepad2.dpad_right) {
                    transferClaw.setPosition(TRANSFER_CLAW_CLOSE);
                }

                //Moves transferArmBottom to front
                if (gamepad2.dpad_up) {
                    transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_FRONT);
                }

                //Moves transferArmBottom to back
                if (gamepad2.dpad_down) {
                    transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_BACK);
                }

                //Moves transferArmTop to front
                if (gamepad2.right_bumper) {
                    transferArmTop.setPosition(TRANSFER_ARM_TOP_FRONT);
                }

                //Moves transferArmTop to back
                if (gamepad2.left_bumper) {
                    transferArmTop.setPosition(TRANSFER_ARM_TOP_BACK);
                }

            }
            //Servo Test Mode
            if (testModeV == 2) {
                if (gamepad1.x) {
                    servoPosition += .1;
                }

                if (gamepad1.a) {
                    servoPosition -= .1;
                }

                if (gamepad1.y) {
                    servoPosition += .01;
                }

                if (gamepad1.b) {
                    servoPosition -= .01;
                }

                if (gamepad1.dpad_down) {
                    servoTest.setPosition(servoPosition);
                }
            }

            //Driver mode
            if (testModeV == 0) {
                //Slows movement
                if (gamepad1.left_stick_button) {
                    SD = .25;
                } else {
                    SD = 1;
                }

                //Resets NavX heading
                if (gamepad1.back) {
                    zeroGyro();
                }

                //Extends and Retracts horizArm
                if (gamepad1.x) {
                    horizArmPIDTarget = 1000;
                }

                if (gamepad1.y) {
                    horizArmPIDTarget += 50;
                }

                if (gamepad1.a) {
                    horizArmPIDTarget = 0;
                }

                if (gamepad1.b) {
                    horizArmPIDTarget -= 50;
                }

                //Opens horizClaw
                if (gamepad1.dpad_down) {
                    horizClaw.setPosition(HORIZONTAL_CLAW_OPEN);
                }

                //Closes horizClaw
                if (gamepad1.dpad_left) {
                    horizClaw.setPosition(HORIZONTAL_CLAW_CLOSE);
                }

                if (gamepad1.dpad_up) {
                    horizClaw.setPosition((HORIZONTAL_CLAW_MIDDLE));
                }

                if (gamepad1.dpad_right) {
                    horizClaw.setPosition(HORIZONTAL_CLAW_HALF_CLOSE);
                }

                //Moves angleArm up and down
               if (gamepad1.right_trigger > TRIGGER_THRESHOLD) {
                    angleArmPIDTarget += 50;
                }

                if (gamepad1.right_bumper) {
                    angleArmPIDTarget = 1000;
                }

                if (gamepad1.left_trigger > TRIGGER_THRESHOLD) {
                    angleArmPIDTarget -= 50;
                }

                if (gamepad1.left_bumper) {
                    angleArmPIDTarget = 0;
                }

                //Moves vertArm
                if (gamepad2.right_trigger > TRIGGER_THRESHOLD) {
                    vertArmPIDTarget += 50;
                }

                if (gamepad2.right_bumper) {
                    vertArmPIDTarget = 1000;
                }

                if (gamepad2.left_trigger > TRIGGER_THRESHOLD) {
                    vertArmPIDTarget -= 50;
                }

                if (gamepad2.left_bumper) {
                    vertArmPIDTarget = 0;
                }

                //Opens and Closes Transfer Claw
                //Opens transfer claw
                if (gamepad2.dpad_right) {
                    transferClaw.setPosition(TRANSFER_CLAW_OPEN);
                }

                //Close transfer claw
                if (gamepad2.dpad_left) {
                    transferClaw.setPosition(TRANSFER_CLAW_CLOSE);
                }

                //Moves transferArmBottom to front
                if (gamepad2.x) {
                    //0 = Middle Position
                    //1 = Front Position
                    //-1 = Back Position
                    // if (transferClawPosition == -1) {

                    //     transferClawPosition = transferClawPosition + 1;
                    //     transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_CENTER);
                    //     transferArmTop.setPosition(TRANSFER_ARM_TOP_CENTER);
                    // } else if (transferClawPosition == 0) {
                    //     transferClawPosition = transferClawPosition + 1;
                    transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_FRONT);
                    transferArmTop.setPosition(TRANSFER_ARM_TOP_FRONT);
                    // }
                }

                if (gamepad2.y) {
                    //0 = Middle Position
                    //1 = Front Position
                    //-1 = Back Position

                    // if (transferClawPosition == 1) {
                    //     transferClawPosition = transferClawPosition - 1;
                    //      transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_CENTER);
                    //      transferArmTop.setPosition(TRANSFER_ARM_TOP_CENTER);
                    //  } else if (transferClawPosition == 0) {
                    //     transferClawPosition = transferClawPosition - 1;
                    transferArmBotttom.setPosition(TRANSFER_ARM_BOTTOM_BACK);
                    transferArmTop.setPosition(TRANSFER_ARM_TOP_BACK);
                    //  }
                }
                if (gamepad2.a) {
                    //0 = Middle Position
                    //1 = Front Position
                    //-1 = Back Position
                    if (transferClawPosition == 1) {
                        transferClaw.setPosition(TRANSFER_CLAW_CLOSE);



                        transferClawPosition = transferClawPosition + 1;
                        transferClaw.setPosition(TRANSFER_CLAW_CLOSE);
                    } else if (transferClawPosition == 0) {
                        transferClawPosition = transferClawPosition + 1;
                        transferClaw.setPosition(TRANSFER_CLAW_OPEN);
                    }
                }
            }
        }
    }
}