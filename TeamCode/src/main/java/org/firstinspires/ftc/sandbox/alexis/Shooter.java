package org.firstinspires.ftc.sandbox.alexis;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public class Shooter {
    DcMotor leftShooter;
    DcMotor rightShooter;
    double shooterPower = 0.4;
    double nectarPower = 0.6;
    double pollenPower = 0.4;
    boolean nectarMode = false;

    public Shooter(HardwareMap hardwareMap) {
        leftShooter = hardwareMap.get(DcMotor.class, "leftShooter");
        rightShooter = hardwareMap.get(DcMotor.class, "rightShooter");

        rightShooter.setDirection(DcMotorSimple.Direction.FORWARD);
        leftShooter.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void shooterTTfx(Gamepad gamepad, Telemetry telemetry) {
        // gamepad 2

        // Shoots artifact
        if (gamepad.a) {
            leftShooter.setPower(shooterPower);
            rightShooter.setPower(shooterPower);
            telemetry.addData("Shooter Status", "Out");
        } else {
            leftShooter.setPower(0);
            rightShooter.setPower(0);
            telemetry.addData("Shooter Status", "Off");
        }

        // change power

        if (gamepad.leftBumperWasPressed()) {
            if (!nectarMode) {
                nectarMode = true;
                shooterPower = nectarPower;
            } else {
                nectarMode = false;
                shooterPower = pollenPower;
            }
        }
    }
}

