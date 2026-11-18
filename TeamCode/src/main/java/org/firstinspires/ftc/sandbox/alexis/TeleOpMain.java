package org.firstinspires.ftc.sandbox.alexis;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/* i bought a usb-a to usb-c adapter
--to put on the orange dongle-- inside of the white big toolbox (big bottom right drawer, please return it to original home after use*/

@TeleOp (name="Celesteels Op Mode, im ngl this is copied straight from our old TT code", group="Linear OpMode")
public class TeleOpMain extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        // Init Hardware
        Drive driversTT = new Drive(hardwareMap);
        Shooter shootersTT = new Shooter(hardwareMap);
        Intake intakersTT = new Intake(hardwareMap);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            // Robot Functions
            driversTT.driveTTfx(gamepad1, telemetry);
            shootersTT.shooterTTfx(gamepad1, telemetry);
            intakersTT.intakeTTfx(gamepad1, telemetry);
            intakersTT.outtakeTTfx(gamepad1, telemetry);

            // Telemetry (will do later)
        }

    }

}
