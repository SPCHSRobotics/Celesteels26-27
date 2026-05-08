package org.firstinspires.ftc.sandbox.ainsley;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="ainsleyTeleOp")
public class test1 extends OpMode {
    DcMotor rfMotor;
    DcMotor rbMotor;
    public void init() {
        rfMotor = hardwareMap.get(DcMotor.class, "rfMotor");

    }

    public void loop() {
        rfMotor.setPower(1);
    }

}
