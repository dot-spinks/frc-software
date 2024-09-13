package frc.robot.subsystems;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//Handoff (one motor, voltage out req)

public class Handoff extends SubsystemBase {
private final TalonFX handoffMotor;
private VoltageOut handoffRequest;
private StatusSignal<Double> current; //whats <Double>
private StatusSignal<Double> RPS;
private StatusSignal<Double> temp;
private double setpointVolts;


public Handoff(){

   handoffMotor = new TalonFX(Constants.canIDConstants.handoffMotor, "canivore");
   handoffRequest = new VoltageOut(0).withEnableFOC(true);
   current = handoffMotor.getStatorCurrent();
   RPS = handoffMotor.getRotorVelocity();
   temp = handoffMotor.getDeviceTemp();

   var handoffMotorConfigs = new TalonFXConfiguration();
   handoffMotorConfigs.CurrentLimits.StatorCurrentLimit = Constants.handoffConstants.statorCurrentLimit;
   handoffMotorConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    
   BaseStatusSignal.setUpdateFrequencyForAll(
    50,
    current,
    RPS,
    temp
   );

   handoffMotor.getConfigurator().apply(handoffMotorConfigs);
}

public void handoffRequest(double voltage){
   setpointVolts = voltage;
   handoffMotor.setControl(handoffRequest.withOutput(voltage));
}
public void periodic(){

    BaseStatusSignal.refreshAll(current, RPS, temp);
    SmartDashboard.putNumber("Handoff Current", current.getValue());
    SmartDashboard.putNumber("Handoff RPS", RPS.getValue());
     SmartDashboard.putNumber("Handoff temp", temp.getValue());
}
}
