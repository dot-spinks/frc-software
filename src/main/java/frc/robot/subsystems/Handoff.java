package frc.robot.subsystems;

import frc.robot.Constants;

//Handoff (one motor, voltage out req)

public class Handoff extends SubsystemBase {
private final TalonFX handoffMotor;
private StatusSignal<Double> current; //whats <Double>
private StatusSignal<Double> RPS;
private StatusSignal<Double> temp;

private VoltageOut handoffRequest; 

public Handoff(){

   handoffMotor = new TalonFX(Constants.canIDConstants.handoffMotor, "canivore");
   handoffRequest = new VoltageOut(0).withEnableFOC(tru);
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
   )

   handoffMotor.getConfigurator().apply(handoffMotorConfigs);
}

public void periodic(){

    BaseStatusSignal.refreshAll(current, RPS, temp);
    SmartDashboard.putNumber("Handoff Current", current.getValue());
    SmartDashboard.putNumber("Handoff RPS", RPS.getValue());
     SmartDashboard.putNumber("Handoff temp", temp,getValue());
}
}
