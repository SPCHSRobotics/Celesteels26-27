package org.firstinspires.ftc.sandbox.ainsley;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="ainsleyTeleOp")
public class test1 extends OpMode {
    private static final double FRONT_INTAKE_POWER = 0.5;
    private static final double STAIRCASE_INTAKE_POWER = 0.5;
    private static final double FRONT_REVERSE_POWER = 0.4;
    private static final double STAIRCASE_REVERSE_POWER = 0.4;
    private static final double TEST_INTAKE_POWER = 0.2; // Temporary individual tests
    private static final double TEST_INTAKE_POWER = 0.2;
    private static final double SHOOTER_POWER = -1;

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontIntakeMotor;
    private DcMotor staircaseIntakeMotor;
    private DcMotor leftShooter;
    private DcMotor rightShooter;
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void init() {

        //cm0 - control hub motor 0, emo - expansion hub 0

        frontLeftDrive = hardwareMap.get(DcMotor.class, "cm2");
        backLeftDrive = hardwareMap.get(DcMotor.class, "cm3");
        frontRightDrive = hardwareMap.get(DcMotor.class, "cm1");
        backRightDrive = hardwareMap.get(DcMotor.class, "cm0");
        frontIntakeMotor = hardwareMap.get(DcMotor.class, "em0");
        staircaseIntakeMotor = hardwareMap.get(DcMotor.class, "em3");
        leftShooter = hardwareMap.get(DcMotor.class, "em1");
        rightShooter = hardwareMap.get(DcMotor.class, "em2");

        // Change either direction after physically testing its stage
        frontIntakeMotor.setDirection(DcMotor.Direction.FORWARD);
        staircaseIntakeMotor.setDirection(DcMotor.Direction.FORWARD);

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        double max;


        double axial   = -gamepad1.left_stick_y;
        double lateral = gamepad1.left_stick_x;
        double yaw     = gamepad1.right_stick_x;


        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;


        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }


        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);


        double frontIntakePower = 0;
        double staircaseIntakePower = 0;
        if (gamepad1.left_bumper) {
            frontIntakePower = -FRONT_REVERSE_POWER;
            staircaseIntakePower = -STAIRCASE_REVERSE_POWER;
        } else if (gamepad1.right_bumper) {
            frontIntakePower = FRONT_INTAKE_POWER;
            staircaseIntakePower = STAIRCASE_INTAKE_POWER;
        } else if (gamepad1.x && !gamepad1.y) {
            // TEMPORARY TEST
            frontIntakePower = TEST_INTAKE_POWER;
        } else if (gamepad1.y && !gamepad1.x) {
            // TEMPORARY TEST
            staircaseIntakePower = TEST_INTAKE_POWER;
        }
        frontIntakeMotor.setPower(frontIntakePower);
        staircaseIntakeMotor.setPower(staircaseIntakePower);

        double shooterPower = gamepad1.a ? SHOOTER_POWER : 0;
        leftShooter.setPower(shooterPower);
        rightShooter.setPower(shooterPower);
        telemetry.addData("Shooter Status", gamepad1.a ? "Out" : "Off");

        telemetry.addData("Front Intake", intakeStatus(frontIntakePower));
        telemetry.addData("Staircase Intake", intakeStatus(staircaseIntakePower));
        telemetry.addData("Front intake commanded power", "%.2f", frontIntakePower);
        telemetry.addData("Staircase intake commanded power", "%.2f", staircaseIntakePower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.update();
    }

    @Override
    public void stop() {
        frontLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backLeftDrive.setPower(0);
        backRightDrive.setPower(0);
        frontIntakeMotor.setPower(0);
        staircaseIntakeMotor.setPower(0);
        leftShooter.setPower(0);
        rightShooter.setPower(0);
    }


    private String intakeStatus(double power) {
        if (power > 0) return "IN";
        if (power < 0) return "OUT";
        return "STOPPED";
    }

}
