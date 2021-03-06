package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class Main extends LinearOpMode {
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

        double driveLeftPower = 0;
        double driveRightPower = 0;
        double drivePower = 0;
        double turnPower = 0;
        double INTAKE_POWER = 1;
        double REVERSE_INTAKE = -1;
        double ARM_EXTENDED = 0.0;
        double ARM_RETRACTED = 0.5;
        boolean intakeOn = false;

        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        intakeLeft.setDirection(DcMotor.Direction.REVERSE);
        motorLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armServo.setPosition(ARM_RETRACTED);


        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Capture Controller Data
            drivePower = -this.gamepad1.left_stick_y;
            turnPower = -this.gamepad1.right_stick_x;

            if (gamepad1.left_bumper){
              intakeOn = true;
            }

            if (gamepad1.right_bumper){
              intakeOn = false;
            }
            if (gamepad1.x){
              intakeLeft.setPower(REVERSE_INTAKE);
              intakeRight.setPower(REVERSE_INTAKE);
            }
            if(intakeOn == true){
                intakeLeft.setPower(INTAKE_POWER);
                intakeRight.setPower(INTAKE_POWER);
            } else {
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
            }
            motorLeft.setPower(drivePower - turnPower);
            motorRight.setPower(drivePower + turnPower);


            telemetry.addData("Status", "Running");
            telemetry.addData("Drive Power", drivePower);
            telemetry.addData("Turn Power", turnPower);
            telemetry.addData("Encoder Distance for 4m", distanceTraveled(5));
            telemetry.addData("Encoder position", motorLeft.getCurrentPosition());
            telemetry.update();

        }
    }
}
