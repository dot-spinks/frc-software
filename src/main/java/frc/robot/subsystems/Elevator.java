package frc.robot.subsystems;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.elevatorConstants;



public class Elevator extends SubsystemBase{
   private final TalonFX leftMotor = new TalonFX(Constants.canIDConstants.elevatorMotor1, "canivore");
    private final TalonFX rightMotor = new TalonFX(Constants.canIDConstants.elevatorMotor2, "canivore");
    private StatusSignal<Double> current1;
    private StatusSignal<Double> temp1;
    private StatusSignal<Double> RPS1;
    private StatusSignal<Double> position1;
    private double setPointMeters;

   private VoltageOut voltagerequest = new VoltageOut(0).withEnableFOC(true);
   private MotionMagicVoltage motionMagicRequest = new MotionMagicVoltage(0).withEnableFOC(true);

   public Elevator(){

    current1 = leftMotor.getStatorCurrent();
    temp1 = leftMotor.getDeviceTemp();
    RPS1 = leftMotor.getRotorVelocity();
    position1 = leftMotor.getPosition();
    
    var leftMotorConfigs = new TalonFXConfiguration();

    leftMotorConfigs.CurrentLimits.StatorCurrentLimit = Constants.elevatorConstants.statorCurrentLimit;
    leftMotorConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    leftMotorConfigs.MotorOutput.Inverted = Constants.elevatorConstants.leftMotorInvert;
    leftMotorConfigs.MotorOutput.NeutralMode = NeutralModeValue.Brake;

leftMotorConfigs.MotionMagic.MotionMagicAcceleration = Constants.elevatorConstants.AccelerationUp;
leftMotorConfigs.MotionMagic.MotionMagicCruiseVelocity = Constants.elevatorConstants.CruiseVelocityUp;
leftMotorConfigs.MotionMagic.MotionMagicJerk = Constants.elevatorConstants.Jerk;

leftMotorConfigs.Slot0.kP = Constants.elevatorConstants.kP;
leftMotorConfigs.Slot0.kD = Constants.elevatorConstants.kD;
leftMotorConfigs.Slot0.kS = Constants.elevatorConstants.kS;
leftMotorConfigs.Slot0.kV = Constants.elevatorConstants.kV;
leftMotorConfigs.Slot0.kG = Constants.elevatorConstants.kG;

leftMotor.getConfigurator().apply(leftMotorConfigs);

rightMotor.setControl(new Follower(leftMotor.getDeviceID(), true));

BaseStatusSignal.setUpdateFrequencyForAll(
50,
current1,
temp1,
RPS1,
position1
);
   }

public void setPosition(double positionMeters){
    double positionRotations = positionMeters / elevatorConstants.wheelCircumferenceMeters * elevatorConstants.gearRatio;
    leftMotor.setControl(motionMagicRequest.withPosition(positionRotations));
}

public void periodic(){
BaseStatusSignal.refreshAll(current1, temp1, RPS1, position1);
SmartDashboard.putNumber("Elevator Current", current1.getValue());
SmartDashboard.putNumber("Elevator Temperature", temp1.getValue());
SmartDashboard.putNumber("Elevator RPS", RPS1.getValue());
SmartDashboard.putNumber("Elevator Position", position1.getValue());
    
}

public boolean atSetPoint(){
 
}
    }   