package frc.robot.subsystems;

import java.util.random.RandomGenerator.LeapableGenerator;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

//(two motors, set as follower, motionmagic velocity voltage req)
public class shooter {
    private TalonFX rightShooterMotor = new TalonFX(Constants.canIDConstants.rightShooterMotor, "canivore");
    private TalonFX leftShooterMotor = new TalonFX(Constants.canIDConstants.leftShooterMotor, "canivore");
    
    private StatusSignal<Double> RPS = leftShooterMotor.getRotorVelocity();
    private StatusSignal<Double> temp = leftShooterMotor.getDeviceTemp();
    private StatusSignal<Double> current = leftShooterMotor.getStatorCurrent();

    private VoltageOut shootRequestVoltage = new VoltageOut(0).withEnableFOC(true);
    private VelocityVoltage leftRequestVelocity = new VelocityVoltage(0).withEnableFOC(true);
    private VelocityVoltage rightRequestVelocity = new VelocityVoltage(0).withEnableFOC(true);

    public shooter(){
    var leftMotorConfigs = new TalonFXConfiguration();
    leftMotorConfigs.CurrentLimits.StatorCurrentLimit = Constants.shooterConstants.statorCurrentLimit;
    leftMotorConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    leftMotorConfigs.MotorOutput.Inverted = //idk
    leftMotorConfigs.Slot0.kP = Constants.shooterConstants.kP;
    leftMotorConfigs.Slot0.kI = 0.0;
    leftMotorConfigs.Slot0.kD = Constants.shooterConstants.kD;
    leftMotorConfigs.Slot0.kS = Constants.shooterConstants.kS;
    leftMotorConfigs.Slot0.kV = Constants.shooterConstants.kV;
    leftMotorConfigs.Slot0.kA = Constants.shooterConstants.kA;

    var rightMotorConfigs = new TalonFXConfiguration();
    rightMotorConfigs.CurrentLimits.StatorCurrentLimit = Constants.shooterConstants.statorCurrentLimit;
    rightMotorConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
    rightMotorConfigs.MotorOutput.Inverted = //idk
    rightMotorConfigs.Slot0.kP = Constants.shooterConstants.kP;
    rightMotorConfigs.Slot0.kI = 0.0;
    rightMotorConfigs.Slot0.kD = Constants.shooterConstants.kD;
    rightMotorConfigs.Slot0.kS = Constants.shooterConstants.kS;
    rightMotorConfigs.Slot0.kV = Constants.shooterConstants.kV;
    rightMotorConfigs.Slot0.kA = Constants.shooterConstants.kA;

    leftShooterMotor.getConfigurator().apply(leftMotorConfigs);
    rightShooterMotor.getConfigurator().apply(rightMotorConfigs);
    rightShooterMotor.setControl(new Follower(leftShooterMotor.getDeviceID, true));

    BaseStatusSignal.setUpdateFrequencyForAll(
        50,
        current,
        temp,
        RPS
    );

    
    }

    public void periodic(){
      BaseStatusSignal.refreshAll(current1, temp1, RPS1, position1);
        SmartDashboard.putNumber("Elevator Position", position1.getValue());
        SmartDashboard.putNumber("Elevator Current", current1.getValue());
        SmartDashboard.putNumber("Elevator Temperature", temp1.getValue());
        SmartDashboard.putNumber("Elevator Speed (RPS)", RPS1.getValue());
    }
    public void setVelocity(double velocity, double ratio) {
        leftShooterMotor.setControl(leftRequestVelocity.withVelocity(Conversions.MPStoRPS(velocity, Constants.shooterConstants.wheelCircumferenceMeters, 1.0)));
        rightShooterMotor.setControl(rightRequestVelocity.withVelocity(Conversions.MPStoRPS(velocity * ratio, Constants.shooterConstants.wheelCircumferenceMeters, 1.0)));
    }

    public void setVoltage(double voltage) {
        rightShooterMotor.setControl(new Follower(leftMotor.getDeviceID(), true));
        leftShooterMotor.setControl(shootRequestVoltage.withOutput(voltage));
}
