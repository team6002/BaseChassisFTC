
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class Auto_Red_Wall extends LinearOpMode {
    private Gyroscope imu;
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor intakeLeft;
    private DcMotor intakeRight;
    private DigitalChannel glyphSwitch;
    private Servo armServo;

    public double distanceTraveled(int distance){
      // Encoder ticks
      return (360/4) * distance;
    }


    @Override
    public void runOpMode() {
        imu = hardwareMap.get(Gyroscope.class, "imu");
        motorLeft = hardwareMap.get(DcMotor.class, "motorLeft");
        motorRight = hardwareMap.get(DcMotor.class, "motorRight");
        intakeLeft = hardwareMap.get(DcMotor.class, "intakeLeft");
        intakeRight = hardwareMap.get(DcMotor.class, "intakeRight");
        glyphSwitch = hardwareMap.get(DigitalChannel.class, "glyphSwitch");
        armServo = hardwareMap.get(Servo.class, "armServo");

        int auto_Step = 0;

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        intakeLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // armServo.setPosition(ARM_RETRACTED);


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            if (auto_Step == 0) {
              // if (color Matches == Red){
                // Turn Left
                if(motorLeft.getCurrentPosition() < distanceTraveled(4)){
                  motorLeft.setPower(0.5);
                  motorRight.setPower(0.5);
                }
              // }
            //   auto_Step++;
            }

            motorLeft.setPower(0.0);
            motorRight.setPower(0.0);

            telemetry.addData("Status", "Running");
            telemetry.addData("Encoder Position", motorLeft.getCurrentPosition());
            telemetry.addData("Distance Traveling", distanceTraveled(4));
            telemetry.addData("Step", auto_Step);
            telemetry.update();
        }
    }
}
