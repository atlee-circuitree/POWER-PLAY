package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
    int CordX = 35;
        int Side = 1;

        double CordY = -58.33333333;
    int StartingAngle = 90;
            RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 20.24)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(CordX, CordY, 90))
                                .lineToConstantHeading(new Vector2d(Side * 36, 6))
                                .lineToConstantHeading(new Vector2d(Side * 36, 20))
                                .lineToSplineHeading(new Pose2d(Side * 33, 4, Math.toRadians(270 - (75 * Side))))
                                .strafeLeft(Side * 3.75)
                                .forward(6)
                                .back(4)

                                //.lineTo(new Vector2d(Side * 57, 10))
                                .build()
                );



        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}